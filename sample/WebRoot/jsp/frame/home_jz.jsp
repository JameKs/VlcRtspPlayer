<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
 <%String ctx = request.getContextPath(); %>  
 <script type="text/javascript" src="<%=ctx%>/resource/library/fusioncharts/FusionCharts-3.2.js"></script>
 

<script type="text/javascript">

jQuery(function() {
var initPageSize=6;
	var zhsPageSize=6;
	initByxk(initPageSize);
	initZhsAZT(zhsPageSize);
	initZhsASY(zhsPageSize);
	initSssr();
	initZhsFlash();
});

/**
 * 初始化本月新开
 */
function initByxk(initPageSize){
	$.ajax({
		type : 'post',
		url : "<%=ctx%>/admin/ldpage.do?pageSize="+ initPageSize,
	   async:false,//同步ajax
	  success: function (data){ 
		  var dataObjArr=jQuery.parseJSON(data);
		  renderToXkTable(dataObjArr);
		  },
	  error:function(){
		  alert("获取本月新开失败！");
	  },
	  datatype:'text'
	});
}


/**
 * 初始化总户数按状态
 */
function initZhsAZT(initPageSize){
	$.ajax({
		type : 'post',
		url : "<%=ctx%>/admin/ldpage.do?queryZhszt&pageSize="+ initPageSize,
	   async:false,//同步ajax
	  success: function (data){ 
		  var dataObjArr=jQuery.parseJSON(data);
		  renderToZhsAztTable(dataObjArr);
		  },
	  error:function(){
		  alert("获取总户数按状态失败！");
	  },
	  datatype:'text'
	});
}


/**
 * 初始化总户数按税源
 */
function initZhsASY(initPageSize){
	$.ajax({
		type : 'post',
		url : "<%=ctx%>/admin/ldpage.do?queryZhssy&pageSize="+ initPageSize,
	   async:false,//同步ajax
	  success: function (data){ 
		  var dataObjArr=jQuery.parseJSON(data);
		  renderToZhsAsyTable(dataObjArr);
		  },
	  error:function(){
		  alert("获取总户数按税源失败！");
	  },
	  datatype:'text'
	});
}

/**
 * 初始化应征入库数据
 */
function initSssr(flag){
	var url="<%=ctx%>/admin/ldpage.do?querySssr";
	if(flag!=undefined && flag!=null & flag!=''){
		url+="&flag="+flag;
	}
 	$.ajax({
		type : 'post',
		url :url,
	   async:false,//同步ajax
	  success: function (data){ 
	  var dataObjArr=jQuery.parseJSON(data);
		  renderToSsTable(dataObjArr); 
		  },
	  error:function(){
		  alert("获取应征入库数据失败！");
	  },
	  datatype:'text'
	});
}


/**
 * 初始化总户数flash图形
 */
function initZhsFlash(){
	if(!tsmp.utils.hasFlash()){
		tsmp.utils.flashInstallMsg("zhsSyChart");
		tsmp.utils.flashInstallMsg("zhsztChart");
		return;
	}
	//获取图形XML数据
	 	$.ajax({
		type : 'post',
		url : "<%=ctx%>/admin/ldpage.do?querychart",
	   async:false,//同步ajax
	  success: function (data){ 
		  var dataObjArr=jQuery.parseJSON(data);
		  ;
		  chartDIV(dataObjArr.ztxml,"zhsztChart");
		    chartDIV(dataObjArr.syxml,"zhsSyChart");
		  },
	  error:function(){
		  alert("获取图形数据失败！");
	  },
	  datatype:'text'
	});
	
}
/**
 * 用fusionchart画图
 */
function chartDIV(dataXml,divName){
	//画总户数按状态图
	 var chart = null;
	    chart = new FusionCharts("<%=ctx%>/resource/library/fusioncharts/Pie3D.swf", divName+"11", "100%", "200", "0", "0");
		chart.setDataXML(dataXml);	
		if(dataXml!=null){
			chart.render(divName);
		}else{
			$("#" + divName).html('无数据');
		}
		
		
	
}

