/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.bjgl.vo;

import java.io.Serializable;
import java.util.Date;

import com.mqm.frame.common.DefaultVo;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class Bj extends DefaultVo implements Serializable {

	/**
	 * $
	 */
	private static final long serialVersionUID = 8671526278647651412L;

	private String bjDm;
	
	private Date bjMc;

	/**
	 * @return the bjDm
	 */
	public String getBjDm() {
		return bjDm;
	}

	/**
	 * @param bjDm the bjDm to set
	 */
	public void setBjDm(String bjDm) {
		this.bjDm = bjDm;
	}

	/**
	 * @return the bjMc
	 */
	public Date getBjMc() {
		return bjMc;
	}

	/**
	 * @param bjMc the bjMc to set
	 */
	public void setBjMc(Date bjMc) {
		this.bjMc = bjMc;
	}
	
	

}
