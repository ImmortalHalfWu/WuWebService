package com.wu.immortal.half.utils;

public class FinalUtil {

    public static final String HEADER_KEY_ACCESS = "Access";

    public static final int REQUEST_SUCCESS = 10000;                // 请求成功
    public static final int REQUEST_ERRO_TOKEN_ILLEGAL = 10002;     // token为null或非法
    public static final int REQUEST_ERRO_TOKEN_END_TIME = 10001;    // token 超时
    public static final int REQUEST_ERRO_NULL_BODY = 10003;         // body参数null
    public static final int REQUEST_ERRO_SERVER = 10004;            // 服务器异常，各子类查找时异常








    public static boolean checkNull(String text) {
        return text == null || text.length() == 0;
    }
}
