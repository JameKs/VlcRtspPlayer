<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
 String ctx = request.getContextPath();
%>
<div id="pageDis">
     共${totalSize}条记录，${currentPage }/${totalPage }页
<select id="pageSize" style=" font-size: 12px" onchange="changePageSize()" name="pageSize" >
   <option value="10"  <c:if test="${pageSize==10 }">selected="selected"</c:if>>10</option>
   <option value="20" <c:if test="${pageSize==20 }">selected="selected"</c:if>>20</option>
   <option value="50" <c:if test="${pageSize==50 }">selected="selected"</c:if>>50</option>
   <option value="100" <c:if test="${pageSize==100 }">selected="selected"</c:if>>100</option>
   <option value="200" <c:if test="${pageSize==200 }">selected="selected"</c:if>>200</option>
</select>

转到第<input type="text" style="width: 40px; " value="${currentPage }" id="want" >页 

 <a  onclick="javascript:goToPage(${totalPage});"><img border="0" alt="" src="<%=ctx %>/pages/fbrp/images/paging/ico-view.gif"></a>
</div>

 <div id="pageOpera">
  <a href="firstPage"><img border="0" alt="第一页" src="<%=ctx %>/pages/fbrp/images/paging/first.gif"></a>
  <a href="prePage"><img border="0" alt="上一页" src="<%=ctx %>/pages/fbrp/images/paging/previous.gif"></a>
  <a href="nextPage"><img border="0" alt="下一页" src="<%=ctx %>/pages/fbrp/images/paging/next.gif"></a> 
  <a href="lastPage"><img border="0" alt="最后一页" src="<%=ctx %>/pages/fbrp/images/paging/last.gif"></a> 
  <a href="refreshPage"><img border="0" alt="刷新" src="<%=ctx %>/pages/fbrp/images/refresh.gif"></a>
  </div>