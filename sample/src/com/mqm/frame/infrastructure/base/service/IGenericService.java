/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.base.service;

import java.util.List;

import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.service.IDefaultService;
import com.mqm.frame.infrastructure.util.PagedResult;

/**
 * 
 * <pre>
 * 泛型服务层接口。
 * </pre>
 * 
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 * @param <T>
 */
public interface IGenericService<T> extends IDefaultService {
	
	/**
	 * 创建实体。
	 * 
	 * @param t
	 *            实体类。
	 */
	public void create(T t);

	/**
	 * 创建实体对象。
	 * @return 实体类对象。
	 */
	public T newValueObject();
	
	/**
	 * 更新实体。
	 * 
	 * @param t
	 *            实体类。
	 */
	public void update(T t);

	/**
	 * 更新实体，允许实体的某个属性为NULL。
	 * 
	 * @param t
	 *            实体类。
	 */
	public void updateAllowNull(T t);

	/**
	 * 创建或更新实体。
	 * 
	 * @param t
	 *            实体类。
	 */
	public void createOrUpdate(T t);

	/**
	 * 删除实体。
	 * 
	 * @param t
	 *            实体类
	 * 
	 * @return 实体类id
	 */
	public int delete(T t);

	/**
	 * 根据实体类的id号删除实体。
	 * 
	 * @param id
	 *            实体类的id
	 * @return 实体类id
	 */
	public int delete(String id);

	/**
	 * 根据实体类的列表批量删除实体类。
	 * 
	 * @param list
	 *            实体类列表
	 * 
	 * @return 删除的个数
	 */
	//TODO luxiaocheng 考虑统一批最操作命名，改为batch开头
	public int deleteAll(List<T> list);

	/**
	 * 根据实体类id的列表批量删除实体类。
	 * 
	 * @param ids
	 *            实体类id的列表
	 * 
	 * @return 删除的个数
	 */
	//TODO luxiaocheng 考虑统一批最操作命名，改为batch开头
	public int deleteByIds(List<String> ids);

	/**
	 * 根据所提供的PMap查询条件，删除符合条件的实体。
	 * 
	 * @param pm PMap
	 * 
	 * @return 删除的个数
	 */
	public int deleteBy(PMap pm);

	/**
	 * 更具所提供的一个查询条件，删除符合条件的实体。
	 * 
	 * @param property
	 *            属性名
	 * @param value
	 *            属性值
	 * 
	 * @return 删除的个数
	 */
	public int deleteBy(String property, Object value);

	// =============================================================================

	/**
	 * 根据id查找实体类。
	 * 
	 * @param id
	 *            String
	 * @return 实体类
	 */
	public T find(String id);

	/**
	 * 根据实体类ids列表查找符合条件的实体类。
	 * 
	 * @param ids
	 *            List<String>
	 * @param orders
	 *            Order
	 *            
	 * @return List
	 */
	public List<T> findByIds(List<String> ids, Order... orders);

	/**
	 * 根据条件查找实体类的列表。
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            属性值
	 * @param orders
	 *            排序
	 * @return List<T>
	 */
	public List<T> findBy(String property, Object value, Order... orders);

	/**
	 * 根据条件分页查找实体类的列表。
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            属性值
	 * @param pageIndex
	 *            页面索引
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            排序
	 * @return List<T>
	 */
	public List<T> findBy(String property, Object value, int pageIndex,
			int pageSize, Order... orders);

	/**
	 * 根据查询条件PMap查询实体类信息并作排序。
	 * 
	 * @param pm
	 *            PMap
	 * @param orders
	 *            Order
	 * @return List<T>
	 */
	public List<T> findBy(PMap pm, Order... orders);

	/**
	 * 根据查询条件PMap分页查询实体类信息并作排序。
	 * 
	 * @param pm
	 *            PMap
	 * @param pageIndex
	 *            int
	 * @param pageSize
	 *            int
	 * @param orders
	 *            Order
	 * @return List<T>
	 */
	public List<T> findBy(PMap pm, int pageIndex, int pageSize, Order... orders);

