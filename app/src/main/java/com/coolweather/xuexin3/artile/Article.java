package com.coolweather.xuexin3.artile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.hot.HotF1;
import com.coolweather.xuexin3.resultData.SeletDatas;

import java.util.ArrayList;
import java.util.List;

public class Article extends AppCompatActivity {
    AdapterArticle mAdapterArticle;
    List<SeletDatas> mList;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int t = (int) msg.obj;
                    if(t == 1){
                        mList.clear();
                        mList.addAll(MyData.listSlect);
                        mAdapterArticle.notifyDataSetChanged();
                    }else {
                        Toast.makeText(Article.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
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
        initDatas();     //初始化数据
        initRecycleView();      //加载RecycleView
    }

    @Override
    protected void onStart() {
        super.onStart();
        initDatas();
    }


    private void init() {
        mList = new ArrayList<>();

    }
    private void initDatas() {           //初始化数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.setHandler(mHandler);
                MyUtils.get_6("research ");
            }
        }).start();
    }
    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_a1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapterArticle = new AdapterArticle(mList);


        mAdapterArticle.setOnitemClickLintener(new AdapterArticle.OnitemClick() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(Article.this,"点击了一下"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Article.this, HotF1.class);
                intent.putExtra("id", position);
                intent.putExtra("name", "干货");
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(mAdapterArticle);
    }



    //列表更新，由看不见变为可见是调用
    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
        mAdapterArticle.notifyDataSetChanged();        //更新说明
    }


}
