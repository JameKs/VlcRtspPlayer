<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%> 
<%
	String ctx = request.getContextPath();
%>
<script type="text/javascript">

var menus = ${menus};
var pMenus = ${pMenus};
//alert(pMenus);
var ctx = '<%=ctx%>';

</script>
<script type="text/javascript" src="<%=ctx%>/jsp/sys/menu/menu.js"></script>

<div style="width:400px;float:left"><ul id="cdtree" class="ztree"></ul></div>
<div id="form_div"></div>
