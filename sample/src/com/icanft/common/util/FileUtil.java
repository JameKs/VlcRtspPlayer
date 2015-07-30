/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.util;

import java.io.File;
import java.io.RandomAccessFile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * <pre>
 * 文件操作的实用类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FileUtil {
	
	private static final Log log = LogFactory.getLog(FileUtil.class);

	/**
	 * 读取txt文件。
	 * 
	 * @param strFullFileName String
	 * @param iEncode int
	 * 
	 * @return String
	 * 
	 * @throws Exception 异常
	 */
	public static String readTxtFile(String strFullFileName, int iEncode)
			throws Exception {
		String strOutput;
		RandomAccessFile infile;
		byte strTemp[];
		strOutput = "";
		infile = null;
		File testfile = null;
		strTemp = null;
		int i_size = 0;
		if (strFullFileName == null || strFullFileName.trim().length() <= 0) {
			return "";
		}
		String s;
		testfile = new File(strFullFileName.trim());
		if (testfile.exists() && !testfile.isDirectory()) {
			return "";
		}
		testfile = null;
		try {
			infile = new RandomAccessFile(strFullFileName.trim(), "rw");
			i_size = (int) infile.length();
			strTemp = new byte[i_size];
			infile.read(strTemp, 0, i_size);
			if (iEncode == 1) {
				strTemp = DESUtil.decode(strTemp);
			}
		} catch (Exception e) {
			log.error((new StringBuilder()).append("Read file error : ")
					.append(e.getMessage()).toString());
			throw e;
		}
		if (infile != null) {
			try {
				infile.close();
			} catch (Exception e) {
				log.error((new StringBuilder()).append("Encode Error : ")
						.append(e.getMessage()).toString());
				throw e;
			}
		}
		strOutput = "";
		if (strTemp != null && strTemp.length > 0) {
			strOutput = new String(strTemp);
		}
		return strOutput;
	}

	/**
	 * 把字符串保存到txt文件中。
	 * 
	 * @param strFullFileName String
	 * @param strContent String
	 * @param iEncode int
	 * 
	 * @return int
	 * 
	 * @throws Exception 异常
	 */
	public static int saveTxtFile(String strFullFileName, String strContent,
			int iEncode) throws Exception {
		try {
			File f1 = new File(strFullFileName);
			RandomAccessFile infile = new RandomAccessFile(f1, "rw");
			byte contentBys[] = strContent.getBytes();
			if (iEncode == 1) {
				contentBys = DESUtil.encode(contentBys);
			}
			infile.write(contentBys);
			infile.close();
		} catch (Exception e) {
			log.error((new StringBuilder())
					.append("UTIL:\u5199\u6587\u672C\u3010")
					.append(strFullFileName)
					.append("\u3011\u65F6\u53D1\u751F\u5F02\u5E38\uFF0C\u5C06\u5220\u9664\u5DF2\u4EA7\u751F\u7684\u6587\u4EF6\u3002")
					.toString());
			File f2 = new File(strFullFileName);
			if (f2.exists()) {
				f2.delete();
			}
			throw e;
		}
		return 0;
	}

}