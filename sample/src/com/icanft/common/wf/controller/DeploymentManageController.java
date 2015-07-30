/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.icanft.common.wf.service.IDeploymentManageService;

/**
 * <pre>
 * 流程部署管理服务类。
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
//@Controller
//@RequestMapping("workflow")
public class DeploymentManageController {
	private static final Log log = LogFactory .getLog(DeploymentManageController.class);

//	@Resource
	IDeploymentManageService deploymentManageService;

	/**
	 * 查询已部署的流程列表。
	 */
//	@RequestMapping(value = "deploymentManage.do")
	public String queryDeploymentList(ModelMap map,HttpServletRequest request) {
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		try {
			deploymentManageService.queryDeploymentList(map,pageSize,1);
		} catch (Exception e) {
			log.error("流程部署查询异常!", e);
		}
		return "workflow/deploymentManage/list";
	}

	/**
	 * 部署新流程。
	 * 
	 * @param map ModelMap
	 * @return String
	 */
//	@RequestMapping(value = "deploymentManage.do", params = "deploy")
	public String deployProcess(ModelMap map) {
		try {
			deploymentManageService.deployProcess(map);
			map.put("msg", "true");
		} catch (Exception e) {
			map.put("msg", "false");
			log.error("流程部署失败！", e);
		}
		return "workflow/deploymentManage/list";
	}

	/**
	 * 根据流程的id启动流程 。
	 */
//	@RequestMapping(value = "deploymentManage.do", params = "startprc")
	public String startProcessById(@RequestParam String id, ModelMap map) {
		deploymentManageService.startProcessById(id, map);
		return "workflow/deploymentManage/list";
	}

	/**
	 * 根据流程ID删除已经部署的流程。
	 * 
	 * @param id String
	 * @param map ModelMap
	 * @return String
	 */
//	@RequestMapping(value = "deploymentManage.do", params = "delete")
//	@ResponseBody
	public boolean deleteDeploymentById(@RequestParam String id, ModelMap map) {
		try{
			deploymentManageService.deleteDeploymentById(id, map);
			log.info("删除成功![id:"+id+"]");
		}catch(Exception e){
			log.error("删除失败!",e);
			return false;
		}
		return true;
	}

	/**
	 * 查询正在执行的流程列表。
	 * 
	 * @param page int
	 * @param map ModelMap
	 * @return String
	 */
//	@RequestMapping(value = "deploymentManage.do", params = "queryexcepro")
	public String queryExecProcessList(@RequestParam int page, ModelMap map) {
		deploymentManageService.queryExecProcessList(page, map);
		return "";
	}

	/**
	 * 处理文件上传。
	 * @param file MultipartFile
	 * @param map ModelMap
	 * @return String
	 * @throws IOException 异常
	 */
//	@RequestMapping(value = "deploymentManage.do", params = "fileUpload")
	public String handleFormUpload(@RequestParam("file") MultipartFile file,
			ModelMap map) throws IOException {
		deploymentManageService.handleFormUpload(file, map);
		return deployProcess(map);
	}

	/**
	 * 页面控制器。
	 * 
	 * @param request HttpServletRequest
	 * @param map ModelMap
	 * @return List<Map<String, Object>>
	 */
//	@RequestMapping(value = "deploymentManage.do", params = "gotoPage")
//	@ResponseBody
	public List<Map<String, Object>> gotoPage(
			HttpServletRequest request, ModelMap map) {
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		List<Map<String, Object>> pr = deploymentManageService .queryDeploymentList(map, pageSize, 1, null);
		return pr;

	}

}
