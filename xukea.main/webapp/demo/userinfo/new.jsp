<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>DEMO演示：用户管理--新增</title>
    <%@ include file="/commons/meta.jsp" %>
</head>
<body>

	<p>
        <a href="${ctx}/demo">DEMO首页</a><br/>
        <a href="${ctx}/demo/userinfo/list">列表</a><br/>
	</p>

<form action="${ctx}/demo/userinfo/new" method="post">

用       户       名：<input type="text" name="userName"/><br/>
密 &nbsp; 码：<input type="password" name="password"/><br/>
性 &nbsp; 别：<select name="sex">
				<option value="1">男</option>
				<option value="2">女</option>
			 </select><br/>
电 &nbsp; 话：<input type="text" name="phone"/><br/>
邮 &nbsp; 箱：<input type="text" name="email"/><br/>

<input type="submit" value="提交"/><input type="reset" value="重置"/>
</form>

</body>
</html>