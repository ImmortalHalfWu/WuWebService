package com.wu.immortal.half.sql.bean;

public class BaseBean {

    private final Integer id;
    private final Integer userId;

    public BaseBean(Integer id, Integer userId) {
        this.id = id;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public Integer getUserId() {
        return userId;
    }
}
