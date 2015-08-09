Ext.require([ '*' ]);

Ext.onReady(function() {

	/* 定义用于显示数据的Grid */

	// 这里就是设置解析格式的地方，一定要和你的Model一样，要不然可是什么都得不到哦~~~~
	Ext.define('Role', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			mapping : 'id'
		}, {
			name : 'id',
			mapping : 'id'
		}, {
			name : 'name',
			mapping : 'name'
		}, {
			name : 'bz',
			mapping : 'bz'
		}, {
			name : 'yxBz',
			mapping : 'yxBz'
		} ]
	});

	var gridStore = new Ext.data.JsonStore({
		model : 'Role',
		pageSize : 10,
//		params : {
//			id : 'YG-000000'
//		},
		proxy : {
			type : 'ajax',
			url : ctx + '/jsxx/jsgl.do?find',

			reader : {
				type : 'json',
				root : 'results',
				idProperty : 'id',
				totalProperty : 'totalCount'
			}
		},
	});

	var cm = [ { 
		xtype: "rownumberer", 
		text: "行号", 
		width:40
	},{
		header : "id",
		dataIndex : 'id'
	}, {
		header : "ID",
		dataIndex : 'id'
	}, {
		header : "name",
		dataIndex : 'name'
	}, {
		header : "bz",
		dataIndex : 'bz'
	}, {
		header : "xyBz",
		dataIndex : 'xyBz'
	} ];

	var jsxxGrid = new Ext.grid.GridPanel({
		store : gridStore,
		columns : cm,
		frame : true,
		height : 350,
		width : 400,
		// region:'west',
		bbar : Ext.create('Ext.PagingToolbar', {
			store : gridStore,
			displayInfo : true,
			displayMsg : 'Displaying topics {0} - {1} of {2}',
			emptyMsg : "No topics to display",
			region : 'north'
		})
	});
	// store.loadPage(1);

	var viewport = Ext.create('Ext.Viewport', {
		renderTo : Ext.getBody(),
		layout : 'hbox',
		height : 600,
		items : [ jsxxGrid]
	});

	var Fpanel = new Ext.tree.TreePanel({
		id : 'ptree',
		region : 'west',
		layout : 'anchor',
		border : false,
		rootVisible : false,
		root : new Ext.tree.AsyncTreeNode({}),
		listeners : {
			"checkchange" : function(node, state) {
				if (node.parentNode != null) {
					// 子节点选中
					node.cascade(function(node) {
						node.attributes.checked = state;
						node.ui.checkbox.checked = state;
						return true;
					});
					// 父节点选中
					var pNode = node.parentNode;
					if (state || Fpanel.getChecked(id, pNode) == "") {
						pNode.ui.toggleCheck(state);
						pNode.attributes.checked = state;
					}
				}
			}
		}
	});
	authorityTree(Fpanel);
	var authorityTree = function(Fpanel) {
		Ext.Ajax.request({
			url : 'authorityTree.ashx',
			method : 'get',
			success : function(request) {
				var data = Ext.util.JSON.decode(request.responseText);
				Fpanel.getRootNode().appendChild(data);
				Fpanel.getRootNode().expandChildNodes(true);
				Fpanel.expandAll();
			},
			failure : function() {
				Fpanel.render();
				Ext.MessageBox.show({
					title : '提示',
					msg : '服务器忙,请使用火狐浏览器浏览或稍后重试！',
					buttons : Ext.MessageBox.OK,
					icon : Ext.MessageBox.ERROR
				});
			}
		});
	}

});
