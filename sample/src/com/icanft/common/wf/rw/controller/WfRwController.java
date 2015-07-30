/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.rw.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icanft.common.wf.rw.service.IWfRwService;
import com.icanft.xtgl.yhgl.vo.User;

/**
 * <pre>
 * 当没有涉及系统因为表时，可以直接通过此类来控制流程。
 * </pre>
 * @author meihu  meihu@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("public-access/workflow")
public class WfRwController {
	private static final Log log = LogFactory.getLog(WfRwController.class);

	@Resource
	IWfRwService wfRwService;

	/**
	 * 签收任务
	 * @return
	 */
	@RequestMapping(value="rw.do", params="claimTask")
	@ResponseBody
	public String claimTask(ModelMap map, String taskId, HttpServletRequest req){
		User sessionUser = (User)req.getSession().getAttribute("user");
		String userId = sessionUser.getId();
		wfRwService.claimTask(taskId, userId);
		return "{\"success\":true,\"msg\":\"提交成功\"}";
	}
	
	/**
	 * 反签收任务
	 * @return
	 */
	@RequestMapping(value="rw.do", params="unClaimTask")
	@ResponseBody
	public String unClaimTask(ModelMap map, String taskId, HttpServletRequest req){
		wfRwService.unClaimTask(taskId);
		return "{\"success\":true,\"msg\":\"提交成功\"}";
	}
	
	/**
	 * 转派任务流程。
	 */
	@ResponseBody
	@RequestMapping(value = "rw.do", params = "transferAssignee")
	public String transferAssignee(String taskId, String userId, ModelMap map, HttpServletRequest req) {
		wfRwService.transferAssignee(taskId, userId);
		return "{\"success\":true,\"msg\":\"转派成功\"}";
	}
	
	/**
	 * 委托任务流程。
	 */
	@ResponseBody
	@RequestMapping(value = "rw.do", params = "delegateTask")
	public String delegateTask(String taskId, String userId, ModelMap map, HttpServletRequest req) {
		wfRwService.delegateTask(taskId, userId);
		return "{\"success\":true,\"msg\":\"委托成功\"}";
	}
	
	/**
	 * 撤回任务流程。
	 */
	@ResponseBody
	@RequestMapping(value = "rw.do", params = "withdrawTask")
	public String withdrawTask(String taskId, ModelMap map, HttpServletRequest req) {
		wfRwService.withdrawTask(taskId);
		return "{\"success\":true,\"msg\":\"撤回成功\"}";
	}
	
	/**
	 * 驳回任务流程。
	 */
	@ResponseBody
	@RequestMapping(value = "rw.do", params = "rejectTask")
	public String rejectTask(String taskId, ModelMap map, HttpServletRequest req) {
		wfRwService.rejectTask(taskId);
		return "{\"success\":true,\"msg\":\"驳回成功\"}";
	}

}
