package com.wu.immortal.half.works.interfaces;

import java.util.concurrent.TimeUnit;

/**
 * 定时任务接口
 */
public interface WorkInterface extends Runnable{

    /**
     * 首次运行任务的延时时间
     * @return 时间
     */
    long getFirstRunTimeDelay();

    /**
     * 首次之后周期运行延时
     * @return 时间
     */
    long getDelay();

    /**
     * 时间单位
     * @return 时间单位
     */
    TimeUnit getTimeUnit();

    /**
     * 停止任务
     */
    void stop();

}
