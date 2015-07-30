/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.startup;

/**
 * <pre>
 * Title:存放FBRP License信息。
 * Description: 程序功能的描述。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class LicenseInfo {

	//产品名称
	private String productName;
	
	//过期日期
	private String expiration;
	
	//签名
	private String signature;

	/**
	 * 获取productName。
	 * 
	 * @return String
	 */
	public String getProductName() {
		return productName;
	}

	/**
	 * 设置productName。
	 * 
	 * @param productName String
	 */
	public void setProductName(String productName) {
		this.productName = productName;
	}

	/**
	 * 获取expiration。
	 * 
	 * @return String
	 */
	public String getExpiration() {
		return expiration;
	}

	/**
	 * 设置expiration。
	 * 
	 * @param expiration String
	 */
	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	/**
	 * 获取signature。
	 * 
	 * @return String
	 */
	public String getSignature() {
		return signature;
	}

	/**
	 * 设置signature。
	 * 
	 * @param signature String
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
		
}
