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
                    <li><a href="${path}"><img src="${path}/images/home.png" alt="home" width="18px"></a>&#62;</li>
                    <li><a href="${path}/groupchart_main">조직도</a></li>
                </ul>
            </div>
            <!-- =================================contents================================================= -->
            <fmt:requestEncoding value="UTF-8"/>			
            <div class="content" style="background-image:url('./images/line.png')">
                
                <div align="center" style="margin: 20px; padding: 10px">
                	<div align="center" style="border: 1px solid black; width: 20%; height: 70px;">
                		<h1 style= "line-height: 70px ">대표이사</h1>
                	</div>
                </div>
                
                <div style="display: flex;">
                
                	<div align="center" style="width: 33%; margin: 20px;">
                		<div align="center" style="border: 1px solid black; width: 60%; height: 40px; margin: 20px;">
	                		<h3 style="line-height: 40px ">부장</h3>
	                	</div> 
	                	
	                	<div style="display: flex;">
	                		<div align="center" style="width: 50%;">
	                			<div class="groupmove" style="width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px "><a href="groupchart_view?deptno=100">영업부</a></h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">팀장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">차장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">과장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">대리</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">사원</h3>
		                		</div>
		                		
		                	</div> 

		                	<div align="center" style="width: 50%; ">
	                			<div class="groupmove" style="width: 80%; margin: 15px">
		                			<h3 style= "line-height: 40px "><a href="groupchart_view?deptno=200">마케팅부</a></h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">팀장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">차장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">과장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">대리</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">사원</h3>
		                		</div>
		                		
		                	</div> 
			                 
	                	</div>                           	
                	</div>
                	
                	<div align="center" style="width: 33%; margin: 20px;">             		
                        <div align="center" style="border: 1px solid black; width: 60%; height: 40px; margin: 20px;">
	                		<h3 style="line-height: 40px ">부장</h3>
	                	</div> 
	                	
	                	<div style="display: flex;">
	                		<div align="center" style="width: 50%;">
	                			<div class="groupmove" style="width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px "><a href="groupchart_view?deptno=300">상품개발부</a></h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">팀장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">차장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">과장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">대리</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">사원</h3>
		                		</div>
		                		
		                	</div> 

		                	<div align="center" style="width: 50%; ">
	                			<div class="groupmove" style="width: 80%; margin: 15px">
		                			<h3 style= "line-height: 40px "><a href="groupchart_view?deptno=400">IT부</a></h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">팀장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">차장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">과장</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">대리</h3>
		                		</div>
		                		
		                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
		                			<h3 style= "line-height: 40px ">사원</h3>
		                		</div>
		                		
		                	</div>	                 
	                	</div>           	
                	</div>
                	
            	    <div align="center" style="width: 33%; margin: 20px;">
                		<div align="center" style="border: 1px solid black; width: 60%; height: 40px; margin: 20px 20px 35px 20px;">
	                		<h3 style="line-height: 40px ">전무</h3>
	                	</div> 
	                	
                		<div align="center" style="width: 50%;">
                			<div class="groupmove" style="width: 80%; height: 40px; margin: 15px">
	                			<h3 style= "line-height: 40px "><a href="groupchart_view?deptno=500">경영지원부</a></h3>
	                		</div>
	                		
	                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
	                			<h3 style= "line-height: 40px ">팀장</h3>
	                		</div>
	                		
	                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
	                			<h3 style= "line-height: 40px ">차장</h3>
	                		</div>
	                		
	                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
	                			<h3 style= "line-height: 40px ">과장</h3>
	                		</div>
	                		
	                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
	                			<h3 style= "line-height: 40px ">대리</h3>
	                		</div>
	                		
	                		<div style="border: 1px solid black; width: 80%; height: 40px; margin: 15px">
	                			<h3 style= "line-height: 40px ">사원</h3>
	                		</div>
	                		
	                	</div> 	                
                           	
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
	<%@ include file="../includes/todoModal.jsp" %>

</body>

</html>