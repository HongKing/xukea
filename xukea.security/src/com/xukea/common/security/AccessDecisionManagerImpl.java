package com.xukea.common.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

/**
 * 决策管理器
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class AccessDecisionManagerImpl implements AccessDecisionManager {
    
	/**
     * 判断用户是否有权访问资源
     * Authentication              authentication    用户具有的角色权限 
     * Collection<ConfigAttribute> configAttributes  访问该资源所需的角色权限
     */
    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) 
    		throws AccessDeniedException, InsufficientAuthenticationException {
    	
        Iterator<ConfigAttribute> iter = configAttributes.iterator();
        while (iter.hasNext()) {
            String accessResourceNeedRole = ((SecurityConfig) iter.next()).getAttribute();
            // 如果资源为匿名访问，则放行
            if("ROLE_ANONYMOUS".equals(accessResourceNeedRole)){
            	return;
            }
            // 轮询用户权限，查看是否有匹配角色
            for (GrantedAuthority grantedAuthority : authentication.getAuthorities()) {
                String userOwnRole = grantedAuthority.getAuthority();
                if (accessResourceNeedRole.equals(userOwnRole)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("访问被拒绝");
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

}