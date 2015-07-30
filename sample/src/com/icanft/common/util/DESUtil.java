/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import java.io.RandomAccessFile;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class DESUtil {

	static int iLength = 0;
	static String ch = "";

	/**
	 * 默认的构造方法。
	 */
	public DESUtil() {
	}

	private static void saveFile(byte strcont[], String filename)
			throws Exception {
		RandomAccessFile infile = null;
		try {
			infile = new RandomAccessFile(filename, "rw");
			infile.write(strcont);
		} catch (Exception e) {
			throw e;
		}
		infile.close();
	}

	private static byte[] readFile(String strfilename) throws Exception {
		byte filecontent[];
		RandomAccessFile infile;
		filecontent = null;
		int i_size = 0;
		infile = null;
		try {
			infile = new RandomAccessFile(strfilename, "rw");
			i_size = (int) infile.length();
			filecontent = new byte[i_size];
			infile.read(filecontent, 0, i_size);
		} catch (Exception e) {
			throw e;
		}
		infile.close();
		return filecontent;
	}

	private static void getKeyFile() throws Exception {
		SecureRandom sr = new SecureRandom();
		KeyGenerator kg = KeyGenerator.getInstance("DES");
		kg.init(sr);
		SecretKey key = kg.generateKey();
		byte rawKeyData[] = key.getEncoded();
		saveFile(rawKeyData, "deskeyfile");
	}

	/**
	 * 判断是否是AscII字符。
	 * 
	 * @param ch1 char
	 * @param iLength int
	 * 
	 * @return boolean
	 */
	public static boolean isAsCII(char ch1, int iLength) {
		int iLevel = 0;
		int ch = 0;
		ch = ch1;
		do {
			ch += iLength;
			if (ch < 0) {
				ch += 256;
			}
			if (ch > 256) {
				ch -= 256;
			}
			iLevel++;
		} while (ch < 33 || ch > 126);
		DESUtil.iLength = iLevel;
		DESUtil.ch = String.valueOf(ch);
		return true;
	}

	/**
	 * 判断是否是AscII解码。
	 * 
	 * @param ch1 char
	 * @param iLength int
	 * @param iFlag char
	 * 
	 * @return boolean
	 */
	public static boolean isAsCII_DeCode(char ch1, int iLength, char iFlag) {
		int chTemp = 0;
		chTemp = ch1;
		do {
			chTemp += iLength * Integer.parseInt(String.valueOf(iFlag));
			if (chTemp < 0) {
				chTemp += 256;
			}
			if (chTemp > 256) {
				chTemp -= 256;
			}
		} while (chTemp < 0 || chTemp > 256);
		DESUtil.ch = String.valueOf(chTemp);
		return true;
	}

	/**
	 * 对密码进行编码。
	 * 
	 * @param strsrc String
	 *  
	 * @return String
	 */
	public static String pwdEncode(String strsrc) {
		strsrc = strsrc.trim();
		StringBuffer strBufSrc = new StringBuffer("");
		StringBuffer strBufFlag = new StringBuffer("");
		if (strsrc.equals("")) {
			return "";
		}
		for (int i = 0; i < strsrc.length(); i++) {
			if (isAsCII(strsrc.charAt(i), A[i])) {
				strBufSrc.append(String.valueOf((char) Integer
						.parseInt(DESUtil.ch)));
				strBufFlag.append(String.valueOf(DESUtil.iLength));
			}
		}

		strEncode = strBufFlag.toString().trim();
		return strBufSrc.toString();
	}
   /**
    * 对字符串进行解码。
    * 
    * @param strsrc String
    * @param strFlag String
    * 
    * @return String
    */
	public static String pwdDecode(String strsrc, String strFlag) {
		strsrc = strsrc.trim();
		strFlag = strFlag.trim();
		StringBuffer strBuf = new StringBuffer("");
		int tmpvalue = 0;
		if (strsrc.equals("")) {
			return "";
		}
		if (strsrc.length() != strFlag.length()) {
			return "\u957F\u5EA6\u4E0D\u4E00\u6837\uFF0C\u8BE5\u4E32\u4E0D\u80FD\u89E3\u7801\uFF01";
		}
		for (int i = 0; i < strsrc.length(); i++) {
			tmpvalue = strsrc.charAt(i) - A[i]
					* Integer.parseInt(String.valueOf(strFlag.charAt(i)));
			if (tmpvalue > 0) {
				tmpvalue %= 256;
			} else if (tmpvalue < 0) {
				tmpvalue = 256 - Math.abs(tmpvalue) % 256;
			}
			strBuf.append(String.valueOf((char) tmpvalue));
		}

		return strBuf.toString();
	}

	/**
	 * 进行编码。
	 * 
	 * @param data byte
	 * 
	 * @return  byte[]
	 * 
	 * @throws Exception 异常
	 */
	public static byte[] encode(byte data[]) throws Exception {
		if (data == null || data.length == 0) {
			return data;
		} else {
			SecureRandom sr = new SecureRandom();
			byte rawKeyData[] = InternationalizationUtil.getBytes("FORESEE.COM.FBRP");
			DESKeySpec dks = new DESKeySpec(rawKeyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(1, key, sr);
			byte encryptedData[] = cipher.doFinal(data);
			return encryptedData;
		}
	}

	/**
	 * 进行解码。
	 * 
	 * @param encryptedData byte
	 * 
	 * @return byte[]
	 * 
	 * @throws Exception 异常
	 */
	public static byte[] decode(byte encryptedData[]) throws Exception {
		if (encryptedData == null || encryptedData.length == 0) {
			return encryptedData;
		} else {
			SecureRandom sr = new SecureRandom();
			byte rawKeyData[] = InternationalizationUtil.getBytes("FORESEE.COM.CN");
			DESKeySpec dks = new DESKeySpec(rawKeyData);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(2, key, sr);
			byte decryptedData[] = cipher.doFinal(encryptedData);
			return decryptedData;
		}
	}

	/** 常量。*/
	private static String strEncode;

	/** 常量。*/
	private static final int A[] = { 31, -42, 53, -64, 75, -86, 97, -108 };

}
