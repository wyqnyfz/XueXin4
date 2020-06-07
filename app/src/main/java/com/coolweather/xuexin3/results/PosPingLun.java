package com.coolweather.xuexin3.results;

public class PosPingLun {


    /**
     * content : 444444
     * date : 2020.02.02 12:12:12
     * id : 3
     * lwNickName : aaa
     * photo :
     * wid : 10
     * yid : 3
     */

    private String content;
    private String date;
    private int id;
    private String lwNickName;
    private String photo;
    private int wid;
    private int yid;

    public PosPingLun(String content, String date, int id, String lwNickName, String photo, int wid, int yid) {
        this.content = content;
        this.date = date;
        this.id = id;
        this.lwNickName = lwNickName;
        this.photo = photo;
        this.wid = wid;
        this.yid = yid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLwNickName() {
        return lwNickName;
    }

    public void setLwNickName(String lwNickName) {
        this.lwNickName = lwNickName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public int getYid() {
        return yid;
    }

    public void setYid(int yid) {
        this.yid = yid;
    }
}
