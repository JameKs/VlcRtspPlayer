<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <% String ctx = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
<script type="text/javascript">
$(function(){
	var h = $(document).height();
	$('#table').height(h-30);
	$('#hinder').toggle(hideLeft,showLeft);
});
function hideLeft(event){
	$('#hinder').attr("src","<%=ctx %>/jsp/frame/img/open.gif");
	parent.document.getElementById("mset").cols = "0,7,*";
}
function showLeft(){
	$('#hinder').attr("src","<%=ctx %>/resource/fbrp/common/image/close.gif");
	parent.document.getElementById("mset").cols = "220,7,*";
}
</script>
</head>
<body style="background-color: #4988cf;margin: 0px;">
<table id="table" border="0" style="margin: 0px;">
<tr>
<td style="padding: 0px;margin-left: -5px;">
<img id='hinder' src='<%=ctx %>/jsp/frame/img/close.gif' style='margin-left: -3px;padding: 0px;cursor: pointer;' alt='隐藏菜单'>
</td>
</tr>
</table>
</body>
</html>