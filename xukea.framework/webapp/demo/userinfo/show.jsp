<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ page import="com.xukea.demo.userinfo.model.UserInfo"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>DEMO演示：用户管理--详情</title>
    <%@ include file="/commons/meta.jsp" %>
</head>
<body>

    <p>
        <a href="${ctx}/demo">DEMO首页</a><br/>
        <a href="${ctx}/demo/userinfo/list">列表</a><br/>
        <a href="${ctx}/demo/userinfo/${userInfo.id}/edit">修改</a><br/>
    </p>

用       户       ID：${userInfo.id}<br/>
用       户       名：${userInfo.userName}<br/>
密 &nbsp; 码：${userInfo.password}<br/>
性 &nbsp; 别：${userInfo.sex}<br/>
电 &nbsp; 话：${userInfo.phone}<br/>
邮 &nbsp; 箱：${userInfo.email}<br/>
日 &nbsp; 期：${userInfo.email}<br/>

</body>
</html>