<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
 <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">

</head>
<body>
	
	<table class="table table-striped">
		<thead>
		  <tr>
		    <th>EMPNO</th>
		    <th>${emp.empno}</th>
		  </tr>
		</thead>
		<tbody>
		  <tr>
		    <td>ENAME</td>
		    <td>${emp.ename}</td>
		  </tr>
		  <tr>
		    <td>JOB</td>
		    <td>${emp.job}</td>
		  </tr>
		  <tr>
		    <td>MGR</td>
		    <td>${emp.mgr}</td>
		  </tr>
		  <tr>
		    <td>HIREDATE</td>
		    <td>${emp.hiredate}</td>
		  </tr>
		  <tr>
		    <td>SAL</td>
		    <td>${emp.sal}</td>
		  </tr>
		  <tr>
		    <td>COMM</td>
		    <td>${emp.comm}</td>
		  </tr>
		  <tr>
		    <td>DEPTNO</td>
		    <td>${emp.deptno}</td>
		  </tr>
		</tbody>
	</table>
	<a class="btn btn-primary" type="button" href="${pageContext.request.contextPath}/emp/empEdit.do?empno=${emp.empno}">수정하기</a>	
	<a class="btn btn-danger" type="button" href="${pageContext.request.contextPath}/emp/empDel.do?empno=${emp.empno}">사원 삭제</a>
	<a class="btn btn-success" type="button" href="${pageContext.request.contextPath}/emp/emp.do">목록가기</a>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>