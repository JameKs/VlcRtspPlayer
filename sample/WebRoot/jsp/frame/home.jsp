﻿<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ page import="com.foresee.bi.tsmp.common.vo.TsmpAdminTask" %>
<%@ page import="java.util.List" %>
<%	String ctx = request.getContextPath();%>
<script type="text/javascript" src="<%=ctx%>/resource/library/fusioncharts/FusionCharts-3.2.js"></script>
<script type="text/javascript">
org_code = null;
$(function(){
	function initH(id,data,mp,fun){
		var arr = [];
		for(var i = 0 ;i < data.length;i++){
			var t = data[i];
			if(mp[t.taskLy]){
				var cls = "";
				if(data[i].sum==0||data[i].sum=="0") {
					cls = "class=\"li_b\" disabled=\"disabled\"";
				}
				if(t.taskLy=="dbrw_nspg" || t.taskLy=="dbrw_djz") //第三方特殊处理
					{
						arr.push("<li "+cls+" ><a href=\"javascript:void(0)\">"+mp[t.taskLy]+"<span> ("+t.sum+")</span></a></li>");
					}
				else
					{
						arr.push("<li "+cls+" onclick=\""+fun+"('"+t.taskLy+"')\" ><a href=\"javascript:void(0)\">"+mp[t.taskLy]+"<span> ("+t.sum+")</span></a></li>");
					}
			}
		}
		$("#"+id).html("");
		$("#"+id).append(arr.join(""));
	}
	var mp = {
			"dbrw_cbcj" : "催报催缴",
			"dbrw_hjgl" : "户籍管理",
			"dbrw_nsfd" : "纳税辅导",
			"dbrw_ybsx" : "一般事项",
			"dbrw_nspg" : "纳税评估",
			"dbrw_syfl" : "税源分类",
			/* "dbrw_xtgl" : "系统管理", */
			"dbrw_nspg" : "纳税评估",
			"dbrw_hxgl" : "后续管理",
			"dbrw_dzbst": "电子办税厅",
			"dbrw_lyxt": "两业系统"
			/* "dbrw_djz" : "大集中" */
	};

	var mp2 = {
			"txrw_hjgl" : "户籍管理",
			"dbrw_cbcj" : "催报催缴",
			"txrw_ybsx" : "一般事项"
	};
	$.get("<%=ctx%>/home.do?readingTask=x&_r="+new Date().getTime(),function(data){
		initH("h_dbq",data["dbq"],mp,"rwView");
		initH("h_txq",data["txq"],mp2,"txView");
		$("#h_dbq>li,#h_txq>li").click(function(){
			$("#h_dbq>li,#h_txq>li").removeClass("current");
			$(this).addClass("current");
		});
	});

});

var dbSetting = {
		cellRender:{
			"taskName":function(value,row){
				if(value!=null){
					if(value.length>1){
						var d = value.substring(value.length-1,value.length);
						if(row.taskSou=="dbrw_nsfd"	){ 
							 value="纳税辅导任务";
						}
						if(row.taskSou=="dbrw_hjgl"){
							value="税务登记催办任务";
						}
						if(value.indexOf("下户调查") >= 0){
							value="下户调查任务";
						}
						if(d!=')'){
							value = value+"("+row.sum+")";
						}
					}
				}
				return "<a href=\"javascript:void(0)\" onclick=\"openItemDb(this)\">"+value+"</a><span style=\"display:none;\">"+JSON.stringify(row)+"</span>";
			}
		},
		toolbar:[
		         {text:"更多...",
		          onClick:function(tId,sels){
		        	 fbrp.utils.openItem("more1","任务管理","首页> 任务管理","/home.do?dqb");
		         }}
		         ]
};
var txSetting = {
		cellRender:{
			"taskName":function(value,row){
				return "<a href=\"javascript:void(0)\" onclick=\"openItemTx(this)\">"+value+"</a><span style=\"display:none;\">"+JSON.stringify(row)+"</span>";
			}
		},
		toolbar:[
		         {text:"更多...",
		          onClick:function(tId,sels){
		        	  fbrp.utils.openItem("more2","提醒管理","首页> 提醒管理","/home.do?txq");
		         }}
		         ]
};
function rwView(taskLy){
	$("#rwTable").show();
	$("#txTable").hide();
	var url = "<%=ctx%>/home.do?showRwmx";
	var p = {taskLy:taskLy};
	rwTable.loadPagedResult(url,p);
}
function txView(taskLy){
	$("#txTable").show();
	$("#rwTable").hide();
	var url = "<%=ctx%>/home.do?showTxmx";
	var p = {taskLy:taskLy};
	txTable.loadPagedResult(url,p);
}

