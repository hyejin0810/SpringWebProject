<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="se"	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>모두의 채팅</title>
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script type="text/javascript">
		
	</script>
</head>
<body>
	<!-- spring security 제공하는 script 언어 -->
	<se:authorize access="!hasRole('ROLE_USER')">
	   <a href="${pageContext.request.contextPath}/login">로그인</a>
	</se:authorize>
	<se:authentication property="name" var="loginuser"  />
	<!-- name 값이 가지는 값을 loginuser 변수에 할당 
		 pageContext.request.userPrincipal.name
	-->
	<se:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
		<a href="${pageContext.request.contextPath}/logout">
			(${loginuser})로그아웃</a>
	</se:authorize>

	<a href="${pageContext.request.contextPath}/chat/chatAll.do">채팅입장</a><br>
	
</body>
</html>