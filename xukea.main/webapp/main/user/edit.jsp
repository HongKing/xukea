<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户管理</title>
    <%@ include file="/commons/meta.jsp" %>
    
</head>
<body >
    <%@ include file="/commons/header.jsp" %>
    
    <ul class="breadcrumb hr">
        <li>用户管理 <span class="divider">：</span></li>
        <li><a href="${ctx}/main/user/list">用户列表</a> <span class="divider">/</span></li>
        <li class="active">张三</li>
    </ul>
    
    <table class="table table-bordered table-striped table-condensed table-hover">
    <thead>
        <tr>
	        <th>登录名</th>
	        <th>姓名</th>
	        <th>邮箱地址</th>
	        <th>添加时间</th>
	        <th></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>admin</td>
            <td>管理员</td>
            <td>admin@xukea.com</td>
            <td>2011-12-28 10:28</td>
            <td>
                <a class="btn" href="${ctx}/main/user/edit/1"><i class="icon-edit"></i> 编辑</a>
                <a class="btn btn-success" href="#"><i class="icon-play"></i> 启用</a>
                <a class="btn btn-danger" href="#"><i class="icon-stop"></i> 禁用</a>
            </td>
        </tr>
        <tr>
            <td>admin</td>
            <td>管理员</td>
            <td>admin@xukea.com</td>
            <td>2011-12-28 10:28</td>
            <td>
                <a class="btn" href="${ctx}/main/user/edit/1"><i class="icon-edit"></i> 编辑</a>
                <a class="btn btn-success" href="#"><i class="icon-play"></i> 启用</a>
                <a class="btn btn-danger" href="#"><i class="icon-stop"></i> 禁用</a>
            </td>
        </tr>
        <tr>
            <td>admin</td>
            <td>管理员</td>
            <td>admin@xukea.com</td>
            <td>2011-12-28 10:28</td>
            <td>
                <a class="btn" href="${ctx}/main/user/edit/1"><i class="icon-edit"></i> 编辑</a>
                <a class="btn btn-success" href="#"><i class="icon-play"></i> 启用</a>
                <a class="btn btn-danger" href="#"><i class="icon-stop"></i> 禁用</a>
            </td>
        </tr>
    </tbody>
    </table>
    
    <div class="pagination pagination-centered">
		<ul>
			<li class="disabled"><span>Prev</span></li>
			<li class="active"><span>1</span></li>
			<li><a href="#">2</a></li>
			<li><a href="#">3</a></li>
			<li><a href="#">4</a></li>
			<li><a href="#">Next</a></li>
		</ul>
    </div>

    <%@ include file="/commons/footer.jsp" %>
</body>
</html>