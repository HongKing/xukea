package com.xukea.framework.filter;

import java.io.IOException;
import java.util.Locale;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


public class HiddenHttpMethodFilter extends OncePerRequestFilter {

	/** Default method parameter: <code>_method</code> */
	public static final String DEFAULT_METHOD_PARAM = "_method";

	private String methodParam = DEFAULT_METHOD_PARAM;


	protected void initFilterBean() throws ServletException {
		try {
			initParameter(getFilterConfig());
		} catch (IOException e) {
			throw new ServletException("init paramerter error",e);
		}
	}

	private void initParameter(FilterConfig filterConfig) throws IOException {
		this.methodParam = getStringParameter(filterConfig, "methodParam", DEFAULT_METHOD_PARAM);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String paramValue = request.getParameter(this.methodParam);
		if (StringUtils.hasLength(paramValue)) {
			String method = paramValue.toUpperCase(Locale.ENGLISH);
			HttpServletRequest wrapper = new HttpMethodRequestWrapper(request, method);
			filterChain.doFilter(wrapper, response);
		} else {
			filterChain.doFilter(request, response);
		}
	}

	private String getStringParameter(FilterConfig filterConfig,String name,String defaultValue) {
		String value = filterConfig.getInitParameter(name);
		if(value == null || "".equals(value.trim())) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * Simple {@link HttpServletRequest} wrapper that returns the supplied method for
	 * {@link HttpServletRequest#getMethod()}.
	 */
	private static class HttpMethodRequestWrapper extends HttpServletRequestWrapper {

		private final String method;

		public HttpMethodRequestWrapper(HttpServletRequest request, String method) {
			super(request);
			this.method = method;
		}

		@Override
		public String getMethod() {
			return this.method;
		}
	}

}
