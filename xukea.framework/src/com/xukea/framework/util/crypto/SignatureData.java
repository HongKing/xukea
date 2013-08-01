package com.xukea.framework.util.crypto;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Random;

/**
 * 授权签字
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2011.12.26
 */
public class SignatureData {
	// 随机码字符集
	private static String alnum[] = {
		 "A","B","C","D","E","F","G","H","J","K","M","N","P","Q","R","S","T","U","V","W","X","Y","Z",
        "a","b","c","d","e","f","g","h","i","j","k","m","n","p","q","r","s","t","u","v","w","x","y","z",
        "1","2","3","4","5","6","7","8","9"};
	// 随机码位数
	private static int    length   = 6;
	
	private static Object lock = new Object(); 
	
	private static SignatureData instance = null;
	
	public static SignatureData getInstance() {
		if (instance == null) {
			synchronized( lock ){   
                if (instance == null){   
                    instance = new SignatureData();   
                }   
            }
		}
		return instance;
	}

	/**
	 * 数据签名
	 * 
	 * @param priveKey
	 * @param infoData
	 */
	public String signed(String priveKey, String infoData) {
		try {
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec( RSAUtil.hexStrToBytes(priveKey) ); 
			KeyFactory keyFct = KeyFactory.getInstance("RSA");
			PrivateKey priKey = keyFct.generatePrivate(priPKCS8);
			
			// 用私钥对信息生成数字签名
			Signature signet = Signature.getInstance("MD5withRSA");
			signet.initSign( priKey );
			signet.update( infoData.getBytes("UTF-8") );
			// 对信息的数字签名
			byte[] signed = signet.sign(); 
			
			return RSAUtil.bytesToHexStr(signed);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 签名数据验证
	 * 
	 * @param publicKey
	 * @param infoData
	 * @param signData
	 */
	public boolean verify(String publicKey, String infoData, String signData) {
		try {
			X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec( RSAUtil.hexStrToBytes(publicKey) );
	
			KeyFactory keyFct = KeyFactory.getInstance("RSA");
			PublicKey  pubKey = keyFct.generatePublic( bobPubKeySpec );
			byte[] signed = RSAUtil.hexStrToBytes( signData );//这是SignatureData输出的数字签名

			Signature signet = Signature.getInstance("MD5withRSA");
			signet.initVerify( pubKey );
			signet.update( infoData.getBytes("UTF-8") );
			
			return signet.verify(signed);
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
	}
	
	/**
	 * @描述： 获取标识码
	 * @return
	 */
	public String getSymbol(){
		// 生成随机码
		String randstr = "";
		for(int i=0;i<length;i++){
			randstr += alnum[randomInt(0, alnum.length)];
		}
		
		return MD5.encryptLowerCase(randstr);
	}

	/**
	 * @描述:返回[from,to)之间的一个随机整数
	 * 
	 * @参数：from 起始值
	 * @参数：to 结束值
	 * @返回值:[from,to)之间的一个随机整数
	 */
	private int randomInt(double from, double to){
		Random r = new Random();
		int len = (int)Math.ceil(to-from);
		int str = (int)Math.floor(from);
		int temp = r.nextInt( Math.abs(len) );
		temp = (len<0) ? -temp : temp;
		return str + temp;
	}
}