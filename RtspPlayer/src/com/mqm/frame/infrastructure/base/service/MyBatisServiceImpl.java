/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.base.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.infrastructure.persistence.Order;
import com.mqm.frame.infrastructure.persistence.PMap;
import com.mqm.frame.infrastructure.persistence.query.QueryBuilder;
import com.mqm.frame.infrastructure.service.impl.DefaultServiceImpl;
import com.mqm.frame.infrastructure.util.PagedResult;
import com.mqm.frame.util.exception.FbrpException;

/**
 * 
 * <pre>
 * 针对MyBatis提供的各种常用API。
 * </pre>
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class MyBatisServiceImpl<T extends ValueObject> extends DefaultServiceImpl implements IGenericService<T>,InitializingBean{
	
	private Class<T> clazz;
	private EntityExecutor<T> executor;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		this.initNamespace();
		this.executor = this.getEntityExecutor(clazz);
	}
	
	
	//CRUD接口=========================================================================
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#create(T)
	 */
	@Override
	public void create(T t){
		this.crjDmAndCrsjForCreate(t);
		this.executor.create(t);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#update(T)
	 */
	@Override
	public void update(T t){
		this.crjDmAndCrsjForUpdate(t);
		this.executor.update(t);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#updateAllowNull(T)
	 */
	@Override
	public void updateAllowNull(T t){
		this.executor.updateAllowNull(t);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#createOrUpdate(T)
	 */
	@Override
	public void createOrUpdate(T t){
		if (StringUtils.hasText(t.getId())) {
			this.crjDmAndCrsjForUpdate(t);
		} else {
			this.crjDmAndCrsjForCreate(t);
		}
		this.executor.createOrUpdate(t);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#delete(T)
	 */
	@Override
	public int delete(T t){
		return this.executor.delete(t);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#delete(java.lang.String)
	 */
	@Override
	public int delete(String id){
		return this.executor.delete(id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#deleteBy( com.mqm.frame.infrastructure.persistence.PMap)
	 */
	@Override
	public int deleteBy(PMap pm){
		return this.executor.deleteBy( pm);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#deleteBy(java.lang.String,java.lang.Object)
	 */
	@Override
	public int deleteBy(String property,Object value){
		return this.executor.deleteBy( property, value);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#deleteAll(java.util.List)
	 */
	@Override
	public int deleteAll(List<T> list){
		return this.executor.deleteAll(list);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#deleteByIds(java.util.List)
	 */
	@Override
	public int deleteByIds(List<String> ids){
		return this.executor.deleteByIds(ids);
	}
	
	//Find接口集=========================================================================
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#find(java.lang.String)
	 */
	@Override
	public T find(String id){
		return this.executor.find(id);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#findByIds(java.util.List,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public List<T> findByIds(List<String> ids,Order ...orders){
		return this.executor.findByIds( ids, orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#findBy
	 * (java.lang.String,java.lang.Object,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public List<T> findBy(String property ,Object value ,Order ...orders){
		return this.executor.findBy( property, value, orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#findBy
	 * (java.lang.String,java.lang.Object,int,int,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public List<T> findBy(String property ,Object value ,int pageIndex,int pageSize,Order ...orders){
		return this.executor.findBy( property, value, pageIndex, pageSize, orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#findBy
	 * (com.mqm.frame.infrastructure.persistence.PMap,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public List<T> findBy(PMap pm,Order ...orders){
		return this.executor.findBy( pm, orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#findBy
	 * (com.mqm.frame.infrastructure.persistence.PMap,int,int,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public List<T> findBy(PMap pm,int pageIndex,int pageSize,Order ...orders){
		return this.executor.findBy( pm, pageIndex, pageSize, orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#findFirstBy
	 * (java.lang.String,java.lang.Object,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public T findFirstBy(String name, Object value,Order ...orders){
		return this.executor.findFirstBy( name, value, orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#findFirstBy
	 * (com.mqm.frame.infrastructure.persistence.PMap,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public T findFirstBy(PMap pm,Order ...orders){
		return this.executor.findFirstBy( pm, orders);
	}
	
	// =============================================================================
	
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#list(com.mqm.frame.infrastructure.persistence.Order) 
	 */
	@Override
	public List<T> list(Order... orders) {
		return this.executor.list( orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#list(int , int,com.mqm.frame.infrastructure.persistence.Order)
	 */
	@Override
	public List<T> list(int pageIndex, int pageSize, Order... orders) {
		return this.executor.list( pageIndex, pageSize, orders);
	}

	// =============================================================================
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#count()
	 */
	@Override
	public long count() {
		return this.executor.count();
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#count(PMap pm)
	 */
	@Override
	public long count(PMap pm) {
		return this.executor.count( pm);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#count(String property, Object value)
	 */
	@Override
	public long count(String property, Object value) {
		return this.executor.count( property, value);
	}
	
	// =============================================================================
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#pagedQuery(String property, Object value, int pageIndex, int pageSize,
			Order... orders)
	 */
	@Override
	public PagedResult<T> pagedQuery(String property, Object value, int pageIndex, int pageSize,
			Order... orders) {
		return this.executor.pagedQuery( property, value, pageIndex, pageSize, orders);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#pagedQuery(PMap pm, int pageIndex, int pageSize,Order ...orders)
	 */
	@Override
	public PagedResult<T> pagedQuery(PMap pm, int pageIndex, int pageSize,Order ...orders){
		return this.executor.pagedQuery( pm, pageIndex, pageSize, orders);
	}

	//===========================================================================
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#batchCreate(List<T> list)
	 */	
	@Override
	public void batchCreate(List<T> list){
		this.executor.batchCreate( list);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#batchCreate(List<T> list,int frequency)
	 */	
	@Override
	public void batchCreate(List<T> list,int frequency){
		this.executor.batchCreate( list, frequency);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#batchUpdate(List<T> list)
	 */	
	@Override
	public void batchUpdate(List<T> list){
		this.executor.batchUpdate( list);
	}

	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#batchUpdate(List<T> list,int frequency)
	 */
	@Override
	public void batchUpdate(List<T> list,int frequency){
		this.executor.batchUpdate( list, frequency);
	}

	//====================================================================================
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#checkExist(T t)
	 */
	@Override
	public boolean checkExist(T t){
		return this.executor.checkExist(t);
	}
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#checkExist(String prop,Object value,String filterId)
	 */
	@Override
	public boolean checkExist(String prop,Object value,String filterId){
		return this.executor.checkExist(prop, value, filterId);
	}
	/* (non-Javadoc)
	 * @see com.mqm.frame.infrastructure.base.service.IMyBatisService#checkExist(String prop,Object value)
	 */
	@Override
	public boolean checkExist(String prop,Object value){
		return this.executor.checkExist(prop, value);
	}

	//关联查询方法=========================================================================
	protected QueryBuilder selectFrom(Class<?> cls1,String alias1,Class<?> cls2,String alias2){
		QueryBuilder builder = new QueryBuilder(this.getSqlSessionTemplate());
		builder.addModel(cls1,alias1);
		builder.addModel(cls2,alias2);
		return builder;
	}
	protected QueryBuilder selectFrom(Class<?> cls1,String alias1,Class<?> cls2,String alias2,Class<?> cls3,String alias3){
		QueryBuilder builder = new QueryBuilder(this.getSqlSessionTemplate());
		builder.addModel(cls1,alias1);
		builder.addModel(cls2,alias2);
		builder.addModel(cls3,alias3);
		return builder;
	}
	protected QueryBuilder selectFrom(Class<?> cls1,Class<?> cls2){
		QueryBuilder builder = new QueryBuilder(this.getSqlSessionTemplate());
		builder.addModel(cls1);
		builder.addModel(cls2);
		return builder;
	}
	protected QueryBuilder selectFrom(Class<?> cls1,Class<?> cls2,Class<?> cls3){
		QueryBuilder builder = new QueryBuilder(this.getSqlSessionTemplate());
		builder.addModel(cls1);
		builder.addModel(cls2);
		builder.addModel(cls3);
		return builder;
	}
	
	/*protected QueryBuilder selectFrom(Class<?> cls1,String relationTable,Class<?> cls3){
		QueryBuilder builder = new QueryBuilder(this.getSqlSessionTemplate());
		builder.addModel(cls1);
		builder.addModel(cls3);
		return builder;
	}*/
	
	
	

	//私有方法集=========================================================================
	private void initNamespace(){
		Type cls = getClass().getGenericSuperclass();
		if (cls instanceof ParameterizedType) {
			ParameterizedType genericSuperclass = (ParameterizedType) cls;
			clazz = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
		}else{
			throw new FbrpException(this.getClass().getName() +"类找不到实体参数");
		}
	}
	
	@Override
	public T newValueObject(){
		Type cls = getClass().getGenericSuperclass();
		if (cls instanceof ParameterizedType) {
			ParameterizedType genericSuperclass = (ParameterizedType) cls;
			Class<T> clazz = (Class<T>) genericSuperclass.getActualTypeArguments()[0];
			try {
				return clazz.newInstance();
			} catch (Exception e) {
				throw new FbrpException("创建实体对象"+clazz.getName() +"时发生错误。");
			}
		}else{
			throw new FbrpException(this.getClass().getName() +"类找不到实体参数");
		}
	}
	
	protected String getStatementId(String name) {
		if(this.clazz==null){
			throw new IllegalArgumentException("命名空间为空，可能是子类未提供泛型参数");
		}
		return clazz.getName() + "." + name;
	}
	
	protected  EntityExecutor<T> getEntityExecutor(Class<T> entryClazz){
		Entity anno = entryClazz.getAnnotation(Entity.class);
		if (anno == null) {
			throw new FbrpException(entryClazz.getName() + "未找到@Entry注解");
		}
		return new EntityExecutor<T>(entryClazz, this.getKeyGen(), this.getSqlSessionTemplate());
	}
	
	private void crjDmAndCrsjForCreate(T t){
		if(t != null && !StringUtils.hasLength(t.getCjrDm())){
			t.setCjrDm(this.getLoginId());
			t.setCjsj(new Date());
		}
	}
	
	private void crjDmAndCrsjForUpdate(T t){
		if(t != null && !StringUtils.hasLength(t.getXgrDm())){
			t.setXgrDm(this.getLoginId());
			t.setXgsj(new Date());
		}
	}

}
