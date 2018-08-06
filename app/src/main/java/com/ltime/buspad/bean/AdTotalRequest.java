package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/3/27.
 */

public class AdTotalRequest {

    String advertisement_id;

    public AdTotalRequest(String advertisement_id) {
        this.advertisement_id = advertisement_id;

    }
    public String getCategory() {
        return advertisement_id;
    }
    public void setCategory(String advertisement_id) {
        this.advertisement_id = advertisement_id;
    }

}
