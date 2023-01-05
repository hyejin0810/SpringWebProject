<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">

<script type="text/javascript">

	$(document).ready(function(){
		
		function formview(){
			$("#formempno").val("");
			$("#formename").val("");
			$("#formjob").val("");
			$("#formmgr").val("");
			$("#formhiredate").val("");
			$("#formsal").val("");
			$("#formcomm").val("");
			$("#formdeptno").val("");
			
			if(document.getElementById("writeform").classList.item(2) == null){
	            document.getElementById("writeform").className += " visually-hidden";
	        }else{
	            document.getElementById("writeform").classList.remove("visually-hidden");
	        }
		}
	
		//사원등록 form 보이게 하거나 지우기
		$('#empwritebtn').click(function(){
			formview();
		});
		
		//사원 등록 비동기 처리
		$("#empwritesubmit").click(function(){
			
			let requestdata = {
						"empno": $("#formempno").val(), 
						"ename": $("#formename").val(),
						"job": $("#formjob").val(),
						"mgr": $("#formmgr").val(),
						"hiredate": $("#formhiredate").val(),
						"sal": $("#formsal").val(),
						"comm": $("#formcomm").val(),
						"deptno": $("#formdeptno").val()
					}
			let data = JSON.stringify(requestdata);
			
			$.ajax({
				type: "post",
				url: "empWrite.do",
				data: data,
				dataType: "text",
				contentType: "application/json; charset=utf-8",
				success: function(data){
					formview();
					alert(data);
					
				}
			});
			
		});
		
		//사원 번호로 검색
		$("#empnosearchbtn").click(function(){
			
			if($("#empnosearch").val() == "" || $("#empnosearch").val() == null){
				
				$.ajax({
					type: "post",
					url: "empList.do",
					contentType: "application/json; charset=utf-8",
					success: function(data){
						
						$("#empList").empty();
						let html = "";
						
						$.each(data, function(){
							html += "<tr>";
							html += '<td>' + this.empno + '</td>';
							html += '<td>' + this.ename + '</td>';
							html += '<td>' + this.job + '</td>';
							html += '<td>' + this.mgr + '</td>';
							html += '<td>' + this.hiredate + '</td>';
							html += '<td>' + this.sal + '</td>';
							html += '<td>' + this.comm + '</td>';
							html += '<td>' + this.deptno + '</td>';
							html += '<td><a href="${pageContext.request.contextPath}/emp/empDetail.do?empno=' + this.empno + '">상세보기</a></td>'
							html += "</tr>";
						});
						
						$("#empList").append(html);
						
					}
				});
				
			}else{
				let requestdata1 = {"empno": $("#empnosearch").val()};
				$.ajax({
					type: "post",
					url: "empSearch.do",
					data: requestdata1,
					success: function(data){
						
						$("#empList").empty();
						let html = "";
						
						html += "<tr>";
						html += '<td>' + data.empno + '</td>';
						html += '<td>' + data.ename + '</td>';
						html += '<td>' + data.job + '</td>';
						html += '<td>' + data.mgr + '</td>';
						html += '<td>' + data.hiredate + '</td>';
						html += '<td>' + data.sal + '</td>';
						html += '<td>' + data.comm + '</td>';
						html += '<td>' + data.deptno + '</td>';
						html += '<td><a href="${pageContext.request.contextPath}/emp/empDetail.do?empno=' + data.empno + '">상세보기</a></td>'
						html += "</tr>";
						
						
						$("#empList").append(html);
						
					}
				});
			}
			
		});
		
		//사원명으로 검색
		$("#enamesearch").keyup(function(){
			let requestdata1 = {"ename": $("#enamesearch").val()};
			let data1 = JSON.stringify(requestdata1);
			
			$.ajax({
				type: "post",
				url: "enameSearch.do",
				data: data1,
				contentType: "application/json; charset=utf-8",
				success: function(data){
					
					$("#empList").empty();
					let html = "";
					
					$.each(data, function(){
						html += "<tr>";
						html += '<td>' + this.empno + '</td>';
						html += '<td>' + this.ename + '</td>';
						html += '<td>' + this.job + '</td>';
						html += '<td>' + this.mgr + '</td>';
						html += '<td>' + this.hiredate + '</td>';
						html += '<td>' + this.sal + '</td>';
						html += '<td>' + this.comm + '</td>';
						html += '<td>' + this.deptno + '</td>';
						html += '<td><a href="${pageContext.request.contextPath}/emp/empDetail.do?empno=' + this.empno + '">상세보기</a></td>'
						html += "</tr>";
					});
					
					$("#empList").append(html);
					
				}
			});
		});
		
		//소켓 테스트 페이지로 이동
		$("#chat").click(function(){
			location.href='emp/chat.do';
		});
		
	});


</script>

</head>
<body>
	<div class="m-5">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>사원번호 :
						<input type="text" id="empnosearch">
						<input type="submit" id="empnosearchbtn" value="검색">
					</th>
					<th>사원명 :
						<input type="text" id="enamesearch">
					</th>
					<th><a class="btn btn-outline-primary" id="empwritebtn">사원 등록</a></th>
					<th><a class="btn btn-outline-primary" id="chat">채팅</a></th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="writeform" class="m-5 row visually-hidden">
		<div class="col-2"></div>
		<div class="col-8 text-center">
			<h3><strong>**사원**</strong></h3>
			<table class="table table-striped">
				<tbody>
					<tr>
						<td>EMPNO</td>
						<td><input type="text" id="formempno" value="" required /></td>
					</tr>
					<tr>
						<td>ENAME</td>
						<td><input type="text" id="formename" value="" /></td>
					</tr>
					<tr>
						<td>JOB</td>
						<td><input type="text" id="formjob" value="" /></td>
					</tr>
					<tr>
						<td>MGR</td>
						<td><input type="text" id="formmgr" value="" /></td>
					</tr>
					<tr>
						<td>HIREDATE</td>
						<td><input type="text" id="formhiredate" value="" /></td>
					</tr>
					<tr>
						<td>SAL</td>
						<td><input type="text" id="formsal" value="" /></td>
					</tr>
					<tr>
						<td>COMM</td>
						<td><input type="text" id="formcomm" value="" /></td>
					</tr>
					<tr>
						<td>DEPTNO</td>
						<td><input type="text" id="formdeptno" value="" /></td>
					</tr>
				</tbody>
			</table>
			<a class="btn btn-outline-primary" id="empwritesubmit">확인</a>
			<a class="btn btn-outline-danger" id="empwritesubmit">취소</a>
		</div>
		<div class="col-2"></div>
	</div>
	
	<div class="m-3">
		<table class="table table-striped">
			<thead>
				<tr>
					<th>EMPNO</th>
					<th>ENAME</th>
					<th>JOB</th>
					<th>MGR</th>
					<th>HIREDATE</th>
					<th>SAL</th>
					<th>COMM</th>
					<th>DEPTNO</th>
					<th>수정/삭제</th>
				</tr>
			</thead>
			<tbody id="empList">
		
				<c:forEach items="${list}" var="l">
					<tr>
						<td>${l.empno}</td>
						<td>${l.ename}</td>
						<td>${l.job}</td>
						<td>${l.mgr}</td>
						<td>${l.hiredate}</td>
						<td>${l.sal}</td>
						<td>${l.comm}</td>
						<td>${l.deptno}</td>
						<td><a href="${pageContext.request.contextPath}/emp/empDetail.do?empno=${l.empno}">상세보기</a>
						</td>
					</tr>
				</c:forEach>
		
			</tbody>
		</table>
	</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>