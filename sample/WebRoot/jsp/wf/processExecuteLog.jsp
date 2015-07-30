<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%
String ctx = request.getContextPath();
String msg=(String)request.getAttribute("msg");
msg=msg!=null?msg:"";
%>
<script>
var msg="<%=msg%>";
if(msg!='') alert("<%=msg%>");
</script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/tree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/tree/js/jquery.ztree.excheck-3.2.js"></script>
<link  rel="stylesheet" href="<%=ctx %>/pages/fbrp/scripts/tree/css/zTreeStyle.css" type="text/css" >
<style>
	#testIframe {margin-left: 10px;}
	.log{
	       border: 2px solid #9DC6F2;
	       height: 600px;
	}
	#wrapper{
	    width:100%;
        height:580px; 
	}
	.leftMenu{
	    float:left; 
	    width:18%; 
	    height:95%;
	    border: 1px solid #9DC6F2;
	}
	#leftMenuContent{
	     height:94%;
	     overflow: auto;
	}
	.content{
	    border: 1px solid #9DC6F2;
	    float: left;
	    height: 100%;
	    margin-left: 10px;
	    width: 80%;
	}
	
	.content  .contentTittle{
	     
	}
 
  </style>
  <SCRIPT type="text/javascript" >
  <!--
	var zTree;
	var demoIframe;

	var zTreeOnClick=function(event,treeId,treeNode){
		   var isParent=treeNode.isParent;
		   if(isParent==true){
			   return ;
		   }
		   
		   var $content=$('#content');
		   var $conTable=$content.find('table');
		   $conTable.find('tr').not($conTable.find('tr').first()).remove();
		   
		   var url='<%=ctx%>/workflow/processExecuteLog.do?loadLog';
           var tr='<tr valign="middle" align="center"><td>序号</td><td>流程实例ID</td><td>流程定义ID</td><td>任务ID</td><td>任务key值</td><td>任务节点</td><td>任务处理人</td><td>处理前的流程变量</td></tr>';
           
           var arr = [];
           $.post(url,{'id':treeNode.id},function(list){
				for(var i = 0;i<list.length;i++){
					       arr.push("<tr valign=\"middle\" align=\"center\">");
					       arr.push('<td>'+list[i].num+'</td>');
					       arr.push('<td>'+list[i].procId+'</td>');
					       arr.push('<td>'+list[i].procDefId+'</td>');
					       arr.push('<td>'+list[i].procTaskKey+'</td>');
					       arr.push('<td>'+list[i].procTNode+'</td>');
					       arr.push('<td>'+list[i].processMan+'</td>');
					       arr.push('<td>'+list[i].num+'</td>');
				}
				 arr.push('</tr>');
				 $conTable.find('tbody').append(arr.join(''));
		   });
	}
	var setting = {
		view: {
			dblClickExpand: false,
			showLine: true,
			selectedMulti: false,
			expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
		},
		data: {
			simpleData: {
				enable:true,
				idKey: "id",
				pIdKey: "pId",
				rootPId: ""
			}
		},
		callback:{
			  onClick:zTreeOnClick
		},
		async:{
			enable:true,
			url:'<%=ctx%>/workflow/processExecuteLog.do?load',
			autoParam:['id','name']
		}
	};
/*
	var zNodes =[
		{id:1, pId:0, name:"[core] 基本功能 演示", open:true},
		{id:101, pId:1, name:"最简单的树 --  标准 JSON 数据", file:"core/standardData"},
		{id:102, pId:1, name:"最简单的树 --  简单 JSON 数据", file:"core/simpleData"},
		{id:103, pId:1, name:"不显示 连接线", file:"core/noline"},
		{id:104, pId:1, name:"不显示 节点 图标", file:"core/noicon"}
	];*/

	var zNodes=${zNodes};

	function loadReady() {
		var bodyH = demoIframe.contents().find("body").get(0).scrollHeight,
		htmlH = demoIframe.contents().find("html").get(0).scrollHeight,
		maxH = Math.max(bodyH, htmlH), minH = Math.min(bodyH, htmlH),
		h = demoIframe.height() >= maxH ? minH:maxH ;
		if (h < 530) h = 530;
		demoIframe.height(h);
	}
	
	var tableSetting = {
			url:'',
			parameter:{},
			toolbar:[],
			operator:[]
	};
	 var settingTree = {
			 view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				data: {
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pId",
						rootPId: ""
					}
				},
				callback:{
					  onClick:zTreeOnClick
				},
				async:{
					enable:true,
					url:'<%=ctx%>/processExecuteLog/manager.do?load',
					autoParam:['id','name']
				}
			};

  //-->
  </SCRIPT>
 </HEAD>

<BODY>
   <div id='wrapper'>
	   <div id='leftMenu' class='leftMenu log' style="float: left;">
	              <f:panel header="模板树" height="400px;" width="100%;">
						<f:tree setting="settingTree" initValue="treeData" id="trees"></f:tree>
				  </f:panel>
	    </div>
	    <div id='content' class='content log' style="float: left;">
	         <f:table header="日志信息" initValue="logDataList" id="rsTable" setting="tableSetting" showPageBar="false" showStripe="true">
				<f:column title="流程实例ID" property="stdId"  width="10%"/>
				<f:column title="流程定义ID" property="defId" width="10%" />
				<f:column title="任务ID" property="tskId" width="10%" />
				<f:column title="任务节点" property="nodNm" width="20%" />
				<f:column title="任务处理人" property="userNm" width="15%" />
				<f:column title="处理前的流程变量" property="variable" width="20%" />
			</f:table>

	    </div>
</BODY>
</HTML>