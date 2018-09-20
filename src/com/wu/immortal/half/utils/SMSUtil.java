package com.wu.immortal.half.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.wu.immortal.half.configs.ApplicationConfig;

public class SMSUtil {

    private static SMSUtil smsUtil;

    private SMSUtil() {
        LogUtil.i("SMSUtil初始化成功");
    }


    public static void init() {
        if (smsUtil == null) {
            synchronized (SMSUtil.class) {
                smsUtil = new SMSUtil();
            }
        }
    }

    public static SMSUtil getInstance() {
        return smsUtil;
    }

    /**
     * @param code  要发送的验证码
     * @return 发送是否成功
     */
    public boolean sendSMS(int code, String phone) {
        LogUtil.i("开始发送短信： phone = " + phone + "__验证码 = " + code);
        GetRequest getRequest;
        try {
            ApplicationConfig applicationConfig = ApplicationConfig.instance();
            // 配置文件读取
            String smsAppCode = applicationConfig.getSMSAppCode();
            String smsUrlHost = applicationConfig.getSMSUrlHost();
            String smsUrlPath = applicationConfig.getSMSUrlPath();
            getRequest = Unirest.get(smsUrlHost + smsUrlPath + "?code=" + code + "&phone=" + phone + "&sign=1" + "&skin=20");
            getRequest.header("authorization", smsAppCode);
            LogUtil.i("发送短信： 请求体 = " + getRequest.getUrl());

            HttpResponse<String> response = getRequest.asString();
            if (response.getStatus() != 200) {
                LogUtil.e("短信请求失败，返回码：" + response.getStatus());
                return false;
            }

            String bodyString = response.getBody();
            if (bodyString == null) {
                LogUtil.e("短信请求失败：响应体body数据为null");
                return false;
            }

            LogUtil.i("发送短信： 响应体 = " + response.getBody());

            JSONObject jsonObject = JSON.parseObject(bodyString);
            if (jsonObject.isEmpty()) {
                LogUtil.e("短信请求失败：响应体body转json数据为null，json = " + jsonObject);
                return false;
            }

            String code1 = jsonObject.getString("Code");
            if (!"OK".equals(code1)) {
                LogUtil.e("短信请求失败：返回状态码为null 或 不为 OK " + jsonObject);
                return false;
            }
        } catch (UnirestException e) {
            LogUtil.e("短信请求失败：" , e);
            return false;
        }
        LogUtil.i("发送短信： 成功 ");
        return true;
    }

}
