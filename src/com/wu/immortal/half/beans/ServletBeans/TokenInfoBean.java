package com.wu.immortal.half.beans.ServletBeans;

import com.wu.immortal.half.utils.FinalUtil;

public class TokenInfoBean {

    private final String token;
    private final String phone;
    private final int userId;
    private final long endMilles;

    public TokenInfoBean(String token, String phone, int userId, long endMilles) {
        this.token = token;
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

    public boolean checkNull() {
        return FinalUtil.checkNull(phone) || userId == 0 || endMilles == 0;
    }

    public String getToken() {
        return token;
    }

    @Override
    public String toString() {
        return "TokenInfoBean{" +
                "token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                ", endMilles=" + endMilles +
                '}';
    }
}

