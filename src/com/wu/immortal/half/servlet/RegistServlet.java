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

import javax.servlet.annotation.WebServlet;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RegistServlet")
public class RegistServlet extends BaseServletServlet {

    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBeann, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) {

        // 1，验证账号是否重复，2，初始化vip数据+userInfo 返回suc

        // 序列化注册信息
        UserInfoBean userInfoBean = gson.jsonToBean(requestBody, UserInfoBean.class);
        // 注册信息无效
        if (userInfoBean == null || FinalUtil.checkNull(userInfoBean.getPhone()) || FinalUtil.checkNull(userInfoBean.getPassWord())) {
            LogUtil.w("注册信息无效");
            return ResultBean.REQUEST_ERRO_REGIST_INFO;
        }

        UserInfoBean userInfoBeanByPhone = UserInfoBean.newInstanceByPhone(userInfoBean.getPhone());

        try {
            // 查询数据库账号是否已经注册
            List<UserInfoBean> userInfoBeans = DaoAgent.selectSQLForBean(userInfoBeanByPhone);

            if (userInfoBeans != null && userInfoBeans.size() != 0) {
                LogUtil.w("账号重复注册：" + userInfoBeanByPhone.getPhone());
                return ResultBean.REQUEST_ERRO_REGIST_IS_REGIST;
            }

            DaoAgent.registerUser(userInfoBean.getPhone(), userInfoBean.getPassWord());

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LogUtil.e("注册账号失败：密码加盐失败 " + userInfoBean, e);
            e.printStackTrace();
            return ResultBean.REQUEST_ERRO_REGIST_PASSWORD;
        } catch (SQLException e) {
            LogUtil.e("注册账号失败：数据库异常 " + userInfoBean, e);
            e.printStackTrace();
            return ResultBean.REQUEST_ERRO_SQL;
        }

        return ResultBean.REQUEST_SUCCESS;
    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }
}
