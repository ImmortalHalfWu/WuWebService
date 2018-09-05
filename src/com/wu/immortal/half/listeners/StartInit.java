package com.wu.immortal.half.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class StartInit implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        // todo 初始化
        System.out.println("contextInitialized");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
