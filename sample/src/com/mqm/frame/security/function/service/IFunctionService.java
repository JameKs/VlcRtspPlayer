/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.function.service;

import java.util.List;

import com.mqm.frame.infrastructure.base.service.IGenericService;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.service.IFunctionFilter;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.security.function.vo.FbrpSecResFunction;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 功能性资源操作接口。
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
public interface IFunctionService extends IFunctionFilter,
		IGenericService<FbrpSecResFunction> {

	/**
	 * Spring受管Bean ID。
	 */
	public static final String BEAN_ID = "fbrp_security_functionAuthService";

	/**
	 * 返回功能。
	 * 
	 * @param id
	 *            String
	 * 
	 * @return FbrpSecResFunction
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public FbrpSecResFunction getFunctionById(String id) throws FbrpException;

	/**
	 * 获得所有功能型资源。
	 * 
	 * @return 集合类型，集合中存放返回对象
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public List getAllFunctions() throws FbrpException;

	/**
	 * 获得所有功能型资源数量。
	 * 
	 * @return 返回结果数据
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public int getAllFunctionCount() throws FbrpException;

	/**
	 * 批量提交保存,处理批量新加,修改,删除操作.
	 * 
	 * @param insertBeans
	 *            List
	 * @param updateBeans
	 *            List
	 * @param delBeans
	 *            List
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public void batchCommit(List insertBeans, List updateBeans, List delBeans)
			throws FbrpException;

	/**
	 * 查询是否有和urlCode相关的数据。
	 * 
	 * @param funcCode
	 *            String
	 * 
	 * @return boolean
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public boolean hasRelation(String funcCode) throws FbrpException;

	/**
	 * 按funcCode查询相关的数据。
	 * 
	 * @param funcCode
	 *            String
	 * 
	 * @return FbrpSecResFunction
	 * 
	 * @throws FbrpException
	 *             FBRP异常
	 */
	public FbrpSecResFunction getFuncByFuncCode(String funcCode)
			throws FbrpException;

	/**
	 * 查询。
	 * 
	 * @param functionvo
	 *            FbrpSecResFunction
	 * @param offSet
	 *            offset
	 * @param pageSize
	 *            每页大小
	 * @return Object[]
	 */
	public Object[] getAllByParam(FbrpSecResFunction functionvo, int offSet,
			int pageSize);
	
	/**
	 * 根据提供的vo,查询出符合条件的列表信息。
	 * 
	 * @param vo FbrpSecResFunction
	 * @param pageIndex int
	 * @param pageSize int
	 * @param orders Order
	 * 
	 * @return  PagedResult<FbrpInfraUrl>
	 */
	public PagedResult<FbrpSecResFunction> pagedQuery(FbrpSecResFunction vo, int pageIndex,
			int pageSize, Order... orders);

}