package com.linkage.commons.util;

/**
 * 提供加密算法，可以对输入的字符串进行加密、解密操作
 * 提供给电子拍照 功能使用的加密方法
 */
public class RC4EncryptUtil {
	/**
	 * 加密解密方法 , 和电子拍照系统对接
	 * @param aInput 需要加密/解密的字符串
	 * @param aKey 密钥
	 * @return 加密/解密 目标字符串
	 */
	public static String HloveyRC4(String aInput, String aKey) {
		int[] iS = new int[256];
		byte[] iK = new byte[256];

		for (int i = 0; i < 256; i++)
			iS[i] = i;

		int j = 1;

		for (short i = 0; i < 256; i++) {
			iK[i] = (byte) aKey.charAt((i % aKey.length()));
		}

		j = 0;

		for (int i = 0; i < 255; i++) {
			j = (j + iS[i] + iK[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
		}

		int i = 0;
		j = 0;
		String rOutput = "";
		short iMask = 15;
		char[] iInputChar = aInput.toCharArray();
		char[] iOutputChar = new char[iInputChar.length];
		for (short x = 0; x < iInputChar.length; x++) {
			i = (i + 1) % 256;
			j = (j + iS[i]) % 256;
			int temp = iS[i];
			iS[i] = iS[j];
			iS[j] = temp;
			int t = (iS[i] + (iS[j] % 256)) % 256;
			int iY = iS[t];
			char iCY = (char) iY;
			iOutputChar[x] = (char) (iInputChar[x] ^ iCY);
		}

		return new String(iOutputChar);

	}

}
