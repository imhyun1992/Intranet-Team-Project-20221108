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
    
		<!--menu list-->
		<%@ include file="./manager_menu_bar.jsp" %>
        
        <!-- main -->
        <div class="con_middle">

            <!-- =================================contents================================================= -->
            <div class="Manager_Main" align="center">   
            
            	<!-- 공지사항 구역 -->
            	<div>
            		<div>
            			<span class="Marking">공지사항</span>
            			<jsp:useBean id="date" class="java.util.Date"/>
						<fmt:formatDate value="${date}" pattern = "yyyy-MM-dd(E)" var ="today"/>
            		</div>
            		<div>
						<table width ="100%">
							<c:forEach var="notice" items="${noticeList.list}" end="5">
								<fmt:formatDate value="${notice.writedate}" pattern = "yyyy-MM-dd(E)" var="writedate"/>
								<tr onclick="location.href='board_view?idx=${notice.idx}&currentPage=1'">
									<td width="70px">${notice.name}</td>
									<td align="left">
										<c:if test="${today eq writedate}">
											<img src="${path}/images/new.png" alt="thums" width="30px">
										</c:if> 
										${notice.title}
									</td>
									<td align="right">${writedate}</td>
								</tr>
							</c:forEach>
						</table>
            		</div>
            	</div>
            	
            	<!-- 계정 승인 대기 목록 구역 -->
            	<div onclick="location.href='account_approval'">
            		<div>
            			<span class="Marking">계정 승인 대기 목록</span>
           				<c:if test="${waitingList.list.size() != 0}">
    	        			<p>
            					${waitingList.totalCount}명
	            			</p>
           				</c:if>
            		</div>
            		<div>
            			<table width = "100%">
            				<c:if test="${waitingList.list.size() == 0}">
								<tr>
									<td colspan="7" style="text-align: center;">대기 목록이 없습니다.</td>
								</tr>
							</c:if>
	            			<c:forEach var="waitlist" items="${waitingList.list}" end="5">
	            				<tr>
	            					<td>${waitlist.empno}</td>
	            					<td>${waitlist.name}</td>
	            					<td>${waitlist.pernum}</td>
	            					<td>${waitlist.email}</td>
	            				</tr>
	            			</c:forEach>
	            		</table>
            		</div>
            	</div>
            	
            	<!-- 결재 대기 목록 구역 -->
            	<div>
            		<div>
            			<span class="Marking">결재 대기 목록</span>
            		</div>
            		<div>
            		
            		</div>
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
	<%@ include file="../includes/insertTodoModal.jsp" %>

</body>

</html>