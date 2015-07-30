/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.icanft.common.wf.rw.vo.WfRw;

/**
 * <pre>
 * 		流程部署管理接口。
 * </pre>
 * @author zengziwen  zengziwen@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IDeploymentManageService {
	
	/**
	 * 根据流程Key启动一个最新版本的流程实例。
	 * 
	 * @param deployKey String
	 * @param variable Map<String,Object>
	 * @return ProcessInstance
	 * @throws Exception
	 */
	public String startProcByKey(WfRw rw, String deployKey,Map<String,Object> variable) ;
	
	
	/**
	 *  查询部署列表。
	 *  
	 * @param map ModelMap
	 */
	public void queryDeploymentList(ModelMap map,int pageSize, int currentPage);
	
	/**
	 * 部署流程。
	 * 
	 * @param map ModelMap
	 * @throws Exception
	 */
	public void deployProcess(ModelMap map) throws Exception ;
	
	/**
	 * 根据流程定义ID起一个流程。
	 * 
	 * @param id String
	 * @return ProcessInstance
	 * @throws Exception
	 */
	public ProcessInstance startProcessById( String id)throws Exception;
	/**
	 * 根据流程定义ID起一个流程。
	 * 
	 * @param deployId String
	 * @param variable Map<String,Object>
	 * @return ProcessInstance
	 * @throws Exception
	 */
	public ProcessInstance startProcessById(String deployId,Map<String,Object> variable) throws Exception;
	
	
	
	/**
	 * 根据ID启动流程。
	 * 
	 * @param id String
	 * @param map ModelMap
	 */
	public void startProcessById( String id, ModelMap map);
	
	/**
	 * 根据ID删除部署。
	 * 
	 * @param id String
	 * @param map ModelMap
	 */
	public void deleteDeploymentById(@RequestParam String id, ModelMap map);
	
	/**
	 * 查询执行的流程列表。
	 * 
	 * @param page int
	 * @param map ModelMap
	 */
	public void queryExecProcessList(@RequestParam int page, ModelMap map);
	
	/**
	 * 处理表单上传。
	 *  
	 * @param file MultipartFile
	 * @param map ModelMap
	 */
	public void handleFormUpload( MultipartFile file, ModelMap map);
	
	/**
	 * 查询部署列表。
	 * 
	 * @param map ModelMap
	 * @param pageSize int
	 * @param currentPage int
	 * @param returnMsg String
	 * @return PagedResult<Map<String, Object>>
	 */
	public List queryDeploymentList(ModelMap map, int pageSize, int currentPage, String returnMsg);
}
