package com.coolweather.xuexin3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.coolweather.xuexin3.MyData;
import com.coolweather.xuexin3.R;
import com.coolweather.xuexin3.f4.MyCenter;
import com.coolweather.xuexin3.f4menu.Menu7F4;
import com.makeramen.roundedimageview.RoundedImageView;

public class Fragment4 extends Fragment {

    private Button mLogin;
    private TextView mName;


    //系统
    private Fragment4ViewModel mViewModel;
    private View mView;
    private View mView1;
    private RoundedImageView mRoundedImageView;

    public static Fragment4 newInstance() {
        return new Fragment4();
    }
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment4_fragment, container, false);
        init();
        return mView;
    }
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(Fragment4ViewModel.class);
        // TODO: Use the ViewModel
    }

    private void init() {

        mName = mView.findViewById(R.id.tv_f4_name);
        mRoundedImageView = mView.findViewById(R.id.img_f4_img);
        initData();


        //头像
        mRoundedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent(mView.getContext(), MyCenter.class);
                    startActivity(intent);
            }
        });

        //menu7
        mView.findViewById(R.id.tv_f4_m7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mView.getContext(), Menu7F4.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {
        if(MyData.sBasicData.nickName!=null){
            mName.setText(MyData.sBasicData.nickName);
        }
        if(!"".equals(MyData.sBasicData.photo)){
            Glide.with(mView.getContext()).load(MyData.sBasicData.photo).into(mRoundedImageView);
        }
    }

}
