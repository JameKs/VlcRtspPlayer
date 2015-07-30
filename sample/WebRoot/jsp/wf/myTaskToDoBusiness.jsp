<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%
String ctx = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<title>系统</title>
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery-ui/jquery-ui-1.8.22.interactions.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/tree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/tree/js/jquery.ztree.excheck-3.2.js"></script>
<link href="<%=ctx %>/resource/fbrp/main/css-common/style-common.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/resource/fbrp/main/css-systems-01/style.css" rel="stylesheet" type="text/css">
<link  rel="stylesheet" href="<%=ctx %>/pages/fbrp/scripts/tree/css/zTreeStyle.css" type="text/css" >

<style type="text/css">
 textarea{
 width: 96%; height: 80px;
}
.row-systems-01-text-02 { text-align: left}
.top-center-systems-01-liucheng-01{
	padding-left: 5px;
	font-size: 14px;
	font-weight: bold;
	color: #333333;	
}
.row-systems-01-text-02 input { width: 90%; height: 20px; line-height: 20px;}
.title{ background:#ADDEFF;
font-size: 14px;
font-weight: bolder;
padding: 4px;
text-align: left;
height: 23px; color:black;line-height: 23px; vertical-align: middle;padding-left: 10px;}
.operaBtn {
	background-color:#C0D8F0;
	height:10px;
	border: 1px solid #7baddd; 
	color:#000; padding-left:10px;padding-right:10px; padding-top:3px;padding-bottom:3px;
    display:inline;
    vertical-align:middle;
	font-size:14px;
     margin-left:5px;
}
input{ width: 100px; height: 15px;}
select {
	height: 18px;
}
</style>
	</head>
<body>
<div>
  <div class="title">待办业务查询条件</div>
  <div style="margin-top: 5px;">
    <table width="100%">
      <tr><td >业务名称:</td><td><input type="text"/></td><td>业务类型:</td><td><select><option>类型1</option><option>类型2</option><option>类型3</option></select></td>
      
      <td >业务状态：</td><td><select><option>状态1</option><option>状态2</option><option>状态3</option></select></td>
      <td >申请人：</td><td><input type="text"/></td>
      <td >申请时间：</td><td><input type="text" id="applyTime"/><img onclick="WdatePicker({el:'applyTime'})" src="<%=ctx %>/pages/fbrp/scripts/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"></td>
      <td >接收时间：</td><td><input type="text" id="receiveTime"/><img onclick="WdatePicker({el:'receiveTime'})" src="<%=ctx %>/pages/fbrp/scripts/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle"></td>
     <td><a href="#" class="operaBtn">查询</a><a href="#" class="operaBtn">重置</a></td>
      </tr>
    
    </table>
  </div>
</div>




<table width="100%" border="0" cellpadding="0" cellspacing="0"  style=" border-collapse:collapse; margin-top: 10px;clear: both;">
 <tr>
	    <td ><table width="100%" cellspacing="0" cellpadding="0" border="0" class="top-table-systems-01-infolist-01">
	    <tbody><tr>
	      <td class="top-left-systems-01-infolist-01"></td>
	      <td class="top-center-systems-01-infolist-01">待办业务</td>
	      <td class="top-right-systems-01-infolist-01"></td>
	    </tr></tbody>
	    </table></td>
 </tr>
 <tr><td class="center-center-systems-01-infolist-01"><table  class="center-table-systems-01-infolist-01" width="100%">
		<tr >
		     <td width="255" class="main-table-title013"  height="33">业务编号</td>
			<td width="255" class="main-table-title013"  height="33">业务类型</td>
			<td width="255" class="main-table-title013"  height="33">业务名称</td>
			<td width="260" class="main-table-title013"  height="33">业务状态</td>
			<td width="255" class="main-table-title013"  height="33">版本</td>
			<td width="260" class="main-table-title013"  height="33">申请人</td>
			<td width="260" class="main-table-title013"  height="33">申请时间</td>
			<td width="260" class="main-table-title013"  height="33">接收时间</td>
			<td width="260" class="main-table-title013"  height="33">操作</td>
		</tr>
		 
		<tr>
			    <td  height="33">001</td> 
			   <td  height="33">类型1</td> 
			   <td  height="33">请假</td>
			   <td  height="33">状态1</td>
			   <td  height="33">3.0</td>
			   <td  height="33">LinKing</td>
			   <td  height="33">2012-08-15</td>
			   <td  height="33">2012-09-01</td>
			   <td  height="33"><a href="#">查看流转日志</a>  <a href="#">开始办理</a></td>
		</tr>
		
		<tr>
			    <td  height="33">001</td> 
			   <td  height="33">类型1</td> 
			   <td  height="33">请假</td>
			   <td  height="33">状态1</td>
			   <td  height="33">3.0</td>
			   <td  height="33">LinKing</td>
			   <td  height="33">2012-08-15</td>
			   <td  height="33">2012-09-01</td>
			   <td  height="33"><a href="#">查看流转日志</a>  <a href="#">开始办理</a></td>
		</tr>
	
</table></td></tr>		
	</table>
	</body>

	</html>