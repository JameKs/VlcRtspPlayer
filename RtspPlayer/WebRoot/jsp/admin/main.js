var tabPanel ;

Ext.require([ '*' ]);

Ext.onReady(function() {


	// 头面板
	var header = Ext.create('Ext.Panel',{
		xtype : 'box',
		collapsible : false,
		id : 'header',
		region : 'north',
		margins : '5 5 0 5',
		html : '<table>'
			+'<tr >'
			+'<td width="1200px"><img src="'+ctx+'/resources/images/head.jpg" alt="继续教育系统" height="60px" width="100%"></td>'
			+'<td width="400px"><span>'+$("#requestTime").val()+'</span><span id="requestTime"></span><span>毫秒</span></td>'
				+ '</tr><table>',
		height : 60
	});

	// 尾面板
	var footer = Ext.create('Ext.Panel',{
		xtype : 'box',
		collapsible : false,
		id : 'footer',
		region : 'south',
		margins : '0 5 5 5',
		html : '<iframe id="head" src="'+ctx+'/footer.html" frameborder="0" heigth="100%" width="100%"',
		height : 30
	});
	

	// 中部内容面板,默认为欢迎界面
	tabPanel = Ext.create('Ext.tab.Panel', {
		id : 'tabPanel',
		bodyStyle : 'padding:5px',
		activeTab : 0,
		//height : 600,
		margins : '5 5 5 0',
		region : 'center'

	});
	
	

	var accordion = Ext.create("Ext.Panel", {
		title : "菜单导航",
		width : 300,
		height : 500,
		region : 'west',
		margins : '5 5 5 5',
		// iconCls : "icon-accordion",
		autoScroll : false,
		collapsible : true,
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

	var viewport = Ext.create('Ext.Viewport', {
		renderTo : Ext.getBody(),
		layout : 'border',
		items : [ header, accordion, tabPanel, footer ]
	});
	
	var addTab = function (view, node, bondview, index,e) {
		e.stopEvent();// 停止href属性产品的链接操作(自动)

		if (node.isLeaf()) {// 如果是非叶子节点，则不用加载相应资源
			// 加载叶子节点对应的资源
			var tabs = tabPanel.getComponent(node.raw.text);
			if (!tabs) {
				tabPanel.add({
					id : node.raw.text,
					title : node.raw.text,
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
	
	//当viewport定义好后才能增加tab
	tabPanel.add({
		id : 'st',
		title : '首页',
		html : '欢迎进入继续教育系统',
		closable : false

	});
	tabPanel.setActiveTab('st');
	
});
