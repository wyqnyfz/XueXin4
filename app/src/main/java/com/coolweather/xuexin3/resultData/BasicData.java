package com.coolweather.xuexin3.resultData;

import com.coolweather.xuexin3.socket.FriendData;

import java.util.ArrayList;
import java.util.List;

public class BasicData {


    //id
    public int id;
    //昵称
    public String nickName;
    //签名
    public String signature;
    //性别
    public String gender;
    //手机号
    public String phone;
    //密码
    public String password;
    //学校
    public String school;
    //专业
    public String major;
    //照片
    public String photo;
    //粉丝信息
    public List<FriendData> fenSiList = new ArrayList<>();
    //关注信息
    public List<FriendData> guanZhuList = new ArrayList<>();

    public BasicData(int id, String nickName, String signature, String gender, String phone, String password, String school, String major, String photo) {
        this.id = id;
        this.nickName = nickName;
        this.signature = signature;
        this.gender = gender;
        this.phone = phone;
        this.password = password;
        this.school = school;
        this.major = major;
        this.photo = photo;
    }
}
