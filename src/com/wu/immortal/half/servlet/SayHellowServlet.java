package com.wu.immortal.half.servlet;


import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.servlet.base.BaseServletServlet;
import com.wu.immortal.half.sql.bean.UserInfoBean;

import javax.servlet.ServletException;
import java.io.IOException;

@javax.servlet.annotation.WebServlet(name = "SayHellowServlet")
public class SayHellowServlet extends BaseServletServlet {

    @Override
    protected ResultBean.ResultInfo post(@Nullable UserInfoBean userInfoBeann, TokenInfoBean tokenInfoBean, String requestBody, JsonWorkInterface gson) throws ServletException, IOException {
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("requestType","POST");
//        byte[] bytes = gson.toJson(jsonObject).getBytes(StandardCharsets.UTF_8);
//        response.setStatus(HttpServletResponse.SC_OK);
//        ServletOutputStream outputStream = response.getOutputStream();
//        outputStream.write(bytes);
//        outputStream.flush();
//        outputStream.close();
        return null;
    }
}
