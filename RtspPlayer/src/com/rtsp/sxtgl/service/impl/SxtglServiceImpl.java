/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.sxtgl.service.impl;

import org.apache.log4j.Logger;

import com.mqm.frame.common.DefaultServiceImpl;
import com.rtsp.sxtgl.dao.ISxtglDao;
import com.rtsp.sxtgl.service.ISxtglService;
import com.rtsp.sxtgl.vo.Sxt;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class SxtglServiceImpl extends DefaultServiceImpl<Sxt> implements ISxtglService<Sxt> {
	
	private static final Logger log = Logger.getLogger(SxtglServiceImpl.class);
	
	private ISxtglDao sxtglDao;
	
	/**
	 * @param sxtglDao the sxtglDao to set
	 */
	public void setSxtglDao(ISxtglDao sxtglDao) {
		this.sxtglDao = sxtglDao;
	}

}
