package com.xukea.framework.test;


import org.apache.log4j.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基类，引入了classpath*:spring/*.xml及file:webapp/WEB-INF/springmvc-servlet.xml配置
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2013-01-05
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:spring/*.xml",
		"file:webapp/WEB-INF/springmvc-servlet.xml",})
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
    protected final Logger log = Logger.getLogger(this.getClass());
    
}