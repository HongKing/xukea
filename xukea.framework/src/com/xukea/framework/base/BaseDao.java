package com.xukea.framework.base;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;

import com.xukea.framework.ibatis3.BaseSqlSessionTemplate;

/**
 * Dao基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public abstract class BaseDao extends DaoSupport {
    protected final Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 数据源对象
     */
    private SqlSessionFactory sqlSessionFactory;
    
    /**
     * sqlMap模板
     */
    private BaseSqlSessionTemplate sqlSessionTemplate;
    
    /**
     * 检测sqlSessionFactory是否为空
     */
	protected void checkDaoConfig() throws IllegalArgumentException {
		if(sqlSessionFactory==null){
			log.error("sqlSessionFactory must be not null");
		}
	}
	
	/**
	 * 自动注入sqlSessionFactory
	 * @param sqlSessionFactory
	 */
	@Autowired //自动注入，防止在RCP等程序中不执行
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionTemplate = new BaseSqlSessionTemplate(sqlSessionFactory);
	}

//	/**
//	 * sqlSessionFactory，暂时无用，
//	 * @return
//	 */
//	public SqlSessionFactory getSqlSessionFactory() {
//		return sqlSessionFactory;
//	}

	/**
	 * SQL执行对象
	 * @return
	 */
    public BaseSqlSessionTemplate getSqlSessionTemplate() {
    	return sqlSessionTemplate;
    }
}
