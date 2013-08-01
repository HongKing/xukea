package com.xukea.framework.ibatis3;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseSqlSessionFactoryBuilder extends SqlSessionFactoryBuilder {

	@Override
	public SqlSessionFactory build(Configuration config) {
		return new BaseSqlSessionFactory(config);
    }

}