function openItemDb(elex){
	var row = $.parseJSON($(elex).next().html());
	var url = row.taskUrl;
	var name = row.taskName;
	var taskYsz = row.taskYsz;
	var flag = false;//临时判断变量
	if(name.indexOf("[") != -1) {
		name = name.substring(0,name.indexOf("["));
	} else if(name.indexOf("(") != -1) {
		name = name.substring(0,name.indexOf("("));
	}
	if(row.taskSou == "dbrw_gzlc") {
		url = '/home.do?dqb';
		var type = row.taskLy.indexOf('_',6);
		if(type==-1){
			type = row.taskLy;
		}else{
			type=row.taskLy.substring(0,type);
		}
		url+="&taskLy="+type+"&taskYsz="+taskYsz;
	}else if(row.taskSou == "dbrw_nsfd"){
		url ="/tsm/nsfd/fdrwgl.do";
		name  = "纳税辅导任务";
	}else if(row.taskSou == "dbrw_hjgl"){
		url = "/tsm/hjgl/swdjcb/rwcx.do";
		name = "税务登记催办任务";
	}
	if(url.indexOf("home.do?dqb") >= 0){
		name = "任务管理";
	}
	if(taskYsz.indexOf('dbrw_lyxt')==-1) {
		$.ajax({
			url: "<%=ctx%>/public-access/security/authorization/staffGrant.do?checkStaffGrant",
			type: "POST",
			data: {url:url},
			async: false,
			success: function(data){
				if(data && data.success){
					flag = data.success ? true:false;
				}
			}
		});
	} else {
		flag = true;
	}
	if(flag){
		fbrp.utils.openItem(row.id,name,name,url);
	} else {
		fbrp.utils.showInfo("您没有\""+name+"\"权限，请您联系管理员授权，谢谢合作！", "error", null, 5000);
	}
}

