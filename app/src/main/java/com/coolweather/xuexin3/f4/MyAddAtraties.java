package com.coolweather.xuexin3.f4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyAddAtraties extends AppCompatActivity {

    private String TAG = "MyAddAtraties";

    private TextView mSend;
    private EditText mTitle;
    private EditText mContent;
    private ImageView mImg1;
    private ImageView mImg2;
    private ImageView mImg3;

    private List<String> mUrl = new ArrayList<>();



    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int t1 = (int) msg.obj;
                    if(t1==1){
                        Toast.makeText(MyAddAtraties.this, "发布成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }else {
                        Toast.makeText(MyAddAtraties.this, "发布失败！", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_add_atraties);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        init();
    }

    private void init() {
        for (int i = 0; i < 10; i++) {
            mUrl.add("");
        }
        mSend = findViewById(R.id.tv_aa_send);
        mTitle = findViewById(R.id.et_aa_title);
        mContent = findViewById(R.id.et_aa_content);
        mImg1 = findViewById(R.id.iv_aa_img1);
        mImg2 = findViewById(R.id.iv_aa_img2);
        mImg3 = findViewById(R.id.iv_aa_img3);


        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = mTitle.getText().toString();
                String s2 = mContent.getText().toString();
                String s3 = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format( new Date(System.currentTimeMillis()));
                mUrl.set(0, s1);
                mUrl.set(1, s2);
                mUrl.set(2, s3);
                Log.d(TAG, s1+s2+s3);
                MyUtils.mImgUrl = mUrl;
                MyUtils.setHandler(mHandler);
                MyUtils.post_12();
            }
        });

        mImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhotoAlbum(1);
            }
        });
        mImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhotoAlbum(2);
            }
        });
        mImg3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goPhotoAlbum(3);
            }
        });
    }


    //激活相册操作
    private void goPhotoAlbum(int t) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, t);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String photoPath;
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String s = GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            mUrl.set(3, s);
            Log.d(TAG, "img=========="+s);
            Glide.with(MyAddAtraties.this).load(s).into(mImg1);
        } else if (requestCode == 2 && resultCode == RESULT_OK) {
            String s = GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            mUrl.set(4, s);
            Log.d(TAG, "img=========="+s);
            Glide.with(MyAddAtraties.this).load(s).into(mImg2);
        }else if (requestCode == 3 && resultCode == RESULT_OK) {
            String s = GetPhotoFromPhotoAlbum.getRealPathFromUri(this, data.getData());
            mUrl.set(5, s);
            Log.d(TAG, "img=========="+s);
            Glide.with(MyAddAtraties.this).load(s).into(mImg3);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }








}

















