package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/3/27.
 */

public class VideoRequest {

    String category;
    String time;
    String page;
    String pageshow;

    public VideoRequest(String category, String page, String pageshow, String time) {
        this.category = category;
        this.page = page;
        this.pageshow = pageshow;
        this.time = time;

    }

    public VideoRequest(String category, String time) {
        this.category = category;
        this.time = time;
    }



    public VideoRequest(String time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageshow() {
        return pageshow;
    }

    public void setPageshow(String pageshow) {
        this.pageshow = pageshow;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "data{" +
                "category=" + category +
                ", time=" + time +
                ", page=" + page +
                ", pageshow=" + pageshow +
                '}';
    }
}
