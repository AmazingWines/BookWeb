<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>根据书名查询</title>
<style type="text/css">
	body {
		margin-top: 5px;
		margin-bottom: 0px;
		margin-left:200px;
		color: #404040;
	}
	input {
		width: 300px;
		height: 30px;
		border-style:solid;
		margin:0px;
		border-color: #15B69A;
	}
	a {
		text-transform:none;
		text-decoration:none;
		border-width: 0px;
	} 
	a:hover {
		text-decoration:underline;
		border-width: 0px;
	}
	span {
		margin: 0px;
	}
</style>
</head>
<body>
	<form action="${pageContext.request.contextPath }/BookServlet" method="post" target="body" id="form1">
		<input type="hidden" name="method" value="findByBname"/>
		<input type="text" name="bname"/>
		<span>
			<a href="javascript:document.getElementById('form1').submit();"><img align="top" border="0" src="../images/btn.bmp"/></a>
			<a href="${pageContext.request.contextPath }/jsps/gj.jsp" style="font-size: 10pt; color: #404040" target="body">高级搜索</a>
		</span>
	</form>
</body>
</html>