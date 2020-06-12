package com.coolweather.xuexin3.f4;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class MyCenter extends AppCompatActivity {

    ItemC1Aapter mItemC1Aapter;
    List<SeletDatas> mList;
    private TextView mName;
    private TextView mSchool;
    private TextView mFensi;
    private TextView mGuanzhu;
    private RoundedImageView mRoundedImageView;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int t1 = (int) msg.obj;
                    if(t1 == 1){
                        initData();
                        mItemC1Aapter.notifyDataSetChanged();
                    }else if(t1==2){
                        Toast.makeText(MyCenter.this, "暂无信息！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    int t2 = (int) msg.obj;
                    if(t2 == 1){

                    }
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_center);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        //隐藏bar
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.hide();
        }
        init();
    }

    private void init() {

        mName = findViewById(R.id.tv_c1_name);
        mSchool = findViewById(R.id.tv_c1_school);
        mFensi = findViewById(R.id.tv_c1_fensi);
        mGuanzhu = findViewById(R.id.tv_c1_guanzhu);
        mRoundedImageView = findViewById(R.id.img_c5_img);

        mFensi.setText(MyData.sBasicData.fenSiList.size()+"");
        mGuanzhu.setText(MyData.sBasicData.guanZhuList.size()+"");



        mList = new ArrayList<>();
        initData();     //初始化数据
        initRecycleView();      //加载RecycleView


        //个人信息
        findViewById(R.id.ll_c1_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCenter.this,MyBasicData.class);
                startActivity(intent);
            }
        });

        //添加动态
        findViewById(R.id.iv_c1_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MyCenter.this,MyAddAtraties.class));
            }
        });

        //下面的推荐
        mItemC1Aapter.setOnitemClickLintener(new ItemC1Aapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(mView.getContext(),"点击了一下"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MyCenter.this, MyCenterMassage.class);
                intent.putExtra("id", position);
                startActivity(intent);
            }
        });

    }

    /**
     * list家族
     */
    private void initData() {
        if(MyData.sBasicData.nickName!=null){
            mName.setText(MyData.sBasicData.nickName);
        }
        if(MyData.sBasicData.school!=null){
            mSchool.setText(MyData.sBasicData.school);
        }
        if(!"".equals(MyData.sBasicData.photo)){
            Glide.with(MyCenter.this).load(MyData.sBasicData.photo).into(mRoundedImageView);
        }
        List<SeletDatas> list_all = MyData.listSlect;
        mList.clear();
        mList.addAll(list_all);
    }
    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_center);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        mItemC1Aapter = new ItemC1Aapter(mList);
        recyclerView.setAdapter(mItemC1Aapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        upDatas(1);
        mItemC1Aapter.notifyDataSetChanged();
    }


    /**
     * 数据
     * @param t
     */
    private void upDatas(int t) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.setHandler(mHandler);
                MyUtils.get_14();
//                MyUtils.get_13(t);
            }
        }).start();
    }







}
