<!DOCTYPE head PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.foresee.bi.tsmp.workflow.vo.WfSpyj"%>
<%@ page import="com.foresee.bi.tsmp.workflow.service.impl.ActivitiProcessManageServiceImpl.*" %>
<%
/**
* 流程审批日志
* 作者：zengziwen  zengziwen@foresee.cn
* 时间:  2012-07-31
**/
String ctx = request.getContextPath();
String msg=(String)request.getAttribute("msg");

//构建图形
List<WfSpyj> list  =  (List)request.getAttribute("dataList");
StringBuffer graph = new StringBuffer();
StringBuffer categories = new StringBuffer();
StringBuffer dataset = new StringBuffer();
graph.append("<graph caption='耗时展示图' yaxisname='时间' hovercapbg='FFFFFF' divLineColor='999999' divLineAlpha='20' numdivlines='6' yaxisminvalue='60' yaxismaxvalue='360'   decimalPrecision='0' numberPrefix='' numberSuffix='秒' >");
categories.append("<categories>");
dataset.append("<dataset color='839F2F'>");
if(list!=null){
	for(WfSpyj k : list){ 
		if(k.getApprDate()!=null){
			long l = 0 ;
			categories.append("<category name='").append(k.getNodeName()).append("(").append(k.getApprName()).append(")").append("'/>");
			if(k.getCjsj()!=null) {
				l = ( k.getApprDate().getTime()-k.getRwJbSj().getTime())/1000;
			}
	     	dataset.append("<set value='").append(l).append("' />");
		}
	}
}
dataset.append("</dataset>");
categories.append("</categories>");
graph.append(categories).append(dataset);
graph.append("</graph>");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" />
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
<link rel="stylesheet"  href="<%=ctx %>/pages/tsmp/fusioncharts/FusionChartsStyle.css" type="text/css" />
<script language="JavaScript" src="<%=ctx %>/resource/library/fusioncharts/FusionCharts.js"></script>
<script>
var _CTX = '<%=ctx%>';
	function show(url){
		var w =833,h=465;
		var vLeft = (window.screen.availWidth-w)/2;
		var vTop = (window.screen.availHeight-h)/2;
		vTop = vTop - 50;if(vTop<0) vTop = 0;
		window.showModalDialog(url,null,'dialogWidth:'+w+'px;dialogHeight:'+h+'px;dialogLeft:'+vLeft+';dialogTop:'+vTop+';scroll:auto;status:no;resizable:yes;help:no;');
		return false;
	}
	
	//特殊转义
	function encode(value) {
		if((value != undefined) && (value != null)) {
			var regexp1 = new RegExp("\"", "gm");
			var regexp2 = new RegExp("\'", "gm");
			var regexp3 = new RegExp("<", "gm");
			var regexp4 = new RegExp(">", "gm");
			var regexp5 = new RegExp("&lt;br&gt;", "gm");
			var regexp6 = new RegExp("&lt;br/&gt;", "gm");
			value = value.replace(regexp1, "&quot;");
			value = value.replace(regexp2, "&#39;");
			value = value.replace(regexp3, "&lt;");
			value = value.replace(regexp4, "&gt;");
			value = value.replace(regexp5, "<br/>");
			value = value.replace(regexp6, "<br/>");
    	}
		return value;
	}
	
	var tableSetting = {
		url:'#',
		parameter:{},
		cellRender:{
			/* "rwJbSj":function(value) {
				if(value){
					var date = new Date(value);
					return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
				} else {
					return "";
				}
			},
			"apprDate":function(value) {
				alert(value);
				if(value){
					var date = new Date(value);
					if(date.getFullYear()==1970) return "";
					return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds();
				} else {
					return "";
				}
			}, */
			"apprOpinion":function(value, data) {
				var o = data.apprOpinion ;
				if(o==undefined) {
					return '--';
				}
				//if(o.length>6) o= o.substring(0,6)+"...";
				o = encode(o);
				return "<a href='#'  title='"+o+"' >"+o+"</a>";
				<%-- if(data.endTime == null) {
					return "";
				} else {
					var str = "<a href=\"<%=ctx%>/public-access/workflow/taskManage.do?opinion&procDefId="+data.processDefinitionId+"&procInstId="+data.processInstanceId+"&nodeId="+data.taskDefinitionKey+"\" onclick=\"javascript:return show(this)\">"+value+"</a>";
					return str;
				} --%>
			},
			"timeConsume":function(value, data) {
				if(data.rwJbSj == null||data.cjsj==null) {
					return "";
				} else {
					var d = (data.cjsj -data.rwJbSj )/1000/3600/24;
					return d<1?"< 1": Math.ceil(d);
				}
			},
			"nextNodeName" : function(value,data){
				if(data.nextNodeName==undefined) return '--';
				return value;
			}
		}
	};
</script>

<style type="text/css">
  #cl_bn{
       width: 50px;
       height: 30px;
       cursor: pointer;
  }
  table tbody tr td{
  	text-align: center;
  }
</style>

<title>任务处理</title>
</head>
<body>
<f:table header="审核记录" initValue="pr" id="rsTable" setting="tableSetting"   columnHeader="number"  showPageBar="false"  >
<f:column title="处理步骤" property="nodeName" />
<f:column title="所属部门" property="apprOrgName" align="left"/>
<f:column title="处理人" property="apprName"  width="80px" />
<f:column title="交办时间" property="rwJbSj" width="120px" >
<f:display type="date" value="yyyy-mm-dd HH:MM:ss"/>
</f:column>
<f:column title="完成时间" property="apprDate" width="120px"  >
<f:display type="date" value="yyyy-mm-dd HH:MM:ss"/>
</f:column>
<f:column title="意见" property="apprOpinion" />
<f:column title="耗时(天)" property="timeConsume" align="right"  width="60px"/>
<f:column title="下一环节" property="nextNodeName" align="right"  width="60px"/>
</f:table>
 	<div id="chartdiv" align="center"></div>
      <script type="text/javascript">
		   var chart = new FusionCharts("<%=ctx%>/resource/library/fusioncharts/FCF_MSBar2D.swf", "ChartId", "700", "350");
		   chart.setDataXML("<%=graph.toString()%>");		   
		   chart.render("chartdiv");
		</script>
 <!--    <button id='cl_bn' onclick='window.close();'>关闭</button> -->
 </body>
</html>
<script>
$(function(){
	$('#rsTable .com_tab_top .ui-table-toolbar').css('display','none');
	tsmp.utils.flashInstallMsg("chartdiv");
})
</script>