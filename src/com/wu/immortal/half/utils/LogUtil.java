package com.wu.immortal.half.utils;

import org.apache.log4j.Logger;

public class LogUtil {

    private static LogUtil logUtil;

    private Logger logger;

    private LogUtil() {

        logger = Logger.getLogger(getClass().getSimpleName());
    }

    public static void init() {
        if (logUtil == null) {
            synchronized (LogUtil.class) {
                logUtil = new LogUtil();
            }
        }
    }

    private void info(Object object) {
        logger.info(object);
    }

    private void warn(Object object) {
        logger.warn(object);
    }

    private void debug(Object object) {
        logger.debug(object);
    }

    private void erro(Object object) {
        logger.error(object);
    }

    private void erro(Object object, Throwable throwable) {
        logger.error(object, throwable);
    }


    public static void i(Object object) {
        logUtil.info(object);
    }

    public static void e(Object object) {
        logUtil.erro(object);
    }

    public static void e(Object object, Throwable throwable) {
        logUtil.erro(object, throwable);
    }

    public static void w(Object object) {
        logUtil.warn(object);
    }

    public static void d(Object object) {
        logUtil.debug(object);
    }

}
