/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.infrastructure.util;

/**
 * 
 * <pre>
 * 分页信息。
 * </pre>
 * @author luxiaocheng  luxiaocheng@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class PageInfo {
	
	private int next;
	private int prev;
	private int totalPage;
	private int pageIndex;
	private int pageSize;
	
	/**
	 * 构建器。
	 * 
	 * @param pageIndex int
	 * @param pageSize int
	 * @param total long
	 */
	public PageInfo(int pageIndex,int pageSize,long total){
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.totalPage = (int)(total / pageSize);
		if (total % pageSize != 0) {
			totalPage++;
		}
		this.next = pageIndex >= this.totalPage ? pageIndex : pageIndex + 1;
		this.prev = pageIndex < 2 ? 1 : pageIndex - 1;
	}
	
	/**
	 * 获取next。
	 * 
	 * @return int
	 */
	public int getNext(){
		return this.next;
	}
	
	/**
	 * 获取prev。
	 * 
	 * @return int
	 */
	public int getPrev(){
		return this.prev;
	}
	
	/**
	 * 获取1。
	 * 
	 * @return int
	 */
	public int getFirst(){
		return 1;
	}
	
	/**
	 * 获取totalPage。
	 * 
	 * @return int
	 */
	public int getLast(){
		return this.totalPage;
	}
	
	/**
	 * 获取totalPage。
	 * 
	 * @return int
	 */
	public int getTotalPage(){
		return this.totalPage;
	}
	
	/**
	 * 获取pageIndex。
	 * 
	 * @return int
	 */
	public int getPageIndex() {
		return pageIndex;
	}
	
	/**
	 * 获取pageSize。
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize;
	}
	
}
