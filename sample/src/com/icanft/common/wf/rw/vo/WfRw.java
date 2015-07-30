/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.rw.vo;

import com.icanft.common.DefaultTaskVo;

/**
 * <pre>
 * 用户流程任务
 * <pre>
 * @author meihu2007@sina.com
 * 2015年6月3日
 */
public class WfRw extends DefaultTaskVo {
	
	private String userId ;
	
	private String yhrwId ;
	
	private String rwlxId ;
	
	private String rwlxDm ;
	
	private String zxZt ; //执行状态
	
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
	 * @return 返回 yhrwId。
	 */
	public String getYhrwId() {
		return yhrwId;
	}

	/**
	 * @param yhrwId 设置 yhrwId。
	 */
	public void setYhrwId(String yhrwId) {
		this.yhrwId = yhrwId;
	}

	/**
	 * @return 返回 rwlxId。
	 */
	public String getRwlxId() {
		return rwlxId;
	}

	/**
	 * @param rwlxId 设置 rwlxId。
	 */
	public void setRwlxId(String rwlxId) {
		this.rwlxId = rwlxId;
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

	/**
	 * @return 返回 zxZt。
	 */
	public String getZxZt() {
		return zxZt;
	}

	/**
	 * @param zxZt 设置 zxZt。
	 */
	public void setZxZt(String zxZt) {
		this.zxZt = zxZt;
	}

	
}
