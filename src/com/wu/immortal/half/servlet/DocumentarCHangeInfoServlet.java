package com.wu.immortal.half.servlet;

import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.DocumentaryInfoBean;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.VIPWithAuthorityUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "DocumentarCHangeInfoServlet")
public class DocumentarCHangeInfoServlet extends BaseServletServlet {

    private static final String TAG = "修改跟投配置：";

    @Override
    protected ResultBean.ResultInfo post(UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        DocumentaryInfoBean newDocumentaryInfoBean = gson.jsonToBean(requestBody, DocumentaryInfoBean.class);

        if (newDocumentaryInfoBean.checkNull() || newDocumentaryInfoBean.getId() == null || newDocumentaryInfoBean.getUserId() == null) {
            return ResultBean.REQUEST_ERRO_JSON;
        }


        // 判断是否已经存在指定的url
        DocumentaryInfoBean documentaryInfoBeanByScanUrl = DocumentaryInfoBean.newInstance();
        documentaryInfoBeanByScanUrl.setScanUrl(newDocumentaryInfoBean.getScanUrl());
        documentaryInfoBeanByScanUrl.setUserId(tokenInfoBean.getUserId());
        try {
            List<DocumentaryInfoBean> documentaryInfoBeans = DaoAgent.selectSQLForBean(documentaryInfoBeanByScanUrl);
            for (DocumentaryInfoBean indexBean : documentaryInfoBeans) {
                if (!indexBean.getId().equals(newDocumentaryInfoBean.getId()) && indexBean.getScanUrl().equals(newDocumentaryInfoBean.getScanUrl())) {
                    LogUtil.i("修改跟投配置，但指定url已存在于数据库， " + newDocumentaryInfoBean.toString());
                    return ResultBean.REQUEST_ERRO_SCAN_ADD_REPEAT;
                }
            }
        } catch (SQLException ignored) {
        }


        // 匹配权限， 是否是合法扫描
        UserVipInfoBean userVipInfoBean;
        userVipInfoBean = DaoAgent.findVipInfoByUserId(tokenInfoBean.getUserId());
        if (userVipInfoBean == null) {
            return ResultBean.REQUEST_ERRO_JSON;
        }
        LogUtil.i(TAG + "开始匹配用户信息与扫描信息是否匹配");
        boolean isCanUser = VIPWithAuthorityUtil.authDocumentaryInfo(userVipInfoBean.getVipTypeEnum(), newDocumentaryInfoBean);
        LogUtil.i(TAG + "匹配结果，" + isCanUser);
        newDocumentaryInfoBean.setCanUser(isCanUser);

        DocumentaryInfoBean oldDocumentaryInfoBean = new DocumentaryInfoBean(
                newDocumentaryInfoBean.getId(), null,null,null,null,null,null,null,null
        );

        try {
            boolean b = DaoAgent.updataBeanForSQL(newDocumentaryInfoBean, oldDocumentaryInfoBean);
            if (!b) {
                LogUtil.e(TAG + "失败, 数据库异常");
                return ResultBean.REQUEST_ERRO_SQL;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(TAG + "失败", e);
            return ResultBean.REQUEST_ERRO_SQL;
        }

        LogUtil.i(TAG + "成功");
        return ResultBean.createSucInfo("");
    }

}
