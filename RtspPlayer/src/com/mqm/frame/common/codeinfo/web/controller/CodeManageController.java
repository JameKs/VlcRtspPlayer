package com.mqm.frame.common.codeinfo.web.controller;

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

import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.common.codeinfo.service.ICodeTypeService;
import com.mqm.frame.common.codeinfo.service.ICodeValueService;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeType;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;

/**
 * 
 * <pre>
 * 代码类型控制器。
 * </pre>
 * 
 * @author linjunxiong linjunxiong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Controller
@RequestMapping("common")
public class CodeManageController extends FbrpBaseController {

	private static final Log log = LogFactory.getLog(CodeManageController.class);

	@Resource
	private ICodeTypeService codeTypeService;

	@Resource
	private ICodeValueService codeValueService;
	
	/**
	 * 根据条件查询。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param fbrpCommonCodeType
	 *            FbrpCommonCodeType
	 * @param map
	 *            ModelMap
	 * @return String
	 */
	@RequestMapping("codeManage.do")
	public String query(HttpServletRequest request,
			@ModelAttribute("paramVo") FbrpCommonCodeType fbrpCommonCodeType,
			ModelMap map) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpCommonCodeType> pr = this.codeTypeService.query(
				fbrpCommonCodeType, pageIndex, pageSize);
		map.put("pr", pr);
		map.put("editData", new FbrpCommonCodeType());
		map.put("topInfo", "当前位置：基础管理 > 系统管理 > 代码管理 代码类型管理");
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
				FbrpCommonAuditLog.LOGTYPE_SELECT, "获取代码类型列表");
		return "common/codeManage";
	}

	/**
	 * 查询。
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param paramVo
	 *            FbrpCommonCodeType
	 * @return pr PagedResult<FbrpCommonCodeType>结果集
	 */
	@RequestMapping(value = "codeManage.do", params = "query")
	@ResponseBody
	public PagedResult<FbrpCommonCodeType> query(HttpServletRequest request,
			FbrpCommonCodeType paramVo) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpCommonCodeType> pr = this.codeTypeService.query(
				paramVo, pageIndex, pageSize);
		this.auditLog(FbrpCommonAuditLog.BIZTYPE_URL,
				FbrpCommonAuditLog.LOGTYPE_SELECT, "获取代码类型列表");
		return pr;
	}

	/**
	 * 保存。
	 * 
	 * @param fbrpCommonCodeType FbrpCommonCodeType
	 * 
	 * @return String 导航
	 */
	@RequestMapping(value = "codeManage.do", params = "save")
	public String ok(
			@ModelAttribute("editData") FbrpCommonCodeType fbrpCommonCodeType) {
		try {
			if (this.codeTypeService.checkExist(fbrpCommonCodeType)) {
				this.exceptionMsg.setMainMsg("名称或中文名称已经存在，保存失败！");
				return "redirect:codeManage.do";
			}
			this.codeTypeService.createOrUpdate(fbrpCommonCodeType);
		} catch (Exception ex) {
			log.error("保存代码类型错误", ex);
			this.exceptionMsg.setMainMsg("保存代码类型错误");
			return "redirect:codeManage.do";
		}
		return "redirect:codeManage.do";
	}

	/**
	 * 显示编辑页面。
	 * 
	 * @param id String
	 * 
	 * @return String
	 */
	@RequestMapping(value = "codeManage.do", params = "edit")
	@ResponseBody
	public FbrpCommonCodeType showEditView(
			@RequestParam(required = false) String id) {
		FbrpCommonCodeType editData = null;
		try {
			if (id != null && id.length() != 0) {
				editData = this.codeTypeService.find(id);
			} else {
				editData = new FbrpCommonCodeType();
			}
		} catch (Exception e) {
			log.error("", e);
			exceptionMsg.setMainMsg(e);
		}
		return editData;
	}

	/**
	 * 删除代码类型。
	 * 
	 * @param id
	 *            String
	 *            
	 * @return String
	 */
	@RequestMapping(value = "codeManage.do", params = "deleteRow")
	public String deleteCodeType(@RequestParam String id) {
		try {
			this.codeTypeService.deleteByCascade(id);
		} catch (Exception ex) {
			log.error("删除代码类型错误", ex);
			this.exceptionMsg.setMainMsg(ex);
		}
		return "redirect:codeManage.do";
	}

	/**
	 * 查询方法。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param id String
	 * 
	 * @param map ModelMap
	 * 
	 * @return String
	 */
	@RequestMapping(value="codeManage.do", params="codeValueEdit")
	public String query(HttpServletRequest request, @RequestParam String id,
			ModelMap map) {
		try {
			//List<FbrpCommonCodeValue> codeValues = this.codeValueService.findByCodeTypeId(id);
			FbrpCommonCodeType fbrpCommonCodeType = this.codeTypeService.find(id);
			PagedResult<FbrpCommonCodeValue> pr = this.query(request, id);
			FbrpCommonCodeValue vo = new FbrpCommonCodeValue();
			vo.setCodeTypeId(id);
			vo.setSortNo(pr.getTotal());
			//map.put("dataList", codeValues);
			map.put("id", id);
			map.put("paramVo", vo);
			map.put("pr", pr);
			map.put("searchCodeType", fbrpCommonCodeType);
		} catch (Exception ex) {
			log.error("代码值查询错误", ex);
		}
		return "common/codeValueEdit";
	}

	/**
	 * 查询。
	 * 
	 * @param request HttpServletRequest
	 * 
	 * @param id
	 *            CodeValue's ID
	 * @return PagedResult<FbrpCommonCodeValue>
	 */
	@RequestMapping(value = "codeManage.do", params = "queryValue")
	@ResponseBody
	public PagedResult<FbrpCommonCodeValue> query(HttpServletRequest request,
			String id) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpCommonCodeValue> pr = this.codeValueService
				.findByCodeTypeId(id, pageIndex, pageSize);
		return pr;
	}

	/**
	 * 编辑。
	 * 
	 * @param id
	 *            CodeValue's ID
	 * @return FbrpCommonCodeValue
	 */
	@RequestMapping(value = "codeManage.do", params = "editValue")
	@ResponseBody
	public FbrpCommonCodeValue edit(String id) {
		if (id == null || "".equals(id)) {
			return new FbrpCommonCodeValue();
		} else {
			return this.codeValueService.find(id);
		}
	}

	/**
	 * 保存。
	 * 
	 * @param data FbrpCommonCodeValue
	 * 
	 * @return String 导航
	 * 
	 */
	@RequestMapping(value = "codeManage.do", params = "saveValue")
	@ResponseBody
	public String save(FbrpCommonCodeValue data) {
		try {
			if ("".equals(data.getId())) {
				List<FbrpCommonCodeValue> codeValues = this.codeValueService
						.findByCodeTypeId(data.getCodeTypeId());
				Long maxSortNo = 0L;
				for(FbrpCommonCodeValue fccv : codeValues) {
					maxSortNo = maxSortNo > fccv.getSortNo() ? maxSortNo : fccv.getSortNo();
				}
				data.setSortNo((maxSortNo + 1L));
			}
			this.codeValueService.createOrUpdateCodeValue(data);
		} catch (Exception ex) {
			log.error("代码值保存错误", ex);
			return "0";
		}
		return "1";
	}

	/**
	 * 单个删除。
	 * 
	 * @param id
	 *            CodeValue's ID
	 * @return String 结果：“-1：否，其他：是”
	 */
	@RequestMapping(value = "codeManage.do", params = "deleteRowValue")
	@ResponseBody
	public int deleteRow(String id) {
		try{
			this.codeValueService.deleteCodeValue(id) ;
			return 1;
		}catch (Exception e) {
		    log.error("", e);
		    return 0;
		}
		 
	}

	/**
	 * 多个删除。
	 * 
	 * @param request
	 *            请求
	 * @return String 结果：“-1：否，其他：是”
	 */
	@RequestMapping(value = "codeManage.do", params = "deleteAllValue")
	@ResponseBody
	public int deleteAll(HttpServletRequest request) {
		String[] ids = request.getParameterValues("ids[]");
		List<String> idList = Arrays.asList(ids);
		try{
		    this.codeValueService.deleteCodeValue(idList) ;
		   return 1;
		}catch (Exception e) {
	        log.error("", e);
	        return 0;
		}
	}

}
