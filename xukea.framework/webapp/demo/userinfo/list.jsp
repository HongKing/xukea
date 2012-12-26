<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>
<%@ page import="java.util.List" %>
<%@ page import="com.xukea.framework.util.PageList" %>
<%@ page import="com.xukea.demo.userinfo.model.UserInfo"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>DEMO演示：用户管理--列表（不需登录查看，数据来源于Oracle）</title>
    <%@ include file="/commons/meta.jsp" %>
</head>
<body>

	<p>
        <a href="${ctx}/demo">DEMO首页</a><br/>
		<a href="${ctx}/demo/userinfo/new">新增</a><br/>
	</p>

   <table>  
    <tr>
        <th >用户ID</th>
        <th >用户名称</th>
        <th >用户性别</th>
        <th >用户电话</th>
        <th >用户邮箱</th>
        <th >创建日期</th>
        <th >操作</th>
    </tr>
    <%
        PageList<UserInfo> pageList = (PageList<UserInfo>)request.getAttribute("pageList");
	    List<UserInfo> userInfoList = pageList.getResult();
	    for(int i=0;i<userInfoList.size();i++){
	        UserInfo userInfo = userInfoList.get(i);
    %>
        <tr align="center">
            <td><%=userInfo.getId() %></td>
            <td><%=userInfo.getUserName() %></td>
            <td><%=(userInfo.getSex()==1?"男":"女") %></td>
            <td><%=userInfo.getPhone()%></td>
            <td><%=userInfo.getEmail() %></td>
            <td><%=userInfo.getCreateDate() %></td>
            <td>
                <a href="${ctx}/demo/userinfo/<%=userInfo.getId() %>">详情</a> |
                <a href="${ctx}/demo/userinfo/<%=userInfo.getId() %>/edit">修改</a> |
                <a href="${ctx}/demo/userinfo/<%=userInfo.getId() %>?_method=delete">删除</a>
            </td>
        </tr>
    <%
        }
    %>
  </table>  
  <br/>
  当前页：<%=pageList.getThisPageNumber() %>&nbsp;
  总页数：<%=pageList.getLastPageNumber() %>&nbsp;
  每页条数：<%=pageList.getPageSize() %>&nbsp;
  总条数：<%=pageList.getTotalCount() %>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
  <% if(pageList.isFirstPage()) { %>首页     <%}else{ %><a href="${ctx}/demo/userinfo/list/1">首页</a><%} %>
  <% if(!pageList.hasPrevPage()){ %>上一页<%}else{ %><a href="${ctx}/demo/userinfo/list/<%=pageList.getPrevPageNumber() %>">上一页</a><%} %>
  <% if(!pageList.hasNextPage()){ %>下一页<%}else{ %><a href="${ctx}/demo/userinfo/list/<%=pageList.getNextPageNumber() %>">下一页</a><%} %>
  <% if(pageList.isLastPage())  { %>末页     <%}else{ %><a href="${ctx}/demo/userinfo/list/<%=pageList.getLastPageNumber() %>">末页</a>  <%} %>
  </body>
</html>
