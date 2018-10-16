package com.wu.immortal.half.servlet;

import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.ScanInfoBean;
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

/**
 * 删除扫描
 */
@WebServlet(name = "ScanDeleteServlet")
public class ScanDeleteServlet extends BaseServletServlet {

    private static final String TAG = "删除扫描";

    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        LogUtil.i(TAG + " json = " + requestBody);

        ScanInfoBean scanInfoBean = gson.jsonToBean(requestBody, ScanInfoBean.class);

        if (scanInfoBean.checkNull() || scanInfoBean.getId() == null || scanInfoBean.getUserId() == null) {
            LogUtil.e(TAG + "失败， json数据不完整");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        try {
//            List<ScanInfoBean> scanInfoBeans = DaoAgent.selectSQLForBean(scanInfoBean);
//            if (scanInfoBeans.size() == 0) {
//                return ResultBean.createSucInfo("");
//            }
//            scanInfoBean = scanInfoBeans.get(0);

            boolean isSuc = DaoAgent.deleteBeanForSQL(scanInfoBean);

            if (!isSuc) {
                throw new SQLException();
            }

        } catch (SQLException e) {
            LogUtil.e(TAG + "异常，删除指定扫描失败");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        LogUtil.i(TAG + "成功");

        return ResultBean.createSucInfo("");
    }
}
