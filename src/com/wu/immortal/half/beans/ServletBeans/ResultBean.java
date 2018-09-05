package com.wu.immortal.half.beans.ServletBeans;

import java.sql.ResultSet;

public class ResultBean {

    private int code;
    private String message;

    private String body;

    private ResultBean(int code, String message, String body) {
        this.code = code;
        this.message = message;
        this.body = body;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public static ResultBean newInstance(int code, String msg) {
        return newInstance(code, msg, null);
    }

    public static ResultBean newInstance(int code, String msg, String body) {
        return new ResultBean(code, msg, body);
    }

}
