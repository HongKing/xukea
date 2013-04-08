package com.xukea.common.resolver;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.exception.ForbiddenException;
import com.xukea.common.exception.PageNotFoundException;
import com.xukea.common.exception.UnLoginException;
import com.xukea.common.exception.UnauthorizedException;
import com.xukea.common.util.WebUtil;
import com.xukea.common.util.cache.Config;
import com.xukea.framework.base.BaseConstants;
import com.xukea.framework.base.BaseExceptionResolver;

/**
 * 全局异常处理
 */
public class GlobalExceptionResolver extends BaseExceptionResolver{
	private final Logger log = Logger.getLogger(getClass());
	
	private static String ERROR_PAGE_403 = Config.getInstance().getString("error.pageview.403");
	private static String ERROR_PAGE_404 = Config.getInstance().getString("error.pageview.404");
	private static String ERROR_PAGE_500 = Config.getInstance().getString("error.pageview.500");
	private static String ERROR_PAGE_login = Config.getInstance().getString("error.pageview.login");
	
	@Override
    public ModelAndView resolveException(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
		log.error("全局异常捕获: " + ex);

		if(ex instanceof UnLoginException){
			return doUnLogin(request, response, ex);
		}else if(ex instanceof ForbiddenException){
			return do403(request, response, ex);
		}else if(ex instanceof PageNotFoundException){
			return do404(request, response, ex);
//		}else if(ex instanceof TypeMismatchException){
//			return do500(request, response, ex);
		}else if(ex instanceof UnauthorizedException){
			return doUnauthorized(request, response, ex);
		}else{
			return do500(request, response, ex);
		}
    }

	/**
	 * unLogin错误
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView doUnLogin(HttpServletRequest request, HttpServletResponse response, Exception ex){
		if(WebUtil.isAjaxRequest(request)){
			this.outputErrorJSON(request, response, BaseConstants.HTTP_UNLOGIN, "未登录");
			return null;
		}else{
			return new ModelAndView(ERROR_PAGE_login);
		}
	}
	/**
	 * 403错误
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView do403(HttpServletRequest request, HttpServletResponse response, Exception ex){
		if(WebUtil.isAjaxRequest(request)){
			this.outputErrorJSON(request, response, BaseConstants.HTTP_FORBIDDEN, "禁止访问");
			return null;
		}else{
			return new ModelAndView(ERROR_PAGE_403);
		}
	}

	/**
	 * 404错误
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView do404(HttpServletRequest request, HttpServletResponse response, Exception ex){
		if(WebUtil.isAjaxRequest(request)){
			this.outputErrorJSON(request, response, BaseConstants.HTTP_NOT_FOUND, "页面不存在");
			return null;
		}else{
			return new ModelAndView(ERROR_PAGE_404);
		}
	}

	/**
	 * 500错误
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView do500(HttpServletRequest request, HttpServletResponse response, Exception ex){
		if(WebUtil.isAjaxRequest(request)){
			this.outputErrorJSON(request, response, BaseConstants.HTTP_SERVER_ERROR, ex.getMessage());
			return null;
		}else{
			return new ModelAndView(ERROR_PAGE_500, "errmsg", ex);
		}
	}

	/**
	 * 未授权错误
	 * @param request
	 * @param response
	 * @param ex
	 * @return
	 */
	private ModelAndView doUnauthorized(HttpServletRequest request, HttpServletResponse response, Exception ex){
		StringBuffer data = new StringBuffer();
		
		data.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Strict//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd\">");
		data.append("<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"zh\" lang=\"zh\">");
		data.append("<head>");
		data.append("	<meta http-equiv=\"content-style-type\" content=\"text/css\" />");
		data.append("	<meta http-equiv=\"content-type\" content=\"text/html; charset=utf-8\" />");
		data.append("	<title>系统错误</title>");
		data.append("	<style >");
		data.append("	    html { font-size: 100.01%; min-height: 100%; margin-bottom: 1px; height: 100%; }");
		data.append("	    body { margin: 0; padding: 0; color: #fff; font: 62.5%/1.4 Georgia,serif; }");
		data.append("	    * { margin-top: 0; margin-bottom: 0; padding-top: 0; padding-bottom: 0; }");
		data.append("	    .simple-tips { background: #FFFFCA;border: 1px solid #E0DBC2;border-radius: 3px 3px 3px 3px;box-shadow: 0 1px 1px #F0F0F0;color: #A79955;font-size: 11px;line-height: 20px; margin: 0 0 20px;padding: 12px 17px;position: relative;}");
		data.append("	    .simple-tips h2 {color: #6D612E;display: block;font-size: 12px;}");
		data.append("	</style>");
		data.append("</head>");
		data.append("<body>");
		data.append("<div style=\"padding:20px;\">");
		data.append("    <div class=\"simple-tips\" >");
		data.append("        <h2>系统错误，请联系我们</h2>");
		data.append("        <ul>");
		data.append("            <li>1. 电话咨询：400-606-5630</li>");
		data.append("            <li>2. 邮件咨询：service@xukeaonline.com</li>");
		data.append("            <li>3. 公司地址：北京市海淀区西四环北路125号德润科技大厦713室</li>");
		data.append("        </ul>");
		data.append("    </div>");
		data.append("</div>");
		data.append("</body>");
		data.append("</html>");
		
		try {
			response.setContentType("text/html; charset=utf-8");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Pragma", "no-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			
			PrintWriter out = new PrintWriter(new OutputStreamWriter(response.getOutputStream(), "UTF-8"));
			out.println( data.toString() );
			out.close();
//			response.getWriter().write(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}