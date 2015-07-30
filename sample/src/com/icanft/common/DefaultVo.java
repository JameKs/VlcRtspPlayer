/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common;

import java.util.Date;

/**
 * <pre>
 * 所以VO共用的属性,所以要保存到数据库或从数据库去数据的都需要继承此类
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月30日
 */
public class DefaultVo {
	
	/**
	 * 常量。
	 */
	public static final String DEL_FLAG_NORMAL = "n";
	/**
	 * 常量。
	 */
	public static final String DEL_FLAG_DELETED = "y";
	
	private String id ;//唯一主键
	
	private String cjr ;//创建人
	
	private Date cjSj ;//创建时间
	
	private String xgr ;//修改人
	
	private Date xgSj ;//修改时间
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the cjr
	 */
	public String getCjr() {
		return cjr;
	}

	/**
	 * @param cjr the cjr to set
	 */
	public void setCjr(String cjr) {
		this.cjr = cjr;
	}

	/**
	 * @return the cjSj
	 */
	public Date getCjSj() {
		return cjSj;
	}

	/**
	 * @param cjSj the cjSj to set
	 */
	public void setCjSj(Date cjSj) {
		this.cjSj = cjSj;
	}

	/**
	 * @return the xgr
	 */
	public String getXgr() {
		return xgr;
	}

	/**
	 * @param xgr the xgr to set
	 */
	public void setXgr(String xgr) {
		this.xgr = xgr;
	}

	/**
	 * @return the xgSj
	 */
	public Date getXgSj() {
		return xgSj;
	}

	/**
	 * @param xgSj the xgSj to set
	 */
	public void setXgSj(Date xgSj) {
		this.xgSj = xgSj;
	}
	
	

}
