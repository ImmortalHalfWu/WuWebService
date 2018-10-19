package com.wu.immortal.half.servlet;

import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.DocumentaryInfoBean;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.sql.bean.enums.DOCUMENTARY_TYPE;
import com.wu.immortal.half.sql.bean.enums.ORDER_TYPE;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.VIPWithAuthorityUtil;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_ORDINARY;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SENIOR;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SUPER;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_ORDINARY;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_SENIOR;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_SUPER;

/**
 * 添加跟投
 */
@WebServlet(name = "DocumentaryAddServlet")
public class DocumentaryAddServlet extends BaseServletServlet {

    private static final String TAG = "添加跟投";

    @Override
    protected ResultBean.ResultInfo post(UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {
        LogUtil.i(TAG + requestBody);
        DocumentaryInfoBean documentaryInfoBean = gson.jsonToBean(requestBody, DocumentaryInfoBean.class);
        if (documentaryInfoBean.checkNull()) {
            LogUtil.e(TAG + " json数据不完整");
            return ResultBean.REQUEST_ERRO_JSON;
        }


        // 判读是否已存在此网址
        DocumentaryInfoBean documentaryInfoBeanByScanUrl = DocumentaryInfoBean.newInstance();
        documentaryInfoBeanByScanUrl.setScanUrl(documentaryInfoBean.getScanUrl());
        documentaryInfoBeanByScanUrl.setUserId(tokenInfoBean.getUserId());
        try {
            List<DocumentaryInfoBean> documentaryInfoBeans = DaoAgent.selectSQLForBean(documentaryInfoBeanByScanUrl);
            if (documentaryInfoBeans.size() != 0) {
                LogUtil.i("重复添加跟投， url = " + documentaryInfoBean.getScanUrl());
                return ResultBean.REQUEST_ERRO_SCAN_ADD_REPEAT;
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

        boolean isCanUser = VIPWithAuthorityUtil.authDocumentaryInfo(userVipInfoBean.getVipTypeEnum(), documentaryInfoBean);

        LogUtil.i(TAG + "匹配结果，" + isCanUser);
        documentaryInfoBean.setCanUser(isCanUser);
        documentaryInfoBean.setUserId(tokenInfoBean.getUserId());
        try {
            boolean b = DaoAgent.insertBeanToSQL(documentaryInfoBean);
            if (!b) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LogUtil.e("添加跟投失败，", e);
            e.printStackTrace();
            return ResultBean.REQUEST_ERRO_JSON;
        }
        LogUtil.i("添加跟投成功");

        return ResultBean.createSucInfo("");
    }

}
