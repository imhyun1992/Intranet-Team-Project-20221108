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
			<c:set var="vo" value="${ReportVO}"></c:set>

			<c:set var="content" value="${fn:replace(vo.content, '<', '&lt;') }"></c:set>
			<c:set var="content" value="${fn:replace(vo.content, '>', '&gt;') }"></c:set>
			<c:set var="content" value="${fn:replace(vo.content, enter, '<br/>') }"></c:set>

			<div class="content">

				<h1>${vo.title}</h1>
				<h5> <fmt:formatDate value="${vo.writedate}" pattern="yyyy-MM-dd(E), aa h시 mm분" /> </h5>
				<hr />
				<p style="width: 100%; height: 500px">${vo.content}</p>
				<td>첨부파일 : <a href="Downloard?filename=${vo.attachedfile}">${vo.attachedfile}</a>
				</td> <hr />

				<div align="right">
					
					<c:if test="${vo.name == EmpVO.name}">
						<input type="button" value="수정" onclick="location.href='report_service?mode=2&idx=${vo.idx}&currentPage=${currentPage}'" />
						<input type="button" value="삭제" onclick="location.href='report_service?mode=3&idx=${vo.idx}'" />
					</c:if>
					<input type="button" value="돌아가기" onclick="location.href='report_list?currentPage=${currentPage}'" />
				</div>
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