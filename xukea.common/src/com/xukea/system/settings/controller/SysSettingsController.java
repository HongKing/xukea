package com.xukea.system.settings.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.util.WebUtil;
import com.xukea.common.util.cache.Config;
import com.xukea.framework.base.BaseRestSpringController;
import com.xukea.system.settings.model.SysSettings;
import com.xukea.system.settings.service.SysSettingsService;

/**
 * 系统设置
 * @author 石头
 *
 */
@Controller
@RequestMapping("/system/settings")
public class SysSettingsController extends BaseRestSpringController<SysSettings, Long>{
	

	@Resource
	private SysSettingsService sysSettingsService;
	
	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, SysSettings obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName("/system/index");
		return result;
	}

	 
	/**
	 * 保存设置
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/save")
	public void sysEmailSettings(HttpServletRequest request, HttpServletResponse response){
		// get value from request
		String[] keys    = request.getParameterValues("key");
		String[] vals    = request.getParameterValues("val");
		String[] remarks = request.getParameterValues("remark");
		
		//判断是否有输入值
		if(keys==null || keys.length==0){
			this.outputErrorJSON(request, response, "请输入key");
			return;
		}
		//删除原有数据
		String group = WebUtil.getValueString(request, "group", "");
		if(group!=null && !"".equals(group)){
			sysSettingsService.deleteByPreName(group);
		}
		
		// save to db
		for(int i=0;i<keys.length;i++){
			//键
			String key = keys[i];
			if(key==null || "".equals(key)) continue;
			//值
			String val = "";
			try{
				val = vals[i];
			}catch(Exception e){}
			//备注
			String remark = "";
			try{
				remark = remarks[i];
			}catch(Exception e){}
			
			//保存
			key = key.trim();
			sysSettingsService.insertOrUpdage(key, val, remark);
		}
		
		// update cache
		Config.getInstance().refresh();
		
		// output
		this.outputSuccessJSON(request, response, "处理成功");
	}

}
 

	 

	

