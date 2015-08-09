/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.yhbjgxsz.dao;

import java.util.HashMap;
import java.util.List;

import com.rtsp.yhbjgxsz.vo.YhBjVo;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface IYhbjszDao {

	/**
	 * @param qjxx
	 */
	void insert(YhBjVo yhBjVo);

	/**
	 * @param qjxx
	 */
	int delete(YhBjVo yhBjVo);

	/**
	 * @param qjxx
	 */
	abstract YhBjVo findById(String id);
	
	/**
	 * @param qjxx
	 */
	abstract List findList(YhBjVo yhBjVo);
	
	/**
	 * @param qjxx
	 */
	abstract int findListCount(YhBjVo yhBjVo);
	
	
	/**
	 * 查询班级列表
	 * @param qjxx
	 */
	abstract List findBjList(HashMap map);


}
