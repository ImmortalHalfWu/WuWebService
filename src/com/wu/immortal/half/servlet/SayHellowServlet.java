package com.wu.immortal.half.servlet;


import com.google.gson.Gson;
import com.google.gson.JsonObject;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@javax.servlet.annotation.WebServlet(name = "SayHellowServlet")
public class SayHellowServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("requestType","POST");
        byte[] bytes = new Gson().toJson(jsonObject).getBytes(StandardCharsets.UTF_8);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("text/html;charset=UTF-8");
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, IOException {
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
