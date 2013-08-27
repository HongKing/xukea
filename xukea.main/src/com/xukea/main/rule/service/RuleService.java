package com.xukea.main.rule.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.common.util.sequence.service.SequenceService;
import com.xukea.framework.base.BaseService;
import com.xukea.framework.util.PageList;
import com.xukea.main.rule.dao.RuleDao;
import com.xukea.main.rule.model.Rule;

/**
 * 数据权限Service
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Service
public class RuleService extends BaseService{
	
	@Resource
	private RuleDao ruleDao;//处理规则的dao
	
	@Resource
	private SequenceService sequenceService; //序号自动生成的Service
	
	/**
	 * 分页查询数据规则
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public PageList<Rule> getRule4PageList(int page, int pageSize){
		return ruleDao.getRule4PageList(page, pageSize);
	}
	
	/**
	 * 查询所有的数据规则
	 * @return
	 */
	public List<Rule> getAllRule(){
		return ruleDao.getAllRule();
	}
	
	/**
	 * 保存新的数据规则
	 * @param rule
	 * @return
	 */
	public boolean saveRule(Rule rule){
		long rid = sequenceService.getNextId("ACC_RULES");
		rule.setId(rid);
		int temp = ruleDao.saveRule(rule);
		return temp>0;
	}

    /**
     * 获取单个数据规则
     * @param rid
     * @return
     */
	public Rule getRuleById(Long rid){
		return ruleDao.getRuleById(rid);
	}

	/**
	 * 更新数据规则
	 * @param rule
	 * @return
	 */
	public boolean updateRule(Rule rule){
		return ruleDao.updateRule(rule)>0?true:false;
	}

	/**
	 * 删除数据规则
	 * @param rid
	 * @return
	 */
	public boolean delRule(Long rid){
		return ruleDao.delRule(rid)>0?true:false;
	}
}
