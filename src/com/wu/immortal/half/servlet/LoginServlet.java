package com.wu.immortal.half.servlet;

import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ResultBeanEnum;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends BaseServletServlet {
    @Override
    protected ResultBean.ResultInfo post(TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {

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
