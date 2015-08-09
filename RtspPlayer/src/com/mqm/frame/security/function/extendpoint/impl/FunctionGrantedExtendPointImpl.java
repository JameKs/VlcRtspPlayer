/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.function.extendpoint.impl;

import java.util.List;

import com.mqm.frame.security.acl.extendpoint.IGrantShowShape;
import com.mqm.frame.security.acl.extendpoint.impl.GrantGrid;
import com.mqm.frame.security.function.service.IFunctionService;
import com.mqm.frame.security.function.vo.FbrpSecResFunction;

/**
 * <pre>
 * 操作数据权限扩展点实现。
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
public class FunctionGrantedExtendPointImpl {

	private IFunctionService functionService;

	/**
	 * 获取所有操作数据权限扩展点。
	 * 
	 * @return List
	 */
	public List getAll() {
		return this.functionService.getAllFunctions();
	}
	
	/**
	 * 根据ID来获取对象。
	 * 
	 * @param id String
	 * 
	 * @return Object
	 */
	public Object getObjectById(String id) {
		return this.functionService.getFunctionById(id);
	}

	/**
	 * 返回数据权限的页面授权的显示形式的接口。
	 *
	 * @return IGrantShowShape
	 */
	public IGrantShowShape getGrantShowShape() {
		IGrantShowShape grantShowShape = new GrantGrid(FbrpSecResFunction.class);
		return grantShowShape;
	}

	/**
	 * 设置IFunctionService。
	 * 
	 * @param functionService IFunctionService
	 */
	public void setFunctionService(IFunctionService functionService) {
		this.functionService = functionService;
	}

}