function test(swjgdm,swjgmc){
		var url = '/admin/ldpage.do?xkhz&swjgdm='+ swjgdm + '&swjgmc=' + swjgmc;
 		fbrp.utils.openItem('byxk' +swjgdm, "新开户数汇总("+swjgmc+")", "新开户数汇总", url);
}

function testmore(swjgdm,swjgmc){
	var url = '/admin/ldpage.do?xkhz&more=true';
		fbrp.utils.openItem('byxkhz' + swjgdm, "新开户数汇总", "新开户数汇总", url);
}




function test2(swjgdm,swjgmc){
	var url = '/admin/ldpage.do?asyhz&swjgdm='+ swjgdm + '&swjgmc=' + swjgmc;
		fbrp.utils.openItem('asy' + swjgdm, "按税源("+swjgmc+")", "本月总户数", url);
}
function test2more(swjgdm,swjgmc){
	var url = '/admin/ldpage.do?asyhz&more=true';
		fbrp.utils.openItem('asyhz' + swjgdm, "总户数按税源，表", "本月总户数", url);
}



function test1(swjgdm,swjgmc){
	var url = '/admin/ldpage.do?azthz&swjgdm='+ swjgdm + '&swjgmc=' + swjgmc;
	fbrp.utils.openItem('azt' +swjgdm, "按状态("+swjgmc+")", "本月总户数", url);
}

function test1more(swjgdm,swjgmc){
	var url = '/admin/ldpage.do?azthz&more=true';
	fbrp.utils.openItem('azthz'+swjgdm, "总户数按状态，表", "本月总户数", url);
}


function test4(){
	var url = '/admin/ldpage.do?tosssr&more=true&flag=' + jQuery("input[name='ddsss']:checked").val();
	fbrp.utils.openItem("yzrk", "应征入库监控", "首页>应征入库监控", url);
}


/**
 * 数据展现到本月新开表格
 */
function renderToXkTable(dataObjArr){
	var length=dataObjArr.length;
	var m = $("#byxkTable");
	var trflag=0;
	m.find("tr[class!=th]").each(function(index, ele) {
		if(trflag<length){
		//var tmp = $(ele).remove();
	//	var trString="";
		var tmpObj=dataObjArr[index];
		var flag=0;
		$(ele).find("td").each(function(index2, ele2) {
			   switch (flag) {
			     case 0:
			    	 if( tmpObj.swjgmc.indexOf('本级')>=0){
				    	 $(ele2)[0].innerHTML= tmpObj.swjgmc;
			    	 }else{
				    	 $(ele2)[0].innerHTML="<a href=\"#\" onclick=\"test('"  + tmpObj.swjgdm + "','" + tmpObj.swjgmc +"')\"> "+tmpObj.swjgmc + "</a>";
			    	 }
				  break;
			     case 1:
			      	 $(ele2)[0].innerText=tmpObj.byxk + "(" + tmpObj.byybswdj+  ")";
					  break;
			     case 2:
			    	 $(ele2)[0].innerText=tmpObj.syxk + "(" + tmpObj.syybswdj+  ")";
					  break;
			     case 3:
			    	 $(ele2)[0].innerText=tmpObj.qntqxk + "(" + tmpObj.qntqybswdj+  ")";
					  break;
		     	default:
				break;
			}
			   flag=flag+1;
		});
	}
		trflag++;
	});
}

/**
 * 数据展现总户数按状态
 */
function renderToZhsAztTable(dataObjArr){
	var m = $("#zhsazt");
	var length=dataObjArr.length;
	var trflag=0;

	m.find("tr[class!=th]").each(function(index, ele) {
		if(trflag<length){
		//var tmp = $(ele).remove();
	//	var trString="";
		var tmpObj=dataObjArr[index];
		var flag=0;
		$(ele).find("td").each(function(index2, ele2) {
			   switch (flag) {
			     case 0:
			     	 if( tmpObj.swjgmc.indexOf('本级')>=0){
				    	 $(ele2)[0].innerHTML=tmpObj.swjgmc;
			    	 }else if(tmpObj.swjgmc.indexOf('总计')>=0){
			    		 $(ele2)[0].innerHTML="<a href=\"#\" onclick=\"test1more()\"> "+tmpObj.swjgmc + "</a>"; 
			    	 }else{
			    	 $(ele2)[0].innerHTML="<a href=\"#\" onclick=\"test1('"  + tmpObj.swjgdm + "','" + tmpObj.swjgmc +"')\"> "+tmpObj.swjgmc + "</a>";
			    	 }
				  break;
			     case 1:
			      	 $(ele2)[0].innerText= tmpObj.byzh;
					  break;
			     case 2:
			    	 $(ele2)[0].innerText= tmpObj.byzhzc;
					  break;
			     case 3:
			    	 $(ele2)[0].innerText= tmpObj.byzhty;
					  break;
			     case 4:
			    	 $(ele2)[0].innerText=tmpObj.byzhfzc;
					  break;
		     	default:
				break;
			}
			   flag=flag+1;
		});
		}
		trflag++;
	});
}



