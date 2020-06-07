package com.coolweather.xuexin3.resultData;

public class SeletDatas {
    public int id;
    public int yid;
    public String title;
    public String category;
    public String content;
    public String date;
    public String photos;
    public int lx;
    public String nickName;
    public String signature;
    public String gender;
    public String photo;
    public int likeAmount;
    public int likeStatus;
    public int focusStatus;
    public int leaveAmount;

    public SeletDatas(int id, int yid, String title, String category, String content, String date, String photos, int lx, String nickName, String signature, String gender, String photo, int likeAmount, int likeStatus, int focusStatus, int leaveAmount) {
        this.id = id;
        this.yid = yid;
        this.title = title;
        this.category = category;
        this.content = content;
        this.date = date;
        this.photos = photos;
        this.lx = lx;
        this.nickName = nickName;
        this.signature = signature;
        this.gender = gender;
        this.photo = photo;
        this.likeAmount = likeAmount;
        this.likeStatus = likeStatus;
        this.focusStatus = focusStatus;
        this.leaveAmount = leaveAmount;
    }
}
