<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"  isErrorPage="true"%>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.PrintStream"%>
<% 
    response.setStatus(200); 
    //set no cache
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
</head>
<body>
500 error
<hr/>
<%
    if(exception!=null&&exception.getMessage()!=null){
    	out.println("消息内容："+ exception.getMessage());
    	
    	ByteArrayOutputStream outStream   = new ByteArrayOutputStream();
    	PrintStream           printStream = new PrintStream(outStream);
    	exception.printStackTrace(printStream);
    	
    	out.println("<pre>");
    	out.println(outStream);
    	out.println("</pre>");
    }
    
%>
</body>
</html>