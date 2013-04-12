<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html>
<html >
<head>
    <title>系统登录</title>
    <%@ include file="/commons/meta.jsp" %>
    <style type="text/css">
      body {
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 300px;
        padding: 19px 29px 29px;
        margin: 0 auto 20px;
        background-color: #fff;
        border: 1px solid #e5e5e5;
        -webkit-border-radius: 5px;
           -moz-border-radius: 5px;
                border-radius: 5px;
        -webkit-box-shadow: 0 1px 2px rgba(0,0,0,.05);
           -moz-box-shadow: 0 1px 2px rgba(0,0,0,.05);
                box-shadow: 0 1px 2px rgba(0,0,0,.05);
      }
      .form-signin .form-signin-heading,
      .form-signin .checkbox {
        margin-bottom: 10px;
      }
      .form-signin input[type="text"],
      .form-signin input[type="password"] {
        font-size: 16px;
        height: auto;
        margin-bottom: 15px;
        padding: 7px 9px;
      }

    </style>
</head>
<body>

<div class="container">
    <form class="form-signin" action="${ctx}/login/login" method="post">
        <h2 class="form-signin-heading">用户登录</h2>
        <div><%=request.getAttribute("ERROR_MSG")==null ? "" : request.getAttribute("ERROR_MSG") %></div>
        <input type="text"     name="username" class="input-block-level" placeholder="用户名 / 邮箱 / 手机号" />
        <input type="password" name="password" class="input-block-level" placeholder="登录密码"/>
        <button class="btn btn-large btn-primary" type="submit">登录</button>
        <button class="btn btn-large btn-primary" type="button">AJAX登录</button>
    </form>

</div> <!-- /container -->
<script type="text/javascript">
JQ("button[type='button']").click(function(){
	var param = {};
	param.username = JQ("input[name='username']").val();
	param.password = JQ("input[name='password']").val();
	JQ.sendPost("${ctx}/login/login", param, function(data){
		window.location.href="${ctx}/index"
	}, function(data){
		alert(data.xukea_msg);
	});
	return false;
});
</script>
</body>
</html>