package com.wu.immortal.half.servlet.base;

import com.google.gson.Gson;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBeanEnum;
import com.wu.immortal.half.beans.ServletBeans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.jsons.JsonWorkImpl;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.utils.RequestUtil;
import com.wu.immortal.half.utils.DataUtil;
import com.wu.immortal.half.utils.FinalString;
import com.wu.immortal.half.utils.JwtUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

/**
 *  统一使用post请求， Token添加在header中
 * 1，完成token的认证
 * 2，Token 中 UserName 及 UserId 的获取
 * 3，请求json的获取并下发给子类， 将子类返回数据push到前端
 */
@WebServlet(name = "BaseServletServlet")
public abstract class BaseServletServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TokenInfoBean tokenInfoBean = null;

        if (needAuthToken()) {
            tokenInfoBean = JwtUtil.authToken(request, response);
            if (tokenInfoBean == null) {
                return;
            }
        }

        // 获取json数据
        String requestBody = RequestUtil.getRequestBody(request);

        if (FinalString.checkNull(requestBody)) {
            // todo 请求体空异常
            callBackResult(ResultBeanEnum.REQUEST_ERRO_NULL_BODY, response);
            return;
        }

        try {

            ResultBeanEnum resultBeanEnum = post(tokenInfoBean, requestBody, JsonWorkImpl.newInstance());
            callBackResult(resultBeanEnum, response);

        } catch (Exception e) {
            e.printStackTrace();
            // todo 子类异常，统一回复
            callBackResult(ResultBeanEnum.REQUEST_ERRO_SERVER, response);
        }

    }

    /**
     * @param tokenInfoBean token数据，包括账号，userid
     * @return 返回的数据原样推给前端
     */
    protected abstract @Nullable
    ResultBeanEnum post(
            @Nullable TokenInfoBean tokenInfoBean,
            @NotNull String requestBody,
            @NotNull JsonWorkInterface gson) throws ServletException, IOException;

    /**
     * @return 是否需要认证token有效性
     */
    protected boolean needAuthToken() {
        return true;
    }

    private void callBackResult(
            @NotNull ResultBeanEnum resultBeanEnum,
            @NotNull HttpServletResponse response) throws IOException {

        RequestUtil.callBackResult(resultBeanEnum, response, JsonWorkImpl.newInstance());

    }
}
