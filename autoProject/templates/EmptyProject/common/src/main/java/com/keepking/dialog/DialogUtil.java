/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */

/**
 * 文件名 : DialogUtil.java
 * 包含类名列表 : DialogUtil
 * 版本信息 : Ver 1.0
 * 创建日期 : 2016年09月14日 17:34
 */
package com.keepking.dialog;

import com.afollestad.materialdialogs.MaterialDialog;

import android.content.Context;
import android.graphics.Color;

/**
 * 类名 : DialogUtil
 * 作者 : wangying
 * 实现的主要功能 :
 * 创建日期 : 2016年09月14日 17:34
 */
public class DialogUtil {

    public static void show(Context context,String content){
        new MaterialDialog.Builder(context).content(content).backgroundColor(Color.WHITE).build().show();
    }
}