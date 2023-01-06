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
                    <li><a href="#">게시판</a>&#62;</li>
                    <li><a href="#">자유게시판</a></li>
                </ul>
            </div>
            <!-- =================================contents================================================= -->
            <fmt:requestEncoding value="UTF-8"/>
            <c:set var="vo" value="${ReportVO}"></c:set>
            
        	<c:set var="content" value="${fn:replace(vo.content, '<', '&lt;') }"></c:set>
			<c:set var="content" value="${fn:replace(vo.content, '>', '&gt;') }"></c:set>			
			
            <div class="content">
				<form action="report_service?idx=${vo.idx}&currentPage=${currentPage}&mode=4" method="post">					
		         	<h1>${vo.title}</h1>
					<h5><fmt:formatDate value="${vo.writedate}" pattern = "yyyy-MM-dd(E), aa h시 mm분"/></h5>
					<hr/>	
					<textarea id="content" name="content" style="resize: none; width: 900px" rows="14" >${content}</textarea>
					<br/>
	                <input type="reset" value="다시쓰기"/>
					<input type="submit" value="저장하기"/>
	                <hr/>
	                <input type="button" value="돌아가기" onclick="history.back()"/>
	            </form>		  
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