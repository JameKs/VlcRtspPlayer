package com.mqm.frame.sys.menu.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.mqm.frame.common.DefaultVO;

/**
 * @author Administrator
 *
 */
public class MenuVO extends DefaultVO implements Serializable {

	private static final long serialVersionUID = -433577178466098132L;
	
	private String cdDm;
	
	private String cnName;

	private String enName;

	private String url;
	
	private String pId;
	
	private BigDecimal ccsd;
	
	private BigDecimal ccsx;
	
	private String leaf;
	
	private String imageUrl;
	
	private int sortNo;

	/**
	 * @return 返回 id。
	 */
	public String getCdDm() {
		return cdDm;
	}

	/**
	 * @param cdDm 设cdDm 。
	 */
	public void setCdDm(String cdDm) {
		this.cdDm = cdDm;
	}

	/**
	 * @return 返回 cnName。
	 */
	public String getCnName() {
		return cnName;
	}

	/**
	 * @param cnName 设置 cnName。
	 */
	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	/**
	 * @return 返回 enName。
	 */
	public String getEnName() {
		return enName;
	}

	/**
	 * @param enName 设置 enName。
	 */
	public void setEnName(String enName) {
		this.enName = enName;
	}

	/**
	 * @return 返回 url。
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url 设置 url。
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return 返回 pId。
	 */
	public String getpId() {
		return pId;
	}

	/**
	 * @param pId 设置 pId。
	 */
	public void setpId(String pId) {
		this.pId = pId;
	}

	/**
	 * @return 返回 ccsd。
	 */
	public BigDecimal getCcsd() {
		return ccsd;
	}

	/**
	 * @param ccsd 设置 ccsd。
	 */
	public void setCcsd(BigDecimal ccsd) {
		this.ccsd = ccsd;
	}

	/**
	 * @return 返回 ccsx。
	 */
	public BigDecimal getCcsx() {
		return ccsx;
	}

	/**
	 * @param ccsx 设置 ccsx。
	 */
	public void setCcsx(BigDecimal ccsx) {
		this.ccsx = ccsx;
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

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public int getSortNo() {
		return this.sortNo;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	
}
