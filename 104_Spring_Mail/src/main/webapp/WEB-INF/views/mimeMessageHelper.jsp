<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Mail Send</title>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	
	<!-- jQuery library -->
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

	<!-- Latest compiled JavaScript -->
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<!-- CkEditor -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/ckeditor/ckeditor.js"></script>
</head>
<body>
	<div class="container">
		<h4>메일 보내기</h4>
		<form action="${pageContext.request.contextPath}/mail/mimeMessageHelper.do"
			method="post" enctype="multipart/form-data">
			<div align="center">
				<!-- 받는 사람 이메일 -->
				<input type="text" name="tomail" size="120" style="width: 100%"
					placeholder="상대의 이메일" class="form-control">
			</div>
			<div align="center">
				<!-- 제목 -->
				<input type="text" name="title" size="120" style="width: 100%"
					placeholder="제목을 입력해주세요" class="form-control">
			</div>
			<div>
				<input type="file" name="files[0]" />
			</div>
			<p>
			<div align="center">
				<!-- 내용 -->
				<textarea name="content" cols="120" rows="12"
					style="width: 100%; resize: none" placeholder="내용#"
					class="form-control ckeditor"></textarea>
			</div>
			<p>
			<div align="center">
				<input type="submit" value="메일 보내기" class="btn btn-warning">
			</div>
		</form>
	</div>
</body>
</html>