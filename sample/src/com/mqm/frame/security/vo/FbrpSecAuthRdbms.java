/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.security.vo;


import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.mqm.frame.dbtool.vo.FbrpDbtoolDs;
import com.mqm.frame.infrastructure.base.vo.ValueObject;
import com.mqm.frame.util.StringUtil;

/**
 * 
 * <pre>
 * 关联表FBRP_SEC_AUTH_RDBMS的类。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpSecAuthRdbms extends ValueObject {

	private static final long serialVersionUID = -4248520090895724947L;

	private int hashCode = Integer.MIN_VALUE;

	// primary key
	private java.lang.String id;

	// fields
	@NotEmpty(message="必填")
	@Length(max=32, message="长度不能超过32")
	private java.lang.String srvName;
	
	private java.lang.String dsId;
	
	@Length(max=64, message="长度不能超过64")
	private java.lang.String userTable;
	
	@Length(max=64, message="长度不能超过64")
	private java.lang.String userName;
	
	@Length(max=64, message="长度不能超过64")
	private java.lang.String userPassword;
	
	@Length(max=200, message="长度不能超过200")
	private String validUserSql;
	
	/**
	 * 加密算法。
	 */
	private String encoder;
	/**
	 * 密钥类型：0-无密钥、1-普通文本、2-表字段。
	 */
	private String encoderType;
	/**
	 * <pre>密钥</pre>
	 * 可以是一个字段名，也可以是一个普通文本，取决于encodeType的取值。
	 */
	private String encoderSalt;
	
	
	private String roleTable;
	private String relationTable;
	
	private String staffMappingData;
	private String relationMappingData;
	private String roleMappingData;
	
	private String synFlag;
	private String ext1;
	private String ext2;
	
	private FbrpDbtoolDs fbrpDbtoolDsVO;
	
	
	//Transient Property
	Map<String,String> staffMapping = new HashMap<String,String>();
	Map<String,String> roleMapping = new HashMap<String,String>();
	Map<String,String> relationMapping = new HashMap<String,String>();
	
	/**
	 * 返回fbrpDbtoolDsVO。
	 * 
	 * @return fbrpDbtoolDsVO
	 */
	@OneToOne(mappedBy="DS_ID")
	public FbrpDbtoolDs getFbrpDbtoolDsVO() {
		return fbrpDbtoolDsVO;
	}

	/**
	 * 设置fbrpDbtoolDsVO。
	 * 
	 * @param fbrpDbtoolDsVO  FbrpDbtoolDs
	 */
	public void setFbrpDbtoolDsVO(FbrpDbtoolDs fbrpDbtoolDsVO) {
		this.fbrpDbtoolDsVO = fbrpDbtoolDsVO;
	}

	/**
	 * 设置 validUserSql。
	 * 
	 * @param validUserSql 设置 validUserSql。
	 */
	public void setValidUserSql(String validUserSql) {
		this.validUserSql = validUserSql;
	}
	
	/**
	 * 返回 validUserSql。
	 * 
	 * @return 返回 validUserSql。
	 */
	public String getValidUserSql() {
		return validUserSql;
	}	

	/**
	 * Return the unique identifier of this class.
	 * 
	 * @return String
	 */
	public java.lang.String getId() {
		return id;
	}

	/**
	 * Set the unique identifier of this class.
	 * 
	 * @param id the new ID
	 */
	public void setId(java.lang.String id) {
		this.id = id;
		this.hashCode = Integer.MIN_VALUE;
	}

	/**
	 * Return the value associated with the column: SRV_NAME.
	 * 
	 * @return String
	 */
	public java.lang.String getSrvName() {
		return srvName;
	}

	/**
	 * Set the value related to the column: SRV_NAME.
	 * 
	 * @param srvName the SRV_NAME value
	 */
	public void setSrvName(java.lang.String srvName) {
		this.srvName = srvName;
	}

	/**
	 * Return the value associated with the column: DS_ID.
	 * 
	 * @return String
	 */
	public java.lang.String getDsId() {
		return dsId;
	}

	/**
	 * Set the value related to the column: DS_ID.
	 * 
	 * @param dsId the DS_ID value
	 */
	public void setDsId(java.lang.String dsId) {
		this.dsId = dsId;
	}

	/**
	 * Return the value associated with the column: USER_TABLE.
	 * 
	 * @return String
	 */
	public java.lang.String getUserTable() {
		return userTable;
	}

	/**
	 * Set the value related to the column: USER_TABLE.
	 * 
	 * @param userTable the USER_TABLE value
	 */
	public void setUserTable(java.lang.String userTable) {
		this.userTable = userTable;
	}

	/**
	 * Return the value associated with the column: USER_NAME.
	 * 
	 * @return String
	 */
	public java.lang.String getUserName() {
		return userName;
	}

	/**
	 * Set the value related to the column: USER_NAME.
	 * 
	 * @param userName the USER_NAME value
	 */
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}

	/**
	 * Return the value associated with the column: USER_PASSWORD.
	 * 
	 * @return String
	 */
	public java.lang.String getUserPassword() {
		return userPassword;
	}

	/**
	 * Set the value related to the column: USER_PASSWORD.
	 * 
	 * @param userPassword the USER_PASSWORD value
	 */
	public void setUserPassword(java.lang.String userPassword) {
		this.userPassword = userPassword;
	}

	/**
	 * 获取roleTable。
	 * 
	 * @return String
	 */
	public String getRoleTable() {
		return this.roleTable;
	}
	
	/**
	 * 获取relationTable。
	 * 
	 * @return String
	 */
	public String getRelationTable(){
		return this.relationTable;
	}

	/**
	 * 设置roleTable。
	 *  
	 * @param roleTable  String
	 */
	public void setRoleTable(String roleTable) {
		this.roleTable = roleTable;
	}

	/**
	 * 设置relationTable。
	 * 
	 * @param relationTable String
	 */
	public void setRelationTable(String relationTable) {
		this.relationTable = relationTable;
	}

	/**
	 * 获取staffMappingData。
	 * 
	 * @return String
	 */
	public String getStaffMappingData() {
		return staffMappingData;
	}

	/**
	 * 设置staffMappingData。
	 * 
	 * @param staffMappingData String
	 */
	public void setStaffMappingData(String staffMappingData) {
		this.staffMappingData = staffMappingData;
	}

	/**
	 * 获取relationMappingData。
	 * 
	 * @return String
	 */
	public String getRelationMappingData() {
		return relationMappingData;
	}

	/**
	 * 设置relationMappingData。
	 * 
	 * @param relationMappingData String
	 */
	public void setRelationMappingData(String relationMappingData) {
		this.relationMappingData = relationMappingData;
	}

	/**
	 * 获取roleMappingData。
	 * 
	 * @return String
	 */
	public String getRoleMappingData() {
		return roleMappingData;
	}

	/**
	 * 设置roleMappingData。
	 * 
	 * @param roleMappingData String
	 */
	public void setRoleMappingData(String roleMappingData) {
		this.roleMappingData = roleMappingData;
	}

	/**
	 * 获取synFlag。
	 * 
	 * @return String
	 */
	public String getSynFlag() {
		return synFlag;
	}

	/**
	 * 设置synFlag。
	 * 
	 * @param synFlag String
	 */
	public void setSynFlag(String synFlag) {
		this.synFlag = synFlag;
	}
	/**
	 * 获取encoder。
	 * 
	 * @return String
	 */
	public String getEncoder() {
		return encoder;
	}

	/**
	 * 设置encoder。
	 * 
	 * @param encoder String
	 */
	public void setEncoder(String encoder) {
		this.encoder = encoder;
	}

	/**
	 * 获取encoderType。
	 * 
	 * @return String
	 */
	public String getEncoderType() {
		return encoderType;
	}

	/**
	 * 设置encoderType。
	 * 
	 * @param encoderType String
	 */
	public void setEncoderType(String encoderType) {
		this.encoderType = encoderType;
	}

	/**
	 * 获取encoderSalt。
	 * 
	 * @return String
	 */
	public String getEncoderSalt() {
		return encoderSalt;
	}

	/**
	 * 设置encoderSalt。
	 * 
	 * @param encoderSalt String
	 */
	public void setEncoderSalt(String encoderSalt) {
		this.encoderSalt = encoderSalt;
	}


	//---------------------------------------------------
	/**
	 * 获取RelationMapping。
	 * 
	 * @return Map<String, String>
	 */
	@Transient
	public Map<String, String> getRelationMapping() {
		relationMapping.clear();
		String md = this.getRelationMappingData();
		if(!StringUtil.isEmpty(md)){
			String[] arr = md.split("[|]");
			if(arr.length==2){
				relationMapping.put("STAFF_ID", arr[0]);
				relationMapping.put("ROLE_ID", arr[1]);
			}
		}
		return relationMapping;
	}
	/**
	 * 获取StaffMapping。
	 * 
	 * @return Map<String, String>
	 */
	@Transient
	public Map<String,String> getStaffMapping(){
		staffMapping.clear();
		String md = this.getStaffMappingData();
		if(!StringUtil.isEmpty(md)){
			String[] arr = md.split("[|]");
			if(arr.length==4){
				staffMapping.put("PK", arr[0]);
				staffMapping.put("CODE", arr[1]);
				staffMapping.put("NAME", arr[2]);
				staffMapping.put("LOGIN_ID", arr[3]);
			}
		}
		return staffMapping;
	}
	/**
	 * 获取RoleMapping。
	 * 
	 * @return Map<String, String>
	 */
	@Transient
	public Map<String,String> getRoleMapping(){
		roleMapping.clear();
		String md = this.getRoleMappingData();
		if(!StringUtil.isEmpty(md)){
			String[] arr = md.split("[|]");
			if(arr.length==3){
				roleMapping.put("PK", arr[0]);
				roleMapping.put("CODE", arr[1]);
				roleMapping.put("NAME", arr[2]);
			}
		}
		return roleMapping;
	}
	
	/**
	 * 获取ext1。
	 * 
	 * @return String
	 */
	public String getExt1() {
		return ext1;
	}

	/**
	 * 设置ext1。
	 * 
	 * @param ext1 String
	 */
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	/**
	 * 获取ext2。
	 * 
	 * @return String
	 */
	public String getExt2() {
		return ext2;
	}

	/**
	 * 设置ext2。
	 * 
	 * @param ext2 String
	 */
	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}
	
}