/**
 * Copyright(c) Foresee Science & Technology Ltd. 
 */
package com.icanft.common.wf.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.activiti.engine.impl.pvm.process.ActivityImpl;

import com.icanft.xtgl.yhgl.vo.User;


/**
 * <pre>
 * 程序的中文名称。
 * </pre>
 * @author zengziwen  zengziwen@foresee.cn
 * @version 1.00.00
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容: 
 * </pre>
 */
public interface IProcessCoreService {
	
	/**
	 * 根据当前任务ID，查询可以驳回的任务节点。
	 * 
	 * @param taskId String
	 * @return List<ActivityImpl>
	 * @throws Exception  异常
	 */
	public List<ActivityImpl> findBackAvtivity(String taskId) throws Exception ;
	
	/**
	 * 驳回流程。
	 * 
	 * @param ywId String
	 * @param taskId String
	 * @param activityId String
	 * @param activitiName String
	 * @param oldActivityId String
	 * @param oldActivityName String
	 * @param opinion String
	 * @param rwJbSj java.util.Date
	 * @param variables Map<String, Object>
	 * @param user UserProfileVO
	 * @throws Exception 异常
	 */
	public void saveBackProcess(String ywId,String taskId,String activityId,String activitiName, String oldActivityId ,String oldActivityName ,
			String opinion,Date rwJbSj,Map<String, Object> variables,User user) throws Exception;
	
}
