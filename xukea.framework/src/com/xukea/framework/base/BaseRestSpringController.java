package com.xukea.framework.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * 定义标准的rest方法以对应实体对象的操作,以达到统一rest的方法名称,
 * 还可以避免子类需要重复编写@RequestMapping annotation.
 * 
 * 子类要实现某功能只需覆盖下面的方法即可.
 * 注意: 覆盖时请使用@Override,以确保不会发生错误
 * <pre>
 * /userinfo                => index()  
 * /userinfo/new            => _new()  
 * /userinfo/new    POST    => create()
 * /userinfo/{id}   GET     => show()    
 * /userinfo/{id}/edit      => edit()  
 * /userinfo/{id}   PUT     => update()  
 * /userinfo/{id}   DELETE  => delete()  
 * /userinfo/delete DELETE  => batchDelete()  
 * </pre>
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public abstract class BaseRestSpringController extends BaseSpringController {
	
	//特别说明: 由于spring的方法参数映射太过于灵活,如果以下参数不适应你,请自己修改参数并修改代码生成器模板
	// 如果你不喜欢 HttpServletRequest request,HttpServletResponse response作为方法参数，也请删除

	/** 默认 */
	@RequestMapping
	public abstract ModelAndView index(HttpServletRequest request, HttpServletResponse response);

	/** 首页 */
	@RequestMapping(value="/index")
	public ModelAndView _index(HttpServletRequest request, HttpServletResponse response) {
		return index(request, response);
	}
	
//	/** 进入新增 */
//	@RequestMapping(value="/new")
//	public ModelAndView _new(HttpServletRequest request, HttpServletResponse response, Entity model) throws Exception {
//		throw new UnsupportedOperationException("not yet implement");
//	}
//
//	/** 保存新增 */
//	@RequestMapping(value="/new", method=RequestMethod.POST)
//	public ModelAndView create(HttpServletRequest request, HttpServletResponse response, Entity model) throws Exception {
//		throw new UnsupportedOperationException("not yet implement");
//	}
//	
//	/** 显示 */
//	@RequestMapping(value="/{id}")
//	public ModelAndView show(HttpServletRequest request, HttpServletResponse response, @PathVariable PK id) throws Exception {
//		throw new UnsupportedOperationException("not yet implement");
//	}
//	
//	/** 编辑 */
//	@RequestMapping(value="/{id}/edit")
//	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, @PathVariable PK id) throws Exception {
//		throw new UnsupportedOperationException("not yet implement");
//	}
//	
//	/** 保存更新 */
//	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
//	public ModelAndView update(HttpServletRequest request, HttpServletResponse response, @PathVariable PK id) throws Exception {
//		throw new UnsupportedOperationException("not yet implement");
//	}
//	
//	/** 删除 */
//	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
//	public ModelAndView delete(HttpServletRequest request, HttpServletResponse response, @PathVariable PK id) {
//		throw new UnsupportedOperationException("not yet implement");
//	}
//
//	/** 批量删除 */
//	@RequestMapping(value="/delete", method=RequestMethod.DELETE)
//	public ModelAndView batchDelete(HttpServletRequest request, HttpServletResponse response, @RequestParam("items[]") PK[] items) {
//		throw new UnsupportedOperationException("not yet implement");
//	}
}
