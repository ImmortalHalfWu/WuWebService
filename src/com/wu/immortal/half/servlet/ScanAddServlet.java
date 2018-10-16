package com.wu.immortal.half.servlet;

import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.ScanInfoBean;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.sql.bean.enums.DOCUMENTARY_TYPE;
import com.wu.immortal.half.sql.bean.enums.ORDER_TYPE;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.VIPWithAuthorityUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;



/**
 *  相同网址重复添加， 应该屏蔽
 */
@WebServlet(name = "ScanAddServlet")
public class ScanAddServlet extends BaseServletServlet {

    private static final String TAG = "添加扫描";

    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        LogUtil.i(TAG + requestBody);

        ScanInfoBean scanInfoBean = gson.jsonToBean(requestBody, ScanInfoBean.class);
        if (scanInfoBean.checkNull()) {
            LogUtil.e(TAG + "失败，数据不完整");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        // 同一网址只能添加一个
        try {
            List<ScanInfoBean> scanInfoBeansByUrl =
                    DaoAgent.selectSQLForBean(
                            ScanInfoBean.newInstanceByUrl(scanInfoBean.getScanUrl(), tokenInfoBean.getUserId()
                            )
                    );
            if (scanInfoBeansByUrl.size() != 0) {
                LogUtil.i(TAG + "重复添加扫描");
                return ResultBean.REQUEST_ERRO_SCAN_ADD_REPEAT;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e(TAG + "", e);
        }


        // 匹配权限， 是否是合法扫描
        UserVipInfoBean userVipInfoBean;
        userVipInfoBean = DaoAgent.findVipInfoByUserId(tokenInfoBean.getUserId());
        if (userVipInfoBean == null) {
            return ResultBean.REQUEST_ERRO_JSON;
        }

        LogUtil.i(TAG + "开始匹配用户信息与扫描信息是否匹配");

        boolean isCanUser = VIPWithAuthorityUtil.authScanInfo(userVipInfoBean.getVipTypeEnum(), scanInfoBean);

        LogUtil.i(TAG + "匹配结果，" + isCanUser);

        // 是否可用
        scanInfoBean.setCanUser(isCanUser);

        LogUtil.i(TAG + "插入数据库");

        scanInfoBean.setUserId(tokenInfoBean.getUserId());

        try {
            boolean isSuc = DaoAgent.insertBeanToSQL(scanInfoBean);
            if (!isSuc) {
                throw new SQLException();
            }

        } catch (SQLException e) {
            LogUtil.e(TAG + "扫描数据插入失败，" + scanInfoBean.toString());
            return ResultBean.REQUEST_ERRO_SQL;
        }

        LogUtil.i(TAG + " 插入数据库成功, 完成添加");
        return ResultBean.createSucInfo("");

    }

}
