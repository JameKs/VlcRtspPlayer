/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.sxtgl.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

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
public class SxtglServiceImpl implements ISxtglService {
	
	private static final Logger log = Logger.getLogger(SxtglServiceImpl.class);
	
	private ISxtglDao sxtglDao;
	
	@Override
	public void insert(Sxt sxt){
		sxtglDao.insert(sxt);
	}
	
	@Override
	public void delete(Sxt sxt){
		sxtglDao.delete(sxt);
	}

	@Override
	public void update(Sxt sxt){
		sxtglDao.update(sxt);
	}
	
	@Override
	public List findList(Sxt sxt){
		List qj = sxtglDao.findList(sxt);
		return qj;
	}

	@Override
	public int findListCount(Sxt sxt){
		int count = sxtglDao.findListCount(sxt);
		return count;
	}
	
	/**
	 * @param sxtglDao the sxtglDao to set
	 */
	public void setSxtglDao(ISxtglDao sxtglDao) {
		this.sxtglDao = sxtglDao;
	}

	/* (non-Javadoc)
	 * @see com.rtsp.sxtgl.service.ISxtglService#findById(java.lang.String)
	 */
	@Override
	public Sxt findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
