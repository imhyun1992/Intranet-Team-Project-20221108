<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>

</head>
<body>
	<div class="con_right">
		<div class="todolist">
			<div style="height: 5%;">
				<h2>TodoList</h2>
			</div>
			<div class="todoarea">
				<c:if test="${todoList.list.size() != 0}">
					<c:forEach var="vo" items="${todoList.list}">
						<div>
							<c:if test="${vo.status == false}">
								<input id="${vo.idx}status" type="checkbox" onclick="updatestatus(${vo.idx},${EmpVO.empno})"/>
							</c:if>
							<c:if test="${vo.status != false}">
								<input id="${vo.idx}status" type="checkbox" checked="checked" onclick="updatestatus(${vo.idx},${EmpVO.empno})"/>
							</c:if>
							<p style="background: ${vo.colorcode}">${vo.content}</p>
						</div>
					</c:forEach>
				</c:if>
			</div>
			<div style="height: 7%;" align="center">
				<button onclick="todoPop(${datedata.year},${datedata.month},${datedata.date})">일정등록</button>
			</div>
		</div>
		
		<div class="weather">
			<h2>추후 공개</h2>
		</div>
	</div>
</body>
</html>