package com.wu.immortal.half.servlet.base;

import com.google.gson.JsonSyntaxException;
import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBean;
import com.wu.immortal.half.beans.ServletBeans.TokenInfoBean;
import com.wu.immortal.half.beans.ServletLogBean;
import com.wu.immortal.half.jsons.JsonWorkImpl;
import com.wu.immortal.half.jsons.JsonWorkInterface;
import com.wu.immortal.half.sql.DaoAgent;
import com.wu.immortal.half.sql.bean.UserInfoBean;
import com.wu.immortal.half.utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 *  统一使用post请求， Token添加在header中
 * 1，完成token的认证
 * 2，Token 中 UserName 及 UserId 的获取
 * 3，请求json的获取并下发给子类， 将子类返回数据push到前端
 */
@WebServlet(name = "BaseServletServlet")
public abstract class BaseServletServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LogUtil.i("++++++++++++++++++++++++++++请求接口"+getClass().getSimpleName()+"+++++++++++++++++++++++++++++++++++");
      // 日志数据
        ServletLogBean servletLogBean = new ServletLogBean();
        servletLogBean.setRequestInfo(RequestUtil.getRequestInfo(request).toString());

        TokenInfoBean tokenInfoBean = null;
        UserInfoBean userInfoBean = null;
        // token认证
        if (needAuthToken()) {
            tokenInfoBean = JwtUtil.authToken(request, response);
            if (tokenInfoBean == null) {
                LogUtil.e(servletLogBean);
                return;
            }
            try {
                //  token匹配数据库信息，是否存在
                userInfoBean = DaoAgent.hasTokenInSql(tokenInfoBean.getToken());
                if (userInfoBean == null) {
                    LogUtil.e("Token验证失败，数据库中未找到" + tokenInfoBean.getToken());
                    RequestUtil.callBackResult(ResultBean.REQUEST_ERRO_TOKEN_ILLEGAL, response, JsonWorkImpl.newInstance());
                    return;
                }
                // token 过期
                if (tokenInfoBean.getEndMilles() <= DataUtil.getNowTimeToLong()) {
                    LogUtil.e("Token验证失败，已过期" + tokenInfoBean.getToken());
                    RequestUtil.callBackResult(ResultBean.REQUEST_ERRO_TOKEN_END_TIME, response, JsonWorkImpl.newInstance());
                    return;
                }
            } catch (SQLException e) {
                LogUtil.e("Token验证失败，查找Token失败：" + tokenInfoBean.getToken(), e);
                RequestUtil.callBackResult(ResultBean.REQUEST_ERRO_TOKEN_ILLEGAL, response, JsonWorkImpl.newInstance());
                return;
            }
        }


        servletLogBean.setTokenInfoBean(tokenInfoBean);

        // 获取json数据
        String requestBody = RequestUtil.getRequestBody(request).trim();

        if (needAuthRequestBody() && FinalUtil.checkNull(requestBody)) {
            // 请求体空异常
            callBackResult(ResultBean.REQUEST_ERRO_NULL_BODY, response);
            LogUtil.e(servletLogBean.toString());
            return;
        }

        try {

            servletLogBean.setRequestBody(requestBody);
            ResultBean.ResultInfo resultBean = post(userInfoBean, tokenInfoBean, requestBody, JsonWorkImpl.newInstance());
            callBackResult(resultBean, response);
            LogUtil.i(servletLogBean.toString());

        } catch (JsonSyntaxException e) {
            LogUtil.e(servletLogBean.toString(), e);
            callBackResult(ResultBean.REQUEST_ERRO_JSON, response);
        } catch (Exception e) {
            LogUtil.e(servletLogBean.toString(), e);
            // 子类异常，统一回复
            callBackResult(ResultBean.REQUEST_ERRO_SERVER, response);
        }
        LogUtil.i("-----------------------------"+getClass().getSimpleName()+"----------------------------------");
    }

    /**
     * @param tokenInfoBean token数据，包括账号，userid
     * @return 返回的数据原样推给前端
     */
    protected abstract @Nullable
    ResultBean.ResultInfo post(
            @Nullable UserInfoBean userInfoBean,
            @Nullable TokenInfoBean tokenInfoBean,
            @NotNull String requestBody,
            @NotNull JsonWorkInterface gson) throws ServletException, IOException;

    /**
     * @return 是否需要认证token有效性
     */
    protected boolean needAuthToken() {
        return true;
    }

    /**
     * @return 是否需要验证请求体
     */
    protected boolean needAuthRequestBody() {
        return true;
    }

    private void callBackResult(
            @NotNull ResultBean.ResultInfo resultBeanEnum,
            @NotNull HttpServletResponse response) throws IOException {

        RequestUtil.callBackResult(resultBeanEnum, response, JsonWorkImpl.newInstance());

    }
}
