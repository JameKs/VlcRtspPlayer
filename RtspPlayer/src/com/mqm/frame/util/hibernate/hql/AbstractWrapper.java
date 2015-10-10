/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.util.hibernate.hql;

/**
 * 
 * <pre>
 * 动态拼装HQL语句的从句的基类。
 * </pre>
 * @author luoshifei luoshifei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public abstract class AbstractWrapper {

	private int offset;
	private int pageSize;
	private int pageNo;
	private int pageCount;
	private int recordCount;
	private boolean countTotalSize;

	/**
	 * 默认的构造方法。
	 */
	public AbstractWrapper() {
		pageSize = 0;
		pageNo = 0;
		pageCount = 0;
		recordCount = 0;
		offset = -1;
		countTotalSize = true;
	}

	/**
	 * 获取pageSize。
	 * 
	 * @return int
	 */
	public int getPageSize() {
		return pageSize >= 0 ? pageSize : 0;
	}

	/**
	 * 设置pageSize。
	 * 
	 * @param pageSize int
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		offset = this.pageSize * (pageNo - 1);
	}

	/**
	 * 获取pageNo。
	 * 
	 * @return int
	 */
	public int getPageNo() {
		return pageNo >= 0 ? pageNo : 0;
	}

	/**
	 * 设置pageNo。
	 * 
	 * @param pageNo int
	 */
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		offset = pageSize * (this.pageNo - 1);
	}

	/**
	 * 获取offset。
	 * 
	 * @return int
	 */
	public int getOffset() {
		return offset >= 0 ? offset : 0;
	}

	/**
	 * 设置offset。
	 * 
	 * @param offset int
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * 获取pageCount。
	 * 
	 * @return int
	 */
	public int getPageCount() {
		if (pageSize <= 0) {
			pageCount = 0;
		} else {
			pageCount = (recordCount - 1) / pageSize + 1;
		}
		return pageCount;
	}

	/**
	 * 获取recordCount。
	 * 
	 * @return int
	 */
	public int getRecordCount() {
		return recordCount >= 0 ? recordCount : 0;
	}

	/**
	 * 设置recordCount。
	 * 
	 * @param recordCount int
	 */
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	/**
	 * 获取countTotalSize。
	 * 
	 * @return boolean
	 */
	public boolean isCountTotalSize() {
		return countTotalSize;
	}

	/**
	 * 设置countTotalSize。
	 * 
	 * @param countTotalSize boolean
	 */
	public void setCountTotalSize(boolean countTotalSize) {
		this.countTotalSize = countTotalSize;
	}

}