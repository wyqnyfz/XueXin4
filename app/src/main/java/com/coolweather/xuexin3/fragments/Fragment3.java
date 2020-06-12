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
import com.coolweather.xuexin3.f3.Item3Adapter;
import com.coolweather.xuexin3.f3.ItemF3;
import com.coolweather.xuexin3.search.Search;
import com.coolweather.xuexin3.socket.ChatActivity;
import com.coolweather.xuexin3.socket.FriendData;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class Fragment3 extends Fragment {

    Item3Adapter mListAdapter;
    List<ItemF3> mList;
    //刷新
    private RefreshLayout mRefreshLayout;


    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 2:
                    int t2 = (int) msg.obj;
                    if(t2 == 1){
                        Log.d("--", "获取信息成功");
                        for(FriendData e : MyData.sBasicData.guanZhuList){
                            Log.d("------------", ""+e.getNickName());
                            mList.add(new ItemF3(e.getPhoto(), e.getNickName(), "", ""));
                        }
                        mListAdapter.notifyDataSetChanged();        //更新说明
                    }
                    break;

            }
        }
    };


    //系统
    private Fragment3ViewModel mViewModel;
    private View mView;
    public static Fragment3 newInstance() {
        return new Fragment3();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment3_fragment, container, false);
        init();
        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Fragment3ViewModel.class);
        // TODO: Use the ViewModel
    }


    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                MyUtils.post_24(mHandler);
            }
        }).start();
        mList = new ArrayList<>();
        initDatas();     //初始化数据
        initRecycleView();      //加载RecycleView

        //刷新
        initShuaxin();

        //搜索
        mView.findViewById(R.id.img_f3_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mView.getContext(), Search.class));
            }
        });

        mListAdapter.setOnitemClickLintener(new Item3Adapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {
                if(position > 3){
                    FriendData friendData = MyData.sBasicData.guanZhuList.get(position-4);

                    Intent intent = new Intent(getContext(), ChatActivity.class);
                    intent.putExtra("friend_id", friendData.getId());
                    intent.putExtra("friend_name", friendData.getNickName());
                    intent.putExtra("friend_img", friendData.getPhoto());
                    startActivity(intent);
                }
            }
        });

    }


    /**
     * list家族
     */
    private void initDatas() {           //初始化数据
        mList.clear();
        mList.addAll(MyData.listF3); //不能用等于号

    }

    private void initRecycleView() {
        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_f3);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        mListAdapter = new Item3Adapter(mList);
        recyclerView.setAdapter(mListAdapter);
    }




    /**
     * 刷新
     */
    private void initShuaxin() {


        //初始化
        mRefreshLayout = mView.findViewById(R.id.refreshLayout_f3);

        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mList.clear();
//                initDatas();
//                mListAdapter.notifyDataSetChanged();
                refreshlayout.finishRefresh();
            }
        });
        //加载
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                mList.add(MyData.listF3.get(5));
//                mListAdapter.notifyDataSetChanged();
                refreshlayout.finishLoadmore();
            }
        });


    }
}
