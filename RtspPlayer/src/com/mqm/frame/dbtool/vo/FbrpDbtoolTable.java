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
 * 关联表FBRP_DBTOOL_TABLE的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpDbtoolTable extends ValueObject {

	private static final long serialVersionUID = 5023001367383552377L;

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	@NotEmpty(message="必填")
	@Length(max=100, message="长度不用超过100")
	private String name;
	
	@Length(max=100, message="长度不用超过100")
	private java.lang.String tableAlias;
	
	private java.lang.Long nodeType;
	
	private java.lang.String parentId;
	
	@NotEmpty(message="必填")
	private java.lang.String dsId;
	
	@Length(max=199,message="长度不能超过200")
	private java.lang.String remark;

	private Long recordState;
	
	/**
	 * 设置 name。
	 * 
	 * @param name  String
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 *  返回 name。
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
	 * @param id the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: TABLE_ALIAS.
	 * 
	 * @return String
	 */
	public java.lang.String getTableAlias() {
		return tableAlias;
	}

	/**
	 * Set the value related to the column: TABLE_ALIAS.
	 * 
	 * @param tableAlias the TABLE_ALIAS value
	 */
	public void setTableAlias(java.lang.String tableAlias) {
		this.tableAlias = tableAlias;
	}

	/**
	 * Return the value associated with the column: NODE_TYPE.
	 * 
	 * @return Long
	 */
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

	/**
	 * Return the value associated with the column: PARENT_ID.
	 * 
	 * @return String
	 */
	public java.lang.String getParentId() {
		return parentId;
	}

	/**
	 * Set the value related to the column: PARENT_ID.
	 * 
	 * @param parentId the PARENT_ID value
	 */
	public void setParentId(java.lang.String parentId) {
		this.parentId = parentId;
	}

	/**
	 * Return the value associated with the column: DS_ID.
	 * 
	 * @return String
	 */
	public java.lang.String getDsId() {
		return dsId;
	}

	/**
	 * Set the value related to the column: DS_ID.
	 * 
	 * @param dsId the DS_ID value
	 */
	public void setDsId(java.lang.String dsId) {
		this.dsId = dsId;
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
}