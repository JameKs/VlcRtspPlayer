/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
/*
package com.mqm.frame.infrastructure.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Id;
import javax.persistence.Transient;

*/
/**
 * 
 * <pre>
 * 所有的持久化VO必须继承的基类。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @deprecated 该类已迁移至com.foresee.fbrp.infrastructure.base.vo.ValueObject
 *//*
public class ValueObject extends com.foresee.fbrp.infrastructure.base.vo.ValueObject implements Serializable {

	private static final long serialVersionUID = 1L;
	*//**
	 * 常量。
	 *//*
	public static final String DEL_FLAG_NORMAL = "n";
	*//**
	 * 常量。
	 *//*
	public static final String DEL_FLAG_DELETED = "y";

	private int version;
	private Date createdTime;
	private String creatorId;
	private Date lastModifiedTime;
	private String lastModifierId;
	private Long recordState;
	private String delFlag;
	private Long valueStatus;
	private String ext1;
	private String ext2;
	private String ext3;
	private boolean checked = false;

   *//**
    * 默认的构造方法。
    *//*
	public ValueObject() {
	}


	*//**
	 * 获取createdTime。
	 *  
	 * @return Date
	 *//*
	@Transient
	public Date getCreatedTime() {
		return createdTime;
	}

	*//**
	 * 设置createdTime。
	 * 
	 * @param createdTime Date
	 *//*
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	*//**
	 * 获取creatorId。
	 * 
	 * @return String
	 *//*
	@Transient
	public String getCreatorId() {
		return creatorId;
	}
   *//**
    * 设置creatorId。
    * 
    * @param creatorId String
    *//*
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	
	*//**
	 * 获取delFlag。
	 * 
	 * @return String
	 *//*
	@Transient
	public String getDelFlag() {
		return delFlag;
	}

	*//**
	 * 设置delFlag。
	 * 
	 * @param delFlag String
	 *//*
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	
	*//**
	 * 获取lastModifiedTime。
	 * 
	 * @return Date
	 *//*
	@Transient
	public Date getLastModifiedTime() {
		return lastModifiedTime;
	}

	*//**
	 * 设置 lastModifiedTime。
	 * 
	 * @param lastModifiedTime Date
	 *//*
	public void setLastModifiedTime(Date lastModifiedTime) {
		this.lastModifiedTime = lastModifiedTime;
	}
	
	*//**
	 * 获取lastModifierId。
	 * 
	 * @return String
	 *//*
	@Transient
	public String getLastModifierId() {
		return lastModifierId;
	}

	*//**
	 * 设置lastModifierId。
	 * 
	 * @param lastModifierId String
	 *//*
	public void setLastModifierId(String lastModifierId) {
		this.lastModifierId = lastModifierId;
	}
	*//**
	 * 获取recordState。
	 * 
	 * @return Long
	 *//*
	@Transient
	public Long getRecordState() {
		return recordState;
	}

	*//**
	 * 设置recordState。
	 * 
	 * @param recordState  Long
	 *//*
	public void setRecordState(Long recordState) {
		this.recordState = recordState;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || !obj.getClass().equals(getClass())) {
			return false;
		}
		ValueObject concretevalueobject = (ValueObject) obj;
		String objectid = this.getId();
		String objectid1 = concretevalueobject.getId();
		if (objectid != null) {
			return objectid.equals(objectid1);
		}
		if (objectid1 != null) {
			return false;
		} else {
			return super.equals(obj);
		}
	}

	
	@Override
	public int hashCode() {
		int result = 17;
		if (this.getId() != null) {
			result = 37 * result + this.getId().hashCode();
		}
		if (creatorId != null) {
			result = 37 * result + creatorId.hashCode();
		}
		return result;
	}
	*//**
	 * 获取ext1。
	 * 
	 * @return String
	 *//*
	@Transient
	public String getExt1() {
		return ext1;
	}
    *//**
     * 设置ext1。
     * 
     * @param ext1 String
     *//*
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}
	*//**
	 * 获取ext2。
	 * 
	 * @return String
	 *//*
	@Transient
	public String getExt2() {
		return ext2;
	}

	*//**
	 * 设置ext2。
	 * 
	 * @param ext2 String
	 *//*
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	*//**
	 * 获取valueStatus。
	 * 
	 * @return Long
	 *//*
	@Transient
	public Long getValueStatus() {
		return valueStatus;
	}

	*//**
	 * 设置valueStatus。
	 * 
	 * @param valueStatus  Long
	 *//*
	public void setValueStatus(Long valueStatus) {
		this.valueStatus = valueStatus;
	}
	*//**
	 * 获取ext3。
	 * 
	 * @return String
	 *//*
	@Transient
	public String getExt3() {
		return ext3;
	}

	*//**
	 * 设置ext3 。
	 * 
	 * @param ext3 String
	 *//*
	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	*//**
	 * 设置version。
	 * 
	 * @param version int
	 *//*
	public void setVersion(int version) {
		this.version = version;
	}

	*//**
	 * 返回 version。
	 * 
	 * @return  int
	 *//*
	@Transient
	public int getVersion() {
		return this.version;
	}

	*//**
	 * 返回 checked。
	 * 
	 * @return  boolean
	 *//*
	@Transient
	public boolean getChecked() {
		return checked;
	}	

	*//**
	 * 设置 checked。
	 * 
	 * @param checked boolean
	 *//*
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	*//**
	 * 获取名字。
	 * 
	 * @return String
	 *//*
	@Transient
	public String getName() {
		return "";
	}
}*/