<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登录</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jsps/css/user/login.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/jsps/js/user/login.js"></script>
	<script src="${pageContext.request.contextPath }/js/common.js"></script>
	
<script type="text/javascript">
	$(function() {/*Map<String(Cookie名称), Cookie(Cookie本身)>*/
		//获取cookie中的用户名
		var loginname = window.decodeURI("${cookie.loginname.value}");
		if("${requestScope.user.loginname}") {
			loginname = "${requestScope.user.loginname}";
		}
		$("#loginname").val(loginname);
	});
</script>
</head>
<body>
	<div class="main">
		<div>
			<div class="imageDiv"><img class="img" src="${pageContext.request.contextPath }/images/zj.png"/></div>
			<div class="login1">
				<div class="login2">
					<div class="loginTopDiv">
						<span class="loginTop">会员登录</span>
						<span>
							<a href="${pageContext.request.contextPath }/jsps/user/regist.jsp" class="registBtn"></a>
						</span>
					</div>
					<div>
						<form target="_top" action="${pageContext.request.contextPath }/UserServlet" method="post" id="loginForm">
							<input type="hidden" name="method" value="login"/>
								<table>
									<tr>
										<td width="50"></td>
										<td><label class="error" id="msg">${msg }</label></td>
									</tr>
									<tr>
										<td width="50">用户名</td>
										<td><input class="input" type="text" name="loginname" id="loginname"/></td>
									</tr>
									<tr>
										<td height="20">&nbsp;</td>
										<td><label class="error" id="loginnameError"></label></td>
									</tr>
									<tr>
										<td>密&nbsp;&nbsp;&nbsp;码</td>
										<td><input class="input" type="password" name="loginpass" id="loginpass" value="${user.loginpass }"/></td>
									</tr>
									<tr>
										<td height="20">&nbsp;</td>
										<td><label class="error" id="loginpassError"></label></td>
									</tr>
									<tr>
										<td>验证码</td>
										<td>
											<input class="input yzm" type="text" name="verifyCode" id="verifyCode" value="${user.verifyCode }"/>
											<img id="vCode" src="${pageContext.request.contextPath }/VerifyCodeServlet"/>
											<a href="javascript: _change()" id="verifyCode">看不清？换一张</a>
										</td>
									</tr>
									<tr>
										<td height="20px">&nbsp;</td>
										<td><label class="error" id="verifyCodeError"></label></td>
									</tr>
									<tr>
										<td>&nbsp;</td>
										<td align="left">
											<input type="image" id="submit" src="${pageContext.request.contextPath }/images/login1.jpg" class="loginBtn"/>
										</td>
									</tr>
								</table>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>