function openItemTx(elex){
	var row = $.parseJSON($(elex).next().html());
	var url = row.taskUrl;
	var name = row.taskName;
	var flag = false;//临时判断变量
	if(name.indexOf("(") != -1) {
		name = name.substring(0,name.indexOf("("));
	}
	$.ajax({
		url: "<%=ctx%>/public-access/security/authorization/staffGrant.do?checkStaffGrant",
		type: "POST",
		data: {url:url},
		async: false,
		success: function(data){
			if(data && data.success){
				flag = data.success ? true:false;
			}
		}
	});
	if(flag){
		$.post("<%=ctx%>/home.do?getTxZxfkById",{id:row.id},function(data){
			fbrp.utils.openItem(row.id,name,name, url);
		});
	} else {
		fbrp.utils.showInfo("您没有\""+name+"\"权限，请您联系管理员授权，谢谢合作！", "error", null, 5000);
	}
}
var mHistory = [];
$(function() {
	var mv = ${mv};
    viewMap(mv);
    $("#mapBack").click(function(){
  	  if(mHistory.length>1){
  		mHistory.pop();
  		var h = mHistory[mHistory.length-1];
  		goMap('',h.code,true,true,h.cnName);
  	  }else{
  		goMap('','${loginOrgId}',true);
  	  }
    });

	
	if(!tsmp.utils.hasFlash())
	{
		tsmp.utils.flashInstallMsg("chartdiv1hs");
		tsmp.utils.flashInstallMsg("chartdiv2");
		return;
	}
	
    var chart = null;
    chart = new FusionCharts("<%=ctx%>/resource/library/fusioncharts/Pie3D.swf", "ChartId1", "100%", "200", "0", "0");
	chart.setDataXML(mv.charts.hs);	
	if(mv.charts.hs!=null){
		chart.render("chartdiv1hs");
	}else{
		$("#chartdiv1hs").html('无数据');
	}
	
	var sqlSyfl = '';
	var sqlYfss = '';
	if(mv.charts.sql!=null){
		sqlSyfl = mv.charts.sql;
		sqlSyfl = sqlSyfl ? sqlSyfl : "";
	}
	$("#hs").click(function(){
		viewSQL(sqlSyfl);
	});
	if(mv.charts.yfssSQL!=null){
		sqlYfss = mv.charts.yfssSQL;
		sqlYfss = sqlYfss ? sqlYfss : "";
	}
	$("#ss").click(function(){
		viewSQL(sqlYfss);
	});
	
	chart = new FusionCharts("<%=ctx%>/resource/library/fusioncharts/MSLine.swf", "ChartId2", "440", "220", "0", "0");
	chart.setDataXML(mv.charts.c2);	   
	if(mv.charts.hs!=null){
		chart.render("chartdiv2");
	}else{
		$("#chartdiv2").html('无数据');
	}
	
	$("#rwTable").css("margin-bottom","10px");
	$("#txTable").css("margin-bottom","10px");
}); 

