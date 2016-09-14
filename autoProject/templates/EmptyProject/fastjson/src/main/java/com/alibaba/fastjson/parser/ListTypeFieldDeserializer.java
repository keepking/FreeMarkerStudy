package com.alibaba.fastjson.parser;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.ParameterizedTypeImpl;

class ListTypeFieldDeserializer extends FieldDeserializer {

    private final Type         itemType;
    private ObjectDeserializer deserializer;
    private final boolean      array;

    public ListTypeFieldDeserializer(ParserConfig mapping, Class<?> clazz, FieldInfo fieldInfo){
        super(clazz, fieldInfo, JSONToken.LBRACKET);

        Type fieldType = fieldInfo.fieldType;
        Class<?> fieldClass= fieldInfo.fieldClass;
        if (fieldType instanceof ParameterizedType) {
            this.itemType = ((ParameterizedType) fieldType).getActualTypeArguments()[0];
            array = false;
        } else if (fieldClass.isArray()) {
            this.itemType = fieldClass.getComponentType();
            array = true;
        } else {
            this.itemType = Object.class;
            array = false;
        }
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public void parseField(DefaultJSONParser parser, Object object, Type objectType, Map<String, Object> fieldValues) {
        if (parser.lexer.token == JSONToken.NULL) {
            setValue(object, null);
            parser.lexer.nextToken();
            return;
        }

        JSONArray jsonArray = null;
        List list;
        if (array) {
            list = jsonArray = new JSONArray();
            jsonArray.setComponentType(itemType);
        } else {
            list = new ArrayList();
        }

        ParseContext context = parser.contex;

        parser.setContext(context, object, fieldInfo.name);
        parseArray(parser, objectType, list);
        parser.setContext(context);

        Object fieldValue;
        if (array) {
            Object[] arrayValue = (Object[]) Array.newInstance((Class<?>)itemType, list.size());
            fieldValue = list.toArray(arrayValue);
            jsonArray.setRelatedArray(fieldValue);
        } else {
            fieldValue = list;
        }

        if (object == null) {
            fieldValues.put(fieldInfo.name, fieldValue);
        } else {
            setValue(object, fieldValue);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    final void parseArray(DefaultJSONParser parser, Type objectType, Collection array) {
        Type itemType = this.itemType;
        ObjectDeserializer itemTypeDeser = this.deserializer;

        if (objectType instanceof ParameterizedType) {
            if (itemType instanceof TypeVariable) {
                TypeVariable typeVar = (TypeVariable) itemType;
                ParameterizedType paramType = (ParameterizedType) objectType;

                Class<?> objectClass = null;
                if (paramType.getRawType() instanceof Class) {
                    objectClass = (Class<?>) paramType.getRawType();
                }

                int paramIndex = -1;
                if (objectClass != null) {
                    for (int i = 0, size = objectClass.getTypeParameters().length; i < size; ++i) {
                        TypeVariable item = objectClass.getTypeParameters()[i];
                        if (item.getName().equals(typeVar.getName())) {
                            paramIndex = i;
                            break;
                        }
                    }
                }

                if (paramIndex != -1) {
                    itemType = paramType.getActualTypeArguments()[paramIndex];
                    if (!itemType.equals(this.itemType)) {
                        itemTypeDeser = parser.config.getDeserializer(itemType);
                    }
                }
            } else if (itemType instanceof ParameterizedType) {
                ParameterizedType parameterizedItemType = (ParameterizedType) itemType;
                Type[] itemActualTypeArgs = parameterizedItemType.getActualTypeArguments();
                if (itemActualTypeArgs.length == 1 && itemActualTypeArgs[0] instanceof TypeVariable) {
                    TypeVariable typeVar = (TypeVariable) itemActualTypeArgs[0];
                    ParameterizedType paramType = (ParameterizedType) objectType;

                    Class<?> objectClass = null;
                    if (paramType.getRawType() instanceof Class) {
                        objectClass = (Class<?>) paramType.getRawType();
                    }

                    int paramIndex = -1;
                    if (objectClass != null) {
                        for (int i = 0, size = objectClass.getTypeParameters().length; i < size; ++i) {
                            TypeVariable item = objectClass.getTypeParameters()[i];
                            if (item.getName().equals(typeVar.getName())) {
                                paramIndex = i;
                                break;
                            }
                        }

                    }

                    if (paramIndex != -1) {
                        itemActualTypeArgs[0] = paramType.getActualTypeArguments()[paramIndex];
                        itemType = new ParameterizedTypeImpl(itemActualTypeArgs, parameterizedItemType.getOwnerType(), parameterizedItemType.getRawType());
                    }
                }
            }
        }

        final JSONLexer lexer = parser.lexer;

        if (lexer.token != JSONToken.LBRACKET) {
            String errorMessage = "exepct '[', but " + JSONToken.name(lexer.token);
            if (objectType != null) {
                errorMessage += ", type : " + objectType;
            }
            throw new JSONException(errorMessage);
        }

        if (itemTypeDeser == null) {
            itemTypeDeser = deserializer = parser.config.getDeserializer(itemType);
        }

        int ch = lexer.ch;
        if (ch == '[') {
            int index = ++lexer.bp;
            lexer.ch = (index >= lexer.len ? //
                JSONLexer.EOI //
                : lexer.text.charAt(index));
            lexer.token = JSONToken.LBRACKET;
        } else if (ch == '{') {
            int index = ++lexer.bp;
            lexer.ch = (index >= lexer.len ? //
                JSONLexer.EOI //
                : lexer.text.charAt(index));
            lexer.token = JSONToken.LBRACE;
        } else if (ch == '"') {
            lexer.scanString();
        } else if (ch == ']') {
            int index = ++lexer.bp;
            lexer.ch = (index >= lexer.len ? //
                JSONLexer.EOI //
                : lexer.text.charAt(index));
            lexer.token = JSONToken.RBRACKET;
        } else {
            lexer.nextToken();
        }

        for (int i = 0;; ++i) {
            while (lexer.token == JSONToken.COMMA //
                    && (lexer.features & Feature.AllowArbitraryCommas.mask) != 0) {
                lexer.nextToken();
                continue;
            }

            if (lexer.token == JSONToken.RBRACKET) {
                break;
            }

            Object val = itemTypeDeser.deserialze(parser, itemType, i);
            array.add(val);

            if (parser.resolveStatus == DefaultJSONParser.NeedToResolve) {
                parser.checkListResolve(array);
            }

            if (lexer.token == JSONToken.COMMA) {
                ch = lexer.ch;
                if (ch == '[') {
                    int index = ++lexer.bp;
                    lexer.ch = (index >= lexer.len ? //
                        JSONLexer.EOI //
                        : lexer.text.charAt(index));
                    lexer.token = JSONToken.LBRACKET;
                } else if (ch == '{') {
                    int index = ++lexer.bp;
                    lexer.ch = (index >= lexer.len ? //
                        JSONLexer.EOI //
                        : lexer.text.charAt(index));
                    lexer.token = JSONToken.LBRACE;
                } else if (ch == '"') {
                    lexer.scanString();
                } else {
                    lexer.nextToken();
                }
                continue;
            }
        }

        if (lexer.ch == ',') {
            int index = ++lexer.bp;
            lexer.ch = (index >= lexer.len ? //
                JSONLexer.EOI //
                : lexer.text.charAt(index));
            lexer.token = JSONToken.COMMA;
        } else {
            lexer.nextToken();
        }
    }

}
