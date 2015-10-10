Ext.require([ '*' ]);

Ext.onReady(function() {

	var formUpload = Ext.create('Ext.form.Panel', {
		id:'formUpload',
	    labelWidth: 80,   
	    fileUpload:true,   
	    defaultType: 'textfield',   
	    items: [{   
	      xtype: 'textfield',   
	      fieldLabel: '文件选择',   
	      name: 'file',   
	      inputType: 'file',   
	      allowBlank: false,   
	      blankText: '请上传文件',   
	      anchor: '90%'  // anchor width by percentage   
	    }]   
	  
	  }); 
	/* 定义用于显示数据的Grid */

	// 这里就是设置解析格式的地方，一定要和你的Model一样，要不然可是什么都得不到哦~~~~
	Ext.define('Depl', {
		extend : 'Ext.data.Model',
		fields : [ {
			name : 'id',
			mapping : 'id'
		}, {
			name : 'key',
			mapping : 'key'
		}, {
			name : 'name',
			mapping : 'name'
		}, {
			name : 'version',
			mapping : 'version'
		} ]
	});

	var gridStore = new Ext.data.JsonStore({
		model : 'Depl',
		pageSize : 1,
		proxy : {
			type : 'ajax',
			url : ctx + '/lcxx/lcgl.do?lcbslb',
			reader : {
				type : 'json',
				root : 'items',
				//idProperty : 'id',
				totalProperty : 'totalCount'
			}
		}
	});

	var cm = [ { xtype:"rownumberer", 
		text: "序号", 
		width:40 
	},{
		header : "部署编号",
		dataIndex : 'id'
	}, {
		header : "部署key",
		dataIndex : 'key'
	}, {
		header : "部署名称",
		dataIndex : 'name'
	}, {
		header : "部署版本",
		dataIndex : 'version'
	}, {  
        text : "操作",  
        xtype : 'actioncolumn',  
        items : [{  
            test : "查看流程图",  
            //icon : ctx + '/resources/images/icons/common/edit.png',  
            tooltip : '查看流程图',  
            renderer : function(value) {  
                //return Ext.String.format('<button>{1}<span>{0}<span></button>', '编辑', value);
            	return '<button><span>编辑<span></button>'; 
            },
            handler:function(grid,rindex,cindex){
            	var record = grid.getStore().getAt(rindex);
                var id = record.get("ID");
                alert(id);
           }
        }, {  
            test : "删除流程",  
            //icon : ctx + '/resources/images/icons/common/delete.png', 
            renderer : function(value) {  
                //return Ext.String.format('<button>{1}<span>{0}<span></button>', '编辑', value);
            	return '<button><span>删除流程<span></button>'; 
            },
            handler:function(grid,rindex,cindex){
                var record = grid.getStore().getAt(rindex);
                var id = record.get("id");
                alert(id);
           }
        }]  
    } ];

	var jsxxGrid = new Ext.grid.GridPanel({
		store : gridStore,
		columns : cm,
		frame : true,
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
		}, {
			xtype : 'toolbar',
			items : [ {
				iconCls : 'icon-add',
				text : '部署流程',
				scope : this, // 添加
				handler : function() {
					// 弹出一个窗口，可以选择要部署的流程
			        var window = new Ext.Window({  
			            //layout : 'fit',//设置window里面的布局  
			            width:400,  
			            height:200,  
			            //关闭时执行隐藏命令,如果是close就不能再show出来了  
			            closeAction:'hide',  
			            //draggable : false, //不允许窗口拖放  
			            //maximizable : true,//最大化  
			            //minimizable : true,//最小话  
			            //constrain : true,//防止窗口超出浏览器  
			            //constrainHeader : true,//只保证窗口顶部不超出浏览器  
			            //resizble : true,//是否可以改变大小  
			            //resizHandles : true,//设置窗口拖放的方式  
			            modal : true,//屏蔽其他组件,自动生成一个半透明的div  
			            //animateTarget : 'target',//弹出和缩回的效果  
			            plain : true,//对窗口进行美化,可以看到整体的边框  
			              
			            buttonAlign : 'center',//按钮的对齐方式  
			            defaultButton : 0,//默认选择哪个按钮  
			            items : [formUpload],
			            buttons : [{  
			                text : '上传',  
			                handler : function(){
			                	Ext.getCmp('formUpload').getForm().submit({
									url : ctx +"/lcxx/lcgl.do?fileUpload",
									waitTitle:"请稍候",
									waitMsg:"正在提交表单数据，请稍候。。。。。。",
									success : function(form, action) {
										var data = Ext.JSON.decode(action.response.responseText);
										this.up('window').close();
										Ext.Msg.alert('Success', data.msg);
									},
									failure : function(form, action) {
										var data = Ext.JSON.decode(action.response.responseText);
										Ext.Msg.alert('Failure', data.msg);
									}
								});
			                }  
			            },{  
			                text : '取消',
			                handler : function(){
			                	this.up('window').close();
							}
			            }]
			            
			        });  
			        window.show(); 
				}
			}, {
				iconCls : 'icon-delete',
				text : '删除流程',
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
		renderTo : Ext.getBody()

	});

	// 初始加载第1页
	gridStore.loadPage(1);


});
