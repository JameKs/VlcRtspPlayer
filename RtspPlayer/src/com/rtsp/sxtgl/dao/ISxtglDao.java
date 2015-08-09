/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.sxtgl.dao;

import java.util.List;

import com.rtsp.sxtgl.vo.Sxt;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface ISxtglDao {

	/**
	 * @param qjxx
	 */
	void insert(Sxt sxt);
	
	/**
	 * @param qjxx
	 */
	void delete(Sxt sxt);

	/**
	 * @param qjxx
	 */
	int update(Sxt sxt);

	/**
	 * @param qjxx
	 */
	abstract Sxt findById(String id);
	
	/**
	 * @param qjxx
	 */
	abstract List findList(Sxt sxt);
	
	/**
	 * @param qjxx
	 */
	abstract int findListCount(Sxt sxt);


}
