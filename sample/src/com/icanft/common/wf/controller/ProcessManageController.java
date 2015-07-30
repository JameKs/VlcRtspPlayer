/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icanft.common.wf.service.IProcessManageService;

/**
 * <pre>
 * 流程管理服务类。
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月29日
 */
@Controller
@RequestMapping("/public-access/workflow")
public class ProcessManageController {
	
	private static final Log log = LogFactory.getLog(ProcessManageController.class);

	@Resource
	IProcessManageService processManageService;

	/**
	 * 查询流程列表。
	 */
	@RequestMapping("processManage.do")
	public String queryProcessList(ModelMap map) {
		processManageService.queryProcessList(map);
		return "workflow/processManage/list";
	}

	/**
	 * 根据流程ID删除流程。
	 */
	@RequestMapping(value = "processManage.do", params = "delete")
	public String deleteProcessById(@RequestParam String id, ModelMap map) {
		try {
			processManageService.deleteProcessById(id, map);
		} catch (Exception e) {
			log.error("删除流程失败", e);
		}
		return "{success:true,msg:'删除流程成功'}";
	}

	@RequestMapping(value = "processManage.do", params = "deleteProcFromRwList")
	@ResponseBody
	public String deleteProcFromRwList(@RequestParam String id) {
		try {
			String[] ids = id.split(",");
			for(String sid:ids)
			{
				processManageService.deleteProcessByProcessInstId(sid);
			}
		} catch (Exception e) {
			log.error("删除流程失败", e);
		}
		return "{success:true,msg:'删除流程成功'}";
	}

	/**
	 * 显示活动中的流程图。
	 * 
	 * @param processDefId
	 *                String
	 * @param processInstanceId
	 *                String
	 * @param executionId
	 *                String
	 * @param map
	 *                ModelMap
	 * @return String
	 */
	@RequestMapping(value = "processManage.do", params = "showPic")
	public String getProcessMonitor(String processDefId, 
									String processInstanceId,
									String executionId, ModelMap map) {
		if (processDefId != null && processDefId.indexOf(":") != -1) {
			processManageService.getProcessMonitor(processDefId, processInstanceId, executionId, map);
		} else {
			map.put("processDefId", processDefId);
			map.put("executionId", executionId);
		}
		return "forward:/jsp/wf/processMonitor.jsp";
	}
	
	/**
	 * 显示流程图2。
	 */
	@RequestMapping(value = "processManage.do", params = "pic")
	public void getProcessPic(@RequestParam String processDefId, 
			ModelMap map,
			HttpServletResponse resp) {
		InputStream is = processManageService.getProcessPic(processDefId);
		resp.setContentType("image/png");
		OutputStream out;
		try {
			out = resp.getOutputStream();
		
			byte[] b = new byte[1024];   
			int len = -1;   
			while((len = is.read(b, 0, 1024)) != -1) {   
			    out.write(b, 0, len);   
			}      
			out.flush();  
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 显示历史的的流程图。
	 * 
	 * @param processDefId
	 *                String
	 * @param executionId
	 *                String
	 * @param map
	 *                ModelMap
	 * @return String
	 */
	@RequestMapping(value = "processManage.do", params = "showHisPic")
	public String getProcessHisPic(@RequestParam String processDefId, @RequestParam String executionId, ModelMap map) {
		processManageService.getProcessHisPic(processDefId, executionId, map);
		return "forward:/jsp/wf/processMonitor.jsp";
	}

	/**
	 * 页面查询。
	 * 
	 * @param request
	 *                HttpServletRequest
	 * @param map
	 *                ModelMap
	 * @return PagedResult<Map<String, Object>>
	 */
	@RequestMapping(value = "processManage.do", params = "gotoPage")
	@ResponseBody
	public List<Map<String, Object>> gotoPage(HttpServletRequest request, ModelMap map) {
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		List<Map<String, Object>> pr = processManageService.queryPage(pageSize, 1, map);
		return pr;

	}

	/**
	 * 历史页面查询。
	 * 
	 * @param request
	 *                HttpServletRequest
	 * @param map
	 *                ModelMap
	 * @return PagedResult<Map<String, Object>>
	 */
	@RequestMapping(value = "processManage.do", params = "gotoPageForHis")
	@ResponseBody
	public List<Map<String, Object>> gotoPageForHis(HttpServletRequest request, ModelMap map) {
		int pageIndex = Integer.parseInt(request.getParameter("pageIndex"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		return processManageService.queryPageForHis(pageSize, 1, map);
	}

}
