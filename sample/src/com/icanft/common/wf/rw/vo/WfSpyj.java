/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.rw.vo;

import com.icanft.common.DefaultVo;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年6月3日
 */
public class WfSpyj extends DefaultVo{

	private static final long serialVersionUID = 3449508947402366892L;

	private String userId;
	
	private String yhrwId;

	private String spyj;

	
	
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
	 * @return 返回 spyj。
	 */
	public String getSpyj() {
		return spyj;
	}

	/**
	 * @param spyj 设置 spyj。
	 */
	public void setSpyj(String spyj) {
		this.spyj = spyj;
	}

	

}
