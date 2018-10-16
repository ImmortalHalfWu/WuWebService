package com.wu.immortal.half.servlet;

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

/**
 * 删除跟投
 */
@WebServlet(name = "DocumentarDeleteServlet")
public class DocumentarDeleteServlet extends BaseServletServlet {

    private static final String TAG = "删除跟投";

    @Override
    protected ResultBean.ResultInfo post(UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        DocumentaryInfoBean documentaryInfoBean = gson.jsonToBean(requestBody, DocumentaryInfoBean.class);
        if (documentaryInfoBean.checkNull() || documentaryInfoBean.getId() == null || documentaryInfoBean.getUserId() == null) {
            LogUtil.e("删除指定跟投失败， json不完整，" + requestBody);
            return ResultBean.REQUEST_ERRO_JSON;
        }

        try {
            boolean b = DaoAgent.deleteBeanForSQL(documentaryInfoBean);
            if (!b) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LogUtil.e(TAG + "失败，", e);
            return ResultBean.REQUEST_ERRO_SQL;
        }

        LogUtil.i(TAG + "成功");

        return ResultBean.createSucInfo(
                ""
        );
    }

}
