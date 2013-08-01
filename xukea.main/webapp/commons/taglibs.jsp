<%@ page language="java" contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.Locale" %>
<%@ page import="org.springframework.web.servlet.i18n.SessionLocaleResolver" %>

<%@ taglib prefix="c"      uri="/tags/jstl-core" %>
<%@ taglib prefix="fmt"    uri="/tags/jstl-fmt" %>
<%@ taglib prefix="fn"     uri="/tags/jstl-functions" %>
<%@ taglib prefix="form"   uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="xukea"  uri="/tags/xukea" %>

<%
    // 从SESSION中获取当前语言
    Locale sessionLocale = (Locale)session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
    if(sessionLocale==null){
        sessionLocale = request.getLocale();
    }
    // 定义语言常量
    pageContext.setAttribute("lang",       sessionLocale.getLanguage());
    pageContext.setAttribute("ctx",        request.getContextPath());
    pageContext.setAttribute("skin",       "default");
    pageContext.setAttribute("ctx_static", request.getContextPath()+"/static");
    pageContext.setAttribute("ctx_skin",   request.getContextPath()+"/static/skin/default");

%>