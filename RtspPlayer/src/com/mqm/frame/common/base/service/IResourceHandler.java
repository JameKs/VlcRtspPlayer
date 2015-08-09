/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.base.service;

import java.util.List;

import com.mqm.frame.util.exception.FbrpException;

/**
 * <pre>
 * 处理资源调用请求。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface IResourceHandler {

	/**
	 * 资源类型。
	 * 
	 * @return String 资源类型
	 */
	public String getResourceTypeID();

	// 目标资源VO 类名
	//public String getVoClassTypeName();

	/**
	 * 获取到资源列表。
	 * 
	 * @return List对象中是resourceVO
	 * 
	 * @throws FbrpException FBRP异常。
	 */
	public List getResList() throws FbrpException;

	/**
	 * 根据ID获取到对象。
	 * 
	 * @param resID
	 *            资源ID
	 * 
	 * @return 获取到的资源对象
	 * 
	 * @throws FbrpException FBRP异常。
	 */
	public Object getResourceByID(String resID) throws FbrpException;

	//public abstract Object convertFromRealVO(ValueObject vo);

}
