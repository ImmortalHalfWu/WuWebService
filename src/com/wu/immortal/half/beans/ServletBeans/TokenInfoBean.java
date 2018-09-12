package com.wu.immortal.half.beans.ServletBeans;

import com.wu.immortal.half.utils.FinalUtil;

public class TokenInfoBean {

    private final String token;
    private final String phone;
    private final int userId;
    private final long endMilles;
    private final String issuerInToken;
    private final String issuerInClaim;


    /**
     * @param token  token字符串
     * @param phone 账号
     * @param userId 数据库id
     * @param endMilles 有效期至
     * @param issuerInClaim
     */
    public TokenInfoBean(String token, String phone, int userId, long endMilles, String issuerInToken, String issuerInClaim) {
        this.token = token;
        this.phone = phone;
        this.userId = userId;
        this.endMilles = endMilles;
        this.issuerInToken = issuerInToken;
        this.issuerInClaim = issuerInClaim;
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
        return FinalUtil.checkNull(phone)
                || userId == 0
                || endMilles == 0
                || FinalUtil.checkNull(issuerInToken)
                || FinalUtil.checkNull(issuerInClaim);
    }

    public String getToken() {
        return token;
    }

    public String getIssuerInToken() {
        return issuerInToken;
    }

    public String getIssuerInClaim() {
        return issuerInClaim;
    }

    @Override
    public String toString() {
        return "TokenInfoBean{" +
                "token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                ", endMilles=" + endMilles +
                ", issuerInToken='" + issuerInToken + '\'' +
                ", issuerInClaim='" + issuerInClaim + '\'' +
                '}';
    }
}

