package com.xukea.system.attachment.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.xukea.framework.base.BaseDao;
import com.xukea.system.attachment.model.Attachment;

/**
 * 附件DAO
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
@Repository
public class AttachmentDao  extends BaseDao {
    private String namespace = "com.xukea.system.attachment.model.Attachment";
    
	/**
	 * 插入
	 * @param data
	 * @return
	 */
	public int insert(Attachment data){
		return getSqlSessionTemplate().insert(namespace + ".insert", data);
	}

	/**
	 * 获取单条信息
	 * @param map
	 * @return
	 */
	public Attachment getFileById(long fid){
		return (Attachment)getSqlSessionTemplate().selectOne(namespace + ".getFileById", fid);
	}

	/**
	 * 获取附件列表
	 * @param map
	 * @return
	 */
	public List<Attachment> getListByIds(String fids){
		return getSqlSessionTemplate().selectList(namespace + ".getListByIds", fids);
	}
	
}
