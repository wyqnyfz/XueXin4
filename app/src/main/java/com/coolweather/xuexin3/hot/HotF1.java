package com.coolweather.xuexin3.hot;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.ImageGetterUtils;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.MyUtils;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.pingLunAdapter.AdapterPingLun;
import com.coolweather.xuexin3.pingLunAdapter.AdapterZhuiPing;
import com.coolweather.xuexin3.pingLunAdapter.PingLun;
import com.coolweather.xuexin3.resultData.SeletDatas;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class HotF1 extends AppCompatActivity {

    private static final String TAG = "HotF1";
    //list
    AdapterPingLun listAdapter;
    List<PingLun> list;

    private SeletDatas mSeletDatas;
    private TextView mContent;
    public  Boolean b_zan = false;
    public  Boolean b_guna = false;
    public  Boolean b_shou = false;
    private RoundedImageView mPhoto;
    private RoundedImageView mGuanzhu;
    private TextView mNeckname;
    private ImageView mGender;
    private TextView mSignature;
    private TextView mShoucang;
    private TextView mNameTv;
    private ImageView mZan;
    private ImageView mPing;
    private TextView mZanNum;
    private TextView mPingNum;

    private EditText mMassage;
    private Button mSend;

    private int mPosition=-1;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 1:
                    int t1 = (int) msg.obj;
                    if(t1==1){
                        mZan.setImageResource(R.drawable.dianzan1);
                        b_zan = true;
                        int t = Integer.valueOf(mZanNum.getText().toString());
                        t++;
                        mZanNum.setText(t+"");
                    }else {
                        Toast.makeText(HotF1.this, "点赞失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 2:
                    int t2 = (int) msg.obj;
                    if(t2==1){
                        mZan.setImageResource(R.drawable.dianzan);
                        b_zan = false;
                        int t = Integer.valueOf(mZanNum.getText().toString());
                        t--;
                        mZanNum.setText(t+"");
                    }else {
                        Toast.makeText(HotF1.this, "取消点赞失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 3:
                    int t3 = (int) msg.obj;
                    if(t3==1){
                        b_shou = true;

                    }else {
                        Toast.makeText(HotF1.this, "收藏失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 4:
                    int t4 = (int) msg.obj;
                    if(t4==1){
                        b_shou = false;
                        mShoucang.setText("收藏");
                    }else {
                        Toast.makeText(HotF1.this, "取消收藏失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 5:
                    int t5 = (int) msg.obj;
                    if(t5==1){
                        b_guna = true;
                        mGuanzhu.setImageResource(R.drawable.img_add_1);
                    }else {
                        Toast.makeText(HotF1.this, "关注失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 6:
                    int t6 = (int) msg.obj;
                    if(t6==1){
                        b_guna = false;
                        mGuanzhu.setImageResource(R.drawable.img_add);
                    }else {
                        Toast.makeText(HotF1.this, "取消关注失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 7:
                    int t7 = (int) msg.obj;
                    if(t7==1){
                       initDatas();
                       initRecycleView();
                    }else {
                        Toast.makeText(HotF1.this, "获取评论失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 8:
                    int t8 = (int) msg.obj;
                    if(t8==1){
                        Toast.makeText(HotF1.this, "评论成功", Toast.LENGTH_SHORT).show();
                        if(mSeletDatas!=null) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MyUtils.post_21(mHandler, mSeletDatas.id);
                                }
                            }).start();
                        }
                    }else {
                        Toast.makeText(HotF1.this, "评论失败", Toast.LENGTH_SHORT).show();
                    }
                    mPosition=-1;
                    break;
                case 9:
                    int t9 = (int) msg.obj;
                    if(t9==1){
                        Toast.makeText(HotF1.this, "回复成功", Toast.LENGTH_SHORT).show();
                        if(mSeletDatas!=null) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    MyUtils.post_21(mHandler, mSeletDatas.id);
                                }
                            }).start();
                        }
                    }else {
                        Toast.makeText(HotF1.this, "回复失败", Toast.LENGTH_SHORT).show();
                    }
                    mPosition=-1;
                    break;


            }
        }
    };
    private String mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_f1);
        //状态栏颜色
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Intent intent = getIntent();
        int id = intent.getIntExtra("id", 0);
        mName = intent.getStringExtra("name");
        mSeletDatas = MyData.listSlect.get(id);
        init();

    }


    private void init() {
        if(mSeletDatas!=null){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyUtils.post_21(mHandler,mSeletDatas.id);
                }
            }).start();

        }
        mNameTv = findViewById(R.id.tv_hf_name);
        mNameTv.setText(mName);

        mPhoto = findViewById(R.id.img_hf_photo);
        mGuanzhu = findViewById(R.id.img_hf_add);
        mNeckname = findViewById(R.id.tv_hf_neckname);
        mGender = findViewById(R.id.iv_hf_gender);

        mSignature = findViewById(R.id.tv_hf_signature);
        mContent = findViewById(R.id.tv_hf_content);
        mShoucang = findViewById(R.id.tv_hf_add);
        mZan = findViewById(R.id.iv_hf_dianzan);
        mPing = findViewById(R.id.img_hf_ping);
        mZanNum = findViewById(R.id.tv_hf_zan);
        mPingNum = findViewById(R.id.tv_hf_ping);
        mMassage = findViewById(R.id.et_hf_massage);
        mSend = findViewById(R.id.bt_hf_send);

        list=new ArrayList<>();

        Glide.with(this).load(mSeletDatas.photo).into(mPhoto);
        mNeckname.setText(mSeletDatas.nickName);
        if(mSeletDatas.gender!=null){
            if(mSeletDatas.gender.equals("男")){
               mGender.setImageResource(R.drawable.boy);
            }
        }

        if (mSeletDatas.signature == null) {
            mSignature.setText("暂无");
        }else {
            mSignature.setText(mSeletDatas.signature);
        }
        if(mSeletDatas.likeStatus==1){
            mZan.setImageResource(R.drawable.dianzan1);
            b_zan = true;
        }

        if(mSeletDatas.focusStatus==1){
            b_guna = true;
            mGuanzhu.setImageResource(R.drawable.img_add_1);
        }



        mZanNum.setText(mSeletDatas.likeAmount+"");
        mPingNum.setText(mSeletDatas.leaveAmount+"");

        mContent.setText(Html.fromHtml(mSeletDatas.content,new ImageGetterUtils.MyImageGetter(this,mContent),null));

        //点赞
        mZan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b_zan){
                    MyUtils.post_16(mHandler, mSeletDatas.id, MyData.sBasicData.id);
                }else {
                    MyUtils.post_15(mHandler, mSeletDatas.id, MyData.sBasicData.id);
                }
            }
        });

        mPing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMassage.setFocusable(true);
                mMassage.setFocusableInTouchMode(true);
                mMassage.requestFocus();
                InputMethodManager imm = (InputMethodManager) HotF1.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                mPosition=-1;
            }
        });
        //关注
        mGuanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b_guna){
                    MyUtils.post_20(mHandler, mSeletDatas.yid,MyData.sBasicData.id);
                }else {
                    MyUtils.post_19(mHandler, mSeletDatas.yid,MyData.sBasicData.id);
                }
            }
        });

        //收藏
        mShoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(b_shou){
//                    MyUtils.post_18(mHandler, mSeletDatas.id, MyData.sBasicData.id);
                }else {
//                    MyUtils.post_17(mHandler, mSeletDatas.id, MyData.sBasicData.id);
                }
            }
        });

        //评论
        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = mMassage.getText().toString();
                if(s!=""){
                    mMassage.setText("");
                    hintKeyBoard();
                    if(mPosition==-1){
                        MyUtils.post_22(mHandler,s,10,MyData.sBasicData.nickName, MyData.sBasicData.photo, mSeletDatas.id, MyData.sBasicData.id);
                    }else {
                        Log.d(TAG, "post  --  "+mPosition);
                        for(int i=0;i<MyData.listPing.size();i++){
                            Log.d(TAG, i+"---"+MyData.listPing.get(i).getLeave().getId());
                        }
                        MyUtils.post_23(mHandler, s, 0, mSeletDatas.nickName, MyData.listPing.get(mPosition).getLeave().getId(), MyData.sBasicData.nickName, MyData.sBasicData.photo, mSeletDatas.id, MyData.sBasicData.id, MyData.sBasicData.id);
                    }
                }
            }
        });

    }



    /**
     * list
     */
    private void initDatas() {           //初始化数据
        List<PingLun> list_all = MyData.listPing;
        //把数据放到list_all
        list.clear();
        list.addAll(list_all); //不能用等于号
    }
    private void initRecycleView() {
        RecyclerView recyclerView = findViewById(R.id.recycler_view_hf);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HotF1.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        listAdapter = new AdapterPingLun(list);
        recyclerView.setAdapter(listAdapter);
        //回复
        listAdapter.setOnitemClickLintener(new AdapterPingLun.OnitemClick() {
            @Override
            public void onItemClick(int position) {
                Log.d(TAG, "点击了====");
                mMassage.setFocusable(true);
                mMassage.setFocusableInTouchMode(true);
                mMassage.requestFocus();
                InputMethodManager imm = (InputMethodManager) HotF1.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                mPosition=position;
            }

            @Override
            public void onHuiFuClick(List<PingLun.RepliesBean> list1,View view,RecyclerView rc) {

                if(rc.getAdapter()!=null){
                    Log.d(TAG, "不是null");
                    AdapterZhuiPing la = new AdapterZhuiPing(new ArrayList<PingLun.RepliesBean>());
                    rc.setAdapter(la);
                    la.notifyDataSetChanged();
                    rc.setAdapter(null);
                }else {
                    Log.d(TAG, "是null");
                    LinearLayoutManager ll = new LinearLayoutManager(view.getContext());
                    rc.setLayoutManager(ll);
                    AdapterZhuiPing la = new AdapterZhuiPing(list1);
                    rc.setAdapter(la);
                    la.notifyDataSetChanged();
                }


            }
        });
    }

    /**
     * 关闭键盘
     */
    public void hintKeyBoard() {
        //拿到InputMethodManager
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //如果window上view获取焦点 && view不为空
        if (imm.isActive() && getCurrentFocus() != null) {
            //拿到view的token 不为空
            if (getCurrentFocus().getWindowToken() != null) {
                //表示软键盘窗口总是隐藏，除非开始时以SHOW_FORCED显示。
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }



}













