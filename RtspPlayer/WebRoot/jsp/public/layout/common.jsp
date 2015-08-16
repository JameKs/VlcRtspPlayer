<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<link rel="stylesheet" href="<%=ctx%>/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<script type="text/javascript" src="<%=ctx%>/resources/extjs/js/ext-all.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ztree/js/jquery.ztree.core-3.5.js"></script>
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
<script type="text/javascript">
	
	var _CTX = '<%=ctx%>';//为全局js提供上下文

	$(function() {
		var ie6 = $.browser.msie && $.browser.version == '6.0';
		if (ie6) {
			if (screen.width <= 1024) {
				$(document.body).css({
					"width" : "1006px"
				});
			}

			$("input[type=text]").addClass("ipt");
			if ($("html")[0].scrollHeight > $("html").height()) {
				$("html").css("overflowY", "scroll");
			}
		} else {
			$(document.body).css({
				"min-width" : "1006px",
				"overflow" : "scroll"
			});
		}
		$.ajaxSetup({
			error : function(jqXHR, textStatus, errorThrown) {
				fbrp.utils.handleException(jqXHR.responseText);
			}
		});

		jQuery("#nav_items_refresh").css("background", "none");

	});
	if ((window.frameElement)
			&& (window.frameElement.id == "if_"
					+ parent.window.fbrp_activity_page_id)) {
		parent.window.hideProgress();
	}
	$(document).keydown(function(e) {
		if (e.keyCode === 8) {
			var t = $(e.target);
			if (!t.is("input[type=text]") && !t.is("textarea")) {
				return false;
	}
	}
});
</script>
</head>

<body style="height:100%;">
<form>
	<input type="hidden" id="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
</form>
<div class="site_nav" >
	<div style="float: left;width:60%; ">
		当前位置：<span id="nav_content"></span>
	</div>
	<div style="color: gray; float: right;width:20%;">
		请求耗时:<span style="<%=color%>;margin-right:1px;">${requestTime}</span>毫秒&nbsp;&nbsp;
	</div>
	<div id="nav_items" style="float: right;width:10%; ">
		<a href="javascript:window.location.reload();">刷新</a>
	</div>
</div>
	<tiles:insertAttribute name="content" />
</body>
</html>