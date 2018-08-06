package com.ltime.buspad.bean;

/**
 * Created by YF001 on 2018/5/16.
 */

public class PreCreateResult {

    /**
     * code : 0
     * msg : 请求预下单成功
     * data : {"terminal_sn":"100006130003559859","sn":"7895252263584880","client_sn":"20180516150234247538","qr_code_image_url":"https://api.shouqianba.com/upay/qrcode?content=weixin%3A%2F%2Fwxpay%2Fbizpayurl%3Fpr%3Dzbok5tX"}
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
         * terminal_sn : 100006130003559859
         * sn : 7895252263584880
         * client_sn : 20180516150234247538
         * qr_code_image_url : https://api.shouqianba.com/upay/qrcode?content=weixin%3A%2F%2Fwxpay%2Fbizpayurl%3Fpr%3Dzbok5tX
         */

        private String terminal_sn;
        private String sn;
        private String client_sn;
        private String qr_code_image_url;
        private String subject;

        public DataBean(String terminal_sn, String sn, String client_sn) {
            this.terminal_sn = terminal_sn;
            this.sn = sn;
            this.client_sn = client_sn;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getTerminal_sn() {
            return terminal_sn;
        }

        public void setTerminal_sn(String terminal_sn) {
            this.terminal_sn = terminal_sn;
        }

        public String getSn() {
            return sn;
        }

        public void setSn(String sn) {
            this.sn = sn;
        }

        public String getClient_sn() {
            return client_sn;
        }

        public void setClient_sn(String client_sn) {
            this.client_sn = client_sn;
        }

        public String getQr_code_image_url() {
            return qr_code_image_url;
        }

        public void setQr_code_image_url(String qr_code_image_url) {
            this.qr_code_image_url = qr_code_image_url;
        }

        @Override
        public String toString() {
            return "data{" +
                    "terminal_sn=" + terminal_sn +
                    ", sn=" + sn +
                    ", client_sn=" + client_sn +
                    '}';
        }
    }
}
