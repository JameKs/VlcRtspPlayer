Ext.require(['*']);
Ext.onReady(function() {
	
	//定义一个用于查询的Form
	var searchFormPanel = Ext.create('Ext.form.Panel', {
        defaultType: 'textfield',
        layout : 'column',
        renderTo:'yhxx_search',
        height: 30,
        items: [{
        		fieldLabel: '用户代码',
        		name: 'loginId',
        		fieldWidth: 60,
                allowBlank: false
    		}, {
        		fieldLabel: '用户名称',
        		name: 'userName',
        		fieldWidth: 60
        }, {
			xtype : 'button',
			text : '搜索',
			margin : '0 0 0 5',
			handler : function() {
				var form = searchFormPanel.getForm();
				
				var loginId = form.findField("loginId").getValue();
				var userName = form.findField("userName").getValue();

				gridStore.load({
					params : {
						loginId : loginId,
						userName : userName
					}
				});// 传递参数

			}
		}]
	});
	
	/*定义用于显示数据的Grid*/
	
	//这里就是设置解析格式的地方，一定要和你的Model一样，要不然可是什么都得不到哦~~~~
	Ext.define('User', {
	    extend: 'Ext.data.Model',
	    fields : [ 
  			{ name : 'id', mapping : 'id' },
  			{ name : 'loginId', mapping : 'loginId' },
  			{ name : 'userName', mapping : 'userName' },
  			{ name : 'password', mapping : 'password' },
  			{ name : 'phone', mapping : 'phone' },
  			{ name : 'email', mapping : 'email' },
  			{ name : 'status', mapping : 'status' },
  			{ name : 'active', mapping : 'active' },
  			{ name : 'sessionOutTime', mapping : 'sessionOutTime' },
  			{ name : 'cjr', mapping : 'cjr' },
  			{ name : 'cjSj', mapping : 'cjSj' },
  		]
	});
	
	var gridStore = Ext.create('Ext.data.Store', {
		model: 'User',
		pageSize: 2,
		proxy : {
			type: 'ajax',
            url: ctx + '/user/user.do?findList&_csrf=' + $("#_csrf").val(),  
            reader: {
                type: 'json',
                root: 'items',
                idProperty: 'id',
                totalProperty: 'totalCount'
            }
		},
		listeners:{
			beforeload:function (store, options) {
				var form = searchFormPanel.getForm();
				var loginId = form.findField("loginId").getValue();
				var userName = form.findField("userName").getValue();
				var	params = {
						loginId : loginId,
						userName : userName
					}
		        Ext.apply(store.proxy.extraParams, params);
		    }
		}
	});
	
	var cm = [ {
		xtype : "rownumberer",
		text : "#",
		width : 30
	}, {
		header : "id",
		dataIndex : 'id'
	}, {
		header : "用户代码",
		dataIndex : 'loginId'
	}, {
		header : "用户名称",
		dataIndex : 'userName'
	}, {
		header : "员工密码",
		dataIndex : 'password'
	}, {
		header : "联系电话",
		dataIndex : 'phone'
	}, {
		header : "邮箱地址",
		dataIndex : 'email'
	}, {
		header : "登录状态",
		dataIndex : 'status'
	}, {
		header : "是否有效",
		dataIndex : 'active'
	}, {
		header : "超时时间",
		dataIndex : 'sessionOutTime'
	}, {
		header : "创建人",
		dataIndex : 'cjr'
	}, {
		header : "创建时间",
		dataIndex : 'cjSj'
	}];
		
	var userGrid = new Ext.grid.GridPanel({
		columns : cm,
		frame : false,
		//height: 350,
		store: gridStore,
		renderTo: 'yhxx_grid',
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
					var editFormPanel = Ext.create('Ext.form.Panel', {
				        defaultType: 'textfield',
				        layout : 'column',
				        items: [{
				    		fieldLabel: '登录ID',
				    		name: 'loginId',
				    		fieldWidth: 60,
				            allowBlank: false
						},{
				    		fieldLabel: '用户名称',
				    		name: 'userName',
				    		fieldWidth: 60,
				            allowBlank: false
						}, {
				    		fieldLabel: '电话号码',
				    		name: 'phone',
				    		fieldWidth: 60,
				    		regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|13\d{9})$/  
						}, {
				    		fieldLabel: '邮箱地址',
				    		name: 'email',
				    		vtype:'email', 
				    		fieldWidth: 60
						},{
						    xtype: "checkbox",          //checkbox控件
						    name: "status",         //表单中字段名称
						    fieldLabel: "是否有效",       //标签名称
						    inputValue: "1",         //选中的值
						    uncheckedValue: "0",    //未选中的值
						    checked: true
						}, {
							xtype: 'numberfield',
							fieldLabel: '超时时间',
				    		name: 'sessionOutTime',
				    		value: 30,
				    		fieldWidth: 60
						}, {
							fieldLabel: 'id',
				    		name: 'id'
						}]
					});
					
					var addWindow = new Ext.Window({    
					    title : '增加用户',  
					    width : 600,
					    height : 250,
					    resizable : false,  
					    closeAction:'hide',   
					    autoHeight : true,   
					    constrainHeader : true,  
					    modal: true, //是否模态窗口，默认为false
					    plain : true,  
					    items : [editFormPanel],
					    buttons : [{  
			                text : '确定',  
			                handler : function(){  
			                	editFormPanel.getForm().submit({
			                		url:  ctx + '/user/user.do?insert&_csrf=' + $("#_csrf").val(),   
				                	method:"POST",
				                	waitMsg:"保存中，请稍后...",
			                        success : function(form, action) {  
			                            // 得到数据  
			                            var result = Ext.JSON  
			                                    .decode(action.response.responseText);// 就可以取出来。如果是数组，那么很简单  
			                            // 把数据放到结果里面  
			                            Ext.Msg.alert('提示',result.msg);  
			                            gridStore.loadPage(1);
			                        },  
			                        failure : function(form, action) {  
			                            Ext.Msg.alert('提示', "操作失败：输入非法字符！！！");  
			                        }  
			                	});
			                	addWindow.close();  
			                }  
			            },{  
			                text : '取消', 
		                	handler : function(){  
		                		addWindow.close();  
			                }
			            }] 
					}); 
					addWindow.show();  
				}
			}, {
				iconCls : 'icon-delete',
				text : '删除',
				// disabled: true,
				itemId : 'delete',
				scope : this,
				handler : function() {
					// var selModel = grid.getSelectionModel();
					var selected = userGrid.getSelectionModel().getSelection();
					var ids = "" ; // 要删除的id
					Ext.each(selected, function(item) {
						ids = ids + ',' + item.data.id; // id 对应映射字段
					});
					$.ajax({
						url : ctx + '/user/user.do?deleteByIds&_csrf=' + $("#_csrf").val(),  
						data : {
							"ids" : ids.substring(1, ids.length)
						},
						type : 'post',
						cache : false,
						dataType : 'json',
						success : function(data) {
							Ext.Msg.alert('提示',data.msg);  
							gridStore.loadPage(1);
						},
						error : function() {
							alert("异常！");
						}
					});  

				}
			}, {
				iconCls : 'icon-update',
				text : '修改',
				// disabled: true,
				itemId : 'update',
				scope : this,
				handler : function() {
					// var selModel = grid.getSelectionModel();
					var record = userGrid.getSelectionModel().getSelection();
					
					var editFormPanel = Ext.create('Ext.form.Panel', {
				        defaultType: 'textfield',
				        layout : 'column',
				        url:  ctx + '/user/user.do?insert&_csrf=' + $("#_csrf").val(),  
				        items: [{
				    		fieldLabel: '登录ID',
				    		name: 'loginId',
				    		fieldWidth: 60,
				            allowBlank: false
						},{
				    		fieldLabel: '用户名称',
				    		name: 'userName',
				    		fieldWidth: 60,
				            allowBlank: false
						}, {
				    		fieldLabel: '电话号码',
				    		name: 'phone',
				    		fieldWidth: 60,
				    		regex: /^((\d{3,4}-)*\d{7,8}(-\d{3,4})*|13\d{9})$/  
						}, {
				    		fieldLabel: '邮箱地址',
				    		name: 'email',
				    		vtype:'email', 
				    		fieldWidth: 60
						},{
						    xtype: "checkbox",          //checkbox控件
						    name: "status",         //表单中字段名称
						    fieldLabel: "是否有效",       //标签名称
						    inputValue: "1",         //选中的值
						    uncheckedValue: "0",    //未选中的值
						    checked: true
						}, {
							xtype: 'numberfield',
							fieldLabel: '超时时间',
				    		name: 'sessionOutTime',
				    		value: 30,
				    		fieldWidth: 60
						}, {
							fieldLabel: 'id',
				    		name: 'id'
						}]
					});
					
					var addWindow = new Ext.Window({    
					    title : '增加用户',  
					    width : 600,
					    height : 300,
					    resizable : false,  
					    closeAction:'hide',   
					    autoHeight : true,   
					    constrainHeader : true,  
					    modal: true, //是否模态窗口，默认为false
					    plain : true,  
					    items : [editFormPanel],
					    buttons : [{  
			                text : '确定',  
			                handler : function(){  
//			                	if(saveFormPanel.getForm().isValid()) {  
			                		editFormPanel.getForm().submit({  
				                        url : ctx + '/user/user.do?update&_csrf=' + $("#_csrf").val(),  
				                        waitMsg : '正在提交数据',  
				                        waitTitle : '提示',  
				                        method : "POST",  
				                        success : function(form, action) {  
				                            var result = Ext.JSON.decode(action.response.responseText);// 就可以取出来。如果是数组，那么很简单  
				                            Ext.Msg.alert('提示',result.msg);  
				                            gridStore.loadPage(1);
				                        },  
				                        failure : function(form, action) {  
				                            Ext.Msg.alert('提示', "操作失败：输入非法字符！！！");  
				                        }  
				                    }); 
//			                	};
			                	addWindow.close(); 
			                }  
			            },{  
			                text : '取消', 
		                	handler : function(){  
		                		addWindow.close();  
			                }
			            }] 
					}); 
					addWindow.show();  
					//editFormPanel.getForm().loadRecord(record);
					var form = editFormPanel.getForm();
					form.findField("id").setValue(record[0].data["id"]);
					form.findField("loginId").setValue(record[0].data["loginId"]);
					form.findField("userName").setValue(record[0].data["userName"]);
					form.findField("phone").setValue(record[0].data["phone"]);
					form.findField("email").setValue(record[0].data["email"]);
					form.findField("status").setValue(record[0].data["status"]);
					form.findField("sessionOutTime").setValue(record[0].data["sessionOutTime"]);
					//form.findField("id").setValue(record.data["id"]);
				}
			} ]
		} ]
	});
	gridStore.loadPage(1);
});
