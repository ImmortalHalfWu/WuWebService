package com.wu.immortal.half.servlet;

import com.google.gson.Gson;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.servlet.base.BaseServletServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet")
public class LogoutServlet extends BaseServletServlet {
    @Override
    protected String post(TokenInfoBean tokenInfoBean, String requestBody, Gson gson) throws ServletException, IOException {
        return null;
    }
}
