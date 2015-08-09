package com.mqm.frame.sys.menu.vo;

import java.io.Serializable;

import com.mqm.frame.common.DefaultVo;

/**
 * @author Administrator
 *
 */
public class Gw extends DefaultVo implements Serializable {

	private static final long serialVersionUID = -433577178466098132L;
	
	private String gwDm;
	
	private String gwMc;

	private String deptId;

	private String leaf;

	/**
	 * @return 返回 gwDm。
	 */
	public String getGwDm() {
		return gwDm;
	}

	/**
	 * @param gwDm 设置 gwDm。
	 */
	public void setGwDm(String gwDm) {
		this.gwDm = gwDm;
	}

	/**
	 * @return 返回 gwMc。
	 */
	public String getGwMc() {
		return gwMc;
	}

	/**
	 * @param gwMc 设置 gwMc。
	 */
	public void setGwMc(String gwMc) {
		this.gwMc = gwMc;
	}

	/**
	 * @return 返回 deptId。
	 */
	public String getDeptId() {
		return deptId;
	}

	/**
	 * @param deptId 设置 deptId。
	 */
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 * @return 返回 leaf。
	 */
	public String getLeaf() {
		return leaf;
	}

	/**
	 * @param leaf 设置 leaf。
	 */
	public void setLeaf(String leaf) {
		this.leaf = leaf;
	}

	
}
