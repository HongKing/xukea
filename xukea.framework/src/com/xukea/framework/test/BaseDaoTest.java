package com.xukea.framework.test;

import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * Dao 测试基类
 * @author  石头
 * @version 1.0
 * @date    2013-1-5
 */
@Transactional
@TransactionConfiguration(transactionManager="transactionManager")
public class BaseDaoTest extends BaseTest {
	
}