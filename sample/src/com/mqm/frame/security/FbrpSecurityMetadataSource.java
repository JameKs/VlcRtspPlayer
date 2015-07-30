/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 
 * <pre>
 * FBRP定义的FilterInvocationSecurityMetadataSource实现类。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpSecurityMetadataSource implements
		FilterInvocationSecurityMetadataSource {

	private static final Log log = LogFactory
			.getLog(FbrpSecurityMetadataSource.class);

	private List<String> uncheckUrlList;
	
	/**
	 * 设置 uncheckUrlList。
	 * 
	 * @param uncheckUrlList 设置 uncheckUrlList。
	 */
	public void setUncheckUrlList(List<String> uncheckUrlList) {
		this.uncheckUrlList = uncheckUrlList;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource#getAttributes
	 * (java.lang.Object)
	 */
	@Override
	public Collection<ConfigAttribute> getAttributes(Object object)
			throws IllegalArgumentException {
		String url = ((FilterInvocation) object).getRequestUrl();

		if (isUncheckResource(url)) {
			log.info("不受保护的URL：" + url);
			return null;
		}

		Collection<ConfigAttribute> confDef = new ArrayList<ConfigAttribute>();
		confDef.add(new SecurityConfig(url));

		HttpServletRequest req = ((FilterInvocation) object).getHttpRequest();
		Map parameterMap = req.getParameterMap();
		for (Iterator it = parameterMap.keySet().iterator(); it.hasNext();) {
			String key = it.next().toString();
			confDef.add(new SecurityConfig(key + "=" + parameterMap.get(key)));
		}
		if(log.isDebugEnabled()){
			log.debug("受保护的URL：" + confDef.toString());
		}
		return confDef;
	}

	private boolean isUncheckResource(String url) {
		for(String uncheckUrl : uncheckUrlList){
			if (url.contains("index.jsf") || (url.startsWith("/"+uncheckUrl.trim())&&url.contains(uncheckUrl.trim()))) {
				return true;
			}
		}
		
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource#getAllConfigAttributes()
	 */
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	/**
	 * 返回配置属性。
	 * 
	 * @return Iterator
	 */
	public Iterator getConfigAttributeDefinitions() {
		return new ArrayList().iterator();
	}

	@Override
	public boolean supports(Class clazz) {
		return clazz.isAssignableFrom(FilterInvocation.class);
	}

}
