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
�̰� ����
	<table border="1">
		<tr>
			<td>��ȣ</td>
			<td>�ۼ���</td>
			<td>����</td>
			<td>���� X</td>
		</tr>
		<c:forEach var="listdto" items="${list}">
		<tr>
			<td>${listdto.getEvent_title()}</td>
			<td>${listdto.getEvent_content()}</td>
		</tr>
		</c:forEach>
	</table>

</body>
</html>