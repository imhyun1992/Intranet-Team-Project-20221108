<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../includes/head.jsp"%>
<link rel="stylesheet" href="${path}/css/approvalStyle.css">
<link rel="stylesheet" href="${path}/css/expenseReport.css">
</head>

<body>
	<!-- header -->
	<%@ include file="../includes/header.jsp"%>

	<!-- contents start -->
	<div class="tjcontainer">

		<!-- menu list -->
		<%@ include file="../includes/menu_bar.jsp"%>
		<div class="con_middle">
			<div class="nav">
				<ul>
					<li><a href="${path}/approval/approvalMain"><img
							src="../images/home.png" alt="home" width="18px"></a>&#62;</li>
					<li><a href="${path}/approval/approvalMain">전자결재</a>&#62;</li>
					<li><a href="${path}/approval/expenseReportView">지출결의서 수신</a></li>
				</ul>
			</div>
			<div class="cash-form-section">
				<div class="cash-disbursement"
					style="text-align: center; border: 2px solid black;">
					<table style="width: 100%; font-size: 20px; border-collapse: collapse;">
						<c:if test="${approval.deptName == 500}"><c:set var="dname" value="경영지원부"></c:set></c:if>
						<c:if test="${approval.deptName == 400}"><c:set var="dname" value="IT부"></c:set></c:if>
						<c:if test="${approval.deptName == 300}"><c:set var="dname" value="상품개발부"></c:set></c:if>
						<c:if test="${approval.deptName == 200}"><c:set var="dname" value="마케팅부"></c:set></c:if>
						<c:if test="${approval.deptName == 100}"><c:set var="dname" value="영업부"></c:set></c:if>
					
						<tr>
							<td rowspan="3" colspan="4"
								style="width: 300px; height: 140px; font-size: 40px; font-weight: 600;">지
								출 결 의 서</td>
							<td rowspan="3"
								style="width: 20px; padding-top: 30px; font-size: 25px;">결
								재</td>
							<td style="height: 25px; width: 90px; font-size: 15px">최초승인자</td>
							<td style="height: 25px; width: 90px; font-size: 15px">중간승인자</td>
							<td style="height: 25px; width: 90px; font-size: 15px">최종승인자</td>
						</tr>
						<tr>
							<c:choose>
								<c:when test="${approval.appPresent eq 'A'}">
									<td name="firstA" id="firstA">${approval.firstApprover}</td>
									<td name="interimA" id="interimA">${approval.interimApprover}</td>
									<td name="finalA" id="finalA">${approval.finalApprover}</td>
								</c:when>
								<c:when test="${approval.appPresent eq 'B'}">
									<td name="firstA" id="firstA">${approval.firstApprover}<img
										src="${path}/images/approved.png"
										style="position: absolute; width: 130px; height: 130px; margin-left: -92px; margin-top: -50px" /></td>
									<td name="interimA" id="interimA">${approval.interimApprover}</td>
									<td name="finalA" id="finalA">${approval.finalApprover}</td>
								</c:when>
								<c:when test="${approval.appPresent eq 'C'}">
									<td name="firstA" id="firstA">${approval.firstApprover}<img
										src="${path}/images/approved.png"
										style="position: absolute; width: 130px; height: 130px; margin-left: -92px; margin-top: -50px" /></td>
									<td name="interimA" id="interimA">${approval.interimApprover}<img
										src="${path}/images/approved.png"
										style="position: absolute; width: 130px; height: 130px; margin-left: -92px; margin-top: -50px" /></td>
									<td name="finalA" id="finalA">${approval.finalApprover}</td>
								</c:when>
								<c:when test="${approval.appPresent eq 'D'}">
									<td name="firstA" id="firstA">${approval.firstApprover}<img
										src="${path}/images/approved.png"
										style="position: absolute; width: 130px; height: 130px; margin-left: -92px; margin-top: -50px" /></td>
									<td name="interimA" id="interimA">${approval.interimApprover}<img
										src="${path}/images/approved.png"
										style="position: absolute; width: 130px; height: 130px; margin-left: -92px; margin-top: -50px" /></td>
									<td name="finalA" id="finalA">${approval.finalApprover}<img
										src="${path}/images/approved.png"
										style="position: absolute; width: 130px; height: 130px; margin-left: -92px; margin-top: -50px" /></td>
								</c:when>
								<c:otherwise>
									<td name="firstA" id="firstA">${approval.firstApprover}</td>
									<td name="interimA" id="interimA">${approval.interimApprover}</td>
									<td name="finalA" id="finalA">${approval.finalApprover}</td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr style="height: 30px;">
							<c:choose>
								<c:when
									test="${EmpVO.name eq approval.firstApprover && approval.appPresent eq 'A'}">
									<td><input type="button" name="Approver1" id="Approver1"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" /></td>
									<td><input type="button" name="Approver2" id="Approver2"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
									<td><input type="button" name="Approver3" id="Approver3"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
								</c:when>
								<c:when
									test="${EmpVO.name eq approval.interimApprover && approval.appPresent eq 'B'}">
									<td><input type="button" name="Approver1" id="Approver1"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
									<td><input type="button" name="Approver2" id="Approver2"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" /></td>
									<td><input type="button" name="Approver3" id="Approver3"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
								</c:when>
								<c:when
									test="${EmpVO.name eq approval.finalApprover && approval.appPresent eq 'C'}">
									<td><input type="button" name="Approver1" id="Approver1"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
									<td><input type="button" name="Approver2" id="Approver2"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
									<td><input type="button" name="Approver3" id="Approver3"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" /></td>
								</c:when>
								<c:otherwise>
									<td><input type="button" name="Approver1" id="Approver1"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
									<td><input type="button" name="Approver2" id="Approver2"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
									<td><input type="button" name="Approver3" id="Approver3"
										style="font-size: 15px; width: 70px; height: 25px; border: none; text-align: center; border-radius: 20px;"
										value="결재서명" disabled /></td>
								</c:otherwise>
							</c:choose>
						</tr>
						<tr>
							<td colspan="2" style="height: 70px;">
								<button class="send-open" type="button" disabled>수신참조자
									+</button>
							</td>
							<td colspan="6" style="height: 70px;"><textArea readonly
									name="referList" id="referList"
									style="border: none; margin-bottom: -12px; font-size: 19px; width: 600px; height: 60px; text-align: center; resize: none;">${approval.referList}</textArea>
							</td>
						</tr>
						<tr>
							<td style="height: 70px; width: 80px;">성 명</td>
							<td><input type="text" readonly value="${approval.userName}"></td>
							<td style="width: 80px;">부 서</td>
							<td><input type="text" readonly value="${dname}"></td>
							<td style="width: 80px;">직 급</td>
							<td colspan="3"><input type="text" readonly
								value="${approval.rank}"></td>
						</tr>
						<tr>
							<td colspan="1" style="height: 70px; width: 80px;">지출금액</td>
							<td colspan="3"><input type="text" name="allAmount"
								id="allAmount" readonly value="${approval.allAmount}" /></td>
							<td colspan="2" style="border: 0px"><input type="text"
								name="moneytaryUnit" id="moneytaryUnit" readonly
								value="${approval.moneytaryUnit}" /></td>
						</tr>
						<tr>
							<td style="height: 70px; width: 80px;">제 목</td>
							<td colspan="7"><input type="text" readonly
								value="${approval.erTitle}"></td>
						</tr>
						<tr>
							<td rowspan="10" style="width: 80px;">내 역</td>
							<td colspan="2">적 요</td>
							<td colspan="2">금 액</td>
							<td colspan="3">비 고</td>
						</tr>

						<!-- 적요 -->
						<c:set var="erDetailArr" value="${ approval.erDetail }"></c:set>
						<c:set var="erDeArr" value="${fn:split(erDetailArr,',')}" />

						<!-- 금액  -->
						<c:set var="erAmountArr" value="${ approval.erAmount }"></c:set>
						<c:set var="eAArr" value="${fn:split(erAmountArr,',')}" />

						<!-- 비고  -->
						<c:set var="erReferenceArr" value="${ approval.erReference }"></c:set>
						<c:set var="eRFArr" value="${fn:split(erReferenceArr,',')}" />

						<c:forEach begin="0" end="${fn:length(eRFArr)-1}"
							varStatus="status">
							<tr>
								<td colspan="2"><input type="text"
									style="font-size: 20px; height: 55px"
									value="${erDeArr[status.index]}" readonly></td>
								<td colspan="2"><input type="text"
									style="font-size: 20px; height: 55px"
									value="${eAArr[status.index]}" readonly></td>
								<td colspan="3"><input type="text"
									style="font-size: 20px; height: 55px"
									value="${eRFArr[status.index]}" readonly></td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="8"
								style="text-align: center; height: 100px; border-bottom: none;">위
								금액을 청구하오니 결재바랍니다.</td>
						</tr>
						<tr style="border: white;">
							<td colspan="8" style="text-align: center; height: 100px;">
								<input type="text" style="text-align: center; font-size: 30px;"
								readonly>
							<fmt:formatDate value="${approval.appWriteDate}"
									pattern="yyyy 년 MM 월 dd 일" />
							</td>
						</tr>
						<tr>
							<td colspan="8"
								style="text-align: right; height: 100px; padding-right: 50px;">
								영수인 : <input type="text"
								style="width: 200px; border: none; text-align: center;"
								maxlength="4" readonly value="${approval.userName}"> (인)
							</td>
						</tr>
					</table>
				</div>
				<div id="button">
					<input type="hidden" name="appNo" value="${approval.appNo}" />

						<c:choose>
							<c:when
								test="${(EmpVO.name eq approval.firstApprover && approval.appPresent eq 'A') || 
	        						(EmpVO.name eq approval.interimApprover && approval.appPresent eq 'B') ||
	        						(EmpVO.name eq approval.finalApprover && approval.appPresent eq 'C')}">
								<button type="submit" id="approveddone">결재</button>
								<input type="text" style="border: none; width: 40px;" disabled>
							</c:when>
							<c:otherwise>
								<button type="submit" id="approveddone" disabled>결재</button>
								<input type="text" style="border: none; width: 40px;" disabled>
							</c:otherwise>
						</c:choose>
					<button>
						<a href="${path}/approval/approvalMain" style="color: black">취소</a>
					</button>
				</div>
			</div>

			<!-- 모달 스크립트 -->
