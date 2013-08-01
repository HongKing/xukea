package com.xukea.demo.account.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.demo.account.dao.AccountDao;
import com.xukea.demo.account.model.Account;
import com.xukea.demo.userinfo.dao.UserInfoDao;
import com.xukea.framework.base.BaseService;
import com.xukea.framework.util.PageList;

/**
 * 用户Service
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Service
public class AccountService extends BaseService{

	@Resource
	private AccountDao accountDao;

	/**
	 * 在同一个Service里面可以引入多个Dao对象，并使用其中的方法做相关业务逻辑处理
	 */
	@Resource
	private UserInfoDao userInfoDao;
	
	/**
	 * 分页查询数据
	 * @param page
	 * @return
	 */
	public PageList<Account> getPageForList(PageList<Account> page){
		return accountDao.getPageForList(page);
	}
}
