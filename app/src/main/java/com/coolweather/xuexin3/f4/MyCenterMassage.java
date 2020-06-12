package com.coolweather.xuexin3.f4;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.ImageGetterUtils;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.makeramen.roundedimageview.RoundedImageView;

public class MyCenterMassage extends AppCompatActivity {

    private SeletDatas mSeletDatas;
    private TextView mContent;
    public  Boolean b_zan = false;
    private ImageView mZan;
    private RoundedImageView mPhoto;
    private TextView mNeckname;
    private TextView mGender;
    private TextView mSignature;
    private ImageView mImg1;
    private ImageView mImg2;
    private ImageView mImg3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center_massage);
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
    }
    private void init() {


        mPhoto = findViewById(R.id.img_cm_photo);
        mNeckname = findViewById(R.id.tv_cm_neckname);
        mSignature = findViewById(R.id.tv_cm_signature);
        mContent = findViewById(R.id.tv_cm_content);
        mZan = findViewById(R.id.iv_cm_dianzan);
        mImg1 = findViewById(R.id.iv_cm_img1);
        mImg2 = findViewById(R.id.iv_cm_img2);
        mImg3 = findViewById(R.id.iv_cm_img3);


        if(mSeletDatas.photo!=null){
            Glide.with(this).load(mSeletDatas.photo).into(mPhoto);
        }
        mNeckname.setText(mSeletDatas.nickName);
        if (mSeletDatas.signature == null) {
            mSignature.setText("暂无");
        }else {
            mSignature.setText(mSeletDatas.signature);
        }
        mContent.setText(Html.fromHtml(mSeletDatas.content,new ImageGetterUtils.MyImageGetter(this,mContent),null));

        String[] imgs = mSeletDatas.photos.split(",");
        if(imgs.length>=3){
            Glide.with(MyCenterMassage.this).load(imgs[2]).into(mImg3);
        }
        if(imgs.length>=2){
            Glide.with(MyCenterMassage.this).load(imgs[1]).into(mImg2);
        }
        if(imgs.length>=1){
            Glide.with(MyCenterMassage.this).load(imgs[0]).into(mImg1);
        }

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


}
