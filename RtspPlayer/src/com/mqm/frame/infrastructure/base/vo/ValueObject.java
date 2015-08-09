/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.base.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;


/**
 * 
 * <pre>
 * 所有的持久化VO必须继承的基类。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 * @see com.foresee.fbrp.infrastructure.vo.ValueObject 
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class ValueObject implements Serializable {

	private static final long serialVersionUID = -1576091278188456238L;
	/**
	 * 常量。
	 */
	public static final String DEL_FLAG_NORMAL = "n";
	/**
	 * 常量。
	 */
	public static final String DEL_FLAG_DELETED = "y";
	
	private String id;
	
	
	private String userOrgId;

	/***创建人代码。 */
	private String cjrDm;
	
	/** 创建时间。 */
	private Date cjsj;
	
	/** 修改人代码 。*/
	private String xgrDm;
	
	/** 修改时间。 */
	private Date xgsj;
	
	/** 数据所属机构代码。*/
	private String sjssjgDm;
	
	/**表里的扩展字段。*/
	private String ext1;
	private String ext2;
	private String ext3;
	private String ext4;
	private String ext5;
	
	/**
	 * 返回id。
	 * 
	 * @return String
	 */
	@Id
	public String getId() {
		return id;
	}
	
	/**
	 * 设置id。
	 * 
	 * @param id String
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 返回 userOrgId。
	 * 
	 * @return String
	 */
	@Transient
	public String getUserOrgId() {
		return userOrgId;
	}

	/**
	 * 设置 userOrgId。
	 * 
	 * @param userOrgId String
	 */
	public void setUserOrgId(String userOrgId) {
		this.userOrgId = userOrgId;
	}

	/**
	 * 返回 cjrDm。
	 * 
	 * @return String
	 */
	public String getCjrDm() {
		return cjrDm;
	}

	/**
	 * 设置 cjrDm。
	 * 
	 * @param cjrDm String
	 */
	public void setCjrDm(String cjrDm) {
		this.cjrDm = cjrDm;
	}

	/**
	 * 返回 cjsj。
	 * 
	 * @return Date
	 */
	public Date getCjsj() {
		return cjsj;
	}

	/**
	 * 设置 cjsj。
	 * 
	 * @param cjsj Date
	 */
	public void setCjsj(Date cjsj) {
		this.cjsj = cjsj;
	}

	/**
	 * 返回 xgrDm。
	 * 
	 * @return String
	 */
	public String getXgrDm() {
		return xgrDm;
	}

	/**
	 * 设置 xgrDm。
	 * 
	 * @param xgrDm String
	 */
	public void setXgrDm(String xgrDm) {
		this.xgrDm = xgrDm;
	}

	/**
	 * 返回 xgsj。
	 * 
	 * @return Date
	 */
	public Date getXgsj() {
		return xgsj;
	}

	/**
	 * 设置 xgsj。
	 * 
	 * @param xgsj Date
	 */
	public void setXgsj(Date xgsj) {
		this.xgsj = xgsj;
	}

	/**
	 * 返回 sjssjgDm。
	 * 
	 * @return String
	 */
	public String getSjssjgDm() {
		return sjssjgDm;
	}

	/**
	 * 设置 sjssjgDm。
	 * 
	 * @param sjssjgDm String
	 */
	public void setSjssjgDm(String sjssjgDm) {
		this.sjssjgDm = sjssjgDm;
	}

	/**
	 * 返回 ext1。
	 * 
	 * @return String
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * 设置 ext1。
	 * 
	 * @param ext1 String
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * 返回 ext2。
	 * 
	 * @return String
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * 设置 ext2。
	 * 
	 * @param ext2 String
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	/**
	 * 返回 ext3。
	 * 
	 * @return String
	 */
	public String getExt3() {
		return ext3;
	}

	/**
	 * 设置 ext3。
	 * 
	 * @param ext3 String
	 */
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	/**
	 * 返回 ext4。
	 * 
	 * @return String
	 */
	public String getExt4() {
		return ext4;
	}

	/**
	 * 设置 ext4。
	 * 
	 * @param ext4 String
	 */
	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	/**
	 * 返回 ext5。
	 * 
	 * @return String
	 */
	public String getExt5() {
		return ext5;
	}

	/**
	 * 设置 ext5。
	 * 
	 * @param ext5 String
	 */
	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}
	
}
