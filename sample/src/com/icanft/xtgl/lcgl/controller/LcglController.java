/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.xtgl.lcgl.controller;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.icanft.xtgl.lcgl.service.ILcglService;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
@Controller
@RequestMapping(value = "/lcxx")
public class LcglController {
	private static final Logger log = Logger.getLogger(LcglController.class);

	@Resource
	ILcglService lcglService;
	
	/**
	 * 进入流程部署界面
	 * @param map
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "lcgl.do",params="lcbs")
	public String lcbsMain(ModelMap map, HttpServletRequest request) throws Exception{
		return "/admin/xtgl/lcgl/lcbs";
	}
	
	/**
	 * 查询已部署的流程列表。
	 * 
	 * @param map
	 *            ModelMap
	 * @param request
	 *            HttpServletRequest
	 * @return String
	 */
	@RequestMapping(value = "lcgl.do",params="lcbslb")
	@ResponseBody
	public String queryDeploymentList(ModelMap map, HttpServletRequest request) throws Exception{
		int currentPage = Integer.parseInt(request.getParameter("page")==null?"0":request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("limit")==null?"1":request.getParameter("limit"));
		String jsonString = "";
		try {
			jsonString = lcglService.queryDeploymentList(map, pageSize, currentPage);
		} catch (Exception e) {
			log.error("流程部署查询异常!", e);
			throw new Exception("流程部署查询异常!");
		}
		
		return jsonString;
	}

	/**
	 * 部署新流程。
	 * 
	 * @param map
	 *            ModelMap
	 * @return String
	 */
	@RequestMapping(value = "lcgl.do", params = "deploy")
	@ResponseBody
	public String deployProcess(ModelMap map) {
		try {
			lcglService.deployProcess(map);
		} catch (Exception e) {
			log.error("流程部署失败！", e);
			return "{seccess:false,msg:'流程部署失败！'}";
		}
		return "{seccess:true,msg:'流程部署成功！'}";
	}

	/**
	 * 根据流程的id启动流程 。
	 * 
	 * @param id
	 *            String
	 * @param map
	 *            ModelMap
	 * @return String
	 */
	@RequestMapping(value = "lcgl.do", params = "startprc")
	@ResponseBody
	public String startProcessById(@RequestParam String id, ModelMap map) {
		lcglService.startProcessById(id, map);
		return "workflow/deploymentManage/list";
	}

	/**
	 * 根据流程ID删除已经部署的流程。
	 * 
	 * @param id
	 *            String
	 * @param map
	 *            ModelMap
	 * @return String
	 */
	@RequestMapping(value = "lcgl.do", params = "delete")
	@ResponseBody
	public boolean deleteDeploymentById(@RequestParam String id, ModelMap map) {
		try {
			lcglService.deleteDeploymentById(id, map);
			log.info("删除成功![id:" + id + "]");
		} catch (Exception e) {
			log.error("删除失败!", e);
			return false;
		}
		return true;
	}

	/**
	 * 查询正在执行的流程列表。
	 * 
	 * @param page
	 *            int
	 * @param map
	 *            ModelMap
	 * @return String
	 */
	@RequestMapping(value = "lcgl.do", params = "queryexcepro")
	@ResponseBody
	public String queryExecProcessList(@RequestParam int page, ModelMap map) {
		lcglService.queryExecProcessList(page, map);
		return "";
	}

	/**
	 * 处理文件上传。
	 * 
	 * @param file
	 *            MultipartFile
	 * @param map
	 *            ModelMap
	 * @return String
	 * @throws IOException
	 *             异常
	 */
	@RequestMapping(value = "lcgl.do", params = "fileUpload")
	@ResponseBody
	public String handleFormUpload(@RequestParam("file") MultipartFile file,
			ModelMap map) throws IOException {
		lcglService.handleFormUpload(file, map);
		return deployProcess(map);
	}

	/**
	 * 页面控制器。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param map
	 *            ModelMap
	 * @return PagedResult<Map<String, Object>>
	 */
	@RequestMapping(value = "deploymentManage.do", params = "gotoPage")
	@ResponseBody
	public String gotoPage(HttpServletRequest request, ModelMap map) {
		int currentPage = Integer.parseInt(request.getParameter("page")==null?"0":request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("limit")==null?"1":request.getParameter("limit"));
		
		return lcglService.queryDeploymentList(map, pageSize, currentPage,
				null);

	}

}
