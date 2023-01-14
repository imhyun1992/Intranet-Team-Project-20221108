 <%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<%@ include file="../includes/head.jsp"%>
<link rel="stylesheet" href="${path}/css/approvalStyle.css">
<link rel="stylesheet" href="${path}/css/expenseReport.css">
<link rel="stylesheet" href="${path}/css/appAutocomplete.css">
<script type="text/javascript" src="${path}/js/approval.js"></script>
</head>
<% Calendar today =  Calendar.getInstance(); %>
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
					<li><a href="${path}/approval/expenseReport">지출결의서 작성</a></li>
				</ul>
			</div>
			<form action="${path}/approval/expenseReport_insert" method="POST" id = "erWriteForm"
				name="erWriteForm" onsubmit="check_onclick()">
				<div class="cash-form-section">
					<div class="cash-disbursement">
						<table border="2"
							style="width: 100%; font-size: 20px; border-collapse: collapse;">
							<tr>
								<td rowspan="2" colspan="4"
									style="width: 300px; height: 120px; font-size: 40px; font-weight: 600;">지
									출 결 의 서</td>
								<td rowspan="2"
									style="width: 20px; padding-top: 30px; font-size: 25px;">결
									재</td>
								<td style="height: 30px; width: 100px;">최초승인자</td>
								<td style="width: 100px;">중간승인자</td>
								<td style="width: 100px;">최종승인자</td>
							</tr>
							<tr>
								<td style=""><input type="text" value="" id="firstApprover"
									name="firstApprover" readonly="readonly" class="nameView">
									<input type="button" value="검색" class="searchMember"
									id="firstBtn" name="firstApprover"></td>
								<td><input type="text" value="" id="interimName"
									name="interimApprover" readonly="readonly" class="nameView">
									<input type="button" value="검색" class="searchMember"
									id="secondBtn" name="interimApprover"></td>
								<td><input type="text" value="" id="finalApprover"
									name="finalApprover" readonly="readonly" class="nameView">
									<input type="button" value="검색" class="searchMember"
									id="thirdBtn" name="finalApprover"></td>
							</tr>
							<tr>
								<td colspan="2" style="height: 70px;">
									<button class="send-open" type="button">수신참조자 +</button>
								</td>
								<td colspan="6" style="height: 70px;"><textArea readonly
										name="referList" id="referList"
										style="border: none; margin-bottom: -12px; font-size: 19px; width: 600px; height: 60px; text-align: center; resize: none;"></textArea>
								</td>
							</tr>
							<tr>
								<td style="height: 70px; width: 80px;">성 명</td>
								<td><input type="text" name="writerName"
									value="${EmpVO.name}" readonly></td>
								<td style="width: 80px;">부 서</td>
								<td><input type="text" value="${deptname}"
									readonly></td>
								<td style="width: 80px;">직 급</td>
								<td colspan="3"><input type="text"
									value="${EmpVO.position}" readonly></td>
							</tr>
							<tr>
								<td colspan="1" style="height: 70px; width: 80px;">지출금액</td>
								<td colspan="2"><input type="text" name="allAmount"
									id="allAmount"></td>
								<td colspan="3"><select id="moneytaryUnit" name="moneytaryUnit" 
									style="width: 130px; height: 40px; font-size: 18px">
										<option value="">화폐단위</option>
										<option value="KRW">KRW</option>
										<option value="USD">USD</option>
										<option value="CNY">CNY</option>
										<option value="JPY">JPY</option>
								</select></td>
							</tr>
							<tr>
								<td style="height: 70px; width: 80px;">제 목</td>
								<td colspan="7"><input type="text" name="erTitle"
									id="erTitle"></td>
							</tr>
							<tr>
								<td rowspan="10" style="width: 80px; height: 100%">내 역</td>
								<td colspan="2">적 요</td>
								<td colspan="2">금 액</td>
								<td colspan="2">비 고</td>
								<td colspan="1" style="font-size: 17px">
									<button type="button" name="addRow" id="addRow"
										style="width: 30px; height: 25px; font-size: 15px; border: 1px solid green;">
										<i class="fas fa-plus" style="color: green;"></i>
									</button> &nbsp;
									<button type="button" name="deleteRow" id="deleteRow"
										style="width: 30px; height: 25px; font-size: 15px; border: 1px solid red;">
										<i class="fas fa-minus" style="color: red;"></i>
									</button>
								</td>
							</tr>
							<tr id="putContents">
								<td colspan="2"><input type="text" name="erDetail"
									id="erDetail"></td>
								<td colspan="2"><input type="text" name="erAmount"
									id="erAmount"></td>
								<td colspan="3"><input type="text" name="erReference"
									id="erReference" value="-"></td>
							</tr>
							<tr>
								<td colspan="8"
									style="text-align: center; height: 100px; border-bottom: none;">위
									금액을 청구하오니 결재바랍니다.</td>
							</tr>
							<tr style="border: white;">
								<td colspan="8" style="text-align: center; height: 100px;">
									<%= today.get(java.util.Calendar.YEAR) %> 년 &nbsp; <%= today.get(java.util.Calendar.MONTH) + 1 %>
									월 &nbsp; <%= today.get(java.util.Calendar.DATE) %> 일 &nbsp;
								</td>
							</tr>
							<tr>
		                    <td colspan="8" style="text-align: right; height: 100px; padding-right: 50px;">
		                        <input type="button" name="proposer" id="proposer" style="font-size:15px; width:70px; height:30px; border: none; text-align: center; border-radius:20px; margin-right:10px" value="서명" />
		                        영수인 :
		                        <textArea name="proposerText" id="proposerText" style="width:130px; border: none; text-align: center; resize: none; font-size:24px; margin-bottom:-42px"></textArea>
		                        (인)
		                    </td>
							</tr>
						</table>
					</div>
					<div id="button">
					<input type="hidden" name="appKinds" value="지출결의서">
						<button type="submit">등록</button>
						<input type="text" style="border: none; width: 40px;" disabled>
						<button type="reset">취소</button>
					</div>
				</div>
			</form>

