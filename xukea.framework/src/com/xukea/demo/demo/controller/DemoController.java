package com.xukea.demo.demo.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.xukea.common.exception.ForbiddenException;
import com.xukea.common.exception.PageNotFoundException;
import com.xukea.common.exception.UnLoginException;
import com.xukea.common.util.FileUploadUtil;
import com.xukea.common.util.WebUtil;
import com.xukea.common.validator.CapthcaValidator;
import com.xukea.common.validator.FileUploadValidator;
import com.xukea.framework.base.BaseRestSpringController;


@Controller
@RequestMapping("/demo")
public class DemoController extends BaseRestSpringController<Object, String>{

	// URL基础路径
	private final String ACT_URL = "/demo";
	
	// JSP文件夹目录
	private final String JSP_DIR = "/demo";

	/**
	 * 首页
	 */
	@Override
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Object obj) {
		ModelAndView result = new ModelAndView();
		result.setViewName( JSP_DIR + "/index" );
		return result;
	}

	/**
	 * 异常DEMO
	 * 
	 * @param request
	 * @param response
	 * @param code
	 * @throws Exception
	 */
	@RequestMapping(value="/error/{code}")
	public void exception(HttpServletRequest request, HttpServletResponse response, @PathVariable String code) throws Exception {
		if("403".equals(code)){
			throw new ForbiddenException();
		}else if("404".equals(code)){
			throw new PageNotFoundException();
		}else if("500".equals(code)){
			throw new Exception("500 exception");
		}else if("unlogin".equals(code)){
			throw new UnLoginException();
		}else{
			throw new Exception("other exception");
		}
	}

	/**
	 * 验证码验证
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value="/validate/captcha")
	public void validateCap(HttpServletRequest request, HttpServletResponse response,
			@RequestParam("capVal") String vnum) throws Exception {
//		String vnum = WebUtil.getValueString(request, "capVal", "");
		
		boolean flag = new CapthcaValidator().validate(request, vnum);
		if( flag){
			this.outputSuccessJSON(request, response, "验证成功");
		}else{
			this.outputErrorJSON(request, response, "验证码错误");
		}
	}

	/**
	 * 文件上传
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value="/upload")
	public String fileupload(MultipartHttpServletRequest request, HttpServletResponse response,
			Object obj, BindingResult result) throws Exception {
		
		MultipartFile file = request.getFile("userlist");
		new FileUploadValidator().validate(file, result);

		String temp = "";
		if(result.hasGlobalErrors()){
			temp = result.getGlobalError().getDefaultMessage();
		}else{
			temp = FileUploadUtil.upload(file);
		}
		request.setAttribute("message", temp);
		return JSP_DIR + "/fileupload";
	}

	/**
	 * 直接输出JSON字符串
	 * @param request
	 * @param response
	 * @param username
	 * @param year
	 * @param month
	 * @throws Exception
	 */
	@RequestMapping(value="/blog/{username}/{year}/{month}")
	public void getList(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable String username, @PathVariable Long year, @PathVariable Long month) 
	throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", username);
		json.put("year"    , year);
		json.put("month"   , month);
		this.outputJSON(request, response, json.toString());
	}
}

