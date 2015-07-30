package com.mqm.frame.common.auditlog.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.common.auditlog.service.IAuditLogService;
import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLog;
import com.mqm.frame.common.auditlog.vo.FbrpCommonAuditLogQueryVO;
import com.mqm.frame.common.codeinfo.service.ICodeValueService;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.util.constants.BaseConstants;


/**
 * 
 * <pre>
 * 日志管理控制器。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("common")
public class AuditLogController extends FbrpBaseController {
	
	private static final Log log = LogFactory.getLog(AuditLogController.class);

	@Resource
	private IAuditLogService auditLogService ;
	@Resource
	private ICodeValueService codeValueService;
		
	/**
	 * 基于注解的方式定义时间类型转换器。
	 * 
	 * @param binder WebDataBinder
	 */
	@InitBinder  
	public void initBinder(WebDataBinder binder) {   
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");   
		CustomDateEditor dateEditor = new CustomDateEditor(df, true);   
		binder.registerCustomEditor(Date.class, dateEditor);       
	} 
	
    /**
     * 根据条件查询。
     * 
     * @param request
     *          HttpServletRequest
     * @param fbrpCommonAuditLogQueryVO
     *          FbrpCommonAuditLogQueryVO
     * @param map
     *          ModelMap
     * @return  String
     */
	@RequestMapping("auditLog.do")
	public String query(HttpServletRequest request,@ModelAttribute("paramVo") FbrpCommonAuditLogQueryVO  fbrpCommonAuditLogQueryVO ,ModelMap map) {
		try{
			 int pageIndex = this.getPageIndex(request);
			 int pageSize = this.getPageSize(request);
			 PagedResult<FbrpCommonAuditLog> pr = this.auditLogService.pagedQuery(fbrpCommonAuditLogQueryVO, pageIndex, pageSize);
			 map.put("pr", pr);
			 map.put("bizTypemap", initBizType());
			 map.put("bizTypemapJson", toJson(initBizType()));
			 map.put("logTypemap", initLogType());
			 map.put("logTypemapJson", toJson(initLogType()));
		}catch(Exception ex){
			log.error("日志管理查询出错", ex);
			this.exceptionMsg.setMainMsg(ex);
		}
		return "common/auditLog";
	}

	/**
	 * 查询。
	 * 
	 * @param request HttpServletRequest
	 * @param paramVo FbrpCommonAuditLogQueryVO
	 * @return pr PagedResult<FbrpCommonAuditLog>结果集
	 */
	@RequestMapping(value="auditLog.do", params="query")
	@ResponseBody
	public PagedResult<FbrpCommonAuditLog> query(HttpServletRequest request, FbrpCommonAuditLogQueryVO paramVo) {
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpCommonAuditLog> pr = this.auditLogService.pagedQuery(paramVo, pageIndex, pageSize);
		return pr;
	}
	
	/**
	 * 批量删除日志。
	 * 
	 * @param ids String[]
	 * 				
	 * @return int
	 */
	@ResponseBody
	@RequestMapping(value="auditLog.do", params="batchDelete")
	public int batchDelete(String[] ids) {
		try{
			//this.auditLogService.deleteLogsByParams("");
			this.auditLogService.deleteByIds(Arrays.asList(ids));
			return 1;
		}catch(Exception ex){
			log.error("删除日志出错", ex);
			return 0;
		}
	}
	
	/**
	 * 删除日志。
	 * 
	 * @param id String
	 * 
	 * @return String
	 */
	@RequestMapping(value="auditLog.do", params="deleteRow")
	public String deleteLog(@RequestParam String id){
		try{
			if(id!=null&&id.length()!=0){
				this.auditLogService.delete(id);
			}
		}catch(Exception ex){
			log.error("删除日志出错", ex);
			this.exceptionMsg.setMainMsg(ex);
		}
		return "redirect:auditLog.do";
	}
	
	/**
	 * 初始化操作类型。
	 */
	protected Map<String, String> initLogType(){
		//下拉
		Map<String, String> logTypemap = new HashMap<String, String>(7);
		List<FbrpCommonCodeValue> codeValueList = this.codeValueService.getCodeInfoList(BaseConstants.LOG_TYPE);
		for (int i = 0; i < codeValueList.size(); i++) {
			FbrpCommonCodeValue codeValue = codeValueList.get(i);
			logTypemap.put(codeValue.getValue1(), codeValue.getName());
		}
		return 	logTypemap;
	}
	
	/**
	 * 初始化业务模块。
	 */
	protected Map<String, String> initBizType(){
		//下拉
		List<FbrpCommonCodeValue> codeValueList = this.codeValueService.getCodeInfoList(BaseConstants.BIZ_TYPE);
		Map<String, String> bizTypemap = new HashMap<String, String>(10);
		for (int i = 0; i < codeValueList.size(); i++) {
			FbrpCommonCodeValue codeValue = codeValueList.get(i);
			bizTypemap.put(codeValue.getValue1(), codeValue.getName());
		}
		return bizTypemap;
	}
	
}
