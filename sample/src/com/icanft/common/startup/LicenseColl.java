/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.startup;

import java.util.List;

/**
 * <pre>
 * Title:FBRP License集合。
 * Description: 程序功能的描述。 
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class LicenseColl {

	private List<LicenseInfo> licenseInfoList;
	
	/**
	 * 设置licenseInfoList。
	 * 
	 * @param licenseInfoList List<LicenseInfo>
	 */
	public void setLicenseInfoList(List<LicenseInfo> licenseInfoList) {
		this.licenseInfoList = licenseInfoList;
	}
	
	/**
	 * 获取licenseInfoList。
	 * 
	 * @return List<LicenseInfo>
	 */
	public List<LicenseInfo> getLicenseInfoList() {
		return licenseInfoList;
	}
	
}
