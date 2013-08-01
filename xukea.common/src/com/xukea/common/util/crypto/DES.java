package com.xukea.common.util.crypto;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec; 

/**
 * 字符串 DESede(3DES) 加密
 * 
 * @author 木木大叔
 * @QQ     285198830
 * @version 1.0
 * @date    2012-02-16
 */
public class DES {

//    public static void main(String[] args) {
//        //添加新安全算法,如果用JCE就要把它添加进去
//        Security.addProvider(new com.sun.crypto.provider.SunJCE());
//
////        final byte[] keyBytes = {0x11, 0x22, 0x4F, 0x58, (byte)0x88, 0x10, 0x40, 0x38
////                               , 0x28, 0x25, 0x79, 0x51, (byte)0xCB, (byte)0xDD, 0x55, 0x66
////                               , 0x77, 0x29, 0x74, (byte)0x98, 0x30, 0x40, 0x36, (byte)0xE2}; //24字节的密钥
//        final byte[] keyBytes = "i832B3dUa)#3P@%vi832B3dU".getBytes();
//        String szSrc = "my name is Sidney.";
//        
//        byte[] encodestr = getBytesFromHex("c3547fd08a87e6e3");
//        System.out.println("加密前的字符串:" + szSrc);
//
//        byte[] encoded = encryptMode(keyBytes, szSrc.getBytes());
//        System.out.println("加密后的字符串:" + new String(encoded));
//
//        byte[] srcBytes = decryptMode(keyBytes, encodestr);
//        System.out.println("解密后的字符串:" + (new String(srcBytes)));
//    }
    
    private static final String Algorithm = "DESede"; //定义 加密算法,可用 DES,DESede,Blowfish

    //keybyte为加密密钥，长度为24字节
    //src为被加密的数据缓冲区（源）
    public static byte[] encryptMode(byte[] keybyte, byte[] src) {
       try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //加密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.ENCRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //keybyte为加密密钥，长度为24字节
    //src为加密后的缓冲区
    public static byte[] decryptMode(byte[] keybyte, byte[] src) {
    	try {
            //生成密钥
            SecretKey deskey = new SecretKeySpec(keybyte, Algorithm);

            //解密
            Cipher c1 = Cipher.getInstance(Algorithm);
            c1.init(Cipher.DECRYPT_MODE, deskey);
            return c1.doFinal(src);
        } catch (java.security.NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (javax.crypto.NoSuchPaddingException e2) {
            e2.printStackTrace();
        } catch (java.lang.Exception e3) {
            e3.printStackTrace();
        }
        return null;
    }

    //转换成十六进制字符串
    public static String byte2hex(byte[] b) {
        String hs="";
        String stmp="";

        for (int n=0;n<b.length;n++) {
            stmp=(java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length()==1) hs=hs+"0"+stmp;
            else hs=hs+stmp;
            if (n<b.length-1) hs=hs+":";
        }
        return hs.toUpperCase();
    }
    
    /** 将16进制的字符串转换成字节数组 */
    public static byte[] getBytesFromHex(String hexstr) {
    	byte[] bytes = new byte[hexstr.length() / 2];
    	if ((hexstr.length()) % 2 != 0) {
    		return null;
    	}
		for (int i = 0; i < hexstr.length() / 2; i++) {
			byte[] temp = new byte[2];
			temp[0] = (byte) hexstr.charAt(i * 2);
			temp[1] = (byte) hexstr.charAt(i * 2 + 1);
			bytes[i] = (byte) (Integer.parseInt(new String(temp), 16));
		}
		
		return bytes;
    } 

}
