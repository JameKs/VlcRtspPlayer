/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.task.TaskDefinition;
import org.activiti.engine.query.Query;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;

import com.icanft.common.constant.BaseConstants;
import com.icanft.common.util.DateTimeUtil;
import com.icanft.common.wf.service.IProcessManageService;
import com.icanft.jsgl.service.IRoleService;
import com.icanft.jsgl.vo.Role;
import com.icanft.xtgl.yhgl.service.IUserService;
import com.icanft.xtgl.yhgl.vo.User;

/**
 * <pre>
 * Activiti 流程管理接口实现类。
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月29日
 */
public class ProcessManageServiceImpl implements IProcessManageService {

	private static final Log log = LogFactory
			.getLog(ProcessManageServiceImpl.class);

	private RepositoryService repositoryService;

	private RuntimeService runtimeService;

	private HistoryService historyService;

	private TaskService taskService;

	private IUserService userService;

	private IRoleService roleService;

	/**
	 * 查询历史流程列表。
	 * 
	 * @param pageSize int
	 * @param currentPage int
	 * @param map ModelMap
	 * @return List<Map<String, Object>>
	 */
	private List<Map<String, Object>> queryHistoryList(int pageSize,
			int currentPage, ModelMap map) {

		Query<?, HistoricProcessInstance> query = historyService
				.createHistoricProcessInstanceQuery().finished();
		List<HistoricProcessInstance> list = query.list();
		Collections.sort(list, new Comparator<HistoricProcessInstance>() {
			@Override
			public int compare(HistoricProcessInstance o1,
					HistoricProcessInstance o2) {
				String id1 = o1.getId();
				String id2 = o2.getId();
				int i1 = Integer.parseInt(id1);
				int i2 = Integer.parseInt(id2);
				return i1 - i2;
			}
		});
		// 总记录数
		int totalSize = list.size();
		// 总页面数
		int totalPage = totalSize / pageSize;
		if (totalPage % pageSize != 0) {
			totalPage++;
		}
		if (totalPage == 0 && totalSize > 0) {
			totalPage++;
		}
		// 第一条记录
		int first = 0;
		if (currentPage <= 0 && currentPage != -100) {
			currentPage = 1;
		}
		if (currentPage > 0) {
			first = (currentPage - 1) * pageSize;
		}
		if (first < totalSize) {
			int value = totalSize - first;
			if (value >= 10) {
				list = list.subList(first, first + 10);
			} else {
				list = list.subList(first, totalSize);
			}
		} else {
			list.clear();
		}
		List<Map<String, Object>> l = new ArrayList<Map<String, Object>>();
		for (HistoricProcessInstance hpi : list) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", hpi.getId());
			m.put("deleteReason", hpi.getDeleteReason());
			m.put("track", "流程图");
			m.put("log", "审批记录");
			m.put("processDefinitionId", hpi.getProcessDefinitionId());
			m.put("processInstanceId", hpi.getSuperProcessInstanceId());
			l.add(m);
		}
		map.put("pr2", l);
		map.put("historyDataList", l);
		return l;
	}

	@Override
	public void queryProcessList(ModelMap map) {
		queryCommonList(10, 1, map, null);
		queryHistoryList(10, 1, map);
	}

	@Override
	public void deleteProcessByProcessInstId(String instId) {
		runtimeService.deleteProcessInstance(instId, "delete");
	}

	@Override
	public void deleteProcessById(String id, ModelMap map) {
		log.info("准备删除流程- id:" + id);
		String msg = null;
		runtimeService.deleteProcessInstance(id, "delete");
		log.info("删除流程成功!- id:" + id);
		msg = "删除成功！";
		queryCommonList(10, 1, map, msg);
		queryHistoryList(10, 1, map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getProcessMonitor(String processDefId,
			String processInstanceId, String executionId, ModelMap map) {
		String msg = null;
		try {
			if (StringUtils.isEmpty(processDefId)) {
				return;
			}
			ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
					.getDeployedProcessDefinition(processDefId);
			List<Execution> list = runtimeService.createExecutionQuery()
					.processDefinitionId(processDefId)
					.processInstanceId(processInstanceId).list();

			List<Task> actTaskList = taskService.createTaskQuery()
					.processInstanceId(processInstanceId).list();// .executionId(executionId).list();
			List<HistoricTaskInstance> hisTaskList = historyService
					.createHistoricTaskInstanceQuery()
					.processInstanceId(processInstanceId).list();

			// 任务下的所有节点
			List<ActivityImpl> actNodeList = def.getActivities();
			//ActivityImpl actImpl = null;
			StringBuffer actNodeBorder = new StringBuffer();
			StringBuffer actNodeArea = new StringBuffer();
			// 保存活动节点的坐标信息
			Map actNodeAreaMap = new HashMap();

			// 处理历史任务信息
			for (HistoricTaskInstance hisTask : hisTaskList) {
				if ("删除".equals(hisTask.getDeleteReason())
						|| "completed".equals(hisTask.getDeleteReason())){
					for (ActivityImpl hisActImpl : actNodeList) {
						
						if (hisActImpl.getId().equals(hisTask.getTaskDefinitionKey())) {
	
							String assigneeUserId = hisTask.getAssignee() == null ? ""
									: hisTask.getAssignee();
							User user = null;
							if (!StringUtils.isEmpty(assigneeUserId)) {
								user = userService.findById(assigneeUserId);
							}
							String taskInfo = hisTask.getName()
									+ "#@#处理人编号:"
									+ assigneeUserId
									+ "#@#处理人名称:"
									+ (user == null ? assigneeUserId : user
											.getUserName())
									+ "#@#接收时间:"
									+ DateTimeUtil.formatDate(
											hisTask.getStartTime(),
											BaseConstants.DATETIME_FORMAT_STR)
									+ "#@#完成时间:"
									+ DateTimeUtil.formatDate(hisTask.getEndTime(),
											BaseConstants.DATETIME_FORMAT_STR)
									+ "#$#";
	
							actNodeBorder
									.append("<div style='position:absolute;z-index:99;border:2px solid blue;left:")
									.append(hisActImpl.getX())
									.append("px;top:")
									.append(hisActImpl.getY())
									.append("px;width:")
									.append(hisActImpl.getWidth()-4)//因border宽度为2，上下共为4，减掉4大小刚好
									.append("px;height:")
									.append(hisActImpl.getHeight()-4).append("px;'></div>");//因border宽度为2，上下共为4，减掉4大小刚好
	
							// 以坐标作为保存节点信息的key值
							keepNodeArea(taskInfo, hisActImpl, actNodeAreaMap);
							break;
						}
					}
				}
			}

			// 处理当前活动的任务
			int cImage = 0;
			for (Execution e : list) {
				ExecutionEntity E = (ExecutionEntity) e;
				if (E.isActive() && E.getProcessInstanceId().equals(processInstanceId)) {
					
					for (ActivityImpl activityImpl : actNodeList) {
						if (E.getActivityId().equals(activityImpl.getId())) {
							//actImpl = activityImpl;
							ProcessDefinitionEntity p = (ProcessDefinitionEntity) activityImpl.getProcessDefinition();
							TaskDefinition tef = p.getTaskDefinitions().get(activityImpl.getId());
							
							//String key = tef.getKey();
							String taskInfo = "";
							for (Task t : actTaskList) {
								
								if (tef.getKey().equals(t.getTaskDefinitionKey())) {
									
									String assigneeIds = t.getAssignee();
									String assIgneeNames = "";
									// 针对角色
									if (StringUtils.isEmpty(assigneeIds)
											&& tef.getCandidateGroupIdExpressions()
													.size() != 0) {
										Iterator iterator = tef
												.getCandidateGroupIdExpressions()
												.iterator();
										StringBuffer rolesName = new StringBuffer();
										while (iterator.hasNext()) {
											Object r = iterator.next();
											if (r != null) {
												Role role = roleService.findById(r.toString());
												if(role!=null){
													rolesName.append(role.getName()).append(",");
												}
											}
										}
										if (rolesName.toString().length() > 0) {
											rolesName.delete(
													rolesName.length() - 1,
													rolesName.length());
										}
										assigneeIds = "("
												+ rolesName.toString() + ")";
									} else if (t.getAssignee() == null
											&& tef.getCandidateUserIdExpressions()
													.size() != 0) {// 针对多个用户
										List<IdentityLink> links = taskService.getIdentityLinksForTask(t.getId());
										assigneeIds = "";
										User user= new User();
										for(IdentityLink d : links){
											user = userService.findById(d.getUserId());
											assigneeIds += user.getId()+","; //签收人id是系统自动生成的ID，故要换成用户的id
											assIgneeNames += (user==null)?"      ":user.getUserName()+",";
										 }
										if (assigneeIds.length() > 0) {
											assigneeIds = assigneeIds
													.substring(0, assigneeIds
															.length() - 1);
										}
										if (assIgneeNames.length() > 0) {
											assIgneeNames = assIgneeNames
													.substring(0, assIgneeNames
															.length() - 1);
										}

									}

									if (StringUtils.isNotEmpty(assigneeIds)) {
										taskInfo += t.getName()
												+ "#@#处理人编号:"
												+ assigneeIds
												+ "#@#处理人名称:"
												+ assIgneeNames
												+ "#@#接收时间:"
												+ DateTimeUtil
														.formatDate(
																t.getCreateTime(),
																BaseConstants.DATETIME_FORMAT_STR)
												+ "#@#完成时间:<font color=red>未完成</font>#$#";
									}
								}
							}

							actNodeBorder
									.append("<div id='myImage"
											+ cImage
											+ "' style='position:absolute;border:2px solid red;left:")
									.append(activityImpl.getX())
									.append("px;top:")
									.append(activityImpl.getY())
									.append("px;width:")
									.append(activityImpl.getWidth()-4)
									.append("px;height:")
									.append(activityImpl.getHeight()-4).append("px;z-index:99;'></div>");

							// 以坐标作为保存节点信息的key值
							keepNodeArea(taskInfo, activityImpl, actNodeAreaMap);
							cImage++;
						}
					}
				}
			}

			// 组装每个节点的显示信息
			buildNodeArea(actNodeArea, actNodeAreaMap);

			map.put("actBorder", actNodeBorder.append(actNodeArea).toString());
			map.put("processDefId", processDefId);
			map.put("executionId", executionId);
		} catch (Exception e) {
			msg = "获取流程图失败！";
			log.error(msg + "[processDefId:" + processDefId + "]", e);
		}
	}

	/**
	 * 保存节点信息。
	 * 
	 * @param taskInfo
	 *            String
	 * @param act
	 *            ActivityImpl
	 * @param actNodeAreaMap
	 *            Map
	 */
	private void keepNodeArea(String taskInfo, ActivityImpl act,
			Map<String, Object> actNodeAreaMap) {
		String pos = act.getX() + "_" + act.getY() + "_" + act.getWidth() + "_"
				+ act.getHeight();
		Object areaInfo = actNodeAreaMap.get(pos);
		if (areaInfo == null) {
			actNodeAreaMap.put(pos, taskInfo);
		} else {
			if (areaInfo.toString().indexOf(taskInfo) == -1) {// 如果不是重复值
				actNodeAreaMap.put(pos, areaInfo + "#$#" + taskInfo);
			}
		}
	}

	/**
	 * 构建节点页面展示信息。
	 * 
	 * @param actNodeArea
	 *            StringBuffer
	 * @param actNodeAreaMap
	 *            Map
	 */
	@SuppressWarnings("rawtypes")
	private void buildNodeArea(StringBuffer actNodeArea, Map actNodeAreaMap) {
		if (actNodeAreaMap != null && actNodeAreaMap.size() > 0) {
			Iterator it = actNodeAreaMap.keySet().iterator();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) actNodeAreaMap.get(key);
				String[] xywh = key.split("_");
				actNodeArea
						.append("<div style='position:absolute;border:0px solid red;left:")
						.append(xywh[0])
						.append("px;top:")
						.append(xywh[1])
						.append("px;width:")
						.append(xywh[2])
						.append("px;height:")
						.append(xywh[3])
						.append("px;")
						.append(" filter:alpha(Opacity=1);-moz-opacity:0.5;opacity: 0.5;z-index:100; background-color:#ffffff;")
						.append("'  onMousemove='p_move(this)' onMouseout='p_out()'  info='")
						.append(value).append("' ></div>");

			}
		}
	}

	@Override
	public void getProcessHisPic(String processDefId, String executionId,
			ModelMap map) {
		String msg = null;
		try {
			map.put("processDefId", processDefId);
			map.put("executionId", executionId);
		} catch (Exception e) {
			msg = "获取流程图失败！";
			log.error(msg + "[processDefId:" + processDefId + "]", e);
		}
		queryCommonList(10, 1, map, null);
	}

	@Override
	public InputStream getProcessPic(String processDefId) {
		InputStream imageData = null;
		try {
			ProcessDefinition processDef = null;
			if (processDefId != null && processDefId.indexOf(":") != -1) {
				processDef = repositoryService.createProcessDefinitionQuery()
						.processDefinitionId(processDefId).singleResult();
			} else {
				processDef = repositoryService.createProcessDefinitionQuery()
						.processDefinitionKey(processDefId).latestVersion()
						.singleResult();
			}
			String diagramResourceName = processDef.getDiagramResourceName();
			imageData = repositoryService.getResourceAsStream(
					processDef.getDeploymentId(), diagramResourceName);
		} catch (Exception e) {
			log.error("[processDefId:" + processDefId + "]", e);
		}
		return imageData;
	}

	@Override
	public List<Map<String, Object>> queryPage(int pageSize, int currentPage,
			ModelMap map) {
		return queryCommonList(pageSize, currentPage, map, null);
	}

	@Override
	public List<Map<String, Object>> queryPageForHis(int pageSize,
			int currentPage, ModelMap map) {
		return queryHistoryList(pageSize, currentPage, map);
	}

	/**
	 * 流程信息通用查询方法。
	 * 
	 * @param pageSize
	 *            int
	 * @param currentPage
	 *            int
	 * @param map
	 *            ModelMap
	 * @param returnMsg
	 *            String
	 * @return List
	 */
	private List<Map<String, Object>> queryCommonList(int pageSize,
			int currentPage, ModelMap map, String returnMsg) {
		Query<?, ProcessInstance> query = runtimeService
				.createProcessInstanceQuery();
		// 总记录数
		long totalSize = query.count();
		// 总页面数
		long totalPage = totalSize / pageSize;
		if (totalPage % pageSize != 0) {
			totalPage++;
		}
		if (totalPage == 0 && totalSize > 0) {
			totalPage++;
		}
		// 第一条记录
		int first = 0;
		if (currentPage > 0) {
			first = (currentPage - 1) * pageSize;
		}
		// 最后一页的
		if (currentPage == -100) {
			currentPage = (int) (totalPage);
			first = (int) ((currentPage - 1) * pageSize);
		}

		List<ProcessInstance> processList = query.listPage(first, pageSize);
		Collections.sort(processList, new Comparator<ProcessInstance>() {
			@Override
			public int compare(ProcessInstance o1, ProcessInstance o2) {
				String id1 = o1.getId();
				String id2 = o2.getId();
				int i1 = Integer.parseInt(id1);
				int i2 = Integer.parseInt(id2);
				return i1 - i2;
			}
		});
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (ProcessInstance pi : processList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", pi.getId());
			m.put("status", "活动");
			m.put("track", "流程图");
			m.put("log", "审批记录");
			m.put("processDefinitionId", pi.getProcessDefinitionId());
			m.put("processInstanceId", pi.getProcessInstanceId());
			list.add(m);
		}
		map.put("pr1", list);
		// 查询历史流程列表
		// queryHistoryList(pageSize,currentPage,map);
		if (returnMsg == null) {
			returnMsg = "";
		}
		map.put("msg", returnMsg);
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.foresee.bi.tsmp.workflow.service.IProcessManageService#isComplete
	 * (org.activiti.engine.impl.pvm.delegate.ActivityExecution)
	 */
	@Override
	public boolean isComplete(ActivityExecution execution) {
		Object runTaskIdObj = execution.getVariable("_run_task_id_");
		String runTaskId = runTaskIdObj == null ? "" : runTaskIdObj.toString();
		String procInstId = execution.getProcessInstanceId();
		String actNodeId = execution.getActivity().getId();
		List<Task> list = taskService.createTaskQuery()
				.processInstanceId(procInstId).list();
		if (list != null) {
			for (Task task : list) {
				if (!runTaskId.equals(task.getId())
						&& task.getTaskDefinitionKey().equals(actNodeId)) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public List<Map<String, String>> getProcessNodeList(String processId) {
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processId);
		List<ActivityImpl> actList = def.getActivities();
		for (ActivityImpl actImp : actList) {
			if (StringUtils.equals((String) actImp.getProperties().get("type"),
					"userTask")) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("id", actImp.getId());
				map.put("name", (String) actImp.getProperties().get("name"));
				result.add(map);
			}
		}
		return result;
	}

	/**
	 * 设置 repositoryService。
	 * 
	 * @param repositoryService
	 *            RepositoryService
	 */
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	/**
	 * 设置 runtimeService。
	 * 
	 * @param runtimeService
	 *            RuntimeService。
	 */
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	/**
	 * 设置 historyService。
	 * 
	 * @param historyService
	 *            HistoryService
	 */
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	/**
	 * 设置 taskService。
	 * 
	 * @param taskService
	 *            TaskService
	 */
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	/**
	 * @param userService
	 *            the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @param roleService
	 *            the roleService to set
	 */
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}

}
