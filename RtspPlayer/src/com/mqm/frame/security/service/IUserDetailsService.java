/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.util.UserProfileVO;
import com.mqm.frame.security.staff.vo.FbrpSecStaff;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 扩展UserDetailsService接口，用于实现用户认证管理。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IUserDetailsService extends UserDetailsService,IGenericService<FbrpSecStaff>{

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_security_userDetailsService";

	/**
	 * 该方法会在登录成功后执行, 首先查找系统中是否存在ICustomUserDetailsService接口实现,
	 * 若存在此实现则调用实现完成登录成功后的操作, 否则继续进行默认操作
	 * <p/>
	 * 默认操作包括将用户登录时间等属性保存到数据库, 并将用户名等属性写入session以便系统中使用。
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param username String
	 * 
	 * @throws FbrpException 异常
	 */
	public void onSuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, String username)
			throws FbrpException;

	/**
	 * 该方法会在登录失败后执行, 首先查找系统中是否存在ICustomUserDetailsService接口实现,
	 * 若存在此实现则调用实现完成登录成功后的操作, 否则继续进行默认操作
	 * <p/>
	 * 默认操作包括记录登录失败时间以及登录失败次数。
	 * 
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param username String
	 * 
	 * @throws FbrpException 异常
	 */
	public void onUnsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, String username)
			throws FbrpException;

	/**
	 * 根据用户名取得用户userauth对象, 若此用户不存在, 则返回null。
	 * 
	 * @param username String
	 * 
	 * @return 若用户存在返回用户对象, 否则返回null
	 * 
	 * @throws FbrpException 异常
	 */
	public FbrpSecStaff getUserAuthByUsername(String username)
			throws FbrpException;

	/**
	 * 根据Staff查找UserAuth。
	 * 
	 * @param staffId String
	 * 
	 * @return FbrpSecStaff
	 * 
	 * @throws FbrpException 异常
	 */
	public FbrpSecStaff getUserAuthByStaff(String staffId)
			throws FbrpException;

	/**
	 * 修改用户密码。
	 * 
	 * @param loginid String
	 * @param oldPassword  用户输入的老password, 用于确认用户身份
	 * @param newPassword 用户输入的新password
	 * 
	 * @return String
	 * 
	 * @throws FbrpException 异常
	 */
	public String savePassword(String loginid, String oldPassword, String newPassword)
			throws FbrpException;

	/**
	 * 保存用户认证相关信息。
	 * 
	 * @param user  FbrpSecStaff
	 * @param userProfileVO UserProfileVO
	 * 
	 * @return FbrpSecStaff
	 * 
	 * @throws FbrpException 异常
	 */
	public FbrpSecStaff save(FbrpSecStaff user, UserProfileVO userProfileVO)
			throws FbrpException;

	/**
	 * 更新用户认证相关信息。
	 * 
	 * @param user FbrpSecStaff
	 * @param userProfileVO UserProfileVO
	 * 
	 * @return FbrpSecStaff
	 * 
	 * @throws FbrpException 异常
	 */
	public FbrpSecStaff update(FbrpSecStaff user, UserProfileVO userProfileVO)
			throws FbrpException;

	/**
	 * 删除用户认证相关信息。
	 * 
	 * @param user  FbrpSecStaff
	 * 
	 * @return int
	 * 
	 * @throws FbrpException 异常
	 */
	public int delete(FbrpSecStaff user) throws FbrpException;

	/**
	 * 用户退出时，更新用户同时在线的数量。
	 * 
	 * @param login_id String
	 * 
	 * @throws FbrpException 异常
	 */
	public void updateOnlineCount(String login_id) throws FbrpException;

	/**
	 * need。
	 * 
	 * @param s String
	 * 
	 * @return boolean
	 * 
	 * @throws FbrpException 异常
	 */
	public boolean needChangePassword(String s) throws FbrpException;

	/**
	 * 检查帐号是否被锁定。
	 * 
	 * @param user 用户信息
	 * 
	 * @return boolean 用户是否被锁定
	 */
	public boolean checkAccountLock(FbrpSecStaff user);

	/**
	 * 用户校验。
	 * 
	 * @param fbrpSecStaffVO FbrpSecStaff
	 * 
	 * @param oldPassword String
	 * 
	 * @throws FbrpException 异常
	 */
	public void checkUser(FbrpSecStaff fbrpSecStaffVO, String oldPassword) throws FbrpException;
}