/**
 * 数据展现总户数按税源
 */
function renderToZhsAsyTable(dataObjArr){
	var m = $("#zhsasy");
	var length=dataObjArr.length;
	var trflag=0;
	m.find("tr[class!=th]").each(function(index, ele) {
		if(trflag<length){
		//var tmp = $(ele).remove();
	//	var trString="";
		var tmpObj=dataObjArr[index];
		var flag=0;
		$(ele).find("td").each(function(index2, ele2) {
			   switch (flag) {
			     case 0:
			     	 if( tmpObj.swjgmc.indexOf('本级')>=0){
				    	 $(ele2)[0].innerHTML=tmpObj.swjgmc;
			    	 }else if(tmpObj.swjgmc.indexOf('总计')>=0){
			    		 $(ele2)[0].innerHTML="<a href=\"#\" onclick=\"test2more()\"> "+tmpObj.swjgmc + "</a>";
			    
			    	 }else{
			    	 $(ele2)[0].innerHTML="<a href=\"#\" onclick=\"test2('"  + tmpObj.swjgdm + "','" + tmpObj.swjgmc +"')\"> "+tmpObj.swjgmc + "</a>";
			    	 }
				  break;
			     case 1:
			      	 $(ele2)[0].innerText= tmpObj.byzhsy;
					  break;
			     case 2:
			    	 $(ele2)[0].innerText=  tmpObj.byzhsyzjzd;
					  break;
			     case 3:
			    	 $(ele2)[0].innerText= tmpObj.byzhsysjzd;
					  break;
			     case 4:
			    	 $(ele2)[0].innerText= tmpObj.byzhsyshij;
					  break;
			     case 5:
			    	 $(ele2)[0].innerText= tmpObj.byzhsyxjzd;
					  break;
			     case 6:
			    	 $(ele2)[0].innerText=+ tmpObj.byzhsyfjzd;
					  break;
			     case 7:
			    	 $(ele2)[0].innerText=tmpObj.byzhsyyb;
					  break;
			     case 8:
			    	 $(ele2)[0].innerText=tmpObj.byzhsysmall;
					  break;
		     	default:
				break;
			}
			   flag=flag+1;
		});
		}
		trflag++;
	});
}
/**
 * 数据展现到应征和入库表格
 */
function renderToSsTable(dataObjArr){
		var length=dataObjArr.length;
		var html="";
		if(length!=null && length!=undefined && length>0){
		for(var i=0;i<length;i++){
			var dataObj=dataObjArr[i];
			  if(i>=length-3){
				  html+="<tr  style=\"BACKGROUND-COLOR: #f2f9fd; \">";
			  }	else{
					if(i%2==0){
						html+="<tr class='com_table_tb01'>";
					}else{
						html+="<tr >";
					}
				  
			  }
				html+="<td style='text-align:left;'>" + dataObj.swjgmc  + "</td>";
				html+="<td style='text-align:right;'>" +fmoney( dataObj.hj)  + "</td>";
				html+="<td  style='text-align:right;'>" + fmoney(dataObj.zyj)  + "</td>";
				html+="<td style='text-align:right;'>" +fmoney( dataObj.sj)  + "</td>";
				html+="<td style='text-align:right;'>" + fmoney(dataObj.sg)  + "</td>";
				html+="<td style='text-align:right;'>" +fmoney( dataObj.dj)  + "</td>";
				html+="<td style='text-align:right;'>" +fmoney( dataObj.qt)  + "</td>";

				html+="</tr>";
		}
	}else{
		html="暂无数据";
	}
	var tbody=$("#sssrTbody").html(html);
}
<!--
/*第一种形式 第二种形式 更换显示样式*/

	function setTab(name, cursel, n) {
		for (i = 1; i <= n; i++) {
			var menu = document.getElementById(name + i);
			var con = document.getElementById("con_" + name + "_" + i);
			menu.className = i == cursel ? "current" : "";
			con.style.display = i == cursel ? "block" : "none";
		}
	}
