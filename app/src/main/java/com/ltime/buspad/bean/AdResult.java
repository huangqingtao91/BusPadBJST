package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/4/20.
 */

public class AdResult {


    /**
     * code : 0
     * msg : SUCCESS
     * data : http://192.168.180.1/Uploads/ad/3.mp3
     */

    private String code;
    private String msg;
    private String data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
