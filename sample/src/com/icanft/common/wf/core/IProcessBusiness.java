/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.core;

import javax.servlet.http.HttpServletRequest;

import com.icanft.common.wf.util.ProcessMessage;

/**
 * <pre>
 * 流程业务处理接口。
 * </pre>
 * @author zengziwen  zengziwen@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IProcessBusiness{
	
	/**
	 * 业务保存处理方法。
	 * @param request　HttpServletRequest
	 * @param params　ProcessMessage
	 * @throws Exception
	 */
	public  void saveBusiness(HttpServletRequest request,ProcessMessage  params) throws Exception;

}
