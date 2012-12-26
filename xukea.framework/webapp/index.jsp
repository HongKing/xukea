<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8" errorPage="" %>
<%@ include file="/commons/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <%@ include file="/commons/meta.jsp" %>
<script>
JQ(document).ready( function(){
    JQ('#menu').tree({
        //checkbox: true,
        url: '${ctx_static}/js/tree_data.json',
        onClick: function(node){
            try{
                var url = node.attributes.url;
                changeMainFrameSrc(url);
            }catch(e){}
        },
        onBeforeExpand: function(node){
            alert('onBeforeExpand');
            return false;
        }
    });
});

function changeMainFrameSrc(_src){
    _src = _src.replace("\${ctx}", "${ctx}");
    _src = _src.replace("\${ctx_static}", "${ctx_static}");
    if(_src != null && _src !="" && _src !="null"){
         JQ("#mainFrame").attr("src", _src);
    }
}
</script>
</head>
<body class="easyui-layout">
    <div region="north" split="true" style="height:50px;padding:10px;overflow:hidden;">
        <p>
            <a href="${ctx}/login/logout">退出</a>
        </p>
    </div>
    <div region="south" split="false" style="height:30px;padding:5px;background:#efefef;overflow:hidden;">
        <div>www.xukea.com &copy;2010</div>
    </div>
    <div region="west" split="true" title="菜单" style="width:150px;">
        <ul id="menu"></ul>
    </div>
    <div region="center" title="主窗口">
        <iframe id="mainFrame" src="about:blank" frameborder="0" scrolling="auto" style="width:100%;height:100%;"></iframe>
    </div>
</body>
</html>