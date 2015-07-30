/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.mqm.frame.common.auditlog.vo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.mqm.frame.infrastructure.base.vo.ValueObject;


/**
 * 
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author linjunxiong  linjunxiong@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
@Entity
public class FbrpCommonAuditLog extends ValueObject {

	private static final long serialVersionUID = -8534051607399627007L;

	/**
	 * 登录。
	 */
	public static final String BIZTYPE_LOGIN = "biztype_login";//登陆
	/**
	 * 注销。
	 */
	public static final String BIZTYPE_LOGOUT = "biztype_logout";//注销
	/**
	 * 登录后选择应用。
	 */
	public static final String BIZTYPE_SELECTAPP = "biztype_selectApp";//登录后选择应用
	/**
	 * URL管理。
	 */
	public static final String BIZTYPE_URL= "biztype_url";//URL管理
	/**
	 * 功能管理。
	 */
	public static final String BIZTYPE_FUNCTION= "biztype_function";//功能管理
	/**
	 * 菜单管理。
	 */
	public static final String BIZTYPE_MENU = "biztype_menu";//菜单管理
	/**
	 * 代码管理。
	 */
	public static final String BIZTYPE_CODE = "biztype_code";//代码管理
	/**
	 * 公告管理。
	 */
	public static final String BIZTYPE_NOTICE = "biztype_notice";//公告管理
	/**
	 * 日志管理。
	 */
	public static final String BIZTYPE_LOG = "biztype_log";//日志管理
	/**
	 * 在线用户监控。
	 */
	public static final String BIZTYPE_USERONLINE = "biztype_userOnline";//在线用户监控
	/**
	 * 首页管理。
	 */
	public static final String BIZTYPE_APPFIRSTTAB = "biztype_appFirstTab";//首页管理
	/**
	 * 自定义首页管理。
	 */
	public static final String BIZTYPE_PORTALLAYOUT = "biztype_portalLayout";//自定义页面管理
	/**
	 * 参数管理。
	 */
	public static final String BIZTYPE_PARAM = "biztype_param";//参数管理
	/**
	 * 人员管理。
	 */
	public static final String BIZTYPE_STAFF = "biztype_staff";//人员管理
	/**
	 * 角色管理。
	 */
	public static final String BIZTYPE_ROLE = "biztype_role";//角色管理
	/**
	 * 机构管理。
	 */
	public static final String BIZTYPE_ORG = "biztype_org";//机构管理
	/**
	 * 授权管理。
	 */
	public static final String BIZTYPE_GRANT = "biztype_grant";//授权管理
	/**
	 * 权限分配维护。
	 */
	public static final String BIZTYPE_GRANTSTAFFQUERY = "biztype_grantStaffQuery";//权限分配维护
	/**
	 * 人员权限查询。
	 */
	public static final String BIZTYPE_STAFFGRANTQUERY = "biztype_staffGrantQuery";//人员权限查询
	/**
	 * 分级规则。
	 */
	public static final String BIZTYPE_ORG_RULE = "biztype_orgRule";//分级规则
	/**
	 * 数据源管理。
	 */
	public static final String BIZTYPE_DS = "biztype_ds";//数据源管理
	/**
	 * 表字段管理。
	 */
	public static final String BIZTYPE_TABLECOLINFO = "biztype_tableColInfo";//表字段管理
	/**
	 * 表间关系管理。
	 */
	public static final String BIZTYPE_TABLEREL = "biztype_tableRel";//表间关系管理
	/**
	 * 资源服务器管理。
	 */
	public static final String BIZTYPE_RESSERVER = "biztype_resServer";//资源服务器管理
	/**
	 * 资源发布。
	 */
	public static final String BIZTYPE_RESRELEASE = "biztype_resRelease";//资源发布
	/**
	 * 资源浏览。
	 */
	public static final String BIZTYPE_RESBROWSE = "biztype_resBrowse";//资源浏览
	/**
	 * 模板管理。
	 */
	public static final String BIZTYPE_TEMPLATE = "biztype_template";//模板管理
	/**
	 * FTP服务器。
	 */
	public static final String BIZTYPE_FTPSERVER = "biztype_ftpServer";//FTP服务器
	/**
	 * 邮件服务器。
	 */
	public static final String BIZTYPE_MAILSERVER = "biztype_mailServer";//邮件服务器
	/**
	 * 作业管理。
	 */
	public static final String BIZTYPE_JOB = "biztype_job";//作业管理
	/**
	 * 触发器管理。
	 */
	public static final String BIZTYPE_TRIGGER = "biztype_trigger";//触发器管理
	/**
	 * 调度管理。
	 */
	public static final String BIZTYPE_SCHEDULE = "biztype_schedule";//调度管理
	/**
	 * 默认菜单。
	 */
	public static final String BIZTYPE_APPDEFMENU = "biztype_appDefMenu";//默认菜单
	/**
	 * CAS(SSO)认证源管理。
	 */
	public static final String BIZTYPE_AUTHCAS = "biztype_authCas";//CAS(SSO)认证源管理
	/**
	 * LDAP认证源管理。
	 */
	public static final String BIZTYPE_AUTHLDAP = "biztype_authLdap";//LDAP认证源管理
	/**
	 * 数据库认证源。
	 */
	public static final String BIZTYPE_AUTHRDBMS = "biztype_authRdbms";//数据库认证源
	/**
	 * 应用管理。
	 */
	public static final String BIZTYPE_APPSYS = "biztype_appSys";//应用管理
	/**
	 * 权限控制项。
	 */
	public static final String BIZTYPE_GRANTCONFIG="biztype_grantconfig";//权限控制项
	/**
	 * 门户。
	 */
	public static final String BIZTYPE_PORTAL="biztype_portal";//门户
	
	/**
	 * 登录。
	 */
	public static final String LOGTYPE_LOGIN = "0";//登录
	/**
	 * 注销。
	 */
	public static final String LOGTYPE_LOGOUT = "1";//注销
	/**
	 * 新增。
	 */
	public static final String LOGTYPE_INSERT = "2";//新增
	/**
	 * 删除。
	 */
	public static final String LOGTYPE_DELETE = "3";//删除
	/**
	 * 查询。
	 */
	public static final String LOGTYPE_SELECT = "4";//查询
	/**
	 * 更新。
	 */
	public static final String LOGTYPE_UPDATE = "5";//更新

	private String bizType;
	private String opCode;
	private String opName;
	private String ipAddr;
	private String logType;
	private String logInfo;
    private Date createdTime;
	/**
	 * 获取opCode。
	 * 
	 * @return String
	 */
	@Column(name="OPER_CODE")
	public String getOpCode() {
		return opCode;
	}

	/**
	 * 获取opName。
	 * 
	 * @return String
	 */
	@Column(name="OPER_NAME")
	public String getOpName() {
		return opName;
	}

	/**
	 * 设置opName。
	 * 
	 * @param opName String
	 */
	public void setOpName(String opName) {
		this.opName = opName;
	}

	/**
	 * 设置opCode。
	 * 
	 * @param opCode String
	 */
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}

	/**
	 * 获取ipAddr。
	 * 
	 * @return String
	 */
	public String getIpAddr() {
		return ipAddr;
	}

	/**
	 * 设置ipAddr。
	 * 
	 * @param ipAddr String
	 */
	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	/**
	 * 获取bizType。
	 *
	 * @return String
	 */
	public String getBizType() {
		return bizType;
	}

	/**
	 * 设置bizType。
	 * 
	 * @param bizType String
	 */
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}

	/**
	 * 获取logType。
	 * 
	 * @return String
	 */
	public String getLogType() {
		return logType;
	}

	/**
	 * 设置logType。
	 * 
	 * @param logType String
	 */
	public void setLogType(String logType) {
		this.logType = logType;
	}

	/**
	 * 获取logInfo。
	 * 
	 * @return String
	 */
	public String getLogInfo() {
		return logInfo;
	}

	/**
	 * 设置logInfo。
	 * 
	 * @param logInfo String
	 */
	public void setLogInfo(String logInfo) {
		this.logInfo = logInfo;
	}
	
	/**
	 * 获取createdTime。
	 * 
	 * @return Date
	 */
	@Column(name="Created_Time")
	public Date getCreatedTime() {
		return createdTime;
	}
	
	/**
	 * 设置createdTime。
	 * 
	 * @param createdTime Date
	 */
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	
}