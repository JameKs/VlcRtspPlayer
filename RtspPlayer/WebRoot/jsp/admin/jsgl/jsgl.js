Ext.require([ '*' ]);

Ext.onReady(function() {

	var searchForm = Ext.create('Ext.form.Panel', {
		title : '按条件搜索',
		width : 800,
		defaultType : 'textfield',
		frame : true,
		method : 'POST',
		collapsible : false,// 可折叠
		bodyPadding : 5,
		layout : 'column',
		margin : '0 0 10 0',
		items : [ {
			fieldLabel : '角色代码',
			labelWidth : 80,
			id : 'roleDm'
		}, {
			fieldLabel : '角色名称',
			labelWidth : 80,
			id : 'roleName'
		}, {
			fieldLabel : '有效标志',
			labelWidth : 80,
			id : 'yxBz'
		}, {
			xtype : 'button',
			text : '搜索',
			margin : '0 0 0 5',
			handler : function() {
				var roleDm = Ext.getCmp('roleDm').getValue();
				var roleName = Ext.getCmp('roleName').getValue(); // 获取文本框值
				var yxBz = Ext.getCmp('yxBz').getValue(); // 获取文本框值

				gridStore.load({
					params : {
						roleDm : roleDm,
						roleName : roleName,
						yxBz : yxBz
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
			name : 'roleDm',
			mapping : 'roleDm'
		}, {
			name : 'roleName',
			mapping : 'roleName'
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
		dataIndex : 'id',
		hidden:true
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
		header : "有效标志",
		dataIndex : 'yxBz'
	} ];

	var jsxxGrid = new Ext.grid.GridPanel({
		store : gridStore,
		columns : cm,
		frame : false,
		height : 320,
		width : 800,
		// region:'west',
		// bbar : Ext.create('Ext.PagingToolbar', {
		// store : gridStore,
		// displayInfo : true,
		// displayMsg : 'Displaying topics {0} - {1} of {2}',
		// emptyMsg : "No topics to display",
		// region : 'north'
		// })
		dockedItems : [ {
			xtype : 'pagingtoolbar',
			store : gridStore, // same store GridPanel is using
			dock : 'bottom', // 分页 位置
			emptyMsg : '没有数据',
			displayInfo : true,
			displayMsg : '当前显示{0}-{1}条记录 / 共{2}条记录 ',
			beforePageText : '第',
			afterPageText : '页/共{0}页'
		}, {
			xtype : 'toolbar',
			items : [ {
				iconCls : 'icon-add',
				text : 'Add',
				scope : this, // 添加
				handler : function() {
					Panel.show(); // 显示
				}
			}, {
				iconCls : 'icon-delete',
				text : 'Delete',
				// disabled: true,
				itemId : 'delete',
				scope : this,
				handler : function() {
					// var selModel = grid.getSelectionModel();
					var selected = grid.getSelectionModel().getSelection();
					var Ids = []; // 要删除的id
					Ext.each(selected, function(item) {
						Ids.push(item.data.id); // id 对应映射字段
					})
					// alert(Ids);
				}
			} ]
		} ],
		listeners : {
			// 监听单击事件 records 当前行 对象
			itemclick : function(dv, records, item, index, e) {
				// alert(record.data.id);

			},
			selectionchange : function(model, records) {
				if (records[0]) {
					dataPanel.show(); // 显示
					dataPanel.loadRecord(records[0]);
				}
			}
		}

	});

	// 初始加载第1页
	gridStore.loadPage(1);

	var dataPanel = Ext.create('Ext.form.Panel', {
		title : '表单',
		width : 300,
		height : 200,
		frame : true,
		bodyPadding : 5,
		rowspan: 2,
		// closable:true,//是否可关闭
		//hidden : true, // 隐藏
		margin : '5',
		defaultType : 'textfield', // name对应grid列中的dataIndex
		items : [ {
			fieldLabel : '角色代码',
			name : 'roleDm'
		}, {
			fieldLabel : '角色名称',
			name : 'roelName'
		}, {
			fieldLabel : '备注',
			name : 'bz'
		} , {
			fieldLabel : 'id',
			name : 'id',
			value:0,
			hidden:true
		} ],
		buttons : [ {
			text : '更新',
			listeners: {
                  //'mouseover': btnresetmouseover,
			    'click' : function() {

					var id = dataPanel.getForm().findField("id").getValue();
					if(id == null || id == ''){
						Ext.MessageBox.alert('提示', '请选择要修改的记录!');
						return false;
					}
					dataPanel.submit({
						url : ctx+'/jsxx/jsgl.do?update",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
						},
						failure : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Failure', data.msg);
						}
					});
 	             }
              }

		},  {
			text : '新建',
			listeners: {
                  'click': function () {
                	  dataPanel.submit({
						url : ctx+'/jsxx/jsgl.do?insert",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
						},
						failure : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Failure', data.msg);
						}
					});
 	             }
              }

		},{
			text: '重置',
          listeners: {
             'click': function () {
            	 dataPanel.getForm().reset();
            	 dataPanel.getForm().findField("id").setValue(0);
            }

         }

		} ]
	});
	

	var viewport = Ext.create('Ext.Panel', {
		renderTo : Ext.getBody(),
		layout: {
	        type: 'table',
	        columns: 2
	    },
		frame: true,
		// width: 800,
		// height: 700,
		items : [ searchForm, dataPanel , jsxxGrid ]
	});

});
