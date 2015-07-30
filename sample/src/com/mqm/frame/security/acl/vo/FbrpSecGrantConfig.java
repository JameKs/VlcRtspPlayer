/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 关联表FBRP_SEC_GRANTCONFIG的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
@Table(name="FBRP_SEC_GRANTCONFIG")
public class FbrpSecGrantConfig extends ValueObject {

	private static final long serialVersionUID = -4777186091475178172L;

	@NotEmpty(message = "必填")
	@Length(max = 200, message = "字符长度不能超过200")
	private java.lang.String grantName;

	@NotEmpty(message = "必填")
	@Length(max = 32, message = "字符长度不能超过32")
	private java.lang.String dsId;

	@NotEmpty(message = "必填")
	@Length(max = 100, message = "字符长度不能超过100")
	private java.lang.String dsTableName;

	@NotEmpty(message = "必填")
	@Length(max = 100, message = "字符长度不能超过100")
	private java.lang.String idColumnName;

	@NotEmpty(message = "必填")
	@Length(max = 100, message = "字符长度不能超过100")
	private java.lang.String parentColumnName;
	
	private String nameColumn;

	private String principalType;
	private String ext1;
	private String ext2;
	private String ext3;
	/**
	 *  返回 grantName。
	 * 
	 * @return 返回 grantName。
	 */
	@Column(name="GRANTNAME")
	public java.lang.String getGrantName() {
		return grantName;
	}

	/**
	 * 设置 grantName。
	 * 
	 * @param grantName 设置 grantName。
	 */
	public void setGrantName(java.lang.String grantName) {
		this.grantName = grantName;
	}

	/**
	 * 返回 dsId。
	 * 
	 * @return 返回 dsId。
	 */
	public java.lang.String getDsId() {
		return dsId;
	}

	/**
	 *  设置 dsId。
	 * 
	 * @param dsId 设置 dsId。
	 */
	public void setDsId(java.lang.String dsId) {
		this.dsId = dsId;
	}

	/**
	 *  返回 dsTableName。
	 * 
	 * @return 返回 dsTableName。
	 */
	@Column(name="DS_TABLENAME")
	public java.lang.String getDsTableName() {
		return dsTableName;
	}

	/**
	 * 设置 dsTableName。
	 * 
	 * @param dsTableName 设置 dsTableName。
	 */
	public void setDsTableName(java.lang.String dsTableName) {
		this.dsTableName = dsTableName;
	}

	/**
	 * 返回 idColumnName。
	 * 
	 * @return 返回 idColumnName。
	 */
	@Column(name="ID_COLUMNNAME")
	public java.lang.String getIdColumnName() {
		return idColumnName;
	}

	/**
	 *  设置 idColumnName。
	 * 
	 * @param idColumnName 设置 idColumnName。
	 */
	public void setIdColumnName(java.lang.String idColumnName) {
		this.idColumnName = idColumnName;
	}

	/**
	 * 返回 parentColumnName。
	 * 
	 * @return 返回 parentColumnName。
	 */
	@Transient
	@Column(name="PARENT_COLUMNNAME")
	public java.lang.String getParentColumnName() {
		return parentColumnName;
	}

	/**
	 * 设置 parentColumnName。
	 * 
	 * @param parentColumnName 设置 parentColumnName。
	 */
	public void setParentColumnName(java.lang.String parentColumnName) {
		this.parentColumnName = parentColumnName;
	}

	/**
	 * 返回 nameColumn。
	 * 
	 * @return 返回 nameColumn。
	 */
	public String getNameColumn() {
		return nameColumn;
	}

	/**
	 * 设置 nameColumn。
	 * 
	 * @param nameColumn 设置 nameColumn。
	 */
	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}

	/**
	 * 返回 principalType。
	 * 
	 * @return 返回 principalType。
	 */
	public String getPrincipalType() {
		return principalType;
	}

	/**
	 * 设置 principalType。
	 * 
	 * @param principalType 设置 principalType。
	 */
	public void setPrincipalType(String principalType) {
		this.principalType = principalType;
	}

	/**
	 * 获取Ext1。
	 * 
	 * @return String
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * 设置Ext1。
	 * 
	 * @param ext1 String
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * 获取Ext2。
	 * 
	 * @return String
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * 设置Ext2。
	 * 
	 * @param ext2 String
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * 获取Ext3。
	 * 
	 * @return String
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * 设置Ext3。
	 * 
	 * @param ext3 String
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}
	
}