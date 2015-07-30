/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.vo;

import java.util.Date;

/**
 * <pre>
 * 业务与流程的映射，关联[T_WORKFLOW_BUSTSK]表。
 * </pre>
 * 
 * @author zengziwen zengziwen@foresee.cn
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class WfBusTask {

	private static final long serialVersionUID = 7071695733098964254L;

	/**
	 * 流程定义ID。
	 */
	private String procDefId;
	/**
	 * 流程实列ID。
	 */
	private String procInstId;
	/**
	 * 业务ID。
	 */
	private String ywId;
	/**
	 * 业务名称。
	 */
	private String ywName;

	/**
	 * 任务级别。
	 */
	private int rwJb;

	/**
	 * 任务需要天数。
	 */
	private int rwTs;

	/**
	 * 任务开始时间。
	 */
	private Date rwKssj;

	/**
	 *  构造方法。
	 */
	public WfBusTask(){
		
	}
	
	/**
	 * 构造方法。
	 * @param procDefId String
	 * @param procInstId String
	 * @param ywId String
	 * @param ywName String
	 * @param rwJb int
	 * @param rwTs int
	 * @param rwKssj Date
	 */
	public WfBusTask(String procDefId, String procInstId, String ywId,
			String ywName, int rwJb, int rwTs, Date rwKssj) {
		super();
		this.procDefId = procDefId;
		this.procInstId = procInstId;
		this.ywId = ywId;
		this.ywName = ywName;
		this.rwJb = rwJb;
		this.rwTs = rwTs;
		this.rwKssj = rwKssj;
	}

	/**
	 * 返回 procDefId。
	 * 
	 * @return String
	 */
	public String getProcDefId() {
		return procDefId;
	}

	/**
	 * 设置 procDefId。
	 * 
	 * @param procDefId
	 *            String
	 */
	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	/**
	 * 返回 procInstId。
	 * 
	 * @return String
	 */
	public String getProcInstId() {
		return procInstId;
	}

	/**
	 * 设置 procInstId。
	 * 
	 * @param procInstId
	 *            String
	 */
	public void setProcInstId(String procInstId) {
		this.procInstId = procInstId;
	}

	/**
	 * 返回 ywId。
	 * 
	 * @return String
	 */
	public String getYwId() {
		return ywId;
	}

	/**
	 * 设置 ywId。
	 * 
	 * @param ywId
	 *            String
	 */
	public void setYwId(String ywId) {
		this.ywId = ywId;
	}

	/**
	 * 返回 ywName。
	 * 
	 * @return String
	 */
	public String getYwName() {
		return ywName;
	}

	/**
	 * 设置 ywName。
	 * 
	 * @param ywName
	 *            String
	 */
	public void setYwName(String ywName) {
		this.ywName = ywName;
	}

	/**
	 * 返回 rwJb。
	 * 
	 * @return int
	 */
	public int getRwJb() {
		return rwJb;
	}

	/**
	 * 设置 rwJb。
	 * 
	 * @param rwJb
	 *            int
	 */
	public void setRwJb(int rwJb) {
		this.rwJb = rwJb;
	}

	/**
	 * 返回 rwTs。
	 * 
	 * @return int
	 */
	public int getRwTs() {
		return rwTs;
	}

	/**
	 * 设置 rwTs。
	 * 
	 * @param rwTs
	 *            int
	 */
	public void setRwTs(int rwTs) {
		this.rwTs = rwTs;
	}

	/**
	 * 返回 rwKssj。
	 * 
	 * @return java.util.Date
	 */
	public Date getRwKssj() {
		return rwKssj;
	}

	/**
	 * 设置 rwKssj。
	 * 
	 * @param rwKssj
	 *            java.util.Date
	 */
	public void setRwKssj(Date rwKssj) {
		this.rwKssj = rwKssj;
	}

}
