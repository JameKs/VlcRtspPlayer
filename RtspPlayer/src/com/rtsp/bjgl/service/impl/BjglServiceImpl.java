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
public class BjglServiceImpl implements IBjglService {
	
	private static final Logger log = Logger.getLogger(BjglServiceImpl.class);
	
	private IBjglDao bjglDao;
	
	@Override
	public void insert(Bj bj){
		bjglDao.insert(bj);
	}
	
	@Override
	public void delete(Bj bj){
		bjglDao.delete(bj);
	}

	@Override
	public void update(Bj bj){
		bjglDao.update(bj);
	}
	
	@Override
	public List findList(Bj bj){
		List qj = bjglDao.findList(bj);
		return qj;
	}

	@Override
	public int findListCount(Bj bj){
		int count = bjglDao.findListCount(bj);
		return count;
	}
	
	@Override
	public Bj findById(String id) {
		Bj bj = bjglDao.findById(id);
		return bj;
	}
	
	public void setBjglDao(IBjglDao bjglDao) {
		this.bjglDao = bjglDao;
	}

}
