package com.xukea.framework.ibatis3;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.managed.ManagedTransactionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.util.Assert;

public class SqlSessionFactoryFactoryBean implements FactoryBean<Object>, InitializingBean {
	protected final Logger log = Logger.getLogger(this.getClass());
	private Resource configLocation;
	private Resource[] mapperLocations;
	private DataSource dataSource;
	private boolean useTransactionAwareDataSource = true;
	
	SqlSessionFactory sqlSessionFactory;
	
	/**
	 * 配置加载完成之后处理
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(configLocation,"configLocation must be not null");
		
		sqlSessionFactory = createSqlSessionFactory();
	}
	
	/**
	 * 创建SqlSession
	 * @return
	 * @throws Exception
	 */
	private SqlSessionFactory createSqlSessionFactory() throws Exception {
		Reader reader = new InputStreamReader(getConfigLocation().getInputStream());
		try {
			SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
			Configuration conf = sqlSessionFactory.getConfiguration();
			if(dataSource != null) {
				DataSource dataSourceToUse = this.dataSource; 
				if (this.useTransactionAwareDataSource  && !(this.dataSource instanceof TransactionAwareDataSourceProxy)) {  
		            dataSourceToUse = new TransactionAwareDataSourceProxy(this.dataSource);  
		        }
				
				conf.setEnvironment(new Environment(dataSource.getConnection().toString(),new ManagedTransactionFactory(),dataSourceToUse));
				sqlSessionFactory = new SqlSessionFactoryBuilder().build(conf);
			}

			if(mapperLocations != null) {
				Map<String, XNode> sqlFragments = new HashMap<String, XNode>();
				for(Resource r : mapperLocations) {
					log.info("Loading iBatis3 mapper xml from file["+r.getFile().getAbsolutePath()+"]");
					
					Reader mapperReader = new InputStreamReader(r.getInputStream());
					try {
						XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(mapperReader,conf,r.getFile().getAbsolutePath(),sqlFragments);
						mapperBuilder.parse();
					}finally {
						mapperReader.close();
					}
				}
			}

			return sqlSessionFactory;
		}finally {
			reader.close();
		}
	}
	
	public SqlSessionFactory getObject() {
		return sqlSessionFactory;
	}

	public Class<? extends SqlSessionFactory> getObjectType() {
		return (this.sqlSessionFactory != null ? this.sqlSessionFactory.getClass() : SqlSessionFactory.class);
	}

	public boolean isSingleton() {
		return true;
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Resource getConfigLocation() {
		return configLocation;
	}

	public void setConfigLocation(Resource configurationFile) {
		this.configLocation = configurationFile;
	}

	public void setMapperLocations(Resource[] mapperLocations) {
		this.mapperLocations = mapperLocations;
	}
	
	public void setUseTransactionAwareDataSource(boolean useTransactionAwareDataSource) {   
		this.useTransactionAwareDataSource = useTransactionAwareDataSource;   
	} 
	
}
