package com.wu.immortal.half.servlet;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wu.immortal.half.beans.ResultBeanEnum;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.utils.FinalString;
import com.wu.immortal.half.utils.RequestUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "RegistServlet")
public class RegistServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String requestBody = RequestUtil.getRequestBody(req);
        if (FinalString.checkNull(requestBody)) {
            // 请求体空异常
            RequestUtil.callBackResult(ResultBeanEnum.REQUEST_ERRO_NULL_BODY, "", resp, new Gson());
            return;
        }

        // todo 解析请求数据并处理， 密码加密，插入数据库， 生成token并返回

    }
}
