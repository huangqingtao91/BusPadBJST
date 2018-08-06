package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/5/16.
 */

public class DataTest {
    String action;
    String total_amount;

    public DataTest(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }
}
