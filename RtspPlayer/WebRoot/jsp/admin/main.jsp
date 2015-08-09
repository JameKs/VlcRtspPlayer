<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%> 
<%
	String ctx = request.getContextPath();
	String requestTime = request.getParameter("requestTime");

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>main</title>
    <link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/extjs/css/ext-all.css" />
	<script type="text/javascript" src="<%=ctx%>/resources/extjs/js/ext-all.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
	<script type="text/javascript" src="<%=ctx%>/jsp/admin/main.js"></script>
<script type="text/javascript">
	var ctx = '${ctx}';
</script>
</head>
<body>
<input type = "hidden" id = "ctx" value = "<%=ctx%>">
<input type = "hidden" id = "requestTime" value = "<%=requestTime%>">
</body>
</html>