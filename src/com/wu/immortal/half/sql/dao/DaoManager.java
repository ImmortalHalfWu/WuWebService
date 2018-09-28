package com.wu.immortal.half.sql.dao;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wu.immortal.half.sql.bean.*;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.sql.dao.impls.ConnectionFactoryImpl;
import com.wu.immortal.half.sql.dao.impls.SQLDaoImpl;
import com.wu.immortal.half.utils.LogUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DaoManager {

    private static DaoManager daoManager;
    private SQLDaoImpl sqlDaoImpl;

    private DaoManager() {
        ConnectionFactoryImpl.instance();
        sqlDaoImpl = SQLDaoImpl.getInstance();
        LogUtil.i("DaoManager初始化完成");
    }

    public static void init() {
        if (daoManager == null) {
            synchronized (DaoManager.class) {
                daoManager = new DaoManager();
            }
        }
    }

    public static @Nullable DaoManager instance() {
        return daoManager;
    }

    public <T> List<T> selectSQLForBean(T bean) throws SQLException {
        ArrayList<T> objects = new ArrayList<>();
        try {
            JSONArray jsonArray = sqlDaoImpl.selectFromSQL(
                    createConnection(),
                    findTableNameByBean(bean),
                    bean);

            if (jsonArray.isEmpty()) {
                objects.trimToSize();
                return objects;
            }

            for (int i = 0, size = jsonArray.size(); i < size; i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                objects.add(JSON.parseObject(jsonObject.toJSONString(),  (Class<T>) bean.getClass()));
            }
            objects.trimToSize();
            return objects;
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean insertBeanToSQL(Object bean) throws SQLException {
        try {
            sqlDaoImpl.addToSQL(createConnection(), findTableNameByBean(bean), bean);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean deleteBeanForSQL(Object bean) throws SQLException {
        try {
            sqlDaoImpl.deleteFromSQL(createConnection(), findTableNameByBean(bean), bean);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public boolean updataBeanForSQL(Object newBean, Object oldBean) throws SQLException {
        try {
            sqlDaoImpl.updataToSQL(createConnection(), findTableNameByBean(oldBean), newBean, oldBean);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    private static @Nullable String findTableNameByBean(Object bean) {

        Class<?> aClass = bean.getClass();
        if (aClass.equals(UserInfoBean.class)) {
            return "user_info";
        }
        if (aClass.equals(UserVipInfoBean.class)) {
            return "vip_info";
        }
        if (aClass.equals(DocumentaryInfoBean.class)) {
            return "documentary_info";
        }
        if (aClass.equals(HistoricalRecordsBean.class)) {
            return "historical_records";
        }
        if (aClass.equals(ScanInfoBean.class)) {
            return "scan_info";
        }
        if (aClass.equals(TestUserBean.class)) {
            return "login_user";
        }
        if (aClass.equals(PayQRcodeBean.class)) {
            return "qrcode";
        }
        if (aClass.equals(PayInfoBean.class)) {
            return "pay_info";
        }

        return null;

    }

    private Connection createConnection() {
        Connection connection;
            try {
                while ((connection = ConnectionFactoryImpl.instance().createConnection()).isClosed());
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        return connection;
    }

    public void release() {
        LogUtil.i("DaoManager释放");
        sqlDaoImpl = null;
        ConnectionFactoryImpl.instance().release();
        LogUtil.i("DaoManager释放完成");
    }
}
