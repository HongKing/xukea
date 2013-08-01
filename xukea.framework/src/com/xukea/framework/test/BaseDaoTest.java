package com.xukea.framework.test;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dao 测试基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2013-01-05
 */
@Transactional
@TransactionConfiguration(transactionManager="transactionManager")
public class BaseDaoTest extends BaseTest {
	
}