<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%
/**
*我的待办任务列表页面
* 作者：zengziwen  zengziwen@foresee.cn
* 时间:  2012-09-08
**/
String ctx = request.getContextPath();
%> 
<script>
var tableSetting = {
		url:'<%=ctx%>/public-access/workflow/taskManage.do',
		parameter:{},
		toolbar:[],
		operator:[
		         {text:'开始处理',icon:'<%=ctx %>/pages/fbrp/images/crud/ico-edit.gif',onClick:function(data){
		        	 gotoBusinessForm(data.wfUrl ,data.wfTaskId,data.wfActNodeId,data.wfActNodeName,data.wfProcessInstanceId,data.wfProcessDefinitionId,data.ywId,data.ywName);
		         }}
	   ]
};
function  gotoBusinessForm(wfUrl ,wfTaskId,wfActNodeId,wfActNodeName,wfProcessInstanceId,wfProcessDefinitionId,ywId,ywName){
	var form = document.getElementById('taskFormId');
	form.action="<%=ctx%>"+wfUrl;
	$("#id").val(ywId);
	$("#p_ywId").val(ywId);
	$("#p_ywName").val(ywName);
	$("#p_wfTaskId").val(wfTaskId);
	$("#p_wfProcessInstanceId").val(wfProcessInstanceId);
	$("#p_wfProcessDefinitionId").val(wfProcessDefinitionId);
	$("#p_wfActNodeId").val(wfActNodeId);
	$("#p_wfActNodeName").val(wfActNodeName); 
	form.submit();
}
</script>
<f:table header="待办任务列表" initValue="taskRes" id="rsTable" setting="tableSetting" >
	<f:column title="任务编号" property="wfTaskId"  width="80px"/>
	<f:column title="任务名称" property="ywName" width="30%" />
	<f:column title="任务级别" property="taskLev" width="90px" />
	<f:column title="创建时间" property="wfCreateTime" width="90px" />
	<f:column title="执行反馈 "property="excBckLev" width="80px" />
	<f:column title="当前步骤" property="wfActNodeName" width="10%" />
</f:table>

<form  action=""  id="taskFormId" name="taskForm" method="post">
	<input type="hidden"  id="id" name="id"/>
	<input type="hidden"  id="p_ywId" name="p_ywId"/>
	<input type="hidden"  id="p_ywName" name="p_ywName"/>
	<input type="hidden"  id="p_wfTaskId" name="p_wfTaskId"/>
	<input type="hidden"  id="p_wfProcessInstanceId" name="p_wfProcessInstanceId"/>
	<input type="hidden"  id="p_wfProcessDefinitionId" name="p_wfProcessDefinitionId"/>
	<input type="hidden"  id="p_wfActNodeId" name="p_wfActNodeId"/>
	<input type="hidden"  id="p_wfActNodeName" name="p_wfActNodeName"/>
	<input type="hidden"  id="p_sp" name="p_sp"   value="true"/><!-- 工作流审批标记，不能删除！ -->
</form>