package com.wu.immortal.half.sql.bean;

public class TokenInfoBean {

    private final String phone;
    private final int userId;
    private final long endMilles;

    public TokenInfoBean(String phone, int userId, long endMilles) {
        this.phone = phone;
        this.userId = userId;
        this.endMilles = endMilles;
    }

    public String getPhone() {
        return phone;
    }

    public int getUserId() {
        return userId;
    }

    public long getEndMilles() {
        return endMilles;
    }

    @Override
    public String toString() {
        return "TokenInfoBean{" +
                "phone='" + phone + '\'' +
                ", userId=" + userId +
                ", endMilles=" + endMilles +
                '}';
    }
}

