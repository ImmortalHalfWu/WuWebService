package com.wu.immortal.half.servlet;

import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.ScanInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.utils.LogUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_ORDINARY;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SENIOR;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SUPER;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_ORDINARY;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_SENIOR;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_SUPER;


@WebServlet(name = "ScanAddServlet")
public class ScanAddServlet extends BaseServletServlet {

    private static final String TAG = "添加扫描";

    @Override
    protected ResultBean.ResultInfo post(TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        LogUtil.i(TAG + requestBody);

        ScanInfoBean scanInfoBean = gson.jsonToBean(requestBody, ScanInfoBean.class);
        if (scanInfoBean.checkNull()) {
            LogUtil.e(TAG + "失败，数据不完整");
            return ResultBean.REQUEST_ERRO_JSON;
        }

        UserVipInfoBean userVipInfoBean = UserVipInfoBean.newInstanceByUserId(tokenInfoBean.getUserId());
        try {
            List<UserVipInfoBean> userVipInfoBeans = DaoAgent.selectSQLForBean(userVipInfoBean);
            if (userVipInfoBeans.size() == 0) {
                LogUtil.e(TAG + "失败， 未找到用户的VIP数据" + tokenInfoBean.toString());
                throw new SQLException();
            }
            userVipInfoBean = userVipInfoBeans.get(0);
        } catch (SQLException e) {
            LogUtil.e(e);
            return ResultBean.REQUEST_ERRO_TOKEN_ILLEGAL;
        }

        LogUtil.i(TAG + "开始匹配用户信息与扫描信息是否匹配");

        VIP_TYPE vipTypeEnum = userVipInfoBean.getVipTypeEnum();

        // 是否可用
        scanInfoBean.setCanUser(!(
                vipTypeEnum == VIP_TYPE_ORDINARY && scanInfoBean.getFrequency() != SCAN_FREQUENCY_ORDINARY
                        || vipTypeEnum == VIP_TYPE_SENIOR && scanInfoBean.getFrequency() != SCAN_FREQUENCY_SENIOR
                        || vipTypeEnum == VIP_TYPE_SUPER && scanInfoBean.getFrequency() != SCAN_FREQUENCY_SUPER
        ));


        LogUtil.i(TAG + "匹配成功, 插入数据库");

        scanInfoBean.setUserId(tokenInfoBean.getUserId());

        try {
            boolean isSuc = DaoAgent.insertBeanToSQL(scanInfoBean);
            if (!isSuc) {
                throw new SQLException();
            }

            List<ScanInfoBean> scanInfoBeans = DaoAgent.selectSQLForBean(scanInfoBean);
            scanInfoBean = scanInfoBeans.get(0);
            LogUtil.i(TAG + " 插入数据库成功, 完成添加");
            return ResultBean.createSucInfo(gson.toJsonElement(scanInfoBean));

        } catch (SQLException e) {
            LogUtil.e(TAG + "扫描数据插入失败，" + scanInfoBean.toString());
            return ResultBean.REQUEST_ERRO_SQL;
        }

    }

}