function viewMap(mv,isBack,canGoDown,cnName){
	$("#mapHeader").text("税源分布情况统计"+(cnName? "("+cnName+")":""));
	if(canGoDown===false){
		var h = mHistory[mHistory.length-1];
		if(h&&!h.leaf){
			mHistory.push({code:mv.code,cnName:cnName,leaf:true});
		}
		return;
	}
	if(!(mv&&mv.image)||(mv&&mv.image=='nopic/wutupian.gif')){
		$('#mapDiv').html("<img src=\""+_CTX+"/resource/map/image/nopic/wutupian.gif\" border=\"0\" usemap=\"#gdMapArea\" />");
		if(mv&&mv.code){
			mHistory.push({code:mv.code,cnName:cnName}); 
		}
		return;
	}
	if(mv.areaContent=='null'||mv.areaContent==null)
	{
		mv.areaContent="";
	}
	if($("#mapDiv>img")[0]){
		if(isBack){
			$("#mapDiv>img").fadeOut(100,function(){
				$('#mapDiv').html("<img src=\"<%=ctx%>/resource/map/image/"+mv.image+"\" border=\"0\" usemap=\"#gdMapArea\" style=\"width:0px;height:0px;\" /><map name=\"gdMapArea\" id=\"gdMapArea\">"+mv.areaContent+"</map>");
				$("#mapDiv>img").animate({width:"510px",height:"400px"},500);
			});
		}else{
			$("#mapDiv>img").animate({width:"0px",height:"0px"},500,function(){
				$('#mapDiv').html("<img src=\"<%=ctx%>/resource/map/image/"+mv.image+"\" border=\"0\" usemap=\"#gdMapArea\" style=\"display:none;\" /><map name=\"gdMapArea\" id=\"gdMapArea\">"+mv.areaContent+"</map>");
				$("#mapDiv>img").fadeIn(800);
				mHistory.push({code:mv.code,cnName:cnName});
			});
		}
	}else{
		$('#mapDiv').html("<img src=\"<%=ctx%>/resource/map/image/"+mv.image+"\" border=\"0\" usemap=\"#gdMapArea\" /><map name=\"gdMapArea\" id=\"gdMapArea\">"+mv.areaContent+"</map>");
		mHistory.push({code:mv.code,cnName:cnName});
	}
}
var hOrgCode = '${loginOrgId}';
var loginDeptId = '${loginDeptId}';
function goMap(name,code,isBack,canGoDown,cnName){
  if(loginDeptId.substr(0,7)=="2440090" || loginDeptId=="24400000055" || loginDeptId=="24400000054") 
  {
	  canGoDown=false;
  }
  if(canGoDown == true){
	if(isBack==true&&code=='244000000'){
		code='2440000';
	};	
	$.get("<%=ctx%>/home.do?map",{code:code},function(data){
		
			viewMap(data,isBack,canGoDown,cnName);
		 if(code=='${loginOrgId}'||code=='2440000'){
			$("#mapBack").hide();			
		 }else{
			$("#mapBack").show();			
		 }
		 var codeSubstr = code.substr(5,4);
		 var $stateBut = $("#stateBut");
		 if(codeSubstr!="0000" && code!='2440000'){
			 $stateBut.append("展开税务分局").show();	
			 org_code = code;
		 }else{
			 $stateBut.empty().hide();	
			 $("#class1content").hide();		 
		 }
		 hOrgCode = code;
		if(tsmp.utils.hasFlash()){
		 var type = $("#chartdiv1hs").is(":visible") ? "hs" : "ss";
		 openChartTh1(type);
		 openChartTh2();
		}
		 //doDpChartSeriesClick(data.code);
	});
  }	
}
function openChartTh1(type){
	$.get("<%=ctx%>/home.do?chart1",{code:hOrgCode,type:type},function(data){
		if(type.indexOf("_z") != -1){
			type = type.substring(0, type.indexOf("_"));
		}
		if(type==='ss'){
			$("#chartdiv1hs").hide();
			$("#syfltj>li:first").removeClass("current");
			$("#syfltj>li:last").addClass("current");
		}else if(type==='hs'){
			$("#chartdiv1ss").hide();
			$("#syfltj>li:last").removeClass("current");
			$("#syfltj>li:first").addClass("current");
		}
		$("#chartdiv1"+type).show();
		if(data.xml!=null){
			var chart = null;
		    chart = new FusionCharts("<%=ctx%>/resource/library/fusioncharts/Pie3D.swf", "ChartId"+type, "100%", "200", "0", "0");
			chart.setDataXML(data.xml);	
			chart.render("chartdiv1"+type);
			$("#chartdiv1Title").html("<a id='hs' href='javascript:void(0);'><img alt='查看SQL' src='<%=ctx%>/resource/icons/toolbar/sql.gif'></a>税源分类统计"+(type=='ss'?'(税收，单位:亿)':'(户数)'));
		}else{
			$("#chartdiv1"+type).html('无数据');
		}
	    
		var sqlSyfl = '';
		if(data.sql!=null){
			sqlSyfl = data.sql;
			sqlSyfl = sqlSyfl ? sqlSyfl : "";
		}
		$("#hs").click(function(){
			viewSQL(sqlSyfl);
		});
		
	});
}
function openChartTh2(){
	$.get("<%=ctx%>/home.do?chart2",{code:hOrgCode},function(data){
		if(data.xml!=null){
			var chart = null;
			chart = new FusionCharts("<%=ctx%>/resource/library/fusioncharts/MSLine.swf", "ChartId2", "440", "220", "0", "0");
			chart.setDataXML(data.xml);	   
			chart.render("chartdiv2");
		}else{
			$("#chartdiv2").html('无数据');
		}
		
		var sqlYfss = '';
		if(data.sql!=null){
			sqlYfss = data.sql;
			sqlYfss = sqlYfss ? sqlYfss : "";
		}
		$("#ss").click(function(){
			viewSQL(sqlYfss);
		});
	    
	});
}
function showLi(that){
	var regexp = new RegExp("display: none", "gm");
	var regexph = new RegExp("hover", "gm");
	var strImgD = "<img src=\"<%=ctx %>/resource/fbrp/ui/images-systems-01/title_mune_hd/sort_-1.gif\" />";
	var strImgU = "<img src=\"<%=ctx %>/resource/fbrp/ui/images-systems-01/title_mune_hd/sort_1.gif\" />";
	if($(that).attr("class") && !($(that).attr("class").match(regexph))){
		$(".title_mune .hover").children("b").children("img").remove();
		$(".title_mune .hover").children("b").append(strImgU);
		$(".title_mune .hover").removeClass("hover");
		$(that).addClass("hover");
		$(that).children("b").children("img").remove();
		$(that).children("b").append(strImgD);
	}
	if($(that).next("ul").attr("style") && $(that).next("ul").attr("style").match(regexp)){
		$("#syfltj>li").removeClass("current");
		$(that).parent("div").parent("li").addClass("current");
		$(".title_mune .title_mune_hd").next("ul").not(that).hide();
		$(that).next("ul").show();
	} else {
		$(that).children("b").children("img").remove();
		$(that).children("b").append(strImgU);
		$(that).removeClass("hover");
		$(that).next("ul").hide();
	}
}

