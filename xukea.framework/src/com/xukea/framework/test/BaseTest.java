package com.xukea.framework.test;


import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 测试基类，引入了classpath*:spring/*.xml及file:webapp/WEB-INF/springmvc-servlet.xml配置
 * 
 * @author  石头
 * @version 1.0
 * @date    2013-1-5
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath*:spring/*.xml",
		"file:webapp/WEB-INF/springmvc-servlet.xml",})
public class BaseTest extends AbstractTransactionalJUnit4SpringContextTests {
	
}