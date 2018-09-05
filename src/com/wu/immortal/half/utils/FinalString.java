package com.wu.immortal.half.utils;

public class FinalString {

    public static final String HEADER_KEY_ACCESS = "Access";

    public static final int REQUEST_SUCCESS = 10000;                // 请求成功
    public static final int REQUEST_ERRO_TOKEN_ILLEGAL = 10002;     // token为null或非法
    public static final int REQUEST_ERRO_TOKEN_END_TIME = 10001;    // token 超时
    public static final int REQUEST_ERRO_NULL_BODY = 10003;         // body参数null
    public static final int REQUEST_ERRO_SERVER = 10004;            // 服务器异常，各子类查找时异常





    public static boolean checkNull(String text) {
        return text == null || text.length() == 0;
    }






















    /**
     *     * 1.获得客户机信息
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
     response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
     //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
     response.setHeader("content-type", "text/html;charset=UTF-8");
     PrintWriter out = response.getWriter();
     out.write("获取到的客户机信息如下：");
     out.write("<hr/>");
     out.write("请求的URL地址："+requestUrl);
     out.write("<br/>");
     out.write("请求的资源："+requestUri);
     out.write("<br/>");
     out.write("请求的URL地址中附带的参数："+queryString);
     out.write("<br/>");
     out.write("来访者的IP地址："+remoteAddr);
     out.write("<br/>");
     out.write("来访者的主机名："+remoteHost);
     out.write("<br/>");
     out.write("使用的端口号："+remotePort);
     out.write("<br/>");
     out.write("remoteUser："+remoteUser);
     out.write("<br/>");
     out.write("请求使用的方法："+method);
     out.write("<br/>");
     out.write("pathInfo："+pathInfo);
     out.write("<br/>");
     out.write("localAddr："+localAddr);
     out.write("<br/>");
     out.write("localName："+localName);

     Enumeration<String> reqHeadInfos = request.getHeaderNames();//获取所有的请求头
     out.write("获取到的客户端所有的请求头信息如下：");
     out.write("<hr/>");
     while (reqHeadInfos.hasMoreElements()) {
     String headName = (String) reqHeadInfos.nextElement();
     String headValue = request.getHeader(headName);//根据请求头的名字获取对应的请求头的值
     out.write(headName+":"+headValue);
     out.write("<br/>");
     }
     out.write("<br/>");
     out.write("获取到的客户端Accept-Encoding请求头的值：");
     out.write("<hr/>");
     String value = request.getHeader("Accept-Encoding");//获取Accept-Encoding请求头对应的值
     out.write(value);
     out.close();
     */
}
