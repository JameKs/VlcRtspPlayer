<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%
String ctx = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<style type="text/css">
.wrapper{
	width: 100%;
	height: 600px;
	margin-top: 10px;
}

.search_body{
	width: 100%;
	height: 15%;
	border: 1px solid #D3E5F6;
}

.list_body{
	width: 100%;
	height: 80%;
	margin-top: 10px;
}

input{
	width: 150px;
}

.select{
	width: 150px;
}
</style>
<title>业务申请</title>
</head>
<body>
<div class="wrapper">
	<div class="search_body">
	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="top-table-systems-01-infolist-01">
    	<tr>
			<td class="top-left-systems-01-infolist-01"></td>
			<td class="top-center-systems-01-infolist-01">业务查询</td>
			<td class="top-right-systems-01-infolist-01"></td>
		</tr>
    </table>
    <form action="">
    <div class="center-center-systems-01-infolist-01">
        <div style="float: left;">
            业务名称：
            <input type="text" id=""/>
        </div>
        <div style="float: left; margin-left: 20px;">
        	最近修改人:
            <input type="text" id=""/>
        </div>
        <div style="float: left; margin-left: 20px;">
        	业务类型：
            <select id="" class="select">
            	<option value="">1</option>
                <option value="">2</option>
                <option value="">3</option>
            </select>
        </div>
        <div class="main-table-btn" style="float: left; margin-left: 20px;">
            <a href="javascript:void(0)"><img alt="查询" src="<%=ctx %>/pages/fbrp/images/crud/search.gif"/>查询</a>
        </div>
         <div class="main-table-btn" style="float: left; margin-left: 10px;">
            <a href="javascript:void(0)"><img alt="重置" src="<%=ctx %>/pages/fbrp/images/crud/reset.gif"/>重置</a>
        </div>
    </div>
    </form>
    </div>
    
    <div class="list_body">
    	<div class="list_title">
        	<table width="100%" cellpadding="0" cellspacing="0" border="0" class="top-table-systems-01-infolist-01">
                <tr>
                    <td class="top-left-systems-01-infolist-01"></td>
                    <td class="top-center-systems-01-infolist-01">业务申请</td>
                    <td class="top-right-systems-01-infolist-01"></td>
                </tr>
            </table>
        </div>
        <div class="center-center-systems-01-infolist-01">
            <table width="100%" cellpadding="0" cellspacing="0" border="0" style=" border-collapse:collapse;" class="center-table-systems-01-infolist-01" >
                <tr>
                    <td class="main-table-title013">业务编号</td>
                    <td class="main-table-title013">业务类型</td>
                    <td class="main-table-title013">业务名称</td>
                    <td class="main-table-title013">版本</td>
                    <td class="main-table-title013">最近修改人</td>
                    <td class="main-table-title013">最近修改时间</td>
                    <td class="main-table-title013">操作</td>
                </tr>
                <tr height="33" valign="middle">
                    <td>001</td>
                    <td>有型</td>
                    <td>测试</td>
                    <td>1.0.0</td>
                    <td>测试者</td>
                    <td>2012-08-14 15:44:36</td>
                    <td>
                        <a href="javascript:void(0);"><img alt="业务申请" src="<%=ctx %>/pages/fbrp/images/crud/submit.gif"/>业务申请</a>
                    </td>
                </tr>
            </table>
        </div>
        <jsp:include page="../pageBar.jsp"/>
    </div>
</div>
</body>
</html>