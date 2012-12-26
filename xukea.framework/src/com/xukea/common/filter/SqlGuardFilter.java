package com.xukea.common.filter;

import java.io.IOException;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;


/**
 * SQL 防注入过滤器
 */
public class SqlGuardFilter implements Filter {
	private final Logger log = Logger.getLogger(getClass());
	
	/** SQL关键字 */
	private String SQL_KEY_WORDS = "select |insert |update |delete |truncate |alter |create |drop |grant |lock table |from |where |and |or |exec |union |count ";

	private Pattern SQL_PATTERN    = Pattern.compile("\\b("+ SQL_KEY_WORDS +")\\b", Pattern.CASE_INSENSITIVE);

	
	public void init(FilterConfig config) throws ServletException {
		log.debug("init");
		String keywords = config.getInitParameter("keywords");
		if(keywords!=null && !"".equals(keywords)){
			this.SQL_KEY_WORDS = keywords;
		}
	}
	
	public void destroy() {
		log.debug("destroy");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException{
		log.debug("doFilter");
		Matcher match = null;
		
		HttpServletRequest  req = (HttpServletRequest)request;
		Iterator values = req.getParameterMap().values().iterator();//获取所有的表单参数
		while(values.hasNext()){
			String[] value = (String[])values.next();
			for(int i = 0;i < value.length;i++){
				value[i]=value[i].replaceAll("'","''");
				match = SQL_PATTERN.matcher(value[i]);
				if(match.find()){
					throw new ServletException("SQL注入攻击过滤");
				}
			}
		}
		
		chain.doFilter(request, response);
	}
}
