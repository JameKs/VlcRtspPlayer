/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf.account;

import java.util.List;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public class CustomUser {

	private String id;
	private String name;
	private String loginName;
	private String password;
	private String email;
	private int revision;
	
	private List<CustomGroup> groupList;
	
	

	/**
	 * @return the groupList
	 */
	public List<CustomGroup> getGroupList() {
		return groupList;
	}
	/**
	 * @param groupList the groupList to set
	 */
	public void setGroupList(List<CustomGroup> groupList) {
		this.groupList = groupList;
	}
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}
	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the revision
	 */
	public int getRevision() {
		return revision;
	}
	/**
	 * @param revision the revision to set
	 */
	public void setRevision(int revision) {
		this.revision = revision;
	}
	
	

}
