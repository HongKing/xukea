package com.xukea.common.util.jsptag;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.springframework.security.access.ConfigAttribute;

import com.xukea.common.UserBasicInfo;
import com.xukea.common.util.cache.SecurityCache;
import com.xukea.main.role.model.Role;

/**
 * 是否有权限操作某个URL
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class HasRoleTag extends BodyTagSupport{
	
	private String url;
	
    @Override
    public int doStartTag() throws JspException {
    	HttpServletRequest request = (HttpServletRequest)this.pageContext.getRequest();
    	// 若无URL信息，则不显示标签内容
    	if(this.url==null || "".equals(this.url)){
    		return SKIP_BODY;
    	}
    	// 判断传入的URL是否含有ctx前缀，如果有，先去掉ctx前缀
    	String ctx = request.getContextPath(); //应用根目录
    	String urlstr = this.url;
    	if(urlstr.startsWith(ctx)){
    		urlstr = urlstr.substring(ctx.length());
    	}
        // 资源权限列表
        List<ConfigAttribute> urlRoles = SecurityCache.getInstance().getRole4URL(urlstr);
        // 若资源无权限限制，则分析是否是之前对URL做了截取
        if(urlRoles.size()==0){
        	if(!this.url.equals(urlstr)){
        		urlstr = ctx + urlstr;
                urlRoles = SecurityCache.getInstance().getRole4URL(urlstr);
        	}
        }
        // 若资源还是无权限限制，则显示标签内容
        if(urlRoles.size()==0){
        	return EVAL_BODY_INCLUDE;
        }
        // 登录用户信息
    	UserBasicInfo user = UserBasicInfo.getFromSession(request);
        // 若需要权限访问，但无用户信息，则不显示标签内容
        if(user == null){
        	return SKIP_BODY;
        }
        // 用户权限
        List<Role> userRoles = (List<Role>)user.getAttribute("ROLES");
        
        // 判断用户是否有权限操作
        boolean flag = false;
        for(Role role : userRoles){
        	String userRoleName = role.getShortWord();
        	for(ConfigAttribute conf : urlRoles){
        		String urlRoleName = conf.getAttribute();
        		if(userRoleName.equals(urlRoleName)){
        			flag = true;
        		}
        		if(flag) break;
        	}
    		if(flag) break;
        }
        
        if(flag){
        	return EVAL_BODY_INCLUDE;
        }else{
            return SKIP_BODY;
        }
    }

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}