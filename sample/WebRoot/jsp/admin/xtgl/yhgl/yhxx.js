Ext.require(['*']);
Ext.onReady(function() {
	
	//定义一个用于查询的Form
	var form = Ext.create('Ext.form.Panel', {
        defaultType: 'textfield',
        layout : 'column',
        renderTo:'yhxx_search',
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
				var loginId = Ext.getCmp('loginId').getValue();
				var userName = Ext.getCmp('userName').getValue(); // 获取文本框值

				ryxxGrid.load({
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
  			{ name : 'bmDm', mapping : 'bmDm' }
  		]
	});
	
	var gridStore = Ext.create('Ext.data.Store', {
		model: 'User',
		pageSize: 2,
		proxy : {
			type: 'ajax',
            url: ctx + '/yhgl/yhgl.do?findList',
            reader: {
                type: 'json',
                root: 'items',
                idProperty: 'id',
                totalProperty: 'totalCount'
            }
		}
	});

	var cm = [ {
		xtype : "rownumberer",
		text : "行号",
		width : 40
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
	}, {
		header : "部门代码",
		dataIndex : 'bmDm'
	}];
		
	var ryxxGrid = new Ext.grid.GridPanel({
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
					});
				}
			} ]
		} ]
	});
	gridStore.loadPage(1);
});
