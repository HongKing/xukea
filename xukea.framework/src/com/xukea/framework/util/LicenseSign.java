package com.xukea.framework.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.KeyPair;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.xukea.framework.base.BaseConstants;
import com.xukea.framework.util.crypto.RSAUtil;
import com.xukea.framework.util.crypto.SignatureData;

/**
 * 配置文件处理
 * @author FishBoy
 * @version
 */
public final class LicenseSign {
	private Logger log = Logger.getLogger(LicenseSign.class);
	
	private final static Properties  propertie = new Properties();
	private static LicenseSign  instance ;
	private String configName = "config/license.properties";

	public static LicenseSign getInstance() {
		if (instance == null) {
			instance = new LicenseSign();
		}
		return instance;
	}
	
	public LicenseSign(){
		InputStream inputStrem = Thread.currentThread().getContextClassLoader().getResourceAsStream(configName);
//		inputStrem = this.getClass().getClassLoader().getResourceAsStream(configName);
		try {
			propertie.load(inputStrem);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改配置
	 * @param key
	 * @param val
	 */
	private void modify(String key, String val){
		propertie.setProperty(key, val);
	}
	
	/**
	 * 保存配置
	 */
	private void saveConfig(){
		try {
			String filePath = Thread.currentThread().getContextClassLoader().getResource(configName).toString();
			filePath = filePath.startsWith("file:/") ? filePath.substring("file:/".length(), filePath.length()) : filePath;
			FileOutputStream out = new FileOutputStream(filePath);
			propertie.store(out, "");
		}catch (Exception e) {
			log.error(e);
		}
	}
	
	
    /**
     * 授权信息验证 
     * @return
     */
    public boolean isSigened(){
		String infoData = "";
		infoData += instance.getString("license.user");    //用户
		infoData += instance.getString("license.model");   //软件
		infoData += instance.getString("license.version"); //版本
		infoData += instance.getString("license.effect");  //生效期
		infoData += instance.getString("license.expiry");  //失效期
		infoData += instance.getString("license.macaddress"); //MAC地址
		infoData += instance.getString("license.symbol");  //标识码
		
		String pubkey   = instance.getString("license.pubkey");
		String signData = instance.getString("license.signature");

		return SignatureData.getInstance().verify(pubkey, infoData, signData);
    }

    /**
     * 授权过期验证 
     * @return
     */
    public boolean isExpiry(){
		return compareGreaterNow( instance.getString("license.expiry") );
    }
    
	
	/**
     * 得到key的值（字符串）<br>
     * 默认值：空字符串
     * @param  key 取得其值的键
     * @return 字符串
     */
    public String getString(String key) {
        if(propertie.containsKey(key)){
            String value = propertie.getProperty(key);//得到某一属性的值 
            return value;
        }else{
            return "";
        }
    }

	/**
	 * 是否大于当前时间
	 * @param text
	 * @return
	 */
	private boolean compareGreaterNow(String text){
		// 空值则返回 false
		if (text==null || text.trim().equals("")) {
			return false;
		}
		// 根据传入数据判断所采用的格式
		SimpleDateFormat tempFormat;
		text = text.trim();
		if( text.length() == BaseConstants.FORMAT_DATE.length() ){
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_DATE );
		}else if( text.length() == BaseConstants.FORMAT_TIME.length() ){
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_TIME );
		}else if( text.length() == BaseConstants.FORMAT_TIME_HM.length() ){
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_TIME_HM );
		}else{
			tempFormat = new SimpleDateFormat( BaseConstants.FORMAT_DATE_TIME );
		}
		// 时间比较
		boolean flag = false;
		try {
			Date date1 = tempFormat.parse( text );
			Date date2 = tempFormat.parse( tempFormat.format(new Date()) ); // 将当前时间格式化为带比较时间的格式
			flag = date1.getTime() > date2.getTime();
		} catch (ParseException e) {
			flag = false;
		}
		
		// 如果过期，则更新license文件的标识码
		if(!flag){
			String sybmal = SignatureData.getInstance().getSymbol();
			this.modify("license.symbol", sybmal);
			this.saveConfig();
		}
		
		return flag;
	}

//	public static void main(String[] args )throws Exception{
////		KeyPair kp = RSAUtil.generateKeyPair();
////		String pubKey = RSAUtil.bytesToHexStr( kp.getPublic().getEncoded()  ); 
////		String priKey = RSAUtil.bytesToHexStr( kp.getPrivate().getEncoded() );
////        System.out.println("public  key :"+ pubKey);
////        System.out.println("private key :"+ priKey);
////		
////		System.out.println(SignatureData.getInstance().getSymbol());
//		
//		SignatureData sg = SignatureData.getInstance();
//		String infoData = "";
//		infoData += LicenseSign.getInstance().getString("license.user");    //用户
//		infoData += LicenseSign.getInstance().getString("license.model");   //软件
//		infoData += LicenseSign.getInstance().getString("license.version"); //版本
//		infoData += LicenseSign.getInstance().getString("license.effect");  //生效期
//		infoData += LicenseSign.getInstance().getString("license.expiry");  //失效期
//		infoData += LicenseSign.getInstance().getString("license.macaddress"); //MAC地址
//		infoData += LicenseSign.getInstance().getString("license.symbol");  //标识码
//		
//		String priKey = "30820277020100300d06092a864886f70d0101010500048202613082025d02010002818100a9c1bba091f6debc4deff69324be0a6102d8af297f32c73ff25823bd03d38ccfdc176a505dccce85f0acf40bafb79c2db1fa8b7f4a7f266e82dcfbe1a67e0766c5e66f28c297427ebc2761eec5e4da4609b7663779bbdb280c714e5e8492edd7fac73564f243d76c2f57f96f29903a084594a80828327488a0f26be1a0e5484b0203010001028180020dd7105d53c8642ce93f698e63424ae436ecffaa8b59bfdb55d7b0dccffd7f32619226c0cbce9fee4699795eb9f108d8837896a4b71f26ed2c715bcca0efc3f88b6a652b464ebaf9e5437558e2912ece01be58af16a002a24acfa616bedd06586b88ba54b508403cbbf24a648c4c36f503d19e028a99438705c1dbbf1925b1024100d37ae85cfbf2772c643449606ec8dc5ac9595f3feff5a1ca59491ab8763f24a0ff77086c7aae178ab44cf1ba6f28b5f3518b3ade3238822b81a785dbc8485699024100cd7e4512e76b01c9962d69b5efd545774bcfdecc764e19f7e9921b781669cfed3beac2ce09626f166dbf7b2aef1feceae974b9153fb4ecd5c42a0244cf9bb8830240051d56db50d8975b427d253d28d0c0cb8d4d1509a5d309fbabd721c74e4ccea4fa28b5d271d11e0ea3b7acacf69d4471430a9bfcddb81225dd37e0fbe4c72319024100a0a21a20c2e1f50dbde5560330ad8895ce9f79cc5e9682da36ad4de7bbad8f2a2572484183387d1a0d44676592736461e588a6bda3852cb595983919cd282f3d024100c78dfcaefd3f49e374df4e5136289d2bfe9d7ade5514bf3e1142012a79d846dd9a038863a524dd78c335da319ac2220bacecb2f94f8dce844a472bc1654df9e5";
//		System.out.println(sg.signed(priKey, infoData));
//		
//		String pubkey   = LicenseSign.getInstance().getString("license.pubkey");
//		String signData = LicenseSign.getInstance().getString("license.signature");
//		System.out.println(sg.verify(pubkey, infoData, signData));
//	}
}