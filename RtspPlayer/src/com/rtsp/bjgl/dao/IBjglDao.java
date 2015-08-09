/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.bjgl.dao;

import java.util.List;

import com.rtsp.bjgl.vo.Bj;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface IBjglDao {

	/**
	 * @param qjxx
	 */
	void insert(Bj bj);
	
	/**
	 * @param qjxx
	 */
	void delete(Bj bj);

	/**
	 * @param qjxx
	 */
	int update(Bj bj);

	/**
	 * @param qjxx
	 */
	abstract Bj findById(String id);
	
	/**
	 * @param qjxx
	 */
	abstract List findList(Bj bj);
	
	/**
	 * @param qjxx
	 */
	abstract int findListCount(Bj bj);


}
