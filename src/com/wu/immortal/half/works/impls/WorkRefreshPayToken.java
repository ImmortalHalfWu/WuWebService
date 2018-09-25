package com.wu.immortal.half.works.impls;

import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.utils.PayUtil;
import com.wu.immortal.half.works.abstracts.AbsWorker;
import com.wu.immortal.half.works.interfaces.WorkInterface;

import java.util.concurrent.TimeUnit;

class WorkRefreshPayToken extends AbsWorker {


    private WorkRefreshPayToken(long firstRunDelay, long runDelay, TimeUnit timeUnit) {
        super(firstRunDelay, runDelay, timeUnit);
    }

    public static WorkInterface newInstance(long firstRunDelay, long runDelay, TimeUnit timeUnit) {
        return new WorkRefreshPayToken(firstRunDelay, runDelay, timeUnit);
    }

    @Override
    protected void doWork() {
        LogUtil.i("WorkRefreshPayToken do work 3ccd959026f23b25a81bafdd2c76b810");
        PayUtil.getInstance().refreshToken();
    }
}
