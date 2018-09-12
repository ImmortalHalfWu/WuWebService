package com.wu.immortal.half.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FinalUtil {

    public static final String HEADER_KEY_ACCESS = "Access";

    public static final int REQUEST_SUCCESS = 10000;                // 请求成功
    public static final int REQUEST_ERRO_TOKEN_ILLEGAL = 10002;     // token为null或非法
    public static final int REQUEST_ERRO_TOKEN_END_TIME = 10001;    // token 超时
    public static final int REQUEST_ERRO_NULL_BODY = 10003;         // body参数null
    public static final int REQUEST_ERRO_SERVER = 10004;            // 服务器异常，各子类查找时异常
    public static final int REQUEST_ERRO_REGIST_INFO = 10005;       // 注册账号， 数据有误，反序列化失败，手机||密码空
    public static final int REQUEST_ERRO_REGIST_PASSWORD = 10008;       // 注册账号，密码格式错误
    public static final int REQUEST_ERRO_REGIST_IS_REGIST = 10006;  // 注册账号， 重复注册
    public static final int REQUEST_ERRO_JSON = 10007;              // json 解析异常
    public static final int REQUEST_ERRO_SQL = 10008;              // 数据库异常
    public static final int REQUEST_ERRO_NOT_FOUND_PHONE = 10009;              // 用户不存在
    public static final int REQUEST_ERRO_PASSWORD = 10010;              // 密码错误
    public static final int REQUEST_ERRO_SMS_FAIL = 10011;          //发送短信验证码失败
    public static final int REQUEST_ERRO_SMS_PHONE = 10012;          //手机号格式错误




    public static final int SCAN_FREQUENCY_ORDINARY = 30;
    public static final int SCAN_FREQUENCY_SENIOR = 5;
    public static final int SCAN_FREQUENCY_SUPER = 1;




    public static boolean checkNull(String text) {
        return text == null || text.length() == 0;
    }

    public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            LogUtil.e(isMatch);
            return isMatch;
        }
    }

}
