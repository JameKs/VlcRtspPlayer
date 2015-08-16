Ext.require([ '*' ]);

Ext.onReady(function() {

	var searchForm = Ext.create('Ext.form.Panel', {
		title : '按条件搜索',
		region:"north",
		height : 70,
		defaultType : 'textfield',
		frame : true,
		method : 'POST',
		collapsible : false,// 可折叠
		bodyPadding : 5,
		layout : 'column',
		margin : '0 0 5 0',
		items : [ {
			fieldLabel : '角色代码',
			labelWidth : 80,
			id : 'code'
		}, {
			fieldLabel : '角色名称',
			labelWidth : 80,
			id : 'name'
		}, {
		    xtype: "checkbox",          //checkbox控件
		    id: "st",         //表单中字段名称
		    abelWidth : 80,
		    fieldLabel: "是否有效",       //标签名称
		    inputValue: "1",         //选中的值
		    uncheckedValue: "0",    //未选中的值
		    checked: true
		}, {
			xtype : 'button',
			text : '搜索',
			margin : '0 0 0 5',
			handler : function() {
				var form = searchForm.getForm();
				
				var code = form.findField("code").getValue();
				var name = form.findField("name").getValue();
				var status = form.findField("st").getSubmitValue();
				gridStore.load({
					params : {
						code : code,
						name : name,
						status : status
					}
				});// 传递参数

			}
		} , {
			xtype : 'button',
			text : '重置',
			margin : '0 0 0 5',
			handler : function() {
				var form = searchForm.getForm();
				form.reset();

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
			name : 'code',
			mapping : 'code'
		}, {
			name : 'name',
			mapping : 'name'
		}, {
			name : 'bz',
			mapping : 'bz'
		}, {
			name : 'status',
			mapping : 'status'
		}, {
			name : 'cjr',
			mapping : 'cjr'
		}, {
			name : 'cjSj',
			mapping : 'cjSj'
		} ]
	});

	var gridStore = new Ext.data.JsonStore({
		model : 'Role',
		pageSize : 10,
		proxy : {
			type : 'ajax',
			url : ctx+'/role/role.do?findList&_csrf=' + $("#_csrf").val(),

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
		dataIndex : 'code'
	}, {
		header : "角色名称",
		dataIndex : 'name'
	}, {
		header : "备注",
		dataIndex : 'bz'
	}, {
		header : "有效标志",
		dataIndex : 'status'
	}, {
		header : "创建人",
		dataIndex : 'cjr'
	}, {
		header : "创建时间",
		dataIndex : 'cjSj'
	} ];

	var jsxxGrid = new Ext.grid.GridPanel({
		store : gridStore,
		columns : cm,
		frame : false,
		width : 550,
		region:"west",
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
				text : '新增',
				scope : this, // 添加
				handler : function() {
					Panel.show(); // 显示
				}
			}, {
				iconCls : 'icon-delete',
				text : '删除',
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
//	gridStore.loadPage(1);
	var form = searchForm.getForm();
	
	var code = form.findField("code").getValue();
	var name = form.findField("name").getValue();
	var status = form.findField("st").getSubmitValue();
	gridStore.load({
		params : {
			code : code,
			name : name,
			status : status
		}
	});

	var dataPanel = Ext.create('Ext.form.Panel', {
		title : '表单',
		width : 300,
		region:"center",
		//frame : true,
		bodyPadding : 5,
		margin : '0 0 5 5',
		defaultType : 'textfield',
		tbar :[{  
        	text : "修改",  
            iconCls:'deleteBtn' ,
            handler : function() {
            	var form = dataPanel.getForm();
            	var id = form.findField("id").getValue();
				if(id == null || id == ''){
					Ext.MessageBox.alert('提示', '请选择要修改的记录!');
					return false;
				}
				form.submit({
					url : ctx+'/role/role.do?update&_csrf=' + $("#_csrf").val(),
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
		},{  
        	text : "新增",  
            iconCls:'deleteBtn' ,
            handler : function() {
            	var form = dataPanel.getForm();
            	form.submit({
					url : ctx+'/role/role.do?insert&_csrf=' + $("#_csrf").val(),
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
		},{  
        	text : "重置",  
            iconCls:'deleteBtn' ,
            handler : function() {
            	var form = dataPanel.getForm();
            	form.reset();
            	form.findField("id").setValue(0);
            }
		}],
		items : [ {
			fieldLabel : '角色代码',
			name : 'code'
		}, {
			fieldLabel : '角色名称',
			name : 'name'
		}, {
			fieldLabel : '备注',
			name : 'bz'
		}, {
		    xtype: "checkbox",          //checkbox控件
		    name: "status",         //表单中字段名称
		    abelWidth : 80,
		    fieldLabel: "是否有效",       //标签名称
		    inputValue: "1",         //选中的值
		    uncheckedValue: "0",    //未选中的值
		    checked: true
		} , {
			fieldLabel : '创建人',
			name : 'cjr'
		} , {
			fieldLabel : '创建时间',
			name : 'cjSj'
		} , {
			fieldLabel : 'id',
			name : 'id',
			value:0,
			hidden:true
		} ]
	});
	

	var viewport = Ext.create('Ext.Panel', {
		renderTo : 'panel_div',
		layout:"border", 
		//frame: true,
		height: 750,
		items : [ searchForm, jsxxGrid, dataPanel]
	});

});
