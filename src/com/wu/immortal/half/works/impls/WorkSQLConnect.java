package com.wu.immortal.half.works.impls;

import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.utils.LogUtil;
import com.wu.immortal.half.works.abstracts.AbsWorker;

import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

class WorkSQLConnect extends AbsWorker {

    private WorkSQLConnect(long firstRunDelay, long runDelay, TimeUnit timeUnit) {
        super(firstRunDelay, runDelay, timeUnit);
    }

    static WorkSQLConnect newInstance(long firstRunDelay, long runDelay, TimeUnit timeUnit) {
        return new WorkSQLConnect(firstRunDelay, runDelay, timeUnit);
    }

    @Override
    protected void doWork() {
        LogUtil.i("WorkSQLConnect do work");
        try {
            DaoAgent.selectSQLForBean(UserInfoBean.newInstanceByPhone("13613571331"));
            LogUtil.i("定时刷新数据库连接成功！");
        } catch (SQLException e) {
            e.printStackTrace();
            LogUtil.e("定时刷新数据库连接失败！", e);
        }
    }
}
