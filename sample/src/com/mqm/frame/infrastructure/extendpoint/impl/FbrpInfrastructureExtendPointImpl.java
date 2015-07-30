/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.extendpoint.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.context.support.ApplicationObjectSupport;

import com.mqm.frame.infrastructure.extendpoint.IExtendPoint;
import com.mqm.frame.infrastructure.extendpoint.IServletExtendPoint;
import com.mqm.frame.infrastructure.service.IServletProxyHandler;

/**
 * 
 * <pre>
 * 动态收集已实现的各种扩展点。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@SuppressWarnings("unchecked")
public class FbrpInfrastructureExtendPointImpl extends ApplicationObjectSupport implements
		IExtendPoint {

	/**
	 * 获得HttpServlet定义集合。
	 * 
	 * @return 返回HttpServlet定义集合
	 */
	public Map<String, IServletProxyHandler> getServletProxyHandlerDefinitionsInfo(){
		Map<String, IServletProxyHandler> servletProxyHandlerDefinitionsInfo = new HashMap<String, IServletProxyHandler>();
		
		//获得已注册的各种IServletExtendPoint
		Map servletExtendPointMap =this.getApplicationContext().getBeansOfType(IServletExtendPoint.class);
		Iterator iterator = servletExtendPointMap.entrySet().iterator();

		while(iterator.hasNext()){
			Entry entry = (Map.Entry) iterator.next();
			
			IServletExtendPoint servletExtendPoint = (IServletExtendPoint)entry.getValue();
			if(servletExtendPoint.getServletProxyHandlerDefinitionsInfo() != null 
					&& servletExtendPoint.getServletProxyHandlerDefinitionsInfo().size() != 0){
				servletProxyHandlerDefinitionsInfo.putAll(servletExtendPoint.getServletProxyHandlerDefinitionsInfo());
			}
		}
		
		return servletProxyHandlerDefinitionsInfo;
	}
	
}
