package com.wu.immortal.half.servlet;

import com.google.gson.JsonElement;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.DocumentaryInfoBean;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.utils.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DocumentaGetAllInfoServlet")
public class DocumentaGetAllInfoServlet extends BaseServletServlet {
    @Override
    protected ResultBean.ResultInfo post(UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        DocumentaryInfoBean documentaryInfoBean = DocumentaryInfoBean.newInstance();
        documentaryInfoBean.setUserId(tokenInfoBean.getUserId());
        try {

            List<DocumentaryInfoBean> documentaryInfoBeans = DaoAgent.selectSQLForBean(documentaryInfoBean);
            JsonElement jsonElement = gson.toJsonElement(documentaryInfoBeans);
            LogUtil.i("查询跟投列表成功" + jsonElement.toString());
            return ResultBean.createSucInfo(jsonElement);
        } catch (SQLException e) {
            LogUtil.e("查询跟投列表失败，用户信息" + userInfoBean.toString(), e);
        }


        return ResultBean.REQUEST_ERRO_SQL;
    }
}
