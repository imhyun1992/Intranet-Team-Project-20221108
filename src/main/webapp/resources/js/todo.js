// 일정 입력 창 열기
var year;
var month;
var date;

var curr_year;
var curr_month;
var curr_date;

var now;

function todoPop(year, month, date) {

	this.year = year;
	this.month = (Number(month) + 1);
	this.date = date;
	
    $('.inserttodo').css('display','block');
    $('.todoback').css('display','block');
}

// 창 닫기
function closeTodoPop() {
    $('.inserttodo').css('display','none');
    $('.todoback').css('display','none');
}

function todoinsert(year, month, date) {
	
	this.curr_year = year;
	this.curr_month = (Number(month) + 1);
	this.curr_date = date;
	
	if (this.year == curr_year && this.month == curr_month && this.date == curr_date) {
		now = 1;
	} else {
		now = 0;
	}
	
	let time = $('#endtime').val();
	let content = $('#content').val();
	let empno = $('#empno').val();
	let colorcode = $('#colorcode option:selected').val();
	let shareset = $('#shareset option:selected').val();
	
	dateinfo = this.year + '-' + this.month + '-' + this.date;
	
//	console.log(dateinfo);
//	console.log(now);
	
	if (time == null || time == "") {		
		c = content;
	} else {
		c = "~" + time + ", " +  content;
	}
	
	if (content == null || content == "") {
		alert('내용을 입력하세요.');
		return;
	} else if (colorcode == null || colorcode == "") {
		alert('배경색을 지정해주세요.');
		return;
	} else if (shareset == null || shareset == "") {
		alert('공개 범위를 지정해주세요.');
		return;
	}
	
	// jQuery Ajax
	$.ajax({
		type: 'POST',
		url: './todo_insert',
		data: {
			content : c,
			empno : empno,
			colorcode : colorcode,
			shareset : shareset,
			dateinfo : dateinfo
		},
		success: res => {
			
			$('#endtime').val('');
			$('#content').val('');
			$('#colorcode').val('');
			$('#colorcode').css('background-color','#ffffff');
			$('#shareset').val('');

			if (this.now == 1) {
				$('.todoarea').append('<div><input id="' + res + 'status" type="checkbox" onclick="updatestatus(' + res + ',' + empno + ')"><p style="background:' + colorcode + '">' + c + '</p></div>');
			}
			
			closeTodoPop();
			location.reload();
	
		},
		error: err => console.log('일정 등록 실패' + err)
	});
	
}

function changeBack() {
	let colorcode = $('#colorcode option:selected').val();
	
	$('#colorcode').css('background-color',colorcode);
}

function updatestatus(idx, empno) {
	
	let status = $('#'+idx+'status').prop('checked');
	
	// jQuery Ajax
	$.ajax({
		type: 'POST',
		url: './todo_updatestatus',
		data: {
			status : status,
			idx : idx,
			empno : empno
		},
		success: res => {
			
			$('#'+idx+'status').prop('checked',status);
	
		},
		error: err => console.log('일정 상태 변경 실패' + err)
	});
}





