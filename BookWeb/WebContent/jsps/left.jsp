<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>left</title>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/left.css'/>">
<script language="javascript">
/*
 * 1、对象名必须和第一个参数相同
   2、第二个参数显示菜单的大标题
 */
var bar = new Q6MenuBar("bar", "网上图书商城");
$(function() {
	bar.colorStyle = 4;//指定配色样式
	bar.config.imgDir = "<c:url value='/menu/img/'/>";//小工具所需图片的路径
	bar.config.radioButton=true;//是否排斥，多个一级分类是否排斥
	
	/*
	1、add方法的第一个参数：一级分类名称
	2、第二个参数：二级分类名称
	3、第三个参数：点击二级分类后链接到的URL
	4、第四个参数：链接的内容在哪个框架页显示
	*/
<c:forEach items="${parents}" var="parent">
	<c:forEach items="${parent.children}" var="children">
		bar.add("${parent.cname}", "${children.cname}", "/BookWeb/BookServlet?method=findByCategory&cid=${children.cid }", "body");
	</c:forEach>
</c:forEach>
	
	$("#menu").html(bar.toString());
});
</script>
</head>
<body>  
  	<div id="menu"></div>
</body>
</html>
