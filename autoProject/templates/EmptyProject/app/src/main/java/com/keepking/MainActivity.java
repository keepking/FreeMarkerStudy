package com.keepking;

import com.keepking.titlebar.TitleBar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import ${project.applicationPackage}.R;

public class MainActivity extends Activity {

    TitleBar mTitleBar;
    TextView mHelloTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTitleBar = (TitleBar) findViewById(R.id.title);
        mHelloTv = (TextView) findViewById(R.id.tv_hello);

        mTitleBar.setImmersive(true);

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

            }
        });

        mTitleBar.addAction(new TitleBar.TextAction("good") {
            @Override
            public void performAction(View view) {

            }
        });

        mTitleBar.setBackgroundColor(Color.YELLOW);
    }
}
