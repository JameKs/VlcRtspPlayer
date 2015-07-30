/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.util;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 审批流程中业务处理消息封装类。
 * </pre>
 * @author zengziwen  zengziwen@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public class ProcessMessage{

	/**
	 * 成功标识。
	 */
	public boolean success;
	
	/** 
	 * 业务ID。
	 * */
	public String ywId;
	
	/**
	 * 业务名称。
	 * */
	public String ywName;
	
	/**
	 * 流程运行的key。
	 */
	public String processDeployKey;
	
	
	/**
	 * 审批同意表识。
	 */
	public String isPass;

	/**
	 * 流程是不是刚发起的新流程。
	 */
	public boolean isNew;
	
	/**
	 * 流程是否已经终止(全部审批完成)。
	 */
	public boolean isEnd;
	
	/**
	 * 任务级别。
	 */
	public String rwJb ;
	
	/**
	 * 任务编号。
	 */
	public String rwBh;
	
	/**
	 * 消息。
	 */
	public String msg;
	
	/**
	 * 数据封装对象。
	 */
	public Map<String,Object> data ;
	
	/**
	 * 异常原因。
	 */
	public String ycyy;
	
	/**
	 * 构造方法。
	 */
	public ProcessMessage(){
		this.setData(new HashMap<String,Object>());
		this.setSuccess(false);
		this.setMsg("");
	}
	
	/**
	 * 获取success。
	 * 
	 * @return 返回 success。
	 */
	public boolean isSuccess() {
		return success;
	}
	/**
	 * 设置 success。
	 * @param success 设置 success。
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	/**
	 * 获取　data。
	 * @return 返回 data。
	 */
	public Map<String, Object> getData() {
		return data;
	}
	/**
	 * 设置 data。
	 * @param data 设置 data。
	 */
	public void setData(Map<String, Object> data) {
		this.data = data;
	}

	/**返回 msg。
	 * @return String
	 */
	public String getMsg() {
		return msg;
	}

	
	/**
	 *  设置 msg。
	 * @param msg String
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * 返回 ywId。
	 * @return String
	 */
	public String getYwId() {
		return ywId;
	}

	/**设置 ywId。
	 * @param ywId String
	 */
	public void setYwId(String ywId) {
		this.ywId = ywId;
	}

	/**返回 ywName。
	 * 
	 * @return String
	 */
	public String getYwName() {
		return ywName;
	}

	/**
	 * 设置 ywName。
	 * 
	 * @param ywName String
	 */
	public void setYwName(String ywName) {
		this.ywName = ywName;
	}

	/**返回 processDeployKey。
	 * @return String
	 */
	public String getProcessDeployKey() {
		return processDeployKey;
	}

	/**
	 * 设置 processDeployKey。　
	 * @param processDeployKey String
	 */
	public void setProcessDeployKey(String processDeployKey) {
		this.processDeployKey = processDeployKey;
	}

	/**
	 * 返回 isPass。
	 * 　
	 * @return String
	 */
	public String getIsPass() {
		return isPass;
	}

	/**
	 * 设置 isPass。
	 * @param isPass String
	 */
	public void setIsPass(String isPass) {
		this.isPass = isPass;
	}

	/**
	 * 返回 isEnd。
	 * 
	 * @return boolean
	 */
	public boolean isEnd() {
		return isEnd;
	}

	/**
	 * 设置 isEnd。
	 * @param isEnd boolean
	 */
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}

	/** 
	 * 返回 isNew。
	 * @return boolean
	 */
	public boolean isNew() {
		return isNew;
	}

	/**
	 * 设置 isNew。
	 * @param isNew 　boolean
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

	/**
	 * 返回 rwJb。　
	 * @return String
	 */
	public String getRwJb() {
		return rwJb;
	}

	/**
	 * 设置 rwJb。　
	 * @param rwJb String
	 */
	public void setRwJb(String rwJb) {
		this.rwJb = rwJb;
	}

	/**
	 * 返回 rwBh。 
	 * @return  String
	 */
	public String getRwBh() {
		return rwBh;
	}

	/**
	 * 设置 rwBh。 
	 * @param rwBh String
	 */
	public void setRwBh(String rwBh) {
		this.rwBh = rwBh;
	}

	public String getYcyy() {
		return ycyy;
	}

	public void setYcyy(String ycyy) {
		this.ycyy = ycyy;
	}
	
}
