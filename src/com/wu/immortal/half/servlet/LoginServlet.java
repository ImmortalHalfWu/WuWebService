package com.wu.immortal.half.servlet;

import com.google.gson.JsonObject;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
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
    protected ResultBean.ResultInfo post(TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        // todo 1, 账号密码是否正确，2，返回用户数据+vip数据
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


        UserVipInfoBean userVipInfoBean = userVipInfoBeans.get(0);
        long vipEndTime = Long.valueOf(userVipInfoBean.getEndTime());

        //  验证成功， 生成token+用户信息+vip信息
        long nowTimeToLong = DataUtil.getNowTimeToLong();
        tokenInfoBean = new TokenInfoBean(
                "",
                userInfoBeanBySql.getPhone(),
                userInfoBeanBySql.getId(),
                vipEndTime,
                nowTimeToLong, nowTimeToLong);

        // 刷新用户Token值， 跟新登录状态
        String token = JwtUtil.createNewToken(tokenInfoBean);
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

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("phone", userInfoBeanBySql.getPhone());
        jsonObject.add("vipInfo", gson.toJsonElement(userVipInfoBean));
        String loginResultJson = jsonObject.toString();
        LogUtil.i("登录成功：" + loginResultJson);

        return ResultBean.createSucInfo(jsonObject);
    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }
}
