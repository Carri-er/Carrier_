<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
	<%
	out.print("MyBatis : 바티스 안녕!");
	%>
	<br />
	<c:forEach var="dto" items="${list}">
		${dto.Eventtitle} / ${dto.Eventcontent} <br />
	</c:forEach>
</body>
</html>