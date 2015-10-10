/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;
import org.springframework.security.core.session.SessionRegistryImpl;

import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.security.service.IUserDetailsService;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 扩展Spring Security内置的SessionRegistryImpl，以完善用户监控。
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
public class UserSessionRegistryImpl extends SessionRegistryImpl {

	private static final Log log = LogFactory
			.getLog(UserSessionRegistryImpl.class);

	private SimpleJdbcTemplate simpleJdbcTemplate;

	@Override
	public void removeSessionInformation(String sessionId) {
		log.info("sessionId:" + sessionId);
		if (sessionId != null && getSessionInformation(sessionId) != null) {
			Object login_id = getSessionInformation(sessionId).getPrincipal();
			if (login_id != null && login_id.toString().trim().length() != 0) {
				IUserDetailsService userDetailsService = (IUserDetailsService) ContextUtil
						.getBean(IUserDetailsService.BEAN_ID);
				try {
					userDetailsService.updateOnlineCount(login_id.toString());
				} catch (FbrpException e) {
					log.error("", e);
				}
			}
		}
		super.removeSessionInformation(sessionId);
	}

	/**
	 * 处理Java EE容器重启造成的数据不准确，比如部分HttpSession没有被销毁时，Java EE容器被停掉了。
	 */
	public void resetLoginAccount() {
		this.simpleJdbcTemplate
				.update("update fbrp_sec_staff set online_count=0");
		log.info("应用启动,重置用户在线数量.....");
	}

	/**
	 * 设置 simpleJdbcTemplate。
	 * 
	 * @param simpleJdbcTemplate 设置 simpleJdbcTemplate。
	 */
	public void setSimpleJdbcTemplate(SimpleJdbcTemplate simpleJdbcTemplate) {
		this.simpleJdbcTemplate = simpleJdbcTemplate;
	}
	
}
