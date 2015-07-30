/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.qjgl.dao;

import java.util.List;

import com.icanft.qjgl.vo.Qjxx;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface IQjglDao {

	/**
	 * @param qjxx
	 */
	void insert(Qjxx qjxx);

	/**
	 * @param qjxx
	 */
	int update(Qjxx qjxx);

	/**
	 * @param qjxx
	 */
	abstract Qjxx findById(String id);
	
	/**
	 * @param qjxx
	 */
	abstract List findList(Qjxx qjxx);
	
	/**
	 * @param qjxx
	 */
	abstract long findListCount(Qjxx qjxx);


}
