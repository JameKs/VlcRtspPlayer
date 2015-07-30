/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
public class HttpTools {

	/**
	 * 默认的构造方法。
	 */
	public HttpTools() {
	}

	/**
	 * doHttpPost。
	 * 
	 * @param requestUrl  String
	 * 
	 * @return byte[]
	 * 
	 * @throws Exception 异常
	 */
	public static byte[] doHttpPost(String requestUrl) throws Exception {
		return doHttpPost(requestUrl, (String) null);
	}

	/**
	 * doHttpPost。
	 * 
	 * @param requestUrl  String
	 * @param  cookiestr String
	 * 
	 * @return byte[]
	 * 
	 * @throws Exception 异常
	 */
	public static byte[] doHttpPost(String requestUrl, String cookiestr)
			throws Exception {
		HttpURLConnection con;
		InputStream cis;
		if (requestUrl == null) {
			throw new IllegalArgumentException("requesturl can not be  null.");
		}
		log.debug((new StringBuilder()).append("Sending request to: ")
				.append(requestUrl).toString());
		con = null;
		cis = null;
		byte fullbyte[];
		URL url = new URL(requestUrl);
		con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("GET");
		con.setDoOutput(true);
		if (cookiestr != null) {
			con.setRequestProperty("Cookie", cookiestr);
		}
		if (con.getResponseCode() == 200) {
			log.info((new StringBuilder()).append("Receive Response Code ")
					.append(con.getResponseCode()).append(" : ")
					.append(con.getResponseMessage()).toString());
		} else {
			throw new Exception((new StringBuilder())
					.append("Receive Response Code ")
					.append(con.getResponseCode()).append(" : ")
					.append(con.getResponseMessage()).toString());
		}
		cis = con.getInputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte linebuf[] = new byte[200];
		int readlen;
		while ((readlen = cis.read(linebuf)) > 0) {
			bos.write(linebuf, 0, readlen);
		}
		fullbyte = bos.toByteArray();
		if (cis != null) {
			cis.close();
		}
		if (con != null) {
			con.disconnect();
		}
		// break MISSING_BLOCK_LABEL_276;
		// Exception exception;
		// exception;
		// if(cis != null)
		// cis.close();
		// if(con != null)
		// con.disconnect();
		// throw exception;
		return fullbyte;
	}

	/**
	 * doHttpPost。
	 * 
	 * @param requestUrl  String
	 * @param  reqType String
	 * @param reqByte byte
	 * 
	 * @return byte[]
	 * 
	 * @throws Exception 异常
	 */
	public static byte[] doHttpPost(String requestUrl, String reqType,
			byte reqByte[]) throws Exception {
		HttpURLConnection con;
		InputStream cis;
		if (requestUrl == null) {
			throw new IllegalArgumentException("requesturl can not be  null.");
		}
		log.debug((new StringBuilder()).append("Sending request to: ")
				.append(requestUrl).toString());
		con = null;
		cis = null;
		byte fullbyte[];
		URL url = new URL(requestUrl);
		con = (HttpURLConnection) url.openConnection();
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestMethod("POST");
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", reqType);
		con.setRequestProperty("Content-Length", String.valueOf(reqByte.length));
		OutputStream ostrm = con.getOutputStream();
		ostrm.write(reqByte);
		ostrm.close();
		if (con.getResponseCode() == 200) {
			log.info((new StringBuilder()).append("Receive Response Code ")
					.append(con.getResponseCode()).append(" : ")
					.append(con.getResponseMessage()).toString());
		} else {
			throw new Exception((new StringBuilder())
					.append("Receive Response Code ")
					.append(con.getResponseCode()).append(" : ")
					.append(con.getResponseMessage()).toString());
		}
		cis = con.getInputStream();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte linebuf[] = new byte[200];
		int readlen;
		while ((readlen = cis.read(linebuf)) > 0) {
			bos.write(linebuf, 0, readlen);
		}
		fullbyte = bos.toByteArray();
		if (cis != null) {
			cis.close();
		}
		if (con != null) {
			con.disconnect();
		}
		// break MISSING_BLOCK_LABEL_317;
		// Exception exception;
		// exception;
		// if(cis != null)
		// cis.close();
		// if(con != null)
		// con.disconnect();
		// throw exception;
		return fullbyte;
	}

   /**
    * 删除HttpServletResponse中的相关头信息。
    * 
    * @param res HttpServletResponse
    */
	public static void removeCacheHeaders(HttpServletResponse res) {
		if (res.containsHeader("Pragma")) {
			log.debug("Removing Pragma header to avoid caching issues in IE");
			res.setHeader("Pragma", "null");
		}
		if (res.containsHeader("Cache-Control")) {
			log.debug("Removing Cache-Control header to avoid caching issues in IE");
			res.setHeader("Cache-Control", "null");
		}
	}

	private static final Log log = LogFactory.getLog(HttpTools.class);

}