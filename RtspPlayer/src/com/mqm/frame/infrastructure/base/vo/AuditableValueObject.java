/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.base.vo;

import java.util.Date;

/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class AuditableValueObject extends ValueObject implements IAuditable{
	private static final long serialVersionUID = -8735131181500225029L;
	
	private String creatorId;
	private Date createdTime;
	private String updatorId;
	private Date updatedTime;
	
	@Override
	public String getCreatorId() {
		return creatorId;
	}
	@Override
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	@Override
	public Date getCreatedTime() {
		return createdTime;
	}
	@Override
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	@Override
	public String getUpdatorId() {
		return updatorId;
	}
	@Override
	public void setUpdatorId(String updatorId) {
		this.updatorId = updatorId;
	}
	@Override
	public Date getUpdatedTime() {
		return updatedTime;
	}
	@Override
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
}
