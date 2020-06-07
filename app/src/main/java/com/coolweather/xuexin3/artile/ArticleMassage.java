package com.coolweather.xuexin3.artile;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.ImageGetterUtils;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.makeramen.roundedimageview.RoundedImageView;

public class ArticleMassage extends AppCompatActivity {
    private String TAG = "ArticleMassage";

    private SeletDatas mSeletDatas;
    private boolean b_zan = false;

    private RoundedImageView mPhoto;
    private TextView mNeckname;
    private TextView mGender;
    private TextView mSignature;
    private TextView mContent;
    private ImageView mZan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_massage);

        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mSeletDatas = MyData.listSlect.get(id);
        init();
        initData();
    }

    private void init() {
        mPhoto = findViewById(R.id.img_am_photo);
        mNeckname = findViewById(R.id.tv_am_neckname);
        mGender = findViewById(R.id.tv_am_gender);
        mSignature = findViewById(R.id.tv_am_signature);
        mContent = findViewById(R.id.tv_am_content);
        mZan = findViewById(R.id.iv_am_dianzan);

        Glide.with(this).load(mSeletDatas.photo).into(mPhoto);
        mNeckname.setText(mSeletDatas.nickName);
        if(mSeletDatas.gender==null){
            mGender.setText("");
        }else {
            mGender.setText(mSeletDatas.gender);
        }

        if (mSeletDatas.signature == null) {
            mSignature.setText("暂无");
        }else {
            mSignature.setText(mSeletDatas.signature);
        }

        mContent.setText(Html.fromHtml(mSeletDatas.content,new ImageGetterUtils.MyImageGetter(this,mContent),null));

        mZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b_zan){
                    mZan.setImageResource(R.drawable.dianzan);
                    b_zan = false;
                }else {
                    mZan.setImageResource(R.drawable.dianzan1);
                    b_zan = true;
                }
            }
        });

    }

    private void initData() {

    }



}
