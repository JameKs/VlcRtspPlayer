<!-- 
<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%
/**
*流程发起的共用ToolBar
*@author zengziwen 
*@date   2012-9-5
*
*/
	String ctx = request.getContextPath();
	String sp_param = request.getParameter("p_sp");//审批标识，用来标识是否为新发起的流程任务，还是对流程进行审批；true/false
	boolean _sp = false;
	if(sp_param==null||sp_param.trim().length()==0){
		_sp = false;
	}else{
		_sp = true;
	}
%>
-->

<div id="procBar"></div>
<form id="_business_form_fw" name="_business_form_fw" method="post"> 
	<input type="hidden"  id="_processDeployKey"  name="_processDeployKey"  value="" >
	<input type="hidden"  id="_ywId"  name="_ywId"  value="" >
	<input type="hidden"  id="_ywName"  name="_ywName"  value="" >
	<input type="hidden"  id="_NotApprNode"  name="_NotApprNode"  value="" >
	<!-- 下一个处理人，审批意见 -->
	<input type="hidden"  id="_nextUserIds"  name="_nextUserIds"  value="" >
	<input type="hidden"  id="_opinion"  name="_opinion"  value="" >
	<input type="hidden"  id="_VAR_opinion_type"  name="_VAR_opinion_type"  value="" >
	<!-- 审批页面展现 -->
	<input type="hidden"  id="_sp"    value="<%=_sp %>" >
	<input type="hidden"  id="_wfTaskId"    name="_wfTaskId" value="" >
	<input type="hidden"  id="_wfActNodeId"    name="_wfActNodeId" value="" >
	<input type="hidden"  id="_wfActNodeName"  name="_wfActNodeName"   value="" >
	<input type="hidden"  id="_wfProcessInstanceId"  name="_wfProcessInstanceId"   value="" >
	<input type="hidden"  id="_VAR_priority"  name="_VAR_priority"   value="50" ><!-- 任务级别 -->
	<input type="hidden"  id="_VAR_priority_ts"  name="_VAR_priority_ts"   value="5" ><!-- 任务级别下的天数 -->
	<input type="hidden"  id="_createTime"name="_createTime" value= ''/><!-- 取交办时间 -->
	<!-- 后续处理环节 -->
	<input type="hidden"  id="_VAR_wfNextNode"    name="_VAR_wfNextNode" 　value="" >
	<input type="hidden"  id="_VAR_mark"    name="_VAR_mark" 　value="" >
	<!-- 回退 -->
	<input type="hidden" name="activitiId"  value="">
	<input type="hidden" name="activitiName"  value="">
	<input type="hidden" name="_opinion2"  value="" >

</form>
					
<div id='deptUsertree'></div>
<script type="text/javascript">
Ext.require([ '*' ]);

