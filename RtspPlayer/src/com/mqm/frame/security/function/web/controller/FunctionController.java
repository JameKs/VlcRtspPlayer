/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.function.web.controller;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.security.function.service.IFunctionService;
import com.mqm.frame.security.function.vo.FbrpSecResFunction;

/**
 * 
 * <pre>
 * 功能管理控制器类。
 * </pre>
 * 
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("admin")
@SessionAttributes("paramVo")
public class FunctionController extends FbrpBaseController {

	private static final Log log = LogFactory.getLog(FunctionController.class);

	@Resource
	private IFunctionService functionService;

	/**
	 * 创建ParamVoa。
	 * 
	 * @return FbrpSecResFunction
	 */
	@ModelAttribute("paramVo")
	public FbrpSecResFunction createParamVoa() {
		return new FbrpSecResFunction();
	}

	/**
	 * 根据条件查询。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param fbrpSecResFunction
	 *            FbrpSecResFunction
	 * @param map
	 *            ModelMap
	 * 
	 * @return String
	 */
	@RequestMapping("functionManage.do")
	public String query(HttpServletRequest request,
			@ModelAttribute("paramVo") FbrpSecResFunction fbrpSecResFunction,
			ModelMap map) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpSecResFunction> pr = this.functionService.pagedQuery(
				fbrpSecResFunction, pageIndex, pageSize);
		map.put("pr", pr);
		map.put("editData", new FbrpSecResFunction());
		map.put("topInfo", "当前位置：基础管理 > 系统管理 > 功能管理 功能管理 ");
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
				FbrpCommonAuditLog.LOGTYPE_SELECT, "获取功能列表");
		return "admin/functionManage";
	}

	/**
	 * 查询。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param paramVo
	 *            FbrpSecResFunction
	 * @return pr PagedResult<FbrpSecResFunction>结果集
	 */
	@RequestMapping(value = "functionManage.do", params = "query")
	@ResponseBody
	public PagedResult<FbrpSecResFunction> query(HttpServletRequest request,
			FbrpSecResFunction paramVo) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpSecResFunction> pr = this.functionService.pagedQuery(
				paramVo, pageIndex, pageSize);
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
				FbrpCommonAuditLog.LOGTYPE_SELECT, "获取功能列表");
		return pr;
	}

	/**
	 * 确定。
	 * 
	 * @param editData FbrpSecResFunction
	 * 
	 * @return String
	 */
	@RequestMapping(value = "functionManage.do", params = "save")
	public String ok(@ModelAttribute("editData") FbrpSecResFunction editData) {
		try {
			if (editData.getId() == null || editData.getId().length() == 0) {
				this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
						FbrpCommonAuditLog.LOGTYPE_INSERT, "新增URL");
			} else {
				this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
						FbrpCommonAuditLog.LOGTYPE_UPDATE, "修改URL");
			}
			this.functionService.createOrUpdate(editData);
		} catch (Exception ex) {
			log.error("", ex);
			super.exceptionMsg.setMainMsg(ex);
		}
		return "redirect:functionManage.do";
	}

	/**
	 * 显示编辑页面。
	 * 
	 * @param id String
	 * 
	 * @return String
	 */
	@RequestMapping(value="functionManage.do", params="edit")
	@ResponseBody
	public FbrpSecResFunction showEditView(
			@RequestParam(required = false) String id) {
		FbrpSecResFunction editData = null;
		try {
			if (id != null && id.length() != 0) {
				editData = this.functionService.find(id);
			} else {
				editData = new FbrpSecResFunction();
			}
		} catch (Exception e) {
			log.error("", e);
			exceptionMsg.setMainMsg(e);
		}
		return editData;
	}

	/**
	 * 删除一条数据。
	 * 
	 * @param id
	 *            功能ID
	 * 
	 * @return String 返回到功能列表
	 */
	@RequestMapping(value = "functionManage.do", params = "deleteRow")
	public String toDelRow(@RequestParam String id) {
		try {
			// 获取删除URL的id
			if (id != null && ((String) id).length() != 0) {
				this.functionService.delete(id);
				this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
						FbrpCommonAuditLog.LOGTYPE_DELETE, "删除一条URL");
			}
		} catch (Exception ex) {
			log.error("URL管理删除URL错误", ex);
			exceptionMsg.setMainMsg(ex);
		}
		return "redirect:functionManage.do";
	}

	/**
	 * 批量删除URL。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @return String
	 */
	@RequestMapping(value = "functionManage.do", params = "deleteAll")
	public String toDelAll(HttpServletRequest request) {
		String[] ids = request.getParameterValues("id");
		List<String> idList = null;
		if (ids.length > 0) {
			idList = Arrays.asList(ids);
		}
		if (idList != null) {
			try {
				this.functionService.deleteByIds(idList);
				this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
						FbrpCommonAuditLog.LOGTYPE_DELETE, "批量删除URL");
			} catch (Exception ex) {
				log.error("URL管理删除URL错误", ex);
				exceptionMsg.setMainMsg(ex);
			}
		}
		return "redirect:functionManage.do";
	}

}
