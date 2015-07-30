/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.delegate.ActivityExecution;
import org.springframework.ui.ModelMap;

/**
 * <pre>
 * 流程管理接口。
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
public interface IProcessManageService {

	/**
	 * 查询流程列表。
	 * 
	 * @param map ModelMap
	 */
	public void queryProcessList(ModelMap map);

	/**
	 * 根据流程ID删除流程。
	 * @param id String
	 * @param map ModelMap
	 */
	public void deleteProcessById(String id, ModelMap map);
	
	/**
	 * 根据流程实列ID删除流程。
	 * @param instId String
	 */
	public void deleteProcessByProcessInstId(String instId);

	/**
	 * 查询流程节点的活动状态。
	 * @param processDefId String
	 * @param processInstanceId String
	 * @param executionId String
	 * @param map ModelMap
	 */
	public void getProcessMonitor(String processDefId, String processInstanceId, String executionId,ModelMap map);

	/**
	 * 构建历史流程的流程图。
	 * @param processDefId String
	 * @param executionId String
	 * @param map ModelMap
	 */
	public void getProcessHisPic(String processDefId, String executionId, ModelMap map);

	/**
	 * 构建活动流程的流程图。
	 * @param processDefId String
	 * @param executionId String
	 * @param map ModelMap
	 */
//	public void getProcessPic(String processDefId, String executionId, ModelMap map);
	
	/**
	 * 获取流程图片输入流。
	 * @param processDefId String
	 * @return InputStream
	 */
	public InputStream getProcessPic(String processDefId) ;
	
	/**
	 * 分页查询活动流程。
	 * @param pageSize int
	 * @param currentPage int
	 * @param map ModelMap
	 * @return PagedResult<Map<String, Object>>
	 */
	public List queryPage(int  pageSize , int currentPage,ModelMap  map);
	
	/**
	 * 分页查询历史流程。
	 * @param pageSize int
	 * @param currentPage int
	 * @param map ModelMap
	 * @return PagedResult<Map<String, Object>>
	 */
	public List queryPageForHis(int  pageSize , int currentPage,ModelMap  map);
	
//	/**
//	 * 会签。
//	 * @param execution ActivityExecution
//	 * @return List<String>
//	 */
//	public List<String> getSignUser(ActivityExecution execution) ;
	
	/**
	 * 判断会签流程是否已经审批结束。
	 * @param execution ActivityExecution
	 * @return boolean
	 */
	public boolean isComplete(ActivityExecution execution);
	
	/** 
	 * 获取流程有效用户结点。
	 * @param processId
	 * @return List<Map<String, String>>
	 */
	public List<Map<String, String>> getProcessNodeList(String processId);
}
