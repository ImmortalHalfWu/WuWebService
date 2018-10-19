package com.wu.immortal.half.servlet;

import com.wu.immortal.half.beans.PayPushBean;
import com.wu.immortal.half.beans.PayPushMsgBean;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.*;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.utils.DataUtil;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.VIPWithAuthorityUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PayPushServlet")
public class PayPushServlet extends BaseServletServlet {
    @Override
    protected ResultBean.ResultInfo post(UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        /*
        收到推送后的处理逻辑
        1, 查找支付二维码数据库，通过qrid找出对应的PayQRcodeBean
        2, 支付二维码对应的userId，查出用户vip信息
        3，判断Vip信息
            if（当前会员 > 购买会员）{   比如当前超级，购买高级，则将付款金额转为超级会员时间     }
            if (当前会员 < 购买会员 && 当前会员 != 普通会员) {  比如当前高级，购买超级，则将高级会员剩余时间转为金额， 再加上购买的时间， 升级为高级会员}
            if (当前会员 == 购买会员) {  直接续费 }
        4, 更新会员数据
        5，将此次支付数据存入数据库
         */
        PayPushMsgBean payPushMsgBean = gson.jsonToBean(
                URLDecoder.decode(
                        gson.jsonToBean(
                                requestBody,
                                PayPushBean.class
                        ).getMsg(),
                        "UTF-8"),
                PayPushMsgBean.class);
        PayPushMsgBean.QrInfoBean qr_info = payPushMsgBean.getQr_info();
        PayQRcodeBean payQRcodeBean = new PayQRcodeBean(null, null);
        payQRcodeBean.setQrId(String.valueOf(qr_info.getQr_id()));

        // 查找数据库中，此次支付对应的二维码及会员信息
        List<PayQRcodeBean> payQRcodeBeans = null;
        try {
            payQRcodeBeans = DaoAgent.selectSQLForBean(payQRcodeBean);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (payQRcodeBeans == null || payQRcodeBeans.size() == 0) {
            LogUtil.e("支付回执： 未在数据库中找到此二维码id = " + payQRcodeBean.getQrId());
            return ResultBean.createSucInfo("success");
        }

        // 得到二维码对应的会员服务
        PayQRcodeBean payQRcodeBeanBySql = payQRcodeBeans.get(0);
        LogUtil.i("支付回执： 此次支付二维码数据 = " + payQRcodeBeanBySql);

        // 获取用户的vip信息
        List<UserVipInfoBean> userVipInfoBeans = null;
        try {
            userVipInfoBeans = DaoAgent.selectSQLForBean(UserVipInfoBean.newInstanceByUserId(payQRcodeBeanBySql.getUserId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (userVipInfoBeans == null || userVipInfoBeans.size() == 0) {
            LogUtil.e("支付回执： 未在数据库中找到此支付用户的Vip信息 userId= " + payQRcodeBeanBySql.getUserId());
            return ResultBean.createSucInfo("success");
        }

        // 获取到支付用户的会员信息
        UserVipInfoBean currentUserVipInfoBean = userVipInfoBeans.get(0);

        // 比较当前用户会员信息与购买的会员信息
        VIP_TYPE currentVipTypeEnum = currentUserVipInfoBean.getVipTypeEnum();
        VIP_TYPE payVipTypeEnum = payQRcodeBeanBySql.getEnumVipType();

        long newVipStartTime = Long.parseLong(currentUserVipInfoBean.getStartTime());
        long newVipEndTime;
        VIP_TYPE newVipTypeForSql;


        if (currentVipTypeEnum == VIP_TYPE.VIP_TYPE_SUPER && payVipTypeEnum == VIP_TYPE.VIP_TYPE_SENIOR) {
            newVipTypeForSql = VIP_TYPE.VIP_TYPE_SUPER;
            // 降级？ 支付金额折为当前会员时间，  超级 -》 高级
            // 超级会员一天的价格
            Integer superVipdayMoney = PayCodeVipTypeInfoBean.VIP_TYPE_SUPER_1MONTH.getMonthMoney() / 30;
            // 获取此次支付的总金额
            Integer allMoney = payQRcodeBeanBySql.getAllMoney();
            // 支付总结/一天单价，得出购买天数，转换为ms，再加上当前用户会员剩余时间
            newVipEndTime = DataUtil.timeDayToLong(allMoney / superVipdayMoney) + Long.parseLong(currentUserVipInfoBean.getEndTime());
            // 更新用户vip数据到数据库

        } else if (currentVipTypeEnum == VIP_TYPE.VIP_TYPE_SENIOR && payVipTypeEnum == VIP_TYPE.VIP_TYPE_SUPER) {
            // 高级 -》 超级
            newVipTypeForSql = VIP_TYPE.VIP_TYPE_SUPER;
            newVipStartTime = DataUtil.getNowTimeToLong();
            newVipEndTime = newVipStartTime + Long.parseLong(payQRcodeBeanBySql.getTimeLong());

            // 升级，当前会员剩余时间折现，在转为购买会员时间
            // 先判断当前用户会员剩余时间， 小于1天则直接忽略
            long endTime = Long.parseLong(currentUserVipInfoBean.getEndTime());
            if ((endTime = DataUtil.timeLongToDay(endTime - DataUtil.getNowTimeToLong())) > 1) {
                // 会员剩余天数大于一天
                // 查找支付二维码数据库， 计算高级会员一天多少钱
                Integer seniorVipdayMoney = PayCodeVipTypeInfoBean.VIP_TYPE_SENIOR_1MONTH.getMonthMoney() / 30;
                long endMoney = endTime * seniorVipdayMoney;
                // 超级会员一天的价格
                int superVipdayMoney = PayCodeVipTypeInfoBean.VIP_TYPE_SUPER_1MONTH.getMonthMoney() / 30;
                // 如果折现的钱足以购买超级会员一天及以上，则添加到vip到期时间中
                if (endMoney / superVipdayMoney > 0) {
                    newVipEndTime += DataUtil.timeDayToLong(endMoney / superVipdayMoney);
                }
            }
        } else {
            // 普通 -》 高级or超级     高级-》高级  超级-》超级

            // 当前为普通会员升级，则开始时间为当前时间， 结束时间为当前时间+购买的时间，
            if (currentVipTypeEnum == VIP_TYPE.VIP_TYPE_ORDINARY) {
                //
                newVipStartTime = DataUtil.getNowTimeToLong();
                newVipEndTime = Long.valueOf(payQRcodeBeanBySql.getTimeLong()) + newVipStartTime;
            } else {
                // 或者续费 高级-》高级  超级-》超级  会员开始时间不变， 结束时间加上购买的时间
                newVipEndTime = Long.valueOf(payQRcodeBeanBySql.getTimeLong()) + Long.parseLong(currentUserVipInfoBean.getEndTime());
            }
            newVipTypeForSql = payVipTypeEnum;
        }


        // 将新的Vip数据存入数据库
        UserVipInfoBean newUserVipInfoBean = UserVipInfoBean.newInstanceByVipType(
                null, null, String.valueOf(newVipStartTime), String.valueOf(newVipEndTime), newVipTypeForSql
        );
        try {
            // 更新用户会员状态
            DaoAgent.updataBeanForSQL(
                    newUserVipInfoBean,
                    currentUserVipInfoBean
            );


            // 区分续费还是升级，如果是升级， 则修正数据库状态，清空对应用户token，强制用户重新登录，更新数据库数据，scan列表。跟投列表
            // 如果是续费，则无需更改
            LogUtil.i("支付回执： 用户 当前VIp = " + currentVipTypeEnum+ "__购买的vip = " + newVipTypeForSql );
            if (currentVipTypeEnum != newVipTypeForSql) {
                LogUtil.i("支付回执： 会员状态改变，开始修正数据库中的状态");
                // 更新用户token， 强制重新登录
                UserInfoBean newUserInfoBean = UserInfoBean.newInstanceByTokenLogin("", false);
                UserInfoBean currentUserInfoBean = UserInfoBean.newInstance();
                currentUserInfoBean.setId(currentUserVipInfoBean.getUserId());
                if (DaoAgent.updataBeanForSQL(newUserInfoBean, currentUserInfoBean)) {
                    LogUtil.i("支付回执： 更新用户token成功");
                } else {
                    LogUtil.e("支付回执： 更新用户token失败，vipId:" + currentUserVipInfoBean.getId());
                }

                // 更新数据库数据，scan列表。跟投列表，修改是否可用
                if (VIPWithAuthorityUtil.vipInfoChangeToSQL(newUserVipInfoBean.getVipTypeEnum(), currentUserVipInfoBean.getUserId())) {
                    LogUtil.i("支付回执： 更新用户扫描列表，跟投列表成功");
                } else {
                    LogUtil.e("支付回执： 更新用户扫描列表，跟投列表失败，toVipType = " +
                            newUserVipInfoBean.getVipTypeEnum() +
                            "__UserId = " +
                            currentUserVipInfoBean.getUserId());
                }
            }

            LogUtil.i("支付回执： 更新用户VIP数据成功 new = " + newUserVipInfoBean.toString() + "__old = " + currentUserVipInfoBean.toString() );
        } catch (SQLException e) {
            LogUtil.e("支付回执， 更新用户VIP数据失败， new = " + newUserVipInfoBean.toString() + "__old = " + currentUserVipInfoBean.toString() , e);
        }



        // 将此次支付数据存入数据库
        PayInfoBean payInfoBean = new PayInfoBean(null, currentUserVipInfoBean.getUserId());
        payInfoBean.setAllData(requestBody);
        payInfoBean.setMoney(payQRcodeBeanBySql.getAllMoney());
        payInfoBean.setQrId(payQRcodeBeanBySql.getId());
        long nowTimeToLong = DataUtil.getNowTimeToLong();
        payInfoBean.setTimeToLong(String.valueOf(nowTimeToLong));
        payInfoBean.setTimeFormat(DataUtil.timeFormatAllToString(nowTimeToLong));
        try {
            DaoAgent.insertBeanToSQL(payInfoBean);
            LogUtil.i("支付回执： 添加支付数据至数据库成功" + payInfoBean);
        } catch (SQLException e) {
            LogUtil.e("支付回执： 添加支付数据至数据库失败" + payInfoBean.toString(), e );
        }

        LogUtil.i("支付回执： 回执流程成功 new = " + newUserVipInfoBean.toString() + "__old = " + currentUserVipInfoBean.toString() );

        return ResultBean.createSucInfo("success");
    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }
}


/*
收到的推送
{
    "client_id":"afcd2eb5b88fbdbd90",
    "id":"E20180918101734022600020",
    "kdt_id":41461397,
    "kdt_name":"????????¤??????????°????",
    "mode":1,
    "msg":"%7B%22order_promotion%22:%7B%22adjust_fee%22:%220.00%22%7D,%22qr_info%22:%7B%22qr_id%22:8286057,%22qr_pay_id%22:23005982,%22qr_name%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22%7D,%22refund_order%22:[],%22full_order_info%22:%7B%22address_info%22:%7B%22self_fetch_info%22:%22%22,%22delivery_address%22:%22%22,%22delivery_postal_code%22:%22%22,%22receiver_name%22:%22%22,%22delivery_province%22:%22%22,%22delivery_city%22:%22%22,%22delivery_district%22:%22%22,%22address_extra%22:%22%7B%7D%22,%22receiver_tel%22:%22%22%7D,%22remark_info%22:%7B%22buyer_message%22:%22%22%7D,%22pay_info%22:%7B%22outer_transactions%22:[%224200000185201809187018379596%22],%22post_fee%22:%220.00%22,%22total_fee%22:%220.01%22,%22payment%22:%220.01%22,%22transaction%22:[%22180918101736000050%22]%7D,%22buyer_info%22:%7B%22fans_type%22:9,%22buyer_id%22:290195682,%22fans_id%22:1950113670,%22fans_nickname%22:%22%22%7D,%22orders%22:[%7B%22outer_sku_id%22:%22%22,%22sku_unique_code%22:%22%22,%22goods_url%22:%22https://h5.youzan.com/v2/showcase/goods%3Falias=null%22,%22item_id%22:2147483647,%22outer_item_id%22:%22null%22,%22discount_price%22:%220.01%22,%22item_type%22:30,%22num%22:1,%22sku_id%22:0,%22sku_properties_name%22:%22%22,%22pic_path%22:%22https://img.yzcdn.cn/public_files/2016/12/29/33e6c838cefa614c5121c63c80f860e9.png%22,%22oid%22:%221472319870133616259%22,%22title%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22,%22buyer_messages%22:%22%22,%22is_present%22:false,%22pre_sale_type%22:%22null%22,%22points_price%22:%220%22,%22price%22:%220.01%22,%22total_fee%22:%220.01%22,%22alias%22:%22null%22,%22payment%22:%220.01%22,%22is_pre_sale%22:%22null%22%7D],%22source_info%22:%7B%22is_offline_order%22:false,%22book_key%22:%22null%22,%22source%22:%7B%22platform%22:%22wx%22,%22wx_entrance%22:%22direct_buy%22%7D%7D,%22order_info%22:%7B%22consign_time%22:%22%22,%22order_extra%22:%7B%22is_from_cart%22:%22false%22%7D,%22created%22:%222018-09-18%2010:17:34%22,%22status_str%22:%22%E5%B7%B2%E6%94%AF%E4%BB%98%22,%22expired_time%22:%222018-09-18%2010:47:34%22,%22success_time%22:%22%22,%22type%22:6,%22tid%22:%22E20180918101734022600020%22,%22confirm_time%22:%22%22,%22pay_time%22:%222018-09-18%2010:17:45%22,%22update_time%22:%222018-09-18%2010:17:46%22,%22pay_type_str%22:%22WEIXIN_DAIXIAO%22,%22is_retail_order%22:false,%22pay_type%22:10,%22team_type%22:1,%22refund_state%22:0,%22close_type%22:0,%22status%22:%22TRADE_PAID%22,%22express_type%22:9,%22order_tags%22:%7B%22is_payed%22:true,%22is_secured_transactions%22:true%7D%7D%7D%7D",
    "msg_id":"3b0020c9-b9cc-4926-ad69-44b3b3b85dc6",
    "sendCount":0,
    "sign":"d22a01e17bf8007d787867fd2ac2dd94",
    "status":"PAID",
    "test":false,
    "type":"trade_TradePaid",
    "version":1537237066
}

 */

/*
0917 创建的二维码
{"qr_url":"https://trade.koudaitong.com/wxpay/confirmQr?qr_id=8286057&kdt_id=41461397","qr_code":"data:image/png;base64,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","qr_id":8286057}
data:image/png;base64,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


{
    "client_id":"afcd2eb5b88fbdbd90",
    "id":"E20180918101734022600020",
    "kdt_id":41461397,
    "kdt_name":"????????¤??????????°????",
    "mode":1,
    "msg":"%7B%22order_promotion%22:%7B%22adjust_fee%22:%220.00%22%7D,%22qr_info%22:%7B%22qr_id%22:8286057,%22qr_pay_id%22:23005982,%22qr_name%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22%7D,%22refund_order%22:[],%22full_order_info%22:%7B%22address_info%22:%7B%22self_fetch_info%22:%22%22,%22delivery_address%22:%22%22,%22delivery_postal_code%22:%22%22,%22receiver_name%22:%22%22,%22delivery_province%22:%22%22,%22delivery_city%22:%22%22,%22delivery_district%22:%22%22,%22address_extra%22:%22%7B%7D%22,%22receiver_tel%22:%22%22%7D,%22remark_info%22:%7B%22buyer_message%22:%22%22%7D,%22pay_info%22:%7B%22outer_transactions%22:[%224200000185201809187018379596%22],%22post_fee%22:%220.00%22,%22total_fee%22:%220.01%22,%22payment%22:%220.01%22,%22transaction%22:[%22180918101736000050%22]%7D,%22buyer_info%22:%7B%22fans_type%22:9,%22buyer_id%22:290195682,%22fans_id%22:1950113670,%22fans_nickname%22:%22%22%7D,%22orders%22:[%7B%22outer_sku_id%22:%22%22,%22sku_unique_code%22:%22%22,%22goods_url%22:%22https://h5.youzan.com/v2/showcase/goods%3Falias=null%22,%22item_id%22:2147483647,%22outer_item_id%22:%22null%22,%22discount_price%22:%220.01%22,%22item_type%22:30,%22num%22:1,%22sku_id%22:0,%22sku_properties_name%22:%22%22,%22pic_path%22:%22https://img.yzcdn.cn/public_files/2016/12/29/33e6c838cefa614c5121c63c80f860e9.png%22,%22oid%22:%221472319870133616259%22,%22title%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22,%22buyer_messages%22:%22%22,%22is_present%22:false,%22pre_sale_type%22:%22null%22,%22points_price%22:%220%22,%22price%22:%220.01%22,%22total_fee%22:%220.01%22,%22alias%22:%22null%22,%22payment%22:%220.01%22,%22is_pre_sale%22:%22null%22%7D],%22source_info%22:%7B%22is_offline_order%22:false,%22book_key%22:%22null%22,%22source%22:%7B%22platform%22:%22wx%22,%22wx_entrance%22:%22direct_buy%22%7D%7D,%22order_info%22:%7B%22consign_time%22:%22%22,%22order_extra%22:%7B%22is_from_cart%22:%22false%22%7D,%22created%22:%222018-09-18%2010:17:34%22,%22status_str%22:%22%E5%B7%B2%E6%94%AF%E4%BB%98%22,%22expired_time%22:%222018-09-18%2010:47:34%22,%22success_time%22:%22%22,%22type%22:6,%22tid%22:%22E20180918101734022600020%22,%22confirm_time%22:%22%22,%22pay_time%22:%222018-09-18%2010:17:45%22,%22update_time%22:%222018-09-18%2010:17:46%22,%22pay_type_str%22:%22WEIXIN_DAIXIAO%22,%22is_retail_order%22:false,%22pay_type%22:10,%22team_type%22:1,%22refund_state%22:0,%22close_type%22:0,%22status%22:%22TRADE_PAID%22,%22express_type%22:9,%22order_tags%22:%7B%22is_payed%22:true,%22is_secured_transactions%22:true%7D%7D%7D%7D",
    "msg_id":"3b0020c9-b9cc-4926-ad69-44b3b3b85dc6",
    "sendCount":0,
    "sign":"d22a01e17bf8007d787867fd2ac2dd94",
    "status":"PAID",
    "test":false,
    "type":"trade_TradePaid",
    "version":1537237066}'
}

%7B%22order_promotion%22:%7B%22adjust_fee%22:%220.00%22%7D,%22qr_info%22:%7B%22qr_id%22:8286057,%22qr_pay_id%22:23005982,%22qr_name%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22%7D,%22refund_order%22:[],%22full_order_info%22:%7B%22address_info%22:%7B%22self_fetch_info%22:%22%22,%22delivery_address%22:%22%22,%22delivery_postal_code%22:%22%22,%22receiver_name%22:%22%22,%22delivery_province%22:%22%22,%22delivery_city%22:%22%22,%22delivery_district%22:%22%22,%22address_extra%22:%22%7B%7D%22,%22receiver_tel%22:%22%22%7D,%22remark_info%22:%7B%22buyer_message%22:%22%22%7D,%22pay_info%22:%7B%22outer_transactions%22:[%224200000185201809187018379596%22],%22post_fee%22:%220.00%22,%22total_fee%22:%220.01%22,%22payment%22:%220.01%22,%22transaction%22:[%22180918101736000050%22]%7D,%22buyer_info%22:%7B%22fans_type%22:9,%22buyer_id%22:290195682,%22fans_id%22:1950113670,%22fans_nickname%22:%22%22%7D,%22orders%22:[%7B%22outer_sku_id%22:%22%22,%22sku_unique_code%22:%22%22,%22goods_url%22:%22https://h5.youzan.com/v2/showcase/goods%3Falias=null%22,%22item_id%22:2147483647,%22outer_item_id%22:%22null%22,%22discount_price%22:%220.01%22,%22item_type%22:30,%22num%22:1,%22sku_id%22:0,%22sku_properties_name%22:%22%22,%22pic_path%22:%22https://img.yzcdn.cn/public_files/2016/12/29/33e6c838cefa614c5121c63c80f860e9.png%22,%22oid%22:%221472319870133616259%22,%22title%22:%22%E6%B5%8B%E8%AF%95%E7%94%9F%E6%88%90%E6%94%B6%E6%AC%BE%E4%BA%8C%E7%BB%B4%E7%A0%81%22,%22buyer_messages%22:%22%22,%22is_present%22:false,%22pre_sale_type%22:%22null%22,%22points_price%22:%220%22,%22price%22:%220.01%22,%22total_fee%22:%220.01%22,%22alias%22:%22null%22,%22payment%22:%220.01%22,%22is_pre_sale%22:%22null%22%7D],%22source_info%22:%7B%22is_offline_order%22:false,%22book_key%22:%22null%22,%22source%22:%7B%22platform%22:%22wx%22,%22wx_entrance%22:%22direct_buy%22%7D%7D,%22order_info%22:%7B%22consign_time%22:%22%22,%22order_extra%22:%7B%22is_from_cart%22:%22false%22%7D,%22created%22:%222018-09-18%2010:17:34%22,%22status_str%22:%22%E5%B7%B2%E6%94%AF%E4%BB%98%22,%22expired_time%22:%222018-09-18%2010:47:34%22,%22success_time%22:%22%22,%22type%22:6,%22tid%22:%22E20180918101734022600020%22,%22confirm_time%22:%22%22,%22pay_time%22:%222018-09-18%2010:17:45%22,%22update_time%22:%222018-09-18%2010:17:46%22,%22pay_type_str%22:%22WEIXIN_DAIXIAO%22,%22is_retail_order%22:false,%22pay_type%22:10,%22team_type%22:1,%22refund_state%22:0,%22close_type%22:0,%22status%22:%22TRADE_PAID%22,%22express_type%22:9,%22order_tags%22:%7B%22is_payed%22:true,%22is_secured_transactions%22:true%7D%7D%7D%7D

 */