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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>left</title>
<link rel="stylesheet" href="<%=ctx%>/resources/ztree/css/zTreeStyle/zTreeStyle.css" type="text/css">
<link rel="stylesheet" href="<%=ctx %>/resources/tinyscrollbar/tinyscrollbar.css" type="text/css" media="screen"/>
<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/ztree/js/jquery.ztree.core-3.5.js"></script>
<script type="text/javascript" src="<%=ctx %>/resources/tinyscrollbar/jquery.tinyscrollbar.js" ></script>

<link href="<%=ctx %>/resources/images/ui/css-common/style-common.css" rel="stylesheet" type="text/css">
<link href="<%=ctx %>/resources/images/ui/css-systems-01/style.css" rel="stylesheet" type="text/css">

<style type="text/css">
.lmnav_div_w .scrollbarx {
	background:
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-track02-x.png)
		no-repeat 0 0;
	position: relative;
	background-position: 0 0 5px;
	clear: both;
	height: 6px;
}

.lmnav_div_w .trackx {
	background: transparent
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-trackend02-x.png)
		no-repeat 100% 0;
	height: 6px;
	width: 100%;
	position: relative;
}

.lmnav_div_w .thumbx {
	background: transparent
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-thumb02-x.png)
		no-repeat 100% 50%;
	height: 7px;
	cursor: pointer;
	overflow: hidden;
	position: absolute;
	top: 0px;
	left: 0;
}

.lmnav_div_w .thumbx .endx {
	background: transparent
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-thumb02-x.png)
		no-repeat 0 50%;
	overflow: hidden;
	height: 7px;
	width: 5px;
	*background:
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-thumb02-x.png)
		no-repeat 0 50%;
	*position: absolute;
	*left: 0;
	*top: -3px;
}

.lmnav_div .scrollbar {
	background:
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-track-y.jpg)
		no-repeat 0 0;
	position: relative;
	background-position: 0 0;
	float: right;
	width: 6px;
}

.lmnav_div .track {
	background: transparent
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-trackend-y.jpg)
		no-repeat 0 100%;
	height: 100%;
	width: 6px;
	position: relative;
}

.lmnav_div .thumb {
	background: transparent
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-thumb-y.jpg)
		no-repeat 50% 100%;
	height: 20px;
	width: 15px;
	cursor: pointer;
	overflow: hidden;
	position: absolute;
	top: 0;
	left: -5px;
}

.lmnav_div .thumb .end {
	background: transparent
		url(<%=ctx%>/resources/images/ui/images-systems-01/scrollbar/bg-scrollbar-thumb-y.jpg)
		no-repeat 50% 0;
	overflow: hidden;
	height: 5px;
	width: 15px;
}
</style>
<script type="text/javascript">
var leftJson = {};
leftJson.wwidth = [];
var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick:function(event, treeId, treeNode){
				if($.browser.mozilla){
					//parent.document.getElementById("headerFrame").contentWindow.openTab(event, treeId, treeNode);
				}else{
					//parent.frames("headerFrame").window.openTab(event, treeId, treeNode);
				}
				addTab(event, treeId, treeNode);
				
			},
			beforeClick:function(treeId,treeNode){
				for(var i = 0 ;i <topItems.length;i++){
					var node = topItems[i];
					var tree = $.fn.zTree.getZTreeObj("lix_"+node.id);
					if(tree){
						tree.cancelSelectedNode();
					}
				}
				return true;
			},
			onExpand:function(){
				updateScroll();
				var maxLiw = 0;
				$("#acc>li").each(function(){
					var liw = $(this).width();
					maxLiw = liw > maxLiw ? liw : maxLiw;
				});
				if(maxLiw > 220){
					parent.document.getElementById("mset").cols = (maxLiw+15) + ",7,*";
				} else {
					parent.document.getElementById("mset").cols = "220,7,*";
				}
			},
			onCollapse:function(){
				updateScroll();
				var maxLiw = 0;
				$("#acc>li").each(function(){
					var liw = $(this).width();
					maxLiw = liw > maxLiw ? liw : maxLiw;
				});
				if(maxLiw > 220){
					parent.document.getElementById("mset").cols = maxLiw + ",7,*";
				} else {
					parent.document.getElementById("mset").cols = "220,7,*";
				}
			}
		}
	};
var topItems = ${top};

