/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.acl.extendpoint;

import java.util.List;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * <pre>
 * 数据权限的页面授权的显示形式的接口。
 * </pre>
 * 
 * @author luoweihong luoweihong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface IGrantShowShape {

	/**
	 * 初始化。
	 * 
	 * @param principalids List
	 * @param isEdit  boolean
	 */
	public void init(List principalids,boolean isEdit);

	/**
	 * 更新Granted。
	 */
	public void updateGranted();
	
	/**
	 * 更新Granted。
	 * 
	 * @param roleId String
	 * 
	 * @param ids 需要更新的列表。
	 * 
	 * @return int
	 */
	public int updateGranted(String roleId,String[] ids);

	/**
	 * 获取type。
	 * 
	 * @return String
	 */
	public String getType();

	/**
	 * 设置GrantExtendpoint。
	 * 
	 * @param extendPoint IGrantExtendPoint
	 * @param isEdit boolean
	 */
	public void setGrantExtendpoint(IGrantExtendPoint extendPoint,boolean isEdit);

	/**
	 * 获取tGrantExtendPoint。
	 * 
	 * @return IGrantExtendPoint
	 */
	public IGrantExtendPoint getGrantExtendPoint();

	/**
	 * 重置。
	 */
	public void reset();
	
	/**
	 * 获取返回结果。
	 * 
	 * @return Object[] 
	 */
	public Object[] getResult();
	
	/**
	 * 获取ModelClass。
	 * 
	 * @return Class<? extends ValueObject>
	 */
	public Class<? extends ValueObject> getModelClass();
}
