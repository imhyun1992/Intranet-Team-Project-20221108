<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../includes/head.jsp"%>
<link rel="stylesheet" href="${path}/css/approvalPage.css">
<link rel="stylesheet" href="${path}/css/approvalStyle.css">
</head>

<body>
	<!-- haeder -->
	<%@ include file="../includes/header.jsp"%>

	<!-- contents start -->
	<div class="tjcontainer">

		<!-- menu list -->
		<%@ include file="../includes/menu_bar.jsp"%>

		<!-- main -->
		<div class="con_middle">
			<div class="nav">
				<ul>
					<li><a href="${path}/approval/approvalMain"><img
							src="../images/home.png" alt="home" width="18px"></a>&#62;</li>
					<li><a href="${path}/approval/approvalMain">전자결재</a>&#62;</li>
					<li><a href="${path}/approval/paging">결재목록</a></li>
				</ul>
			</div>
			<!-- =================================contents================================================= -->
			<fmt:requestEncoding value="UTF-8" />
			<c:set var="view" value="${paging.list}"/>

			<div class="content">
				<div align="right" style="margin-bottom: 15px">
				</div>
				<!-- 반복문 -->
				<div class="list_view">
					<table style="width: 900px; margin: auto; height: 30px;">
						<thead>
							<tr style="text-align: center;">
								<th width="50">번호</th>
								<th width="170">종류</th>
								<th width="200">제목</th>
								<th width="100">기안자</th>
								<th width="130">부서</th>
								<th width="120">기안일</th>
								<th width="120">결재상태</th>
							</tr>
						</thead>

						<tbody>
							<c:if test="${empty mainList}">
								<tr>
									<td colspan="4"><marquee>조회된 결재목록이 없습니다.</marquee></td>
								</tr>
							</c:if>
							<c:if test="${mainList != null}">
								<c:forEach var="list" items="${mainList}">
									<tr>
										<!--
										${list} 
										 -->
										<td>${list.appNo}</td>
										<c:choose>
											<c:when test="${list.appKinds eq '품의서'}">
												<td><a
													href="${path}/approval/letterOfApprovalView?appNo=${list.appNo}">${list.appKinds}</a></td>
											</c:when>
											<c:when test="${list.appKinds eq '휴가신청서'}">
												<td><a
													href="${path}/approval/leaveApplicationView?appNo=${list.appNo}">${list.appKinds}</a></td>
											</c:when>
											<c:when test="${list.appKinds eq '지출결의서'}">
												<td><a
													href="${path}/approval/expenseReportView?appNo=${list.appNo}">${list.appKinds}</a></td>
											</c:when>
										</c:choose>
										<c:choose>
											<c:when test="${list.appKinds eq '품의서'}">
												<td><a style="color: black"
													href="${path}/approval/letterOfApprovalView?appNo=${list.appNo}">${list.loaTitle}</a></td>
											</c:when>
											<c:when test="${list.appKinds eq '휴가신청서'}">
												<td><a style="color: black"
													href="${path}/approval/leaveApplicationView?appNo=${list.appNo}">${list.leaveClassify}</a></td>
											</c:when>
											<c:when test="${list.appKinds eq '지출결의서'}">
												<td><a style="color: black"
													href="${path}/approval/expenseReportView?appNo=${list.appNo}">${list.erTitle}</a></td>
											</c:when>
										</c:choose>
										<td>${list.userName}</td>
										<td>${deptname}</td>
										<td><fmt:formatDate value="${list.appWriteDate}"
												pattern="yyyy/MM/dd" /></td>
										<td>${list.appCheckProgress}</td>
									</tr>
								</c:forEach>
							</c:if>
						</tbody>
					</table>
				</div>

				<!-- 페이지 이동 -->
				<table class="pagebutton" align="center" border="0" cellpadding="0"
					cellspacing="0" height="30">
					<tr>
						<!-- ${paging} -->
						<c:if test="${paging.currentPage > 1 }">
							<td><button type="button" title="첫 페이지로"
									onclick="location.href='?currentPage=1&'">
									처음</button></td>
						</c:if>
						<c:if test="${paging.currentPage <= 1 }">
							<td><button type="button" title="이미 첫 페이지입니다"
									disabled="disabled">처음</button></td>
						</c:if>
						<c:if test="${paging.startPage > 1 }">
							<td><button type="button" title="10페이지 이동"
									onclick="location.href='?currentPage=${paging.currentPage - 10}'">
									&lt;&lt;</button></td>
						</c:if>
						<c:if test="${paging.startPage <= 1 }">
							<td><button type="button" title="이미 첫 페이지입니다"
									disabled="disabled"><<</button></td>
						</c:if>
						<c:if test="${paging.currentPage > 1}">
							<td><button type="button" title="전 페이지로"
									onclick="location.href='?currentPage=${paging.currentPage - 1}'">
									<</button></td>
						</c:if>
						<c:if test="${paging.currentPage <= 1}">
							<td><button type="button" title="이미 전 페이지 입니다."
									disabled="disabled"><</button></td>
						</c:if>

						<c:forEach var="i" begin="${paging.startPage}"
							end="${paging.endPage}">
							<c:if test="${i == paging.currentPage}">
								<td width='30' align='center'
									style='background: #D8D2CB; border: 1px;'>${i}</td>
							</c:if>
							<c:if test="${i != paging.currentPage}">
								<td class='tda' width='30' align='center'><a
									href='/approval/approvalList?currentPage=${i}'>${i}</a></td>
							</c:if>
						</c:forEach>

						<c:if test="${paging.currentPage < paging.totalPage}">
							<td><button type="button" title="다음 페이지로"
									onclick="location.href='?currentPage=${paging.currentPage+1}'">
									></button></td>
						</c:if>
						<c:if test="${paging.currentPage >= paging.totalPage}">
							<td><button type="button" title="이미 마지막 페이지 입니다."
									disabled="disabled">></button></td>
						</c:if>
						<c:if test="${paging.endPage < paging.totalPage}">
							<td><button type="button" title="10페이지 이동"
									onclick="location.href='?currentPage=${paging.currentPage + 10}'">
									>></button></td>
						</c:if>
						<c:if test="${paging.endPage >= paging.totalPage}">
							<td><button type="button" title="이미 마지막 페이지입니다"
									disabled="disabled">>></button></td>
						</c:if>
						<c:if test="${paging.currentPage < paging.totalPage}">
							<td><button type="button" title="마지막 페이지로"
									onclick="location.href='?currentPage=${paging.totalPage}'">
									끝</button></td>
						</c:if>
						<c:if test="${paging.currentPage >= paging.totalPage}">
							<td><button type="button" title="이미 마지막 페이지입니다"
									disabled="disabled">끝</button></td>
						</c:if>

					</tr>

				</table>

			</div>
			<!-- =================================contents================================================= -->

		</div>
		<!-- main -->

		<!-- right -->
		<%@ include file="../includes/con_right.jsp"%>

	</div>
	<!-- contents end -->

	<!-- footer -->
	<%@ include file="../includes/footer.jsp"%>

	<!-- 일정 등록 Modal -->
	<%@ include file="../includes/todoModal.jsp"%>

</body>

</html>