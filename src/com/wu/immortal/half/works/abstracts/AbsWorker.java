package com.wu.immortal.half.works.abstracts;

import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.works.interfaces.WorkInterface;

import java.util.concurrent.TimeUnit;

public abstract class AbsWorker implements WorkInterface {

    private volatile boolean canRun = true;

    private final long firstRunDelay;
    private final long runDelay;
    private final TimeUnit timeUnit;

    public AbsWorker(long firstRunDelay, long runDelay, TimeUnit timeUnit) {
        this.firstRunDelay = firstRunDelay;
        this.runDelay = runDelay;
        this.timeUnit = timeUnit;
    }

    @Override
    public long getFirstRunTimeDelay() {
        return firstRunDelay;
    }

    @Override
    public long getDelay() {
        return runDelay;
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    @Override
    public void run() {
        if (!canRun) {
            return;
        }
        try {
            doWork();
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.e("定时循环任务异常: class = " + getClass().getName() + "__ e : " + e.getMessage());
        }
    }

    @Override
    public void stop() {
        canRun = false;
    }

    protected abstract void doWork();
}
