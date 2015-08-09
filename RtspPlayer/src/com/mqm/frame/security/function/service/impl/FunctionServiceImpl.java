/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.function.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.mqm.frame.common.auditlog.service.IAuditLogService;
import com.mqm.frame.common.cache.service.IDataCacheService;
import com.mqm.frame.infrastructure.base.service.MyBatisServiceImpl;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.security.acl.service.IGrantService;
import com.mqm.frame.security.function.service.IFunctionService;
import com.mqm.frame.security.function.vo.FbrpSecResFunction;
import com.mqm.frame.security.role.vo.FbrpSecRole;
import com.mqm.frame.util.constants.BaseConstants;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 功能性资源操作服务实现类。
 * </pre>
 * 
 * @author linjunxiong linjunxiong@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class FunctionServiceImpl extends MyBatisServiceImpl<FbrpSecResFunction>
		implements IFunctionService {
	private IAuditLogService logService;

	private IDataCacheService dataCacheService;

	private IGrantService grantService;

	/**
	 * 初始化。
	 */
	public void init() {

		/*
		 * List<FbrpSecResFunctionVO> fbrpSecResFunctionVOList = this
		 * .getHibernateTemplate().loadAll(FbrpSecResFunctionVO.class);
		 */
		List<FbrpSecResFunction> fbrpSecResFunctionVOList = this.list();
		Map<String, FbrpSecResFunction> fbrpSecResFunctionMap = new HashMap<String, FbrpSecResFunction>();
		for (FbrpSecResFunction fbrpSecResFunctionVO : fbrpSecResFunctionVOList) {
			fbrpSecResFunctionMap.put(fbrpSecResFunctionVO.getId(),
					fbrpSecResFunctionVO);
		}
		this.dataCacheService.putDataToCache(
				FunctionServiceImpl.class.getName(),
				(Serializable) fbrpSecResFunctionMap);
	}

	@Override
	public FbrpSecResFunction getFunctionById(String id) throws FbrpException {
		return this.find(id);

		/*
		 * return (FbrpSecResFunctionVO) this.getHibernateTemplate().get(
		 * FbrpSecResFunctionVO.class, id);
		 */
	}

	@Override
	public int getAllFunctionCount() throws FbrpException {

		/*
		 * List retInt = this.getHibernateTemplate().find(
		 * "select count(id) from FbrpSecResFunctionVO");
		 * 
		 * if (retInt != null && !retInt.isEmpty()) { Map map = (Map)
		 * retInt.get(0); Object obj = map.get("count(id)");
		 * java.math.BigDecimal bd = (java.math.BigDecimal) obj; return
		 * bd.intValue(); }
		 */

		Long count = this.count();
		if (count != null) {
			return count.intValue();
		}
		return 0;
	}

	@Override
	public List getAllFunctions() throws FbrpException {
		/*
		 * return this.getHibernateTemplate().find(
		 * "from FbrpSecResFunctionVO");
		 */
		return this.list();
	}

	@Override
	public FbrpSecResFunction getFuncByFuncCode(String funcCode)
			throws FbrpException {
		/*
		 * List list = this.getHibernateTemplate().find(
		 * "from FbrpSecResFunctionVO where code=?", funcCode); if (list != null
		 * && list.size() > 0) { return (FbrpSecResFunctionVO) list.get(0); }
		 * return null;
		 */
		FbrpSecResFunction vo = this.findFirstBy("code", funcCode);
		return vo;
	}

	// TODO ljx-lxc FbrpSecGrantVO 找不到
	@Override
	public boolean hasRelation(String funcCode) throws FbrpException {
		boolean result = false;
		/*
		 * List grantList = this.getHibernateTemplate().find(
		 * "from FbrpSecGrantVO where resourceId =? and resourceType = ?",
		 * funcCode, FbrpSecResFunctionVO.class.getName());
		 */

		PMap pm = this.newMapInstance();
		pm.eq("resourceId", funcCode);
		pm.eq("resourceType", FbrpSecResFunction.class.getName());
		List<FbrpSecResFunction> grantList = this.findBy(pm);
		if (grantList.size() > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public void batchCommit(List insertBeans, List updateBeans, List delBeans)
			throws FbrpException {

		// 从缓存中取得所有功能
		Map<String, FbrpSecResFunction> fbrpSecResFunctionMap = (Map<String, FbrpSecResFunction>) this.dataCacheService
				.getDataFromCache(FunctionServiceImpl.class.getName());

		if (insertBeans != null) {
			for (Iterator it = insertBeans.iterator(); it.hasNext();) {
				FbrpSecResFunction vo = (FbrpSecResFunction) it.next();
				checkUrl(vo);
				// vo.setId(this.getKeyGen().getUUIDKey(vo));
				// this.getHibernateTemplate().save(vo);
				this.create(vo);
				grantService.createAcl(vo);
				// 获取应用管理员的角色
				FbrpSecRole role = this.grantService.getAppAdminRole();
				// 给应用管理员授权
				this.grantService.insertGrant(vo,
						BaseConstants.GRANT_TYPE_ADMIN, role.getId());
				// 添加到缓存中
				fbrpSecResFunctionMap.put(vo.getId(), vo);
			}
		}
		if (updateBeans != null) {
			for (Iterator it = updateBeans.iterator(); it.hasNext();) {
				FbrpSecResFunction vo = (FbrpSecResFunction) it.next();
				checkUrl(vo);
				// update_Hibernate_Object(vo);

				// 删除对Desc,Name的空字符串判断,增加null处理。将描述改为空时也能修改成功，并且避免页面出现null
				// 郝清山 2010-9-27
				/*
				 * String funcNameSql = ""; if (vo.getName() == null) {
				 * vo.setName(""); } funcNameSql = ",NAME='" + vo.getName() +
				 * "'"; if (vo.getRemark() == null) { vo.setRemark(""); } String
				 * funcDescSql = ",REMARK='" + vo.getRemark() + "'";
				 * 
				 * // 表名 “FBRP_SEC_RES_FUNCTION” 错写成 ”IP_RES_FUNCTION“ 少写了开头字母B
				 * // 。-----导致无法修改记录。 String updateSql =
				 * "update FBRP_SEC_RES_FUNCTION  set CODE  ='" + vo.getCode() +
				 * "'" + funcNameSql + funcDescSql + " where ID='" + vo.getId()
				 * + "' ";
				 * 
				 * this.getSimpleJdbcTemplate().update(updateSql);
				 */
				this.update(vo);
				// 更新到缓存
				fbrpSecResFunctionMap.put(vo.getId(), vo);
			}
		}

		if (delBeans != null) {
			// String delsql = "DELETE from FbrpSecResFunctionVO where id=?";
			for (Iterator it = delBeans.iterator(); it.hasNext();) {
				FbrpSecResFunction vo = (FbrpSecResFunction) it.next();
				checkUrl(vo);
				// this.getHibernateTemplate().bulkUpdate(delsql, vo.getId());
				this.delete(vo.getId());
				grantService.deleteAcl(vo);
				// 从缓存中删除
				fbrpSecResFunctionMap.remove(vo.getId());
			}
		}

	}

	private void checkUrl(FbrpSecResFunction vo) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.mqm.frame.security.function.service.IFunctionService#getAllByParam
	 * (com.mqm.frame.security.function.vo.FbrpSecResFunctionVO)
	 */
	@Override
	public Object[] getAllByParam(FbrpSecResFunction functionvo, int offSet,
			int pageSize) {
		/*
		 * HqlWrapper hqlWrapper = new HqlWrapper(); hqlWrapper.setHql("from " +
		 * FbrpSecResFunctionVO.class.getName()); if (functionvo != null) {
		 * hqlWrapper.setConditionIncludeIgnoreCase("code",
		 * functionvo.getCode());
		 * hqlWrapper.setConditionIncludeIgnoreCase("name",
		 * functionvo.getName());
		 * hqlWrapper.setConditionIncludeIgnoreCase("remark",
		 * functionvo.getRemark()); }
		 * 
		 * Object[] objectArray = this.find_Hibernate_ComposedHQL(hqlWrapper,
		 * offSet, pageSize);
		 */
		PMap pm = this.newMapInstance();
		if (functionvo != null) {
			pm.includeIgnoreCase("code", functionvo.getCode());
			pm.includeIgnoreCase("name", functionvo.getName());
			pm.includeIgnoreCase("remark", functionvo.getRemark());
		}
		PagedResult<FbrpSecResFunction> pagedQuery = this.pagedQuery(pm,
				offSet, pageSize);
		Long size = pagedQuery.getTotal();
		Object[] objectArray = { pagedQuery.getData(), size.intValue() };
		return objectArray;
	}

	/**
	 * 设置dataCacheService。
	 * 
	 * @param dataCacheService
	 *            IDataCacheService
	 */
	public void setDataCacheService(IDataCacheService dataCacheService) {
		this.dataCacheService = dataCacheService;
	}

	/**
	 * 获取logService。
	 * 
	 * @return IAuditLogService
	 */
	public IAuditLogService getLogService() {
		return logService;
	}

	/**
	 * 设置logService。
	 * 
	 * @param logService
	 *            IAuditLogService
	 */
	public void setLogService(IAuditLogService logService) {
		this.logService = logService;
	}

	/**
	 * 设置grantService。
	 * 
	 * @param grantService
	 *            IGrantService
	 */
	public void setGrantService(IGrantService grantService) {
		this.grantService = grantService;
	}

	@Override
	public PagedResult<FbrpSecResFunction> pagedQuery(FbrpSecResFunction vo,
			int pageIndex, int pageSize, Order... orders) {
		
		return super.pagedQuery(toMap(vo), pageIndex, pageSize, orders);
	} 
	
	private PMap toMap(FbrpSecResFunction vo) {
		PMap pm = this.newMapInstance();
		pm.includeIgnoreCase("name", vo.getName());
		pm.includeIgnoreCase("code", vo.getCode());
		pm.includeIgnoreCase("remark", vo.getRemark());
		return pm;
	}

}
