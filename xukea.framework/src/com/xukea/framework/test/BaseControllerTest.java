package com.xukea.framework.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * Controller测试基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2013-01-05
 */
public class BaseControllerTest extends BaseTest {
	
    @Autowired
    private RequestMappingHandlerAdapter handlerAdapter;

    @Autowired
    private RequestMappingHandlerMapping handlerMapping;
    
    /**
     * 执行请求
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public ModelAndView excuteAction(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HandlerExecutionChain chain = handlerMapping.getHandler(request);
        return handlerAdapter.handle(request, response, chain.getHandler());
    }
}