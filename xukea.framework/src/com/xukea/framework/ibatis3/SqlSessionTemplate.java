package com.xukea.framework.ibatis3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.support.JdbcAccessor;

import com.xukea.framework.util.PageList;

/**
 * SQL模板
 * @author FishBoy
 *
 */
public class SqlSessionTemplate extends JdbcAccessor {
	SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}


	private SqlSession session = null;
	private Connection conn = null;
	private PreparedStatement pstmt = null;

	/**
	 * 执行SQL<br>
	 * <code>
	 * 处理完返回的游标结果集之后，需要手动调用close()关闭连接<br>
	 * 不支持并发，故不推荐使用。如需执行SQL，请在Dao类中使用以下代码：<br>
	 * <pre>
	 * SqlSession session = null;
	 * Connection conn = null;
	 * PreparedStatement pstmt = null;
	 * try {
	 *     session = getSqlSessionFactory().openSession();
	 *     conn  = session.getConnection();
	 *     pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
	 *     pstmt = getSqlSessionTemplate().bindParamter(pstmt, parameter);
	 *     ResultSet rs = pstmt.executeQuery();
	 *     while(rs.next()){
	 *         //get result list
	 *     }
	 * } catch(SQLException e) {
	 *     //do something
	 * } finally {
	 *     //close connect session
	 * }
	 * </pre>
	 * </code>
	 * @param sql
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet querySql(String sql) throws SQLException {
		return this.querySql(sql, null);
	}
	
	/**
	 * 执行SQL<br>
	 * <code>
	 * 处理完返回的游标结果集之后，需要手动调用close()关闭连接<br>
	 * 不支持并发，故不推荐使用。如需执行SQL，请在Dao类中使用以下代码：<br>
	 * <pre>
	 * SqlSession session = null;
	 * Connection conn = null;
	 * PreparedStatement pstmt = null;
	 * try {
	 *     session = getSqlSessionFactory().openSession();
	 *     conn  = session.getConnection();
	 *     pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
	 *     pstmt = getSqlSessionTemplate().bindParamter(pstmt, parameter);
	 *     ResultSet rs = pstmt.executeQuery();
	 *     while(rs.next()){
	 *         //get result list
	 *     }
	 * } catch(SQLException e) {
	 *     //do something
	 * } finally {
	 *     //close connect session
	 * }
	 * </pre>
	 * </code>
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws SQLException 
	 */
	public ResultSet querySql(String sql, Object[] parameter) throws SQLException {
        session = sqlSessionFactory.openSession();
		conn  = session.getConnection();
		pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
		pstmt = this.bindParamter(pstmt, parameter);
	    return pstmt.executeQuery();
	}
	
	/**
	 * 关闭连接
	 */
	public void close(){
		if(pstmt!=null){
			try {
	        	pstmt.close();
	        } catch (Exception e) {
	        }
		}
		if(conn!=null){
	        try {
	        	conn.close();
	        } catch (Exception e) {
	        }
		}
		if(session!=null){
	        try {
	            session.close();
	        } catch (Exception e) {
	        }
		}
	}
	
	/**
	 * 执行SslMap
	 * @param action
	 * @return
	 */
	public Object execute(SqlSessionCallback action)  {
		SqlSession session = null;
        try {
            session = sqlSessionFactory.openSession();
//            session.getConnection();
            return action.doInSession(session);
        } finally {
        	if(session!=null){
        		try{
        			session.getConnection().close();
        			session.close();
        		}catch(Exception e){
        			e.printStackTrace();
        		}
        	}
        }
	}
	
	/**
	 * 新增
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int insert(final String statement, final Object parameter) {
		return (Integer)execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.insert(statement, parameter);
			}
		});
	}
	
	/**
	 * 删除（无参数）
	 * @param statement
	 * @return
	 */
	public int delete(final String statement) {
		return (Integer)execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.delete(statement);
			}
		});
	}
	
	/**
	 * 删除（带参数）
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int delete(final String statement, final Object parameter) {
		return (Integer)execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.delete(statement, parameter);
			}
		});
	}
	
	/**
	 * 修改
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public int update(final String statement, final Object parameter) {
		return (Integer)execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.update(statement, parameter);
			}
		});
	}

	/**
	 * 获取单条记录（无参数）
	 * @param statement
	 * @return
	 */
	public Object selectOne(final String statement) {
		return execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.selectOne(statement);
			}
		});
	}
	
	/**
	 * 获取单条记录（带参数）
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public Object selectOne(final String statement, final Object parameter) {
		return execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.selectOne(statement, parameter);
			}
		});
	}

	/**
	 * 获取记录集合（无参数）
	 * @param statement
	 * @return
	 */
	public List selectList(final String statement) {
		return (List)execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.selectList(statement);
			}
		});
	}

	/**
	 * 获取记录集合（带参数）
	 * @param statement
	 * @param parameter
	 * @return
	 */
	public List selectList(final String statement, final Object parameter) {
		return (List)execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.selectList(statement, parameter);
			}
		});
	}
	

	/**
	 * 获取记录集合（带参数，支持分页）
	 * @param statement
	 * @param page
	 * @return
	 */
    public PageList getPageForList(String statement, PageList page){
    	return this.getPageForList(statement, null, page);
    }

	/**
	 * 获取记录集合（带参数，支持分页）
	 * @param statement
	 * @param parameter
	 * @param page
	 * @return
	 */
    public PageList getPageForList(String statement, Object parameter, PageList page){
	    Configuration configuration = sqlSessionFactory.getConfiguration();
    	MappedStatement ms = configuration.getMappedStatement(statement);
    	BoundSql boundSql = ms.getBoundSql(parameter);
    	
    	//拼接查询总记录数的SQL
		String sql = boundSql.getSql().trim();
		sql = sql.replaceAll("\\n", " ");
		sql = sql.replaceAll("\\r", " ");
		sql = sql.replaceAll("\\t", " ");
		
		if ( sql.toLowerCase().endsWith(" for update") ) {
			sql = sql.substring( 0, sql.length()-11 );
		}
		sql = sql.substring(sql.toLowerCase().indexOf(" from "), sql.length());
		int idx = sql.toLowerCase().indexOf("order by");
		if(idx!=-1){
			sql = sql.substring(0, idx);
		}
		idx = sql.toLowerCase().indexOf("group on");
		if(idx!=-1){
			sql = sql.substring(0, idx);
		}
		sql = "SELECT count(*) " + sql;
		
		List<ParameterMapping> parameterMaps = boundSql.getParameterMappings();  
		Object[] parameterArray = null;
		//获取SQL的参数
		if(parameterMaps != null){
			parameterArray = new Object[parameterMaps.size()];  
		    MetaObject metaObject = parameter == null ? null : MetaObject.forObject(parameter);  
		    for (int i = 0, paramNum = parameterMaps.size(); i < paramNum; i++) {  
		    	ParameterMapping paramMap = parameterMaps.get(i);
		    	if (paramMap.getMode() != ParameterMode.OUT) {
		    		Object value;  
		            String propertyName = paramMap.getProperty();  
		            PropertyTokenizer prop = new PropertyTokenizer(propertyName);  
		            if (parameter == null) {
		            	value = null;  
		            } else if (ms.getConfiguration().getTypeHandlerRegistry().hasTypeHandler(parameter.getClass())) {
		            	value = parameter;  
		            } else if (boundSql.hasAdditionalParameter(propertyName)) {
		            	value = boundSql.getAdditionalParameter(propertyName);  
		            } else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX) && boundSql.hasAdditionalParameter(prop.getName())) {
		            	value = boundSql.getAdditionalParameter(prop.getName());
		            	if (value != null) {
		            		value = MetaObject.forObject(value).getValue(propertyName.substring(prop.getName().length()));
		            	}  
		            } else {  
		              value = metaObject == null ? null : metaObject.getValue(propertyName);  
		            }  
		            parameterArray[i] = value;
		        }
		    }
		}
	    
		//获取总记录数
		SqlSession session = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			session = sqlSessionFactory.openSession();
			conn  = session.getConnection();
			pstmt = conn.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE);
			pstmt = this.bindParamter(pstmt, parameterArray);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				page.setTotalCount(rs.getInt(1));
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(pstmt!=null){
				try {
		        	pstmt.close();
		        } catch (Exception e) {
		        }
			}
			if(conn!=null){
		        try {
		        	conn.close();
		        } catch (Exception e) {
		        }
			}
			if(session!=null){
		        try {
		            session.close();
		        } catch (Exception e) {
		        }
			}
		}
		
		List list = this.selectList(statement, parameter, page.getFirstResultNumber(), page.getPageSize());
		page.setResult(list);
    	return page;
    }
    
	/**
	 * 获取记录集合（带参数，支持分页）
	 * @param statement
	 * @param parameter
	 * @param offset
	 * @param limit
	 * @return
	 */
	private List selectList(final String statement, final Object parameter, final int offset, final int limit) {
		return (List)execute(new SqlSessionCallback() {
			public Object doInSession(SqlSession session) {
				return session.selectList(statement, parameter, new RowBounds(offset, limit));
			}
		});
	}
	
	/**
	 * SQL参数绑定
	 * @param pstmt
	 * @param parameter
	 * @return
	 */
	public PreparedStatement bindParamter(PreparedStatement pstmt, Object[] parameter){
		if(parameter!=null && parameter.length>0){
		    for (int i = 0, paramNum = parameter.length; i < paramNum; i++) {
		    	try {
		    		if (null != parameter[i] && parameter[i] instanceof java.util.Date) {
		    			pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) parameter[i]).getTime()) );
	                }else if (null != parameter[i] && parameter[i] instanceof java.sql.Date) {
		    			pstmt.setDate(i + 1, (java.sql.Date) parameter[i]);
	                } else {
	                	pstmt.setObject(i + 1, parameter[i]);
	                }
		    	} catch (SQLException e) {
		    		e.printStackTrace();
		    	}
		    }
		}
		return pstmt;
	}
} 