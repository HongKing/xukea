package com.xukea.framework.ibatis3;


import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @author  石头
 * @version 1.0
 * @date    2012-12-28
 */
public class BaseSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {

	@Override
	public SqlSessionFactory build(Configuration config) {
		return new BaseSqlSessionFactory(config);
    }

}
