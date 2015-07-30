/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.extendpoint;

import java.util.Map;

import com.mqm.frame.infrastructure.service.IServletProxyHandler;

/**
 * <pre>
 * Title:用于模块化Web资源的扩展点。
 * Description: 希望同一web.xml尽可能支持所有的BI产品。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IServletExtendPoint extends IExtendPoint {
	
	/**
	 * 获得IServletProxyHandler定义集合	。
	 * 
	 * @return 返回IServletProxyHandler定义集合
	 */
	public Map<String, IServletProxyHandler> getServletProxyHandlerDefinitionsInfo();

}