function $use(targetid,objN){
	 var d=$('#'+targetid)[0];
	 var sb=$('#'+objN)[0];
	 if (d.style.display=="block"){  
	       d.style.display="none";
	       sb.innerHTML="展开税务分局";
	  } else {
	       d.style.display="block";
	       sb.innerHTML='收缩税务分局';
	       show_org_button();
	   }
	}
	
//展开税务分局
function show_org_button(){
	$("#class1content").empty();
	$.ajax({
		  type: "POST",
		  url: "home.do?openOrgFromBut",
		  data: "code=" + org_code,
		  dataType: "json",
		  success:function(data){
			  var str1 = "<li class='current'><h3><a class='mapM_title'";	
			  var str4= "<img src='resource/fbrp/ui/images-systems-01/map_mune/down.gif'/></a></h3>";
			  $.each(data,function(i,item){
				  var str3= "<a class='mapM_down' href='javascript:show_org_button2("+ item.orgcode +");'>";
				  var str2= "href='" + "javascript:show_org_button1("+ item.orgcode +");" + "'>";
				  var str5= "<ul id='id_" + item.orgcode + "' class='childrenOrg'></ul></li>";
				  $("#class1content").append(str1 + str2 + item.orgname + str3 + str4 + str5);			  
			  });
		    }		  
	  }); 	
	$("#class1content").show();
}	

function show_org_button1(orgcode){
	hOrgCode = orgcode;
	var type = $("#chartdiv1hs").is(":visible") ? "hs" : "ss";
	openChartTh1(type);
	openChartTh2();
}

function show_org_button2(orgcode){
	$("ul.childrenOrg").children("li").remove();
	$.ajax({
		  type: "POST",
		  url: "home.do?openOrgFromBut",
		  data: "code=" + orgcode,
		  dataType: "json",
		  success:function(data){
				  if(data == ''){  
					     $("#id_" + orgcode).append("<li><span>无数据</span></li>");
				  }else{
					  $.each(data,function(i,item){
						 var str = "<li id='id_org_sub_"+item.orgcode+"'><a href='javascript:show_org_button3("+item.orgcode+")'>"
						           +item.orgname+"</a></li>";					 
						 $("#id_" + orgcode).append(str);
					  });
				  }		 
			  }
		  });
}

function show_org_button3(orgcode){
	hOrgCode = orgcode;
	$("ul.childrenOrg").children("li").removeClass();
	var $id = $("#id_org_sub_" + orgcode);
	$id.addClass("current");
	var type = $("#chartdiv1hs").is(":visible") ? "hs" : "ss";
	openChartTh1(type);
	openChartTh2();
}

