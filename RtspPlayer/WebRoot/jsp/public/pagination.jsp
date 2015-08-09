<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String ctx = request.getContextPath();
%>
<script type="text/javascript">
  function gotoPage(pageIndex){
		 var path = window.location.href;
		 if(path.indexOf("pageIndex=")>0){
			 path = path.replace(/pageIndex=\d+/g,"pageIndex="+pageIndex);
			 window.location = path;
		 }else{
			 window.location = path + "?pageIndex="+pageIndex;
		 }
	  } 
 
 </script>
<div id="pageDis">

<!--翻页begin -->
<table width="100%" border="0" align="center" cellspacing="0" cellpadding="0">
<tr>
  <td height="12"></td>
</tr>
<tr>
  <td align="center"><table width="99%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td>共 <font color="#FF0000" id="total">${pr.total}</font> 条 <font color="#FF0000" id="totalPage">${pr.info.totalPage }</font> 页 <font color="#FF0000">10 </font>条/页 第 <font color="#FF0000" id="pageIndex">${pr.info.pageIndex }</font> 页 </td>
        <td width="60"><img src="<%=ctx %>/resource/fbrp/main/images-systems-01/page/page01.gif" align="absmiddle"> <a href="javascript:gotoPage(${pr.info.first })">首页</a> </td>
        <td width="60"><img src="<%=ctx %>/resource/fbrp/main/images-systems-01/page/page02.gif" align="absmiddle"> <a href="javascript:gotoPage(${pr.info.prev })">前一页</a> </td>
        <td width="60"><img src="<%=ctx %>/resource/fbrp/main/images-systems-01/page/page03.gif" align="absmiddle"> <a href="javascript:gotoPage(${pr.info.next })">下一页</a> </td>
        <td width="60"><img src="<%=ctx %>/resource/fbrp/main/images-systems-01/page/page04.gif" align="absmiddle"> <a href="javascript:gotoPage(${pr.info.last })">尾页</a> </td>
        <td width="180" align="right" nowrap="nowrap">转到第
          <input name="PageNo1" type="text" size="3" style="width:36px;height:18px" value="${pr.info.pageIndex }"  id="want" >
        页&nbsp; </td>
        <td width="28" height="22" style="cursor:hand;" align="right"
nowrap="nowrap"><a  onclick="javascript:gotoPage($('#want').val());"><img src="<%=ctx %>/resource/fbrp/main/images-systems-01/page/btn_go.gif"></a></td>
      </tr>
  </table></td>
</tr>
<tr>
  <td height="6"></td>
</tr>
</table>
<!--翻页end-->

</div>