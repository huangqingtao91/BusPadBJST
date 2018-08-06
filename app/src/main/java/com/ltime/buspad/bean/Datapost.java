package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/4/20.
 */

public class Datapost {
    String action;
    VideoRequest data;
    String sign;

    public Datapost(String action, VideoRequest data, String sign) {
        this.action = action;
        this.data = data;
        this.sign = sign;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public VideoRequest getData2() {
        return data;
    }

    public void setData2(VideoRequest data) {
        this.data = data;
    }
}
