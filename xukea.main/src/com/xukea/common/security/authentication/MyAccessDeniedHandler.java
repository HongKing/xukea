package com.xukea.common.security.authentication;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import com.xukea.common.util.WebUtil;

public class MyAccessDeniedHandler extends AccessDeniedHandlerImpl {

	@Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {
    	
        boolean isAjax = WebUtil.isAjaxRequest(request);
        //如果是ajax请求  
        if (isAjax) {
            String jsonObject = "{\"message\":\"You are not privileged to request this resource.\","+  
                  "\"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";  
            String contentType = "application/json";  
            response.setContentType(contentType);  
//            String jsonObject="noright";  
            PrintWriter out = response.getWriter();  
            out.print(jsonObject);  
            out.flush();  
            out.close();
        } else {
        	super.handle(request, response, accessDeniedException);
        }
    }
	
}  