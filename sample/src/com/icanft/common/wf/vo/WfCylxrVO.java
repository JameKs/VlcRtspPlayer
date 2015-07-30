/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.vo;

import java.util.Date;

/**
 * <pre>
 *  常用联系人信息封装展显类。
 * </pre>
 * @author lijiawei  lijiawei@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class WfCylxrVO {

	private static final long serialVersionUID = -1956485514091731293L;
	
	private String yhBm;
	
	private String lxrBm;
	
	private String lxrMc;
	
	private String szjgMc;
	
	private Integer lxrPx;
	
	private Date xgSj;

	/**
	 * 用户编码的Get方法。
	 * @return yhBm 用户编码
	 */
	public String getYhBm() {
		return yhBm;
	}

	/**
	 * 用户编码的Set方法。
	 * @param yhBm 用户编码
	 */
	public void setYhBm(String yhBm) {
		this.yhBm = yhBm;
	}

	/**
	 * 联系人编码的Get方法。
	 * @return lxrBm 联系人编码
	 */
	public String getLxrBm() {
		return lxrBm;
	}

	/**
	 * 联系人编码的Set方法。
	 * @param lxrBm 联系人编码
	 */
	public void setLxrBm(String lxrBm) {
		this.lxrBm = lxrBm;
	}

	/**
	 * 联系人名称的Get方法。
	 * @return lxrMc 联系人名称
	 */
	public String getLxrMc() {
		return lxrMc;
	}

	/**
	 * 联系人名称的Set方法。
	 * @param lxrMc 联系人名称
	 */
	public void setLxrMc(String lxrMc) {
		this.lxrMc = lxrMc;
	}

	/**
	 * 所在机构名称的Get方法。
	 * @return szjgMc 所在机构名称
	 */
	public String getSzjgMc() {
		return szjgMc;
	}

	/**
	 * 所在机构名称的Set方法。
	 * @param szjgMc 所在机构名称
	 */
	public void setSzjgMc(String szjgMc) {
		this.szjgMc = szjgMc;
	}

	/**
	 * 联系人排序的Get方法。
	 * @return lxrPx 联系人排序
	 */
	public Integer getLxrPx() {
		return lxrPx;
	}

	/**
	 * 联系人排序的Set方法。
	 * @param lxrPx 联系人排序
	 */
	public void setLxrPx(Integer lxrPx) {
		this.lxrPx = lxrPx;
	}

	/**
	 * 修改时间的Get方法。
	 * @return xgSj 修改时间
	 */
	public Date getXgSj() {
		return xgSj;
	}

	/**
	 * 修改时间的Set方法。
	 * @param xgSj 修改时间
	 */
	public void setXgSj(Date xgSj) {
		this.xgSj = xgSj;
	}

}
