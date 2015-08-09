/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.extendpoint;

import java.util.List;

import com.mqm.frame.infrastructure.util.UserProfileVO;

/**
 * 
 * <pre>
 * 菜单扩展点。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IMenuExtendPoint extends IExtendPoint{

	/**
	 * 收集菜单资源。
	 * 
	 * @param userProfileVO UserProfileVO
	 * 
	 * @return Map 菜单资源
	 */
	@SuppressWarnings("rawtypes")
	public List getMenuResources(UserProfileVO userProfileVO);
	
	/**
	 * 生成我的收藏夹子菜单。
	 * 
	 * @param parentKey String
	 * @param userProfileVO UserProfileVO
	 * @param mustToMap boolean
	 * 
	 * @return List
	 */
	public Object  getFavoriteItem(String parentKey,UserProfileVO userProfileVO,boolean mustToMap);
	
}
