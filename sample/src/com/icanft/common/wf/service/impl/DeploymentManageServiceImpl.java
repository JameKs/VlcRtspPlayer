/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 *//*
package com.icanft.common.wf.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.query.Query;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.icanft.common.wf.service.IDeploymentManageService;
import com.icanft.common.wf.yhrw.dao.IYhrwDao;
import com.icanft.common.wf.yhrw.vo.WfYhrw;



*//**
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
 *//*
public class DeploymentManageServiceImpl implements IDeploymentManageService {

	private static final Log log = LogFactory.getLog(DeploymentManageServiceImpl.class);

	RepositoryService repositoryService;

	RuntimeService runtimeService;
	
	IYhrwDao yhrwDao;
	
	
	

	@Override
	public void queryDeploymentList(ModelMap map,int pageSize, int currentPage) {
		querCommonList(map, pageSize, currentPage, null);
	}

	@Override
	public List queryDeploymentList(ModelMap map, int pageSize, int currentPage, String returnMsg) {
		List pr = querCommonList(map, pageSize, currentPage, null);
		return pr;
	}

	private List querCommonList(ModelMap map, int pageSize, int currentPage, String returnMsg) {
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

	@Override
	public void deployProcess(ModelMap map) throws  Exception {
		String msg = "";

		String fileName = (String) map.get("fileName");
		if (!fileName.endsWith(".bar")) {
			msg="流程部署失败";
			querCommonList(map, 10, 1, msg);
		}
		MultipartFile multipartFile = (MultipartFile) map.get("file");
		InputStream fileStream = multipartFile.getInputStream();
		ZipInputStream inputStream = new ZipInputStream(fileStream);
		repositoryService.createDeployment().name(fileName).addZipInputStream(inputStream).deploy();
		msg = "流程部署成功！";
		querCommonList(map, 10, 1, msg);
	}
	
	*//**
	 * 根据流程定义ID起一个流程。
	 * @param deployId String
	 * @param variable Map<String,Object>
	 * @return ProcessInstance
	 * @throws Exception 异常
	 *//*
	public ProcessInstance startProcessById(String deployId,Map<String,Object> variable) throws  Exception {
		return runtimeService.startProcessInstanceById(deployId, variable); 
	}
	

	
	*//**
	 * 根据流程定义ID起一个流程。
	 * @param deployId String
	 * @return ProcessInstance
	 * @throws Exception 异常
	 *//*
	public ProcessInstance startProcessById(String deployId) throws  Exception {
		return runtimeService.startProcessInstanceById(deployId);
	}
	
	*//**
	 * 根据流程定义ID起一个流程，并返回流程的前20记录([该方法在后继必须废弃，转而使用startProcessById(String deployId)方法])。
	 * 
	 * @param deployId String
	 * @param map ModelMap
	 *//*
	public void startProcessById(String deployId, ModelMap map) {
		String msg = null;
		ProcessInstance processInstance = null;
		try {
			processInstance = runtimeService.startProcessInstanceById(deployId);
			if (processInstance != null) {
				msg = "流程启动成功";
				log.info(msg + " ！[id:" + deployId + "]");
			}
		} catch (Exception e) {
			log.error("流程启动成功出错！", e);
		}
		querCommonList(map, 20, 1, msg);
	}

	@Override
	public void deleteDeploymentById(String deployId, ModelMap map) {
		log.info("准备卸载Deployment- id:" + deployId);
		String msg = null;
		try {
			repositoryService.deleteDeployment(deployId, true);
			log.info("卸载Deployment- id:" + deployId + "成功 ");
			msg = "卸载成功！";
		} catch (Exception e) {
			log.error("卸载失败！", e);
		}
		querCommonList(map, 10, 1, msg);
	}

	@Override
	public void queryExecProcessList(int page, ModelMap map) {
		List<ProcessInstance> processList = runtimeService.createProcessInstanceQuery().list();
		map.put("execList", processList);
	}

	@Override
	public void handleFormUpload(MultipartFile file, ModelMap map) {
		String name = file.getOriginalFilename();
		map.put("fileName", name);
		map.put("file", file);
	}
	*//**
	 * 设置 repositoryService。
	 * 
	 * @param repositoryService RepositoryService
	 *//*
	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	*//**
	 * 设置 runtimeService。
	 * 
	 * @param runtimeService RuntimeService
	 *//*
	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}
	
}
*/