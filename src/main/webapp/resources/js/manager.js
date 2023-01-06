function approvalEMP(empno) {
	
	let tr = document.getElementById('tr'+empno);
	
	let position_form = document.getElementById('position'+empno);
	let position = position_form.options[position_form.selectedIndex].value;
	
	let dept_form = document.getElementById('deptno'+empno);
	let deptno = dept_form.options[dept_form.selectedIndex].value;
	
	let incnum = document.getElementById('incnum'+empno).value;
	let hiredate = document.getElementById('hiredate'+empno).value;
	
	let permission = document.querySelector('input[name="permission'+empno+'"]:checked').value;																					// value)
	
	if (incnum.length == 0) {
		alert('전화번호를 적어주세요.');
		return;
	}
	if (hiredate.length == 0) {
		alert('고용일을 적어주세요.');
		return;
	}
	
	let empvo = {
		empno : empno,
		position : position,
		deptno : deptno,
		incnum : incnum,
		hiredate : hiredate,
		permission : permission
	};
	
	httpRequest = new XMLHttpRequest();
	/* httpRequest의 readyState가 변화했을때 함수 실행 */
    httpRequest.onreadystatechange = () => {
	    if (httpRequest.readyState === XMLHttpRequest.DONE) {
		      if (httpRequest.status === 200) {
		    	  alert('성공');
		    	  tr.remove();
		      } else {
		        alert('실패');
		      }
		}
    };
    /* Post 방식으로 요청 */
    httpRequest.open('POST', 'account_approvalOK', true);
    /* Response Type을 Json으로 사전 정의 */
    httpRequest.responseType = "json";
    /* 요청 Header에 컨텐츠 타입은 Json으로 사전 정의 */
    httpRequest.setRequestHeader('Content-Type', 'application/json');
    /* 정의된 서버에 Json 형식의 요청 Data를 포함하여 요청을 전송 */
    httpRequest.send(JSON.stringify(empvo));
		
	
}





