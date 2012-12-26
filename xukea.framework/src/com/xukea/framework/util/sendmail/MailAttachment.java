package com.xukea.framework.util.sendmail;

import java.io.File;
import java.net.URI;

/**
 * 邮件附件
 * @author FishBoy
 * @version
 * @date   2012.02.09
 */
public class MailAttachment extends File{
	private static final long serialVersionUID = -6162496934460350687L;
	
	private String alias;


	public MailAttachment(URI uri) {
		super(uri);
	}

	public MailAttachment(String pathname) {
		super(pathname);
	}
	
	public MailAttachment(File parent, String child) {
		super(parent, child);
	}

	public MailAttachment(String parent, String child) {
		super(parent, child);
	}
	
	/**
	 * 获取附件名称
	 * @return
	 */
	public String getAliasName() {
		String ext = this.getName().substring(this.getName().lastIndexOf("."));
		if(alias!=null && !"".equals(alias)){
			if(alias.indexOf(".")>0){
				return alias;
			}
			return alias + ext;
		}
		
		return this.getName();
	}

	/**
	 * 设置附件别名
	 * @param alias
	 */
	public void setAliasName(String alias) {
		this.alias = alias;
	}
}
