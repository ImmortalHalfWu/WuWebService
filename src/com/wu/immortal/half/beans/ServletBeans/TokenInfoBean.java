package com.wu.immortal.half.beans.ServletBeans;

import com.wu.immortal.half.utils.FinalUtil;

public class TokenInfoBean {

    private final String token;
    private final String phone;
    private final int userId;
    private final long endMilles;
    private final long issuedAtClaims;
    private final long issuedAtToken;

    /**
     * @param token  token字符串
     * @param phone 账号
     * @param userId 数据库id
     * @param endMilles 有效期至
     * @param issuedAtClaims 签发时间
     * @param issuedAtToken 签发时间
     */
    public TokenInfoBean(String token, String phone, int userId, long endMilles, long issuedAtClaims, long issuedAtToken) {
        this.token = token;
        this.phone = phone;
        this.userId = userId;
        this.endMilles = endMilles;
        this.issuedAtToken = issuedAtToken;
        this.issuedAtClaims = issuedAtClaims;
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
        return FinalUtil.checkNull(phone) || userId == 0 || endMilles == 0 || issuedAtClaims == 0 || issuedAtToken == 0;
    }

    public String getToken() {
        return token;
    }

    public long getIssuedAtClaims() {
        return issuedAtClaims;
    }

    public long getIssuedAtToken() {
        return issuedAtToken;
    }

    @Override
    public String toString() {
        return "TokenInfoBean{" +
                "token='" + token + '\'' +
                ", phone='" + phone + '\'' +
                ", userId=" + userId +
                ", endMilles=" + endMilles +
                ", issuedAtClaims=" + issuedAtClaims +
                ", issuedAtToken=" + issuedAtToken +
                '}';
    }
}

