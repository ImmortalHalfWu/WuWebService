package com.wu.immortal.half.servlet;

import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.utils.FinalUtil;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.PasswordEncryption;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ResetPassWordServlet")
public class ResetPassWordServlet extends BaseServletServlet {

    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBeann, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {
        LogUtil.i("修改密码 json = " + requestBody);

        UserInfoBean userInfoBean = gson.jsonToBean(requestBody, UserInfoBean.class);
        String phone = userInfoBean.getPhone();
        String passWord = userInfoBean.getPassWord();

        if (FinalUtil.checkNull(phone) || FinalUtil.checkNull(passWord)) {
            LogUtil.e("修改密码失败，账号或密码null");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        UserInfoBean userInfoBeanByPhone = UserInfoBean.newInstanceByPhone(phone);

        try {
            List<UserInfoBean> userInfoBeans = DaoAgent.selectSQLForBean(userInfoBeanByPhone);
            if (userInfoBeans.size() == 0) {
                throw new SQLException("修改密码失败，未找到手机号" + phone);
            }
        } catch (SQLException e) {
            LogUtil.e("修改密码失败，未找到手机号", e);
            return ResultBean.REQUEST_ERRO_NOT_FOUND_PHONE;
        }

        try {
            String salt = PasswordEncryption.generateSalt();
            passWord = PasswordEncryption.getEncryptedPassword(passWord, salt);

            DaoAgent.updataBeanForSQL(
                    UserInfoBean.newInstanceByResetPassWord(passWord, salt),
                    userInfoBeanByPhone);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LogUtil.e("修改密码失败，密码加密失败 ，" + passWord, e);
            return ResultBean.REQUEST_ERRO_REGIST_PASSWORD;
        } catch (SQLException e) {
            LogUtil.e("修改密码失败，更新数据库失败 ，" + phone + "_password" + passWord, e);
            return ResultBean.REQUEST_ERRO_REGIST_INFO;
        }
        LogUtil.i("修改密码成功" + phone);
        return ResultBean.createSucInfo("");
    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }
}
