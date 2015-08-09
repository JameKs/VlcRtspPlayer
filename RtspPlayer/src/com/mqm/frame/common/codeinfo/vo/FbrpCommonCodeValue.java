/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.codeinfo.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 关联表FBRP_INFRA_CODE_VALUE的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpCommonCodeValue extends ValueObject {

	private static final long serialVersionUID = -6883718176172189331L;

	// fields
	private java.lang.String codeTypeId;
	private java.lang.String value1;
	private java.lang.String value2;
	private java.lang.String value3;
	private java.lang.String value4;
	private java.lang.String value5;
	private java.lang.String value6;
	private java.lang.Long sortNo;
	private java.lang.String remark;
	private java.lang.String name;
	private int version;
	private String ext1;
	private String ext2;
	private String ext3;

	private Long recordState;
	
	/**
	 * Return the value associated with the column: CODE_TYPE_ID。
	 * 
	 * @return String codeTypeId
	 */
	public java.lang.String getCodeTypeId() {
		return codeTypeId;
	}

	/**
	 * Set the value related to the column: CODE_TYPE_ID。
	 * 
	 * @param codeTypeId
	 *            the CODE_TYPE_ID value
	 */
	public void setCodeTypeId(java.lang.String codeTypeId) {
		this.codeTypeId = codeTypeId;
	}

	/**
	 * Return the value associated with the column: VALUE1。
	 * 
	 * @return String value1;
	 */
	public java.lang.String getValue1() {
		return value1;
	}

	/**
	 * Set the value related to the column: VALUE1。
	 * 
	 * @param value1
	 *            the VALUE1 value
	 */
	public void setValue1(java.lang.String value1) {
		this.value1 = value1;
	}

	/**
	 * Return the value associated with the column: VALUE2。
	 * 
	 * @return String value2
	 */
	public java.lang.String getValue2() {
		return value2;
	}

	/**
	 * Set the value related to the column: VALUE2。
	 * 
	 * @param value2
	 *            the VALUE2 value
	 */
	public void setValue2(java.lang.String value2) {
		this.value2 = value2;
	}

	/**
	 * Return the value associated with the column: VALUE3。
	 * 
	 * @return String value3
	 */
	public java.lang.String getValue3() {
		return value3;
	}

	/**
	 * Set the value related to the column: VALUE3。
	 * 
	 * @param value3
	 *            the VALUE3 value
	 */
	public void setValue3(java.lang.String value3) {
		this.value3 = value3;
	}

	/**
	 * Return the value associated with the column: VALUE4。
	 * 
	 * @return String value4
	 */
	public java.lang.String getValue4() {
		return value4;
	}

	/**
	 * Set the value related to the column: VALUE4。
	 * 
	 * @param value4
	 *            the VALUE4 value
	 */
	public void setValue4(java.lang.String value4) {
		this.value4 = value4;
	}

	/**
	 * Return the value associated with the column: VALUE5。
	 * 
	 * @return String value5
	 */
	public java.lang.String getValue5() {
		return value5;
	}

	/**
	 * Set the value related to the column: VALUE5。
	 * 
	 * @param value5
	 *            the VALUE5 value
	 */
	public void setValue5(java.lang.String value5) {
		this.value5 = value5;
	}

	/**
	 * Return the value associated with the column: VALUE6。
	 * 
	 * @return String value6
	 */
	public java.lang.String getValue6() {
		return value6;
	}

	/**
	 * Set the value related to the column: VALUE6。
	 * 
	 * @param value6
	 *            the VALUE6 value
	 */
	public void setValue6(java.lang.String value6) {
		this.value6 = value6;
	}

	/**
	 * Return the value associated with the column: SORT_NO。
	 * 
	 * @return Long sortNo
	 */
	public java.lang.Long getSortNo() {
		return sortNo;
	}

	/**
	 * Set the value related to the column: SORT_NO。
	 * 
	 * @param sortNo
	 *            the SORT_NO value
	 */
	public void setSortNo(java.lang.Long sortNo) {
		this.sortNo = sortNo;
	}

	/**
	 * Return the value associated with the column: REMARK。
	 * 
	 * @return String remark
	 */
	public java.lang.String getRemark() {
		return remark;
	}

	/**
	 * Set the value related to the column: REMARK。
	 * 
	 * @param remark
	 *            the REMARK value
	 */
	public void setRemark(java.lang.String remark) {
		this.remark = remark;
	}

	/**
	 * 返回name。
	 * 
	 * @return 返回 name。
	 */
	@Column(name="name")
	public java.lang.String getName() {
		return name;
	}

	/**
	 * 设置name。
	 * 
	 * @param name
	 *            设置 name。
	 */
	public void setName(java.lang.String name) {
		this.name = name;
	}

	/**
	 * 获取version。
	 * 
	 * @return int
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * 设置version。
	 * 
	 * @param version int
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	
	/**
	 * 获取ext1。
	 * 
	 * @return String
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * 设置ext1。
	 * 
	 * @param ext1 String
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * 获取ext2。
	 * 
	 * @return String
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * 设置ext2。
	 * 
	 * @param ext2 String
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * 获取ext3。
	 * 
	 * @return String
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * 设置ext3。
	 * 
	 * @param ext3 String
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
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