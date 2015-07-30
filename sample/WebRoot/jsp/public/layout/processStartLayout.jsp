<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%String ctx = request.getContextPath();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
    <link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/extjs/css/ext-all.css" />
	<script type="text/javascript" src="<%=ctx%>/resources/extjs/js/ext-all.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
	<style type="text/css">
/* navigation */
html{
	overflow:hidden;
}
.site_nav {
	overflow: hidden;
	line-height: 25px;
	padding-left: 10px;
	background: url(ctx + '/jsp/public/images/navigation/navigation_bg.jpg') repeat-x left top;
	_zoom: 1;
	width:100%;
	height:25px;
}

</style>
</head>

<body style="height:100%">
<div class="site_nav" >
	<div style="float: left;width:60%; ">
		当前位置：<span id="nav_content"></span>
	</div>
	<div id="nav_items" style="float: right;width:10%; ">
		<a href="javascript:window.location.reload();">刷新</a>
	</div>
</div>

<tiles:insertAttribute name="toolbar"/>
<tiles:insertAttribute name="content"/>
<iframe id="printIframe" class="printIframe" style="position: absolute;left: -9999px;" name="printIframe"></iframe>
</body>
</html>