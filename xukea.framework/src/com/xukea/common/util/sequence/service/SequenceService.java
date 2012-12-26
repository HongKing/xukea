package com.xukea.common.util.sequence.service;


import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.common.util.sequence.dao.SequenceDao;
import com.xukea.common.util.sequence.model.Sequence;
import com.xukea.framework.base.BaseService;

/**
 * 序列处理
 * @author 石头
 *
 */
@Service
public class SequenceService extends BaseService<Sequence, Long>{

	@Resource
	private SequenceDao sequenceDao;
	
	/**
	 * 获取下一个ID值
	 * @param tabName
	 * @return
	 */
	public synchronized long getNextId(String tabName){
		Sequence seq = sequenceDao.getNextId(tabName);
		if(seq == null){
			seq = new Sequence(tabName, 1);
			sequenceDao.insert(seq);
		}
		long nextid = seq.getNextId();
		seq.setNextId(seq.getNextId()+1);
		sequenceDao.update(seq);
		return nextid;
	}
	
	/**
	 * 获取下一个编号
	 * @param tabName
	 * @return
	 */
	public String getNextCode(String tabName){
		return getNextCode(tabName, null);
	}

	/**
	 * 获取下一个编号（每级最大编号为999）
	 * @param name
	 * @param parent
	 * @return
	 */
	public synchronized String getNextCode(String tabName, String preCode){
		preCode = (preCode==null) ? "" : preCode;
		
		String code = sequenceDao.getNextCode(tabName, preCode);
		if(code == null){
			code = preCode + "001";
		}else{
			long subId = Long.parseLong(code.substring(preCode.length()));
			subId += 1;
			String subStr = subId+"";
			for(;subStr.length()<3;){
				subStr = "0" + subStr;
			}
			code = preCode + subStr;
		}
		return code;
	}
	
	
}
