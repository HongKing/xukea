package com.xukea.framework.ibatis3;

import org.apache.ibatis.exceptions.ExceptionFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.*;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.util.List;

/**
 * SqlSession
 * 
 * @author  石头
 * @version 1.0
 * @date    2012-12-28
 */
public class BaseSqlSession extends DefaultSqlSession {

  private Executor executor;

  public BaseSqlSession(Configuration configuration, Executor executor) {
	  super(configuration, executor);
    this.executor = executor;
  }
  
  /**
   * 根据ms对象执行SQL（主要用于翻页方法，其他地方不建议调用）
   * @param ms
   * @param parameter
   * @param rowBounds
   * @return
   */
  public <E> List<E> selectList(MappedStatement ms, Object parameter, RowBounds rowBounds) {
	  try {
	      List<E> result = executor.<E>query(ms, wrapCollection(parameter), rowBounds, Executor.NO_RESULT_HANDLER);
	      return result;
	    } catch (Exception e) {
	      throw ExceptionFactory.wrapException("Error querying database.  Cause: " + e, e);
	    } finally {
	      ErrorContext.instance().reset();
	    }
  }
  
  private Object wrapCollection(final Object object) {
    if (object instanceof List) {
      StrictMap<Object> map = new StrictMap<Object>();
      map.put("list", object);
      return map;
    } else if (object != null && object.getClass().isArray()) {
      StrictMap<Object> map = new StrictMap<Object>();
      map.put("array", object);
      return map;
    }
    return object;
  }
  
}
