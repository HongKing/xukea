package com.xukea.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.xukea.common.util.cache.SecurityCache;

/**
 * 权限资源加载管理<br>
 * 默认所有资源具有ROLE_ANONYMOUS角色
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private Collection<ConfigAttribute> DEFAULT_ROLE;

    /**
     * 构造每一种资源所需要的角色权限
     */
    public SecurityMetadataSource() {
        super();
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes(){
        return null;
    }

    /**
     * 获取访问某一个url所需的角色
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object arg0) throws IllegalArgumentException {
    	// 获取请求URL
    	HttpServletRequest request = ((FilterInvocation) arg0).getRequest();
    	String url = request.getRequestURI().substring(request.getContextPath().length());
    	// 获取请求URL对应的角色
    	List<ConfigAttribute> list = SecurityCache.getInstance().getRole4URL(url);
    	
    	if(list==null || list.size()==0){
    		return DEFAULT_ROLE;
    	}else{
    		return list;
    	}
    }

    @Override
    public boolean supports(Class<?> clasz) {
        return true;
    }

    /**
     *  设置默认权限
     * @param arg
     */
    public void setDefaultRole(String arg){
    	if(arg==null || "".equals(arg)){
    		arg = "ROLE_ANONYMOUS";
    	}
    	String[] roles = arg.split(",");
    	DEFAULT_ROLE = createAuthorityList(roles);
    }

    /**
     * 批量生成权限信息
     * @param roles
     * @return
     */
    private List<ConfigAttribute> createAuthorityList(String... roles) {
        List<ConfigAttribute> authorities = new ArrayList<ConfigAttribute>(roles.length);
        for (String role : roles) {
            authorities.add(new SecurityConfig(role));
        }
        return authorities;
    }
}