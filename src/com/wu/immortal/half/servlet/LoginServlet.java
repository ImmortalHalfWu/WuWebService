package com.wu.immortal.half.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wu.immortal.half.beans.ResultBeanEnum;
import com.wu.immortal.half.beans.ServletBeans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkImpl;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.utils.DataUtil;
import com.wu.immortal.half.utils.FinalString;
import com.wu.immortal.half.utils.JwtUtil;
import com.wu.immortal.half.utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends BaseServletServlet {
    @Override
    protected ResultBeanEnum post(TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

        String resultJson = "";
        ResultBeanEnum resultBeanEnum = null;
        // todo 1, 账号密码是否正确，2，返回用户数据+vip数据

        return null;
    }

    @Override
    protected boolean needAuthToken() {
        return false;
    }
}
