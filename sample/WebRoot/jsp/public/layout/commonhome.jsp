<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%String ctx = request.getContextPath();%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><tiles:insertAttribute name="title" ignore="true" /></title>
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
<link href="<%=ctx %>/resource/library/ztree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" >
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
		if(screen.width<=1024){
			$(document.body).css({"width": "1006px"});	
		}
		
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
	var containFlag="${containLd}";
	if("false"==containFlag){
		jQuery("#set1").css("display","none");
		jQuery("#set2").css("display","none");
	     jQuery("#nav_items_refresh").css("background","none");
	}
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
function qhsy(){
	
	var value=$("#qhsy").val();
	  window.location.href='<%=ctx%>/home.do?qhsy&syType='+ value;
	
}
/**
 * 设置默认首页
 */
function saveDefaultPage(){
	var value=$("#qhsy").val();
	$.ajax({
		type : 'post',
		url : "<%=ctx%>/home.do?szmrsy&syType="+ value,
	   async:false,//同步ajax
	  success: function (data){ 
		    alert("设置成功!");
		  },
	  error:function(){
		  alert("设置默认首页失败！");
	  },
	  datatype:'text'
	});
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
         <li class="pre" id="set1"><a href="#" onclick="saveDefaultPage()">设置默认首页</a></li>
        <li class="change" id="set2"><a href="#" onclick="qhsy()">切换<span></span>首页</a></li>
       <li id="nav_items_refresh" class="return" ><a href="javascript:window.location.reload();">刷新</a></li>
        
    </ul>
    <b class="ngt_br"></b>
</div>
<%
	String color = "color:green";
	Long t = (Long)request.getAttribute("fbrpRequestTimeMillis");
	if(t!=null&&t>1000){
		color = "color:red";
	}
%>
<div style="float: right;color: gray;"> 
            
        请求耗时:<span style="<%=color %>;margin-right:1px;">${fbrpRequestTimeMillis}</span>毫秒&nbsp;&nbsp;<span style="color:#d30303;"> 统计数据产生于每天22:00至第二天6:00</span>&nbsp;</div>

</div>


<tiles:insertAttribute name="content"/>
<f:dialog id="fbrp_common_dialog" width="500" height="280" header="提示"> </f:dialog>
</body>
</html>