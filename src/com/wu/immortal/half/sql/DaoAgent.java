package com.wu.immortal.half.sql;

import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.sql.bean.UserVipInfoBean;
import com.wu.immortal.half.sql.bean.enums.VIP_TYPE;
import com.wu.immortal.half.sql.dao.DaoManager;
import com.wu.immortal.half.utils.DataUtil;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.PasswordEncryption;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;
import java.util.List;

public class DaoAgent {

    public static void init() throws SQLException {
        DaoManager.init();
    }

    public static <T> List<T> selectSQLForBean(T bean) throws SQLException {
        return DaoManager.instance().selectSQLForBean(bean);
    }

    public static boolean insertBeanToSQL(Object bean) throws SQLException {
        return DaoManager.instance().insertBeanToSQL(bean);
    }

    public static boolean deleteBeanForSQL(Object bean) throws SQLException {
        return DaoManager.instance().deleteBeanForSQL(bean);
    }

    public static boolean updataBeanForSQL(Object newBean, Object oldBean) throws SQLException {
        return DaoManager.instance().updataBeanForSQL(newBean, oldBean);
    }

    /**
     * 1. 生成userInfoBean，添加入数据库， 再查处UserId，生成VipInfo，添加UserId，插入数据库，查出VipId，更新userInfo的vipId
     * @param phone 帐号
     * @param passWord 密码
     * @return 是否成功
     * @throws NoSuchAlgorithmException 加密异常
     * @throws InvalidKeySpecException 加密异常
     * @throws SQLException 数据库异常
     */
    public static boolean registerUser(String phone, String passWord)
            throws NoSuchAlgorithmException, InvalidKeySpecException, SQLException {
        LogUtil.i("注册账号：" + phone + "\t" + passWord);
        String salt = PasswordEncryption.generateSalt();
        String encryptedPassword = PasswordEncryption.getEncryptedPassword(passWord, salt);

        UserInfoBean newUserInfoBean = new UserInfoBean(
                null, null, 0,
                phone, DataUtil.getNowTimeToString(),false,encryptedPassword,null,salt
                );

        boolean insertSuc = DaoManager.instance().insertBeanToSQL(newUserInfoBean);
        if (!insertSuc) {
            LogUtil.e("注册账号失败，数据库插入失败");
            return false;
        }
        LogUtil.i("数据库插入新用户成功");

        List<UserInfoBean> userInfoBeans = DaoManager.instance().selectSQLForBean(newUserInfoBean);

        if (userInfoBeans.size() == 0) {
            LogUtil.e("注册账号失败，数据库插入失败, 未获得新用户的id及相关数据");
            return false;
        }

        if (userInfoBeans.size() > 1) {
            LogUtil.e("注册账号失败，数据库异常，手机号多次注册");
            return false;
        }

        newUserInfoBean = userInfoBeans.get(0);

        if (newUserInfoBean.getId() == null || newUserInfoBean.getId() == 0) {
            LogUtil.e("注册账号失败，数据库读入用户id为null");
            return false;
        }

        UserVipInfoBean userVipInfoBean = UserVipInfoBean.newInstanceByVipType(
                null, newUserInfoBean.getId(),
                String.valueOf(DataUtil.getNowTimeToLong()),
                String.valueOf(DataUtil.getDefaultOrdidnaryTimeToLong()),
                VIP_TYPE.VIP_TYPE_ORDINARY
        );

        insertSuc = DaoManager.instance().insertBeanToSQL(userVipInfoBean);
        if (insertSuc) {
            LogUtil.i("注册账号插入VIP数据成功");
        } else {
            LogUtil.i("注册账号插入VIP数据失败");
            return false;
        }

        List<UserVipInfoBean> userVipInfoBeans = DaoManager.instance().selectSQLForBean(userVipInfoBean);
        if (userVipInfoBeans.size() == 0 || userInfoBeans.size() > 1) {
            LogUtil.e("注册账号失败，VIP数据库异常" + userInfoBeans);
            return false;
        }

        userVipInfoBean = userVipInfoBeans.get(0);
        UserInfoBean userInfoBean = UserInfoBean.newInstanceByVipId(userVipInfoBean.getId());

        boolean updataSuc = DaoAgent.updataBeanForSQL(userInfoBean, newUserInfoBean);

        if (updataSuc) {
            LogUtil.i("账号注册流程成功：" + newUserInfoBean + "\n" + userVipInfoBean);
        } else {
            LogUtil.i("账号注册流程失败：" + newUserInfoBean + "\n" + userVipInfoBean);
        }
        return insertSuc;
    }


    /**
     * 数据库中是否存在指定token
     * @param token 指定token
     * @return 数据库中是否存在
     */
    public static boolean hasTokenInSql(String token) throws SQLException {
        return DaoManager.instance().selectSQLForBean(UserInfoBean.newInstanceByToken(token)).size() > 0;
    }
}
