<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>用户管理：用户列表</title>
    <%@ include file="/commons/meta.jsp" %>
    
</head>
<body >
    <%@ include file="/commons/header.jsp" %>
    
    <ul class="breadcrumb hr">
        <li>用户管理 <span class="divider">：</span></li>
        <li class="active">用户列表</li>
        <li class="pull-right">
            <a class="btn btn-primary" href="${ctx}/main/user/new">添加用户</a>
            <a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
                <i class="icon-search"></i> 搜索
                <span class="caret"></span>
            </a>
        </li>
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
                <a class="btn btn-success" href="#myModal" role="button" data-toggle="modal"><i class="icon-play"></i> 启用</a>
            </td>
        </tr>
        <tr>
            <td>admin</td>
            <td>管理员</td>
            <td>admin@xukea.com</td>
            <td>2011-12-28 10:28</td>
            <td>
                <a class="btn" href="${ctx}/main/user/edit/1"><i class="icon-edit"></i> 编辑</a>
                <a class="btn btn-danger" href="#myModal" role="button" data-toggle="modal"><i class="icon-stop"></i> 禁用</a>
            </td>
        </tr>
        <tr>
            <td>admin</td>
            <td>管理员</td>
            <td>admin@xukea.com</td>
            <td>2011-12-28 10:28</td>
            <td>
                <a class="btn" href="${ctx}/main/user/edit/2"><i class="icon-edit"></i> 编辑</a>
                <a class="btn btn-danger" href="#myModal" role="button" data-toggle="modal"><i class="icon-stop"></i> 禁用</a>
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
 
<!-- Modal -->
<div id="myModal" class="modal hide fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-header">
    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    <h3 id="myModalLabel">Modal header</h3>
  </div>
  <div class="modal-body">
    <p>One fine body…</p>
  </div>
  <div class="modal-footer">
    <button class="btn" data-dismiss="modal" aria-hidden="true">Close</button>
    <button class="btn btn-primary">Save changes</button>
  </div>
</div>
    <%@ include file="/commons/footer.jsp" %>
</body>
</html>