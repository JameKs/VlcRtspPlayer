/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mqm.frame.infrastructure.util.ContextUtil;
import com.mqm.frame.security.QmUserDetails;
import com.mqm.frame.security.role.service.IRoleService;
import com.mqm.frame.sys.user.service.IUserService;
import com.mqm.frame.sys.user.vo.User;
import com.mqm.frame.util.constants.BaseConstants;

/**
 * 
 * <pre>
 * UserDetailsService接口实现，用于实现用户认证管理。
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
public final class UserDetailsServiceImpl implements UserDetailsService {

	private static final Log log = LogFactory
			.getLog(UserDetailsServiceImpl.class);

	private IUserService userService;
	private IRoleService roleService;

	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException, DataAccessException {
	
		User userVO = userService.findByLoginId(username);//此处的username就是loginId

		// 获取当前用户所有所属角色集合
		List roles = null;//roleService.findByUserLoginId(userVO.getLoginId());
		ContextUtil.put(BaseConstants.USER_PROFILE, userVO,ContextUtil.SCOPE_SESSION);
		
		QmUserDetails userDetails = new QmUserDetails();
		userDetails.setUsername(username);
		userDetails.setPassword(userVO.getPassword());
		userDetails.setAccountNonExpired(!checkAccountExpire(userVO));
		userDetails.setAccountNonLocked(!checkAccountLock(userVO));
		userDetails.setEnabled(checkEnabled(userVO));

		userDetails.setAuthorities(roles);

		return userDetails;
	}

	private boolean checkEnabled(User user) {
		return true;//User.DEL_FLAG_NORMAL.equals(user.getDelFlag());
	}

	/**
	 * 根据密码出错次数确定账户是否被锁定, 若超过最大出错次数则账户锁定。
	 * 若user的getMaxFailedLoginCount为空则取系统安全策略中的最大出错次数。
	 * 
	 * @param user FbrpSecStaff
	 * 
	 * @return boolean
	 */
	public boolean checkAccountLock(User user) {
		if (user.getFailedLoginCount() == null) {
			return false;
		}

		if (user.getMaxFailedLoginCount() != null) {
			return user.getFailedLoginCount().intValue() >= user
					.getMaxFailedLoginCount().intValue();
		}

		return user.getFailedLoginCount().intValue() > 10;
	}

	/**
	 * 根据accountExpireTime字段与当前系统时间比较, 若在当前时间之前则账户过期, 返回true, 否则返回false
	 * 若accountExpireTime字段为赋值默认账户没有过期时间。
	 * 
	 * @param user FbrpSecStaff
	 * 
	 * @return boolean
	 */
	private boolean checkAccountExpire(User user) {
		if (user.getPasswdExpireTime() != null) {
			// getServerDateTime抛出异常时需要后续处理而不应该直接消化
			try {
				return user.getPasswdExpireTime().before(
						new Date());
			} catch (Exception e) {
				log.error("", e);
				return false;
			}
		}
		return false;
	}

	protected boolean getNonLocked(Map user) throws DataAccessException {
		int failedLoginCount = Integer.parseInt(user.get("FAILEDLOGINCOUNT")
				.toString());
		if (failedLoginCount < 5) {
			return true;
		}
		return false;
	}

	/**
	 * @return the userService
	 */
	public IUserService getUserService() {
		return userService;
	}

	/**
	 * @param userService the userService to set
	 */
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	/**
	 * @return the roleService
	 */
	public IRoleService getRoleService() {
		return roleService;
	}

	/**
	 * @param roleService the roleService to set
	 */
	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
	
	

}
