///**
// * Copyright(c) Foresee Science & Technology Ltd. 
// */
//package com.icanft.common.wf.service.impl;
//
//import java.util.List;
//import java.util.Map;
//
//import org.activiti.engine.HistoryService;
//import org.activiti.engine.TaskService;
//import org.activiti.engine.history.HistoricProcessInstance;
//import org.activiti.engine.query.Query;
//import org.activiti.engine.task.Task;
//import org.apache.log4j.Logger;
//
//import com.icanft.common.wf.service.ITaskManageService;
//
///**
// * <pre>
// * Activiti 工作流程管理接口实现类。
// * <pre>
// * @author meihu2007@sina.com
// * 2015年6月3日
// */
//public class TaskManageServiceImpl implements ITaskManageService {
//
//	private static final Logger logger = Logger.getLogger(TaskManageServiceImpl.class);
//	
//	private TaskService taskService;
//
//	private HistoryService historyService;
//
//
//	@Override
//	public boolean isFinishedForProcess(String processDefinitionId,
//			String processInstanceId) {
//		Query<?, HistoricProcessInstance> queryHis = historyService
//				.createHistoricProcessInstanceQuery().finished()
//				.processDefinitionId(processDefinitionId)
//				.processInstanceId(processInstanceId);
//		List list = queryHis.list();
//		if (list == null || list.size() == 0) {
//			return false;
//		}
//		return true;
//	}
//
//	public void completeBusTask(String wfTaskId, String apprId,
//			Map<String, Object> variable) throws Exception {
//		taskService.claim(wfTaskId, apprId);
//		taskService.complete(wfTaskId, variable);
//	}
//
//	@Override
//	public Task findTaskById(String id) throws Exception {
//		return taskService.createTaskQuery().taskId(id).singleResult();
//	}
//
//	@Override
//	public Task getActTask(String processDefinitionId, String processInstanceId)
//			throws Exception {
//		List<Task> taskList = taskService.createTaskQuery()
//				.processInstanceId(processInstanceId)
//				.processDefinitionId(processDefinitionId).list();
//		if (taskList == null || taskList.size() == 0) {
//			return null;
//		}
//		return taskList.get(0);
//	}
//	
//	@Override
//	public List<Task> getActTaskList(String processDefinitionId,
//			String processInstanceId) throws Exception {
//		List<Task> taskList = taskService.createTaskQuery()
//				.processInstanceId(processInstanceId)
//				.processDefinitionId(processDefinitionId).list();
//		if (taskList == null || taskList.size() == 0) {
//			return null;
//		}
//		return taskList;
//	}
//
//	@Override
//	public void completeStartNode(String wfTaskId, String apprId)
//			throws Exception {
//		taskService.claim(wfTaskId, apprId);
//		taskService.complete(wfTaskId);
//	}
//	
//
//	
//	
//
//	
//	/**
//	 * 获得用户已办任务列表
//	 * 
//	 * @param taskService
//	 *            TaskService
//	 */
//	@Override
//	public List getUserYbTask(String userId, int firstResult, int maxResults) {
//		List<Task> task = taskService.createTaskQuery().taskCandidateOrAssigned(userId).listPage(firstResult, maxResults);
//		return task;
//	}
//	
//	/**
//	 * 签收任务
//	 */
//	@Override
//	public void claimTask(String taskId, String userId){
//		taskService.claim(taskId, userId);
//	}
//	
//	/**
//	 * 委托任务
//	 */
//	@Override
//	public void delegateTask(String taskId, String userId){
//		taskService.delegateTask(taskId, userId);
//	}
//	
//	
//	
//
//	/**
//	 * 设置 taskService。
//	 * 
//	 * @param taskService
//	 *            TaskService
//	 */
//	public void setTaskService(TaskService taskService) {
//		this.taskService = taskService;
//	}
//
//	/**
//	 * 设置 historyService。
//	 * 
//	 * @param historyService
//	 *            HistoryService
//	 */
//	public void setHistoryService(HistoryService historyService) {
//		this.historyService = historyService;
//	}
//
//
//}
