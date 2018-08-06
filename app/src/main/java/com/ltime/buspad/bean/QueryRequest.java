package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/4/20.
 */

public class QueryRequest {
    String action;
    PreCreateResult.DataBean data;

    public QueryRequest(String action, PreCreateResult.DataBean data) {
        this.action = action;
        this.data = data;

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public PreCreateResult.DataBean getData2() {
        return data;
    }

    public void setData2(PreCreateResult.DataBean data) {
        this.data = data;
    }
}
