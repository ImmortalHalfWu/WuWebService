package com.wu.immortal.half.beans;

import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;

public class ServletLogBean {

    private String requestInfo;
    private TokenInfoBean tokenInfoBean;
    private String requestBody;

    public String getRequestInfo() {
        return requestInfo;
    }

    public void setRequestInfo(String requestInfo) {
        this.requestInfo = requestInfo;
    }

    public TokenInfoBean getTokenInfoBean() {
        return tokenInfoBean;
    }

    public void setTokenInfoBean(TokenInfoBean tokenInfoBean) {
        this.tokenInfoBean = tokenInfoBean;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    @Override
    public String toString() {
        return "ServletLogBean{" +
                "requestInfo='" + requestInfo + '\'' +
                ", tokenInfoBean=" + tokenInfoBean +
                '}';
    }
}
