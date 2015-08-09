/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.sxtgl.service;

import java.util.List;

import com.rtsp.sxtgl.vo.Sxt;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface ISxtglService {

	public abstract void insert(Sxt sxt);
	
	public abstract void delete(Sxt sxt);

	public abstract void update(Sxt sxt);

	public abstract List findList(Sxt sxt);
	
	public abstract int findListCount(Sxt sxt);
	
	public abstract Sxt findById(String id);
	
}