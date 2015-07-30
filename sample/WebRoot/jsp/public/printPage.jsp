<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
/*
*页面打印缓存页打
*@author zengziwen 2012-9-19
*/
String ctx = request.getContextPath();
%>
<link href="<%=ctx %>/resource/fbrp/ui/css-common/style-common.css" rel="stylesheet" type="text/css" />
<link href="<%=ctx %>/resource/fbrp/ui/css-systems-01/style.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="<%=ctx %>/resource/library/jquery/jquery-1.7.2.min.js" ></script>
<style media=print>    
.Noprint{display:none;}    
.PageNext{page-break-after: always;}  
</style>
<style type="text/css">
<!--
.printContext .doc02_tab{width: 6px;border: 0px;}
-->
</style>
<body onload="javascript:printit();">
<OBJECT id=wb height=0 width=0    classid=CLSID:8856F961-340A-11D0-A96B-00C04FD705A2 name=wb></OBJECT> 
<script>
function printit(){    
  //wb.execwb(6,6);//打印
  if($.browser.mozilla){
	  parent.document.getElementById("printIframe").focus();
  }else{
	  parent.frames("printIframe").focus();
  }
   window.print();
 //wb.execwb(7,1);
} 

function printsetup(){    
	wb.execwb(8,1);   // 打印页面设置    
}   

function printpreview(){    
	wb.execwb(7,1); // 打印页面预览
}
var context_name = "${context_name}";
if(context_name == null || context_name == ""){
	context_name = "printContext";
}
var html = window.parent.document.body.innerHTML;
var context = $(html).find("."+context_name);

for(var i=0;i<context.length;i++){
	document.write(context.eq(i).html());
}

var show = "${show}";

if(context.find(".doc_tab")){
	var doc = $(document).find(".doc_tab");
	
	if(show == "1" || show == "3"){
		doc.find(".hidden").show();
	}
	
	doc.find("input").each(function(){
		var val = $(this).val();
		var t_width = $(this).width();
		var cls = $(this).attr("class");
		var align_text = "left";
		if(cls == "right_text"){
			align_text = "right; float: right";
		}
		var regexp = /\n/gi;
		val = val.replace(regexp, "<br/>");
		var istyle = $(this).attr("type");
		if(istyle.indexOf("hidden") == -1
				&& istyle.indexOf("radio") == -1){
			var str = "<div style=\"white-space: normal; word-wrap: break-word; word-break: break-all; text-align: "+align_text+"; width: "+t_width+"\">" + val + "</div>";
			$(this).after(str);
		}
		if(istyle.indexOf("radio") == -1){
			$(this).remove();
		}
	});
	doc.find("textarea").each(function(){
		var val = $(this).val();
		var t_width = $(this).width();
		var cls = $(this).attr("class");
		var align_text = "left";
		if(cls == "right_text"){
			align_text = "right; float: right";
		}
		var regexp = /\n/gi;
		val = val.replace(regexp, "<br/>");
		var str = "<div style=\"white-space: normal; word-wrap: break-word; word-break: break-all; text-align: "+align_text+"; width: "+t_width+"\">" + val + "</div>";
		$(this).after(str);
		$(this).remove();
	});
	doc.find("p.info").each(function(){
		var _html = $(this).html();
		$(this).parent().append(_html);
		$(this).next("br").remove();
		$(this).remove();
	});
}

if(context.find(".doc02_tab")){
	var doc02 = $(document).find(".doc02_tab");
	var style02 = doc02.attr("style");
	doc02.attr("style", "width: 650px;"+style02);
	doc02.find(".file_box").remove();
	doc02.find("input").each(function(){
		var val = $(this).val();
		var regexp = /\n/gi;
		val = val.replace(regexp, "<br/>");
		var istyle = $(this).attr("type");
		if(istyle.indexOf("hidden") == -1){
			$(this).after(val);
		}
		$(this).remove();
	});
	doc02.find("textarea").each(function(){
		var val = $(this).val();
		var regexp = /\n/gi;
		val = val.replace(regexp, "<br/>");
		var str = "<div style=\""+$(this).attr("style")+"\">" + val + "</div>";
		$(this).after(str);
		$(this).remove();
	});
	doc02.find("table").each(function(){
		var val = $(this).children("tbody").children("tr").eq(0).children("td").eq(0).html();
		if(val.match("请选择")){
			val = "&nbsp;";
		}
		$(this).parent().append(val);
		$(this).remove();
	});
	doc02.find("#title_div").attr("style", "");
	doc02.find(".print_hidden").remove();
	doc02.find("select").remove();
	doc02.find("img").remove();
}

if((show == "2" || show == "3" || show == "5") && context.find(".doc02_tab") && context.find(".mx_doc")){
	var advicedoc = $(document).find(".advice_doc");
	var mxdoc = $(document).find(".mx_doc");
	var doc02 = $(document).find(".doc02_tab");
	var divd = advicedoc.next("#DIV1");
	var div1 = doc02.next("#DIV1");
	mxdoc.remove();
	doc02.remove();
	if(show == "5"){
		divd.remove();
	}
	div1.remove();
}

if(show == "4" || show == "5"){
	$(document).find(".doc").remove();
}
</script>


</body>