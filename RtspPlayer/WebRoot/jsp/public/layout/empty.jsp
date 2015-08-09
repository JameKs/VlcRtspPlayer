<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
	String ctx = request.getContextPath();

	//设置加载时间过程时显示的颜色
	String color = "color:green";
	Long t = (Long) request.getAttribute("requestTime");
	if (t != null && t > 1000) {
		color = "color:red";
	}
%>
<tiles:insertAttribute name="content"  />