/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.task.Task;

/**
 * <pre>
 * 任务管理接口。
 * </pre>
 * 
 * @author zengziwen zengziwen@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface ITaskManageService {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "tsmp_workflow_taskManageService";

	/**
	 * 完成任务提交。
	 * 
	 * @param wfTaskId
	 *            String
	 * @param apprId
	 *            String
	 * @param variable
	 *            Map<String,Object>
	 * @throws Exception
	 *             异常
	 */
	public void completeBusTask(String wfTaskId, String apprId,
			Map<String, Object> variable) throws Exception;

	/**
	 * 检查流程是否已结束。
	 * 
	 * @param processDefinitionId
	 *            String
	 * @param processInstanceId
	 *            String
	 * @return boolean
	 * @throws Exception
	 *             异常
	 */
	public boolean isFinishedForProcess(String processDefinitionId,
			String processInstanceId) throws Exception;

	/**
	 * 权限id 获取流程下的节点的任务信息。
	 * 
	 * @param id
	 *            String
	 * @return Task
	 * @throws Exception
	 *             异常
	 */
	public Task findTaskById(String id) throws Exception;

	/**
	 * 获取当前活动的Task。
	 * 
	 * @param processDefinitionId
	 *            String
	 * @param processInstanceId
	 *            String
	 * @return Task
	 * @throws Exception
	 *             异常
	 */
	public Task getActTask(String processDefinitionId, String processInstanceId)
			throws Exception;

	/**
	 * 获取Task队列。
	 * 
	 * @param processDefinitionId
	 *            String
	 * @param processInstanceId
	 *            String
	 * @return List<Task>
	 * @throws Exception
	 *             异常
	 */
	public List<Task> getActTaskList(String processDefinitionId,
			String processInstanceId) throws Exception;

	/**
	 * 完成第一个审批节点。
	 * 
	 * @param wfTaskId
	 *            String
	 * @param apprId
	 *            String
	 * @throws Exception
	 *             异常
	 */
	public void completeStartNode(String wfTaskId, String apprId)
			throws Exception;

	/**
	 * 根据流程实例ID查找task
	 * 
	 * @param processInstanceId
	 *            流程实例ID
	 * @return 返回任务
	 * @throws Exception
	 */
	public Task getActTask(String processInstanceId) ;

	/**
	 * @param userId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	List getUserDbTask(String userId, int firstResult, int maxResults);

	/**
	 * @param userId
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	List getUserYbTask(String userId, int firstResult, int maxResults);

	/**
	 * @param taskId
	 * @param userId
	 */
	void claimTask(String taskId, String userId);

	/**
	 * @param taskId
	 * @param userId
	 */
	void delegateTask(String taskId, String userId);

	/**
	 * @param taskId
	 * @param variables
	 */
	void completeTask(String taskId, Map<String, Object> variables);

	/**
	 * @param userId
	 * @return
	 */
	long getUserDbTaskCount(String userId);

}
