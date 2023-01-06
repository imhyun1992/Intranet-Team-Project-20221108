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
                   	<li><a href="board_list?category=자료실">게시판</a>&#62;</li>
                    <li><a href="board_list?category=자료실">자료실</a></li>
                </ul>
            </div>
            <!-- =================================contents================================================= -->
            <fmt:requestEncoding value="UTF-8"/>
            <c:set var="vo" value="${BoardVO}"></c:set>
            
        	<c:set var="content" value="${fn:replace(vo.content, '<', '&lt;') }"></c:set>
			<c:set var="content" value="${fn:replace(vo.content, '>', '&gt;') }"></c:set>
			<c:set var="content" value="${fn:replace(vo.content, enter, '<br/>') }"></c:set>			
			
			<c:if test="${vo.deptno == 500}"><c:set var="dname" value="경영지원부"></c:set></c:if>
			<c:if test="${vo.deptno == 400}"><c:set var="dname" value="IT부"></c:set></c:if>
			<c:if test="${vo.deptno == 300}"><c:set var="dname" value="상품개발부"></c:set></c:if>
			<c:if test="${vo.deptno == 200}"><c:set var="dname" value="마케팅부"></c:set></c:if>
			<c:if test="${vo.deptno == 100}"><c:set var="dname" value="영업부"></c:set></c:if>
			
            <div class="content_view">
										
	         	<h1>${vo.title}</h1>
				<h5><fmt:formatDate value="${vo.writedate}" pattern = "yyyy-MM-dd aa h시 mm분"/></h5>
	         	<div style="display: flex; line-height: 20px">
					<h5 style="width: 95%">${vo.name}&lpar;${dname}&rpar;</h5>
					<div style="display: flex ">
						<img src="./images/thums.png" alt="thums" width="14px" height="14px" style="padding: 3px">
						<h5>${vo.hit}</h5>
					</div>
				</div>
				<hr/>
				<div style="padding: 2px 10px; height: 50%">	
					<div style="height: 100%">
						<div style="height : 95%">
							<p>${content}</p>
						</div>
						<c:if test="${vo.attachedfile != null}">
							<div class="attach">
								<img alt="" src="./images/clip.png" width="15x" style="margin: 4px 1px">
								<a href="downfile?category=자료실&idx=${vo.idx}&currentPage=${currentPage}">${vo.attachedfile}</a>
							</div>
						</c:if>
					</div>
				</div>
				<div align="right">
	             	<c:if test="${vo.name == EmpVO.name}">
	                	<input type="button" value="수정" onclick="location.href='board_update?idx=${vo.idx}&currentPage=${currentPage}'"/>
	                	<input type="button" value="삭제" onclick="location.href='board_delete?idx=${vo.idx}&currentPage=${currentPage}&category=자료실'"/>
	                </c:if>
	            </div>
                <hr/>
				<div align="right">
					<input type="button" value="목록" onclick="location.href='board_list?currentPage=${currentPage}&category=자료실'"/>
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