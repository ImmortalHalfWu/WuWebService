package com.wu.immortal.half.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.servlet.base.BaseServletServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends BaseServletServlet {

    @Override
    protected String post(TokenInfoBean tokenInfoBean, String requestBody, Gson gson) throws ServletException, IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("post_login","Success");
        return jsonObject.toString();
    }
}
