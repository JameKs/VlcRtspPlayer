/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.qjgl.service;

import java.util.List;

import com.icanft.common.wf.rw.vo.WfSpyj;
import com.icanft.qjgl.vo.Qjxx;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface IQjglService {

	public abstract void insert(Qjxx qjxx);

	public abstract void update(Qjxx qjxx);

	public abstract List findList(Qjxx qjxx);
	
	public abstract long findListCount(Qjxx qjxx);
	
	public void insertOrUpdateSpyj(WfSpyj spyj);

	/**
	 * @param qjxx
	 * @return
	 */
	long getUserDbTaskCount(Qjxx qjxx);

	/**
	 * @param qjxx
	 * @return
	 */
	List getUserDbTask(Qjxx qjxx);

	/**
	 * @param qjxx
	 * @return
	 */
	Qjxx findById(String id);
	
	/**
	 * @param qjxx
	 * @return
	 */
	List findSpyjByYhrwId(String id);

	/**
	 * @param qjxx
	 */
	void updateYwAndRw(Qjxx qjxx, String spyj);

	/**
	 * @param qjxx
	 */
	void insertYwAndRw(Qjxx qjxx);

}