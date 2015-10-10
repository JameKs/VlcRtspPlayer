/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.auditlog.vo;

import java.util.Date;

/**
 * <pre>
 * 日志管理查询参数Bean。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpCommonAuditLogQueryVO extends FbrpCommonAuditLog {
	
	private static final long serialVersionUID = -4711257117271719777L;
	//起始时间
	private Date startTime;
	//截止时间
	private Date endTime;
	/**
	 * 返回 startTime。
	 * 
	 * @return 返回 startTime。
	 */
	public Date getStartTime() {
		return startTime;
	}
	/**
	 * 设置 startTime。
	 * 
	 * @param startTime 设置 startTime。
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	/**
	 * 返回 endTime。
	 * 
	 * @return 返回 endTime。
	 */
	public Date getEndTime() {
		return endTime;
	}
	/**
	 * 设置 endTime。
	 * 
	 * @param endTime 设置 endTime。
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
}
