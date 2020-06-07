package com.coolweather.xuexin3.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.artile.Article;
import com.coolweather.xuexin3.f1.Item1Adapter;
import com.coolweather.xuexin3.f1.PagerAdapter;
import com.coolweather.xuexin3.hot.HotF1;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.coolweather.xuexin3.search.SearchResult;
import com.coolweather.xuexin3.time.TimeLine;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class Fragment1 extends Fragment {

    //list
    Item1Adapter listAdapter;
    List<SeletDatas> list;
    //刷新
    private RefreshLayout mRefreshLayout;

    //轮番图
    private Handler mHandler;
    private ViewPager mViewPager;
    private ViewPager mLooPager;
    private PagerAdapter mPagerAdapter;

    private static List<Integer> mList  = new ArrayList<>();



    static {
        mList.add(R.drawable.img_1);
        mList.add(R.drawable.img_2);
        mList.add(R.drawable.img_3);
        mList.add(R.drawable.img_4);
        mList.add(R.drawable.img_5);
    }


    @SuppressLint("HandlerLeak")
    Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    int t1 = (int) msg.obj;
                    if(t1 == 1){
                        initDatas();
                        listAdapter.notifyDataSetChanged();
                    }else if(t1==2){
                        Toast.makeText(mView.getContext(), "暂无信息！", Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };





    //系统
    private Fragment1ViewModel mViewModel;
    private View mView;
    private EditText mEt_search;

    public static Fragment1 newInstance() {
        return new Fragment1();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment1_fragment, container, false);
        init();
        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Fragment1ViewModel.class);
        // TODO: Use the ViewModel
    }



    private void init() {
        //轮番图
        mViewPager = mView.findViewById(R.id.vp_1_imgs);
        mEt_search = mView.findViewById(R.id.et_f1_search);

        initListener();
        initPager();
        //list
        list = new ArrayList<>();
        initRecycleView();      //加载RecycleView
        //刷新
        initShuaxin();
        //搜索
        mView.findViewById(R.id.img_f1_search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mView.getContext(), SearchResult.class);
                intent.putExtra("search", mEt_search.getText().toString());
                startActivity(intent);
            }
        });
        //赛程提醒
        mView.findViewById(R.id.ll_f1_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mView.getContext(), TimeLine.class));
            }
        });

        //考研干货
        mView.findViewById(R.id.ll_f1_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mView.getContext(), Article.class));
            }
        });



        //下面的推荐
        listAdapter.setOnitemClickLintener(new Item1Adapter.OnitemClick() {
            @Override
            public void onItemClick(int position) {
//                Toast.makeText(mView.getContext(),"点击了一下"+position,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mView.getContext(), HotF1.class);
                intent.putExtra("id", position);
                intent.putExtra("name", "热点");
                startActivity(intent);
            }
        });






    }

    /**
     * 轮播图
     */
    private void initPager() {
        //适配器+设置数据
        mPagerAdapter = new PagerAdapter(mList);
        mLooPager.setAdapter(mPagerAdapter);
        mLooPager.setCurrentItem(100*mList.size(), false);
    }

    @Override
    public void onStart() {
        super.onStart();
        upDatas(1);
        mHandler.post(mLooperTask);
    }
    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mLooperTask);
    }
    Runnable mLooperTask = new Runnable() {
        @Override
        public void run() {
            int t = mLooPager.getCurrentItem();
            mLooPager.setCurrentItem(++t, true);
            mHandler.postDelayed(this, 3000);
        }
    };
    private void initListener() {
        mLooPager = mView.findViewById(R.id.vp_1_imgs);
        mHandler = new Handler();
        //点击事件
        mLooPager.setOnTouchListener(new View.OnTouchListener() {
            int flage = 0 ;
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        flage = 0 ;
                        break ;
                    case MotionEvent.ACTION_MOVE:
                        flage = 1 ;
                        break ;
                    case  MotionEvent.ACTION_UP :
                        if (flage == 0) {
                            int item = mLooPager.getCurrentItem()%mList.size();
                            if (item == 0) {

                            } else if (item == 1) {

                            } else if (item == 2) {

                            }else if (item == 3) {


                            }else if (item == 4) {

                            }else{

                            }
                        }
                        break ;
                }
                return false;
            }
        });

    }


    /**
     * list
     */
    private void initDatas() {           //初始化数据
        List<SeletDatas> list_all = MyData.listSlect;
        //把数据放到list_all

        list.clear();
        list.addAll(list_all); //不能用等于号
    }
    private void initRecycleView() {
        RecyclerView recyclerView = mView.findViewById(R.id.recycler_view_f1);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new Item1Adapter(list);
        recyclerView.setAdapter(listAdapter);
    }

    /**
     * 刷新
     */
    private void initShuaxin() {


        //初始化
        mRefreshLayout = mView.findViewById(R.id.refreshLayout_f1);

        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                upDatas(1);
                refreshlayout.finishRefresh();
            }
        });
        //加载
        mRefreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
//                upDatas(0);
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
                MyUtils.setHandler(mHandler1);
                MyUtils.get_6("dailyLearning");
//                MyUtils.get_13(t);
            }
        }).start();
    }




}
