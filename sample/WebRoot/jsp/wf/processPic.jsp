<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.io.InputStream" %>
<%
/**
* 流程图
* 作者：zengziwen  zengziwen@foresee.cn
* 时间:  2012-07-30
**/
String ctx = request.getContextPath();
String msg=(String)request.getAttribute("msg");

%> 
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache" />
<meta http-equiv="Expires" content="0" /> 
</head>
<script>
if("<%=msg%>"!='') alert("<%=msg%>");
</script>
<body>
<div>
<%
InputStream is = (InputStream)request.getAttribute("imageData");
 
byte[] b = new byte[1024];   
int len = -1;   
while((len = is.read(b, 0, 1024)) != -1) {   
   response.getOutputStream().write(b, 0, len);   
    out.clear();   
    out = pageContext.pushBody();   
}      
 %>
 </div>
 </body>
</html>
