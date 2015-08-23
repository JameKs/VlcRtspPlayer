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
		margin : '5 5 5 5',
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
			text : '查询',
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
		pageSize : 2,
		proxy : {
			type : 'ajax',
			url : ctx+'/role/role.do?findList&_csrf=' + $("#_csrf").val(),

			reader : {
				type : 'json',
				root : 'items',
				idProperty : 'id',
				totalProperty : 'totalCount'
			}
		},
		listeners:{
			beforeload: function (store, options) {
				var form = searchForm.getForm();
				
				var code = form.findField("code").getValue();
				var name = form.findField("name").getValue();
				var status = form.findField("st").getSubmitValue();
				var	params = {
						code : code,
						name : name,
						status : status
					}
		        Ext.apply(store.proxy.extraParams, params);
		    }
		}
	});
	
	var cm = [ {
		header : "id",
		dataIndex : 'id',
		hidden:true
	}, {
		header : "角色代码",
		width : 60,
		dataIndex : 'code'
	}, {
		header : "角色名称",
		width : 60,
		dataIndex : 'name'
	}, {
		header : "备注",
		dataIndex : 'bz'
	}, {
		header : "有效标志",
		width : 60,
		renderer :function(value, cellmeta, record, rowIndex, columnIndex, store){
//			1.value是当前单元格的值
//			2.cellmeta里保存的是cellId单元格id，id不知道是干啥的，似乎是列号，css是这个单元格的css样式。
//			3.record是这行的所有数据，你想要什么，record.data["id"]这样就获得了。
//			4.rowIndex是行号，不是从头往下数的意思，而是计算了分页以后的结果。
//			5.columnIndex列号太简单了。
//			6.store，这个厉害，实际上这个是你构造表格时候传递的ds，也就是说表格里所有的数据，你都可以随便调用，唉，太厉害了。
			if(value == '1'){
				return "Y";
			}else{
				return "N";
			}
		},
		dataIndex : 'status'
	}, {
		header : "创建人",
		width : 60,
		dataIndex : 'cjr'
	}, {
		header : "创建时间",
		//xtype:'datecolumn',
		//dateFormat:'Y-m-d H:i:s',
		renderer : Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		width : 120,
		dataIndex : 'cjSj'
	} ];

	var jsxxGrid = new Ext.grid.GridPanel({
		store : gridStore,
		columns : cm,
		frame : false,
		width : 580,
		//height:300,
		region:"west",
		margin : '0 0 5 5',
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
					//dataPanel.show(); // 显示
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
		//title : '表单',
		width : 300,
		region:"center",
		//frame : true,
		bodyPadding : 5,
		margin : '0 5 5 5',
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
			name : 'cjSj',
			id : 'cjSj',
			listeners:{  

	            render : function(p) {
					p.getEl().on('click', function() {

						WdatePicker({
							el : 'cjSj-inputEl',
							dateFmt : 'yyyy-MM-dd'
						});

					});
				}
			}  

		} , {
			fieldLabel : 'id',
			name : 'id',
			value:0,
			hidden:true
		}  , {
			xtype     : 'textarea',
			name : 'sm',
			readOnly : true,
			value:'说明：\n1.添加记录：先点重置，填写信息后再点新增即可添加记录。\n2.修改记录：选择一条记录，修改内容，点击修改按钮。',
			
			anchor    : '100%' 
		} ]
	});
	

	var viewport = Ext.create('Ext.Panel', {
		renderTo : 'panel_div',
		layout:"border", 
		//frame: true,
		height: 400,
		items : [ searchForm, jsxxGrid, dataPanel]
	});

});
