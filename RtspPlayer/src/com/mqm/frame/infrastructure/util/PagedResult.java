/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

import java.util.LinkedList;
import java.util.List;

import com.mqm.frame.util.StringUtil;

/**
 * <pre>
 * 分页查询结果。
 * </pre>
 * 
 * @param <T>
 *            ValueObject
 * @author luxiaocheng luxiaocheng@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class PagedResult<T> {

	private List<T> data;
	private long total;

	private PageInfo info;

	private String sql;
	/** 
	 * 业务说明。
	 */
	private LinkedList<String> ywDesc;
	/**
	 * 自定义的构造器。
	 * 
	 * @param data
	 *            List<T>
	 *            
	 * @param total
	 *            long
	 *            
	 * @param pageIndex
	 * 				int
	 * 
	 * @param pageSize
	 * 				int
	 */
	public PagedResult(List<T> data, long total, int pageIndex, int pageSize) {
		this.data = data;
		this.total = total;
		this.info = new PageInfo(pageIndex, pageSize, total);
	}

	/**
	 * 获取data。
	 * 
	 * @return List<T>
	 */
	public List<T> getData() {
		return data;
	}

	/**
	 * 设置data。
	 * 
	 * @param data
	 *            List<T>
	 */
	public void setData(List<T> data) {
		this.data = data;
	}

	/**
	 * 获取total。
	 * 
	 * @return long
	 */
	public long getTotal() {
		return total;
	}

	/**
	 * 设置total。
	 * 
	 * @param total
	 *            long
	 */
	public void setTotal(long total) {
		this.total = total;
	}

	/**
	 * 返回info。
	 * 
	 * @return PageInfo
	 */
	public PageInfo getInfo() {
		return this.info;
	}

	/**
	 * 设置sql。
	 * 
	 * @param sql String
	 */
	public void setSql(String sql) {
		if(sql.lastIndexOf("&")>=0){
			String[] str = sql.split("&");
			int length = str.length-2;
			String[] desc = str[length].split("\n");
			LinkedList<String> list = new LinkedList<String>();
			for(int i=0;i<desc.length;i++){
				desc[i] = StringUtil.trimRight(desc[i]);
				if(!StringUtil.isEmpty(desc[i]))
					list.add(desc[i]);
			}
			setYwDesc(list);
		}
		this.sql = sql;
	}

	/**
	 * 获取sql。
	 * 
	 * @return String
	 */
	public String getSql() {
		return sql;
	}

	/**
	 * @return 返回 ywDesc。
	 */
	public List<String> getYwDesc() {
		return ywDesc;
	}

	/**
	 * @param ywDesc 设置 ywDesc。
	 */
	public void setYwDesc(LinkedList<String> ywDesc) {
		this.ywDesc = ywDesc;
	}
	
	

}
