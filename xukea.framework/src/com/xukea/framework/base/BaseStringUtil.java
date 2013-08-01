package com.xukea.framework.base;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.util.StringUtils;

/**
 * 字符串处理基类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-12-27
 */
public class BaseStringUtil extends StringUtils
{
	/**
	 * 检测字符串是否为空
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if(str == null || str.trim().length() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 取字符串的前toCount个字符
	 * @param str 被处理字符串
	 * @param toCount 截取长度
	 * @param more 后缀字符串
	 * @return
	 */
	public static String subString(String str, int toCount, String more) {
		if (isEmpty(str)) return "";

		int    reInt = 0;
		String reStr = "";
		char[] tempChar = str.toCharArray();
		for (int kk = 0; (kk < tempChar.length && toCount > reInt); kk++)
		{
			String s1 = str.valueOf(tempChar[kk]);
			byte[] b = null;
			try
			{
				b = s1.getBytes("UTF-8");
			}
			catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			reInt += b.length;
			reStr += tempChar[kk];
		}
		if (toCount == reInt || (toCount == reInt - 1) || (toCount < reInt))
			reStr += more;
		return reStr;
	}


    /**
     * 将str按多个分隔符进行切分，
     * 
     * 示例：StringTokenizerUtils.split("1,2;3 4"," ,;");
     * 返回: ["1","2","3","4"]
     * 
     * @param str
     * @param seperators
     * @return
     */
	@SuppressWarnings("all")
	public static String[] split(String str, String seperators) {
		StringTokenizer tokenlizer = new StringTokenizer(str, seperators);
		List result = new ArrayList();
		
		while(tokenlizer.hasMoreElements()) {
			Object s = tokenlizer.nextElement();
			result.add(s);
		}
		return (String[])result.toArray(new String[result.size()]);
	}
}
