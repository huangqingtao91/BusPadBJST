package com.ltime.buspad.bean;

import java.util.List;

/**
 * Created by ltime on 2018/5/17.
 */

public class QueryResult {

    /**
     * code : 0
     * msg : 请求查询成功
     * data : {"sn":"7895252207019793","client_sn":"20180517161414432534","client_tsn":"20180517161414432534","trade_no":"299560006630201805176254336290","finish_time":"1526544884469","channel_finish_time":"1526544883000","status":"SUCCESS","order_status":"PAID","payway":"3","payway_name":"微信","sub_payway":"2","payer_uid":"okSzXt_ozJ25pxmV8lNGlluuXzuc","payer_login":"","total_amount":"1","net_amount":"1","subject":"测试交易","operator":"liuxd","payment_list":[{"type":"WALLET_WEIXIN","amount_total":"1"}]}
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
         * sn : 7895252207019793
         * client_sn : 20180517161414432534
         * client_tsn : 20180517161414432534
         * trade_no : 299560006630201805176254336290
         * finish_time : 1526544884469
         * channel_finish_time : 1526544883000
         * status : SUCCESS
         * order_status : PAID
         * payway : 3
         * payway_name : 微信
         * sub_payway : 2
         * payer_uid : okSzXt_ozJ25pxmV8lNGlluuXzuc
         * payer_login :
         * total_amount : 1
         * net_amount : 1
         * subject : 测试交易
         * operator : liuxd
         * payment_list : [{"type":"WALLET_WEIXIN","amount_total":"1"}]
         */

        private String sn;
        private String client_sn;
        private String client_tsn;
        private String trade_no;
        private String finish_time;
        private String channel_finish_time;
        private String status;
        private String order_status;
        private String payway;
        private String payway_name;
        private String sub_payway;
        private String payer_uid;
        private String payer_login;
        private String total_amount;
        private String net_amount;
        private String subject;
        private String operator;
        private List<PaymentListBean> payment_list;

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

        public String getClient_tsn() {
            return client_tsn;
        }

        public void setClient_tsn(String client_tsn) {
            this.client_tsn = client_tsn;
        }

        public String getTrade_no() {
            return trade_no;
        }

        public void setTrade_no(String trade_no) {
            this.trade_no = trade_no;
        }

        public String getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(String finish_time) {
            this.finish_time = finish_time;
        }

        public String getChannel_finish_time() {
            return channel_finish_time;
        }

        public void setChannel_finish_time(String channel_finish_time) {
            this.channel_finish_time = channel_finish_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPayway() {
            return payway;
        }

        public void setPayway(String payway) {
            this.payway = payway;
        }

        public String getPayway_name() {
            return payway_name;
        }

        public void setPayway_name(String payway_name) {
            this.payway_name = payway_name;
        }

        public String getSub_payway() {
            return sub_payway;
        }

        public void setSub_payway(String sub_payway) {
            this.sub_payway = sub_payway;
        }

        public String getPayer_uid() {
            return payer_uid;
        }

        public void setPayer_uid(String payer_uid) {
            this.payer_uid = payer_uid;
        }

        public String getPayer_login() {
            return payer_login;
        }

        public void setPayer_login(String payer_login) {
            this.payer_login = payer_login;
        }

        public String getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(String total_amount) {
            this.total_amount = total_amount;
        }

        public String getNet_amount() {
            return net_amount;
        }

        public void setNet_amount(String net_amount) {
            this.net_amount = net_amount;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public List<PaymentListBean> getPayment_list() {
            return payment_list;
        }

        public void setPayment_list(List<PaymentListBean> payment_list) {
            this.payment_list = payment_list;
        }

        public static class PaymentListBean {
            /**
             * type : WALLET_WEIXIN
             * amount_total : 1
             */

            private String type;
            private String amount_total;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAmount_total() {
                return amount_total;
            }

            public void setAmount_total(String amount_total) {
                this.amount_total = amount_total;
            }
        }
    }
}
