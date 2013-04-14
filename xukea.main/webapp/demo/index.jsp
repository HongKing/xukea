<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>DEMO演示页面</title>
    <%@ include file="/commons/meta.jsp" %>
    
<style type="text/css">
    fieldset{float:left; padding:10px; margin:10px; width:400px; height:110px;}
</style>

<script language="javascript">
JQ(document).ready( function(){
    JQ("#cap_img").click(function(){
        this.src = "${ctx}/captcha.jpg?ram="+Math.random();
    });
    JQ("#cap_btn").click(validateCap);
});

/****************** 退出 ******************/
function logout(){
  alert('logout');
}

/****************** ajax请求 ******************/
function errorRequest(code){
    var url = "${ctx}/demo/error/"+code;
    JQ.send(url, function(data){
        alert('success callback:'+JQ.stringify(data));
    }, function(data){
        alert('error callback:'+JQ.stringify(data));
    });
}
function getParamRequest(){
    var url = "${ctx}/demo/blog/用户名/2011/10";
    JQ.send(url, function(data){
        alert('success callback:'+JQ.stringify(data));
    }, function(data){
        alert('error callback:'+JQ.stringify(data));
    });
}

/****************** 验证码 ******************/
function validateCap(){
    var url = "${ctx}/demo/validate/captcha";
    var param = {capVal: JQ("#cap_val").val()};
    JQ.send(url, param, function(data){
        alert('success callback:'+JQ.stringify(data));
    }, function(data){
        alert('error callback:'+JQ.stringify(data));
    });
}
</script>
</head>
<body>

<fieldset>
    <legend><b>登录演示</b></legend>
    <p>
        <a href="${ctx}/login">登录页面</a><br/>
        <a href="${ctx}/demo/account/list" >登录后才能访问的内容</a><br/>
    </p>
</fieldset>

<fieldset>
    <legend><b>用户管理演示</b></legend>
    <p>
        <a href="${ctx}/demo/userinfo/list">列表</a><br/>
        <a href="${ctx}/demo/userinfo/new" >新增</a><br/>
    </p>
</fieldset>

<fieldset style="height:150px;">
    <legend><b>普通请求</b></legend>
    <p>
        <a href="${ctx}/demo/error/403" >403错误</a><br/>
        <a href="${ctx}/demo/error/404" >404错误</a><br/>
        <a href="${ctx}/demo/error/500" >500错误</a><br/>
        <a href="${ctx}/demo/error/unlogin" >未登录错误</a><br/>
    </p>
</fieldset>

<fieldset style="height:150px;">
    <legend><b>Ajax请求</b></legend>
    <p>
        <a href="javascript:void(0);" onclick="errorRequest('403')">403错误</a><br/>
        <a href="javascript:void(0);" onclick="errorRequest('404')">404错误</a><br/>
        <a href="javascript:void(0);" onclick="errorRequest('500')">500错误</a><br/>
        <a href="javascript:void(0);" onclick="errorRequest('unlogin')">未登录错误</a><br/>
        <a href="javascript:void(0);" onclick="getParamRequest()">解析/{username}/{year}/{month}路径</a><br/>
    </p>
</fieldset>

<fieldset>
    <legend><b>文件上传</b></legend>
    <p>
        <iframe name="fileFrame" style="display:none;"></iframe>
        <form action="${ctx}/demo/upload" method="post" enctype="multipart/form-data" target="fileFrame">
            <input type="file" name="userlist"/>
            <input type="submit" value="提交"/>
        </form>
    </p>
</fieldset>

<fieldset>
    <legend><b>验证码</b></legend>
    <p>
        <img   id="cap_img" src="${ctx}/captcha.jpg" title="看不清楚？点击换一张" style="margin-left:5px;cursor: pointer;"/>
        <br/><br/>
        <input id="cap_val" type="text"/>
        <input id="cap_btn" type="button" value="验证"/>
    </p>
</fieldset>

<fieldset>
    <legend><b>国际化支持</b></legend>
    <p>
        <a href="?locale=zh_CN" >中文</a> &nbsp;&nbsp;
        <a href="?locale=en_US" >英语</a><br/><br/>
        fmt:message    : <fmt:message    key="entity.saved"/> <br/>
        spring:message : <spring:message code="entity.saved"/><br/>
    </p>
</fieldset>

<fieldset>
    <legend><b>其他</b></legend>
    <p>
        <a href="${ctx_static}/demo/static_test.html" >静态页面</a><br/>
        <a href="${ctx}/login.jsp?username=admin' and 1=1" >SQL注入攻击</a><br/>
    </p>
</fieldset>

</body>
</html>