<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户管理：添加新用户</title>
    <%@ include file="/commons/meta.jsp" %>
    
</head>
<body >
    <%@ include file="/commons/header.jsp" %>
    
    <ul class="breadcrumb hr">
        <li>用户管理 <span class="divider">：</span></li>
        <li><a href="${ctx}/main/user/list">用户列表</a> <span class="divider">/</span></li>
        <li class="active">添加新用户</li>
    </ul>

    <%@ include file="/commons/footer.jsp" %>
</body>
</html>