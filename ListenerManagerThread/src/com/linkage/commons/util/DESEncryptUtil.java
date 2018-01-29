package com.linkage.commons.util;

import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.linkage.commons.exception.BssRuntimeException;
import com.linkage.commons.exception.Result;

/**
 * DES�ԳƼ����㷨  
 * 
 *  ����:encrypt(String srcStr, String key)
 *  ����:descrypt(String encryptStr,String key )
 *  ���ܽ��ܹ����в��õ�key��һ����
 *  
 * @author zhaoxin
 *
 */
public class DESEncryptUtil {
	private static Log log = Log.getLog(DESEncryptUtil.class);

	private final static String charset = "UTF-8";
	private final static String algorithm = "DES";

	/**
	 * ����
	 * @param srcStr	����
	 * @param key		��Կ
	 * @return			����
	 */
	public static String encrypt(String srcStr, String key) {
		String strEncrypt = null;
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			strEncrypt = base64en.encode(encryptByte(srcStr.getBytes(charset), key));
		} catch (Exception e) {
			log.error("�����쳣:", e);
			throw new BssRuntimeException(Result.BSS_SYS_ERROR,"�����쳣",e);
		}
		return strEncrypt;
	}

	/**
	 * ����
	 * @param encryptStr	����
	 * @param key			��Կ
	 * @return				����
	 */
	public static String decrypt(String encryptStr, String key) {
		BASE64Decoder base64De = new BASE64Decoder();
		String strDecrypt = null;
		try {
			strDecrypt = new String(decryptByte(base64De.decodeBuffer(encryptStr), key), charset);
		} catch (Exception e) {
			log.error("�����쳣:", e);
			throw new BssRuntimeException(Result.BSS_SYS_ERROR,"�����쳣",e);
		} finally {
			base64De = null;
		}
		return strDecrypt;
	}

	/**
	 * �ֽڼ���
	 * @param srcByte	����
	 * @param key		��Կ
	 * @return			����
	 */
	public static byte[] encryptByte(byte[] srcByte, String key) {
		byte[] byteFina = null;
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, generateKey(key));
			byteFina = cipher.doFinal(srcByte);
		} catch (Exception e) {
			log.error("�ֽڼ����쳣:", e);
			throw new BssRuntimeException(Result.BSS_SYS_ERROR,"�ֽڼ����쳣",e);
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * �ֽڽ���
	 * @param encryptByte	����
	 * @param key			��Կ
	 * @return				����
	 */
	public static byte[] decryptByte(byte[] encryptByte, String key) {
		Cipher cipher;
		byte[] decryptByte = null;
		try {
			cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, generateKey(key));
			decryptByte = cipher.doFinal(encryptByte);
		} catch (Exception e) {
			log.error("�ֽڽ����쳣:", e);
			throw new BssRuntimeException(Result.BSS_SYS_ERROR,"�ֽڽ����쳣",e);
		} finally {
			cipher = null;
		}
		return decryptByte;

	}

	/**  
	 * ���ݴ�����ַ�����key����key����  
	 *   
	 * @param strKey  
	 */
	public static Key generateKey(String strKey) {
		try{
	        DESKeySpec desKeySpec = new DESKeySpec(strKey.getBytes(charset));   
	        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(algorithm);   
	        return keyFactory.generateSecret(desKeySpec); 		
		}catch(Exception e){
			throw new BssRuntimeException(Result.BSS_SYS_ERROR,"������Կ�쳣",e);
		}

	}
	
	/**����MD5���м���
	* @param str  �����ܵ��ַ���
	* @return  ���ܺ���ַ���
	* @throws NoSuchAlgorithmException  û�����ֲ�����ϢժҪ���㷨
	* @throws UnsupportedEncodingException  
	*/
	public static String encoderByMd5(String souceStr) throws Exception{
		String s = null;
		// �������ֽ�ת���� 16 ���Ʊ�ʾ���ַ�
		char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  'e', 'f'}; 
		try{
			java.security.MessageDigest md = java.security.MessageDigest.getInstance( "MD5" );
			md.update( souceStr.getBytes() );
			byte tmp[] = md.digest();          // MD5 �ļ�������һ�� 128 λ�ĳ�������
			// ���ֽڱ�ʾ���� 16 ���ֽ�
			char str[] = new char[16 * 2];   // ÿ���ֽ��� 16 ���Ʊ�ʾ�Ļ���ʹ�������ַ���
			// ���Ա�ʾ�� 16 ������Ҫ 32 ���ַ�
			int k = 0; // ��ʾת������ж�Ӧ���ַ�λ��
			for (int i = 0; i < 16; i++) {          // �ӵ�һ���ֽڿ�ʼ���� MD5 ��ÿһ���ֽ�
				// ת���� 16 �����ַ���ת��
				byte byte0 = tmp[i];                 // ȡ�� i ���ֽ�
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // ȡ�ֽ��и� 4 λ������ת��, 
		                                                     // >>> Ϊ�߼����ƣ�������λһ������
				str[k++] = hexDigits[byte0 & 0xf];            // ȡ�ֽ��е� 4 λ������ת��
			} 
			s = new String(str);                                 // ����Ľ��ת��Ϊ�ַ���
		
		}catch( Exception e )
		{
			throw e;
		}
		return s;
	}
	
	/**
	 * ���Ժ���
	 * @param args
	 */
	public static void main(String[] args) {
		
		String key = "i am key,let me encrypt you! 1234haha";
		String src = "crm_app";
		
		log.debug("��Կ:"+key);
		log.debug("����:"+src );
		
		String strEnc = DESEncryptUtil.encrypt(src, key);
		log.debug("���ܺ�,����:"+strEnc);

		String strDes = DESEncryptUtil.decrypt("FtAkg6QBlt8=", key);
		log.debug("���ܺ�,����:"+ strDes);

	}

}