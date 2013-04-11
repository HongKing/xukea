package com.xukea.common.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;

/**
 * 权限资源加载管理<br>
 * 默认所有资源具有ROLE_ANONYMOUS角色
 * @author Administrator
 *
 */
public class SecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	private Collection<ConfigAttribute> DEFAULT_ROLE;
    private Map<String, List<ConfigAttribute>> sourceMap;  // 资源权限
    private Map<String, AntPathRequestMatcher> matcherMap; // 资源权限

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
    	if(sourceMap==null){
    		loadResourceDefine();
    	}
    	HttpServletRequest request = ((FilterInvocation) arg0).getRequest();
        Iterator<String> iter = this.sourceMap.keySet().iterator();
        while (iter.hasNext()) {
            String temp = iter.next();
            AntPathRequestMatcher matcher = matcherMap.get(temp);
            if (matcher.matches(request)) {
                return sourceMap.get(temp);
            }
        } 
        return DEFAULT_ROLE;
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
    
    /**
     * 初始化角色权限信息
     */
    private void loadResourceDefine() {
    	//TODO 
        this.sourceMap  = new HashMap<String, List<ConfigAttribute>>();
        this.matcherMap = new HashMap<String, AntPathRequestMatcher>();
        
        List<ConfigAttribute> list = new ArrayList<ConfigAttribute>();
        ConfigAttribute cb = new SecurityConfig("ROLE_ADMIN"); // 构造一个权限(角色)
        ConfigAttribute cbUser = new SecurityConfig("ROLE_USER"); // 构造一个权限(角色)
        ConfigAttribute cbManager = new SecurityConfig("ROLE_MANAGER"); // 构造一个权限(角色)
        list.add(cb);
        list.add(cbUser);
        list.add(cbManager);
        
        sourceMap.put("/main/user/*", list);
        sourceMap.put("/main/index/*", list);
        sourceMap.put("/index", list);

        matcherMap.put("/main/user/*", new AntPathRequestMatcher("/main/user/*"));
        matcherMap.put("/main/index/*", new AntPathRequestMatcher("/main/index/*"));
        matcherMap.put("/index", new AntPathRequestMatcher("/index"));
    }
}