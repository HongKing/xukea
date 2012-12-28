package com.xukea.framework.ibatis3;


import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * 实例化SqlSession工厂Bean
 * @author  石头
 * @version 1.0
 * @date    2012-12-28
 */
public class BaseSqlSessionFactoryBean extends SqlSessionFactoryBean{
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//强制设置sqlSessionFactoryBuilder
		this.setSqlSessionFactoryBuilder( new BaseSqlSessionFactoryBuilder() );
		super.afterPropertiesSet();
	}
	
}