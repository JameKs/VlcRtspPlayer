<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% String ctx = request.getContextPath();%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
    <link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/extjs/css/ext-all.css" />
	<script type="text/javascript" src="<%=ctx%>/resources/extjs/js/ext-all.js"></script>
	<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
	
<script type="text/javascript">

var tabPanel ;

Ext.require([ '*' ]);

Ext.onReady(function() {

	var ctx = $("#ctx").val();


	// 中部内容面板,默认为欢迎界面
	tabPanel = Ext.create('Ext.tab.Panel', {
		id : 'tabPanel',
		//bodyStyle : 'padding:5px;',
		activeTab : 0,
		layout : 'border', // 设置布局
		renderTo : Ext.getBody(),
		//margins : '5 5 5 0',
		region : 'center'

	});

	//当viewport定义好后才能增加tab
	tabPanel.add({
		id : 'st',
		title : '首页',
		height:$('#mainFrame',window.parent.document).height()-38,
		html : '欢迎进入继续教育系统',
		closable : false

	});
	tabPanel.setActiveTab('st');
	
});



</script>
</head>
<body style="margin: 0px;height:100%">
</body>
</html>