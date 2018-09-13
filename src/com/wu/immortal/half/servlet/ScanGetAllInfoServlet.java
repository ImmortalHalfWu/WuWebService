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
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ScanGetAllInfoServlet")
public class ScanGetAllInfoServlet extends BaseServletServlet {
    private static final String TAG = "获取扫描列表";
    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBeann, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        LogUtil.i(TAG + "开始");
        List<ScanInfoBean> scanInfoBeans;
        try {
            scanInfoBeans = DaoAgent.selectSQLForBean(ScanInfoBean.newInstanceByUserId(userInfoBeann.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(TAG + "失败，查找数据库异常");
            return ResultBean.REQUEST_ERRO_SQL;
        }
        LogUtil.i(TAG + "成功");
        return ResultBean.createSucInfo(gson.toJsonElement(scanInfoBeans));
    }
}
