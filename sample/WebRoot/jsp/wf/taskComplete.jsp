<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%
/**
* 任务处理页面
* 作者：zengziwen  zengziwen@foresee.cn
* 时间:  2012-07-30
**/
String ctx = request.getContextPath();
String msg=(String)request.getAttribute("msg");
String id=(String)request.getAttribute("id");

%> 
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css"  href="<spring:theme code="commonStyleSheet"/>"/>
<link rel="stylesheet" type="text/css"  href="<spring:theme code="generalStyleSheet"/>"/>
<link  rel="stylesheet" href="<%=ctx %>/pages/fbrp/scripts/tree/css/zTreeStyle.css" type="text/css" >
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/tree/js/jquery.ztree.core-3.2.js"></script>
<title>任务处理</title>
</head>
<script>
if("<%=msg%>"!='') {
	alert(11);  
	alert("<%=msg%>");
	window.location.href="list";
}

function checkValue(){
	var desc = $("#desc");
	var nextUser = $("#nextUser");
	
	if(desc.val().length==0){
		alert('处理意见不能为空!');
		desc.focus();
		return false;
	}
	if(desc.val().length>=1000){
		alert('处理意见不能多于1000字符!');
		desc.focus();
		return false;
	}
	
	if(nextUser.val().length==0){
		alert('选择下一个处理人!');
		nextUser.focus();
		return false;
	}
	
	if(!confirm("确定要提交吗？")){
		return false;
	}
	
	document.forms[0].submit();
	return true;
}

</script>
<body>
<%--  <form id="taskform" action="<%=ctx %>/tilesview/wftask/complete" method="post">
 <table width="100%" border="0" cellpadding="0" cellspacing="0"  style=" border-collapse:collapse; margin-top: 10px;clear: both;" >
 <tr height="40px"  bgcolor="#DBE5F1"> 
	<td colspan="4" align="center" ><b>任务处理</b><input type="hidden"  value="<%=id%>" name="id"></td>
</tr>
 	<tr> <td  width="30%" align="right" >任务名称：</td><td>${name}<td> </tr>
 	<tr> <td width="30%" align="right" >创建时间：</td><td><fmt:formatDate value="${createTime}" pattern="yyyy-MM-dd HH:mm:ss"/><td> </tr>
 	<tr> <td colspan=2></td> </tr>
 	<tr> <td width="30%" align="right" >处理人：</td><td>${assignee}<td> </tr>
 	<tr> <td width="30%" align="right" >处理意见：</td><td><textarea id = "desc" name="desc" cols="60" rows="6"></textarea><font size=2 color=red>*必填</font><td> </tr>
 	<tr> <td colspan=2></td> </tr>
 	<tr> <td width="30%" align="right" >下一个处理人：</td>
 	<td><select style="width: 200px;" id="nextUser" name="nextUser">
 		<option value=""> </option>
 		<option value="kermit"> kermit</option>
 		<option value="gonzo"> gonzo</option>
 		
 		</select><font size=2 color=red>*必填</font></td> </tr>
 	<tr> <td colspan=2 align="center">
 	<a class="buttonnew"  id="commit"  onclick="return checkValue()"><img  border="0" style="display: inline; vertical-align: middle;" src="<%=ctx %>/pages/fbrp/images/crud/submit.gif">提交</a>&nbsp;&nbsp;
 	<a class="buttonnew" id="cancel" href="list" ><img border="0" style="display: inline; vertical-align: middle;"  src="<%=ctx %>/pages/fbrp/images/crud/delete.gif">取消</a>
 </table>
 </form> --%>
</body>
</html>
