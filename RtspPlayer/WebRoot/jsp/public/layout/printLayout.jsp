<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>

<%String ctx = request.getContextPath();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery/jquery-1.7.2.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery-plugins/jquery.form.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery-ui/jquery-ui-1.8.22.interactions.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery-plugins/jquery.tinyscrollbar.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/fbrp/fbrp-ui.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/tsmp/print-common.js" ></script>

<script type="text/javascript" src="<%=ctx %>/resource/library/ztree/js/jquery.ztree.core-3.3.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/resource/library/ztree/js/jquery.ztree.excheck-3.3.min.js" ></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/My97DatePicker/WdatePicker.js"></script>

<link href="<%=ctx %>/resource/fbrp/ui/css-common/style-common.css" rel="stylesheet" type="text/css" />
<link href="<%=ctx %>/resource/fbrp/ui/css-systems-01/style.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/resource/library/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" >
<script type="text/javascript">
//为全局js提供上下文
var _basePath = '<%=ctx%>';
var _CTX = '<%=ctx%>';

$(function(){
	var ie6 = $.browser.msie&&$.browser.version=='6.0';
	if(ie6){
		if($("html")[0].scrollHeight>$("html").height()){
			$("html").css("overflowY","scroll"); 
	    }
	}else{
		$(document.body).css({"min-width": "1018px","overflow":"scroll"});
	}
});

if(window.frameElement.id=="if_"+parent.window.fbrp_activity_page_id){
	parent.window.hideProgress();
}

function _onlyShowHiddenLi(){
	var btn_ws_hz = $(".btn #_ws_hz");
	var btn_ws = $(".btn #_ws");
	var btn_dy = $(".btn #_hiddenLi");
	btn_ws_hz.hide();
	btn_ws.hide();
	btn_dy.show();
}
</script>
</head>

<body>
<div class="site_nav">
<div class="navigation">
   当前位置：<span id="nav_content"></span>
</div><!-- navigation end -->

<div id="nav_items" class="ngt">
	<b class="ngt_bl"></b>
    <ul class="ngt_ul">
        <li id="nav_items_refresh" class="return"><a href="javascript:window.location.reload();">刷新</a></li>
        <li id="nav_items_back" style="display: none;" ><a href="javascript:history.back();">返回</a></li>
    </ul>
    <b class="ngt_br"></b>
</div>

</div>


 
<table width="100%" cellspacing="0"  cellpadding="0" border="0" align="center"  style="background: #f4f9fd;height:40px;">
	<tr>
		<td align="left"><b>　</b></td>
    	<td>
        	<div class="operator">
            <div class="button1" id='printDiv'>
            	<ul class="btn">
            	<li class="com_printer_btn" id="_ws_hz"><a href="#" onclick="goPrint();" ><span>打印文书和回执</span></a></li>
            	<li class="com_printer_btn" id="_hiddenLi" style="display: none;"><a href="#" onclick="goPrint();" id="_printer_btn"><span>打印</span></a></li>
            	<li class="com_printer_btn" id="_ws"><a href="#" onclick="goPrint2();" ><span>打印文书</span></a></li>
                </ul>
            </div>
            </div>
        </td>
    </tr>
</table>
<!-- <input type=button name=button_back value="返回" onclick="javascript:window.history.go(-1)"> -->
 
<tiles:insertAttribute name="content"/>
<iframe id="printIframe" style="position: absolute;left: -9999px;" name="printIframe"></iframe>
</body>
</html>