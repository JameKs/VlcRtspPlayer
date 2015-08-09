/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.yhbjgxsz.service;

import java.util.List;

import com.rtsp.yhbjgxsz.vo.YhBjVo;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface IYhbjszService {

	public abstract void insert(YhBjVo yhBjVo);

	public abstract void delete(YhBjVo yhBjVo);

	public abstract List findList(YhBjVo yhBjVo);
	
	public abstract int findListCount(YhBjVo yhBjVo);
	
}