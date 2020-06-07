package com.coolweather.xuexin3;

import com.coolweather.xuexin3.f3.ItemF3;
import com.coolweather.xuexin3.pingLunAdapter.PingLun;
import com.coolweather.xuexin3.resultData.BasicData;
import com.coolweather.xuexin3.resultData.SeletDatas;

import java.util.ArrayList;
import java.util.List;

public class MyData {

    //登录状态
    public static boolean isLogin = false;

    //登录返回的信息e
    public static BasicData sBasicData;



    //F1选择的文章信息类型
    public static List<SeletDatas> listSlect = new ArrayList<>();

    public static List<PingLun> listPing = new ArrayList<>();

    public static List<ItemF3> listF3 = new ArrayList<>();

    static {
        listF3.add(new ItemF3(String.valueOf(R.drawable.img_f3_zz1), "比赛提醒", "2020.5.10", "比赛提醒：..."));
        listF3.add(new ItemF3(String.valueOf(R.drawable.img_f3_zz2), "行程助手", "2020.5.10", "行程助手：..."));
        listF3.add(new ItemF3(String.valueOf(R.drawable.img_f3_zz3), "代办事项", "2020.5.10", "代办事项：..."));
        listF3.add(new ItemF3(String.valueOf(R.drawable.img_f3_zz4), "考试提醒", "2020.5.10", "考试提醒：..."));
    }



}
