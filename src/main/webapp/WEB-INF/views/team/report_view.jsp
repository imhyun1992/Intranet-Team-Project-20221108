<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="../includes/head.jsp" %>
</head>

<body>

	<!-- haeder -->
	<%@ include file="../includes/header.jsp" %>

	<!-- contents start -->
	<div class="tjcontainer">
	
		<!-- menu list -->	
		<%@ include file="../includes/menu_bar.jsp" %>
		
		<!-- main -->
		<div class="con_middle">
			<div class="nav">
				<ul>
					<li><a href="${path}/main"><img src="${path}/images/home.png" alt="home" width="18px"></a>&#62;</li>
					<li><a href="#">팀 커뮤니티</a>&#62;</li>
					<li><a href="#">업무 보고</a></li>
				</ul>
			</div>
			<!-- =================================contents================================================= -->
			<fmt:requestEncoding value="UTF-8" />
			<c:set var="view" value="${ReportList.list}"></c:set>
			
			<div class="content_view">
				<h1>업무 보고</h1>
				<hr />
				<div style="height: 550px;">
					<table style="width: 900px; margin: auto; height: 30px;">
						<thead>
							<tr style="background-color: #D8D2CB;">
								<th style="width: 200px; font-size: 16x;" align="center">보고일</th>
								<th style="width: 200px; font-size: 16x;" align="center">보고서</th>
								<th style="width: 330px; font-size: 16x;" align="center">제목</th>
								<th style="width: 200px; font-size: 16x;" align="center">작성자</th>
							</tr>
						</thead>
						<!--=========================== 글 출력 =====================================-->
						<c:if test="${view.size() == 0}">
							<tr>
								<td colspan="4" style="text-align: center;">저장된 글이 없습니다.</td>
							</tr>
						</c:if>
						<c:if test="${view.size() != 0}">
							<jsp:useBean id="date" class="java.util.Date" />
							<!-- 오늘 날짜 기억하는 Date 클래스 객체를 useBean으로 만든다. -->
							<c:forEach var="vo" items="${view}">
								<tr>
									<td style="text-align: center;">
										<fmt:formatDate value="${vo.writedate}" pattern="yyyy.MM.dd(E)" />
									</td>
									<td style="text-align: center;">${vo.type}</td>

									<td style="text-align: center;">
										<a href="report_content_list?idx=${vo.idx}&currentPage=${currentPage}">${vo.title}</a>
									</td>

									<td style="text-align: center;">${vo.name}</td>
								</tr>
							</c:forEach>
						</c:if>
					</table>
				</div>
				<!-- ============================= 글쓰기 버튼 ============================================= -->
				<table style="width: 900px; margin: auto;">
					<tr>
						<td colspan="4" align="right"><input type="button" value="글쓰기" onclick="location.href='report_insert'"/></td>
					</tr>
				</table>

				<!--===========================페이지 이동 버튼 ====================================-->
				<table class="pagebutton" align="center" border="0" cellpadding="0"
					cellspacing="0" height="30">
					<tr>
						<!-- 처음으로 -->
						<c:if test="${ReportList.currentPage > 1}">
							<td><button class="button1" type="button" title="첫 페이지로."
									onclick="location.href='?currentPage=1'">처음</button></td>
						</c:if>
						<c:if test="${ReportList.currentPage <= 1}">
							<td><button class="button1_1" type="button"
									disabled="disabled" title="현재 페이지입니다.">처음</button></td>
						</c:if>

						<!-- 10페이지 앞으로 -->
						<c:if test="${ReportList.startPage > 1}">
							<td><button class="button1" type="button"
									title="이전 10페이지로 이동합니다."
									onclick="location.href='?currentPage=${ReportList.startPage-1}'">이전</button></td>
						</c:if>
						<c:if test="${ReportList.startPage <= 1}">
							<td><button class="button1_1" type="button"
									title="이미 첫 10페이지 입니다." disabled="disabled">이전</button></td>
						</c:if>

						<!-- 페이지 이동 버튼 -->
						<c:forEach var="i" begin="${ReportList.startPage}"
							end="${ReportList.endPage}" step="1">
							<c:if test="${ReportList.currentPage == i}">
								<td><button type="button" disabled="disabled"
										class="button2">${i}</button></td>
							</c:if>
							<c:if test="${ReportList.currentPage != i}">
								<td><button class="button2" type="button"
										onclick="location.href='?currentPage=${i}'">${i}</button></td>
							</c:if>
						</c:forEach>

						<!-- 10페이지 뒤로 -->
						<c:if test="${ReportList.endPage < ReportList.totalPage}">
							<td><button class="button1" type="button"
									title="다음 10페이지로 이동합니다."
									onclick="location.href='?currentPage=${ReportList.endPage + 1}'">다음</button></td>
						</c:if>
						<c:if test="${ReportList.endPage >= ReportList.totalPage}">
							<td><button class="button1_1" type="button"
									title="이미 마지막 10페이지 입니다." disabled="disabled">다음</button></td>
						</c:if>

						<!-- 마지막으로 -->
						<c:if test="${ReportList.currentPage < ReportList.totalPage}">
							<td><button class="button1" type="button"
									title="마지막 페이지로 이동합니다."
									onclick="location.href='?currentPage=${ReportList.totalPage}'">마지막</button></td>
						</c:if>
						<c:if test="${ReportList.currentPage >= ReportList.totalPage}">
							<td><button type="button" disabled="disabled"
									title="이미 마지막 10페이지 입니다." class="button1_1">마지막</button></td>
						</c:if>
					</tr>
				</table>
			</div>
			<!-- =================================contents================================================= -->
		
		</div>
		<!-- main -->
		
		<!-- right -->
		<%@ include file="../includes/con_right.jsp" %>
		
	</div>
	<!-- contents end -->
	
	<!-- footer -->
	<%@ include file="../includes/footer.jsp" %>

	<!-- 일정 등록 Modal -->
	<%@ include file="../includes/todoModal.jsp" %>

</body>

</html>