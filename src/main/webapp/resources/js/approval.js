// letterOfApproval
function check_onclick() {
	let loaWriteForm = $('#loaWriteForm').val();
	let loaTitle = $('#loaTitle').val();
	console.log(loaWriteForm);
	console.log(loaTitle);
	
	if (loaWriteForm.loaContent.value == "" || loaWriteForm.loaTitle.value == "") {
		alert('상세내용 또는 제목란이 비어있습니다. 확인 후 등록하세요.');
		return false;

	} else if (loaWriteForm.proposerText.value == "") {
		alert('결재서명 후 \n결재를 진행해주세요.');
		return false;
		
	} else {
		return true;
	}

}

// leaveWriteForm
function check_onclick() {
    let leaveWriteForm = $('#leaveWriteForm');
    if(leaveWriteForm.leaveClassify.value=="" || leaveWriteForm.leaveDetail.value==""){
        
        Swal.fire({
			   icon: 'error',
			   title: '상세내용 또는 \n제목란이 비어있습니다.',
			   text: '확인 후 등록하세요!'
		})
        
        return false;
    } else if(leaveWriteForm.proposerText.value=="") {
  	  Swal.fire({
			  icon: 'error',
			  title: '서명 후 등록을 완료해주세요.',
			  text: '확인 후 등록하세요!'
		})
       
       return false;
   } else {
      return true;
   }
    
}

// expenseReport
function check_onclick() {
    let erWriteForm = $('#erWriteForm');
    
    if(erWriteForm.allAmount.value=="" || erWriteForm.erTitle.value=="") {
    	Swal.fire({
		  icon: 'error',
		  title: '지출금액 또는 제목란이 비어있습니다.',
		  text: '확인 후 등록하세요!'
		})
	    return false;
    } else if(erWriteForm.proposerText.value=="") {
       /* alert("서명 후 등록을 완료해주세요."); */
       Swal.fire({
			  icon: 'error',
			  title: '서명 후 등록을 완료해주세요.',
			  text: '확인 후 등록하세요!'
		})
       return false;
    } else if(erWriteForm.erDeadline.value=="") {
    	/* alert("마감일자란이 비어있습니다. 확인 후 등록하세요."); */
    	Swal.fire({
			  icon: 'error',
			  title: '마감일자란이 비어있습니다.',
			  text: '확인 후 등록하세요!'
		})
	    return false;
    } else if(erWriteForm.moneytaryUnit.value=="") {
    	/* alert("화폐단위를 선택해주세요."); */
    	Swal.fire({
			  icon: 'error',
			  title: '화폐단위를 선택해주세요.',
			  text: '확인 후 등록하세요!'
		})
	    return false;
    } else {
		return true;
	}
}

