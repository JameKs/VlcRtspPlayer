<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>  
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="springform" %>
<%@ taglib uri="http://www.foresee.com.cn/fbrp/taglib" prefix="f" %>
<%
String ctx = request.getContextPath();
%>
<style type="text/css">

#leftDiv{ float:left; width:18%; height:100%; }
#rightDiv{
	width:80%;
	height:100%;
	float:left;
	margin-left:10px;
}
#leftTop{ width:100%; height:100%; border:1px solid #9DC6F2}
#leftBottom{ width:100%; height:50%;  margin-top: 10px;border:1px solid #9DC6F2}
#rightTop{ width:100%; height:100%; border:1px solid #9DC6F2}
#rightBottom{ width:100%; height:40%; margin-top: 10px;border:1px solid #9DC6F2}
#leftBottom table { font-size:12px; color:#929292}
#leftBottom table td input{ width:80%;}
#leftBottom table td select{ width:83%;}
</style>
<link  rel="stylesheet"  href="<%=ctx %>/pages/fbrp/scripts/tree/css/zTreeStyle.css" type="text/css" />
<script type="text/javascript" src="<%=ctx %>/pages/fbrp/scripts/tree/js/jquery.ztree.core-3.2.js"></script>
<script type="text/javascript">
	var setting = {
		data: {
			simpleData: {
				enable: true
			}
		}
    
	};
	
	var tableSetting = {
			url:'',
			parameter:{},
			toolbar:[],
			operator:[
			         {text:'修改',icon:'<%=ctx%>/resource/icons/operator/edit.gif',onClick:function(data){
			        	 
			         }}
		   ]
	};

</script>

<body>

<div id="leftDiv">
  <div id="leftTop">
    <f:panel header="模板树" height="400px;" width="100%;">
      <f:tree setting="setting" initValue="treeData" id="treeDemo"></f:tree>  
  </f:panel>
  </div>
  
</div>
<div id="rightDiv">
  <div id="rightTop">
	<f:table header="配置项" initValue="configList" id="rsTable" setting="tableSetting" showStripe="true">
		<f:column title="配置的KEY" property="id"  width="10%"/>
		<f:column title="配置的名称" property="name" width="30%" />
		<f:column title="配置的值" property="id" width="20%" />
		<f:column title="配置项的关系" property="name" width="25%" />
	</f:table>
  </div>
</div>
</body>
</html>
