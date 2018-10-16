package com.wu.immortal.half.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.configs.ApplicationConfig;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.PayQRcodeBean;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends BaseServletServlet {
    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBeann, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        // 1, 账号密码是否正确，2,更新数据库状态，3，返回用户数据+vip数据
        UserInfoBean userInfoBean = gson.jsonToBean(requestBody, UserInfoBean.class);

        if (userInfoBean == null || FinalUtil.checkNull(userInfoBean.getPhone()) || FinalUtil.checkNull(userInfoBean.getPassWord())) {
            LogUtil.e("登录失败，账号密码数据异常" + requestBody);
            return ResultBean.REQUEST_ERRO_JSON;
        }

        List<UserInfoBean> userInfoBeans;
        UserInfoBean userInfoBeanBySql;
        // 判断账号密码是否匹配
        try {
            userInfoBeans = DaoAgent.selectSQLForBean(UserInfoBean.newInstanceByPhone(userInfoBean.getPhone()));
        } catch (SQLException e) {
            LogUtil.e("登录失败，通过手机号查找用户数据库异常" + userInfoBean.getPhone(), e);
            return ResultBean.REQUEST_ERRO_SQL;
        }

        if (userInfoBeans == null || userInfoBeans.size() == 0) {
            LogUtil.w("登录失败，用户尚未注册" + userInfoBean.getPhone());
            return ResultBean.REQUEST_ERRO_NOT_FOUND_PHONE;
        }

        userInfoBeanBySql = userInfoBeans.get(0);
        String passWord = userInfoBean.getPassWord();
        String passWordBySql = userInfoBeanBySql.getPassWord();
        String salt = userInfoBeanBySql.getSalt();

        try {
            boolean authenticate = PasswordEncryption.authenticate(passWord, passWordBySql, salt);
            if (!authenticate) {
                throw new NoSuchAlgorithmException();
            }
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LogUtil.e("登录失败，密码对比异常，明文：" + passWord + "__密文：" + passWordBySql, e);
            return ResultBean.REQUEST_ERRO_PASSWORD;
        }

        // 获取vip数据
        List<UserVipInfoBean> userVipInfoBeans;
        try {
            userVipInfoBeans = DaoAgent.selectSQLForBean(
                    UserVipInfoBean.newInstanceByUserId(userInfoBeanBySql.getId())
            );
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e("登录失败，未找到账号"+userInfoBeanBySql.getPhone()+"对应会员数据", e);
            return ResultBean.REQUEST_ERRO_SQL;
        }

        if (userVipInfoBeans == null || userVipInfoBeans.size() == 0) {
            LogUtil.e("登录失败，未找到账号"+userInfoBeanBySql.getPhone()+"对应会员数据");
            return ResultBean.REQUEST_ERRO_SQL;
        }

        // todo 会员过期的判断， 要修正数据库信息，测试
        UserVipInfoBean userVipInfoBean = userVipInfoBeans.get(0);
        long vipEndTime = Long.valueOf(userVipInfoBean.getEndTime());
        // 会员到期，修正数据库信息
        if (vipEndTime <= DataUtil.getNowTimeToLong()) {
            LogUtil.i("会员过期，修正数据库信息 : " + userVipInfoBean.toString());
            // 修正扫描+跟投的可用状态
            boolean b = VIPWithAuthorityUtil.vipInfoChangeToSQL(VIP_TYPE.VIP_TYPE_ORDINARY, userVipInfoBean.getUserId());
            if (!b) {
                LogUtil.e("会员过期，修正数据库信息失败，vipInfoChangeToSQL返回false，userid = " + userVipInfoBean.getUserId());
            }
            UserVipInfoBean newUserVipInfoBean = UserVipInfoBean.newInstance();
            // 重置为普通会员， 普通会员十年
            vipEndTime = DataUtil.getDefaultOrdidnaryTimeToLong();
            newUserVipInfoBean.setEndTime(String.valueOf(vipEndTime));
            newUserVipInfoBean.setEndTimeFormat(DataUtil.timeFormatYMDToString(vipEndTime));
            newUserVipInfoBean.setVipType(VIP_TYPE.VIP_TYPE_ORDINARY.getCode());
            try {
                DaoAgent.updataBeanForSQL(newUserVipInfoBean, userVipInfoBean);
                LogUtil.i("会员过期，修正数据库会员状态成功");
            } catch (SQLException e) {
                LogUtil.e("会员过期，修正数据库会员状态失败", e);
            }
        }

        //  验证成功， 生成token+用户信息+vip信息
        // 刷新用户Token值， 跟新登录状态
        String token = JwtUtil.createNewToken(userInfoBeanBySql.getPhone(),
                userInfoBeanBySql.getId(),
                vipEndTime);
        try {
            boolean upadtaSuc = DaoAgent.updataBeanForSQL(
                UserInfoBean.newInstanceByTokenLogin(token, true),
                    userInfoBeanBySql
            );
            if (!upadtaSuc) {
                throw new SQLException("");
            }
        } catch (SQLException e) {
            LogUtil.e("登录失败，跟新用户Token失败"+userInfoBeanBySql.getPhone());
            return ResultBean.REQUEST_ERRO_SQL;
        }


        // 获取支付二维码数据 todo 服务器热更新
        List<PayQRcodeBean> payQRcodeBeans;
        try {
            payQRcodeBeans = DaoAgent.selectSQLForBean(new PayQRcodeBean(null, userInfoBeanBySql.getId()));
            if (payQRcodeBeans.size() == 0) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LogUtil.e("登录失败，获取用户支付二维码数据失败 userId = "+ userInfoBeanBySql.getId());
            return ResultBean.REQUEST_ERRO_SQL;
        }

        JsonArray jsonArraySuperVip = new JsonArray();
        JsonArray jsonArraySeniorVip = new JsonArray();
        // 排序
        for (PayQRcodeBean payQRcodeBean : payQRcodeBeans) {
            payQRcodeBean.setId(null);
            payQRcodeBean.setQrId(null);
            payQRcodeBean.setQrName(null);
            payQRcodeBean.setCreateTime(null);
            payQRcodeBean.setUserId(null);

            JsonElement payQRcodeBeanJsonElement = gson.toJsonElement(payQRcodeBean);
            switch (payQRcodeBean.getEnumVipType()) {
                // 高级会员
                case VIP_TYPE_SENIOR:
                    jsonArraySeniorVip.add(payQRcodeBeanJsonElement);
                    break;
                default:
                    // 超级会员
                    jsonArraySuperVip.add(payQRcodeBeanJsonElement);
            }
        }

        JsonObject payQrInfoJsonObject = new JsonObject();
        payQrInfoJsonObject.add("superVip", jsonArraySuperVip);
        payQrInfoJsonObject.add("seniorVip", jsonArraySeniorVip);


        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("phone", userInfoBeanBySql.getPhone());
        jsonObject.add("vipInfo", gson.toJsonElement(userVipInfoBean));
        jsonObject.add("vipQR", gson.toJsonElement(payQrInfoJsonObject));
        String loginResultJson = jsonObject.toString();
        LogUtil.i("登录成功：" + loginResultJson);
        ApplicationConfig.instance().setDebug(false);
        return ResultBean.createSucInfo(jsonObject);
    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }
}
