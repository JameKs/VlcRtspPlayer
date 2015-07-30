Ext.require([ '*' ]);

//这里就是设置解析格式的地方，一定要和你的Model一样，要不然可是什么都得不到哦~~~~
Ext.define('Qjxx', {
	extend : 'Ext.data.Model',
	fields : [ {
		name : 'id',
		mapping : 'id'
	}, {
		name : 'rwlxDm',
		mapping : 'rwlxDm'
	}, {
		name : 'taskId',
		mapping : 'taskId'
	}, {
		name : 'name',
		mapping : 'name'
	}, {
		name : 'kssj',
		mapping : 'kssj'
	}, {
		name : 'jssj',
		mapping : 'jssj'
	} , {
		name : 'qjyy',
		mapping : 'qjyy'
	}, {
		name : 'userId',
		mapping : 'userId'
	} , {
		name : 'owner',
		mapping : 'owner'
	}, {
		name : 'assignee',
		mapping : 'assignee'
	}, {
		name : 'priority',
		mapping : 'priority'
	}, {
		name : 'createTime',
		mapping : 'createTime'
	}, {
		name : 'dueDate',
		mapping : 'dueDate'
	}  ]
});

var gridStore = Ext.create('Ext.data.Store', {
	model : 'Qjxx',
	pageSize : 10,
	proxy : {
		type : 'ajax',
		url : ctx+'/qjgl/njgl.do?findList',

		reader : {
			type : 'json',
			root : 'items',
			idProperty : 'id',
			totalProperty : 'totalCount'
		}
	}
});

Ext.onReady(function() {
	
	var data= [['年假','00010001'],['公干','00010002']];
    var simpleStore = new Ext.data.SimpleStore({
        fields :['text','value'],
        data : data
    });
    
	var searchForm = Ext.create('Ext.form.Panel', {
		//title : '按条件搜索',
		width : '90%',
		defaultType : 'textfield',
		frame : false,
		method : 'POST',
		//collapsible : true,// 可折叠
		bodyPadding : 5,
		layout : 'column',
		//margin : '0 0 10 0',
		items : [ {
			fieldLabel : '请假类型',
			xtype: 'combo',
			id : 'rwlxDm',
			name : 'rwlxDm',
			anchor : '60%',     // 布局
			store : simpleStore,
			displayField : 'text',  // 显示字段
            valueField : 'value',   // 值，可选
            mode: 'local',//因为data已经取数据到本地了，所以’local’,默认为”remote”，枚举完
            emptyText:'请选择...',
            allowBlank : false,// 不允许为空
            blankText : '请选择'// 该项如果没有选择，则提示错误信息,
		}, {
			xtype : 'button',
			text : '搜索',
			margin : '0 0 0 5',
			handler : function() {
				var rwlxDm = Ext.getCmp('rwlxDm').getValue();

				gridStore.load({
					params : {
						rwlxDm : rwlxDm
					}
				});// 传递参数

			}
		} ],
		renderTo : 'searchNjlb'
	});

	/* 定义用于显示数据的Grid */
	var cm = [ { 
		xtype: "rownumberer", 
		text: "行号", 
		width:40
	},{
		header : "id",
		dataIndex : 'id',
		hidden : true
	}, {
		header : "类型",
		dataIndex : 'rwlxDm',
		renderer:function(value, cellmeta, record, rowIndex, columnIndex, store){
			//			1.value是当前单元格的值
			//			2.cellmeta里保存的是cellId单元格id，id不知道是干啥的，似乎是列号，css是这个单元格的css样式。
			//			3.record是这行的所有数据，你想要什么，record.data["id"]这样就获得了。
			//			4.rowIndex是行号，不是从头往下数的意思，而是计算了分页以后的结果。
			//			5.columnIndex列号太简单了。
			//			6.store，这个厉害，实际上这个是你构造表格时候传递的ds，也就是说表格里所有的数据，你都可以随便调用，唉，太厉害了
			switch(value){
				case '00010001':
					return '年假';
					break;
				case '00010002':
					return '公干';
					break;
				default:
					return value;
			}
		}

	}, {
		header : "任务ID",
		dataIndex : 'taskId'
	}, {
		header : "任务名称",
		dataIndex : 'name'
	}, {
		header : "请假开始时间",
		dataIndex : 'kssj',
		renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
		header : "请假结束时间",
		dataIndex : 'jssj',
		renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
		header : "原因",
		dataIndex : 'qjyy'
	}, {
		header : "申请人",
		dataIndex : 'userId'
	} , {
		header : '拥有者',
		dataIndex : 'owner'
	}, {
		header : '签收人',
		dataIndex : 'assignee'
	}, {
		header : '优先级',
		dataIndex : 'priority'
	}, {
		header : '任务开始时间',
		dataIndex : 'createTime',
		renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
		header : '到期日',
		dataIndex : 'dueDate',
		renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s')
	}, {
		header : '操作',
		dataIndex : 'assignee',
		locked : true,//锁定该列
		renderer: function(value, cellmeta, record, rowIndex, columnIndex, store){
		    var id=record.get("id");
		    var rwlxDm=record.get("rwlxDm");
		    var taskId=record.get("taskId");
		    var name=record.get("name");

			var rtStr = "";
			if(value == '' || value == null){
				rtStr = "<a onclick='claimTask(this)' href='#' taskId='"+taskId+"'>签收</a>";
				
			}else{
				rtStr = "<a onclick='njmx(this)' href='#' rwlxDm='"+rwlxDm+"' id='"+id+"' name='"+name+"'>办理</a>   "+
				"<a onclick='rejectTask(this)' href='#' taskId='"+taskId+"'>驳回</a>   " + 
				"<a onclick='unClaimTask(this)' href='#' taskId='"+taskId+"'>反签收</a>";
			}
			return rtStr;
		}
	}];
	
	var pagingToolbar = new Ext.PagingToolbar({  
		pageSize:10,
		store : gridStore, // same store GridPanel is using
		dock : 'bottom', // 分页 位置
		emptyMsg : '没有数据',
		displayInfo : true,
		displayMsg : '当前显示{0}-{1}条记录 / 共{2}条记录 ',
		beforePageText : '第',
		afterPageText : '页/共{0}页',
		plugins:[new Ext.ui.plugins.ComboPageSize({index:10})]
	});  
	
	
	var jsxxGrid = new Ext.grid.GridPanel({
		store : gridStore,
		columns : cm,
		frame : false,
		width : '95%',
		height:380,
		bbar:pagingToolbar,
		listeners : {
			// 监听单击事件 records 当前行 对象
			itemclick : function(dv, records, item, index, e) {
				// alert(record.data.id);

			},
			selectionchange : function(model, records) {
//				if (records[0]) {
//					dataPanel.show(); // 显示
//					dataPanel.loadRecord(records[0]);
//				}
			}
		},
		renderTo : 'njlb'

	});

	// 初始加载第1页
	gridStore.loadPage(1);


});

