package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/4/20.
 */

public class PreCreateRequest {
    String action;
    ChoosePayWay data;

    public PreCreateRequest(String action, ChoosePayWay data) {
        this.action = action;
        this.data = data;

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public ChoosePayWay getData2() {
        return data;
    }

    public void setData2(ChoosePayWay data) {
        this.data = data;
    }
}
