package com.xukea.framework.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 用于rest URL和重写,以便构造出没有扩展名的restURL
 * 
 * <pre>
 * prefix的默认值为: /static
 * excludeExtentions的默认值为: do,jsp,jspx
 * 
 * 以下为web.xml为完整的配置使用,可删除默认值配置
 * <code>
 *	<!-- default是tomcat,jetty等容器提供的servlet, 将静态资源重定向至 /static/, 原来访问css用"/styles/test.css"现需要"/static/styles/test.css" -->
 *	<servlet-mapping>
 *		<servlet-name>default</servlet-name>
 *		<url-pattern>/static/*</url-pattern>
 *	</servlet-mapping>
 *	
 *  <!-- URL重写,访问静态资源将为其增加前缀,如 /demo.css 重写至 ${prefix}/demo.css,现prefix为/static -->
 * 	<filter>
 *		<filter-name>RestUrlRewriteFilter</filter-name>
 *		<filter-class>cn.org.rapid_framework.web.filter.RestUrlRewriteFilter</filter-class>
 *		<init-param>
 *			<param-name>prefix</param-name>
 *			<param-value>/static</param-value>
 *		</init-param>
 *		<init-param>
 *			<param-name>excludeExtentions</param-name>
 *			<param-value>jsp,jspx,do</param-value>
 *		</init-param>
 *		<init-param>
 *			<param-name>excludePrefixes</param-name>
 *			<param-value>/userinfod,/security</param-value>
 *		</init-param>
 *	</filter>
 *	<filter-mapping>
 *		<filter-name>RestUrlRewriteFilter</filter-name>
 *		<url-pattern>/*</url-pattern>
 *	</filter-mapping>
 * </code>
 * </pre>
 */
public class RestUrlRewriteFilter extends OncePerRequestFilter implements Filter{
	private static final String DEFAULT_EXECUDE_EXTENTIONS = "";
	private static final String DEFAULT_PREFIX = "/static";
	
	private String prefix;
	private Set<String> excludeExtentions = new HashSet<String>();
	private String[] excludePrefixes = new String[0];
	
	protected void initFilterBean() throws ServletException {
		try {
			initParameter(getFilterConfig());
		} catch (IOException e) {
			throw new ServletException("init paramerter error",e);
		}
	}

	private void initParameter(FilterConfig filterConfig) throws IOException {
		prefix = getStringParameter(filterConfig, "prefix", DEFAULT_PREFIX);
		String excludeExtentionsString = getStringParameter(filterConfig, "excludeExtentions", DEFAULT_EXECUDE_EXTENTIONS);
		excludeExtentions = new HashSet<String>((Arrays.asList(excludeExtentionsString.split(","))));
		
		String excludePrefixsString = getStringParameter(filterConfig, "excludePrefixes", null);
		if(StringUtils.hasText(excludePrefixsString)) {
			excludePrefixes = excludePrefixsString.split(",");
		}
	}

	protected void doFilterInternal(HttpServletRequest request,HttpServletResponse response, FilterChain filterChain)throws ServletException, IOException {
		String from = request.getRequestURI().substring(request.getContextPath().length());
		if(isRewriteURL(from)) {
//			final String to = prefix+from;
			request.setAttribute("IS_STATIC", Boolean.TRUE);
			final String to = from.substring(prefix.length(), from.length());
			request.getRequestDispatcher(to).forward(request, response);
		}else {
			request.setAttribute("IS_STATIC", Boolean.FALSE);
			filterChain.doFilter(request, response);
		}
	}
	
	private boolean isRewriteURL(String from) {
		String extension = StringUtils.getFilenameExtension(from);
//		if(extension == null || "".equals(extension)) {
//			return false;
//		}

		if(!from.startsWith(prefix)){
			return false;
		}
		
		for(String excludePrefix : excludePrefixes) {
			if(from.startsWith(excludePrefix)) {
				return false;
			}
		}
		
		if(excludeExtentions.contains(extension)) {
			return false;
		}
		
		return true;
	}

	private String getStringParameter(FilterConfig filterConfig,String name,String defaultValue) {
		String value = filterConfig.getInitParameter(name);
		if(value == null || "".equals(value.trim())) {
			return defaultValue;
		}
		return value;
	}
	
}
