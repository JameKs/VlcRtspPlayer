/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.vo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 关联表FBRP_DBTOOL_DS的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpDbtoolDs extends ValueObject {

	private static final long serialVersionUID = 3807295511397185532L;

	private int hashCode = Integer.MIN_VALUE;
	/** 默认最大活动连接数。*/
	public static final Long DEFAULT_MAX_ACTIVE = 8L;
	/** 默认最大空闲连接数。 */
	public static final Long DEFAULT_MAX_IDLE = 8L;
	/** 默认最小空闲连接数。 */
	public static final Long DEFAULT_MIN_IDLE = 0L;
	/**  默认最大等待连接时间（毫秒）。 */
	public static final Long DEFAULT_MAX_WAIT = 60000L;
	
	// primary key
	private java.lang.String id;

	// fields
	@NotEmpty(message="必填")
	@Length(max=20,message="长度不能超过20")
	private String name;
	
	@NotEmpty(message="必填")
	private java.lang.String dbType;
	
	@NotEmpty(message="必填")
	@Length(max=200,message="长度不能超过200")
	private java.lang.String url;
	
	@NotEmpty(message="必填")
	@Length(max=100,message="长度不能超过100")
	private java.lang.String driver;
	
	@Length(max=20,message="长度不能超过20")
	private java.lang.String username;
	
	@Length(max=20,message="长度不能超过20")
	private java.lang.String passwd;
	
	@NotEmpty(message="必填")
	@Length(max=60,message="长度不能超过60")
	private java.lang.String jndiName;
	
	@NotBlank(message="必填")
	@Min(value=1,message="应大于或等于1")
	private java.lang.Long maxActive;
	
	@NotBlank(message="必填")
	@Min(value=1,message="应大于或等于1")
	private java.lang.Long maxIdle;
	
	@NotBlank(message="必填")
	@Min(value=0,message="应大于或等于0")
	private java.lang.Long minIdle;
	
	@NotBlank(message="必填")
	@Min(value=1,message="应大于或等于1")
	private java.lang.Long maxWait;
	
	@Length(max=200,message="长度不能超过200")
	private java.lang.String validationQuery;
	
	@Length(max=200,message="长度不能超过200")
	private java.lang.String remark;
	
	// 节点类型，比如0（目录）、1（叶子节点）
	private java.lang.Long nodeType;

	/**
	 *  设置 name。
	 * 
	 * @param name String
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 返回 name。
	 * 
	 * @return String
	 */
	@Column(name="NAME")
	public String getName() {
		return name;
	}

	/**
	 * Return the value associated with the column: DB_TYPE.
	 * 
	 * @return String
	 */
	public java.lang.String getDbType() {
		return dbType;
	}

	/**
	 * Set the value related to the column: DB_TYPE.
	 * 
	 * @param dbType the DB_TYPE value
	 */
	public void setDbType(java.lang.String dbType) {
		this.dbType = dbType;
	}

	/**
	 * Return the value associated with the column: URL.
	 * 
	 * @return String
	 */
	public java.lang.String getUrl() {
		return url;
	}

	/**
	 * Set the value related to the column: URL.
	 * 
	 * @param url the URL value
	 */
	public void setUrl(java.lang.String url) {
		this.url = url;
	}

	/**
	 * Return the value associated with the column: DRIVER.
	 * 
	 * @return String
	 */
	public java.lang.String getDriver() {
		return driver;
	}

	/**
	 * Set the value related to the column: DRIVER.
	 * 
	 * @param driver the DRIVER value
	 */
	public void setDriver(java.lang.String driver) {
		this.driver = driver;
	}

	/**
	 * Return the value associated with the column: USERNAME.
	 * 
	 * @return String
	 */
	public java.lang.String getUsername() {
		return username;
	}

	/**
	 * Set the value related to the column: USERNAME.
	 * 
	 * @param username the USERNAME value
	 */
	public void setUsername(java.lang.String username) {
		this.username = username;
	}


	/**
	 * 返回 passwd。
	 * 
	 * @return String
	 */
	public java.lang.String getPasswd() {
		return passwd;
	}

	/**
	 *  设置 passwd。
	 * 
	 * @param passwd String
	 */
	public void setPasswd(java.lang.String passwd) {
		this.passwd = passwd;
	}

	/**
	 * Return the value associated with the column: JNDI_NAME.
	 * 
	 * @return String
	 */
	public java.lang.String getJndiName() {
		return jndiName;
	}

	/**
	 * Set the value related to the column: JNDI_NAME.
	 * 
	 * @param jndiName the JNDI_NAME value
	 */
	public void setJndiName(java.lang.String jndiName) {
		this.jndiName = jndiName;
	}

	/**
	 * Return the value associated with the column: MAX_ACTIVE.
	 * 
	 * @return Long
	 */
	public java.lang.Long getMaxActive() {
		return maxActive;
	}

	/**
	 * Set the value related to the column: MAX_ACTIVE.
	 * 
	 * @param maxActive the MAX_ACTIVE value
	 */
	public void setMaxActive(java.lang.Long maxActive) {
		this.maxActive = maxActive;
	}

	/**
	 * Return the value associated with the column: MAX_IDLE.
	 * 
	 * @return Long
	 */
	public java.lang.Long getMaxIdle() {
		return maxIdle;
	}

	/**
	 * Set the value related to the column: MAX_IDLE.
	 * 
	 * @param maxIdle the MAX_IDLE value
	 */
	public void setMaxIdle(java.lang.Long maxIdle) {
		this.maxIdle = maxIdle;
	}

	/**
	 * Return the value associated with the column: MIN_IDLE.
	 * 
	 * @return Long
	 */
	public java.lang.Long getMinIdle() {
		return minIdle;
	}

	/**
	 * Set the value related to the column: MIN_IDLE.
	 * 
	 * @param minIdle the MIN_IDLE value
	 */
	public void setMinIdle(java.lang.Long minIdle) {
		this.minIdle = minIdle;
	}

	/**
	 * Return the value associated with the column: MAX_WAIT.
	 * 
	 * @return Long
	 */
	public java.lang.Long getMaxWait() {
		return maxWait;
	}

	/**
	 * Set the value related to the column: MAX_WAIT.
	 * 
	 * @param maxWait the MAX_WAIT value
	 */
	public void setMaxWait(java.lang.Long maxWait) {
		this.maxWait = maxWait;
	}

	/**
	 * Return the value associated with the column: VALIDATION_QUERY.
	 * 
	 * @return String
	 */
	public java.lang.String getValidationQuery() {
		return validationQuery;
	}

	/**
	 * Set the value related to the column: VALIDATION_QUERY.
	 * 
	 * @param validationQuery the VALIDATION_QUERY value
	 */
	public void setValidationQuery(java.lang.String validationQuery) {
		this.validationQuery = validationQuery;
	}

	/**
	 * Return the value associated with the column: REMARK.
	 * 
	 * @return String
	 */
	public java.lang.String getRemark() {
		return remark;
	}

	/**
	 * Set the value related to the column: REMARK.
	 * 
	 * @param remark the REMARK value
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/**
	 * Return the value associated with the column: NODE_TYPE.
	 * 
	 * @return Long
	 */
	@Transient
	public java.lang.Long getNodeType() {
		return nodeType;
	}

	/**
	 * Set the value related to the column: NODE_TYPE.
	 * 
	 * @param nodeType the NODE_TYPE value
	 */
	public void setNodeType(java.lang.Long nodeType) {
		this.nodeType = nodeType;
	}
	
}