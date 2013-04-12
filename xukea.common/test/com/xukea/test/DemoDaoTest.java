package com.xukea.test;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.xukea.framework.test.BaseServiceTest;
import com.xukea.system.settings.dao.SysSettingsDao;

/**
 * 
 * @author  石头
 * @version 1.0
 * @date    2013-1-5
 */
public class DemoDaoTest extends BaseServiceTest {

	@Autowired
	private SysSettingsDao sysSettingsDao;
	
    @Test
    public void getSysSetting(){
    	System.out.println("=============");
    	System.out.println(sysSettingsDao.getByName("fileupload.path"));
    }

}