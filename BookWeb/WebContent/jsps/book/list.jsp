<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>图书列表</title>
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/css/book/list.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/jsps/pager/pager.css'/>" />
    <script type="text/javascript" src="<c:url value='/jsps/pager/pager.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jquery/jquery-1.5.1.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/jsps/js/book/list.js'/>"></script>
</head>
<body>

<c:forEach items="${pb.beanList }" var="book">
	<li>
		<div class="inner">
			<a class="pic" href="${pageContext.request.contextPath }/BookServlet?method=load&bid=${book.bid }"><img src="${pageContext.request.contextPath }/${book.image_b }" border="0"/></a>
			<p class="price">
				<span class="price_n">&yen;${book.currPrice }</span>
				<span class="price_r">&yen;${book.price }</span>
				(<span class="price_s">${book.discount }折</span>)
			</p>
			<p><a id="bookname" title="${book.bname }" href="${pageContext.request.contextPath }/BookServlet?method=load&bid=${book.bid }">${book.bname }</a></p>
			<%--url标签会自动对参数进行url编码 --%>
			<c:url value="/BookServlet" var="authorUrl">
				<c:param name="method" value="findByAuthor"/>
				<c:param name="author" value="${book.author }"/>
			</c:url>
			<c:url value="/BookServlet" var="pressUrl">
				<c:param name="method" value="findByPress"/>
				<c:param name="press" value="${book.press }"/>
			</c:url>
			<p><a href="${authorUrl }" name='P_zz' title='${book.author }'>${book.author }</a></p>
			<p class="publishing">
				<span>出 版 社：</span><a href="${pressUrl }">${book.press }</a>
			</p>
			<p class="publishing_time"><span>出版时间：</span>${book.publishtime }</p>
		</div>
	</li>
</c:forEach>

<div style="float:left; width: 100%; text-align: center;">
	<hr/>
	<br/>
	<%@include file="/jsps/pager/pager.jsp" %>
</div>

</body>
</html>