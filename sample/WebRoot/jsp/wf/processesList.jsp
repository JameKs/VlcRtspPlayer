<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%
/**
* 流程管理页面
* 作者：zengziwen  zengziwen@foresee.cn
* 时间:  2012-07-28
**/
String ctx = request.getContextPath();
String msg=(String)request.getAttribute("msg");
%> 
<script type="text/javascript">
	if("<%=msg%>"!='') alert("<%=msg%>");
	
	function show(id){
		var w=833,h=465;
		var vLeft = (window.screen.availWidth-w)/2;
		var vTop = (window.screen.availHeight-h)/2;
		vTop = vTop - 50;if(vTop<0) vTop = 0;
		var methodArray=['showHisPic','showPic','taskLog'];
		var method=methodArray[1];
	    var isHistory=$(id).attr('isHistory');
	    var isLog=$(id).attr('isLog');
 
	   if(isHistory=='true'){
	          method=methodArray[0];
	   }

	   if(isLog=='true'){
	          method=methodArray[2];
	   }
    
		var url = '<%=ctx%>/public-access/workflow/processManage.do?'+method+'&processDefId='+id;
		
		window.showModalDialog(url,null,'dialogWidth:'+w+'px;dialogHeight:'+h+'px;dialogLeft:'+vLeft+';dialogTop:'+vTop+';scroll:auto;status:no;resizable:yes;help:no;');
		return false;
	}
	
	var tableSetting1 = {
		url:'<%=ctx%>/public-access/workflow/processManage.do?gotoPage',
		parameter:{},
		operator:[
			{text:'删除流程',icon:'<%=ctx%>/resource/icons/operator/delete.gif',onClick:function(data){
				if(confirm('确实要删除吗?')) {
					location.href = "processManage.do?delete&id=" + data.processInstanceId;
				}
			}}
		],
		cellRender:{
      	  "track":function(value, data){
      		return "<a href=\"" + data.processDefinitionId + "&executionId=" + data.id + "&processInstanceId=" + data.processInstanceId + "\" onclick=\"javascript:return show(this)\" isHistory='false'>" + value + "</a>";
      	  },
      	  "log":function(value, data){
      		return "<a href=\"" + data.processDefinitionId + "&executionId=" + data.id + "&processInstanceId=" + data.processInstanceId + "\" onclick=\"javascript:return show(this)\" isHistory='true' isLog='true' >" + value + "</a>";
      	  }
        }
	};
	var tableSetting2 = {
		url:'<%=ctx%>/public-access/workflow/processManage.do?gotoPageForHis',
		parameter:{},
		cellRender:{
		  "deleteReason":function(value, data){
			return (data.deleteReason==null ? '已完成' : data.deleteReason);
		  },
	      "track":function(value, data){
	      	return "<a href=\"" + data.processDefinitionId + "&executionId=" + data.id + "&processInstanceId=" + data.id + "\" onclick=\"javascript:return show(this)\" isHistory='false'>" + value + "</a>";
	      },
	      "log":function(value, data){
	      	return "<a href=\"" + data.processDefinitionId + "&executionId=" + data.id + "&processInstanceId=" + data.id + "\" onclick=\"javascript:return show(this)\" isHistory='true' isLog='true' >" + value + "</a>";
	      }
	    }
	}
</script>

<f:switchPanel>
<f:switchItem header="活动流程" id="hdlc">
<f:table header="活动流程" initValue="pr1" id="rsTable1" setting="tableSetting1" showStripe="true" showHeader="false" toolbarLocation="hdlc" >
<f:column title="流程编号" property="id" />
<f:column title="流程状态" property="status" />
<f:column title="流程跟踪" property="track" />
<f:column title="审核记录" property="log" />
<f:column title="部署编号" property="processDefinitionId" />
</f:table>
</f:switchItem>

<f:switchItem header="历史流程" id="lslc">
<f:table header="历史流程" initValue="pr2" id="rsTable2" setting="tableSetting2" showStripe="true" showHeader="false" toolbarLocation="lslc" >
<f:column title="流程编号" property="id" />
<f:column title="流程状态" property="deleteReason" />
<f:column title="流程跟踪" property="track" />
<f:column title="审核记录" property="log" />
<f:column title="部署编号" property="processDefinitionId" />
</f:table>
</f:switchItem>
</f:switchPanel>