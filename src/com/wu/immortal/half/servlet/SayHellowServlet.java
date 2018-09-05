package com.wu.immortal.half.servlet;


import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.wu.immortal.half.servlet.base.BaseServletServlet;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@javax.servlet.annotation.WebServlet(name = "SayHellowServlet")
public class SayHellowServlet extends BaseServletServlet {
    @Override
    protected void mDoPost(HttpServletRequest request, HttpServletResponse response, Gson gson) throws ServletException, IOException {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("requestType","POST");
        byte[] bytes = gson.toJson(jsonObject).getBytes(StandardCharsets.UTF_8);
        response.setStatus(HttpServletResponse.SC_OK);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @Override
    protected void mDoGet(HttpServletRequest request, HttpServletResponse response, Gson gson) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Hellow Word!</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>Hellow java Web</h1>");
        writer.println("</body>");
        writer.println("</html>");
        writer.close();

    }
}
