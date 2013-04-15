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
                <li url="#tab_source"  >2. 角色权限</li>
                <li url="#tab_users"  >3. 授权用户</li>
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
                <div id="tab_source" class="step">
	                <div class="accordion" id="plugin_roles">
	                    <div class="accordion-group">
	                        <div class="accordion-heading">
	                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#plugin_roles" href="#plugin_000">系统默认</a>
	                        </div>
	                        <div id="plugin_000" class="accordion-body collapse in">
	                            <div class="accordion-inner">
	                                <table class="table table-bordered table-striped table-condensed table-hover">
	                                <thead>
	                                    <tr>
	                                        <th style="width:50px;"></th>
	                                        <th style="width:30%;">模块资源</th>
	                                        <th>备注</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                    <tr>
	                                        <td><input type="checkbox"/></td>
	                                        <td>通用模块</td>
	                                        <td></td>
	                                    </tr>
	                                </tbody>
	                                </table>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="accordion-group">
	                        <div class="accordion-heading">
	                            <a class="accordion-toggle" data-toggle="collapse" data-parent="#plugin_roles" href="#plugin_001">基础框架</a>
	                        </div>
	                        <div id="plugin_001" class="accordion-body collapse">
	                            <div class="accordion-inner">
	                                <table class="table table-bordered table-striped table-condensed table-hover">
	                                <thead>
	                                    <tr>
	                                        <th style="width:50px;"></th>
	                                        <th style="width:30%;">模块资源</th>
	                                        <th>备注</th>
	                                    </tr>
	                                </thead>
	                                <tbody>
	                                    <tr>
	                                        <td><input type="checkbox"/></td>
	                                        <td>用户角色管理模块</td>
	                                        <td></td>
	                                    </tr>
	                                    <tr>
	                                        <td><input type="checkbox"/></td>
	                                        <td>插件管理模块</td>
	                                        <td></td>
	                                    </tr>
	                                </tbody>
	                                </table>
	                            </div>
	                        </div>
	                    </div>
	                </div>
                </div><!-- /#tab_source -->
                <div id="tab_users" class="step">
                    <div class="form-search ">
                        <div class="input-append">
                            <input type="text" class="span2 search-query" />
                            <button type="button" class="btn">添加</button>
                        </div>
                        <button type="button" class="btn">批量添加</button>
                    </div>
	                <table class="table table-bordered table-striped table-condensed table-hover">
	                    <thead>
	                        <tr>
	                            <th>登录名</th>
	                            <th>姓名</th>
	                            <th>邮箱地址</th>
	                            <th></th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <tr>
	                            <td>admin</td>
	                            <td>管理员</td>
	                            <td>admin@xukea.com</td>
	                            <td>
	                                <a class="btn" href="${ctx}/main/role/remove/user/1" ><i class="icon-remove"></i> 删除</a>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td>admin</td>
	                            <td>管理员</td>
	                            <td>admin@xukea.com</td>
	                            <td>
	                                <a class="btn" href="${ctx}/main/role/remove/user/1" ><i class="icon-remove"></i> 删除</a>
	                            </td>
	                        </tr>
	                        <tr>
	                            <td>admin</td>
	                            <td>管理员</td>
	                            <td>admin@xukea.com</td>
	                            <td>
	                                <a class="btn" href="${ctx}/main/role/remove/user/1" ><i class="icon-remove"></i> 删除</a>
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
                    </div><!-- /#tab_users -->
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