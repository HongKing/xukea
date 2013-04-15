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
    
    <!-- tabbable -->
    <div class="tabbable tabs-left">
        <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_profile" data-toggle="tab">基本资料</a></li>
	        <li><a href="#tab_role" data-toggle="tab">角色权限</a></li>
	        <li><a href="#tab_pswd" data-toggle="tab">修改密码</a></li>
	    </ul>
	    <div class="tab-content">
            <!-- user's profile -->
	        <div id="tab_profile" class="tab-pane active">
				<form class="form-horizontal">
				    <div class="control-group">
				       <label class="control-label" for="username">用户名</label>
				       <div class="controls">
				           <input type="text" id="username" value="" disabled/>
				       </div>
				    </div><!-- /username -->
				    <div class="control-group">
				       <label class="control-label" for="realname">姓名</label>
				       <div class="controls">
				           <input type="text" id="realname" placeholder="请填写真实姓名" />
				           <span class="help-inline">Something may have gone wrong</span>
				       </div>
				    </div><!-- /realname -->
				    <div class="control-group">
				       <label class="control-label" for="email">邮箱</label>
				       <div class="controls">
				           <input type="text" id="email" placeholder="请填写常用邮箱地址" />
				           <span class="help-inline">Something may have gone wrong</span>
				       </div>
				    </div><!-- /email -->
                    <div class="control-group">
                        <div class="controls">
                            <button type="button" class="btn">Cancel</button>
	                        <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
				</form>
	        </div>
	        <!-- /user's profile -->
            <!-- user's role -->
	        <div id="tab_role" class="tab-pane">
                <form class="form-horizontal">
		            <ul class="responsive">
			            <li>系统管理员<span roleCode="001" >&#10004; 系统管理员</span></li>
			            <li>用户管理<span roleCode="001" >&#10004; 用户管理</span></li>
			            <li>角色管理<span roleCode="001" class="hidden">&#10004; 角色管理</span></li>
	                    <li>用户查询<span roleCode="001" class="hidden">&#10004; 用户查询</span></li>
	                    <li>Tablet<span roleCode="001" class="hidden">&#10004; Tablet</span></li>
	                </ul>
	                <div class="control-group">
	                    <div class="controls">
	                        <button type="button" class="btn">Cancel</button>
	                        <button type="submit" class="btn btn-primary">Save changes</button>
	                    </div>
	                </div>
                </form>
	        </div>
            <!-- /user's role -->
            <!-- user's password -->
            <div id="tab_pswd" class="tab-pane">
                <form class="form-horizontal">
                    <div class="control-group">
                       <label class="control-label" for="password">当前密码</label>
                       <div class="controls">
                           <input type="text" id="password" placeholder="请填写当前登录密码" />
                       </div>
                    </div><!-- /username -->
                    <div class="control-group">
                       <label class="control-label" for="newpswd">新密码</label>
                       <div class="controls">
                           <input type="text" id="newpswd" placeholder="请填写新密码" />
                           <span class="help-inline">Something may have gone wrong</span>
                       </div>
                    </div><!-- /realname -->
                    <div class="control-group">
                       <label class="control-label" for="aginpswd">确认新密码</label>
                       <div class="controls">
                           <input type="text" id="aginpswd" placeholder="请再次填写新密码" />
                           <span class="help-inline">Something may have gone wrong</span>
                       </div>
                    </div><!-- /email -->
                    <div class="control-group">
                        <div class="controls">
                            <button type="button" class="btn">Cancel</button>
                            <button type="submit" class="btn btn-primary">Save changes</button>
                        </div>
                    </div>
                </form>
            </div>
            <!-- /user's password -->
        </div>
    </div> <!-- /tabbable -->
    
    
<script type="text/javascript">
JQ(document).ready( function(){
	JQ(".responsive li").click(function(){
		JQ(this).children("span").toggleClass("hidden");
        return false;
	});
	JQ(".responsive span").click(function(e){
		JQ(this).toggleClass("hidden");
		return false;
    });
	
})
</script>


    <%@ include file="/commons/footer.jsp" %>
</body>
</html>