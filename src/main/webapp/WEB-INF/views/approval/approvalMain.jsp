<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!-- jstl 사용 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="path" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>1조 팀프로젝트</title>
<%@ include file="../includes/head.jsp"%>
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
					<li><a href="${path}/approval/approvalMain">전자결재 HOME</a></li>
				</ul>
			</div>
			<!-- =================================contents================================================= -->
			<fmt:requestEncoding value="UTF-8" />
			
			<div class="content_view">
				<div style="text-align: left;">
					<h4>결재현황</h4>
				</div>
				<div class="row">
					<div class="col-sm-6">
						<div class="card">
							<div class="card-body">
								<button type="button" class="btn btn-info position-relative"
									onclick="location.href='${path}/approval/approvalList?approvalStatus=결재대기&userNo=${EmpVO.empno}'">결재대기</button>
								<p class="card-text">${countYet}건</p>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="card">
							<div class="card-body">
								<button type="button" class="btn btn-danger position-relative"
									onclick="location.href='${path}/approval/approvalList?approvalStatus=결재중&userNo=${EmpVO.empno}'">결재중</button>
								<p class="card-text">${countUnder}건</p>
							</div>
						</div>
					</div>
					<div class="col-sm-6">
						<div class="card">
							<div class="card-body">
								<button type="button" class="btn btn-success position-relative"
									onclick="location.href='${path}/approval/approvalList?approvalStatus=결재완료&userNo=${EmpVO.empno}'">결재완료</button>
								<p class="card-text">${countDone}건</p>
							</div>
						</div>
					</div>
				</div>
				<hr />
				<!-- 목록view -->
				<div style="text-align: left; height: auto;">
					<h6	style="float: right;">
						<a href="${path}/approval/approvalList">+ 결재목록 전체보기</a>
					</h6>
					<h4>결재 수신목록</h4>
				</div>
				<form action="">
					<table>
						<c:if test="${empty mainList}">
						</table>
							<tr><td colspan="4"><marquee>조회된 결재목록이 없습니다.</marquee></td></tr>
						</c:if>

						<c:if test="${mainList != null}">
							<c:forEach var="list" items="${mainList}">
								<tr>
									<td>${list.rowNum}</td>
									<c:choose>
										<c:when test="${list.appKinds eq '품의서'}">
											<td><a style="color: black"
												href="${path}/approval/letterOfApprovalView?appNo=${list.appNo}">${list.appKinds}</a></td>
										</c:when>
										<c:when test="${list.appKinds eq '휴가신청서'}">
											<td><a style="color: black"
												href="${path}/approval/leaveApplicationView?appNo=${list.appNo}">${list.appKinds}</a></td>
										</c:when>
										<c:when test="${list.appKinds eq '지출결의서'}">
											<td><a style="color: black"
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
									<td>${list.deptName}</td>
									<td><fmt:formatDate value="${list.appWriteDate}"
											pattern="yyyy/MM/dd" /></td>
									<td>${list.appCheckProgress}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</form>

				<hr/>

				<div class="myDraftDocumentArea">
					<h4>내가 작성한 결재 목록</h4>
					<table>
						<c:if test="${empty mainList1}">
					</table>
					<tr><td colspan="4"><marquee>조회된 결재목록이 없습니다.</marquee></td></tr>
					</c:if>

					<c:if test="${mainList1 != null}">
						<c:forEach var="list" items="${mainList1}">
							<tr>
								<td>${list.rowNum}</td>
								<c:choose>
									<c:when test="${list.appKinds eq '품의서'}">
										<td><a style="color: black"
											   href="${path}/approval/letterOfApprovalView?appNo=${list.appNo}">${list.appKinds}</a></td>
									</c:when>
									<c:when test="${list.appKinds eq '휴가신청서'}">
										<td><a style="color: black"
											   href="${path}/approval/leaveApplicationView?appNo=${list.appNo}">${list.appKinds}</a></td>
									</c:when>
									<c:when test="${list.appKinds eq '지출결의서'}">
										<td><a style="color: black"
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
								<td>${list.deptName}</td>
								<td><fmt:formatDate value="${list.appWriteDate}" pattern="yyyy/MM/dd" /></td>
								<td>${list.appCheckProgress}</td>
							</tr>
						</c:forEach>
					</c:if>
					</table>
				</div>

			</div>
		</div>
		<!-- right -->
		<%@ include file="../includes/con_right.jsp"%>

	</div>

	<!-- footer -->
	<%@ include file="../includes/footer.jsp"%>

	<!-- 일정 등록 Modal -->
	<%@ include file="../includes/todoModal.jsp"%>

</body>
</html>