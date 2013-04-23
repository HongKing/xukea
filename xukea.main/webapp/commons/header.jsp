<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.xukea.main.role.model.Menu" %>
<%@ page import="com.xukea.common.UserBasicInfo" %>
<%
	UserBasicInfo headerUserInfo = UserBasicInfo.getFromSession(request);
	String thisUrlCode   = (String)request.getAttribute("THIS_URL_CODE");
	thisUrlCode = (thisUrlCode==null) ? "" : thisUrlCode;
	
	List<Menu> mainMenu   = new ArrayList<Menu>();//主菜单（一级菜单）
    List<Menu> subMenu2   = new ArrayList<Menu>();//二级菜单
    List<Menu> quickMenu2 = new ArrayList<Menu>();//二级快捷菜单
    List<Menu> subMenu3   = new ArrayList<Menu>();//三级菜单
    List<Menu> quickMenu3 = new ArrayList<Menu>();//三级快捷菜单
	if(headerUserInfo!=null){
		mainMenu = (List<Menu>)headerUserInfo.getAttribute("MENU");
	    mainMenu = (mainMenu==null) ? new ArrayList<Menu>() : mainMenu;
	}
%>

<!--start: main menu bar -->
<div class="navbar navbar-inverse  navbar-fixed-top">
    <div class="navbar-inner">
        <div class="container">
            <!-- .btn-navbar is used as the toggle for collapsed navbar content -->
            <button type="button" class="btn btn-navbar" data-toggle="collapse" data-target=".nav-collapse">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
             <!-- Be sure to leave the brand out there if you want it shown -->
             <a class="brand" href="${ctx}">Project name</a>
            <!-- Everything you want hidden at 940px or less, place within here -->
            <div class="nav-collapse collapse">
                <!-- main menu -->
                <ul class="nav">
                <% 
		            for(Menu item : mainMenu){
			        	String active = "";
			        	if(thisUrlCode.startsWith(item.getCode())){
			        		active   = "active";
			        		subMenu2   = item.getSubMenu();
			        		quickMenu2 = item.getQuickMenu();
			        	}
		        %>
		            <li class="<%=active%>"><a href="${ctx}<%=item.getUrl()%>"><%=item.getName()%></a></li>
		        <% } %>
		        </ul>
		        
                <!-- user profile -->
				<%if(headerUserInfo!=null){ %>
					<ul class="nav pull-right">
					    <li class="dropdown">
					        <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="icon-user icon-white"></i>用户一<b class="caret"></b></a>
					        <ul class="dropdown-menu">
					            <li><a href="#"><i class="icon-pencil"></i> 资料修改</a></li>
					            <li><a href="#"><i class="icon-cog"></i> 个性配置</a></li>
					            <li class="divider"></li>
					            <li><a href="${ctx}/login/logout"><i class="icon-off"></i> 退出</a></li>
					        </ul>
					    </li>
					</ul>
				<% } %>

            </div>
        </div><!-- end .container -->
    </div>
</div>
<!--end: main menu bar -->

<!--start: sub menu bar -->
<% if(subMenu2!=null && subMenu2.size()>0){ %>
<div class="subnav subnav-fixed">
    <ul class="nav nav-pills">
        <%  
            for(Menu item : subMenu2){
                String active = "";
                if(thisUrlCode.startsWith(item.getCode())){
                	active     = "active";
                    subMenu3   = item.getSubMenu();
                    quickMenu3 = item.getQuickMenu();
                }
        %>
            <li class="<%=active%>"><a href="${ctx}<%=item.getUrl()%>" ><%=item.getName()%></a></li>
        <% } %>
    </ul>
</div>
<% } %>
<!--end: sub menu bar -->

<!--start: .container -->
<div class="container">
    <div class="row">
        <!-- sider menu bar  -->
        <% if(subMenu3!=null && subMenu3.size()>0){ %>
            <div class="span2 sidebar">
                <ul class="nav nav-list sidenav">
                <%  
		            for(Menu item : subMenu3){
		                String active = "";
		                if(thisUrlCode.startsWith(item.getCode())){
		                    active     = "active";
		                }
		        %>
		            <li class="<%=active%>"><a href="${ctx}<%=item.getUrl()%>"><i class="icon-chevron-right"></i> <%=item.getName()%></a></li>
		        <% } %>
	            </ul>
	        </div>
	        <div class="span10" >
        <% }else{ %>
            <div class="span12" >
        <% } %>