Ext.onReady(function() {
	var procBar = Ext.create("Ext.Toolbar",{
		renderTo:'procBar',
		items:[{
			glyph:70,
			text:'关闭',
			handler:function(){
				parent.parent.frames['mainFrame'].tabPanel.getActiveTab().close();
			}
		},{
			glyph:70,
			text:'流程图',
			handler:function(){
				var processDefId = $('#procDefId').val();
				var processInstanceId = $('#procInsId').val();
				var executionId = "";
				// 弹出一个窗口，可以选择要部署的流程
			    var win = new Ext.Window({
			        //layout : 'fit',//设置window里面的布局  
			        height:410,  
			        width:770,  
			        //关闭时执行隐藏命令,如果是close就不能再show出来了  
			        closeAction:'hide',  
			        modal : true,//屏蔽其他组件,自动生成一个半透明的div  
			        plain : true,//对窗口进行美化,可以看到整体的边框  
			        buttonAlign : 'center',//按钮的对齐方式  
			        defaultButton : 0,//默认选择哪个按钮  
			        html:'<iframe style="overflow:auto;width:100%; height:100%;" src="'+ ctx +'/public-access/workflow/processManage.do?'
			        	+'showPic&processDefId='+ processDefId
			        	+'&processInstanceId='+processInstanceId
			        	+'&executionId='+executionId
			        	+'" frameborder="0"></iframe>'
			        
			    });  
			    win.show(); 
			}
		},{
			glyph:70,
			text:'保存',
			handler:function(){
				var formParam = $("#form1").serialize();
				$.ajax({
					type:'POST',
					url : ctx + "/qjgl/njgl.do?insertOrUpdateSpyj",
					data:{
						yhrwId:$('#yhrwId').val(),
						spyj:$('#spyj').val()
					},
					dataType:'json',
					success : function(data) {
						Ext.Msg.alert('Success', data.msg);
						//this.disable(); 
					},
					error : function(data) {
						Ext.Msg.alert('Failure', data.msg);
					}
				});
			}
		},{
			glyph:70,
			text:'提交',
			handler:function(){
				$.ajax({
					type:'POST',
					url : ctx + "/qjgl/njgl.do?updateYwAndRw",
					data:{
						id:$('#id').val(),
						spyj:$('#spyj').val(),
						taskId:$('#taskId').val(),
						procInsId:$('#procInsId').val(),
						rwId:$('#rwId').val(),
						spjg:$('#spjg').val()
					},
					dataType:'json',
					success : function(data) {
						Ext.Msg.alert('Success', data.msg);
						$('#spjg').attr('disable',true); 
					},
					error : function(data) {
						Ext.Msg.alert('Failure', data.msg);
					}
				});
			}
		},{
			glyph:70,
			text:'转其他人处理',
			handler:function(){
				var detpUserStore = Ext.create('Ext.data.TreeStore', {
					proxy : {
						type : 'ajax',
						actionMethods: { read: 'POST' },
						url : ctx + '/cdxx/menu.do?getDeptGwUserTree'
					}

				});

				var detpUserTree = Ext.create('Ext.tree.Panel', {
					id:'detpUserTree',
					title : '部门人员树',
					store : detpUserStore,
					rootVisible : false,
					useArrows : true,
					renderTo:'deptUsertree',
					//renderTo:Ext.body,
					frame : false,
					style : 'margin: 1px',
					width: 400,
					height : 450
					,
					tbar:[{iconCls:'refreshItem',
						text:'刷新',
						handler:function(){
							detpUserStore.load();
						}
					},{
						iconCls:'refreshItem',
						text:'转派',
						handler:function(){
							var node = this.up().up().getSelectionModel().getLastSelected();
							if(node != null && node.data['leaf']){//如果是叶子节点
								var id = node.data['id'];
								$.ajax({
									type:'POST',
									url : ctx + "/public-access/workflow/rw.do?transferAssignee",
									data:{
										userId:id,
										taskId:$('#taskId').val()
									},
									dataType:'json',
									success : function(data) {
										Ext.Msg.alert('Success', data.msg);
										win.close();
										//$('#spjg').attr('disable',true); 
									},
									error : function(data) {
										Ext.Msg.alert('Failure', data.msg);
									}
								});
							}
						}
					},{
						iconCls:'refreshItem',
						text:'委托',
						handler:function(){
							var node = this.up().up().getSelectionModel().getLastSelected();
							if(node != null && node.data['leaf']){//如果是叶子节点
								var id = node.data['id'];
								$.ajax({
									type:'POST',
									url : ctx + "/public-access/workflow/rw.do?delegateTask",
									data:{
										userId:id,
										taskId:$('#taskId').val()
									},
									dataType:'json',
									success : function(data) {
										Ext.Msg.alert('Success', data.msg);
										win.close();
										//$('#spjg').attr('disable',true); 
									},
									error : function(data) {
										Ext.Msg.alert('Failure', data.msg);
									}
								});
							}
						}
					}]
				});
				
				// 弹出一个窗口，可以选择要部署的流程
			    var win = new Ext.Window({
			        //layout : 'fit',//设置window里面的布局  
			        title:'选择处理人',
			        height:450,  
			        width:770,  
			        //关闭时执行隐藏命令,如果是close就不能再show出来了  
			        closeAction:'hide',  
			        modal : true,//屏蔽其他组件,自动生成一个半透明的div  
			        plain : true,//对窗口进行美化,可以看到整体的边框  
			        buttonAlign : 'center',//按钮的对齐方式  
			        defaultButton : 0//默认选择哪个按钮  
			    });  
			    win.add(detpUserTree);
			    win.show(); 
			}
		}]
	});


});
</script>


