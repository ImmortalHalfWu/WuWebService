package com.wu.immortal.half.beans;

import com.wu.immortal.half.utils.FinalUtil;

public class ResultBean {

    public static final ResultInfo REQUEST_SUCCESS = new ResultInfo(FinalUtil.REQUEST_SUCCESS, "success");
    public static final ResultInfo REQUEST_ERRO_TOKEN_END_TIME = new ResultInfo(FinalUtil.REQUEST_ERRO_TOKEN_END_TIME, "身份过期");
    public static final ResultInfo REQUEST_ERRO_NULL_BODY = new ResultInfo(FinalUtil.REQUEST_ERRO_NULL_BODY, "请求参数异常");
    public static final ResultInfo REQUEST_ERRO_SERVER = new ResultInfo(FinalUtil.REQUEST_ERRO_SERVER, "服务器异常");
    public static final ResultInfo REQUEST_ERRO_TOKEN_ILLEGAL = new ResultInfo(FinalUtil.REQUEST_ERRO_TOKEN_ILLEGAL, "身份验证失败");

    public static class ResultInfo {

        private final String msg;
        private final int code;
        private String jsonBody;

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

        public String getJsonBody() {
            return jsonBody;
        }

        public void setJsonBody(String jsonBody) {
            this.jsonBody = jsonBody;
        }
    }
}
