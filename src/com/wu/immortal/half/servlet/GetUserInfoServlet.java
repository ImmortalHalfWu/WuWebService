package com.wu.immortal.half.servlet;

import com.google.gson.JsonObject;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.utils.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetUserInfoServlet")
public class GetUserInfoServlet extends BaseServletServlet {
    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBeann, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        LogUtil.i("获取用户信息");

        UserVipInfoBean userVipInfoBean;

        try {
            List<UserVipInfoBean> userVipInfoBeans = DaoAgent.selectSQLForBean(UserVipInfoBean.newInstanceByUserId(userInfoBeann.getId()));
            if (userVipInfoBeans.size() == 0) {
                throw new SQLException();
            }
            userVipInfoBean = userVipInfoBeans.get(0);
        } catch (SQLException e) {
            LogUtil.e("获取用户数据失败：未找到VIP信息");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("phone", userInfoBeann.getPhone());
        jsonObject.add("vipInfo", gson.toJsonElement(userVipInfoBean));

        LogUtil.i("获取用户信息成功");

        return ResultBean.createSucInfo(jsonObject);
    }
}
