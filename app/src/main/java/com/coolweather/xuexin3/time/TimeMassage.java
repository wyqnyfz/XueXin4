package com.coolweather.xuexin3.time;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.coolweather.xuexin3.ImageGetterUtils;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;

public class TimeMassage extends AppCompatActivity {
    private String TAG = "TimeMassage";

    private SeletDatas mSeletDatas;
    private TextView mTitle;
    private TextView mTime;
    private TextView mContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_massage);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //隐藏bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mSeletDatas = MyData.listSlect.get(id);
        init();
        initData();
    }

    private void init() {
        mTitle = findViewById(R.id.tv_tm_title);
        mTime = findViewById(R.id.tv_tm_time);
        mContent = findViewById(R.id.tv_tm_content);

    }

    private void initData() {
        mTitle.setText(mSeletDatas.title);
        mTime.setText(mSeletDatas.date);
        mContent.setText(Html.fromHtml(mSeletDatas.content,new ImageGetterUtils.MyImageGetter(this,mContent),null));
    }



}