<script>
    	const open = () => {
	    	document.querySelector(".modal1").classList.remove("hidden");
	    }
	
	    const close = () => {
	        document.querySelector(".modal1").classList.add("hidden");
	    }
	
	    document.querySelector("#openRejection").addEventListener("click", open);
	    document.querySelector(".rejModalNo1").addEventListener("click", close);
	<!-- 결재승인버튼 스크립트 --> 
   	$("#Approver1").one("click",function(){
   		
   		$.ajax({
               type: "post",
               url: "${path}/approval/loaApproved1?appNo="+${approval.appNo},
               success: function(){
          		   /* alert("결재서명이 완료되었습니다."); */
           		/* Swal.fire({
     			   icon: 'success',
     			   title: '결재서명이 \n완료되었습니다.'
     			}) */
  				   $("#firstA").append('<img src="${path}/images/approved.png" id="checkIfApproved" style="position:absolute; width:130px; height:130px; margin-left:-92px; margin-top:-50px" />');
        	   },
               error: function(){ alert("잠시 후 다시 시도해주세요."); }
   		});
   	});
   	
	$("#Approver2").one("click",function(){
   		
   		$.ajax({
               type: "post",
               url: "${path}/approval/loaApproved2?appNo="+${approval.appNo},
               success: function(){
               	/* alert("결재서명이 완료되었습니다."); */
           		Swal.fire({
     			   icon: 'success',
     			   title: '결재서명이 \n완료되었습니다.'
     			})
  				   $("#interimA").append('<img src="${path}/images/approved.png" id="checkIfApproved" style="position:absolute; width:130px; height:130px; margin-left:-92px; margin-top:-50px" />');
        	   },
               error: function(){ alert("잠시 후 다시 시도해주세요."); }
   		});
   	});
	
	$("#Approver3").one("click",function(){
   		
   		$.ajax({
               type: "post",
               url: "${path}/approval/loaApproved3?appNo="+${approval.appNo},
               success: function(){
               	/* alert("결재서명이 완료되었습니다."); */
           		Swal.fire({
     			   icon: 'success',
     			   title: '결재서명이 \n완료되었습니다.'
     			})
  				   $("#finalA").append('<img src="${path}/images/approved.png" id="checkIfApproved" style="position:absolute; width:130px; height:130px; margin-left:-92px; margin-top:-50px" />');
        	   },
               error: function(){ alert("잠시 후 다시 시도해주세요."); }
   		});
   	});
</script>

		<!-- 하단 결재버튼 -->
		<script>
  		$("#approveddone").click(function() {
  			if($('#checkIfApproved').length > 0) {
  				var url = "${path}/approval/approvalMain";
   			alert("결재가 완료되었습니다.");
   	        $(location).attr('href', url);  			
  			} else {
  				var url = "${path}/approval/letterOfApprovalView?appNo="+${approval.appNo};
  				alert("결재서명 후 결재를 진행해주세요.");
  			}
  		});
  		
  	</script>
		</div>
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