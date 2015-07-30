<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/extjs/css/ext-all.css" />
<link rel="stylesheet" href="<%=ctx%>/resources/ztree/css/demo.css" type="text/css">
<link rel="stylesheet" href="<%=ctx%>/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=ctx%>/resources/extjs/js/ext-all.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ztree/js/jquery.ztree.excheck-3.5.js"></script>

</head>

<body style="height:100%;">
	<tiles:insertAttribute name="content" />
</body>
</html>