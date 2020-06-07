package com.coolweather.xuexin3.results;

public class PosShouCang {

    /**
     * date : string
     * wid : 1
     * yid : 4
     */

    private String date;
    private int wid;
    private int yid;

    public PosShouCang(String date, int wid, int yid) {
        this.date = date;
        this.wid = wid;
        this.yid = yid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
