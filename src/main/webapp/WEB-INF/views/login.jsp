<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- jstl 사용 선언 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="path" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
	    <title>1조 팀프로젝트</title>
	    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no" />
	    <link rel="icon" href="${path}/images/apple.png">
	
	    <link rel="stylesheet" href="${path}/css/tjoeun_login.css">	
   	    
	</head>

	<body class="is-preload">
	
		<c:if test="${alertt != null}">
	    	<script type="text/javascript">alert('${alertt}')</script> 
	    	<% session.setAttribute("alertt", null); %>
	    </c:if>

		<!-- Wrapper -->
		<div class="wrapper">

			<!-- Main -->
			<section class="main">
				<h1>TJ INTRANET</h1><br>
				<form method="post" action="login_confirm">
					<div class="fields">

						<div class="field">
							<input type="text" name="empno" id="empno" placeholder="사원번호를 입력해주세요" />
						</div>
						
						<div class="field">
							<input type="password" name="password" id="password" placeholder="비밀번호를 입력해주세요" />
						</div>

						</hr>

						<div class="field">
							<button type = "submit">Login</a>
							<button type = "button" onclick="location.href='${path}/register'">Join us</button>
						</div>			
		
					</div>	

					<a href="#">아이디</a><h7>·</h7><a href="#">비밀번호 찾기</a>
				</form>
			
				</hr>
				
			</section>

			<!-- Footer -->
			<footer class="footer">
				<ul class="copyright">
					<li>&copy; 1조 박혜림 강동현 임현성 선예은</li>
				</ul>
			</footer>

		</div>

		<!-- Scripts -->
		<script>
			if ('addEventListener' in window) {
				window.addEventListener('load', function() { document.body.className = document.body.className.replace(/\bis-preload\b/, ''); });
				document.body.className += (navigator.userAgent.match(/(MSIE|rv:11\.0)/) ? ' is-ie' : '');
			}
		</script>
	</body>
</html>