package com.xukea.test;  
  

import org.junit.Test;  
import org.springframework.mock.web.MockHttpServletRequest;  
import org.springframework.mock.web.MockHttpServletResponse;  
import org.springframework.web.servlet.ModelAndView;  

import com.xukea.framework.test.BaseControllerTest;

/**
 * 
 * @author  石头
 * @version 1.0
 * @date    2013-1-5
 */
public class DemoControllerTest extends BaseControllerTest {

    @Test  
    public void testAccountList() throws Exception{  
        MockHttpServletRequest  request  = new MockHttpServletRequest();  
        MockHttpServletResponse response = new MockHttpServletResponse();
        
        request.setServletPath("/index"); 
        
        ModelAndView mav = this.excuteAction(request, response);  
        System.out.println(mav);
        System.out.println(mav.getViewName());
    }  
} 