var childPageAddTab = function (id, title, url) {
	var tabs = window.parent.tabPanel.getComponent(id);// 加载叶子节点对应的资源
	if (!tabs) {
		window.parent.tabPanel.add({
			id : id,
			title : title,
			height:$('#mainFrame',window.parent.parent.document).height(),
			html : '<iframe id="head" frameborder="0" style="height:100%; width:100%" src="'+ url+ '" ',
			closable : true

		});
		tabs = window.parent.tabPanel.getComponent(id);
	}
	window.parent.tabPanel.setActiveTab(tabs);
};

//签收任务
var unClaimTask = function (object){
    var taskId = $(object).attr('taskId');
    Ext.Ajax.request( {
		url : ctx + "/public-access/workflow/rw.do?unClaimTask&taskId="+ taskId,
		method : 'post',
		success : function(response, options) {
			var o = Ext.JSON.decode(response.responseText);
			Ext.MessageBox.alert('Success',o.msg);
			gridStore.load();
	  	},
	  	failure : function(response, options) {
	  		var o = Ext.JSON.decode(response.responseText);
	  		Ext.MessageBox.alert('Success',o.msg);
	  	}
 	});
};
//签收任务
var claimTask = function (object){
    var taskId = $(object).attr('taskId');
    Ext.Ajax.request( {
		url : ctx + "/public-access/workflow/rw.do?claimTask&taskId="+ taskId,
		method : 'post',
		success : function(response, options) {
			var o = Ext.JSON.decode(response.responseText);
			Ext.MessageBox.alert('Success',o.msg);
			gridStore.load();
	  	},
	  	failure : function(response, options) {
	  		var o = Ext.JSON.decode(response.responseText);
	  		Ext.MessageBox.alert('Success',o.msg);
	  	}
 	});
};

//办理
var njmx = function (object){
    var id= $(object).attr('id');
    var rwlxDm = $(object).attr('rwlxDm');
    var name = $(object).attr('name');
    switch(rwlxDm){
		case '00010001':
			rwlxDm = '年假';
			break;
		case '00010002':
			rwlxDm =  '公干';
			break;
		default:
			return;
	}
    var title = rwlxDm +'('+ name + ')';
    var url=ctx + "/qjgl/njgl.do?njmx&id="+ id;
	childPageAddTab(id, title, url);
};

//驳回任务
var rejectTask = function(object){
	var taskId = $(object).attr('taskId');
    Ext.Ajax.request( {
		url : ctx + "/public-access/workflow/rw.do?rejectTask&taskId="+ taskId,
		method : 'post',
		success : function(response, options) {
			var o = Ext.JSON.decode(response.responseText);
			Ext.MessageBox.alert('Success',o.msg);
			gridStore.load();
	  	},
	  	failure : function(response, options) {
	  		var o = Ext.JSON.decode(response.responseText);
	  		Ext.MessageBox.alert('Success',o.msg);
	  	}
 	});
};




