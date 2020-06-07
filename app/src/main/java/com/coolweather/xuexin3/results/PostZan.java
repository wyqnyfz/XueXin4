package com.coolweather.xuexin3.results;

public class PostZan {

    /**
     * lx : 1
     * wid : 1
     * yid : 1
     */

    private int lx;
    private int wid;
    private int yid;

    public PostZan(int lx, int wid, int yid) {
        this.lx = lx;
        this.wid = wid;
        this.yid = yid;
    }

    public int getLx() {
        return lx;
    }

    public void setLx(int lx) {
        this.lx = lx;
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
