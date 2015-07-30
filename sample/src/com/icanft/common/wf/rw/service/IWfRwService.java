/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.rw.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.icanft.common.wf.rw.vo.WfRw;
import com.icanft.common.wf.rw.vo.WfSpyj;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年6月3日
 */
public interface IWfRwService {

	/**
	 * 根据流程Key启动一个最新版本的流程实例。
	 */
	public abstract String startProcByKey(WfRw rw, String deployKey,
			Map<String, Object> variable);

	/**
	 * 完成任务
	 */
	public abstract void completeTask(String taskId, String rwId, String procInsId,
			Map<String, Object> variables, 
			WfSpyj spyj );

	/**
	 * 根据流程实例查询流程
	 */
	public abstract WfRw getActTask(String rwId);

	/**
	 * 获得用户代办任务列表
	 * 
	 * @param taskService
	 *            TaskService
	 */
	public abstract List getUserDbTask(String userId, String ywlxId, int firstResult,
			int maxResults);

	/**
	 * 获得用户代办任务记录数
	 * 
	 * @param taskService
	 *            TaskService
	 */
	public abstract long getUserDbTaskCount(String userId);

	public abstract void insertOrUpdateSpyj(WfSpyj spyj);


	/**
	 * 签收任务
	 */
	public abstract void  claimTask(String taskId, String userId);
	
	/**
	 * 转办任务
	 */
	public abstract void transferAssignee(String taskId, String userId);

	/**
	 * @param userId
	 * @param ywlxId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 */
	List getUserDbTaskByYwlx(HashMap<String,String> map, int firstResult,
			int maxResults);

	/**
	 * @param yhrwId
	 * @return
	 */
	List findSpyjByYhrwId(String yhrwId);

	/**
	 * @param taskId
	 * @param userId
	 */
	void delegateTask(String taskId, String userId);

	/**
	 * @param map
	 */
	void deployProcess(ModelMap map)  throws Exception ;

	/**
	 * @param deployId
	 */
	void deleteDeploymentById(String deployId);
	
	List querCommonList(int pageSize, int currentPage);

	/**
	 * @param taskId
	 */
	void withdrawTask(String taskId);

	/**
	 * @param taskId
	 */
	void rejectTask(String taskId);

	/**
	 * @param taskId
	 */
	void unClaimTask(String taskId);

}