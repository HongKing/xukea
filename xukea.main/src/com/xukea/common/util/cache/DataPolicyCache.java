package com.xukea.common.util.cache;

import java.util.ArrayList;
import java.util.List;

import com.xukea.framework.base.BaseCache;
import com.xukea.framework.util.ContextUtil;
import com.xukea.main.role.model.Role;
import com.xukea.main.rule.model.Rule;
import com.xukea.main.rule.service.RuleService;

/**
 * 数据权限缓存<br>
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @date   2013.08.21
 */
public class DataPolicyCache extends BaseCache<Rule> {
	private static String GROUP_NAME     = "data_policy";
	private static int    REFRESH_PERIOD = 24*60*60;
	private static Object lock = new Object(); 
	private static DataPolicyCache instance = null;
	
	private RuleService ruleService;

	/**
	 * 构造方法，service需在这里手动获取bean
	 */
	private DataPolicyCache(){
		super(GROUP_NAME, REFRESH_PERIOD);
		// Service需要手动加载
		ruleService = ContextUtil.getBean(RuleService.class);
	}
	
	/**
	 * 单例工厂
	 * @return
	 */
	public static DataPolicyCache getInstance() {
		if (instance == null) {
			synchronized( lock ){   
                if (instance == null){   
                    instance = new DataPolicyCache();   
                }   
            }
		}
		return instance;
	}

	/**
	 * 获取角色对应的规则
	 * @param roles
	 * @return
	 */
	public List<Rule> getRuleByRole(List<Role> roles){
		List<Rule> rules = new ArrayList<Rule>();
		for(Role item : roles){
			List<Rule> temp = this.getRuleByRole(item.getCode());
			if(temp!=null && temp.size()>0){
				rules.addAll(temp);
			}
		}
		return rules;
	}
	
	/**
	 * 获取角色对应的规则
	 * @param roleCode
	 * @return
	 */
	public List<Rule> getRuleByRole(String roleCode){
		return this.getList("role_"+roleCode, false);
	}

	/**
	 * 获取表对应的规则
	 * @param roleCode
	 * @return
	 */
	public List<Rule> getRuleByTable(String tabName){
		return this.getList("table_"+tabName, false);
	}
	
	/**
	 * 刷新缓存
	 */
	public void refresh(){
		this.removeAll(); // 删除所有缓存
		cacheFromDB();     // 缓存DB中的数据
	}
	
	/**
	 * 从DB中获取数据，并缓存
	 */
	private void cacheFromDB(){
		List<Rule> list = ruleService.getAllRule();
		if(list==null || list.size()==0) return;
		
		for(Rule item : list){
			// 缓存单个规则对象
			String key = item.getRoleCode() +"_"+ item.getTabName();
			this.put(key, item);
			// 根据角色缓存规则
			cacheRule4Role(item);
			// 根据表缓存规则
			cacheRule4Table(item);
		}
	}
	
	/**
	 * 根据角色缓存规则
	 * @param rule
	 */
	private void cacheRule4Role(Rule rule){
		String key = rule.getRoleCode();
		List<Rule> rules = this.getRuleByRole(key);
		if(rules==null){
			rules = new ArrayList<Rule>();
		}
		rules.add(rule);
		
		this.update("role_"+key, rules);
	}
	
	/**
	 * 根据表缓存规则
	 * @param rule
	 */
	private void cacheRule4Table(Rule rule){
		String key = rule.getTabName();
		List<Rule> rules = this.getRuleByTable(key);
		if(rules==null){
			rules = new ArrayList<Rule>();
		}
		rules.add(rule);
		
		this.update("table_"+key, rules);
	}
	
}