function viewSQL(temp){
	var arr = [];
	arr.push("<textarea rows=\"12\" cols=\"95\" style=\"margin-top:4px;\">");
	if(temp.indexOf("@")>=0){
		var str = temp.split("@");
		str[1] = str[1] == "null" ? "" : str[1];
		arr.push(str[0]);
		arr.push("</textarea>");
		//arr.push("<div style=\"text-align: left;\">"+str[1]+"</div>");
	}else{
		arr.push(temp);
		arr.push("</textarea>");
	}
	var isIE6 = $.browser.msie&&$.browser.version=='6.0';
	var isIE8 = $.browser.msie&&$.browser.version=='8.0';
	var config = isIE6 ? {width:715,height:200} : {width:620,height:275};
	config = isIE8 ? {width:620,height:172} : config;
	fbrp.utils.openCommonDialog("查看SQL", arr.join(""), config);
}

</script>
<input type="hidden" value="1" id="qhsy" />
	<div class="framemain">
	<table width="100%" border="0">
		<tr>
		<td width="50%" valign="top">
		<div class="info_L" style="margin-bottom: 8px;">
			<div class="info_title">
				<h2>待办区</h2>
			</div>
			<div class="info_main info_main02" style="height: 96px; padding-right: 0px;">
				<ul id="h_dbq" class="info_ul">
					<img alt="" src="<%=ctx%>/pages/fbrp/images/loading/blue-loading.gif">
				</ul>
			</div>
		</div>
		
		</td>
		<td  width="50%" valign="top">
		<div class="info_R" style="margin-left: 8px; margin-bottom: 8px;">
			<div class="info_title">
				<h2>提醒区</h2>
			</div>
			<div class="info_main info_main02" style="height: 96px; padding-right: 0px;">
				<ul id="h_txq" class="info_ul">
				<img alt="" src="<%=ctx%>/pages/fbrp/images/loading/blue-loading.gif">
				</ul>
			</div>
		</div>
		</td>
		</tr>
	</table>
	<f:table id="rwTable" initValue="rwmxPr" showPageBar="false" setting="dbSetting" header="待办列表" showToolbarPageNav="false">
		<f:column title="任务名称" property="taskName" align="left"></f:column>
		<f:column title="交办时间" property="createDate" width="25%" >
			<f:display type="date" value="yyyy-mm-dd HH:MM"></f:display>
		</f:column>
	</f:table>
	<f:table id="txTable" initValue="txmxPr" showPageBar="false" setting="txSetting" header="提醒列表" showToolbarPageNav="false">
		<f:column title="任务名称" property="taskName" align="left"></f:column>
		<f:column title="时间" property="createDate" width="12%" >
			<f:display type="date" value="yyyy-mm-dd  HH:MM"></f:display>
		</f:column>
		
		<f:column title="状态" property="zxfk" width="10%"></f:column>
	</f:table>
		<script>
		$("#rwTable,#txTable").hide();
		</script>
	</div>


<div class="info_two" style="margin-bottom: 0px;padding-bottom: 0px;">
<div class="info_title">
    <h2 id="mapHeader" style="width: 400px;">税源分布情况统计</h2>
   <%--  <ul class="tab">
        <li class="current"><a href="#"><span>总体税收</span></a></li>
        <li><a href="#"><span>营业税</span></a></li>
        <li><a href="#"><span>企业所得税</span></a></li>
        <li><a href="#"><span>个人所得税</span></a></li>
    </ul> --%>
</div>

