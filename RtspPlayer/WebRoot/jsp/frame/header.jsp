<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
	String ctx = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
<title></title>
<script type="text/javascript">
/*$("#logout").click(function(){
	var r = confirm(" 你确定要注销吗？");
	if(r == true){	
		//$(this).attr({href:"logout"});
		parent.window.location = "<%=ctx %>/logout.do";
	}
});*/

function loginOut(){
	var r = confirm(" 你确定要注销吗？");
	if(r == true){	
		//document.forms[0].submit();
		//$(this).attr({href:"logout"});
		//parent.window.location = "<%=ctx %>/mainlogout.do";
		//parent.window.location.href= "javascript:jQuery.post('/logout')";
		jQuery.post('<%=ctx %>/logout',{"${_csrf.parameterName}":"${_csrf.token}"});
		parent.window.location = "<%=ctx %>/";
	}
}

</script>
</head>
<body>

<div>
	<!-- <img src="<%=ctx%>/resources/images/head.jpg" alt="继续教育系统"> -->
	<div style="float:right;background-image:url(<%=ctx%>/resources/images/head.jpg)">
	    <span class="Sline">|</span>
	    <span><a href="#"><b class="text">修改密码</b></a></span>
	  	<span class="Sline">|</span>
	    <span><a href="#" id="help"><b class="text">资料下载</b></a></span>
	    <span class="Sline">|</span>
	    <span><a href = "javascript:loginOut();" id="logout" style="cursor: pointer;" ><span class="text">退出</span></a></span>
	</div>
</div>

<form action="<c:url value='/logout'/>" method="POST">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>


</body>
</html>