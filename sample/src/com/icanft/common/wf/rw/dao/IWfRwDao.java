/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.rw.dao;

import java.util.HashMap;
import java.util.List;

import com.icanft.common.wf.rw.vo.WfRw;
import com.icanft.common.wf.rw.vo.WfSpyj;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月27日
 */
public interface IWfRwDao {

	void insert(WfRw rw);

	void update(WfRw rw);

	void updateById(HashMap map);

	abstract WfRw findById(String id);

	abstract List findList(WfRw rw);

	abstract long findListCount(WfRw rw);

	public void insertSpyj(WfSpyj spyj);
	
	public void updateSpyj(WfSpyj spyj);

	/**
	 * @param yhrwId
	 * @return
	 */
	abstract List findSpyjByYhrwId(String yhrwId);
	
	abstract List findUserDbTask(HashMap<String,String> map);

}
