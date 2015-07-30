<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%
/**
* 流程部署管理页面。
* 作者：zengziwen  zengziwen@foresee.cn
* 时间:  2012-07-28
**/
String ctx = request.getContextPath();
String msg=(String)request.getAttribute("msg");
if(msg==null){
	msg="";
}else{
	request.setAttribute("msg","");
}
%> 
<script type="text/javascript">
$(function(){
	var msg = "<%=msg%>";
	if(msg!='') {
		if(msg=='true'){
			fbrp.utils.showInfo("部署成功！",'ok');
		}else if(msg=='false'){
			fbrp.utils.showInfo("部署失败!",'error');
		}
	}
})
function onQueryx(){
	rsTable.loadPagedResult('<%=ctx%>/workflow/deploymentManage.do?gotoPage',"");
}

var tableSetting = {
	url:'<%=ctx%>/workflow/deploymentManage.do?gotoPage',
	parameter:{},
	toolbar:[{text:'部署流程',icon:'<%=ctx%>/resource/icons/toolbar/add.gif',onClick:function(){
		winDeployProcess();
	}}],
	operator:[
	<%-- 	{text:'启动流程',icon:'<%=ctx%>/resource/icons/toolbar/add.gif',onClick:function(data){
			if(confirm('确实要启动吗?')) {
				location.href = "deploymentManage.do?startprc&id=" + data.id;
			}
		}}, --%>
		{text:'卸载部署',icon:'<%=ctx%>/resource/icons/operator/delete.gif',onClick:function(data){
			if(confirm('确实要卸载该流程吗?')) {
				$.ajax({
					url:"deploymentManage.do?delete&id=" + data.deploymentId,
					type:"post",
					async:false,
					data:"",
					success:function(data){
						 if(data==true){
							 onQueryx();
							 deleteOkDialog.open();
						 }else{
							 deleteFailDialog.open();
						 }
					}
				});
			}
		}}
	]
};
</script>
<script type="text/javascript">
<!--
	function check(){
		var form= document.forms[0];
		if(form.file.value==''){
			alert('请选择部署文件!');
	        return false;
	    }else{
	        return true;
	    }
    }
    
	var confirmSetting = {
			buttons:[{text:'确定',icon:'<%=ctx%>/resource/fbrp/ui/images-systems-01/drop_list/certain_icon.gif',onClick:function(){
				deleteOkDialog.close();
				deleteFailDialog.close();
				deployOkDialog.close();
				deployFailDialog.close();
			       }}
			 ]
     };
	
	var uploadSetting = {
			buttons:[
			     {text:'确定',icon:'<%=ctx%>/resource/fbrp/ui/images-systems-01/drop_list/certain_icon.gif',onClick:function(){
			 		if(!check()) return;
			    	 uploadForm.submit();
			     }},
				  {text:'取消',icon:'<%=ctx%>/resource/fbrp/ui/images-systems-01/drop_list/certain_icon.gif',onClick:function(){
					 	 readyDeployProcess.close();
				}}
		]
     };

	function winDeployProcess(){
		readyDeployProcess.open();
		var html =' <form action="deploymentManage.do?fileUpload" enctype="multipart/form-data"  name="uploadForm"   id="uploadForm"  method="post"  style="padding-right: 10px;" >'
		html+='<table><tr><td>文件选择：</td><td><input type="file" name="file" size="50" ></td></tr></table>';
		html+='  </form>';
		$('#readyDeployProcess_td').html(html);
	}
//-->
</script>
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<%--头行 结束--%>
<f:table header="流程列表"  initValue="pr"  id="rsTable"  setting="tableSetting"  showStripe="true" >
<f:column title="部署编号" property="id" />
<f:column title="部署Key" property="key" />
<f:column title="部署名称" property="name" />
<f:column title="部署版本" property="version" />
</f:table>

<f:dialog id="readyDeployProcess" header="部署流程"  width="480" height="100" setting="uploadSetting" >
<br>
<table width="100%" cellspacing="0" cellpadding="0"   border="0" >
	<tbody>
	<tr>
		<td align="right"  id="readyDeployProcess_td" >
	   	</td>  
     </tr>
     </tbody>
</table>
</f:dialog>

<f:dialog id="deleteFailDialog" header="提示"  width="300" height="100" setting="confirmSetting" >
<br>卸载失败!
</f:dialog>
<f:dialog id="deleteOkDialog" header="提示"  width="300" height="100" setting="confirmSetting" >
<br>卸载成功!
</f:dialog>
<f:dialog id="deployOkDialog" header="提示"  width="300" height="100" setting="confirmSetting" >
<br>部署成功!
</f:dialog>
<f:dialog id="deployFailDialog" header="提示"  width="300" height="100" setting="confirmSetting" >
<br>部署失败!
</f:dialog>