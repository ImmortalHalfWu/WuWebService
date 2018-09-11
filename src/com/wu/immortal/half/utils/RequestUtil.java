package com.wu.immortal.half.utils;

import com.google.gson.JsonObject;
import com.sun.istack.internal.NotNull;
import com.wu.immortal.half.jsons.JsonWorkInterface;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;

public class RequestUtil {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    public static void callBackResult(
            @NotNull com.wu.immortal.half.beans.ResultBean.ResultInfo resultBeanEnum,
            @NotNull HttpServletResponse response,
            @NotNull JsonWorkInterface jsonWorkInterface
    ) throws IOException {
        String resultJson = jsonWorkInterface.toJsonString(resultBeanEnum);
        byte[] bytes = resultJson.getBytes(StandardCharsets.UTF_8);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(CONTENT_TYPE);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    /**
     * @param request 请求体
     * @return 请求体中的数据
     */
    public static @NotNull String getRequestBody(@NotNull HttpServletRequest request) {
        // 获取json数据
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            StringBuilder stringBuilder = new StringBuilder();
            String inputString;
            while ((inputString = reader.readLine()) != null) {
                stringBuilder.append(inputString);
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 解析请求信息
     * @param request 请求提
     * @return 返回信息
     */
    public static @NotNull JsonObject getRequestInfo(@NotNull HttpServletRequest request) {
        String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
        String requestUri = request.getRequestURI();//得到请求的资源
        String queryString = request.getQueryString();//得到请求的URL地址中附带的参数
        String remoteAddr = request.getRemoteAddr();//得到来访者的IP地址
        String remoteHost = request.getRemoteHost();
        int remotePort = request.getRemotePort();
        String remoteUser = request.getRemoteUser();
        String method = request.getMethod();//得到请求URL地址时使用的方法
        String pathInfo = request.getPathInfo();
        String localAddr = request.getLocalAddr();//获取WEB服务器的IP地址
        String localName = request.getLocalName();//获取WEB服务器的主机名

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("请求的URL地址",requestUrl);
        jsonObject.addProperty("请求的资源",requestUri);
        jsonObject.addProperty("请求的URL地址中附带的参数",queryString);
        jsonObject.addProperty("来访者的IP地址",remoteAddr);
        jsonObject.addProperty("来访者的主机名",remoteHost);
        jsonObject.addProperty("使用的端口号",remotePort);
        jsonObject.addProperty("remoteUser",remoteUser);
        jsonObject.addProperty("请求使用的方法",method);
        jsonObject.addProperty("pathInfo",pathInfo);
        jsonObject.addProperty("WEB地址",localAddr);
        jsonObject.addProperty("WEB主机名称",localName);

        Enumeration<String> reqHeadInfos = request.getHeaderNames();//获取所有的请求头
        while (reqHeadInfos.hasMoreElements()) {
            String headName = reqHeadInfos.nextElement();
            String headValue = request.getHeader(headName);//根据请求头的名字获取对应的请求头的值
            jsonObject.addProperty("header." + headName, headValue);
        }

        jsonObject.addProperty("Accept-Encoding", request.getHeader("Accept-Encoding"));

        return jsonObject;
    }

}
