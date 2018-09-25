package com.wu.immortal.half.works;

import com.wu.immortal.half.works.impls.WorkerFactory;
import com.wu.immortal.half.works.interfaces.WorkInterface;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 周期性执行任务：
 * 1. 支付的token， 5天 = 120小时一次
 * 2, sqlconnect 5小时一次
 */
public class WorkPoolUtil {

    private volatile static WorkPoolUtil threadPoolUtil;

    private ScheduledExecutorService scheduledExecutorService;
    private WorkerFactory workerFactory;

    private WorkPoolUtil() {
        initWorks();
        initThreadPool();
        startWork();
    }

    private void startWork() {
        WorkInterface[] allWorks = workerFactory.getAllWorks();
        for (WorkInterface worker : allWorks) {
            scheduledExecutorService.scheduleWithFixedDelay(
                    worker,
                    worker.getFirstRunTimeDelay(),
                    worker.getDelay(),
                    worker.getTimeUnit()
            );
        }
    }

    private void initWorks() {
        workerFactory = WorkerFactory.getInstance();
    }

    private void initThreadPool() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    }

    public static WorkPoolUtil init() {
        if (threadPoolUtil == null) {
            synchronized (WorkPoolUtil.class) {
                threadPoolUtil = new WorkPoolUtil();
            }
        }
        return threadPoolUtil;
    }


    public void release() {
        if (scheduledExecutorService != null) {
            if (!scheduledExecutorService.isShutdown()) {
                scheduledExecutorService.shutdown();
            }
            scheduledExecutorService = null;
        }
        if (workerFactory != null) {
            workerFactory.release();
            workerFactory = null;
        }
        threadPoolUtil = null;
    }
}
