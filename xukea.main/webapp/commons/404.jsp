<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
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
404 error
</body>
</html>