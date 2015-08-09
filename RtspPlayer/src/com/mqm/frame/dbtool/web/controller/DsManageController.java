/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.dbtool.web.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mqm.frame.dbtool.service.IDsService;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.dbtool.vo.FbrpDbtoolDsVO;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.infrastructure.web.controller.FbrpBaseController;
import com.mqm.frame.util.StringUtil;
import com.mqm.frame.common.codeinfo.service.ICodeValueService;
import com.mqm.frame.common.codeinfo.vo.FbrpCommonCodeValue;

/**
 * <pre>
 * 数据源的控制器。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Controller
@RequestMapping("dbtool")
public class DsManageController extends FbrpBaseController{
	
	private static final Log log = LogFactory.getLog(DsManageController.class);
	
	@Resource
	private IDsService dsService;
	
	@Resource
	private ICodeValueService codeValueService;
  
	/**
	 * DS的初始化。
	 * 
	 * @param fbrpDbtoolDs FbrpDbtoolDs
	 * @param request HttpServletRequest
	 * @param map ModelMap
	 * @return String
	 */
	@RequestMapping("dsManage.do")
	public String init(FbrpDbtoolDsVO fbrpDbtoolDs, HttpServletRequest request, ModelMap map){
		String rootCode = this.getUser().getOrgId();
		String rootName = this.getUser().getOrgName();
		String userCode = this.getUser().getLoginId();
		/** 缓存查询条件*/
		FbrpDbtoolDsVO tmp_vo = new FbrpDbtoolDsVO();
		this.setValueObject(tmp_vo, fbrpDbtoolDs, this.getClass().getName()+"_", "ext5");
		
		fbrpDbtoolDs.setIncludeOrgCode(StringUtils.isEmpty(fbrpDbtoolDs.getIncludeOrgCode())?rootCode:fbrpDbtoolDs.getIncludeOrgCode());
		fbrpDbtoolDs.setSwjgMc(StringUtils.isEmpty(fbrpDbtoolDs.getSwjgMc())?rootName:fbrpDbtoolDs.getSwjgMc());
	    PagedResult<FbrpDbtoolDsVO> pr = this.load(fbrpDbtoolDs, request);
	    
		/** 回传查询条件*/
		if(null != tmp_vo && "back".equals(tmp_vo.getExt5())) {
			fbrpDbtoolDs = tmp_vo;
		}
		map.put("pr", pr);
		map.put("fbrpDbtoolDs", fbrpDbtoolDs);
		map.put("rootCode", rootCode);
		map.put("userCode", userCode);
		return "dbtool/dsManage";   
	}
	
	/**
	 * DS加载。
	 * 
	 * @param fbrpDbtoolDs FbrpDbtoolDs
	 * @param request HttpServletRequest
	 * @return PagedResult<FbrpDbtoolDsVO>
	 */
	@ResponseBody
	@RequestMapping(value="dsManage.do",params="load")
	public PagedResult<FbrpDbtoolDsVO> load(FbrpDbtoolDsVO fbrpDbtoolDs, HttpServletRequest request){
		int pageIndex = this.getPageIndex(request);
		int pageSize = this.getPageSize(request);
		PagedResult<FbrpDbtoolDsVO> pr = this.dsService.pagedQuery(fbrpDbtoolDs, pageIndex, pageSize);
		return pr;
	}
	
	/**
	 * 新增或修改。
	 * 
	 * @param id String
	 * @param map ModelMap
	 * @return String
	 */
	@RequestMapping(value= "dsManage.do",params="edit")
	public String edit(String id,ModelMap map){
		FbrpDbtoolDs fbrpDbtoolDs = null;
		if(StringUtil.isStrEmpty(id)){
			fbrpDbtoolDs = new FbrpDbtoolDs();
		}else{
			fbrpDbtoolDs = this.dsService.findFirstBy("id",id);			
		}
		map.put("fbrpDbtoolDs",fbrpDbtoolDs);
	    return "dbtool/dsEdit";   
	}
	
	/**
	 * DS测试连接。
	 * 
	 * @param fbrpDbtoolDs FbrpDbtoolDs
	 * @return boolean
	 */
	@ResponseBody
	@RequestMapping(value="dsManage.do",params="testConn")
	public boolean testConnection(FbrpDbtoolDs fbrpDbtoolDs){
		boolean result = this.dsService.testConnection(fbrpDbtoolDs);
		return result;
	}
	
	/**
	 * DS保存。
	 * 
	 * @param fbrpDbtoolDs FbrpDbtoolDs
	 * @param result BindingResult
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="dsManage.do",params="save")
	public String save(FbrpDbtoolDs fbrpDbtoolDs,BindingResult result){
		if(result.hasErrors()){
			return "-1";
		}
		try{		
			fbrpDbtoolDs.setSjssjgDm(this.getUser().getOrgId());
			String message = this.dsService.createOrUpdateDs(fbrpDbtoolDs);
			return message;
		}catch (Exception e) {
			log.error("", e);
			return "0";
		}
	}
	
	/**
	 * 删除。
	 * 
	 * @param id String
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="dsManage.do",params="delete")
	public String delete(String id){
		try{
			this.dsService.delete(id);
			return "1";
		}catch (Exception e) {
			log.error("", e);
			return "0";
		}
	}
	
	/**
	 * 批量删除。
	 * 
	 * @param ids String[]
	 * @return String
	 */
	@ResponseBody
	@RequestMapping(value="dsManage.do",params="batchDelete")
	public String batchDelete(String[] ids){
		try{
			this.dsService.deleteByIds(Arrays.asList(ids));
			return "1";
		}catch (Exception e) {
			log.error("", e);
			return "0";
		}
	}
	
	/**
	 * 类型改变。
	 * 
	 * @param dbType String
	 * @return FbrpDbtoolDs
	 */
	@ResponseBody
	@RequestMapping(value="dsManage.do",params="typeChanged")
	public FbrpDbtoolDs typeChanged(String dbType){
		List<FbrpCommonCodeValue> dbTypeList = this.codeValueService.getCodeInfoList("db_type");
		Map<String, FbrpDbtoolDs> defaultDsInfo = this.dsService.getDefaultDsVOMap(dbTypeList);
		return defaultDsInfo.get(dbType);
	}
	
}
