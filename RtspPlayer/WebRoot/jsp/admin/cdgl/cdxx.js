Ext.require([ 'Ext.tree.*', 'Ext.data.*', 'Ext.window.MessageBox' ]);

Ext.onReady(function() {

	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			onClick:function(event, treeId, treeNode){
				var form = formPanel.getForm();
				form.findField("cnName").setValue(treeNode.exts.cnName);
				form.findField("enName").setValue(treeNode.exts.enName);
				form.findField("cdDm").setValue(treeNode.exts.cdDm);
				form.findField("ccsd").setValue(treeNode.exts.ccsd);
				form.findField("ccsx").setValue(treeNode.exts.ccsx);
				form.findField("url").setValue(treeNode.exts.url);
				form.findField("id").setValue(treeNode.id);
				form.findField("pId").setValue(treeNode.pId);
				Ext.getCmp('leaf').setValue(treeNode.exts.leaf == '1');
			}
		}
	};
	$.fn.zTree.init($("#cdtree"), setting, nodes);
	
	var formPanel = Ext.create('Ext.form.Panel', {
		id:'formPanel',
		width: 400,
		height : 450,
		style : 'margin-left: 2px;',
		bodyStyle : 'padding: 20px;',
		renderTo : 'form_div',
		items : [ {
			xtype : 'textfield',
			fieldLabel : '菜单中文名称',
			name : 'cnName',
			cls : 'field-margin',
			flex : 1
		}, {
			xtype : 'textfield',
			fieldLabel : '菜单英文名称',
			name : 'enName',
			cls : 'field-margin',
			flex : 1
		}, {
			xtype : 'textfield',
			fieldLabel : '菜单编码',
			name : 'cdDm',
			cls : 'field-margin',
			flex : 1
		}, {
			xtype : 'textfield',
			fieldLabel : '菜单路径',
			name : 'url',
			cls : 'field-margin',
			flex : 1
		}, {
			xtype : 'numberfield',
			fieldLabel : '菜单层级',
			name : 'ccsd',
			cls : 'field-margin',
			flex : 1
		}, {
			xtype : 'numberfield',
			fieldLabel : '层级顺序',
			name : 'ccsx',
			cls : 'field-margin',
			flex : 1
		}, {
			xtype : 'checkboxgroup',
			fieldLabel : '是否为叶子节点',
			columns : 1,
			cls : 'field-margin',
			vertical : false,
			items : [ {
				name : 'leaf',
				id : 'leaf',
				inputValue : '1'
			} ]
		}, {
			xtype : 'textfield',
			name : 'id',
			id : 'id',
			hidden : true,
			hideLabel : true
		}, {
			xtype : 'textfield',
			name : 'pId',
			id : 'pId',
			hidden : true,
			hideLabel : true
		} ],
		dockedItems : [ {
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				text : '新增',
				tooltip : 'New',
				iconCls : 'icon-add',
				handler : function() {
					Ext.getCmp('formPanel').getForm().submit({
						url : ctx+"/cdxx/menu.do?new&_csrf=" + $("#_csrf").val(),
						waitTitle:"请稍候",
						waitMsg:"正在提交表单数据，请稍候。。。。。。",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
							Ext.getCmp('formPanel').getForm().reset();
							store.load();
						},
						failure : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Failure', data.msg);
						}
					});
				}
			}, {
				text : '修改',
				tooltip : 'Save',
				iconCls : 'icon-add',
				handler : function() {
					Ext.getCmp('formPanel').getForm().submit({
						url : ctx+"/cdxx/menu.do?update&_csrf=" + $("#_csrf").val(),
						waitTitle:"请稍候",
						waitMsg:"正在提交表单数据，请稍候。。。。。。",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
							Ext.getCmp('formPanel').getForm().reset();
							store.load();
						},
						failure : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Failure', data.msg);
						}
					});
				}
			}, {
				text : '删除',
				tooltip : 'Delete',
				iconCls : 'icon-delete',
				handler : function() {
					Ext.getCmp('formPanel').getForm().submit({
						url : ctx+"/cdxx/menu.do?delete&_csrf=" + $("#_csrf").val(),
						waitTitle:"请稍候",
						waitMsg:"正在提交表单数据，请稍候。。。。。。",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
							Ext.getCmp('formPanel').getForm().reset();
							store.load();
						},
						failure : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Failure', data.msg);
						}
					});
				}
			}, {
				text : '清空',
				tooltip : 'Clear',
				handler : function() {
					Ext.getCmp('formPanel').getForm().reset();
				}
			} ]
		} ]
	});


});
