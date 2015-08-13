/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.sxtgl.vo;

import java.io.Serializable;
import java.util.Date;

import com.mqm.frame.common.DefaultVO;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class Sxt extends DefaultVO implements Serializable {

	/**
	 * $
	 */
	private static final long serialVersionUID = 8671526278647651412L;

	private String url;
	
	private Date description;

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the description
	 */
	public Date getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(Date description) {
		this.description = description;
	}
	
	

}
