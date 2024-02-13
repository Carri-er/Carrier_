<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
</head>
<body>
이게 뭐람
	<table border="1">
		<tr>
			<td>번호</td>
			<td>작성자</td>
			<td>제목</td>
			<td>삭제 X</td>
		</tr>
		<c:forEach var="listdto" items="${list}">
		<tr>
			<td>${listdto}</td>
			<td>${listdto}</td>
		</tr>
		</c:forEach>
	</table>

</body>
</html>