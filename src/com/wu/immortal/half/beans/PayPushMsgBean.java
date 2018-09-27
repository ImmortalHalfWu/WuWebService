package com.wu.immortal.half.beans;

import java.util.List;

public class PayPushMsgBean {

    /**
     * order_promotion : {"adjust_fee":"0.00"}
     * qr_info : {"qr_id":8352149,"qr_pay_id":23213076,"qr_name":"可重复使用。09211029"}
     * refund_order : []
     * full_order_info : {"address_info":{"self_fetch_info":"","delivery_address":"","delivery_postal_code":"","receiver_name":"","delivery_province":"","delivery_city":"","delivery_district":"","address_extra":"{}","receiver_tel":""},"remark_info":{"buyer_message":""},"pay_info":{"outer_transactions":["4200000166201809251039556211"],"post_fee":"0.00","total_fee":"0.01","payment":"0.01","transaction":["180925095940000017"]},"buyer_info":{"fans_type":9,"buyer_id":290195682,"fans_id":1950113670,"fans_nickname":""},"orders":[{"outer_sku_id":"","sku_unique_code":"","goods_url":"https://h5.youzan.com/v2/showcase/goods?alias=null","item_id":2147483647,"outer_item_id":"null","discount_price":"0.01","item_type":30,"num":1,"sku_id":0,"sku_properties_name":"","pic_path":"https://img.yzcdn.cn/public_files/2016/12/29/33e6c838cefa614c5121c63c80f860e9.png","oid":"1473616351109060473","title":"可重复使用。09211029","buyer_messages":"","is_present":false,"pre_sale_type":"null","points_price":"0","price":"0.01","total_fee":"0.01","alias":"null","payment":"0.01","is_pre_sale":"null"}],"source_info":{"is_offline_order":false,"book_key":"null","source":{"platform":"wx","wx_entrance":"direct_buy"}},"order_info":{"consign_time":"","order_extra":{"is_from_cart":"false"},"created":"2018-09-25 09:59:35","status_str":"已完成","expired_time":"2018-09-25 10:29:35","success_time":"2018-09-25 09:59:46","type":6,"tid":"E20180925095935022600019","confirm_time":"","pay_time":"2018-09-25 09:59:45","update_time":"2018-09-25 09:59:46","pay_type_str":"WEIXIN_DAIXIAO","is_retail_order":false,"pay_type":10,"team_type":1,"refund_state":0,"close_type":0,"status":"TRADE_SUCCESS","express_type":9,"order_tags":{"is_payed":true,"is_secured_transactions":true}}}
     */

    private QrInfoBean qr_info;

    public QrInfoBean getQr_info() {
        return qr_info;
    }

    public void setQr_info(QrInfoBean qr_info) {
        this.qr_info = qr_info;
    }

    public static class QrInfoBean {
        /**
         * qr_id : 8352149
         * qr_pay_id : 23213076
         * qr_name : 可重复使用。09211029
         */

        private int qr_id;
        private int qr_pay_id;
        private String qr_name;

        public int getQr_id() {
            return qr_id;
        }

        public void setQr_id(int qr_id) {
            this.qr_id = qr_id;
        }

        public int getQr_pay_id() {
            return qr_pay_id;
        }

        public void setQr_pay_id(int qr_pay_id) {
            this.qr_pay_id = qr_pay_id;
        }

        public String getQr_name() {
            return qr_name;
        }

        public void setQr_name(String qr_name) {
            this.qr_name = qr_name;
        }
    }

}
