/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.icanft.common.wf.service.ITaskManageService;

/**
 * <pre>
 * 任务控制器。
 *  1、按业务要求发一个审批流。
 *  2、对已经发起的流程进行管理。
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
//@RequestMapping("public-access/workflow")
public class TaskManageController {
	private static final Log log = LogFactory.getLog(TaskManageController.class);

//	@Resource
	ITaskManageService taskManageService;

	
	/**
	 * 驳回流程。
	 */
//	@ResponseBody
//	@RequestMapping(value = "taskManage.do", params = "backProcess")
	public boolean backProcess(String _ywId, String _wfTaskId, String activitiId, String activitiName,
									String _wfActNodeId, String _wfActNodeName,
									String _opinion2, String _createTime,
									ModelMap map) {
		try {
//			Date rwJbSj = new Date(Long.parseLong(_createTime));
		} catch (Exception e) {
			log.error(" 驳回流程失败!", e);
			return false;
		}
		return true;
	}

}
