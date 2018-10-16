package com.wu.immortal.half.servlet;

import com.google.gson.Gson;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ResultBeanEnum;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.ScanInfoBean;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.VIPWithAuthorityUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_ORDINARY;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SENIOR;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SUPER;
import static com.wu.immortal.half.utils.FinalUtil.*;

@WebServlet(name = "ScanChangeInfoServlet")
public class ScanChangeInfoServlet extends BaseServletServlet {

    private static final String TAG = "修改扫描信息";

    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBean, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        ScanInfoBean scanInfoBean = gson.jsonToBean(requestBody, ScanInfoBean.class);

        if (scanInfoBean.checkNull() || scanInfoBean.getId() == null) {
            LogUtil.e(TAG + "失败，数据不完整");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        // 判断更改后是否合法， 与vip数据匹配
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

        ScanInfoBean scanInfoBeanBySQL;

        LogUtil.i(TAG + "查找数据库中对应的扫描信息，id = " + scanInfoBean.getId());
        // 先判断库中是否存在此条扫描
        try {
            List<ScanInfoBean> scanInfoBeansByUrl =
                    DaoAgent.selectSQLForBean(
                            ScanInfoBean.newInstanceById(scanInfoBean.getId())
                    );
            if (scanInfoBeansByUrl.size() == 0) {
                throw new SQLException();
            }
            scanInfoBeanBySQL =  scanInfoBeansByUrl.get(0);
        } catch (SQLException e) {
            LogUtil.i(TAG + "失败， 未找到要修改的扫描");
            return ResultBean.REQUEST_ERRO_SCAN_UPADTA_NOT_FOUND;
        }
        LogUtil.i(TAG + "查找成功");
        try {
            boolean isSuc = DaoAgent.updataBeanForSQL(scanInfoBean, scanInfoBeanBySQL);
            if (!isSuc) {
                throw new SQLException();
            }
        } catch (SQLException e) {
            LogUtil.e(TAG + "失败， 更新数据库失败", e);
            return ResultBean.REQUEST_ERRO_SQL;
        }


        return ResultBean.createSucInfo("");
    }
}