$(function(){
	//初始化垂直滚动条
	$("#scb").tinyscrollbar({
		axis : 'y',
		size : tHeight
	});
	//初始化菜单树
	var nodes = ${treeData};
	for(var i = 0 ;i <topItems.length;i++){
		var node = topItems[i];
		//菜单标题
		var accItemHTML = "<li><h3><img src='<%=ctx %>"+ node.exts.icon + "' />" +
									"<a href='javascript:void(0)'>"+node.name+"</a>" +
									"<img class='down' src='<%=ctx %>/resources/images/ui/images-systems-01/lmnav/down.gif' />" +
									"<a class='show_a' href='javascript:void(0)'>" +
									"<img class='show_down' title='最大化' src='<%=ctx %>/resources/images/ui/images-systems-01/lmnav/lessen.gif' /></a>" +
							   "</h3>" +
							   "<ul id='lix_"+node.id+"' class='ztree' style='display:none;margin:0px;'></ul>" +
						   "</li>";

		$('#acc').append(accItemHTML);
		//菜单树节点
		var ns = nodes[0][topItems[i].id];
		for(var m = 0 ;m <ns.length;m++){
			ns[m].exts.topNodeName = node.name;
		}
		$.fn.zTree.init($("#lix_"+node.id), setting, ns);
	}
	
	var isIE6 = $.browser.msie&&$.browser.version=='6.0';
	
	var tHeight = $(window).innerHeight()-22;
	$('.lmnav').height(tHeight);
	if(isIE6){
		var tWidth = $(window).innerWidth()-18;
		$('.lmnav').width(tWidth);
	}

	$('.show_a').toggle(function(){
		var $parent = $(this).parent("h3").parent("li");
		$parent.parent("ul").children("li").not($parent).hide();
		$("img.show_down", $(this).parent("h3")).attr("src", "<%=ctx %>/resources/images/ui/images-systems-01/lmnav/amplify.gif");
		$("img.show_down", $(this).parent("h3")).attr("title", "还原");
		$("img.down", $(this).parent("h3")).attr("src","<%=ctx %>/resources/images/ui/images-systems-01/lmnav/up.gif");
		if(isIE6){
			$(this).parent("h3").next().show();
			updateScroll();
		}else{
			$(this).parent("h3").next().show("fast",function(){
				updateScroll();
			});
		}
	}, function(){
		var $parent = $(this).parent("h3").parent("li");
		$parent.parent("ul").children("li").not($parent).show();
		$("img.show_down", $(this).parent("h3")).attr("src", "<%=ctx %>/resources/images/ui/images-systems-01/lmnav/lessen.gif");
		$("img.show_down", $(this).parent("h3")).attr("title", "最大化");
		updateScroll();
	});
	$('.lmnav>ul>li>h3').toggle(function(){
		$("img.down",this).attr("src","<%=ctx%>/resources/images/ui/images-systems-01/lmnav/up.gif");
		if(isIE6){
			$(this).next().show();
			updateScroll();
		}else{
			$(this).next().show("fast",function(){
				updateScroll();
			});
		}
	},function(){
		$("img.down",this).attr("src","<%=ctx%>/resources/images/ui/images-systems-01/lmnav/down.gif");
			if (isIE6) {
				$(this).next().hide();
				updateScroll();
			} else {
				$(this).next().hide("fast", function() {
					updateScroll();
				});
			}
		}
	);
		
	$('#acc>li>h3').first().click();
});
	function updateScroll() {
		/* $("#scbw").tinyscrollbarx_update("relative"); */
		$("#scb").tinyscrollbar_update("relative");
	}

	function updateFavorite(nodes) {
		var favoriteId = topItems[0].id;
		$("#lix_" + favoriteId).empty();
		$.fn.zTree.init($("#lix_" + favoriteId), setting, nodes);
	}
	var addTab = function (e, treeId, node) {
		//e.stopEvent();// 停止href属性产品的链接操作(自动)
		if (!node.isParent) {// 如果是非叶子节点，则不用加载相应资源
			// 加载叶子节点对应的资源
			var tabPanel = parent.frames['mainFrame'].tabPanel;
			var tabs = tabPanel.getComponent(node.id);
			if (!tabs) {
				tabPanel.add({
					id : node.id,
					title : node.name,
					height:$('#mainFrame',window.parent.document).height()-tabPanel.tabBar.getHeight()-17,
					html : '<iframe id="head" frameborder="0" style="height:100%; width:100%" src="'
							+'<%=ctx%>'
							+ node.exts.url
							+ '" ',
					closable : true

				});
				tabs = tabPanel.getComponent(node.id);
			}
			tabPanel.setActiveTab(tabs);
		}
	};
</script>

</head>
<body style="background: #e2f0fd;padding-left: 7px;padding-top: 3px;">

	<table id="scb" width="100%" border="0" class="lmnav_div">
		<tr>
			<td valign="top" style="background-color: white;">
				<div class="lmnav viewport">
					<ul id="acc" class="overview"></ul>
				</div>
			</td>
			<td width="10" valign="top">
				<div class="scrollbar">
					<div class="track">
						<div class="thumb">
							<div class="end"></div>
						</div>
					</div>
				</div>
			</td>
		</tr>
	</table>

</body>
</html>