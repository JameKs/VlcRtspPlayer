Ext.require([ '*' ]);

Ext.onReady(function() {

	var searchForm = Ext.create('Ext.form.Panel', {
		renderTo :'js_form',
		title : '按条件搜索',
		height : 60,
		width : '100%',
		defaultType : 'textfield',
		frame : false,
		method : 'POST',
		collapsible : false,// 可折叠
		bodyPadding : 1,
		layout : 'column',
		margin : '0 0 0 0',
		items : [ {
			fieldLabel : '角色ID',
			labelWidth : 80,
			id : 'id'
		}, {
			fieldLabel : '角色代码',
			labelWidth : 80,
			id : 'roleDm'
		}, {
			fieldLabel : '角色名称',
			labelWidth : 80,
			id : 'roleName'
		}, {
			xtype : 'button',
			text : '搜索',
			margin : '0 0 0 5',
			handler : function() {
				var id = Ext.getCmp('id').getValue();
				var name = Ext.getCmp('name').getValue(); // 获取文本框值

				gridStore.load({
					params : {
						id : id,
						name : name
					}
				});// 传递参数

			}
		} ]
	});

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
			name : 'roleDm',
			mapping : 'roleDm'
		}, {
			name : 'roleName',
			mapping : 'roleName'
		}, {
			name : 'bz',
			mapping : 'bz'
		}, {
			name : 'xyBz',
			mapping : 'xyBz'
		} ]
	});

	var gridStore = new Ext.data.JsonStore({
		model : 'Role',
		pageSize : 10,
		proxy : {
			type : 'ajax',
			url : ctx+'/jsxx/jsgl.do?findList',

			reader : {
				type : 'json',
				root : 'items',
				idProperty : 'id',
				totalProperty : 'totalCount'
			}
		}
	});

	var cm = [ {
		header : "id",
		dataIndex : 'id'
	}, {
		header : "角色代码",
		dataIndex : 'roleDm'
	}, {
		header : "角色名称",
		dataIndex : 'roleName'
	}, {
		header : "备注",
		dataIndex : 'bz'
	}, {
		header : "选用标志",
		dataIndex : 'xyBz'
	} ];

	var jsxxGrid = new Ext.grid.GridPanel({
		renderTo :'js_grid',
		store : gridStore,
		columns : cm,
		frame : false,
		height : 320,
		width : '100%',
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			store : gridStore, // same store GridPanel is using
			dock : 'bottom', // 分页 位置
			emptyMsg : '没有数据',
			displayInfo : true,
			displayMsg : '当前显示{0}-{1}条记录 / 共{2}条记录 ',
			beforePageText : '第',
			afterPageText : '页/共{0}页'
		} ],
		listeners : {
			// 监听单击事件 records 当前行 对象
			itemclick : function(dv, records, item, index, e) {
				// alert(record.data.id);

			},
			selectionchange : function(model, records) {
				loadZtree(records[0].data.id);
			}
		}

	});

	// 初始加载第1页
	gridStore.loadPage(1);
	
	var setting = {
			check: {
				enable: true
			},
			data: {
				key: {
					title: "title"
				},
				simpleData: {
					enable: true
				}
			}
		};

		function saveNodes() {
			var rows = jsxxGrid.getSelectionModel().getSelection();
			var roleId = '';
			if(rows != null){
				roleId = rows[0].data.id;
			}else{
				Ext.Msg.alert('Errer', '请选择角色');
			}
			var zTree = $.fn.zTree.getZTreeObj("treeDemo");
			var checkedNodes = zTree.getCheckedNodes();
			var ids = '';
			for(var i=0;i<checkedNodes.length;i++){
				ids += checkedNodes[i].id +"#";
			}
			$.ajax({
				url:ctx+'/jsxx/jssq.do?save',
				data:{
					'ids' : ids,
					'roleId':roleId
				},
				type:'post',    
			    cache:false,    
			    dataType:'json',  
			    success: function(data) {    
					Ext.Msg.alert('Success', data.msg);
			     },    
			     error : function() {    
			    	 alert("异常！"); 
			     }
				
			});
		}
		
		$("#save").bind("click", {type:"rename"}, saveNodes);
		
		function loadZtree(roleId){
			$.ajax({
				url:ctx+'/jsxx/jssq.do?findRoleMenuZtree',
				data:{    
					"roleId" : roleId 
			    },    
			    type:'post',    
			    cache:false,    
			    dataType:'json',  
			    success: function(data) {    
			        if(data != '' ){    
			        	$.fn.zTree.init($("#treeDemo"), setting, data);
			        }
			     },    
			     error : function() {    
			          alert("异常！"); 
			     }

			});
			
		}

//	var treeStore = Ext.create('Ext.data.TreeStore', {
//		autoLoad : false,
//		proxy : {
//			type : 'ajax',
//			actionMethods : {
//				read : 'POST'
//			},
//			url : ctx+'/jsxx/jssq.do?findRoleMenuTree' // 默认不查询出记录
//		}
//
//	});

//	var treePanel = Ext.create('Ext.tree.Panel', {
//		renderTo :'js_tree',
//		title : '菜单权限',
//		store : treeStore,
//		rootVisible : false,
//		useArrows : true,
//		rowspan : 2,
//		frame : false,
//		style : 'margin-left: 5px',
//		width : 300,
//		height : 450,
//		tbar : [ {
//			text : '保存',
//			handler : function() {
//				var rows = jsxxGrid.getSelectionModel().getSelection();
//				if(rows != null){
//					roleId = rows[0].data.id;
//				}else{
//					Ext.Msg.alert('Errer', '请选择角色');
//				}
//				var checkedNodes = treePanel.getChecked();
//				var ids = '';
//				for(var i=0;i<checkedNodes.length;i++){
//					ids += checkedNodes[i].raw.id +"#";
//				}
//				Ext.Ajax.request({
//					url : ctx+'/jsxx/jssq.do?save', // 请求地址
//					params : {
//						'ids' : ids,
//						'roleId':roleId
//					}, // 请求参数
//					method : 'post', // 方法
//					callback : function(options, success, response) {
//						var data = Ext.JSON
//								.decode(response.responseText);
//						if (success) {
//							Ext.Msg.alert('Success', data.msg);
//						} else {
//							Ext.Msg.alert('Failure', data.msg);
//						}
//					}
//				});
//
//			}
//
//		} ]
//	});

//	var viewport = Ext.create('Ext.Panel', {
//		renderTo :'jssq',
//		layout : {
//			type : 'table',
//			columns : 2
//		},
//		frame : false,
//		items : [ searchForm, treePanel, jsxxGrid ]
//	});

});
