package com.wu.immortal.half.works.impls;

import com.wu.immortal.half.configs.ApplicationConfig;
import com.wu.immortal.half.works.interfaces.WorkInterface;

import java.util.concurrent.TimeUnit;

public class WorkerFactory {

    private static WorkerFactory workerFactory;
    private WorkInterface[] allWorker;
    private static final TimeUnit timeUnit = TimeUnit.HOURS;

    private WorkerFactory() {
    }

    public static WorkerFactory getInstance() {
        if (workerFactory == null) {
            synchronized (WorkerFactory.class) {
                workerFactory = new WorkerFactory();
            }
        }
        return workerFactory;
    }

    private WorkInterface createRefreshPayToken() {
        long workerRefreshPayTokenDelay = ApplicationConfig.instance().getWorkerRefreshPayTokenDelay();
        return WorkRefreshPayToken.newInstance(
                workerRefreshPayTokenDelay,
                workerRefreshPayTokenDelay,
                timeUnit);
    }


    private WorkInterface createRefreshSqlConnect() {
        long workerSqlRefreshDelay = ApplicationConfig.instance().getWorkerSqlRefreshDelay();
        return WorkSQLConnect.newInstance(
                workerSqlRefreshDelay,
                workerSqlRefreshDelay,
                timeUnit);
    }

    public WorkInterface[] getAllWorks() {
        return allWorker == null ? allWorker = new WorkInterface[]{
                createRefreshPayToken(),
                createRefreshSqlConnect()
        } : allWorker;
    }

    public void release() {
        if (allWorker != null && allWorker.length > 0) {
            for(WorkInterface workInterface : allWorker) {
                workInterface.stop();
            }
        }
        allWorker = null;
        workerFactory = null;
    }
}
