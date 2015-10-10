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
				form.findField("sortNo").setValue(treeNode.exts.sortNo);
				form.findField("url").setValue(treeNode.exts.url);
				form.findField("id").setValue(treeNode.id);
				Ext.getCmp("pId").setValue(treeNode.pId);
				leafCheckbox.setValue(treeNode.exts.leaf);
				
			}
		}
	};
	$.fn.zTree.init($("#cdtree"), setting, menus);
	
	var leafCheckbox=new Ext.form.Checkbox({
	    xtype: "checkbox",          //checkbox控件
	    id: "leaf",         //表单中字段名称
	    name: "leaf",         //表单中字段名称
	    fieldLabel: "叶子节点",       //标签名称
	    inputValue: "1",         //选中的值
	    uncheckedValue: "0",    //未选中的值
	    checked: false//,              //绘制时的选中状态
	   // width: 180                  //宽度
	})
	
	var mySimpleStore = new Ext.data.ArrayStore({
		data : pMenus,
		fields : [{
			name : 'name',
			mapping : 'name'
			},
			{
			name : 'id',
			mapping : 'id'
			}]
		});

//		var remoteJsonStore = new Ext.data.JsonStore({
//			root : '',
//			fields : [ {
//				name : 'name',
//				mapping : 'name'
//			}, {
//				name : 'id',
//				mapping : 'id'
//			} ],
//			proxy : new Ext.data.ScriptTagProxy({
//				url : ctx+"/menu/menu.do?reloadPTree&_csrf=" + $("#_csrf").val()
//			})
//		});
	
	var combo = {
		xtype : 'combo',
		id : 'pId',
		name : 'pId',
		fieldLabel : '父菜单',
		store : mySimpleStore,
		displayField : 'name',
		valueField : 'id',
		editable : false,// 是否允许输入
		typeAhead : true,
		emptyText: '请选择',// 该项如果没有选择，则提示错误信息,
		forceSelection : true
	};
		
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
			fieldLabel : '菜单顺序',
			name : 'sortNo',
			cls : 'field-margin',
			flex : 1
		},leafCheckbox, combo, {
			xtype : 'textfield',
			name : 'id',
			id : 'id',
			hidden : true,
			hideLabel : true
		}],
		dockedItems : [ {
			xtype : 'toolbar',
			dock : 'top',
			items : [ {
				text : '新增',
				tooltip : 'New',
				iconCls : 'icon-add',
				handler : function() {
					var form = formPanel.getForm();
					var id = form.findField("id").getValue();
					if(id != null && id != ''){
						alert("请先点击清空按钮再保存");
						return false;
					}
					
					form.submit({
						url : ctx+"/menu/menu.do?new&_csrf=" + $("#_csrf").val(),
						waitTitle:"请稍候",
						waitMsg:"正在提交表单数据，请稍候。。。。。。",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
							form.reset();
							
							//重新加载下拉框
							var aj = $.ajax( {    
							    url:ctx+"/menu/menu.do?reloadPTree&_csrf=" + $("#_csrf").val(),   
							    type:'post',    
							    cache:false,    
							    dataType:'json',    
							    success:function(data) { 
							    	menus = data;
							        $.fn.zTree.init($("#cdtree"), setting, menus);
							     },    
							     error : function() {    
							          alert("异常！");    
							     }    
							});

							
						},
						failure : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Failure', data.msg);
						}
					});
				}
			}, {
				text : '修改',
				tooltip : 'Update',
				iconCls : 'icon-add',
				handler : function() {
					var form = formPanel.getForm();
					var id = form.findField("id").getValue();
					if(id == null || id == ''){
						alert("请选择记录");
						return false;
					}
					
					form.submit({
						url : ctx+"/menu/menu.do?update&_csrf=" + $("#_csrf").val(),
						waitTitle:"请稍候",
						waitMsg:"正在提交表单数据，请稍候。。。。。。",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
							form.reset();
							//重新加载树
							var aj = $.ajax( {    
							    url:ctx+"/menu/menu.do?reloadTree&_csrf=" + $("#_csrf").val(),   
							    type:'post',    
							    cache:false,    
							    dataType:'json',    
							    success:function(data) { 
							    	menus = data;
							        $.fn.zTree.init($("#cdtree"), setting, menus);
							     },    
							     error : function() {    
							          alert("异常！");    
							     }    
							}); 
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
					var form = formPanel.getForm();
					var id = form.findField("id").getValue();
					if(id == null || id == ''){
						alert("请选择记录");
						return false;
					}
					
					form.submit({
						url : ctx+"/menu/menu.do?delete&_csrf=" + $("#_csrf").val(),
						waitTitle:"请稍候",
						waitMsg:"正在提交表单数据，请稍候。。。。。。",
						success : function(form, action) {
							var data = Ext.JSON.decode(action.response.responseText);
							Ext.Msg.alert('Success', data.msg);
							form.reset();
							//重新加载树
							var aj = $.ajax( {    
							    url:ctx+"/menu/menu.do?reloadTree&_csrf=" + $("#_csrf").val(),   
							    type:'post',    
							    cache:false,    
							    dataType:'json',    
							    success:function(data) { 
							    	menus = data;
							        $.fn.zTree.init($("#cdtree"), setting, menus);
							     },    
							     error : function() {    
							          alert("异常！");    
							     }    
							}); 
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
