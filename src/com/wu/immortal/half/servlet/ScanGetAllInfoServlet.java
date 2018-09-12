package com.wu.immortal.half.servlet;

import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "ScanGetAllInfoServlet")
public class ScanGetAllInfoServlet extends BaseServletServlet {
    @Override
    protected ResultBean.ResultInfo post(TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {
        return null;
    }
}
