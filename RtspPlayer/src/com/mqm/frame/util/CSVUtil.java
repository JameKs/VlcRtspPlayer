/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <pre>
 * csv文件生成工具。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class CSVUtil {

	private static final Log log = LogFactory.getLog(CSVUtil.class);

	/**
	 * 创建CSV的列头。
	 * 
	 * @param csvFile
	 *            BufferedWriter
	 * @param headerMap
	 *            列头的Map
	 */
	public static void createCSVHeader(File csvFile,
			Map<String, Object> headerMap) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile, false)));
//			bw.newLine();
			// 写入文件列头
			Entry<String, Object> entry;
			// 用for迭代
			// for (Iterator<Entry<String, String>> iter = headerMap.entrySet()
			// .iterator(); iter.hasNext();) {
			// entry = iter.next();
			// bw.write("\"" + entry.getValue().toString() + "\"");
			// if (iter.hasNext()) {
			// bw.write(",");
			// }
			// }

			// 用while迭代
			Iterator<Entry<String, Object>> iter = headerMap.entrySet()
					.iterator();
			while (iter.hasNext()) {
				entry = iter.next();
				bw.write("\"" + entry.getValue().toString() + "\"");
				if (iter.hasNext()) {
					bw.write(",");
				}
			}
			bw.flush();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				log.error("", e);
			}
		}
	}
	
	/**
	 * 创建CSV的列头。
	 * 
	 * @param csvFile
	 *            BufferedWriter
	 * @param headerList
	 *            列头的List
	 */
	public static void createCSVHeader(File csvFile, List<String> headerList) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile, false)));
			// bw.newLine();

			// 用while迭代
			Iterator<String> iter = headerList.iterator();
			String header;
			while (iter.hasNext()) {
				header = iter.next();
				bw.write("\"" + header + "\"");
				if (iter.hasNext()) {
					bw.write(",");
				}
			}
			bw.newLine();
			bw.flush();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				log.error("", e);
			}
		}
	}

	/**
	 * 往CSV文件中追加内容。
	 * 
	 * @param csvFile
	 *            BufferedWriter
	 * @param data
	 *            内容数据
	 * @param headerMap
	 *            列头的Map
	 */
	public static void appendCSVContent(File csvFile,
			List<Map<String, Object>> data, Map<String, Object> headerMap) {
		if (data == null || data.isEmpty()) {
			return;
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile, true)));
			bw.newLine();
			// 往文件中追加内容
			Set<String> keys = headerMap.keySet();
			int size = keys.size();
			for (Map<String, Object> rowData : data) {
				int i = 1;
				for (String key : keys) {
					bw.write("\"" + BeanUtils.getProperty(rowData, key) + "\"");
					if (i++ < size) {
						bw.write(",");
					}
				}
				bw.newLine();
			}
			bw.flush();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				log.error("", e);
			}
		}
	}
	
	/**
	 * 往CSV文件中追加内容。
	 * 
	 * @param csvFile
	 *            BufferedWriter
	 * @param data
	 *            内容数据
	 * @param headerList
	 *            列头集合
	 */
	public static void appendCSVContent(File csvFile,
			List<Map<String, Object>> data, List<String> headerList) {
		if (data == null || data.isEmpty()) {
			return;
		}
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(csvFile, true)));
			//bw.newLine();
			// 往文件中追加内容
			//Set<String> keys = headerMap.keySet();
			//int size = keys.size();
			for (Map<String, Object> rowData : data) {
				int i = 1;
				for (String key : headerList) {
					bw.write("\"" + BeanUtils.getProperty(rowData, key) + "\"");
					if (i++ < headerList.size()) {
						bw.write(",");
					}
				}
				bw.newLine();
			}
			bw.flush();
		} catch (Exception e) {
			log.error("", e);
		} finally {
			try {
				if (bw != null) {
					bw.close();
				}
			} catch (IOException e) {
				log.error("", e);
			}
		}
	}

}