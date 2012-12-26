package com.xukea.framework.base;

import java.io.Serializable;
import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.support.DaoSupport;

import com.xukea.framework.ibatis3.EntityDao;
import com.xukea.framework.ibatis3.SqlSessionTemplate;


public abstract class BaseDao<E, PK extends Serializable> extends DaoSupport implements EntityDao<E, PK> {
    protected final Logger log = Logger.getLogger(this.getClass());
    
    /**
     * 数据源对象
     */
    private SqlSessionFactory sqlSessionFactory;
    
    /**
     * sqlMap模板
     */
    private SqlSessionTemplate sqlSessionTemplate;
    
	protected void checkDaoConfig() throws IllegalArgumentException {
		log.error("sqlSessionFactory must be not null");
	}

	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}
	
//	@Required
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory);
	}

    public SqlSessionTemplate getSqlSessionTemplate() {
    	return sqlSessionTemplate;
    }
    
    
    //以下实现EntityDao接口
//	/**
//	 * 新增
//	 * <pre>
//	 * 对应于sqlMap中的
//	 * &lt;insert id="insert" parameterType="..."&gt;
//	 * ......
//	 * &lt;/insert&gt;
//	 * </pre>
//	 * @param entity
//	 */
//    public void insert(E entity) {
//		prepareObjectForSaveOrUpdate(entity);
//		getSqlSessionTemplate().insert(getInsertQuery(), entity);    	
//    }
//
//	/**
//	 * 删除
//	 * <pre>
//	 * 对应于sqlMap中的
//	 * &lt;delete id="delete" parameterType="java.lang.Long"&gt;
//	 * ......
//	 * &lt;/delete&gt;
//	 * </pre>
//	 * @param id
//	 */
//	public void delete(PK id) {
//		getSqlSessionTemplate().delete(getDeleteQuery(), id);
//	}
//
//	/**
//	 * 修改
//	 * <pre>
//	 * 对应于sqlMap中的
//	 * &lt;update id="update" parameterType="..."&gt;
//	 * ......
//	 * &lt;/update&gt;
//	 * </pre>
//	 * @param entity
//	 */
//	public void update(E entity) {
//		prepareObjectForSaveOrUpdate(entity);
//		getSqlSessionTemplate().update(getUpdateQuery(), entity);
//	}
//
//	/**
//	 * 查询单条信息
//	 * <pre>
//	 * 对应于sqlMap中的
//	 * &lt;select id="getById" parameterType="java.lang.Long" resultMap="..."&gt;
//	 * ......
//	 * &lt;/select&gt;
//	 * </pre>
//	 * @param id
//	 * @return
//	 */
//    public Object getById(PK id) {
//        Object object = getSqlSessionTemplate().selectOne(getFindByPrimaryKeyQuery(), id);
//        return object;
//    }
//
//	/**
//	 * 查询所有
//	 * <pre>
//	 * 空方法，如有需要请在对应的Dao类中自己完成
//	 * </pre>
//	 * @return
//	 */
//	public List<E> findAll() {
//		throw new UnsupportedOperationException();
//	}
//
//	/**
//	 * 增加或修改
//	 * <pre>
//	 * 空方法，如有需要请在对应的Dao类中自己完成
//	 * </pre>
//	 * @param entity
//	 */
//	public void insertOrUpdate(E entity) {
//		throw new UnsupportedOperationException();
//	}
//
//	/**
//	 * 增加或修改前置方法
//	 * <pre>
//	 * 空方法，用于在增加或者修改之前做一些前置处理
//	 * </pre>
//	 * @param entity
//	 */
//    protected void prepareObjectForSaveOrUpdate(E o) {
//    }
//    
//	public void flush() {
//		//ignore
//	}
//
//	//以下为“增删改”默认对应的sqlMap的ID
//    public String getFindByPrimaryKeyQuery() {
//        return getNamespace() + ".getById";
//    }
//
//    public String getInsertQuery() {
//        return getNamespace() + ".insert";
//    }
//
//    public String getUpdateQuery() {
//    	return getNamespace() + ".update";
//    }
//
//    public String getDeleteQuery() {
//    	return getNamespace() + ".delete";
//    }
//
//    public String getCountQuery() {
//		return getNamespace() + ".count";
//	}
//    
//    public abstract String getNamespace();
}
