package com.mqm.frame.sys.menu.vo;

import java.io.Serializable;

import com.mqm.frame.common.DefaultVo;

/**
 * @author Administrator
 *
 */
public class Dept extends DefaultVo implements Serializable {

	private static final long serialVersionUID = -433577178466098132L;
	
	private String deptDm;
	
	private String deptMc;

	private String leaf;

	/**
	 * @return 返回 deptDm。
	 */
	public String getDeptDm() {
		return deptDm;
	}

	/**
	 * @param deptDm 设置 deptDm。
	 */
	public void setDeptDm(String deptDm) {
		this.deptDm = deptDm;
	}

	/**
	 * @return 返回 deptMc。
	 */
	public String getDeptMc() {
		return deptMc;
	}

	/**
	 * @param deptMc 设置 deptMc。
	 */
	public void setDeptMc(String deptMc) {
		this.deptMc = deptMc;
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
