/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.xtgl.lcgl.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.icanft.common.util.StringUtil;
import com.icanft.xtgl.lcgl.service.ILcglService;

/**
 * <pre>
 * 文件中文描述
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public class LcglServiceImpl implements ILcglService {
	
	private static final Logger logger = Logger.getLogger(LcglServiceImpl.class);
	
	private RepositoryService repositoryService;
	
	private RuntimeService runtimeService;
	
	private ManagementService managementService;
	
	
	@Override
	public String queryDeploymentList(ModelMap map,int pageSize, int currentPage) {
		return querCommonList(map, pageSize, currentPage, null);
	}

	@Override
	public String queryDeploymentList(ModelMap map, int pageSize, int currentPage, String returnMsg) {
		return querCommonList(map, pageSize, currentPage, null);
	}

	private String querCommonList(ModelMap map, int pageSize, int currentPage, String returnMsg) {
		ProcessDefinitionQuery query = repositoryService.createProcessDefinitionQuery();
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
		int firstResult = 0;
		if (currentPage <= 0 && currentPage != -100) {
			currentPage = 1;
		}
		if (currentPage > 0) {
			firstResult = (currentPage - 1) * pageSize+1;
		}
		int maxResults = firstResult+pageSize-1;
		List<ProcessDefinition> queryList = query.listPage(firstResult, maxResults);
		Collections.sort(queryList, new Comparator<ProcessDefinition>() {
			@Override
			public int compare(ProcessDefinition o1, ProcessDefinition o2) {
				int i1 = o1.getVersion();
				int i2 = o2.getVersion();
				return i1 - i2;
			}
		});
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		for (ProcessDefinition pd : queryList) {
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", pd.getId());
			m.put("key", pd.getKey());
			m.put("name", pd.getName());
			m.put("version", pd.getVersion());
			m.put("deploymentId", pd.getDeploymentId());
			list.add(m);
		}
		String jsonString = StringUtil.pageListToJsonPD(list,totalSize);
		return jsonString;
	}

	/* (non-Javadoc)
	 * @see com.icanft.xtgl.lcgl.service.ILcglService#deployProcess(org.springframework.ui.ModelMap)
	 */
	@Override
	public void deployProcess(ModelMap map) throws  Exception {
		String msg = "";

		String fileName = (String) map.get("fileName");
		if (!fileName.endsWith(".bar")) {
			msg="流程部署失败";
			throw new Exception(msg);
		}
		MultipartFile multipartFile = (MultipartFile) map.get("file");
		InputStream is = multipartFile.getInputStream();
		ZipInputStream zi = new ZipInputStream(is);
		repositoryService.createDeployment().name(fileName).addZipInputStream(zi).deploy();
		msg = "流程部署成功！";
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.xtgl.lcgl.service.ILcglService#startProcessById(java.lang.String, java.util.Map)
	 */
	@Override
	public ProcessInstance startProcessById(String deployId,Map<String,Object> variable) throws  Exception {
		return runtimeService.startProcessInstanceById(deployId, variable); 
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.xtgl.lcgl.service.ILcglService#startProcessByKey(java.lang.String, java.util.Map)
	 */
	@Override
	public ProcessInstance startProcessByKey(String deployKey,Map<String,Object> variable) throws  Exception {
		return runtimeService.startProcessInstanceByKey(deployKey, variable); 
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.xtgl.lcgl.service.ILcglService#startProcessById(java.lang.String)
	 */
	@Override
	public ProcessInstance startProcessById(String deployId) throws  Exception {
		return runtimeService.startProcessInstanceById(deployId);
	}
	
	/* (non-Javadoc)
	 * @see com.icanft.xtgl.lcgl.service.ILcglService#startProcessById(java.lang.String, org.springframework.ui.ModelMap)
	 */
	@Override
	public void startProcessById(String deployId, ModelMap map) {
		String msg = null;
		ProcessInstance processInstance = null;
		try {
			processInstance = runtimeService.startProcessInstanceById(deployId);
			if (processInstance != null) {
				msg = "流程启动成功";
				logger.info(msg + " ！[id:" + deployId + "]");
			}
		} catch (Exception e) {
			logger.error("流程启动成功出错！", e);
		}
		querCommonList(map, 20, 1, msg);
	}

	/* (non-Javadoc)
	 * @see com.icanft.xtgl.lcgl.service.ILcglService#deleteDeploymentById(java.lang.String, org.springframework.ui.ModelMap)
	 */
	@Override
	public void deleteDeploymentById(String deployId, ModelMap map) {
		logger.info("准备卸载Deployment- id:" + deployId);
		String msg = null;
		try {
			repositoryService.deleteDeployment(deployId, true);
			logger.info("卸载Deployment- id:" + deployId + "成功 ");
			msg = "卸载成功！";
		} catch (Exception e) {
			logger.error("卸载失败！", e);
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

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	/**
	 * @param managementService the managementService to set
	 */
	public void setManagementService(ManagementService managementService) {
		this.managementService = managementService;
	}
	
	
}
