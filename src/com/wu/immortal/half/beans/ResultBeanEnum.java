package com.wu.immortal.half.beans;

import com.wu.immortal.half.utils.FinalString;

public enum ResultBeanEnum {

//    public static final int REQUEST_SUCCESS = 10000;                // 请求成功
//    public static final int REQUEST_ERRO_TOKEN_ILLEGAL = 10002;     // token为null或非法
//    public static final int REQUEST_ERRO_TOKEN_END_TIME = 10001;    // token 超时
//    public static final int REQUEST_ERRO_NULL_BODY = 10003;         // body参数null
//    public static final int REQUEST_ERRO_SERVER = 10004;            // 服务器异常，各子类查找时异常

    REQUEST_SUCCESS(FinalString.REQUEST_SUCCESS, "success"),
    REQUEST_ERRO_TOKEN_END_TIME(FinalString.REQUEST_ERRO_TOKEN_END_TIME, "身份过期"),
    REQUEST_ERRO_NULL_BODY(FinalString.REQUEST_ERRO_NULL_BODY, "请求参数异常"),
    REQUEST_ERRO_SERVER(FinalString.REQUEST_ERRO_SERVER, "服务器异常"),
    REQUEST_ERRO_TOKEN_ILLEGAL(FinalString.REQUEST_ERRO_TOKEN_ILLEGAL, "身份验证失败");


    private int code;
    private String msg;

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
}
