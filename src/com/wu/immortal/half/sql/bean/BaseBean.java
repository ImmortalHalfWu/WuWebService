package com.wu.immortal.half.sql.bean;

public class BaseBean {

    private Integer id;
    private Integer userId;

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

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
