package com.coolweather.xuexin3.time;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.resultData.SeletDatas;

import java.util.ArrayList;
import java.util.List;

public class TimeLine extends AppCompatActivity {

    AdapterTime mAdapterTime;
    List<SeletDatas> mSeletDatas;

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int t = (int) msg.obj;
                    if(t == 1){
                        mSeletDatas.clear();
                        mSeletDatas.addAll(MyData.listSlect);
                        mAdapterTime.notifyDataSetChanged();
                    }else {
                        Toast.makeText(TimeLine.this, "获取数据失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
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
        mSeletDatas = new ArrayList<>();

    }
    private void initDatas() {           //初始化数据

        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.setHandler(mHandler);
                MyUtils.get_6("competition");
            }
        }).start();
    }
    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_t1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapterTime = new AdapterTime(mSeletDatas);
        mAdapterTime.setOnitemClickLintener(new AdapterTime.OnitemClick() {
            @Override
            public void onItemClick(int position, View v) {
                //第二个参数是绑定的那个view
                PopupMenu popup = new PopupMenu(TimeLine.this, v);
                //获取菜单填充器
                popup.getMenuInflater().inflate(R.menu.bisai_menu, popup.getMenu());
                //绑定菜单项的点击事件
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(TimeLine.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        switch (item.getItemId()){
                            case R.id.menu_bisai_1:
                                Toast.makeText(TimeLine.this, "闹钟添加成功", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_bisai_2:
                                Toast.makeText(TimeLine.this, "暂时无法进入官网", Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.menu_bisai_3:
                                Intent intent = new Intent(TimeLine.this, TimeMassage.class);
                                intent.putExtra("id", position);
                                startActivity(intent);
                                break;

                        }
                        return true;
                    }
                });
                //显示(这一行代码不要忘记了)
                popup.show();
            }

//            public void onItemClick(int position) {
//                Toast.makeText(mView.getContext(),"点击了一下"+position,Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(TimeLine.this, TimeMassage.class);
//                intent.putExtra("id", position);
//                startActivity(intent);
//            }
        });
        recyclerView.setAdapter(mAdapterTime);
    }



    //列表更新，由看不见变为可见是调用
    @Override
    protected void onResume() {
        super.onResume();
        initDatas();
        mAdapterTime.notifyDataSetChanged();        //更新说明
    }

}
