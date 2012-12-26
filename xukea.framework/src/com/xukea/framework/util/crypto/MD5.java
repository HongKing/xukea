package com.xukea.framework.util.crypto;

import java.security.MessageDigest;

import org.apache.log4j.Logger;


/**
 * MD5加密算法类
 * 
 * @author    FishBoy
 * @version   v1.0
 * @date      2011.12.26
 */
public class MD5 {
	// 日志
	private static final Logger log = Logger.getLogger(MD5.class.getClass());
	
	// 加密算法
    private static  MessageDigest currentAlgorithm ;
    static {
        try{
        	currentAlgorithm = MessageDigest.getInstance("md5");
        }catch (Exception e) {
        	log.error("无法获取信息摘要加密算法", e);
        }
    }
    
	/**
	 * 加密为大写的MD5字符串.
	 * 
	 * @param src
	 *                      要加密的字符串.
	 * @return  String
	 *                      加密完毕的字符串,其中字母为大写.
	 */
	public static String encrypt(String src) {
		return encryptLowerCase(src).toUpperCase();
	}
	
	/**
	 * 加密为小写的MD5字符串.
	 * 
	 * @param src
	 *                      要加密的字符串.
	 * @return  String
	 *                      加密完毕的字符串,其中字母为小写.
	 */
	public static String encryptLowerCase(String src) {
		src = src.trim();
		StringBuilder digest = new StringBuilder(32);
		try {
			byte[] mess = src.getBytes("UTF-8");
			byte[] hash ;
			
			synchronized(currentAlgorithm){
				currentAlgorithm.reset();
				hash = currentAlgorithm.digest(mess);
			}
			
			int legth = hash.length;
			for (int i = 0; i < legth; i++) {
				int v = hash[i];
				if (v < 0)   v = 256 + v;
				if (v < 16) digest.append('0');
				digest.append(Integer.toString(v, 16));
			}
		} catch (Exception e) {
		}
		
		return digest.toString();
	}
        
        
}