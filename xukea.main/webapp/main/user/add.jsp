<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>用户管理：添加新用户</title>
    <%@ include file="/commons/meta.jsp" %>
    
    <link type="text/css" rel="stylesheet" href="${ctx_skin}/wizard/wizard.default.css"/>
    <script type="text/javascript" src="${ctx_static}/js/jquery.wizard.js"></script>
    
</head>
<body >
    <%@ include file="/commons/header.jsp" %>
    
    <ul class="breadcrumb hr">
        <li>用户管理 <span class="divider">：</span></li>
        <li><a href="${ctx}/main/user/list">用户列表</a> <span class="divider">/</span></li>
        <li class="active">添加新用户</li>
    </ul>


    <div class="wizard">
        <div class="wizard_progressbar"></div>
        <div class="wizard_steps">
            <ul>
                <li url="#tab_profile" next_handle="my_check" >1. 基本信息</li>
                <li url="#tab_role"  >2. 角色权限</li>
            </ul>
        </div>   
        <div class="wizard_content" style="height: 290px; overflow-y: auto;">
           <form class="form-horizontal" action="" method="post">
             <div class="steps_page">
                <div id="tab_profile" class="step">
                    <div class="control-group">
                       <label class="control-label" for="username">用户名</label>
                       <div class="controls">
                           <input type="text" id="username" placeholder="请填写登录用户名" />
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
                </div><!-- /#tab_profile -->
                <div id="tab_role" class="step">
                    <ul class="responsive">
                        <li>系统管理员<span roleCode="001" >&#10004; 系统管理员</span></li>
                        <li>用户管理<span roleCode="001" >&#10004; 用户管理</span></li>
                        <li>角色管理<span roleCode="001" class="hidden">&#10004; 角色管理</span></li>
                        <li>用户查询<span roleCode="001" class="hidden">&#10004; 用户查询</span></li>
                        <li>Tablet<span roleCode="001" class="hidden">&#10004; Tablet</span></li>
                    </ul>
                </div><!-- /#tab_role -->
             </div>
           </form>
        </div>
        <div class="button_bar">
            <div class="in">
                <a type="button" class="btn btn-info  btn_prev" >上一步</a>
                <a type="button" class="btn btn-success btn_next" >下一步</a>
                <a type="button" class="btn btn-primary   btn_complete" >提 &nbsp; 交</a>
            </div>
        </div>
    </div>

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