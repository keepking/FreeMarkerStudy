package com.keepking;

import com.afollestad.materialdialogs.MaterialDialog;
import com.keepking.titlebar.TitleBar;
import com.keepking.toast.ToastUtil;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import ${project.applicationPackage}.R;

public class MainActivity extends Activity {

    @BindView(R.id.title) TitleBar mTitleBar;
    @BindView(R.id.tv_hello) TextView mHelloTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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



        mTitleBar.addAction(new TitleBar.ImageAction(R.drawable.ic_error) {
            @Override
            public void performAction(View view) {
                ToastUtil.show("error");
            }
        });

        mTitleBar.addAction(new TitleBar.TextAction("good") {
            @Override
            public void performAction(View view) {
                new MaterialDialog.Builder(MainActivity.this).title("good").content("job").backgroundColor(Color
                        .WHITE).build().show();
            }
        });

        mTitleBar.setBackgroundColor(Color.YELLOW);
    }
}