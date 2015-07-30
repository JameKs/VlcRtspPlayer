/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.license;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mqm.frame.util.InternationalizationUtil;

/**
 * 
 * <pre>
 * 收集Mac地址。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class CollectMacAddress {

	private static final Log log = LogFactory.getLog(CollectMacAddress.class);
	
	/**
	 * 返回本机的所有Mac地址列表。
	 * 
	 * @return Mac地址列表
	 */
	public static List<String> getMacAddress() {		
		List<String> macAddressList = new ArrayList<String>();
		String os = InternationalizationUtil.toLowerCase(System.getProperty("os.name"));
		if (os != null && os.startsWith("windows")) {
			BufferedReader br = null;
			Process p = null;
			try {
				String command = "cmd.exe /c ipconfig /all";
				p = Runtime.getRuntime().exec(command);				
				br = new BufferedReader(new InputStreamReader(p
						.getInputStream()));
				String line;
				while ((line = br.readLine()) != null) {
					if (line.indexOf("Physical Address") > 0) {
						int index = line.indexOf(":");
						index += 2;
						macAddressList.add(line.substring(index).trim());
					}
				}
				return macAddressList;
			} catch (Exception e) {
				log.info("", e);
			} finally{
				if(br != null){
					try {
						br.close();
					} catch (IOException e) {
						log.error("", e);
					}
				}
				if(p != null){
					//终止子进程
					p.destroy();
				}
			}
		}
		return macAddressList;
	}

}
