package com.xukea.system.attachment.service;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.xukea.common.util.sequence.service.SequenceService;
import com.xukea.framework.base.BaseService;
import com.xukea.system.attachment.dao.AttachmentDao;
import com.xukea.system.attachment.model.Attachment;

/**
 * 
 * @author 石头
 *
 */
@Service
public class AttachmentService extends BaseService<Attachment, Long> {
	
	@Resource
	private AttachmentDao attachmentDao; 
	
	@Resource
	private SequenceService sequenceService; 
	
	/**
	 * 添加附件
	 * @param fileName
	 * @param fileUrl
	 * @param fileSize
	 * @param fileType
	 * @param remark
	 * @param relateType
	 * @param relateId
	 * @param uploadUser
	 * @param uploadUserName
	 * @return
	 */
	public long insert(String fileName, String fileUrl, float fileSize, String fileType, String remark, long uploadUser){
		long id = sequenceService.getNextId("SYS_ATTACHMENT");
		Attachment data = new Attachment(id, fileName, fileUrl, fileSize, fileType, remark, uploadUser, new Date());

		attachmentDao.insert(data);
		return id;
	}

	/**
	 * 获取单条信息
	 * @param fid
	 * @return
	 */
	public Attachment getFileById(long fid){
		return attachmentDao.getFileById(fid);
	}

	/**
	 * 获取附件列表
	 * @param map
	 * @return
	 */
	public List<Attachment> getListByIds(String ids){
		if(ids==null || "".equals(ids)){
			return null;
		}
		if(ids.endsWith(",")){
			ids = ids.substring(0, ids.length()-1);
		}
		
		return attachmentDao.getListByIds(ids);
	}
	
}
