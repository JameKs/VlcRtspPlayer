<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String ctx = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>继续教育</title>

</head>

<frameset rows="60,*,28" frameborder="no" border="0" framespacing="0">

	<frame src="<%=ctx%>/jsp/frame/header.jsp" name="headerFrame" scrolling="no"
		border="0" noresize="noresize" id="headerFrame">
	<frameset cols="220,7,*" frameborder="no" border="0" framespacing="0"
		name="mset" id="mset">

		<frame src="<%=ctx%>/left.do" name="left" scrolling="no"
			noresize="noresize" id="lFrame" frameborder="no" border="0">
		<frame src="<%=ctx%>/jsp/frame/split.jsp" name="split" scrolling="no"
			noresize="noresize" id="sFrame" frameborder="no" border="0">
		<frame src="<%=ctx%>/jsp/frame/main.jsp" name="mainFrame" scrolling="no"
			noresize="noresize" id="mainFrame" frameborder="no" border="0">

	</frameset>
	<frame src="<%=ctx%>/jsp/frame/footer.jsp" name="footer" scrolling="no"
		noresize="noresize" id="footerFrame"></frame>
</frameset>

</html>