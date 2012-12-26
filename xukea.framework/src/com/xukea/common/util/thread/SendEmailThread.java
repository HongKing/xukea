package com.xukea.common.util.thread;


import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.xukea.common.util.MessageUtil;
import com.xukea.framework.base.BaseThread;
import com.xukea.framework.util.ContextUtil;
import com.xukea.framework.util.sendmail.MailAttachment;
import com.xukea.system.attachment.model.Attachment;
import com.xukea.system.attachment.service.AttachmentService;
import com.xukea.system.message.model.EmailWait;
import com.xukea.system.message.service.SendEmailService;


/**
 * 邮件自动发送
 * 
 * @author   FishBoy
 * @version  1.0
 * @date     2012.02.16
 */
public class SendEmailThread extends BaseThread {
	private final Logger log = Logger.getLogger(getClass());

	private SendEmailService  sendEmailService;
	private AttachmentService attachmentService;
	/**
	 * 构造方法
	 * @param group
	 */
	public SendEmailThread(ThreadGroup group) {
		super(group, "send_mail");
		
		// Service需要手动加载
		sendEmailService = ContextUtil.getBean(SendEmailService.class);
		attachmentService=ContextUtil.getBean(AttachmentService.class);
	}
	
	/**
	 * 线程主入口
	 */
	public void run() {
		this.setStatus(NEW);
		while(running){
			this.setStatus(WORKING);
			
			List<EmailWait> list = sendEmailService.getListEmail();
			
			for(EmailWait temp : list){
				//收件人地址
				List<String> toEmails = new ArrayList<String>();
				toEmails.add(temp.getToaddr());
				
				//待发附件
				List<MailAttachment> files = new ArrayList<MailAttachment>();
				//从 email wailt 获取 附件ID
				String fids = temp.getFileIds();
				//通过附件ID 获取附件内容
				List<Attachment> filelast = attachmentService.getListByIds(fids);
				for(Attachment item : filelast){
					try {
						MailAttachment file = new MailAttachment(item.getFileUrl());
						file.setAliasName(item.getFileName());
						files.add(file);
					} catch (Exception e) { 
						log.error("附件读取失败："+item.toString(), e);
					}
				}
				
				boolean flag = MessageUtil.getInstance().sendMail(temp.getSubject(), temp.getContent(), toEmails, files);
				if(flag){
					sendEmailService.deleteEmailById(temp);
				}
			}
			
			try {
				this.setStatus(WAITING);
				sleep(300000);// 1/2 * 60 * 1000
			} catch (Exception e) {
			}
		}
		
		this.setStatus(STOPED);
	}
}