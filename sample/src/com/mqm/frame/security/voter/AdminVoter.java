/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.voter;

import java.util.Collection;

import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;

import com.mqm.frame.security.util.SecurityUtil;

/**
 * 
 * <pre>
 * 管理员投票器。判断登陆用户是否为超级管理员。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class AdminVoter implements AccessDecisionVoter<Object> {

	@Override
	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	@Override
	public boolean supports(Class clazz) {
		return true;
	}

	@Override
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
		if (SecurityUtil.isAdmin()) {
			return ACCESS_GRANTED;
		}
		return ACCESS_ABSTAIN;
	}

}
