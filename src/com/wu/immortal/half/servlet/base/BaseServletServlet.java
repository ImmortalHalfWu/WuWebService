package com.wu.immortal.half.servlet.base;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.beans.ServletLogBean;
import com.wu.immortal.half.jsons.JsonWorkImpl;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

        ServletLogBean servletLogBean = new ServletLogBean();
//        JsonObject requestInfoJsonObject = RequestUtil.getRequestInfo(request);
        servletLogBean.setRequestInfo(RequestUtil.getRequestInfo(request).toString());

        TokenInfoBean tokenInfoBean = null;

        if (needAuthToken()) {
            tokenInfoBean = JwtUtil.authToken(request, response);
            if (tokenInfoBean == null) {
                LogUtil.e(servletLogBean);
                return;
            }
        }

        servletLogBean.setTokenInfoBean(tokenInfoBean);

        // 获取json数据
        String requestBody = RequestUtil.getRequestBody(request);

        if (FinalUtil.checkNull(requestBody)) {
            // 请求体空异常
            callBackResult(ResultBean.REQUEST_ERRO_NULL_BODY, response);
            LogUtil.e(servletLogBean.toString());
            return;
        }

        try {

            servletLogBean.setRequestBody(requestBody);
            ResultBean.ResultInfo resultBean = post(tokenInfoBean, requestBody, JsonWorkImpl.newInstance());
            callBackResult(resultBean, response);
            LogUtil.i(servletLogBean.toString());

        } catch (Exception e) {
            LogUtil.e(servletLogBean.toString(), e);
            // 子类异常，统一回复
            callBackResult(ResultBean.REQUEST_ERRO_SERVER, response);
        }

    }

    /**
     * @param tokenInfoBean token数据，包括账号，userid
     * @return 返回的数据原样推给前端
     */
    protected abstract @Nullable
    ResultBean.ResultInfo post(
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
            @NotNull ResultBean.ResultInfo resultBeanEnum,
            @NotNull HttpServletResponse response) throws IOException {

        RequestUtil.callBackResult(resultBeanEnum, response, JsonWorkImpl.newInstance());

    }
}
