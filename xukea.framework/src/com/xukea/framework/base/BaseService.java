package com.xukea.framework.base;

import java.io.Serializable;
import java.util.List;

import com.xukea.framework.ibatis3.EntityDao;


/**
 * Service基类
 * @author FishBoy
 *
 * @param <E>
 * @param <PK>
 */
public abstract class BaseService <E, PK extends Serializable> {
	
//	/**
//	 * Manager对应的Dao类
//	 * @return
//	 */
//	protected abstract EntityDao<E, PK> getEntityDao();

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
//	public void insert(E entity) {
//		getEntityDao().insert(entity);
//	}
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
//		getEntityDao().delete(id);
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
//		getEntityDao().update(entity);
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
//	public E getById(PK id) {
//		return (E)getEntityDao().getById(id);
//	}
//	
//	/**
//	 * 查询所有
//	 * <pre>
//	 * 空方法，如有需要请在对应的Dao类中自己完成
//	 * </pre>
//	 * @return
//	 */
//	public List<E> findAll() {
//		return getEntityDao().findAll();
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
//		getEntityDao().insertOrUpdate(entity);
//	}
	
}
