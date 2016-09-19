package com.keepking;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.utils.ImageUtils;
import com.keepking.titlebar.TitleBar;
import com.keepking.toast.ToastUtil;
import com.keepking.viewpager.JazzyViewPager;
import com.keepking.viewpager.OutlineContainer;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.BezierPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import ${project.applicationPackage}.R;

public class MainActivity extends Activity {

    /**
     * 扫描跳转Activity RequestCode
     */
    public static final int REQUEST_CODE = 111;
    /**
     * 选择系统图片Request Code
     */
    public static final int REQUEST_IMAGE = 112;

    @BindView(R.id.title)
    TitleBar mTitleBar;
    @BindView(R.id.tv_hello)
    TextView mHelloTv;
    @BindView(R.id.pager)
    JazzyViewPager mPager;
    @BindView(R.id.indicator)
    MagicIndicator mIndicator;
    @BindView(R.id.view_pager_ptr_frame)
    PtrFrameLayout mPtrFrame;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //标题栏设置沉浸式
            mTitleBar.setImmersive(true);
        }

        mTitleBar.setLeftText("返回");
        mTitleBar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mTitleBar.setTitle("主标题\n副标题");

        mTitleBar.setTitleSize(24);
        mTitleBar.setSubTitleSize(12);

        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_launcher) {
            @Override
            public void performAction(View view) {
                ToastUtil.show("error");
                new MaterialDialog.Builder(MainActivity.this).title("扫描二维码").backgroundColor(Color
                        .WHITE).items("照相机","图库").itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                        Intent intent = null;
                        switch (position){
                            case 0:
                                intent = new Intent(getApplication(), CaptureActivity.class);
                                startActivityForResult(intent, REQUEST_CODE);
                                break;
                            case 1:
                                intent = new Intent(Intent.ACTION_GET_CONTENT);
                                intent.addCategory(Intent.CATEGORY_OPENABLE);
                                intent.setType("image/*");
                                startActivityForResult(intent, REQUEST_IMAGE);
                                break;
                        }
                    }
                }).build().show();
            }
        });

        mTitleBar.addAction(new TitleBar.TextAction("good") {
            @Override
            public void performAction(View view) {
                new MaterialDialog.Builder(MainActivity.this).title("good").content("job").backgroundColor(Color
                        .WHITE).build().show();
                EventBus.getDefault().post("good job");
            }
        });

        mTitleBar.setBackgroundColor(Color.YELLOW);

        //setup JazzyViewPager
        mPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return getResources().getStringArray(R.array.jazzy_effects).length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                if (view instanceof OutlineContainer) {
                    return ((OutlineContainer) view).getChildAt(0) == object;
                }
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                TextView text = new TextView(MainActivity.this);
                text.setGravity(Gravity.CENTER);
                text.setTextSize(30);
                text.setTextColor(Color.WHITE);
                text.setText(getResources().getStringArray(R.array.jazzy_effects)[position]);
                text.setPadding(30, 30, 30, 30);
                int bg = Color.rgb((int) Math.floor(Math.random() * 128) + 64,
                        (int) Math.floor(Math.random() * 128) + 64,
                        (int) Math.floor(Math.random() * 128) + 64);
                text.setBackgroundColor(bg);
                container.addView(text, ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT);
                mPager.setObjectForPosition(text, position);
                return text;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mPager.findViewFromObject(position));
            }
        });
        mPager.setTransitionEffect(JazzyViewPager.TransitionEffect.Accordion);
        //        mPager.setOutlineEnabled(true);
        //        mPager.setOutlineColor(Color.WHITE);

        //setup MagicIndicator
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return getResources().getStringArray(R.array.jazzy_effects).length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView titleView = new SimplePagerTitleView(context);
                titleView.setText(getResources().getStringArray(R.array.jazzy_effects)[index]);
                titleView.setNormalColor(Color.WHITE);
                titleView.setSelectedColor(context.getResources().getColor(android.R.color.holo_orange_light));
                titleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPager.setCurrentItem(index, true);
                        for (JazzyViewPager.TransitionEffect effect : JazzyViewPager.TransitionEffect.values()) {
                            if (effect.ordinal() == index) {
                                mPager.setTransitionEffect(effect);
                                break;
                            }
                        }
                    }
                });
                return titleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                BezierPagerIndicator indicator = new BezierPagerIndicator(context);
                indicator.setColors(context.getResources().getColor(android.R.color.holo_orange_light));
                indicator.setMaxCircleRadius(6);
                indicator.setMinCircleRadius(3);
                return indicator;
            }
        });
        mIndicator.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mIndicator, mPager);

        //init ultra-pull-to-refresh
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                },2000);
            }
        });
    }

    @Subscribe
    public void goodjob(String msg){
        ToastUtil.show(msg,ToastUtil.INFO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        /**
         * 处理二维码扫描结果
         */
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtil.show("解析结果:" + result);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show("解析二维码失败");
                }
            }
        }

        /**
         * 选择系统图片并解析
         */
        else if (requestCode == REQUEST_IMAGE) {
            if (data != null) {
                Uri uri = data.getData();
                try {
                    CodeUtils.analyzeBitmap(ImageUtils.getImageAbsolutePath(this, uri), new CodeUtils.AnalyzeCallback
                            () {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            ToastUtil.show("解析结果:" + result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            ToastUtil.show("解析二维码失败");
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}