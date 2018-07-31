<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>pwd</title>
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/css/css.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jsps/css/user/pwd.css">
	<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/jquery-1.5.1.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath }/jsps/js/user/pwd.js"></script>
	<script src="${pageContext.request.contextPath }/js/common.js"></script>
</head>
<body>
	<div class="div0">
		<span>修改密码</span>
	</div>
	
	<div class="div1">
		<form action="${pageContext.request.contextPath }/UserServlet" method="post" target="_top">
			<input type="hidden" name="method" value="updatePassword"/>
			<table>
				<tr>
					<td><label class="error">${msg }</label></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td align="right">原密码：</td>
					<td><input type="password" class="input" name="loginpass" id="loginpass" value="${user.loginpass }"/></td>
					<td><label class="error" id="loginpassError"></label></td>
				</tr>
				<tr>
					<td align="right">新密码：</td>
					<td><input type="password" class="input" name="newloginpass" id="newloginpass" value="${user.newloginpass }"/></td>
					<td><label class="error" id="newloginpassError"></label></td>
				</tr>
				<tr>
					<td align="right">确认密码：</td>
					<td><input type="password" class="input" name="reloginpass" id="reloginpass" value="${user.reloginpass }"/></td>
					<td><label class="error" id="reloginpassError"></label></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td>
						<img id="vCode" src="${pageContext.request.contextPath }/VerifyCodeServlet" border="1"/>
						<a href="javascript: _change();">看不清？换一张</a>
					</td>
				</tr>
				<tr>
					<td align="right">验证码：</td>
					<td>
						<input type="text" class="input" name="verifyCode" id="verifyCode" value="${user.verifyCode }"/>
					</td>
					<td><label class="error" id="verifyCodeError"></label></td>
				</tr>
				<tr>
					<td align="right"></td>
					<td><input type="submit" id="submit" value="修改密码"/></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>