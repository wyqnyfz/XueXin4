package com.coolweather.xuexin3.results;

public class PosGuanZhu {

    /**
     * yid : 4
     * fid : 3
     * date : 2020-02-02 12:12:12
     */

    private int yid;
    private int fid;
    private String date;

    public PosGuanZhu(int yid, int fid, String date) {
        this.yid = yid;
        this.fid = fid;
        this.date = date;
    }

    public int getYid() {
        return yid;
    }

    public void setYid(int yid) {
        this.yid = yid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
