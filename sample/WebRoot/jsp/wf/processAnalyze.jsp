<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="org.activiti.engine.history.HistoricProcessInstance" %>
<%@ page import="org.activiti.engine.history.HistoricTaskInstance" %>
<%
String ctx = request.getContextPath();

//构建图形
List<HistoricTaskInstance> list  =  (List)request.getAttribute("dataList");
StringBuffer graph = new StringBuffer();
StringBuffer categories = new StringBuffer();
StringBuffer categorie = new StringBuffer();
StringBuffer set = new StringBuffer();
StringBuffer dataset = new StringBuffer();
graph.append("<graph caption='任务名称' subcaption='(from 2012/4/8 to 2012/10/8)' hovercapbg='FFECAA' hovercapborder='F47E00' formatNumberScale='0' decimalPrecision='0' showvalues='0' numdivlines='3' numVdivlines='0' yaxisminvalue='1000' yaxismaxvalue='1800'  rotateNames='1'>");
categories.append("<categories>");
categorie.append("<category name='2012/4/8' />");
categorie.append("<category name='2012/5/8' />");
categorie.append("<category name='2012/6/8' />");
categorie.append("<category name='2012/7/8' />");
categorie.append("<category name='2012/8/8' />");
categorie.append("<category name='2012/9/8' />");
categorie.append("<category name='2012/10/8' />");
categories.append(categorie);
categories.append("</categories>");
dataset.append("<dataset seriesName='Offline Marketing' color='1D8BD1' anchorBorderColor='1D8BD1' anchorBgColor='1D8BD1'>");
set.append("<set value='1327' />");
set.append("<set value='1826' />");
set.append("<set value='1699' />");
set.append("<set value='1511' />");
set.append("<set value='1904' />");
set.append("<set value='1957' />");
set.append("<set value='1296' />");
dataset.append(set);
dataset.append("</dataset>");
graph.append(categories).append(dataset);
dataset = new StringBuffer();
set = new StringBuffer();
dataset.append("<dataset seriesName='Search' color='F1683C' anchorBorderColor='F1683C' anchorBgColor='F1683C'>");
set.append("<set value='2042' />");
set.append("<set value='3210' />");
set.append("<set value='2994' />");
set.append("<set value='3115' />");
set.append("<set value='2844' />");
set.append("<set value='3576' />");
set.append("<set value='1862' />");
dataset.append(set);
dataset.append("</dataset>");
graph.append(dataset);
dataset = new StringBuffer();
set = new StringBuffer();
dataset.append("<dataset seriesName='Paid Search' color='2AD62A' anchorBorderColor='2AD62A' anchorBgColor='2AD62A'>");
set.append("<set value='850' />");
set.append("<set value='1010' />");
set.append("<set value='1116' />");
set.append("<set value='1234' />");
set.append("<set value='1210' />");
set.append("<set value='1054' />");
set.append("<set value='802' />");
dataset.append(set);
dataset.append("</dataset>");
graph.append(dataset);
dataset = new StringBuffer();
set = new StringBuffer();
dataset.append("<dataset seriesName='From Mail' color='2AD62A' anchorBorderColor='2AD62A' anchorBgColor='2AD62A'>");
set.append("<set value='541' />");
set.append("<set value='781' />");
set.append("<set value='920' />");
set.append("<set value='754' />");
set.append("<set value='840' />");
set.append("<set value='893' />");
set.append("<set value='451' />");
dataset.append(set);
dataset.append("</dataset>");
graph.append(dataset);
graph.append("</graph>");
%>
<script type="text/javascript" src="<%=ctx %>/resource/library/fusioncharts/FusionCharts.js"></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/My97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" href="<%=ctx %>/resource/library/fusioncharts/Style.css" type="text/css" />
<script type="text/javascript">
 	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
	};
 	
 	function reset(){
 		var form = document.forms[0];
 		var input = form.getElementsByTagName("input");
 		for(var i=0;i<input.length;i++){
 			input[i].value="";
 		}
 		var select = form.getElementsByTagName("select");
 		for(var i=0;i<select.length;i++){
 			select[i].value="";
 		}
 		document.forms[0].submit();
 	}

 	function onQueryx(){
 		alert("OK");
 	}
</script>
<style type="text/css">
.style_left{
	float: left;
	width: 18%;
	height: 100%;
}

.style_right{
	float: left;
	width: 80%;
	height: 100%;
	margin-left: 10px;
}
</style>
<div class="wrapper">
<div class="style_left">
	<f:panel header="模板树" height="400px;" width="100%;">
		<f:tree setting="setting" initValue="treeData" id="treeDemo" />
	</f:panel>
</div>
<div class="style_right">
<form action="" id="firstForm">
	<f:queryPanel onQuery="onQueryx" onReset="reset">
		<tr>
			<td class="name">开始时间：</td>
			<td class="text"><input type="text" id="startTime"/></td>
			<td class="name">结束时间：</td>
			<td class="text"><input type="text" id="endTime"/></td>
			<td class="name">查询精度：</td>
			<td class="text">
				<select>
			        <option value="">有</option>
			        <option value="">无</option>
		        </select>
			</td>
		</tr>
	</f:queryPanel>
</form>
<div class="char_table">
	<div id="chartdiv" align="center"></div>
</div>
</div>
</div>
<script type="text/javascript">
	var chart = new FusionCharts("<%=ctx%>/resource/library/fusioncharts/FCF_MSLine.swf", "ChartId", "600", "350");
	chart.setDataXML("<%=graph.toString()%>");		   
	chart.render("chartdiv");
	
	tsmp.utils.flashInstallMsg("chartdiv");//添加浏览器验证。 add by zengzw 2012-12-11
</script>