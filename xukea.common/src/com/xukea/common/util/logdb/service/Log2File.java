/*
 * @(#)version 1.0 2009-1-12
 * @author hujun
 * AccountFile.java
 * Copyright 2008 CSS WEB Microsystems, Inc. All rights reserved.
 * CSS WEB ROOM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.xukea.common.util.logdb.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log2File {
	
	private final static String fileName = "log_";
	
	/**
	 * 获得当前系统的时间
	 * @return
	 */
    public static String getTime(){   
        SimpleDateFormat simpledateformat = new SimpleDateFormat("yyyy-MM-dd");   
        return simpledateformat.format(new Date());   
    }
    
    public static void writeLogs(String ... strs) {
    	StringBuffer sb = new StringBuffer();
		try {
//			RandomAccessFile rf = new RandomAccessFile(System.getProperty("user.dir")+ File.separatorChar + fileName + getTime() + ".txt","rw");
			RandomAccessFile rf = new RandomAccessFile("/u01/log/weblogin" + File.separatorChar + fileName + getTime() + ".txt","rw");
			rf.seek(rf.length());//将指针移动到文件末尾 
			for(int i=0; i<strs.length; i++ ) {
				sb.append(strs[i]+" ");
			}
			sb.append("\n");
			rf.write(sb.toString().getBytes());
			rf.close();//关闭文件流 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public static void writeLogs2(String ... strs) {
    	StringBuffer sb = new StringBuffer();
		try {
//			RandomAccessFile rf = new RandomAccessFile(System.getProperty("user.dir")+ File.separatorChar + fileName + getTime() + ".txt","rw");
			RandomAccessFile rf = new RandomAccessFile("/u01/log/weblogin" + File.separatorChar + "errpwd.txt","rw");
			rf.seek(rf.length());//将指针移动到文件末尾 
			for(int i=0; i<strs.length; i++ ) {
				sb.append(strs[i]+"\t");
			}
			sb.append("\n");
			rf.write(sb.toString().getBytes());
			rf.close();//关闭文件流 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
//	public static void main(String[] args) {
//		writeLogs("方玉洁","123456","lovefyj");
//	}
}
