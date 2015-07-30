<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%@ taglib uri="http://java.sun.com/jstl/fmt" prefix="fmt"%> 
<%
	String ctx = request.getContextPath();
%>
<script type="text/javascript">

var nodes = ${menus};

</script>
<script type="text/javascript" src="<%=ctx%>/jsp/admin/cdgl/cdxx.js"></script>
<!-- 
    <style>
        .x-tree-checked .x-grid-cell-inner {
            font-style: italic;
            color: #777;
        }
        .x-grid-row-selected .x-grid-cell {
            background-color: #efefef !important;
        }
    </style> -->

<div style="width:400px;float:left"><ul id="cdtree" class="ztree"></ul></div>
<div id="form_div"></div>
