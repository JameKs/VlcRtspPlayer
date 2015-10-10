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
var html = window.parent.document.body.innerHTML;
var context = $(html).find(".printContext");

for(var i=0;i<context.length;i++){
	document.write(context.eq(i).html());
}

$(document).find(".doc").remove();
</script>


</body>