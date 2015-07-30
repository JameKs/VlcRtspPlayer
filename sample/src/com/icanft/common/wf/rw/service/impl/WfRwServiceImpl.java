/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.rw.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.query.Query;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.DelegationState;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.IdentityLinkType;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.icanft.common.wf.rw.dao.IWfRwDao;
import com.icanft.common.wf.rw.service.IWfRwService;
import com.icanft.common.wf.rw.vo.WfRw;
import com.icanft.common.wf.rw.vo.WfSpyj;



/**
 * <pre>
 * Activiti流程部署管理接口实现类。
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
public class WfRwServiceImpl implements IWfRwService {

	private static final Logger log = Logger.getLogger(WfRwServiceImpl.class);

	private RepositoryService repositoryService;

	private RuntimeService runtimeService;
	
	private TaskService taskService ;
	
	private HistoryService historyService;
	
	private IWfRwDao wfRwDao;
	
	/* (non-Javadoc)
	 * @see com.icanft.common.wf.yhrw.service.impl.IYhrwServices#startProcByKey(com.icanft.common.wf.yhrw.vo.WfYhrw, java.lang.String, java.util.Map)
	 */
	@Override
	public String startProcByKey(WfRw rw, String deployKey, Map<String,Object> variable) {
		
		//插入T_WF_YHRW表generate
		wfRwDao.insert(rw);
		
		String rwId = rw.getId();
		//启动流程
		ProcessInstance procIns = runtimeService.startProcessInstanceByKey(deployKey, rwId, variable); 
		
		//更新任务
		rw.setProcInsId(procIns.getId());
		rw.setXgr(rw.getCjr());
		wfRwDao.update(rw); //同一个事物再次更新时不生效，所以改成了insert
		
		return rwId;//返回任务ID
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.common.wf.yhrw.service.impl.IYhrwServices#completeTask(java.util.HashMap, java.util.Map)
	 */
	@Override
	public void completeTask(String taskId, String rwId, String procInsId,
			Map<String, Object> variables, 
			WfSpyj spyj ){
		Task t = taskService.createTaskQuery().taskId(taskId).singleResult();
		if(StringUtils.equals(t.getDelegationState().toString(), DelegationState.PENDING.toString())){//判断此任务事实不是委派任务
			taskService.resolveTask(taskId);
		} else {
			if(variables == null){
				taskService.complete(taskId);
			} else {
				taskService.complete(taskId, variables);
			}
		}
		
		//插入审批意见
		
		this.insertOrUpdateSpyj(spyj);
		
		//如果整个任务完成则更新t_wf_rw
		Task task = taskService.createTaskQuery().processInstanceId(procInsId).singleResult();
		if(task == null){
			WfRw rw = new WfRw();
			rw.setId(rwId);
			rw.setXgr(spyj.getCjr());
			rw.setZxZt("1");
			wfRwDao.update(rw);
		}
		
		
		
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.common.wf.yhrw.service.impl.IYhrwServices#getActTask(java.lang.String)
	 */
	@Override
	public WfRw getActTask(String rwId) {
		
		WfRw yhrw = wfRwDao.findById(rwId);
		
	    // 根据当前人的ID查询  
		Task task = taskService.createTaskQuery()
				.processInstanceId(yhrw.getProcInsId()).singleResult();
        
		yhrw.setTaskId(task.getId());
		yhrw.setName(task.getName());  //任务名称 
		yhrw.setProcDefId(task.getProcessDefinitionId());//流程定义ID
		yhrw.setOwner(task.getOwner());//拥有人
		yhrw.setAssignee(task.getAssignee());//签收人
		yhrw.setPriority(task.getPriority());//优先级
		yhrw.setCreateTime(task.getCreateTime());//创建时间
		yhrw.setDueDate(task.getDueDate());//到期时间
	  
		return yhrw;
	}
	
	
	/* (non-Javadoc)
	 * @see com.icanft.common.wf.yhrw.service.impl.IYhrwServices#getUserDbTask(java.lang.String, int, int)
	 */
	@Override
	public List getUserDbTaskByYwlx(HashMap<String,String> map, int firstResult, int maxResults) {

		List<WfRw> yhrws = wfRwDao.findUserDbTask(map);
//		List<WfRw> yhrws = wfRwDao.findList(rw);  
//		
//		for (WfRw yhrw : yhrws) {  
//			Task task = taskService.createTaskQuery().processInstanceId(yhrw.getProcInsId()).singleResult();
//	        
//	        yhrw.setTaskId(task.getId());
//	        yhrw.setName(task.getName());  //任务名称 
//	        yhrw.setProcDefId(task.getProcessDefinitionId());//流程定义ID
//	        yhrw.setOwner(task.getOwner());//拥有人
//	        yhrw.setAssignee(task.getAssignee());//签收人
//	        yhrw.setPriority(task.getPriority());//优先级
//	        yhrw.setCreateTime(task.getCreateTime());//创建时间
//	        yhrw.setDueDate(task.getDueDate());//到期时间
//	    	
//	        results.add(yhrw);  
//	    }  
		
		return yhrws;
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.common.wf.yhrw.service.impl.IYhrwServices#getUserDbTask(java.lang.String, int, int)
	 */
	@Override
	public List getUserDbTask(String userId, String ywlxId, int firstResult, int maxResults) {
		
		List<WfRw> results = new ArrayList<WfRw>();  
		
		List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(userId).listPage(firstResult, maxResults);
		
		for (Task task : tasks) {  
	        String processInstanceId = task.getProcessInstanceId();  
	        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).active().singleResult();  
	        
	        String businessKey = processInstance.getBusinessKey();  
	        if (businessKey == null) {  
	            continue;  
	        } 
	        
	        WfRw yhrw = wfRwDao.findById(businessKey);
	        
	        yhrw.setTaskId(task.getId());
	        yhrw.setName(task.getName());  //任务名称 
	        yhrw.setProcDefId(task.getProcessDefinitionId());//流程定义ID
	        yhrw.setOwner(task.getOwner());//拥有人
	        yhrw.setAssignee(task.getAssignee());//签收人
	        yhrw.setPriority(task.getPriority());//优先级
	        yhrw.setCreateTime(task.getCreateTime());//创建时间
	        yhrw.setDueDate(task.getDueDate());//到期时间
	    	
	        results.add(yhrw);  
	    }  
		
		return results;
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.common.wf.yhrw.service.impl.IYhrwServices#getUserDbTaskCount(java.lang.String)
	 */
	@Override
	public long getUserDbTaskCount(String userId) {
		long count = taskService.createTaskQuery().taskCandidateOrAssigned(userId).count();
		return count;
	}
	
	/* 
	 * 
	 */
	@Override
	public void insertOrUpdateSpyj(WfSpyj spyj) {
		if(StringUtils.isEmpty(spyj.getId()) ){
			wfRwDao.insertSpyj(spyj);
		}else{
			spyj.setXgr(spyj.getCjr());
			wfRwDao.updateSpyj(spyj);
		}
	}
	
	/**
	 * 
	 * @param qjxx
	 * @return
	 */
	@Override
	public List findSpyjByYhrwId(String yhrwId){
		List list = wfRwDao.findSpyjByYhrwId(yhrwId);
	   
		return list;
	}
	
	/**
	 * 签收或反签收任务
	 * @return
	 */
	@Override
	public void claimTask(String taskId, String userId ){
		if(userId != null){
			taskService.claim(taskId, userId);//签收任务
		} else {//当userId为null时就是反签收
			//如果一个任务有相关的候选人、候选组才可以反签收
			List<IdentityLink> links = taskService.getIdentityLinksForTask(taskId);
			for(IdentityLink link : links){
				if(StringUtils.equals(IdentityLinkType.CANDIDATE, link.getType())){
					taskService.claim(taskId, null);
					break;
				}
			}
		}
		
		
	}
	
	/**
	 * 反签收任务
	 * @return
	 */
	@Override
	public void unClaimTask(String taskId){
		taskService.unclaim(taskId);//签收任务
	}
	
	/**
	 * 转办任务
	 * @return
	 */
	@Override
	public void transferAssignee(String taskId, String userId){
		taskService.setAssignee(taskId, userId);
	}
	
	/**
	 * 委托任务
	 */
	@Override
	public void delegateTask(String taskId, String userId){
		taskService.delegateTask(taskId, userId);
	}
	
	/**
	 * 挂起流程实例。
	 */
	public void suspendProcessInstanceById(String procInsId) {
		runtimeService.suspendProcessInstanceById(procInsId);
	}
	
	/**
	 * 重启流程实例。
	 */
	public void activateProcessInstanceById(String procInsId) {
		runtimeService.activateProcessInstanceById(procInsId);
	}
	
	/**
	 * 挂起流程。
	 */
	public void suspendProcessDefinitionById(String procInsId) {
		repositoryService.suspendProcessDefinitionById(procInsId);
	}
	
	/**
	 * 重启流程。
	 */
	public void activateProcessDefinitionById(String procInsId) {
		repositoryService.activateProcessDefinitionById(procInsId);
	}
	
	/**
	 * 驳回任务
	 * @param taskId
	 */
	@Override
	public void rejectTask(String taskId) {
		Map<String, Object> variables;
		// 取得当前任务
		HistoricTaskInstance currTask = historyService
				.createHistoricTaskInstanceQuery().taskId(taskId)
				.singleResult();
		// 取得流程实例
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(currTask.getProcessInstanceId())
				.singleResult();

		variables = instance.getProcessVariables();
		// 取得流程定义
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(currTask.getProcessDefinitionId());

		// 取得上一步活动
		ActivityImpl currActivity = ((ProcessDefinitionImpl) processDefinitionEntity)
				.findActivity(currTask.getTaskDefinitionKey());
		List<PvmTransition> nextTransitionList = currActivity
				.getIncomingTransitions();
		// 清除当前活动的出口
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		List<PvmTransition> pvmTransitionList = currActivity
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		// 建立新出口
		List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
		for (PvmTransition nextTransition : nextTransitionList) {
			PvmActivity nextActivity = nextTransition.getSource();
			ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) processDefinitionEntity)
					.findActivity(nextActivity.getId());
			TransitionImpl newTransition = currActivity
					.createOutgoingTransition();
			newTransition.setDestination(nextActivityImpl);
			newTransitions.add(newTransition);
		}
		// 完成任务
		List<Task> tasks = taskService.createTaskQuery()
				.processInstanceId(instance.getId())
				.taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
		for (Task task : tasks) {
			taskService.complete(task.getId(), variables);
			historyService.deleteHistoricTaskInstance(task.getId());
		}
		// 恢复方向
		for (TransitionImpl transitionImpl : newTransitions) {
			currActivity.getOutgoingTransitions().remove(transitionImpl);
		}
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
	
	/**
	 * 当下一个处理人还没有处理时可以撤回任务
	 * @param taskId
	 */
	@Override
	public void withdrawTask(String taskId) {
		Map<String, Object> variables;
		// 取得当前任务
		HistoricTaskInstance currTask = historyService
				.createHistoricTaskInstanceQuery().taskId(taskId)
				.singleResult();
		// 取得流程实例
		ProcessInstance instance = runtimeService.createProcessInstanceQuery()
				.processInstanceId(currTask.getProcessInstanceId())
				.singleResult();

		variables = instance.getProcessVariables();
		// 取得流程定义
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(currTask.getProcessDefinitionId());

		// 取得上一步活动
		ActivityImpl currActivity = ((ProcessDefinitionImpl) processDefinitionEntity)
				.findActivity(currTask.getTaskDefinitionKey());
		List<PvmTransition> nextTransitionList = currActivity
				.getIncomingTransitions();
		// 清除当前活动的出口
		List<PvmTransition> oriPvmTransitionList = new ArrayList<PvmTransition>();
		List<PvmTransition> pvmTransitionList = currActivity
				.getOutgoingTransitions();
		for (PvmTransition pvmTransition : pvmTransitionList) {
			oriPvmTransitionList.add(pvmTransition);
		}
		pvmTransitionList.clear();

		// 建立新出口
		List<TransitionImpl> newTransitions = new ArrayList<TransitionImpl>();
		for (PvmTransition nextTransition : nextTransitionList) {
			PvmActivity nextActivity = nextTransition.getSource();
			ActivityImpl nextActivityImpl = ((ProcessDefinitionImpl) processDefinitionEntity)
					.findActivity(nextActivity.getId());
			TransitionImpl newTransition = currActivity
					.createOutgoingTransition();
			newTransition.setDestination(nextActivityImpl);
			newTransitions.add(newTransition);
		}
		// 完成任务
		List<Task> tasks = taskService.createTaskQuery()
				.processInstanceId(instance.getId())
				.taskDefinitionKey(currTask.getTaskDefinitionKey()).list();
		for (Task task : tasks) {
			taskService.complete(task.getId(), variables);
			historyService.deleteHistoricTaskInstance(task.getId());
		}
		// 恢复方向
		for (TransitionImpl transitionImpl : newTransitions) {
			currActivity.getOutgoingTransitions().remove(transitionImpl);
		}
		for (PvmTransition pvmTransition : oriPvmTransitionList) {
			pvmTransitionList.add(pvmTransition);
		}
	}
//以上是跟task相关的
	
//以下是跟ProcessDefinition相关的
	/**
	 * 已部署流程列表
	 */
	@Override
	public List querCommonList(int pageSize, int currentPage) {
		Query<ProcessDefinitionQuery, ProcessDefinition> query = repositoryService.createProcessDefinitionQuery();
		ProcessDefinitionQuery pq = this.repositoryService.createProcessDefinitionQuery();
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
		if (currentPage <= 0 && currentPage != -100) {
			currentPage = 1;
		}
		if (currentPage > 0) {
			first = (currentPage - 1) * pageSize;
		}
		List<ProcessDefinition> processDefList = query.listPage(first, pageSize);
		Collections.sort(processDefList, new Comparator<ProcessDefinition>() {
			@Override
			public int compare(ProcessDefinition o1, ProcessDefinition o2) {
				int i1 = o1.getVersion();
				int i2 = o2.getVersion();
				return i1 - i2;
			}
		});
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (ProcessDefinition pd : processDefList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", pd.getId());
			m.put("key", pd.getKey());
			m.put("name", pd.getName());
			m.put("version", pd.getVersion());
			m.put("deploymentId", pd.getDeploymentId());
			list.add(m);
		}
		return list;
	}

	/**
	 * 已部署流程列表
	 */
	@Override
	public void deployProcess(ModelMap map) throws Exception {
//		String msg = "";

		String fileName = (String) map.get("fileName");
		if (!fileName.endsWith(".bar")) {
			throw  new Exception("流程部署失败");
		}
		MultipartFile multipartFile = (MultipartFile) map.get("file");
		InputStream fileStream = multipartFile.getInputStream();
		ZipInputStream inputStream = new ZipInputStream(fileStream);
		repositoryService.createDeployment().name(fileName).addZipInputStream(inputStream).deploy();
	}
	
	/**
	 * 根据流程定义ID起一个流程。
	 */
	public ProcessInstance startProcessById(String deployId,Map<String,Object> variable) throws  Exception {
		if(variable != null){
			return runtimeService.startProcessInstanceById(deployId, variable); 
		}else{
			return runtimeService.startProcessInstanceById(deployId);
		}
	}

	/**
	 * 卸载流程。
	 */
	@Override
	public void deleteDeploymentById(String deployId) {
		log.info("准备卸载Deployment- id:" + deployId);
		String msg = null;
		try {
			repositoryService.deleteDeployment(deployId, true);
			log.info("卸载Deployment- id:" + deployId + "成功 ");
			msg = "卸载成功！";
		} catch (Exception e) {
			log.error("卸载失败！", e);
		}
	}



	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}
	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setWfRwDao(IWfRwDao wfRwDao) {
		this.wfRwDao = wfRwDao;
	}
	
	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}
	
	
}
