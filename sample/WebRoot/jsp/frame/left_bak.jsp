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
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=ctx%>/resources/extjs/css/ext-all.css" />
<script type="text/javascript" src="<%=ctx%>/resources/extjs/js/ext-all.js"></script>
<script type="text/javascript" src="<%=ctx%>/resources/jquery/js/jquery-1.7.2.js"></script>
<script type="text/javascript">


Ext.require([ '*' ]);

Ext.onReady(function() {

	var ctx = '<%=ctx%>';


	var accordion = Ext.create("Ext.Panel", {
		title : "菜单导航",
		width : 300,
		height : $('#lFrame',window.parent.document).height(),
		region : 'west',
		margins : '5 5 5 5',
		// iconCls : "icon-accordion",
		autoScroll : false,
		collapsible : true,
		renderTo : 'frame_menu',
		layout : 'accordion', // 设置布局
		layoutConfig : {
			animate : true
		// 动态切换树空间
		}
	});

	Ext.Ajax.request({
		url : ctx + '/cdxx/cdgl.do?getMenuPanel', // 请求地址
		// params : config.params, // 请求参数
		method : 'post', // 方法
		callback : function(options, success, response) {
			addTree(Ext.JSON.decode(response.responseText));// 调用回调函数
		}
	});
	
	function addTree(data) {
		// 添加我的收藏夹
		accordion.add(Ext.create("Ext.tree.Panel",{
			title : '我的收藏夹',
			// iconCls : data[i].iconCls,
			autoScroll : true,
			rootVisible : false,
			viewConfig : {
				loadingText : "正在加载..."
			},
			store : createStoreWdscj(),
			listeners : {
				itemclick : function (view, node, bondview, index,e){
					addTab(view, node, bondview, index,e);// 当点击时在右边主窗口中加载相应的资源
				}
			}
		}));

		for (var i = 0; i < data.length; i++) {
			accordion.add(Ext.create("Ext.tree.Panel",{
				title : data[i].title,
				iconCls : data[i].iconCls,
				autoScroll : true,
				rootVisible : false,
				viewConfig : {
					loadingText : "正在加载..."
				},
				store : createStore(data, i),
				listeners : {
					itemclick : function (view, node, bondview, index,e){
						addTab(view, node, bondview, index,e);// 当点击时在右边主窗口中加载相应的资源
					}
				}
			}));
		}
	}
	var model = Ext.define("TreeModel", { // 定义树节点数据模型
		extend : "Ext.data.Model",
		fields : [ {
			name : "id",
			type : "string"
		}, {
			name : "text",
			type : "string"
		}, {
			name : "iconCls",
			type : "string"
		}, {
			name : "leaf",
			type : "boolean"
		}, {
			name : "id",
			type : "string"
		} ]
	});
	var createStore = function(data, i) { // 创建树面板数据源
		var id = data[i].id;
		var store = Ext.create('Ext.data.TreeStore', {
			proxy : {
				type : 'ajax',
				clearOnLoad : true,
				url : ctx + '/cdxx/cdgl.do?userMenuPanelTree&id=' + id
			}
		});

		return store;
	};

	var createStoreWdscj = function() { // 创建树面板数据源
		var store = Ext.create('Ext.data.TreeStore', {
			proxy : {
				type : 'ajax',
				clearOnLoad : true,
				url : ctx + '/cdxx/cdgl.do?wdscj'
			}
		});

		return store;
	};
	
	var addTab = function (view, node, bondview, index,e) {
		e.stopEvent();// 停止href属性产品的链接操作(自动)
		if (node.isLeaf()) {// 如果是非叶子节点，则不用加载相应资源
			// 加载叶子节点对应的资源
			var tabPanel = parent.frames['mainFrame'].tabPanel;
			var tabs = tabPanel.getComponent(node.raw.text);
			if (!tabs) {
				tabPanel.add({
					id : node.raw.text,
					title : node.raw.text,
					height:$('#mainFrame',window.parent.document).height()-tabPanel.tabBar.getHeight()-17,
					html : '<iframe id="head" frameborder="0" style="height:100%; width:100%" src="'
							+ node.raw.url
							+ '" ',
					closable : true

				});
				tabs = tabPanel.getComponent(node.raw.text);
			}
			tabPanel.setActiveTab(tabs);
		}
	};
	
});

</script>

</head>
<body >
<div id='frame_menu'></div>
</body>
</html>