<div class="info_main">
<input id="hOrgCode" type="hidden" value="${loginOrgId}">
<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
        <tbody>
            <tr>
                <td width="50%" valign="top">
                    <div  class="gdmap">
	                <div class="return_map">
	                	<!-- <a href="javascript:void(0)">查看详细</a> -->
	                	<a id="mapBack" href="javascript:void(0)" style="display: none;">返回上级</a>
	                </div>
	                <div class="open_org">
	                	<a id="stateBut" href="javascript:void(0)" style="display: none;" onClick="$use('class1content','stateBut')"></a>
	                 </div>
	                	<ul class="map_mume" id="class1content" style="display: none;">	
						</ul>              
                    <div id="mapDiv" style="padding:20px 0px;width:510px;height:400px;"></div>
           
                  </div>
                </td>
                <td width="50%" valign="top" style=" overflow: hidden; _zoom: 1;padding-left: 10px;">
                <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="map_table">
                    <tbody>
                    	<tr><td height="10"></td></tr>
                    </tbody>
                </table>
                
               	<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="map_table">
                    <tbody>
                        <tr class="map_th">
                            <th class="titleM_L"></th>
                            <th class="titleM_C">
                            <span class="titleM_C_h" id="chartdiv1Title"><a id="hs" href="javascript:void(0);" ><img alt="查看SQL" src="<%=ctx%>/resource/icons/toolbar/sql.gif"></a>税源分类统计(户数)</span>
                            <ul id="syfltj" class="titleM_C_ul">
                                <li class="current">
                                <div class="title_mune">
	                                <a class="title_mune_hd" href="javascript:void(0);" onclick="showLi(this)">按户数
	                                <b><img src="<%=ctx %>/resource/fbrp/ui/images-systems-01/title_mune_hd/sort_1.gif" /></b></a>
	                                <ul class="title_mune_bd" style=" display: none;">
	                                	<li><a href="javascript:openChartTh1('hs');">总户数</a></li>
	                                    <li><a href="javascript:openChartTh1('hs_z');">重点税源户数</a></li>
	                            	</ul>
                                </div>
                                </li>
                                <li style="border: none;">
                                <div class="title_mune">
	                                <a class="title_mune_hd" href="javascript:void(0);" onclick="showLi(this)">按税收
	                                <b><img src="<%=ctx %>/resource/fbrp/ui/images-systems-01/title_mune_hd/sort_1.gif" /></b></a>
	                                <ul class="title_mune_bd" style=" display: none;">
	                                	<li><a href="javascript:openChartTh1('ss');">总税收</a></li>
	                                    <li><a href="javascript:openChartTh1('ss_z');">重点税源税收</a></li>
	                            	</ul>
                                </div>
                                </li>
                            </ul>
                            </th>
                          <th class="titleM_R"></th>
                        </tr>
                        
                        <tr>
                            <td class="infoM_L"></td>
                            <td>
                            	<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="map_table">
                                    <tbody>
                                    	<tr>
                                        <td align="center">
                                        <div id="chartdiv1hs" style="height:200px;width: 100%"></div>
                                        <div id="chartdiv1ss" style="display: none;height:200px;width: 100%; "></div>
                                        </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td class="infoM_R"></td>
                        </tr>
                        
                	</tbody>
                </table>
                
                <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="map_table">
                    <tbody>
                        <tr class="map_th">
                            <th class="titleM_L"></th>
                            <th class="titleM_C"><a id="ss" href="javascript:void(0);"><img alt="查看SQL" src="<%=ctx%>/resource/icons/toolbar/sql.gif"></a>税收同比走势(单位：亿)</th>
                            <th class="titleM_R"></th>
                        </tr>
                        <tr>
                            <td class="infoM_L"></td>
                            <td>
                            	<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="map_table">
                                    <tbody>
                                    	<tr>
                                        <td align="center">
                                        <%-- <img src="<%=ctx %>/resource/fbrp/ui/images-systems-01/gdmap/pic.jpg" /> --%>
                                        <div id="chartdiv2"></div>
                                        </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </td>
                            <td class="infoM_R"></td>
                        </tr>
                	</tbody>
                </table>
                </td>
            </tr>
        </tbody>
    </table>
</div>
</div>
