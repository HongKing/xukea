<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<% 
    response.setStatus(200); 
    //set no cache
    //response.setHeader("Pragma","No-cache");
    //response.setHeader("Cache-Control","no-cache");
    //response.setDateHeader("Expires", 0);
%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <title>404 ERROR PAGE</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="${ctx_skin}/main/error.css">
</head>
<body>
    <div id="gradiant"></div>
    <div id="content">
        <h1>4<img src="${ctx_skin}/main/images/ouh.png">4</h1>
        <p class="text"></p>
        <h2>客官，您的干煸辣子鸡的小鸡仔正在孵化中，请稍等。。。。</h2>
        <p></p>
        <form action="">
            <input type="text" name="searchterms" class="inputform" />
            <input type="submit" name="SearchSubmit" value="Search" class="button">
        </form>
        <p class="links">
            <a href="${ctx}">首页</a>
            <a href="#">站点地图</a>
            <a href="javascript: history.go(-1)">返回</a>
        </p>
    </div>
</body>
</html>