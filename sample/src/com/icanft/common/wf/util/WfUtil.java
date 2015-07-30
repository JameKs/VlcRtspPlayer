/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.util;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

/**
 * <pre>
 * 工作流程中审批共用工具类。
 * </pre>
 * @author zengziwen  zengziwen@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class WfUtil {
	
	/**
	 * 从HttpServletRequest中取值以"p_"开头的参数名称，然后将参数值构建到ModelMap中。
	 * 
	 * @param request HttpServletRequest
	 * @param map ModelMap
	 */
	public final static void buildParams(HttpServletRequest request,ModelMap map){
		Enumeration<?> enParamsNm = request.getParameterNames();
		while(enParamsNm.hasMoreElements()){
			Object element = enParamsNm.nextElement();
			if(element!=null&&element.toString().startsWith("p_")){
				map.put(element.toString(), request.getParameter(element.toString()));
			}
		}
	}
		
	/**
	 * 从HttpServletRequest中取值以"_VAR_"开头的参数名称，然后将参数值构建到variable中。
	 * 
	 * @param request HttpServletRequest
	 * @param variable Map<String,Object>
	 */
	public final static void buildWorkflowVariable(HttpServletRequest request,Map<String,Object> variable){
		Enumeration<?> enParamsNm = request.getParameterNames();
		while(enParamsNm.hasMoreElements()){
			Object element = enParamsNm.nextElement();
			if(element!=null&&element.toString().startsWith("_VAR_")){
				variable.put(element.toString(), request.getParameter(element.toString()));
			}
		}
	}
	
}
