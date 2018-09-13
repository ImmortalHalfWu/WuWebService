package com.wu.immortal.half.beans;

import com.wu.immortal.half.utils.FinalUtil;

public class ResultBean {

    public static final ResultInfo REQUEST_SUCCESS = new ResultInfo(FinalUtil.REQUEST_SUCCESS, "success");
    public static final ResultInfo REQUEST_ERRO_TOKEN_END_TIME = new ResultInfo(FinalUtil.REQUEST_ERRO_TOKEN_END_TIME, "身份过期");
    public static final ResultInfo REQUEST_ERRO_NULL_BODY = new ResultInfo(FinalUtil.REQUEST_ERRO_NULL_BODY, "请求参数异常");
    public static final ResultInfo REQUEST_ERRO_SERVER = new ResultInfo(FinalUtil.REQUEST_ERRO_SERVER, "服务器异常");
    public static final ResultInfo REQUEST_ERRO_TOKEN_ILLEGAL = new ResultInfo(FinalUtil.REQUEST_ERRO_TOKEN_ILLEGAL, "身份验证失败");
    public static final ResultInfo REQUEST_ERRO_REGIST_INFO = new ResultInfo(FinalUtil.REQUEST_ERRO_REGIST_INFO, "账号密码无效");
    public static final ResultInfo REQUEST_ERRO_REGIST_PASSWORD = new ResultInfo(FinalUtil.REQUEST_ERRO_REGIST_PASSWORD, "密码格式异常");
    public static final ResultInfo REQUEST_ERRO_REGIST_IS_REGIST = new ResultInfo(FinalUtil.REQUEST_ERRO_REGIST_IS_REGIST, "手机号已注册");
    public static final ResultInfo REQUEST_ERRO_JSON = new ResultInfo(FinalUtil.REQUEST_ERRO_JSON, "数据格式异常");
    public static final ResultInfo REQUEST_ERRO_SQL = new ResultInfo(FinalUtil.REQUEST_ERRO_SQL, "数据库异常");
    public static final ResultInfo REQUEST_ERRO_NOT_FOUND_PHONE = new ResultInfo(FinalUtil.REQUEST_ERRO_NOT_FOUND_PHONE, "用户尚未注册");
    public static final ResultInfo REQUEST_ERRO_PASSWORD = new ResultInfo(FinalUtil.REQUEST_ERRO_PASSWORD, "用户名或密码错误");
    public static final ResultInfo REQUEST_ERRO_SMS_FAIL = new ResultInfo(FinalUtil.REQUEST_ERRO_SMS_FAIL, "发送短信验证码失败");
    public static final ResultInfo REQUEST_ERRO_SMS_PHONE = new ResultInfo(FinalUtil.REQUEST_ERRO_SMS_PHONE, "手机号格式错误");
    public static final ResultInfo REQUEST_ERRO_SCAN_ADD_REPEAT = new ResultInfo(FinalUtil.REQUEST_ERRO_SCAN_ADD_REPEAT, "此网址已经在扫描列表啦");
    public static final ResultInfo REQUEST_ERRO_SCAN_UPADTA_NOT_FOUND = new ResultInfo(FinalUtil.REQUEST_ERRO_SCAN_UPADTA_NOT_FOUND, "未找到此扫描信息");




    public static ResultInfo createSucInfo(Object jsonBody) {
        ResultInfo success = new ResultInfo(FinalUtil.REQUEST_SUCCESS, "success");
        success.setJsonBody(jsonBody);
        return success;
    }

    public static class ResultInfo {

        private final String msg;
        private final int code;
        private Object jsonBody;

        ResultInfo(int code, String msg) {
            this.msg = msg;
            this.code = code;
            jsonBody = "";
        }

        public String getMsg() {
            return msg;
        }

        public int getCode() {
            return code;
        }

        public Object getJsonBody() {
            return jsonBody;
        }

        public void setJsonBody(Object jsonBody) {
            this.jsonBody = jsonBody;
        }
    }
}
