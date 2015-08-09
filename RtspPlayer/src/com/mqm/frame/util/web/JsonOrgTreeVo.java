package com.mqm.frame.util.web;

/**
 * 
 * <pre>
 * 对org ZTree进行传递数据的JSON类。
 * </pre>
 * 
 * @author zouyongqiao zouyongqiao@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class JsonOrgTreeVo extends JsonTreeVo {

	private static final long serialVersionUID = 578985975678848208L;

	private String code;
	private String orgType;
	private String orgLevel;
	private String parentName;

	/**
	 * 默认的构造方法。
	 */
	public JsonOrgTreeVo() {
	}

	/**
	 * 机构树。
	 * 
	 * @param id String
	 * 
	 * @param pId String
	 * 
	 * @param name String
	 * 
	 * @param open String
	 * 
	 * @param click boolean
	 * 
	 * @param code String
	 * 
	 * @param orgType String
	 * 
	 * @param orgLevel String
	 * 
	 * @param parentName String
	 */
	public JsonOrgTreeVo(String id, String pId, String name, boolean open,
			String click, String code, String orgType, String orgLevel,
			String parentName) {
		super(id, pId, name, open, click);
		this.code = code;
		this.orgType = orgType;
		this.orgLevel = orgLevel;
		this.parentName = parentName;
	}

	/**
	 * 获取parentName。
	 * 
	 * @return String
	 */
	public String getParentName() {
		return parentName;
	}

	/**
	 * 设置parentName。
	 * 
	 * @param parentName String
	 */
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	/**
	 * 获取code。
	 * 
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置code。
	 * 
	 * @param code
	 *            String
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取orgType。
	 * 
	 * @return String
	 */
	public String getOrgType() {
		return orgType;
	}

	/**
	 * 设置orgType。
	 * 
	 * @param orgType
	 *            String
	 */
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	/**
	 * 获取orgLevel。
	 * 
	 * @return String
	 */
	public String getOrgLevel() {
		return orgLevel;
	}

	/**
	 * 设置orgLevel。
	 * 
	 * @param orgLevel
	 *            String
	 */
	public void setOrgLevel(String orgLevel) {
		this.orgLevel = orgLevel;
	}

}
