/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.vo;

import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 关联表FBRP_DBTOOL_TABLE_COL的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpDbtoolTableCol extends ValueObject {

	private static final long serialVersionUID = -3013581083515255437L;

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	@NotEmpty(message = "必填")
	@Length(max = 99, message = "长度不能超过100")
	private String name;

	@Length(max = 99, message = "长度不能超过100")
	private java.lang.String colAlias;

	private java.lang.String length;

	@NotEmpty(message = "必填")
	private java.lang.String colType;

	private java.lang.Long primaryKeyFlag;

	private java.lang.Long nullable;

	@Length(max = 199, message = "长度不能超过200")
	private java.lang.String defaultValue;

	@NotEmpty(message = "必填")
	private java.lang.String tableId;

	@Length(max = 199, message = "长度不能超过200")
	private java.lang.String remark;
	
	private java.lang.Long sortNo;
	private Long recordState;
	private Long valueStatus;
	/**
	 * 获取colAlias。
	 * 
	 * @return String
	 */
	public java.lang.String getEscapedColAlias() {
		if (this.colAlias != null && this.colAlias.length() > 20) {
			return this.colAlias.substring(0, 20) + "...";
		}
		return this.colAlias;
	}

	/**
	 * 获取remark。
	 * 
	 * @return String
	 */
	public java.lang.String getEscapedRemark() {
		if (this.remark != null && this.remark.length() > 20) {
			return this.remark.substring(0, 20) + "...";
		}
		return this.remark;
	}

	/**
	 *  设置 name。
	 * 
	 * @param name String
	 *           
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回 name。
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Return the unique identifier of this class.
	 * 
	 * @return String
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class.
	 * 
	 * @param id  the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: COL_ALIAS.
	 * 
	 * @return String
	 */
	public java.lang.String getColAlias() {
		return colAlias;
	}

	/**
	 * Set the value related to the column: COL_ALIAS.
	 * 
	 * @param colAlias  the COL_ALIAS value
	 */
	public void setColAlias(java.lang.String colAlias) {
		this.colAlias = colAlias;
	}

	/**
	 * Return the value associated with the column: LENGTH.
	 * 
	 * @return String
	 */
	public java.lang.String getLength() {
		return length;
	}

	/**
	 * Set the value related to the column: LENGTH。
	 * 
	 * @param length  the LENGTH value
	 */
	public void setLength(java.lang.String length) {
		this.length = length;
	}

	/**
	 * Return the value associated with the column: COL_TYPE.
	 * 
	 * @return String
	 */
	public java.lang.String getColType() {
		return colType;
	}

	/**
	 * Set the value related to the column: COL_TYPE.
	 * 
	 * @param colType  the COL_TYPE value
	 */
	public void setColType(java.lang.String colType) {
		this.colType = colType;
	}

	/**
	 * Return the value associated with the column: PRIMARY_KEY_FLAG.
	 * 
	 * @return Long
	 */
	public java.lang.Long getPrimaryKeyFlag() {
		return primaryKeyFlag;
	}

	/**
	 * Set the value related to the column: PRIMARY_KEY_FLAG.
	 * 
	 * @param primaryKeyFlag  the PRIMARY_KEY_FLAG value
	 */
	public void setPrimaryKeyFlag(java.lang.Long primaryKeyFlag) {
		this.primaryKeyFlag = primaryKeyFlag;
	}

	/**
	 * Return the value associated with the column: NULLABLE.
	 * 
	 * @return Long
	 */
	public java.lang.Long getNullable() {
		return nullable;
	}

	/**
	 * Set the value related to the column: NULLABLE.
	 * 
	 * @param nullable  the NULLABLE value
	 */
	public void setNullable(java.lang.Long nullable) {
		this.nullable = nullable;
	}

	/**
	 * Return the value associated with the column: DEFAULT_VALUE.
	 * 
	 * @return String
	 */
	public java.lang.String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * Set the value related to the column: DEFAULT_VALUE.
	 * 
	 * @param defaultValue  the DEFAULT_VALUE value
	 */
	public void setDefaultValue(java.lang.String defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * Return the value associated with the column: TABLE_ID.
	 * 
	 * @return String
	 */
	public java.lang.String getTableId() {
		return tableId;
	}

	/**
	 * Set the value related to the column: TABLE_ID.
	 * 
	 * @param tableId the TABLE_ID value
	 */
	public void setTableId(java.lang.String tableId) {
		this.tableId = tableId;
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
	 * Return the value associated with the column: SORT_NO.
	 * 
	 * @return Long
	 */
	public java.lang.Long getSortNo() {
		return sortNo;
	}

	/**
	 * Set the value related to the column: SORT_NO.
	 * 
	 * @param sortNo  the SORT_NO value
	 */
	public void setSortNo(java.lang.Long sortNo) {
		this.sortNo = sortNo;
	}
	/**
	 * 获取recordState。
	 * 
	 * @return Long
	 */
	@Transient
	public Long getRecordState() {
		return recordState;
	}

	/**
	 * 设置recordState。
	 * 
	 * @param recordState  Long
	 */
	public void setRecordState(Long recordState) {
		this.recordState = recordState;
	}
	
	/**
	 * 获取valueStatus。
	 * 
	 * @return Long
	 */
	@Transient
	public Long getValueStatus() {
		return valueStatus;
	}

	/**
	 * 设置valueStatus。
	 * 
	 * @param valueStatus  Long
	 */
	public void setValueStatus(Long valueStatus) {
		this.valueStatus = valueStatus;
	}
	
}