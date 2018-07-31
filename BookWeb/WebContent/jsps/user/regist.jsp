<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册页面</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath }/jsps/css/user/regist.css">

<script type="text/javascript" src="${pageContext.request.contextPath }/jquery/jquery-1.5.1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/jsps/js/user/regist.js"></script>
</head>
<body>
	<div id="divMain">
		<div id="divTitle">
			<span id="spanTitle">新用户注册</span>
		</div>
		<div id="divBody">
			<form action="${pageContext.request.contextPath }/UserServlet" method="post" id="registForm">
				<input type="hidden" name="method" value="regist"/>
				<table id="tableForm">
					<tr>
						<td class="tdText">用户名：</td>
						<td class="tdInput">
							<input type="text" name="loginname" id="loginname" class="inputClass"/>
						</td>
						<td class="tdError">
							<label id="loginnameError" class="errorClass"></label>
						</td>
					</tr>
					<tr>
						<td class="tdText">登录密码：</td>
						<td>
							<input type="password" name="loginpass" class="inputClass" id="loginpass"/>
						</td>
						<td>
							<label class="errorClass" id="loginpassError"></label>
						</td>
					</tr>
					<tr>
						<td class="tdText">确认密码：</td>
						<td>
							<input type="password" name="reloginpass" class="inputClass" id="reloginpass"/>
						</td>
						<td>
							<label class="errorClass" id="reloginpassError"></label>
						</td>
					</tr>
					<tr>
						<td class="tdText">Email：</td>
						<td>
							<input type="text" name="email" class="inputClass" id="email"/>
						</td>
						<td>
							<label class="errorClass" id="emailError"></label>
						</td>
					</tr>
					<tr>
						<td class="tdText">验证码：</td>
						<td>
							<input type="text" name="verifyCode" class="inputClass" id="verifyCode"/>
						</td>
						<td>
							<label class="errorClass" id="verifyCodeError"></label>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<div id="divVerifyCode"><img id="imgVerifyCode" src="${pageContext.request.contextPath }/VerifyCodeServlet"></div>
						</td>
						<td>
							<label><a href="javascript: _hyz()">看不清？换一张</a></label>
						</td>
					</tr>
					<tr>
						<td></td>
						<td>
							<input type="image" src="${pageContext.request.contextPath }/images/regist1.jpg" id="submitBtn"/>
						</td>
						<td>
							<label></label>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>