/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.bjgl.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.mqm.frame.common.DefaultServiceImpl;
import com.mqm.frame.sys.user.service.IUserService;
import com.mqm.frame.sys.user.vo.User;
import com.rtsp.bjgl.dao.IBjglDao;
import com.rtsp.bjgl.service.IBjglService;
import com.rtsp.bjgl.vo.Bj;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class BjglServiceImpl  extends DefaultServiceImpl<Bj> implements IBjglService<Bj> {
	
	private static final Logger log = Logger.getLogger(BjglServiceImpl.class);
	
	private IBjglDao bjglDao;
	
	
	public void setBjglDao(IBjglDao bjglDao) {
		this.bjglDao = bjglDao;
	}

}
