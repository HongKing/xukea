package com.xukea.main.rule.dao;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.xukea.common.util.logdb.service.LogDBService;
import com.xukea.framework.base.BaseDao;
import com.xukea.framework.util.PageList;
import com.xukea.main.rule.model.Rule;

/**
 * 数据权限Dao
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Repository
public class RuleDao  extends BaseDao{
    private String namespace = "com.xukea.main.rule.model.Rule";

	@Resource
	private LogDBService    logDBService;     //系统日志保存
	
	/**
	 * 分页查询数据规则
	 * 
	 * @param page
	 * @param pageSize
	 * @return
	 */
    public PageList<Rule> getRule4PageList(int page, int pageSize){
    	return this.getSqlSessionTemplate().selectPageList(namespace+".getAllRule", page, pageSize);
    }
    
	/**
	 * 查询所有的数据规则
	 * @return
	 */
    public List<Rule> getAllRule(){
    	return this.getSqlSessionTemplate().selectList(namespace+".getAllRule");
    }
    
    /**
     * 保存数据规则
     * @param rule
     * @return
     */
    public int saveRule(Rule data){
		try{
			return this.getSqlSessionTemplate().insert(namespace+".insert", data);
		}catch(Exception e){
			logDBService.saveSysErrorLog("ACC_RULES", data, e);
			log.error("save ACC_RULES:"+data, e);
			return -1;
		}
    }
    
    /**
     * 获取单个数据规则
     * @param rid
     * @return
     */
	public Rule getRuleById(long rid){
		return this.getSqlSessionTemplate().selectOne(namespace+".getRuleById", rid);
	}
	
	/**
	 * 更新数据规则
	 * @param rule
	 * @return
	 */
	public int updateRule(Rule data){
		try{
			return this.getSqlSessionTemplate().update(namespace+".updateRule", data); 
		}catch(Exception e){
			logDBService.saveSysErrorLog("ACC_RULES", data, e);
			log.error("update ACC_RULES:"+data, e);
			return -1;
		}
	}	
	
	/**
	 * 删除数据规则
	 * @param rid
	 * @return
	 */
	public int delRule(long rid){
		try{
			return this.getSqlSessionTemplate().delete(namespace+".delRule", rid); 
		}catch(Exception e){
			logDBService.saveSysErrorLog("ACC_RULES", rid, e);
			log.error("delete ACC_RULES:"+rid, e);
			return -1;
		}
	}
    
}
