/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.rtsp.bjgl.service;

import java.util.List;

import com.rtsp.bjgl.vo.Bj;
import com.rtsp.sxtgl.vo.Sxt;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface IBjglService {

public abstract void insert(Bj bj);
	
	public abstract void delete(Bj bj);

	public abstract void update(Bj bj);

	public abstract List findList(Bj bj);
	
	public abstract int findListCount(Bj bj);
	
	public abstract Bj findById(String id);

}