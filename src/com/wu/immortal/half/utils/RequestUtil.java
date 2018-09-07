package com.wu.immortal.half.utils;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import com.wu.immortal.half.beans.ResultBeanEnum;
import com.wu.immortal.half.beans.ServletBeans.ResultBean;
import com.wu.immortal.half.jsons.JsonWorkInterface;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class RequestUtil {

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    public static void callBackResult(
            @NotNull ResultBeanEnum resultBeanEnum,
            @NotNull HttpServletResponse response,
            @NotNull JsonWorkInterface jsonWorkInterface
    ) throws IOException {
        ResultBean resultBean = ResultBean.newInstance(
                resultBeanEnum.getCode(),
                resultBeanEnum.getMsg(),
                resultBeanEnum.getResultJsonBody());
        String resultJson = jsonWorkInterface.toJsonString(resultBean);
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

}
