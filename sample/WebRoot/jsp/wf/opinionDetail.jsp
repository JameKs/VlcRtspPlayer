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
<link href="<%=ctx %>/resource/fbrp/main/css-common/style-common.css" rel="stylesheet" type="text/css"/>
<link href="<%=ctx %>/resource/fbrp/main/css-systems-01/style.css" rel="stylesheet" type="text/css"/>

<style type="text/css">
 textarea{
 width: 96%; height: 120px;
}
.row-systems-01-text-02 { text-align: left}
.top-center-systems-01-liucheng-01{
	padding-left: 5px;
	font-size: 14px;
	font-weight: bold;
	color: #333333;	
}
.row-systems-01-text-02 input { width: 90%; height: 20px; line-height: 20px;}
</style>
</head>
<body>

<!-- liuchengmain begin -->
<div class="liuchengmain" style="width: 800px;">

<table width="100%" border="0" cellpadding="0" cellspacing="0"  style=" border-collapse:collapse; margin-top: 10px;clear: both;">	
 <tr>
	    <td ><table width="100%" cellspacing="0" cellpadding="0" border="0" class="top-table-systems-01-infolist-01">
	    <tbody><tr>
	      <td class="top-left-systems-01-infolist-01"></td>
	      <td class="top-center-systems-01-infolist-01">意见信息</td>
	      <td class="top-right-systems-01-infolist-01"></td>
	    </tr></tbody>
	    </table></td>
 </tr>
 <tr><td class="center-center-systems-01-infolist-01"><table  class="center-table-systems-01-infolist-01" width="100%">
		
		<tr >
              <td class="main-table-title012" height="33" >意见：</td> 
			   <td class="main-table-title012" height="33" colspan="3"><textarea name="opinion"  readonly="readonly">${approvalOpinion.apprOpinion }</textarea></td> 
		</tr>
</table></td></tr>		
	</table>
</div>
</body>
</html>
