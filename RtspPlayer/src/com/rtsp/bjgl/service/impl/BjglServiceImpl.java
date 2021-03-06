/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.bjgl.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

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
public class BjglServiceImpl implements IBjglService<Bj> {
	
	private static final Logger log = Logger.getLogger(BjglServiceImpl.class);
	
	private IBjglDao bjglDao;
	
	
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(Bj t) {
		bjglDao.insert(t);
	}

	@Override
	public void deleteById(String id) {
		bjglDao.deleteById(id);
	}
	
	@Override
	public void deleteByIds(String[] ids) {
		for(String id : ids){
			this.deleteById(id);
		}
	}

	@Override
	public void update(Bj t) {
		bjglDao.update(t);
	}

	@Override
	public Bj findById(String id) {
		return (Bj)bjglDao.findById(id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(Bj t) {
		return bjglDao.findList(t);
	}

	@Override
	public List findListPage(Bj t, int pageIndex, int pageSize) {
		return bjglDao.findListPage(t , pageIndex , pageSize);
	}

	@Override
	public int findListCount(Bj t) {
		return bjglDao.findListCount(t);
	}
	
	@Override
	public List findAll() {
		return bjglDao.findAll();
	}
	
	public void setBjglDao(IBjglDao bjglDao) {
		this.bjglDao = bjglDao;
	}

}
