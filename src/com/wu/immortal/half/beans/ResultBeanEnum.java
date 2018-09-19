package com.wu.immortal.half.beans;

import com.sun.istack.internal.NotNull;
import com.wu.immortal.half.utils.FinalUtil;

public enum ResultBeanEnum {

    REQUEST_SUCCESS(FinalUtil.REQUEST_SUCCESS, "success"),
    REQUEST_ERRO_TOKEN_END_TIME(FinalUtil.REQUEST_ERRO_TOKEN_END_TIME, "身份过期"),
    REQUEST_ERRO_NULL_BODY(FinalUtil.REQUEST_ERRO_NULL_BODY, "请求参数异常"),
    REQUEST_ERRO_SERVER(FinalUtil.REQUEST_ERRO_SERVER, "服务器异常"),
    REQUEST_ERRO_TOKEN_ILLEGAL(FinalUtil.REQUEST_ERRO_TOKEN_ILLEGAL, "身份验证失败");


    private int code;
    private String msg;
    private String resultJsonBody = "";

    ResultBeanEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public String getResultJsonBody() {
        return resultJsonBody;
    }

    public void setResultJsonBody(@NotNull String resultJsonBody) {
        this.resultJsonBody = resultJsonBody == null ? "" : resultJsonBody;
    }
}
