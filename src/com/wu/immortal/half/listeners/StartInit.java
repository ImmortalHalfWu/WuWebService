package com.wu.immortal.half.listeners;

import com.wu.immortal.half.jsons.JsonWorkImpl;
import com.wu.immortal.half.sql.dao.DaoManager;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

public class StartInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // todo 初始化
        try {
            DaoManager.init();
            JsonWorkImpl.newInstance();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        DaoManager.instance().release();
    }
}
