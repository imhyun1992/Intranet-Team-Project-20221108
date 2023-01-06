<%@page import="com.tjoeun.vo.EmpList"%>
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
		<c:if test="${deptno == 500}"><c:set var="dname" value="경영지원부"></c:set></c:if>
		<c:if test="${deptno == 400}"><c:set var="dname" value="IT부"></c:set></c:if>
		<c:if test="${deptno == 300}"><c:set var="dname" value="상품개발부"></c:set></c:if>
		<c:if test="${deptno == 200}"><c:set var="dname" value="마케팅부"></c:set></c:if>
		<c:if test="${deptno == 100}"><c:set var="dname" value="영업부"></c:set></c:if>
			
        <div class="con_middle">
            <div class="nav">
                <ul>
					<li><a href="${path}"><img src="${path}/images/home.png" alt="home" width="18px"></a>&#62;</li>
					<li><a href="${path}/groupchart_main">조직도</a>&#62;</li>
                    <li>${dname}</li>
                </ul>
            </div>
            <!-- =================================contents================================================= -->
            <fmt:requestEncoding value="UTF-8"/>
            <c:set var="Elists" value="${EmpList.list}"></c:set>
			
            <div class="content_view">

                <div class="address_view">
					<table>	
						<thead>
							<tr>
								<th width="120">사원번호</th>
			           	 		<th width="100">이름</th>
			           	 		<th width="180">직급</th>
			           	 		<th width="250">직통번호</th>
								<th width="250">E-mail</th>						
							</tr>
						</thead>
						<c:if test="${Elist.size() == 0}">					
							<tr>
								<td colspan="5" style="text-align: center;">주소록이 없습니다.</td>
							</tr>
						</c:if>

						<c:if test="${Elist.size() != 0}">					
							<c:forEach var="Elist" items="${Elists}">							
								<tr>
									<td>${Elist.empno}</td>
									<td>${Elist.name}</td>
									<td>${Elist.position}</td>
									<td>${Elist.incnum}</td>
									<td>${Elist.email}</td>
								</tr>
							</c:forEach>
						</c:if>
						
					</table>
				</div>
				
				<!-- 페이지 이동 -->
                <table class="pagebutton" align="center" border="0" cellpadding="0" cellspacing="0" height="30">
					<tr>		
						<c:if test="${EmpList.currentPage > 1 }">
							<td><button type="button" title="첫 페이지로" onclick="location.href='?currentPage=1&deptno=${deptno}'"> 처음 </button></td>
						</c:if>
						<c:if test="${EmpList.currentPage <= 1 }">
							<td><button type="button" title="이미 첫 페이지입니다" disabled="disabled"> 처음 </button></td>
						</c:if>
						<c:if test="${EmpList.startPage > 1 }">
							<td><button type="button" title="10페이지 이동" onclick="location.href='?currentPage=${EmpList.currentPage - 10}&deptno=${deptno}'"> << </button></td>
						</c:if>
						<c:if test="${EmpList.startPage <= 1 }">
							<td><button type="button" title="이미 첫 페이지입니다" disabled="disabled"> << </button></td>
						</c:if>
						<c:if test="${EmpList.currentPage > 1}">
							<td><button type="button" title="전 페이지로" onclick="location.href='?currentPage=${EmpList.currentPage - 1}&deptno=${deptno}'"> < </button></td>
						</c:if>
						<c:if test="${EmpList.currentPage <= 1}">
							<td><button type="button" title="이미 전 페이지 입니다." disabled="disabled"> < </button></td>
						</c:if>
			 			
						<c:forEach var="i" begin="${EmpList.startPage}" end="${EmpList.endPage}">
							<c:if test="${i == EmpList.currentPage}">
								<td width='30' align='center' style='background: #D8D2CB; border:1px;'>${i}</td>
							</c:if>
							<c:if test="${i != EmpList.currentPage}">
								<td class='tda' width='30' align='center'><a href='board_list?currentPage=${i}&deptno=${deptno}'>${i}</a></td>
							</c:if>
						</c:forEach>	
						
						<c:if test="${EmpList.currentPage < EmpList.totalPage}">
							<td><button type="button" title="다음 페이지로" onclick="location.href='?currentPage=${EmpList.currentPage+1}&deptno=${deptno}'"> > </button></td>
						</c:if>
						<c:if test="${EmpList.currentPage >= EmpList.totalPage}">
							<td><button type="button" title="이미 마지막 페이지 입니다." disabled="disabled"> > </button></td>
						</c:if>
						<c:if test="${EmpList.endPage < EmpList.totalPage}">
							<td><button type="button" title="10페이지 이동" onclick="location.href='?currentPage=${EmpList.currentPage + 10}&deptno=${deptno}'"> >> </button></td>
						</c:if>
						<c:if test="${EmpList.endPage >= EmpList.totalPage}">
							<td><button type="button" title="이미 마지막 페이지입니다" disabled="disabled"> >> </button></td>
						</c:if>
						<c:if test="${EmpList.currentPage < EmpList.totalPage}">
							<td><button type="button" title="마지막 페이지로" onclick="location.href='?currentPage=${EmpList.totalPage}&deptno=${deptno}'"> 끝 </button></td>
						</c:if>
						<c:if test="${EmpList.currentPage >= EmpList.totalPage}">
							<td><button type="button" title="이미 마지막 페이지입니다" disabled="disabled"> 끝 </button></td>
						</c:if>
										
					</tr>
					
				</table>
				
				<hr/>
                <div align="right">
                	<input type="button" value="돌아가기" onclick="history.back()"/>
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