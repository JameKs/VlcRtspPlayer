
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%>
<%
	String ctx = request.getContextPath();
	String requestTime = request.getParameter("requestTime");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title></title>
</head>
<body style="height:100%">
	<form id="form1">
		<input type="hidden" id="procInsId" value="${qjxx.procInsId }">
		<input type="hidden" id="procDefId" value="${qjxx.procDefId }">
		<input type="hidden" id="taskId" value="${qjxx.taskId }"> 
		<input type="hidden" id="id" value="${qjxx.id }">
		<input type="hidden" id="yhrwId" value="${qjxx.id }">
		<input type="hidden" id="rwId" value="${qjxx.rwId }">
		<table>
			<tr>
				<td>请假类型:</td>
				<td><input type="text" id="rwlxDm" value="${qjxx.rwlxDm }"></td>
			</tr>
			<tr>
				<td>开始时间:</td>
				<td><input type="text" id="kssj" value="${qjxx.kssj }">
				</td>
			</tr>
			<tr>
				<td>结束时间:</td>
				<td><input type="text" id="jssj" value="${qjxx.jssj }">
				</td>
			</tr>
			<tr>
				<td>请假原因:</td>
				<td><input type="text" id="qjyy" value="${qjxx.qjyy }"
					readonly="readonly"></td>
			</tr>
			<c:forEach var="spyj" items="${spyjList}">
				<tr>
					<td>审批意见:</td>
					<td><input type="text" value="${spyj.spyj }"
						readonly="readonly"></td>
				</tr>
			</c:forEach>
			<tr>
				<td>审批意见:</td>
				<td><input type="textarea" id="spyj" value=""></td>
			</tr>
			<tr>
				<td>审批:</td>
				<td>
					<input type="radio" name="spjg" name="退回" value="0">
					<input type="radio" name="spjg" name="通过" value="1">
					<input type="radio" name="spjg" name="拒绝" value="2">
				</td>
			</tr>
			
		</table>

	</form>
	

</body>
</html>
