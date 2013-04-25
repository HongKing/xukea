<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"  isErrorPage="true"%>
<%@ include file="/commons/taglibs.jsp" %>
<%@ page import="java.io.ByteArrayOutputStream"%>
<%@ page import="java.io.PrintStream"%>
<% 
    response.setStatus(200); 
    //set no cache
    response.setHeader("Pragma","No-cache");
    response.setHeader("Cache-Control","no-cache");
    response.setDateHeader("Expires", 0);
    
    // 异常信息
    exception = (exception==null) ? new Exception() : exception;
    // 异常标题内容
    String msgTitle = (exception.getMessage()==null) ? "" : exception.getMessage();
    // 异常堆栈信息
    ByteArrayOutputStream msgStream   = new ByteArrayOutputStream();
    PrintStream           printStream = new PrintStream(msgStream);
    exception.printStackTrace(printStream);
%>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <title>500 ERROR PAGE</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link type="text/css" rel="stylesheet" href="${ctx_skin}/main/error.css">
    <script type="text/javascript" src="${ctx_static}/js/jquery.min.js"></script>

<script language="javascript">
    var JQ = jQuery.noConflict();
    JQ(document).ready( function(){
        JQ("a.show_error_info").click(function(){
            JQ("pre").slideToggle("slow");
        });
    });
</script>
</head>
<body>
    <div id="gradiant"></div>
    <div id="content">
        <h1>ERR<img src="${ctx_skin}/main/images/ouh.png">R</h1>
        <h3>Oooooops… 客官，您的干煸辣子鸡爆锅了，我们给您重做，请稍等。。。。</h3>
        <p class="text"><%=msgTitle%><a href="javascript:void(0)" class="show_error_info">详情</a></p>
        <pre style="display:none;"><%=msgStream %></pre>
        <p class="links">
            <a href="${ctx}">首页</a>
            <a href="#">站点地图</a>
            <a href="javascript: history.go(-1)">返回</a>
        </p>
    </div>
</body>
</html>