//-->
</script>
<input type="hidden" value="2"  id="qhsy" />
<div class="com_tab fbrp_ui_table"  style="width:100%"> 
            <div class="com_tab" style="margin-bottom: 10px;">
            <div class="com_tab_top">
            <div class="title"><span><b class="tab_b">本月新开户数</b></span></div>
            	<ul class="ui-table-toolbar">
        		<li class="toolbar_li"><a href="#" class="toolbar_li_a"  onclick="testmore()"><strong>更多...</strong></a></li>
                </ul>
            </div>
            <div class="com_tab_main">
            <!--com_table begin -->
            <table id="byxkTable" width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="com_table">
            <tbody>
            <tr class="th">
            <th><div class="titlex"><span>税务机关名称</span><img src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif" /></div></th>
            <th><div class="titlex"><span>本月新开户数(其中一般税务登记)</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
            <th><div class="titlex"><span>上月开户数(其中一般税务登记)</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
            <th><div class="titlex"><span>去年同期数(其中一般税务登记)</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
            </tr>
            
            <tr>
            <td style="text-align:left"></td>
            <td></td>
			<td></td>
            <td></td>
            </tr>
            
            <tr class="com_table_tb01">
          <td style="text-align:left"></td>
            <td></td>
			<td></td>
            <td></td>
            </tr>
            
            <tr >
            <td style="text-align:left"></td>
            <td></td>
			<td></td>
            <td></td>
            </tr>
            
             <tr class="com_table_tb01">
            <td style="text-align:left"></td>
            <td></td>
			<td></td>
            <td></td>
            </tr>
            
            <tr >
          <td style="text-align:left"></td>
            <td></td>
			<td></td>
            <td></td>
            </tr>
             <tr class="com_table_tb01">
           <td style="text-align:left"></td>
            <td></td>
			<td></td>
            <td></td>
            </tr>
            </tbody>
            </table>
            <!--com_table end --> 
            </div><!-- com_tab_main end -->
            
            </div>
   </div>

