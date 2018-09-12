package com.wu.immortal.half.servlet.base;

import com.google.gson.JsonObject;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "SendSMSServlet")
public class SendSMSServlet extends BaseServletServlet {
    @Override
    protected ResultBean.ResultInfo post(TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {
        LogUtil.i("申请发送验证码：" + requestBody);
        UserInfoBean userInfoBean = gson.jsonToBean(requestBody, UserInfoBean.class);
        if (FinalUtil.checkNull(userInfoBean.getPhone())) {
            LogUtil.e("申请发送验证码失败，反序列化未找到phone");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        if (!FinalUtil.isPhone(userInfoBean.getPhone())) {
            LogUtil.e("申请发送验证码失败，phone 非法格式");
            return ResultBean.REQUEST_ERRO_SMS_PHONE;
        }

        int smsCode = (int) (DataUtil.getNowTimeToLong() % 1000000);

        try {
            String smsCodeMd5 = MD5Util.EncoderByMd5(smsCode + "");
            LogUtil.i("申请发送验证码：MD5成功， code = " + smsCode + "__MD5 = " + smsCodeMd5);

//            if (!SMSUtil.sendSMS(smsCode, userInfoBean.getPhone())) {
//                LogUtil.e("申请发送验证码失败");
//                return ResultBean.REQUEST_ERRO_SMS_FAIL;
//            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("code", smsCodeMd5);

            return ResultBean.createSucInfo(jsonObject);
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e("申请发送验证码失败, 验证码MD5加密异常", e);
            return ResultBean.REQUEST_ERRO_SMS_FAIL;
        }

    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }
}