	/**
	 * 根据查询条件查询某个实体类，并作排序。
	 * 
	 * @param name
	 *            属性
	 * @param value
	 *            属性值
	 * @param orders
	 *            排序
	 * @return T
	 */
	public T findFirstBy(String name, Object value, Order... orders);

	/**
	 * 根据PMap查询条件查询某个实体类，并作排序。
	 * 
	 * @param pm
	 *            PMap
	 * @param orders
	 *            排序
	 * 
	 * @return T
	 */
	public T findFirstBy(PMap pm, Order... orders);

	// =============================================================================
	/**
	 * 列出某个实体类在数据库中的所有记录。
	 * 
	 * @param orders
	 *            排序
	 * 
	 * @return List<T>
	 */
	public List<T> list(Order... orders);

	/**
	 * 分页列出某个实体类在数据库中的所有记录。
	 * 
	 * @param pageIndex
	 *            页面索引
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            排序
	 * 
	 * @return List<T>
	 */
	public List<T> list(int pageIndex, int pageSize, Order... orders);

	// =============================================================================
	/**
	 * 统计实体类在数据库中的记录数目。
	 * 
	 * @return long
	 */
	public long count();

	/**
	 * 根据条件统计实体类在数据库中的记录数目。
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            属性值
	 * 
	 * @return long
	 */
	public long count(String property, Object value);

	/**
	 * 根据PMap条件统计实体类在数据库中的记录数目。
	 * 
	 * @param pm
	 *            PMap
	 * 
	 * @return long
	 */
	public long count(PMap pm);

	// =============================================================================
	/**
	 * 根据查询条件，分页查找实体类信息，并作排序操作。
	 * 
	 * @param property
	 *            属性
	 * @param value
	 *            属性值
	 * @param pageIndex
	 *            页面索引
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            排序
	 * 
	 * @return PagedResult<T>
	 */
	public PagedResult<T> pagedQuery(String property, Object value,
			int pageIndex, int pageSize, Order... orders);

	/**
	 * 根据PMap查询条件，分页查找实体类信息，并作排序操作。
	 * 
	 * @param pm
	 *            PMap
	 * @param pageIndex
	 *            页面索引
	 * @param pageSize
	 *            页面大小
	 * @param orders
	 *            排序
	 * 
	 * @return PagedResult<T>
	 */
	public PagedResult<T> pagedQuery(PMap pm, int pageIndex, int pageSize,
			Order... orders);

	// =============================================================================
	/**
	 * 批量插入数据。
	 * 
	 * @param list
	 *            List<T>
	 */
	public void batchCreate(List<T> list);

	/**
	 * 批量插入数据，指定刷新频率。
	 * 
	 * @param list
	 *            List<T>
	 * @param frequency
	 *            int
	 */
	public void batchCreate(List<T> list, int frequency);

	/**
	 * 批量更新数据。
	 * 
	 * @param list
	 *            List<T>
	 */
	public void batchUpdate(List<T> list);

	/**
	 * 批量更新数据，并指定刷新频率。
	 * 
	 * @param list
	 *            List<T>
	 * @param frequency
	 *            int
	 */
	public void batchUpdate(List<T> list, int frequency);

	// =============================================================================
	/**
	 * 检查是否存在。
	 * 
	 * @param t
	 *            T
	 * 
	 * @return boolean
	 */
	public boolean checkExist(T t);

	/**
	 * 检查是否存在。
	 * 
	 * @param prop String
	 * 
	 * @param value Object
	 * 
	 * @param filterId String
	 * 
	 * @return boolean
	 */
	public boolean checkExist(String prop,Object value,String filterId);

	/**
	 * 检查是否存在。
	 * 
	 * @param prop String
	 * 
	 * @param value Object
	 * 
	 * @return boolean
	 */
	public boolean checkExist(String prop,Object value);
}