/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * 
 * <pre>
 * 标识各种类型的权限。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class FbrpGrantedAuthority implements GrantedAuthority {
	
	private static final long serialVersionUID = 4373648628714718370L;

	/**
	 * 常量。
	 */
	public final static String MENU_GRANT_TYPE = "1";
	
	/**
	 * 常量。
	 */
	public final static String URL_GRANT_TYPE = "2";
	
	/**
	 * 常量。
	 */
	public final static String RESOURCE_GRANT_TYPE = "3";
	/**
	 * 常量。
	 */
	public final static String FUNCTION_GRANT_TYPE = "4";
	/**
	 * 常量。
	 */
	public final static String ORGRULE_GRANT_TYPE = "5";
		
	//标识权限项，比如菜单ID、URL ID等
	private String id;
	
	//权限类型，比如"menu"菜单
	private String grantType;
	
	@Override
	public String getAuthority() {
		return id + "" + grantType;
	}

	/**
	 * 设置 id。
	 * 
	 * @param id 设置 id。
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * 返回 id。
	 * 
	 * @return 返回 id。
	 */
	public String getId() {
		return id;
	}
	
	/**
	 * 设置 grantType。
	 * 
	 * @param grantType 设置 grantType。
	 */
	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
	/**
	 * 返回 grantType。
	 * 
	 * @return 返回 grantType。
	 */
	public String getGrantType() {
		return grantType;
	}
	
}
