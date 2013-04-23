<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>角色管理</title>
    <%@ include file="/commons/meta.jsp" %>
    
</head>
<body >
    <%@ include file="/commons/header.jsp" %>
    
    <ul class="breadcrumb hr">
        <li>角色管理 <span class="divider">：</span></li>
        <li class="active">角色列表</li>
        <li class="pull-right">
            <a class="btn btn-primary" href="${ctx}/main/role/new">添加角色</a>
        </li>
    </ul>
          
    <table class="table table-bordered table-striped table-condensed table-hover">
    <thead>
        <tr>
            <th>角色名称</th>
            <th>角色编码</th>
            <th>备注</th>
            <th></th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td>系统管理员</td>
            <td>SYSTEM_MANAGER</td>
            <td></td>
            <td>
                <a class="btn" href="${ctx}/main/role/edit/1"><i class="icon-edit"></i> 详细</a>
                <a class="btn" href="${ctx}/main/role/delete/1" ><i class="icon-remove"></i> 删除</a>
            </td>
        </tr>
        <tr>
            <td>系统管理员</td>
            <td>SYSTEM_MANAGER</td>
            <td></td>
            <td>
                <a class="btn" href="${ctx}/main/role/edit/1"><i class="icon-edit"></i> 详细</a>
                <a class="btn" href="${ctx}/main/role/delete/1" ><i class="icon-remove"></i> 删除</a>
            </td>
        </tr>
        <tr>
            <td>系统管理员</td>
            <td>SYSTEM_MANAGER</td>
            <td></td>
            <td>
                <a class="btn" href="${ctx}/main/role/edit/1"><i class="icon-edit"></i> 详细</a>
                <a class="btn" href="${ctx}/main/role/delete/1" ><i class="icon-remove"></i> 删除</a>
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