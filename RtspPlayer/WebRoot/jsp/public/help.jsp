<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%String ctx = request.getContextPath();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>资料下载</title>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/others/json2.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery-plugins/jquery.form.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery-ui/jquery-ui-1.8.22.interactions.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery-plugins/jquery.tinyscrollbar.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/fbrp/fbrp-ui.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/tsmp/tsmp.js" ></script>

<script type="text/javascript" src="<%=ctx %>/resource/library/ztree/js/jquery.ztree.core-3.3.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/ztree/js/jquery.ztree.excheck-3.3.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/My97DatePicker/WdatePicker.js"></script>

<link href="<%=ctx %>/resource/fbrp/ui/css-common/style-common.css" rel="stylesheet" type="text/css" />
<link href="<%=ctx %>/resource/fbrp/ui/css-systems-01/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
select {BEHAVIOR: url('<%=ctx%>/resource/fbrp/ui/css-htc/selectBox.htc'); font-family: "宋体"; vertical-align: middle; font-size: 12px; cursor: hand; *border: none; border: none\9;}
</style>
<script type="text/javascript">
//为全局js提供上下文
var _basePath = '<%=ctx%>';
var _CTX = '<%=ctx%>';

$(function(){
	var ie6 = $.browser.msie&&$.browser.version=='6.0';
	if(ie6){
		$("input[type=text]").addClass("ipt");
		if($("html")[0].scrollHeight>$("html").height()){
			$("html").css("overflowY","scroll"); 
	    }
	}else{
		$(document.body).css({"min-width": "1006px","overflow":"scroll"});
	}
	$.ajaxSetup({
		error:function(jqXHR,textStatus,errorThrown){
			fbrp.utils.handleException(jqXHR.responseText);
		}
	});
});
if(window.frameElement.id=="if_"+parent.window.fbrp_activity_page_id){
	parent.window.hideProgress();
}
$(document).keydown(function(e){
	if(e.keyCode===8){
		var t = $(e.target);
		if(!t.is("input[type=text]")&&!t.is("textarea")){
			return false;
		}
	}
});
</script>
</head>
<body class="help_body">
<table width="100%">
<tbody>
<tr>
<td width="50%" valign="top" >
<span style="font-size: 30px;">税源管理</span>
	<ul class="help">
	    <c:forEach items="${list1 }" var="map1">
	    <c:if test="${map1.WEB_URL != null and map1.WEB_URL != '' }">
	    <li><span><a style="font-size: 15px;" href="<%=ctx %>/public-access/ptwd.do?download&webUrl=${map1.WEB_URL}">${map1.WEB_NAME }</a></span></li>
	    </c:if>
	    </c:forEach>
	</ul>

	<div style="font-size: 15px;font-weight: bold; margin-top: 20px; text-align: left; margin-left: 60px;"><span>支持邮箱：</span><a style="font-size: 15px;font-weight: bold;" href='mailto:syglpt@gdltax.gov.cn'>syglpt@gdltax.gov.cn</a> </div>
	
</td>

<td width="50%" valign="top">
<span style="font-size: 30px;">电子办税及电子档案</span>
<ul class="help">
	<li><span style="font-size: 20px; padding-left: 0; background: none;">文档</span></li>
    <c:forEach items="${list2 }" var="map2">
    <c:if test="${map2.WEB_URL != null and map2.WEB_URL != '' }">
    <li><span><a style="font-size: 15px;" href="<%=ctx %>/public-access/ptwd.do?download&webUrl=${map2.WEB_URL}">${map2.WEB_NAME }</a></span></li>
    </c:if>
    </c:forEach>
</ul>

<ul class="help" style="margin: -20px auto 30px;">
	<li><span style="font-size: 20px; padding-left: 0; background: none;">视频</span></li>
    <c:forEach items="${list3 }" var="map3">
    <c:if test="${map3.WEB_URL != null and map3.WEB_URL != '' }">
    <li><span><a style="font-size: 15px;" href="<%=ctx %>/public-access/ptwd.do?download&webUrl=${map3.WEB_URL}">${map3.WEB_NAME }</a></span></li>
    </c:if>
    </c:forEach>
</ul>

<ul class="help" style="margin: -20px auto 30px;">
	<li><span style="font-size: 20px; padding-left: 0; background: none;">软件</span></li>
    <c:forEach items="${list4 }" var="map4">
    <c:if test="${map4.WEB_URL != null and map4.WEB_URL != '' }">
    <li><span><a style="font-size: 15px;" href="<%=ctx %>/public-access/ptwd.do?download&webUrl=${map4.WEB_URL}">${map4.WEB_NAME }</a></span></li>
    </c:if>
    </c:forEach>
</ul>
</td>
</tr>
</tbody>
</table>
</body>
</html>