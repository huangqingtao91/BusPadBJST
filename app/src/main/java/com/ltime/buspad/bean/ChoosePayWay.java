package com.ltime.buspad.bean;

/**
 * Created by ltime on 2018/5/18.
 */

public class ChoosePayWay {
    String total_amount;
    String payway;
    String operator;

    public ChoosePayWay(String total_amount, String payway, String operator) {
        this.total_amount = total_amount;
        this.payway = payway;
        this.operator = operator;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
