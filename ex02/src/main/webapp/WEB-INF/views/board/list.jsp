<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<%@ include file="../includes/header.jsp"%>

<!-- Begin Page Content -->
<div class="container-fluid">

	<!-- Page Heading -->
	<h1 class="h3 mb-2 text-gray-800">Board</h1>

	<!-- DataTales Example -->
	<div class="card shadow mb-4">
		<div class="card-header py-3">
			<h4 class="m-0 font-weight-bold text-primary">
				List <a href="/board/register"
					class="btn btn-primary btn-sm float-right">글쓰기</a>
			</h4>
		</div>
		<div class="card-body">
			<table class="table table-bordered" width="100%" cellspacing="0">

				<tr>
					<th>#번호</th>
					<th>제목</th>
					<th>작성자</th>
					<th>작성일</th>
					<th>수정일</th>
				</tr>


				<!-- 테이블 내용 ------------------------->
				<c:forEach items="${list}" var="board">
					<tr>
						<td><c:out value="${board.bno }" /></td>
						<%-- <td>
							<!--  // 링크 태그를 적용해서 조회 페이지로 이동하게 처리 --> <a
							href='/board/get?bno=<c:out value="${board.bno}" />'> <c:out
									value="${board.title }" />
						</a>
						</td> --%>
						<td><a class='move' href='<c:out value="${board.bno}"/>'>
								<c:out value="${board.title}" />
						</a></td>

						<td><c:out value="${board.writer }" /></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd"
								value="${board.regdate}" /></td>
						<td><fmt:formatDate pattern="yyyy-MM-dd"
								value="${board.updateDate}" /></td>
					</tr>
				</c:forEach>

				<!-- 테이블 내용 ----->
			</table>


			<!-- Paging ------------------------->
			<div class="float-right">
				<ul class="pagination">
					<c:if test="${pageMaker.prev }">
						<li class="page-item previous"><a class="page-link"
							href="${pageMaker.startPage -1 }">Previous</a></li>
					</c:if>

					<c:forEach var="num" begin="${pageMaker.startPage }"
						end="${pageMaker.endPage }">
						<li
							class="page-item ${pageMaker.cri.pageNum == num ? 'active':''}">
							<a class="page-link" href="${num }">${num }</a>
						</li>
					</c:forEach>

					<c:if test="${pageMaker.next }">
						<li class="page-item next"><a class="page-link"
							href="${pageMaker.endPage +1 }">Next</a></li>
					</c:if>
				</ul>
			</div>
			<form id='actionForm' action="/board/list" method='get'>
				<input type='hidden' name='pageNum' value='${pageMaker.cri.pageNum}'>
				<input type='hidden' name='amount' value='${pageMaker.cri.amount}'>
			</form>



			<!-- TheModal 추가 -->
			<div class="modal" id="myModal">
				<div class="modal-dialog">
					<div class="modal-content">

						<!-- Modal Header -->
						<div class="modal-header">
							<h4 class="modal-title">알림</h4>
							<button type="button" class="close" data-dismiss="modal">
								&times;</button>
						</div>

						<!-- Modal body -->
						<div class="modal-body">처리가 완료 되었습니다</div>

						<!-- Modal footer -->
						<div class="modal-footer">
							<button type="button" class="btn btn-danger" data-dismiss="modal">
								닫기</button>
						</div>
					</div>
				</div>
			</div>
			<!-- Modal 끝 --------->

		</div>
	</div>

</div>
<!-- /.container-fluid -->

</div>
<!-- End of Main Content -->

<%@ include file="../includes/footer.jsp"%>
​
<script>
	$(function() {
		var result = '<c:out value="${result}" />';
		checkModal(result);
		history.replaceState({}, null, null);

		function checkModal(result) {
			if (result === '' || history.state) {
				return;
			}
			if (parseInt(result) > 0) {
				$(".modal-body").html(
						"게시글 " + parseInt(result) + " 번이 등록되었습니다.");
			}

			$("#myModal").modal("show");
		}

		/* -- 추가 -------------------------- */
		$("#regBtn").on("click", function() {
			self.location = "/board/register";
		});

		var actionForm = $("#actionForm");

		$(".page-item a").on("click", function(e) {
			e.preventDefault(); //전송을 막음
			console.log('click');
			actionForm.find("input[name='pageNum']").val($(this).attr("href"));
			actionForm.submit();
		});
		
		$(".move").on("click",function(e){
			e.preventDefault();
			actionForm
				.append("<input type='hidden' name='bno' value='"
					+ $(this).attr("href") + "'>");
			actionForm.attr("action", "/board/get");
			actionForm.submit();

		});
	});
</script>

