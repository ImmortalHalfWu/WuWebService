package com.wu.immortal.half.servlet;

import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.utils.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends BaseServletServlet {

    @Override
    protected ResultBean.ResultInfo post(TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        String token = tokenInfoBean.getToken();
        List<UserInfoBean> userInfoBeans;
        try {
            userInfoBeans = DaoAgent.selectSQLForBean(UserInfoBean.newInstanceByToken(token));
            if (userInfoBeans.size() == 0 || userInfoBeans.size() > 1) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LogUtil.e("退出登录失败，未找到账号"+tokenInfoBean, e);
            return ResultBean.REQUEST_ERRO_SQL;
        }

        UserInfoBean userInfoBean = userInfoBeans.get(0);
        if (!userInfoBean.getIsLogin()) {
            return ResultBean.createSucInfo("");
        }
        UserInfoBean newUserInfoBean = UserInfoBean.newInstanceByTokenLogin(token, false);

        try {
            boolean updataSuc = DaoAgent.updataBeanForSQL(newUserInfoBean, userInfoBean);
            if (!updataSuc) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LogUtil.e("退出登录失败，更新用户状态失败"+tokenInfoBean, e);
            return ResultBean.REQUEST_ERRO_SQL;
        }

        return ResultBean.createSucInfo("");
    }

}
