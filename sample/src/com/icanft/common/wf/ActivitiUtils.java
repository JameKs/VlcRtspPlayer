/**
 * Copyright(c) MQM Science & Technology Ltd.
 * 秦墨科技有限公司
 */
package com.icanft.common.wf;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.repository.ProcessDefinition;

import com.icanft.common.wf.account.CustomGroup;
import com.icanft.common.wf.account.CustomUser;

/**
 * <pre>
 * 文件中文描述
 * 
 * <pre>
 * @author meihu2007@sina.com
 * 2015年5月25日
 */
public class ActivitiUtils {

	public static UserEntity toActivitiUser(CustomUser customUser) {
		UserEntity userEntity = new UserEntity();
		userEntity.setId(customUser.getId().toString());
		userEntity.setFirstName(customUser.getName());
		userEntity.setLastName(customUser.getLoginName());
		userEntity.setPassword(customUser.getPassword());
		userEntity.setEmail(customUser.getEmail());
		userEntity.setRevision(1);
		return userEntity;
	}

	public static GroupEntity toActivitiGroup(CustomGroup customGroup) {
		GroupEntity groupEntity = new GroupEntity();
		groupEntity.setRevision(1);
		groupEntity.setType("assignment");
		groupEntity.setId(customGroup.getId());
		groupEntity.setName(customGroup.getName());
		return groupEntity;
	}

	public static List<org.activiti.engine.identity.Group> toActivitiGroups(
			List<CustomGroup> bGroups) {

		List<org.activiti.engine.identity.Group> groupEntitys = new ArrayList<org.activiti.engine.identity.Group>();

		for (CustomGroup bGroup : bGroups) {
			GroupEntity groupEntity = toActivitiGroup(bGroup);
			groupEntitys.add(groupEntity);
		}
		return groupEntitys;
	}
	
//    /** 
//     * 显示流程图 
//     * @return 
//     * @throws Exception 
//     */  
//        public String getProcessPic(HttpServletRequest req) throws Exception {  
//            String procDefId = req.getParameter("procDefId");  
//            ProcessDefinition procDef = repositoryService.createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();  
//            String diagramResourceName = procDef.getDiagramResourceName();  
//            InputStream imageStream = repositoryService.getResourceAsStream(  
//                    procDef.getDeploymentId(), diagramResourceName);  
//            req.setAttribute("inputStream", imageStream);  
//            return null;  
//        }  
//    /** 
//     * 获取跟踪信息 
//     * @return 
//     * @throws Exception 
//     */  
//        public String getProcessMap(HttpServletRequest req) throws Exception {  
//            String procDefId = req.getParameter("procDefId");  
//            String executionId = req.getParameter("executionId");  
//            ProcessDefinition processDefinition = repositoryService  
//                    .createProcessDefinitionQuery().processDefinitionId(procDefId).singleResult();  
//  
//            ProcessDefinitionImpl pdImpl = (ProcessDefinitionImpl) processDefinition;  
//            String processDefinitionId = pdImpl.getId();// 流程标识  
//  
//            ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)  
//                    .getDeployedProcessDefinition(processDefinitionId);  
//            ActivityImpl actImpl = null;  
//  
//            ExecutionEntity execution = (ExecutionEntity) runtimeService  
//                    .createExecutionQuery().executionId(executionId).singleResult();// 执行实例  
//  
//            String activitiId = execution.getActivityId();// 当前实例的执行到哪个节点  
////          List<String>activitiIds = runtimeService.getActiveActivityIds(executionId);  
//              
//  
//            List<ActivityImpl> activitiList = def.getActivities();// 获得当前任务的所有节点  
////          for(String activitiId : activitiIds){  
//            for (ActivityImpl activityImpl : activitiList) {  
//                String id = activityImpl.getId();  
//                if (id.equals(activitiId)) {// 获得执行到那个节点  
//                    actImpl = activityImpl;  
//                    break;  
//                }  
//            }  
////          }  
//  
//            req.setAttribute("coordinateObj", actImpl);  
//            req.setAttribute("procDefId",procDefId );  
//            return "";  
//        }  
}