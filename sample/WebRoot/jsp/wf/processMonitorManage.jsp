<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%
String ctx = request.getContextPath();
%>
<style type="text/css">

#leftDiv{ float:left; width:18%; height:100%; }
#rightDiv{
	width:80%;
	height:100%;
	float:left;
	margin-left:10px;
}

</style>
<script type="text/javascript">
 var setting = {
		 view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false,
				expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
			},
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			},
			callback:{
				  /* onClick:zTreeOnClick */
			},
			async:{
				enable:true,
				url:'#',
				autoParam:['id','name']
			}
    
	};

function getProcessIntanceList(id){
	$.post("manager.do?getProcessIntanceList",{id:id},function(data){
	
		$.each(data,function(i){
			
			$(".tableDiv>table").eq(0).append("<tr><td>"+data[i].name+"</td><td>活动</td></tr>");
		});
		  
	});
}

function reset(){
	var form = document.forms[0];
	var input = form.getElementsByTagName("input");
	for(var i=0;i<input.length;i++){
		input[i].value="";
	}
	var select = form.getElementsByTagName("select");
	for(var i=0;i<select.length;i++){
		select[i].value="";
	}
	document.forms[0].submit();
}

function onQueryx(){
	alert("OK");
}

var tableSetting1 = {
	url:'#',
	parameter:{},
	toolbar:[
		{text:'查看流程',icon:'<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/tool_addbtn.gif',onClick:function(e){
			alert("OK");
		}},
		{text:'取消流程',icon:'<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/tool_addbtn.gif',onClick:function(e){
			alert("OK");
		}},
		{text:'查看流程变量',icon:'<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/tool_addbtn.gif',onClick:function(e){
			alert("OK");
		}},
		{text:'异常管理',icon:'<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/tool_addbtn.gif',onClick:function(e){
			alert("OK");
		}},
		{text:'删除',icon:'<%=ctx%>/resource/icons/toolbar/delete.gif',onClick:function(e, sels){
			alert("OK");
		}}
	]
};

var tableSetting2 = {
	url:'#',
	parameter:{}
}
</script>
<div id="leftDiv">
  <f:panel header="模板树" height="400px;" width="100%;">
    <f:tree setting="setting" initValue="treeData" id="treeDemo" />
  </f:panel>
</div>
<div id="rightDiv">
<div id="rightTop">
<form action="" id="firstForm">
	<f:queryPanel onQuery="onQueryx" onReset="reset">
		<tr>
			<td class="name">流水号：</td>
			<td class="text"><input type="text" /></td>
			<td class="name">模板名称：</td>
			<td class="text"><input type="text" /></td>
			<td class="name">主版本号：</td>
			<td class="text"><input type="text" /></td>
		</tr>
		<tr>
			<td class="name">主题：</td>
			<td class="text"><input type="text" /></td>
			<td class="name">状态：</td>
			<td class="text">
				<select><option>状态1</option><option>状态2</option><option>状态3</option></select>
			</td>
			<td class="name">发起者ID：</td>
			<td class="text"><input type="text" /></td>
		</tr>
		<tr>
			<td class="name">发起时间：</td>
			<td class="text">
			<select><option>时间1</option><option>时间2</option><option>时间3</option></select>
			至
			<select><option>时间1</option><option>时间2</option><option>时间3</option></select>
			</td>
		</tr>
	</f:queryPanel>
</form>
<f:table header="流程实例列表" initValue="pr1" id="rsTable1" setting="tableSetting1" columnHeader="radio" showStripe="true">
<f:column title="流程名称" property="name" />
<f:column title="流程状态" property="workflowStatus" />
<f:column title="流程跟踪" property="track" />
<f:column title="部署编号" property="id" />
<f:column title="发布时间" property="startTime" />
<f:column title="结束时间" property="endTime" />
<f:column title="状态" property="status" />
<f:column title="流水号" property="workflowNumber" />
</f:table>
</div>
<br/>
<div id="rightBottom">
<f:table header="任务列表" initValue="pr2" id="rsTable2" setting="tableSetting2" showStripe="true">
<f:column title="结点名称" property="nodeName" />
<f:column title="分派模式" property="model" />
<f:column title="领取人" property="get" />
<f:column title="执行人" property="do" />
<f:column title="优先级别" property="priority" />
<f:column title="创建时间" property="startTime" />
<f:column title="完成时间" property="endTime" />
<f:column title="状态" property="stauts" />
</f:table>
</div>
</div>