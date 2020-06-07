package com.coolweather.xuexin3.f2;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.ImageGetterUtils;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.makeramen.roundedimageview.RoundedImageView;

public class ShengHuoMassage extends AppCompatActivity {

    private SeletDatas mSeletDatas;
    private TextView mContent;
    public  Boolean b_zan = false;
    private RoundedImageView mPhoto;
    private TextView mNeckname;
    private TextView mSignature;
    private ImageView mZan;
    private TextView mZanNum;
    private TextView mPingNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sheng_huo_massage);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mSeletDatas = MyData.listSlect.get(id);
        init();
    }



    private void init() {
        mPhoto = findViewById(R.id.img_sh_photo);
        mNeckname = findViewById(R.id.tv_sh_neckname);
        mSignature = findViewById(R.id.tv_sh_signature);
        mContent = findViewById(R.id.tv_sh_content);
        mZan = findViewById(R.id.iv_sh_dianzan);
        mZanNum = findViewById(R.id.tv_sh_zan);
        mPingNum = findViewById(R.id.tv_sh_ping);


        Glide.with(this).load(mSeletDatas.photo).into(mPhoto);
        mNeckname.setText(mSeletDatas.nickName);
        if (mSeletDatas.signature == null) {
            mSignature.setText("暂无");
        }else {
            mSignature.setText(mSeletDatas.signature);
        }

        mContent.setText(Html.fromHtml(mSeletDatas.content,new ImageGetterUtils.MyImageGetter(this,mContent),null));
        if(mSeletDatas.focusStatus == 1){
            mZan.setImageResource(R.drawable.dianzan1);
            b_zan = true;
        }
        mZanNum.setText(mSeletDatas.likeAmount+"");
        mPingNum.setText(mSeletDatas.leaveAmount+"");

        mZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b_zan){
                    Toast.makeText(ShengHuoMassage.this, "已取消点赞", Toast.LENGTH_SHORT).show();
                    mZan.setImageResource(R.drawable.dianzan);
                    b_zan = false;
                    int t = Integer.valueOf(mZanNum.getText().toString());
                    t--;
                    mZanNum.setText(t+"");
                }else {
                    Toast.makeText(ShengHuoMassage.this, "点赞成功", Toast.LENGTH_SHORT).show();
                    mZan.setImageResource(R.drawable.dianzan1);
                    b_zan = true;
                    int t = Integer.valueOf(mZanNum.getText().toString());
                    t++;
                    mZanNum.setText(t+"");

                }
            }
        });



    }





}
