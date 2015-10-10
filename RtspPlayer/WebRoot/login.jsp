<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  
<%
	String ctx = request.getContextPath();
	String msg = request.getParameter("msg");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>

<sec:csrfMetaTags/>

<TITLE>继续教育系统登录页面</TITLE>
<LINK href="<%=ctx%>/resources/login/css/Default.css" type=text/css rel=stylesheet>
<LINK href="<%=ctx%>/resources/login/css/xtree.css" type=text/css rel=stylesheet>
<LINK href="<%=ctx%>/resources/login/css/User_Login.css" type=text/css
	rel=stylesheet>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<META content="MSHTML 6.00.6000.16674" name=GENERATOR>
<script type="text/javascript">
	var msg = '${msg}';
	if(msg != null && msg != ''){
		alert(msg);
	}
	function submit(){
		document.form.submit();
	}
</script>
</HEAD>
<BODY id=userlogin_body>
	
	<form id="form" name="form" action="<c:url value='login'/>" method="POST">
		<input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>"/>
		<DIV></DIV>

		<DIV id=user_login>
			<DL>
				<DD id=user_top>
					<UL>
						<LI class=user_top_l></LI>
						<LI class=user_top_c></LI>
						<LI class=user_top_r></LI>
					</UL>
					<DD id=user_main>
					<UL>
					<LI class=user_main_l></LI>
					<LI class=user_main_c>
						<DIV class=user_main_box>
							<UL>
								<LI class=user_main_text>用户名:</LI>
								<LI class=user_main_input>
								<INPUT class=TxtUserNameCssClass
									name="username" maxLength=20 value="supadmin"></LI>
							</UL>
							<UL>
								<LI class=user_main_text>密码:</LI>
								<LI class=user_main_input>
								<INPUT class=TxtPasswordCssClass
									name="password" value="1"></LI>
							</UL>
							<UL>
								<LI class=user_main_text>Cookie:</LI>
								<LI class=user_main_input>
								<input type='checkbox' name='remember-me' />
								<span>Remember me on this computer.</span>
								</LI>
							</UL>
						</DIV>
					</LI>
					<LI class=user_main_r>
					<INPUT class=IbtnEnterCssClass
						id=IbtnEnter
						style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
						onclick='submit'
						type=image src="<%=ctx%>/resources/login/images/user_botton.gif"
						name=IbtnEnter></LI>
					</UL>
				<DD id=user_bottom>
				<UL>
					<LI class=user_bottom_l></LI>
					<LI class=user_bottom_c><SPAN style="MARGIN-TOP: 40px">技术支持：秦墨科技有限公司
							<A href="http://localhost/">继续教育</A>。
					</SPAN></LI>
					<LI class=user_bottom_r></LI>
				</UL>
			</DD>
		
			</DL>
	</DIV>
	<SPAN id=ValrUserName style="DISPLAY: none; COLOR: red"></SPAN>
	<SPAN id=ValrPassword style="DISPLAY: none; COLOR: red"></SPAN>
	<SPAN id=ValrValidateCode style="DISPLAY: none; COLOR: red"></SPAN>
	<DIV id=ValidationSummary1 style="DISPLAY: none; COLOR: red"></DIV>

	<DIV></DIV>

	</form>
</BODY>
</HTML>