<table width="100%" cellspacing="0" cellpadding="0" border="0" align="center">
    <tbody>
    	<tr>
            <td width="40%" valign="top">
            
            <div class="com_tab" style="margin-bottom: 10px;">
            
					<div class="com_tab_top02 info_title">
						<ul class="tab tab02">
							<li id="titletab051" onMouseOver="setTab('titletab05',1,2)"
								class="current"><a href="#"><span>总户数(按状态，图)</span></a></li>
							<li id="titletab052" onMouseOver="setTab('titletab05',2,2)"><a
								href="#"><span>总户数(按状态，表)</span></a></li>
						</ul>
						<ul class="ui-table-toolbar">
		        		<li class="toolbar_li"><a class="toolbar_li_a" href="#"   onclick="test1more()"><strong>更多...</strong></a></li>
		                </ul>
					</div>

					<div id="con_titletab05_1" class="com_tab_main com_tab_main02" style="height:208px">
						<div id="zhsztChart" style="height: 190px;"></div>
					</div>
					
					<div id="con_titletab05_2" class="com_tab_main com_tab_main02" style="display:none;height:203px;border:medium none;">
					      <table id="zhsazt" width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="com_table">
				            <tbody>
				            <tr class="th">
				            <th width="36%"><div class="titlex"><span>税务机关名称</span><img src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif" /></div></th>
				            <th><div class="titlex"><span>本月总户数</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>正常户数</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>停业户数</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>非正常营业户数</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            </tr>
				            <tr>
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				            </tr>
				              <tr class="com_table_tb01">
				           <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				            </tr>
				             <tr>
				           <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				            </tr>
				              <tr class="com_table_tb01">
				              <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				            </tr>
				             <tr>
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				            </tr>
				              <tr class="com_table_tb01">
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				            </tr>
				             <tr>
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				            </tr>
				         
				            </tbody>
				         </table>
            
					</div>
					
					
				</div>
			</td>
            
            <td width="60%" valign="top">

				<div class="com_tab" style="margin-bottom: 10px; margin-left: 10px;">
					<div class="com_tab_top02 info_title">
						<ul class="tab tab02">
							<li id="titletab061" style="border-left: none;" onMouseOver="setTab('titletab06',1,2)"
								class="current"><a href="#"><span>总户数(按税源类型，图)</span></a></li>
							<li  id="titletab062" onMouseOver="setTab('titletab06',2,2)"><a
								href="#" ><span>总户数(按税源类型，表)</span></a></li>
						</ul>
						<ul class="ui-table-toolbar">
		        		<li class="toolbar_li"><a class="toolbar_li_a" href="#" onclick="test2more()"><strong>更多...</strong></a></li>
		                </ul>
					</div>

					<div id="con_titletab06_1" class="com_tab_main com_tab_main02" style="height:208px">
							<div id="zhsSyChart" style="height: 190px;"></div>

					</div>
					  
					<div id="con_titletab06_2" class="com_tab_main com_tab_main02"  style="display:none;height:209px;border:medium none;">
   					    <table  id="zhsasy"  width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="com_table">
				            <tbody>
				            <tr class="th">
				            <th width="20%"><div class="titlex"><span>税务机关名称</span><img src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif" /></div></th>
				            <th><div class="titlex"><span>本月总户数</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>总局重点</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>省级重点</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				             <th><div class="titlex"><span>市级重点</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				             <th><div class="titlex"><span>县级重点</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>分局重点</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>一般税源</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            <th><div class="titlex"><span>小税源</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
				            </tr>
				            <tr>
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				               <td></td>
				            </tr>
				              <tr class="com_table_tb01">
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				               <td></td>
				            </tr>
				              <tr>
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				               <td></td>
				            </tr>
				               <tr class="com_table_tb01">
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				               <td></td>
				            </tr>
				              <tr>
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				               <td></td>
				            </tr>
				                   <tr class="com_table_tb01">
				              <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				               <td></td>
				            </tr>
				              <tr>
				             <td style="text-align:left"></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				              <td></td>
				               <td></td>
				            </tr>
				          
				            </tbody>
				         </table>
            
					</div>

				</div>
			</td>
        
        	
            
        </tr>
    </tbody>
</table>

<div class="com_tab fbrp_ui_table"  style="width:100%"> 
            <div class="com_tab">
            <div class="com_tab_top">
            <div class="title"><span><b class="tab_b">应征和入库数</b></span></div>
            	<div class="filterItemDiv">
				 <input type="radio"  name="ddsss"  checked   onclick="initSssr(this.value)"  value="1"/><label >应征</label>
                 <input type="radio"  name="ddsss"    onclick="initSssr(this.value)"  value="3"/><label>入库</label>
                 </div>
                 <ul class="ui-table-toolbar">
		        		<li class="toolbar_li"><a class="toolbar_li_a" href="#" onclick="test4()"><strong>更多...</strong></a></li>
		        </ul>
            </div>
            
            </div>
            
            <div class="com_tab_main">
            <!--com_table begin -->
            <table width="100%" cellspacing="0" cellpadding="0" border="0" align="center" class="com_table">
            <thead>
            <tr class="th">
            		 <th><div class="titlex"><span>税种名称</span><img src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
            		<th><div class="titlex"><span>合计</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
		            <th><div class="titlex"><span>中央级</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
		            <th><div class="titlex"><span>省级</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
		            <th><div class="titlex"><span>其中省固</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
		            <th><div class="titlex"><span>市县级</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
		             <th><div class="titlex"><span>其它</span><img class="ui_table_split ui-draggable" src="<%=ctx%>/resource/fbrp/ui/images-systems-01/com_table/split.gif"/></div></th>
		            
            </tr>
            </thead>
            <tbody id="sssrTbody">
            </tbody>
            </table>
            <!--com_table end --> 
            </div><!-- com_tab_main end -->
            
            
            </div>
   </div>
