package com.xukea.common.util.logdb.service;


import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.xukea.common.util.logdb.dao.LogDBDao;
import com.xukea.common.util.logdb.model.SysErrorLog;
import com.xukea.framework.base.BaseService;


/**
 * 日志记录
 * @author 石头
 *
 */
@Service
public class LogDBService extends BaseService{
	private Logger log = Logger.getLogger(this.getClass());
	
	@Resource
	private LogDBDao logDBDao;
	
	
	
	
	
	
	/**
	 * 记录系统运行错误日志
	 * 
	 * @param errFrom
	 * @param obj
	 * @param e
	 */
	public void saveSysErrorLog(String errFrom, Object obj, Exception e){
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw));

		SysErrorLog data = new SysErrorLog(new Date(), "ERROR", errFrom, obj.toString(), e.getMessage(), sw.toString());
		String namespace = data.getClass().getName();
		try{
			logDBDao.saveLog2DB(namespace, data);
		}catch(Exception ex){
			log.error(ex);
		}
	}
}
