package com.wu.immortal.half.utils;

import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.DocumentaryInfoBean;
import com.wu.immortal.half.sql.bean.ScanInfoBean;
import com.wu.immortal.half.sql.bean.enums.DOCUMENTARY_TYPE;
import com.wu.immortal.half.sql.bean.enums.ORDER_TYPE;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;

import java.sql.SQLException;
import java.util.List;

import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_ORDINARY;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SENIOR;
import static com.wu.immortal.half.sql.bean.enums.VIP_TYPE.VIP_TYPE_SUPER;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_ORDINARY;
import static com.wu.immortal.half.utils.FinalUtil.SCAN_FREQUENCY_SUPER;

/**
 * VIP 对应权限判断的工具类
 */
public class VIPWithAuthorityUtil {

    /**
     * // 指定监听的交易类型，普通只能买或卖， 高级超级随意
     * // 指定扫描频率，普通只能30s/次， 高级5s，超级1s
     * // 指定跟单类型，普通不能添加跟单， 高级用户只能闪电跟单，超级用户随意
     * 验证一个扫描的配置数据是否会用户会员等级匹配
     *
     * @param vipTypeEnum   用户会员类型
     * @param orderTypeEnum 扫描指定的交易类型
     * @param frequency     扫描频率
     * @return 扫描是否合法
     */
    private static boolean authScanInfo(VIP_TYPE vipTypeEnum, ORDER_TYPE orderTypeEnum, Integer frequency) {
        if (vipTypeEnum == VIP_TYPE_ORDINARY) {
            return
                    orderTypeEnum != ORDER_TYPE.ORDER_TYPE_ALL  // 交易类型
                            && frequency == SCAN_FREQUENCY_ORDINARY     // 扫描频率
                    ;
        }
        if (vipTypeEnum == VIP_TYPE_SENIOR) {
            return
                    // 交易类型orderTypeEnum != ORDER_TYPE.ORDER_TYPE_ALL &&
                    frequency != SCAN_FREQUENCY_SUPER    // 扫描频率
                    ;
        }
        return vipTypeEnum == VIP_TYPE_SUPER;

    }

    public static boolean authScanInfo(VIP_TYPE vipTypeEnum, ScanInfoBean scanInfoBean) {
        // 指定监听的交易类型，普通只能买或卖， 高级超级随意
        ORDER_TYPE orderTypeEnum = scanInfoBean.getOrderTypeEnum();
        // 指定扫描频率，普通只能30s/次， 高级5s，超级1s
        Integer frequency = scanInfoBean.getFrequency();
        return authScanInfo(vipTypeEnum, orderTypeEnum, frequency);
    }

    /**
     * 验证一个跟投的配置数据是否会用户会员等级匹配
     *
     * @param vipTypeEnum         用户会员类型
     * @param orderTypeEnum       扫描指定的交易类型
     * @param frequency           扫描频率
     * @param documentaryTypeEnum 跟单类型，快速还是严格
     * @return 是否合法
     */
    private static boolean authDocumentaryInfo(VIP_TYPE vipTypeEnum, ORDER_TYPE orderTypeEnum, Integer frequency, DOCUMENTARY_TYPE documentaryTypeEnum) {

        if (!authScanInfo(vipTypeEnum, orderTypeEnum, frequency)) {
            return false;
        }

        if (vipTypeEnum == VIP_TYPE_ORDINARY) {
            return documentaryTypeEnum == null;             // 禁止跟投
        }

        if (vipTypeEnum == VIP_TYPE_SENIOR) {
            return documentaryTypeEnum != null && documentaryTypeEnum == DOCUMENTARY_TYPE.DOCUMENTARY_TYPE_STRICT; // 跟投类型只能是严格

        }
        return vipTypeEnum == VIP_TYPE_SUPER;
    }

    public static boolean authDocumentaryInfo(VIP_TYPE vipTypeEnum, DocumentaryInfoBean documentaryInfoBean) {
        // 指定监听的交易类型，普通只能买或卖， 高级超级随意
        ORDER_TYPE orderTypeEnum = documentaryInfoBean.getOrderTypeEnum();
        // 指定扫描频率，普通只能30s/次， 高级5s，超级1s
        Integer frequency = documentaryInfoBean.getFrequency();
        // 指定跟单类型，普通不能添加跟单， 高级用户只能闪电跟单，超级用户随意
        DOCUMENTARY_TYPE documentaryTypeEnum = documentaryInfoBean.getDocumentaryTypeEnum();
        return authDocumentaryInfo(vipTypeEnum, orderTypeEnum, frequency, documentaryTypeEnum);
    }


    /**
     * vip信息更改， 则修正对应用户数据库中数据， 屏蔽权限
     * @param toVIPType 目标VIP等级
     * @param userId 用户id
     */
    public static boolean vipInfoChangeToSQL(VIP_TYPE toVIPType, int userId) {

        try {
            // todo 测试
            // 1，修正扫描可用状态，获取所有扫描
            List<ScanInfoBean> scanInfoBeans = DaoAgent.selectSQLForBean(ScanInfoBean.newInstanceByUserId(userId));
            if (scanInfoBeans.size() > 0) {
                // 遍历所有
                ScanInfoBean newScanInfoBean = ScanInfoBean.newInstance();
                for(ScanInfoBean scanInfoBean : scanInfoBeans) {
                    boolean b = authScanInfo(toVIPType, scanInfoBean);
                    // 如果有权限不符的，改为不可用状态
                    if (scanInfoBean.isCanUser() != b) {
                        newScanInfoBean.setCanUser(b);
                        DaoAgent.updataBeanForSQL(newScanInfoBean, scanInfoBean);
                    }
                }
            }
            DocumentaryInfoBean documentaryInfoBean = DocumentaryInfoBean.newInstance();
            documentaryInfoBean.setUserId(userId);

            // 2，修正跟投可用状态
            List<DocumentaryInfoBean> documentaryInfoBeans = DaoAgent.selectSQLForBean(documentaryInfoBean);
            if (documentaryInfoBeans.size() > 0) {
                DocumentaryInfoBean newDocumentaryInfoBean = DocumentaryInfoBean.newInstance();
                // 遍历所有
                for (DocumentaryInfoBean oldDocumentaryInfoBean : documentaryInfoBeans) {
                    boolean b = authDocumentaryInfo(toVIPType, oldDocumentaryInfoBean);
                    if (oldDocumentaryInfoBean.isCanUser() != b) {
                        // 如果有权限不符的，改为不可用状态
                        newDocumentaryInfoBean.setCanUser(b);
                        DaoAgent.updataBeanForSQL(newDocumentaryInfoBean, oldDocumentaryInfoBean);
                    }
                }
            }

        } catch (SQLException e) {
            LogUtil.e("会员状态更改，修正数据库信息失败", e);
            return false;
        }
        return true;
    }

}
