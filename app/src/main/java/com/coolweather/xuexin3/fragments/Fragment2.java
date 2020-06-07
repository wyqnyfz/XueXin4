package com.coolweather.xuexin3.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.f2.Item2Adapter;
import com.coolweather.xuexin3.hot.HotF1;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.coolweather.xuexin3.search.Search;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment2 extends Fragment {
    Item2Adapter mItem2Adapter;
    List<SeletDatas> mList;

    //刷新
    private RefreshLayout mRefreshLayout;


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int t1 = (int) msg.obj;
                    if(t1 == 1){
                        initDatas();
                        mItem2Adapter.notifyDataSetChanged();
                    }else if(t1==2){
                        Toast.makeText(mView.getContext(), "暂无信息！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };


    //系统
    private Fragment2ViewModel mViewModel;
    private View mView;
    public static Fragment2 newInstance() {
        return new Fragment2();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment2_fragment, container, false);
        init();
        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Fragment2ViewModel.class);
        // TODO: Use the ViewModel
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d("---stayjsbc", "--------");
        upDatas(1);
        initDatas();
        mItem2Adapter.notifyDataSetChanged();        //更新说明
    }

    private void init() {
        mList = new ArrayList<>();
        initDatas();     //初始化数据
        initRecycleView();      //加载RecycleView
        //刷新
        initShuaxin();
        //搜索
        mView.findViewById(R.id.img_f2_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mView.getContext(), Search.class);
                startActivity(intent);
            }
        });

        //点击动态
        mItem2Adapter.setOnitemClickLintener(new Item2Adapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(mView.getContext(),"点击了一下"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mView.getContext(), HotF1.class);
                intent.putExtra("id", position);
                intent.putExtra("name", "生活");
                startActivity(intent);
            }
        });

    }


    /**
     * list家族
     */
    private void initDatas() {           //初始化数据

        List<SeletDatas> list_all = MyData.listSlect;
        //把数据放到list_all
        mList.clear();
        mList.addAll(list_all); //不能用等于号
    }
    private void initRecycleView() {
        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_f2);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mItem2Adapter = new Item2Adapter(mList);
        recyclerView.setAdapter(mItem2Adapter);
    }



    /**
     * 刷新
     */
    private void initShuaxin() {


        //初始化
        mRefreshLayout = mView.findViewById(R.id.refreshLayout_f2);

        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
                initDatas();
                mItem2Adapter.notifyDataSetChanged();
                refreshlayout.finishRefresh();
            }
        });
        //加载
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                mList.add(MyData.listSlect.get(1));
//                mItem2Adapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });


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
                MyUtils.get_13();
            }
        }).start();
    }




}
