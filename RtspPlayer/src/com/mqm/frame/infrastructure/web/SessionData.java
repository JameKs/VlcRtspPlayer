/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.web;

import java.util.HashMap;
import java.util.Map;

import com.mqm.frame.infrastructure.util.UserProfileVO;

/**
 * <pre>
 *      Title: Seesion范围的JSF受管Bean定义。
 *      Description:  Seesion范围的BEAN定义，用来保存一些在不同页面用到的参数或经常会用到的信息。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 *      修改记录
 *      修改后版本:  修改人：guanyuan_gong 修改日期:2010-11-18上午10:08:18修改内容:
 * </pre>
 */
public class SessionData {
	
	private UserProfileVO currentUserProfileVO;

	//页面可显示内容的高度
	private int contentHeight = 0;

	// 可以存放不同页面之间或后台需要引用的参数值对
	private Map<String, Object> paramMap = new HashMap<String, Object>();

	/**
	 * 获取currentUserProfileVO。
	 * 
	 * @return UserProfileVO
	 */
	public UserProfileVO getCurrentUserProfileVO() {
		return currentUserProfileVO;
	}

	/**
	 * 设置currentUserProfileVO。
	 * 
	 * @param currentUserProfileVO UserProfileVO
	 */
	public void setCurrentUserProfileVO(UserProfileVO currentUserProfileVO) {
		this.currentUserProfileVO = currentUserProfileVO;
	}

	/**
	 * 获取paramMap。
	 * 
	 * @return Map<String, Object> 
	 */
	public Map<String, Object> getParamMap() {
		return paramMap;
	}

	/**
	 * 设置paramMap。
	 * 
	 * @param paramMap Map<String, Object> 
	 */
	public void setParamMap(Map<String, Object> paramMap) {
		this.paramMap = paramMap;
	}

	/**
	 * 返回 contentHeight。
	 * 
	 * @return 返回 contentHeight。
	 */
	public int getContentHeight() {
		return contentHeight;
	}

	/**
	 * 设置 contentHeight。
	 * 
	 * @param contentHeight 设置 contentHeight。
	 */
	public void setContentHeight(int contentHeight) {
		this.contentHeight = contentHeight;
	}}
