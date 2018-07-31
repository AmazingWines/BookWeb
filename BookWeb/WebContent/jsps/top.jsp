<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>top</title>
<style type="text/css">
	body {
		background: #15B69A;
		margin: 0px;
		color: #ffffff;
	}
	a {
		text-decoration:none;
		color: #ffffff;
		font-weight: 900;
	} 
	a:hover {
		text-decoration: underline;
		color: #ffffff;
		font-weight: 900;
	}
</style>
</head>
<body>
	<h1 style="text-align: center;">骆靖琰的网上图书商城</h1>
	<div style="font-size: 10pt; line-height: 10px;">
		<%--根据用户是否登录，显示不同的链接 --%>
		<c:choose>
			<c:when test="${empty sessionScope.sessionUser }">
				<a href="${pageContext.request.contextPath }/jsps/user/login.jsp" target="_parent">会员登录</a> |&nbsp;
				<a href="${pageContext.request.contextPath }/jsps/user/regist.jsp" target="_parent">注册会员</a>
			</c:when>
			<c:otherwise>
				会员名称：${sessionScope.sessionUser.loginname } &nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/CartItemServlet?method=myCart" target="body">我的购物车</a> &nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/OrderServlet?method=myOrder" target="body">我的订单</a> &nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/jsps/user/pwd.jsp" target="body">修改密码</a> &nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${pageContext.request.contextPath }/UserServlet?method=quit" target="_parent">退出</a> &nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="http://www.hstc.edu.cn" target="_top">联系我们</a>
			</c:otherwise>
		</c:choose>
	</div>
</body>
</html>