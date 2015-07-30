/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.xtgl.jsfp.service;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public interface IJsfpService {

	/**
	 * @param map
	 * @param pageSize
	 * @param currentPage
	 */
	public abstract String queryDeploymentList(ModelMap map, int pageSize,
			int currentPage);

	/**
	 * @param map
	 * @param pageSize
	 * @param currentPage
	 * @param returnMsg
	 * @return
	 */
	public abstract String queryDeploymentList(ModelMap map, int pageSize,
			int currentPage, String returnMsg);

	/**
	 * @param map
	 * @throws Exception
	 */
	public abstract void deployProcess(ModelMap map) throws Exception;

	/**
	 * 根据流程定义ID起一个流程。
	 * @param deployId String
	 * @param variable Map<String,Object>
	 * @return ProcessInstance
	 * @throws Exception 异常
	 */
	public abstract ProcessInstance startProcessById(String deployId,
			Map<String, Object> variable) throws Exception;

	/**
	 * 根据流程Key启动一个最新版本的流程实例。
	 * @param deployKey String
	 * @param variable Map<String,Object>
	 * @return ProcessInstance
	 * @throws Exception 异常
	 */
	public abstract ProcessInstance startProcessByKey(String deployKey,
			Map<String, Object> variable) throws Exception;

	/**
	 * 根据流程定义ID起一个流程。
	 * @param deployId String
	 * @return ProcessInstance
	 * @throws Exception 异常
	 */
	public abstract ProcessInstance startProcessById(String deployId)
			throws Exception;

	/**
	 * 根据流程定义ID起动一个流程，并返回流程的前20记录([该方法在后继必须废弃，转而使用startProcessById(String deployId)方法])。
	 * 
	 * @param deployId String
	 * @param map ModelMap
	 */
	public abstract void startProcessById(String deployId, ModelMap map);

	/**
	 * 根据流程定义ID删除一个流程
	 * @param deployId
	 * @param map
	 */
	public abstract void deleteDeploymentById(String deployId, ModelMap map);

	/**
	 * 查询流程实例
	 * @param page
	 * @param map
	 */
	public abstract void queryExecProcessList(int page, ModelMap map);

	/**
	 * 处理文件上传
	 * @param file
	 * @param map
	 */
	public abstract void handleFormUpload(MultipartFile file, ModelMap map);


}