package com.xukea.demo.account.dao;


import org.springframework.stereotype.Repository;

import com.xukea.demo.account.model.Account;
import com.xukea.framework.base.BaseDao;
import com.xukea.framework.util.PageList;

@Repository
public class AccountDao  extends BaseDao<Account, Long>{
    private String namespace = "com.xukea.demo.account.model.Account";

    /**
	 * 分页查询数据
	 * @param page
	 * @return
	 */
	public PageList<Account> getPageForList(PageList<Account> page){
		return getSqlSessionTemplate().selectPageList(namespace +".getList", page);
	}

}
