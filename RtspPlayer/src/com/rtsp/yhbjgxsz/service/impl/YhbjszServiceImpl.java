/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.yhbjgxsz.service.impl;

import java.util.List;

import org.apache.log4j.Logger;

import com.mqm.frame.common.DefaultServiceImpl;
import com.rtsp.yhbjgxsz.dao.IYhbjszDao;
import com.rtsp.yhbjgxsz.service.IYhbjszService;
import com.rtsp.yhbjgxsz.vo.YhBjVO;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public class YhbjszServiceImpl implements IYhbjszService<YhBjVO> {
	
	private static final Logger log = Logger.getLogger(YhbjszServiceImpl.class);
	
	private IYhbjszDao yhbjszDao;

	
	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#insert(java.lang.String, java.lang.Object)
	 */
	@Override
	public void insert(YhBjVO yhBjVO) {
		yhbjszDao.insert("insert", yhBjVO);
	}

	@Override
	public void deleteById(String id) {
		yhbjszDao.deleteById("delete", id);
	}

	@Override
	public void update(YhBjVO yhBjVO) {
		yhbjszDao.update("update", yhBjVO);
	}

	@Override
	public YhBjVO findById(String id) {
		return (YhBjVO)yhbjszDao.findById("findById", id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.common.IDao#findList(java.lang.String, java.lang.Object)
	 */
	@Override
	public List findList(YhBjVO yhBjVO) {
		return yhbjszDao.findList("findList", yhBjVO);
	}

	@Override
	public List findPageList(YhBjVO yhBjVO, int pageIndex, int pageSize) {
		return yhbjszDao.findPageList("findList", yhBjVO , pageIndex , pageSize);
	}

	@Override
	public int findListCount(YhBjVO yhBjVO) {
		return yhbjszDao.findListCount("findList", yhBjVO);
	}
	
	@Override
	public List findAll() {
		return yhbjszDao.findAll("findAll");
	}
	
	/**
	 * @param yhbjszDao the yhbjszDao to set
	 */
	public void setYhbjszDao(IYhbjszDao yhbjszDao) {
		this.yhbjszDao = yhbjszDao;
	}
	
	
	
	
	
}
