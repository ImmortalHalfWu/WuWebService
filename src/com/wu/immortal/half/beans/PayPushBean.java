package com.wu.immortal.half.beans;

/**
 * Do : 用户支付成功的回执bean
 * Created : immortalHalfWu
 * Time : 2018/9/27  10:48
 */
public class PayPushBean {

    /**
     * client_id : afcd2eb5b88fbdbd90
     * id : E20180918101734022600020
     * kdt_id : 41461397
     * kdt_name : ????????¤??????????°????
     * mode : 1
     * msg : %7B%22order_promotion%22:%7B%22adjust_fee%22:%220.00%22%7D,%22qr_info%22:%7B%22qr_id%22:8286057,%22qr_pay_id%22:23005982,%22qr_name%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22%7D,%22refund_order%22:[],%22full_order_info%22:%7B%22address_info%22:%7B%22self_fetch_info%22:%22%22,%22delivery_address%22:%22%22,%22delivery_postal_code%22:%22%22,%22receiver_name%22:%22%22,%22delivery_province%22:%22%22,%22delivery_city%22:%22%22,%22delivery_district%22:%22%22,%22address_extra%22:%22%7B%7D%22,%22receiver_tel%22:%22%22%7D,%22remark_info%22:%7B%22buyer_message%22:%22%22%7D,%22pay_info%22:%7B%22outer_transactions%22:[%224200000185201809187018379596%22],%22post_fee%22:%220.00%22,%22total_fee%22:%220.01%22,%22payment%22:%220.01%22,%22transaction%22:[%22180918101736000050%22]%7D,%22buyer_info%22:%7B%22fans_type%22:9,%22buyer_id%22:290195682,%22fans_id%22:1950113670,%22fans_nickname%22:%22%22%7D,%22orders%22:[%7B%22outer_sku_id%22:%22%22,%22sku_unique_code%22:%22%22,%22goods_url%22:%22https://h5.youzan.com/v2/showcase/goods%3Falias=null%22,%22item_id%22:2147483647,%22outer_item_id%22:%22null%22,%22discount_price%22:%220.01%22,%22item_type%22:30,%22num%22:1,%22sku_id%22:0,%22sku_properties_name%22:%22%22,%22pic_path%22:%22https://img.yzcdn.cn/public_files/2016/12/29/33e6c838cefa614c5121c63c80f860e9.png%22,%22oid%22:%221472319870133616259%22,%22title%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22,%22buyer_messages%22:%22%22,%22is_present%22:false,%22pre_sale_type%22:%22null%22,%22points_price%22:%220%22,%22price%22:%220.01%22,%22total_fee%22:%220.01%22,%22alias%22:%22null%22,%22payment%22:%220.01%22,%22is_pre_sale%22:%22null%22%7D],%22source_info%22:%7B%22is_offline_order%22:false,%22book_key%22:%22null%22,%22source%22:%7B%22platform%22:%22wx%22,%22wx_entrance%22:%22direct_buy%22%7D%7D,%22order_info%22:%7B%22consign_time%22:%22%22,%22order_extra%22:%7B%22is_from_cart%22:%22false%22%7D,%22created%22:%222018-09-18%2010:17:34%22,%22status_str%22:%22%E5%B7%B2%E6%94%AF%E4%BB%98%22,%22expired_time%22:%222018-09-18%2010:47:34%22,%22success_time%22:%22%22,%22type%22:6,%22tid%22:%22E20180918101734022600020%22,%22confirm_time%22:%22%22,%22pay_time%22:%222018-09-18%2010:17:45%22,%22update_time%22:%222018-09-18%2010:17:46%22,%22pay_type_str%22:%22WEIXIN_DAIXIAO%22,%22is_retail_order%22:false,%22pay_type%22:10,%22team_type%22:1,%22refund_state%22:0,%22close_type%22:0,%22status%22:%22TRADE_PAID%22,%22express_type%22:9,%22order_tags%22:%7B%22is_payed%22:true,%22is_secured_transactions%22:true%7D%7D%7D%7D
     * msg_id : 3b0020c9-b9cc-4926-ad69-44b3b3b85dc6
     * sendCount : 0
     * sign : d22a01e17bf8007d787867fd2ac2dd94
     * status : PAID
     * test : false
     * type : trade_TradePaid
     * version : 1537237066
     */
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
