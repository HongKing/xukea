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
        <li><a href="${ctx}/main/role/list">角色列表</a> <span class="divider">/</span></li>
        <li class="active">系统管理员</li>
    </ul>
    
    <!-- tabbable -->
    <div class="tabbable tabs-left">
        <ul class="nav nav-tabs">
	        <li class="active"><a href="#tab_profile" data-toggle="tab">基本资料</a></li>
	        <li><a href="#tab_source" data-toggle="tab">角色资源</a></li>
	        <li><a href="#tab_users" data-toggle="tab">授权用户</a></li>
	    </ul>
	    <div class="tab-content">
            <!-- user's profile -->
	        <div id="tab_profile" class="tab-pane active">
				<form class="form-horizontal">
				    <div class="control-group">
				        <label class="control-label" for="shortword">角色编码</label>
				        <div class="controls">
				           <input type="text" id="shortword" value="SYSTEM_MANAGER" disabled/>
				        </div>
				    </div><!-- /shortword -->
				    <div class="control-group">
				        <label class="control-label" for="rolename">角色名称</label>
				        <div class="controls">
				           <input type="text" id="rolename" placeholder="请填写角色名称" />
				           <span class="help-inline">Something may have gone wrong</span>
				        </div>
				    </div><!-- /rolename -->
				    <div class="control-group">
				        <label class="control-label" for="email">备注</label>
				        <div class="controls">
				            <textarea rows="3" id="email" placeholder="请填写备注信息" ></textarea>
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
	        <div id="tab_source" class="tab-pane">
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
	        </div>
            <!-- /user's role -->
            <!-- user's password -->
            <div id="tab_users" class="tab-pane">
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