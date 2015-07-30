/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.vo;

import com.mqm.frame.infrastructure.base.vo.ValueObject;

/**
 * <pre>
 * 关联表FBRP_DBTOOL_DS的VO类。
 * </pre>
 * 
 * @author zouyongqiao zouyongqiao@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FbrpDbtoolDsVO extends ValueObject {

	private static final long serialVersionUID = -6747155081328595690L;

	private String name;// 数据源名称
	private String username;// 用户名
	private String dbType;// 数据源类型
	private String jndiName;// JNDI名
	private String driver;// 驱动类型
	private String url;// 连接地址
	private String includeOrgCode;
	private String swjgMc;// 税务机关名称
	private String sfbj;
	
	/**
	 * 构造器。
	 * 
	 */
	public FbrpDbtoolDsVO() {
		super();
	}

	/**
	 * 获取name。
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置name。
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取username。
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 设置username。
	 * 
	 * @param username String
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 获取dbType。
	 * 
	 * @return dbType
	 */
	public String getDbType() {
		return dbType;
	}

	/**
	 * 设置dbType。
	 * 
	 * @param dbType String
	 */
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	/**
	 * 获取jndiName。
	 * 
	 * @return jndiName
	 */
	public String getJndiName() {
		return jndiName;
	}

	/**
	 * 设置jndiName。
	 * 
	 * @param jndiName String
	 */
	public void setJndiName(String jndiName) {
		this.jndiName = jndiName;
	}

	/**
	 * 获取driver。
	 * 
	 * @return driver
	 */
	public String getDriver() {
		return driver;
	}

	/**
	 * 设置driver。
	 * 
	 * @param driver String
	 */
	public void setDriver(String driver) {
		this.driver = driver;
	}

	/**
	 * 获取url。
	 * 
	 * @return url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置url
	 * 
	 * @param url String
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 获取includeOrgCode。
	 * 
	 * @return includeOrgCode
	 */
	public String getIncludeOrgCode() {
		return includeOrgCode;
	}

	/**
	 * 设置includeOrgCode
	 * 
	 * @param includeOrgCode String
	 */
	public void setIncludeOrgCode(String includeOrgCode) {
		this.includeOrgCode = includeOrgCode;
	}

	/**
	 * 获取swjgMc。
	 * 
	 * @return swjgMc
	 */
	public String getSwjgMc() {
		return swjgMc;
	}

	/**
	 * 设置swjgMc。
	 * 
	 * @param swjgMc String
	 */ 
	public void setSwjgMc(String swjgMc) {
		this.swjgMc = swjgMc;
	}

	/**
	 * 获取sfbj。
	 * 
	 * @return String
	 */
	public String getSfbj() {
		return sfbj;
	}

	/**
	 * 设置sfbj。
	 * 
	 * @param sfbj String
	 */
	public void setSfbj(String sfbj) {
		this.sfbj = sfbj;
	}

}
