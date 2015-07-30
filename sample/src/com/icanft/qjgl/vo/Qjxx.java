/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.qjgl.vo;

import java.io.Serializable;
import java.util.Date;

import com.icanft.common.DefaultTaskVo;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class Qjxx extends DefaultTaskVo implements Serializable {

	/**
	 * $
	 */
	private static final long serialVersionUID = 8671526278647651412L;

	private String userId;
	
	private Date kssj;

	private Date jssj;

	private String qjyy;
	
	private String rwId;
	
	private String rwlxDm;

	/**
	 * @return 返回 userId。
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * @param userId 设置 userId。
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * @return 返回 kssj。
	 */
	public Date getKssj() {
		return kssj;
	}

	/**
	 * @param kssj 设置 kssj。
	 */
	public void setKssj(Date kssj) {
		this.kssj = kssj;
	}

	/**
	 * @return 返回 jssj。
	 */
	public Date getJssj() {
		return jssj;
	}

	/**
	 * @param jssj 设置 jssj。
	 */
	public void setJssj(Date jssj) {
		this.jssj = jssj;
	}

	/**
	 * @return 返回 qjyy。
	 */
	public String getQjyy() {
		return qjyy;
	}

	/**
	 * @param qjyy 设置 qjyy。
	 */
	public void setQjyy(String qjyy) {
		this.qjyy = qjyy;
	}

	/**
	 * @return 返回 rwId。
	 */
	public String getRwId() {
		return rwId;
	}

	/**
	 * @param rwId 设置 rwId。
	 */
	public void setRwId(String rwId) {
		this.rwId = rwId;
	}

	/**
	 * @return 返回 rwlxDm。
	 */
	public String getRwlxDm() {
		return rwlxDm;
	}

	/**
	 * @param rwlxDm 设置 rwlxDm。
	 */
	public void setRwlxDm(String rwlxDm) {
		this.rwlxDm = rwlxDm;
	}

	
}
