/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.sxtgl.service.impl;

import java.util.List;

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
public class SxtglServiceImpl implements ISxtglService<Sxt> {
	
	private static final Logger log = Logger.getLogger(SxtglServiceImpl.class);
	
	private ISxtglDao sxtglDao;
	
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(Sxt t) {
		sxtglDao.insert("insert", t);
	}

	@Override
	public void deleteById(String id) {
		sxtglDao.deleteById("deleteById", id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		for(String id : ids){
			this.deleteById(id);
		}
	}

	@Override
	public void update(Sxt t) {
		sxtglDao.update("update", t);
	}

	@Override
	public Sxt findById(String id) {
		return (Sxt)sxtglDao.findById("findById", id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(Sxt t) {
		return sxtglDao.findList("findList", t);
	}

	@Override
	public List findPageList(Sxt t, int pageIndex, int pageSize) {
		return sxtglDao.findPageList("findList", t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(Sxt t) {
		return sxtglDao.findListCount("findList", t);
	}
	
	@Override
	public List findAll() {
		return sxtglDao.findAll("findAll");
	}
	
	/**
	 * @param sxtglDao the sxtglDao to set
	 */
	public void setSxtglDao(ISxtglDao sxtglDao) {
		this.sxtglDao = sxtglDao;
	}

}
