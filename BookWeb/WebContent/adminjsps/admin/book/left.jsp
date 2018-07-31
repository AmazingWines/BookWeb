<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>left</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<link rel="stylesheet" type="text/css" href="<c:url value='/adminjsps/admin/css/book/left.css'/>">
	<script type="text/javascript" src="<c:url value='/menu/mymenu.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<link rel="stylesheet" href="<c:url value='/menu/mymenu.css'/>" type="text/css" media="all">
<script language="javascript">
/*
 * 1、对象名必须和第一个参数相同
   2、第二个参数显示菜单的大标题
 */
var bar = new Q6MenuBar("bar", "网上图书商城");
$(function() {
	bar.colorStyle = 2;//指定配色样式
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
		bar.add("${parent.cname}", "${children.cname}", "/BookWeb/AdminBookServlet?method=findByCategory&cid=${children.cid }", "body");
	</c:forEach>
</c:forEach>
	
	$("#menu").html(bar.toString());
});
</script>
  </head>
  
  <body onload="load()">
<div id="menu"></div>
  </body>
</html>
