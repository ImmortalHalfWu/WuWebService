package com.wu.immortal.half.listeners;

import com.wu.immortal.half.jsons.JsonWorkImpl;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.dao.DaoManager;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.SMSUtil;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LogUtil.init();
        LogUtil.i("服务器初始化......");
        // 初始化
        try {
            DaoAgent.init();
            JsonWorkImpl.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("初始化失败", e);
        }
        LogUtil.i("服务器初始化完成");

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DaoManager.instance().release();
        LogUtil.i("服务器停止");
    }
}