<script>
function check_onclick() {
	
    if ($('#allAmount').val() == "" || $('#erTitle').val() == "") {
    	alert('지출금액 또는 제목란이 비어있습니다.');
    	$('#erTitle').focus();
	    return false;
	    
    } if ($('#proposerText').val() == "") {
       	alert("서명 후 등록을 완료해주세요.");
       return false;
       
    }  if($('#moneytaryUnit').val() == "") {
    	alert("화폐단위를 선택해주세요.");
	    return false;
	    
    } else {
		return true;
	}
}
</script>


<script>
	$(document).ready(function(){
	    //키를 누르거나 떼었을때 이벤트 발생
	    $("#allAmount").bind('keyup keydown',function(){
	        inputNumberFormat(this);
	    });
	
	    //입력한 문자열 전달
	    function inputNumberFormat(obj) {
	        obj.value = comma(uncomma(obj.value));
	    }
	      
	    //콤마찍기
	    function comma(str) {
	        str = String(str);
	        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	    }
	
	    //콤마풀기
	    function uncomma(str) {
	        str = String(str);
	        return str.replace(/[^\d]+/g, '');
	    }
	
	    //숫자만 리턴(저장할때)
	    //alert(cf_getNumberOnly('1,2./3g')); -> 123 return
	    function cf_getNumberOnly (str) {
	        var len      = str.length;
	        var sReturn  = "";
	
	        for (var i=0; i<len; i++){
	            if ( (str.charAt(i) >= "0") && (str.charAt(i) <= "9") ){
	                sReturn += str.charAt(i);
	            }
	        }
	        return sReturn;
	    }
	});
</script>

<!-- 서명 클릭 스크립트  -->
<script>
	$("#proposer").one("click",function(){
 		var proposerValue = $("input[name='writerName']").val();
 	
 		$("#proposerText").append(proposerValue);
	});
</script>

<!-- 적요 금액 비고 칸 추가 및 삭제 -->
<script>
	$("#addRow").on("click", function() {
		
		$('#putContents').after(
			 '<tr id="putContents">'
            +'<td colspan="2"><input type="text" name="erDetail" id="erDetail"></td>'
            +'<td colspan="2"><input type="text" name="erAmount" id="erAmount"></td>'
            +'<td colspan="3"><input type="text" name="erReference" id="erReference" value="-"></td>'
        	+'</tr> '
	}); 
	$('#deleteRow').on('click', function() {
		$('#putContents').detach();
	});
</script>

	</div>
	<!-- right -->
	<%@ include file="../includes/con_right.jsp"%>
</div>

	<!-- 수신참조자 modal/script/ajax -->
	<%@ include file="../approval/selectReferList.jsp"%>

 	<!-- footer -->
	<%@ include file="../includes/footer.jsp"%>

	<!-- 일정 등록 Modal -->
	<%@ include file="../includes/todoModal.jsp"%>
</body>
</html>
