Ext.require([ '*' ]);

Ext.onReady(function() {
	var data= [['年假','00010001'],['公干','00010002']];
    var store = new Ext.data.SimpleStore({
        fields :['text','value'],
        data : data
    });
	var formPanel = Ext.create('Ext.form.Panel', {
		id : 'formPanel',
		title : '请假申请',
		width : '100%',
		height: 400,
		defaultType : 'textfield',
		labelWidth : 80,
		frame : true,
		method : 'POST',
		collapsible : true,// 可折叠
		bodyPadding : 10,
		margin : '0 0 10 0',
		items : [ {
			fieldLabel : '请假类型',
			xtype: 'combo',
			id : 'rwlxId',
			name : 'rwlxId',
			anchor : '60%',     // 布局
			store : store,
			displayField : 'text',  // 显示字段
            valueField : 'value',   // 值，可选
            mode: 'local',//因为data已经取数据到本地了，所以’local’,默认为”remote”，枚举完
            emptyText:'请选择...',
            allowBlank : false,// 不允许为空
            readOnly : true,
			value : $('#trwlxId').val()
		}, {
			fieldLabel : '开始时间',
			anchor : '60%',     // 布局
			xtype: 'datefield',
			format: 'Y-m-d H:i:s',
			id : 'kssj',
			name : 'kssj',
			value : $('#tkssj').val()
		}, {
			fieldLabel : '结束时间',
			anchor : '60%',     // 布局
			xtype: 'datefield',
			format: 'Y-m-d H:i:s',
			id : 'jssj',
			name : 'jssj',
			readOnly : true,
			value : $('#tjssj').val()
		}, {
	        xtype     : 'textareafield',
	        grow      : true,
	        id      : 'qjyy',
	        name      : 'qjyy',
	        fieldLabel: '请假原因',
	        anchor    : '100%',
	        readOnly : true,
	        value : $('#tqjyy').val()
	    }, {
	        xtype     : 'textareafield',
	        grow      : true,
	        id      : 'spyj',
	        name      : 'spyj',
	        fieldLabel: '审批意见',
	        anchor    : '100%',
	        allowBlank : false,// 不允许为空
	    }, { 
            xtype : 'radiogroup', 
            fieldLabel : '审批', 
            //itemCls : 'x-check-group-alt', 
            // Put all controls in a single column with width 100% 
            columns : 3, 
            items : [ { 
                boxLabel : '退回', 
                name : 'spjg' ,
                inputValue : 0
            }, { 
                boxLabel : '通过', 
                name : 'spjg',
                inputValue : 1 
            }, { 
                boxLabel : '拒绝', 
                name : 'spjg' ,
                inputValue : 2
            } ] 
        }, {
			fieldLabel : 'taskId',
			anchor : '60%',     // 布局
			id : 'taskId',
			name : 'taskId',
			value : $('#ttaskId').val()
		}, {
			fieldLabel : 'id',
			anchor : '60%',     // 布局
			id : 'id',
			name : 'id',
			value : $('#tid').val()
		}],
		buttons: [{
	        text: '保存',
	        handler: function() {
	        	Ext.getCmp('formPanel').getForm().submit({
					url : ctx + "/qjgl/njgl.do?insertOrUpdateSpyj",
					waitTitle:"请稍候",
					waitMsg:"正在提交表单数据，请稍候。。。。。。",
					success : function(form, action) {
						var data = Ext.JSON.decode(action.response.responseText);
						Ext.Msg.alert('Success', data.msg);
						this.disable(); 
					},
					failure : function(form, action) {
						var data = Ext.JSON.decode(action.response.responseText);
						Ext.Msg.alert('Failure', data.msg);
					}
				});
	        }
	    }, {
			text : '提交',
			handler : function() {
				Ext.getCmp('formPanel').getForm().submit({
					url : ctx + "/qjgl/njgl.do?updateYwAndRw",
					waitTitle:"请稍候",
					waitMsg:"正在提交表单数据，请稍候。。。。。。",
					success : function(form, action) {
						var data = Ext.JSON.decode(action.response.responseText);
						Ext.Msg.alert('Success', data.msg);
						this.disable(); 
					},
					failure : function(form, action) {
						var data = Ext.JSON.decode(action.response.responseText);
						Ext.Msg.alert('Failure', data.msg);
					}
				});
			}
		}, {
			text : '流程图',
			handler : function() {
				
				var processDefId = $('#procDefId').val();
				var processInstanceId = $('#procInsId').val();
				var executionId = "";
				// 弹出一个窗口，可以选择要部署的流程
		        var win = new top.Ext.Window({
		            //layout : 'fit',//设置window里面的布局  
		            height:410,  
		            width:770,  
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
		            html:'<iframe style="overflow:auto;width:100%; height:100%;" src='+ctx+'"/public-access/workflow/processManage.do?'
		            	+'showPic&processDefId='+ processDefId
		            	+'&processInstanceId='+processInstanceId
		            	+'&executionId='+executionId
		            	+'" frameborder="0"></iframe>'
		            
		        });  
		        win.show(); 
			
			}
		}],
		renderTo : Ext.getBody()
	});
	

});
