package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/4/20.
 */

public class AboutUsResult {


    /**
     * code : 0
     * msg : SUCCESS
     * data : {"about_info":"﻿[\r\n\t\"Company Introduction\",\r\n\r\n\t\"Company Introduction Shenzhen LTIME In-Vehicle Entertainment System Company Limited is a hi-tech enterprise, controlled and established by Hong Kong LEC International Company Limited jointly with its outstanding domestic and overseas partners. Over years, we have been focusing on R&D and innovation, and providing reliable onboard entertainment system and communication solution to our customers all over the world. Now, LTIME has already developed many patent products with independent intellectual property rights such as Wireless Car Entertainment System(RoadPAD), Wireless Bus Entertainment System(Buspad), Bus Cloud Entertainment System(BCES), Bus Video System(BVS) and Onboard Internet Router etc. Most of them are very popular in Europe, North & South America, South-east Asia, Middle-east, Africa, Oceania and domestic market. LTIME also becomes qualified suppliers of VOLVO,SCANIA, MAN, IRIZAR, YUTONG, ZHONG TONG  and AK buses. Our products are widely installed in passenger buses, tourist buses, mini buses, business cars, cruise lines and high-speed trains which greatly enhances passengers satisfaction and travel quality. Company Vision Our goal is Either be the first, or be the best!, and we are committed to provide a better onboard entertainment and communication system solution for our global users!\",\r\n\r\n\t\"Contact information\",\r\n\r\n\t\"Company tel: 0086-755-2881 2090\",\r\n\r\n\t\"Company fax: 0086-755-2881 2089\",\r\n\r\n\t\"Company mail: ltime@ltime-cn.com\",\r\n\r\n\t\"Company website:www.ltime-cn.com\",\r\n\r\n\t\"Company address:4/F, Building 1, Nangang 1st Industrial Park, No. 1029, Songbai Road, Xili, Nanshan District, Shenzhen, China.\"\r\n]","about_img":"http://ltimeadmin.com/Uploads/about/dizhi.png"}
     */

    private String code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * about_info : ﻿[
         "Company Introduction",

         "Company Introduction Shenzhen LTIME In-Vehicle Entertainment System Company Limited is a hi-tech enterprise, controlled and established by Hong Kong LEC International Company Limited jointly with its outstanding domestic and overseas partners. Over years, we have been focusing on R&D and innovation, and providing reliable onboard entertainment system and communication solution to our customers all over the world. Now, LTIME has already developed many patent products with independent intellectual property rights such as Wireless Car Entertainment System(RoadPAD), Wireless Bus Entertainment System(Buspad), Bus Cloud Entertainment System(BCES), Bus Video System(BVS) and Onboard Internet Router etc. Most of them are very popular in Europe, North & South America, South-east Asia, Middle-east, Africa, Oceania and domestic market. LTIME also becomes qualified suppliers of VOLVO,SCANIA, MAN, IRIZAR, YUTONG, ZHONG TONG  and AK buses. Our products are widely installed in passenger buses, tourist buses, mini buses, business cars, cruise lines and high-speed trains which greatly enhances passengers satisfaction and travel quality. Company Vision Our goal is Either be the first, or be the best!, and we are committed to provide a better onboard entertainment and communication system solution for our global users!",

         "Contact information",

         "Company tel: 0086-755-2881 2090",

         "Company fax: 0086-755-2881 2089",

         "Company mail: ltime@ltime-cn.com",

         "Company website:www.ltime-cn.com",

         "Company address:4/F, Building 1, Nangang 1st Industrial Park, No. 1029, Songbai Road, Xili, Nanshan District, Shenzhen, China."
         ]
         * about_img : http://ltimeadmin.com/Uploads/about/dizhi.png
         */

        private String about_info;
        private String about_img;

        public String getAbout_info() {
            return about_info;
        }

        public void setAbout_info(String about_info) {
            this.about_info = about_info;
        }

        public String getAbout_img() {
            return about_img;
        }

        public void setAbout_img(String about_img) {
            this.about_img = about_img;
        }
    }
}
