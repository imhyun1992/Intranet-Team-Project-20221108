<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<div class="searchTemplate_area">
    <div class="result_area">
        <h3>반려 사유</h3>
        <div>
            <textarea id="cancelReason" name="cancelReason" style="height: 250px; margin-top: 20px"></textarea>
        </div>
    </div>
    <div class="button_area">
        <button onclick="cancelAction()">확인</button>
        <button onclick="closePopup()">닫기</button>
    </div>
    <input type="hidden" id="approvalSeq" name="approvalSeq"/>
</div>
<script>
  (function () {
    $(".searchTemplate_area").hide();
  })();

  function showCancelForm(seq) {
    $('#approvalSeq').val(seq);

    const height = Math.floor($(".searchTemplate_area").height());
    const scrollTop = Math.floor($("html").scrollTop() / 2);
    $(".searchTemplate_area").css("top", height + scrollTop + 'px');
    $(".searchTemplate_area").show();
  }

  async function cancelAction(){
    if (confirm("정말 반려 처리하시겠습니까?")) {
      const seq = $('#approvalSeq').val();
      const reason = $('#cancelReason').val();
      await fetch('cancelAction', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({'seq' : seq, 'reason' : reason})
      }).then( async data => {
        let result = await data.text();
        if(result === 'ok'){
          alert("반려 처리에 성공했습니다.");
        }else{
          alert('반려 처리에 실패했습니다.');
        }
        closePopup();
        let mainPath = '${path}/approval/approvalMain';
        console.log('mainPath', mainPath);
        location.href = mainPath;
      });
    }
  }

  function closePopup(){
    $('#approvalSeq').val("");
    $('#cancelReason').val("");
    $('.searchTemplate_area').hide();
  }
</script>