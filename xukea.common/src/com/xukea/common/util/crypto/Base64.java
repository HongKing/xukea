package com.xukea.common.util.crypto;


/**
 * BASE64编码算法类
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2011.12.26
 */
public class Base64 {

	// code characters for values 0..63
	private static char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=".toCharArray();
	
	// lookup table for converting base64 characters to value in range 0..63
	private static  byte[] codes = new byte[256];
	static {
	    for (int i=0; i<256; i++) codes[i] = -1;
	    for (int i = 'A'; i <= 'Z'; i++) codes[i] = (byte)(     i - 'A');
	    for (int i = 'a'; i <= 'z'; i++) codes[i] = (byte)(26 + i - 'a');
	    for (int i = '0'; i <= '9'; i++) codes[i] = (byte)(52 + i - '0');
	    codes['+'] = 62;
	    codes['/'] = 63;
	}

	/**
	 * 编码
	 * 
	 * @param data
	 * @return
	 */
	public static char[] encode(byte[] data)
	{
		char[] out = new char[((data.length + 2) / 3) * 4];
		for (int i=0, index=0; i<data.length; i+=3, index+=4) {
			boolean quad = false;
			boolean trip = false;
			int val = (0xFF & (int) data[i]);
			val <<= 8;
			if ((i+1) < data.length) {
				val |= (0xFF & (int) data[i+1]);
				trip = true;
			}
			val <<= 8;
			if ((i+2) < data.length) {
				val |= (0xFF & (int) data[i+2]);
				quad = true;
			}
			out[index+3] = alphabet[(quad? (val & 0x3F): 64)];
			val >>= 6;
			out[index+2] = alphabet[(trip? (val & 0x3F): 64)];
			val >>= 6;
			out[index+1] = alphabet[val & 0x3F];
			val >>= 6;
			out[index+0] = alphabet[val & 0x3F];
		}
		return out;
	}

	/**
	 * 解码
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static byte[] decode(char[] data) throws Exception
	{
		int len = ((data.length + 3) / 4) * 3;
		if (data.length>0 && data[data.length-1] == '=') --len;
		if (data.length>1 && data[data.length-2] == '=') --len;
		byte[] out = new byte[len];
		
		int shift = 0;   // # of excess bits stored in accum
		int accum = 0;   // excess bits
		int index = 0;
		
		for (int ix=0; ix<data.length; ix++)
		{
			int value = codes[ data[ix] & 0xFF ];   // ignore high byte of char
			if ( value >= 0 ) {                     // skip over non-code
				accum <<= 6;            // bits shift up by 6 each time thru
				shift += 6;             // loop, with new bits being put in
				accum |= value;         // at the bottom.
				if ( shift >= 8 ) {     // whenever there are 8 or more shifted in,
					shift -= 8;         // write them out (from the top, leaving any
					out[index++] =      // excess at the bottom for next iteration.
					(byte) ((accum >> shift) & 0xff);
				}
			}
		}
		if (index != out.length)
			throw new Exception("miscalculated data length!");//Error("miscalculated data length!");
		return out;
	}


	/**
	 * 编码
	 * 
	 * @param source
	 * @return
	 * @throws Exception
	 */
	public static String encode(String source) throws Exception
	{
		byte[] msgby  = source.getBytes("UTF-8");
		char[] enchr  = Base64.encode(msgby);
		String str    = new String(enchr);
		
		return str;
	}

	/**
	 * 解码
	 * 
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String decode(String data) throws Exception
	{
		char[] enchr = data.toCharArray();
		byte[] debyt = Base64.decode(enchr);
		String str   = new String(debyt, "UTF-8");
		
		return str;
	}

	/**
	 * 转换成十六进制字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++)
		{
			stmp = (Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

}

