<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%
// 消息提示&转发器
//添加人：zenzgiwen  2012-8-10
//处理Controll中的消息提示以及进行跳转处理
String ctx = request.getContextPath();
%>

<%
String message =(String) request.getAttribute("message");
String url =(String) request.getAttribute("url");
%>
<script type='text/javascript'>
var msg = '<%=message%>';
if(msg!=null&&msg!=''&msg!='null') alert(msg);
<%
StringBuffer suf = new StringBuffer();

if(url!=null){
	String[] array =null;
	int  idx =url.indexOf("?");
	if(idx!=-1){
		array = new String[]{url.substring(0,idx),url.substring(idx+1,url.length())};
	}else{
		array = new String[]{url};
	}
	
	if(array.length>1){
		String action = array[0];
		String[] param = array[1].split("&");
		suf.append("<form  action='").append(ctx).append("").append(array[0]).append("' id='msgForm' name='msgForm' method='post' >");
		for(int i=0;i<param.length;i++){
			String[] tpVl = param[i].split("=");
			suf.append("<input type='hidden' name='").append(tpVl[0]).append("'");
			if(tpVl.length>1) suf.append(" value='").append(tpVl[1]).append("' />");
		}
		suf.append("</form>");
	}
 
}
%>
</script>
<%
out.println(suf.toString());
%>
<script>
 
<%
out.println("document.getElementById('msgForm').submit()");
%>
</script>


