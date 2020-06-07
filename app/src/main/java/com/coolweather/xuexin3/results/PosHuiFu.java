package com.coolweather.xuexin3.results;

public class PosHuiFu {

    /**
     * content : 6666666
     * date : 2020.3.3
     * id : 10
     * lfNickName : aaaaaaaaa
     * lid : 10
     * lrNickName : bbbbbbbbb
     * photo : string
     * wid : 10
     * yfid : 10
     * yid : 3
     */

    private String content;
    private String date;
    private int id;
    private String lfNickName;
    private int lid;
    private String lrNickName;
    private String photo;
    private int wid;
    private int yfid;
    private int yid;

    public PosHuiFu(String content, String date, int id, String lfNickName, int lid, String lrNickName, String photo, int wid, int yfid, int yid) {
        this.content = content;
        this.date = date;
        this.id = id;
        this.lfNickName = lfNickName;
        this.lid = lid;
        this.lrNickName = lrNickName;
        this.photo = photo;
        this.wid = wid;
        this.yfid = yfid;
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

    public String getLfNickName() {
        return lfNickName;
    }

    public void setLfNickName(String lfNickName) {
        this.lfNickName = lfNickName;
    }

    public int getLid() {
        return lid;
    }

    public void setLid(int lid) {
        this.lid = lid;
    }

    public String getLrNickName() {
        return lrNickName;
    }

    public void setLrNickName(String lrNickName) {
        this.lrNickName = lrNickName;
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

    public int getYfid() {
        return yfid;
    }

    public void setYfid(int yfid) {
        this.yfid = yfid;
    }

    public int getYid() {
        return yid;
    }

    public void setYid(int yid) {
        this.yid = yid;
    }
}
