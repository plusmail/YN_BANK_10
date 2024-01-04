<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<%@include file="../include/menu.jsp"%>
<%@include file="../include/sectionBar.jsp"%>
<style>
* {
	margin: 0;
	padding: 0;
	font-family: 'Noto Sans KR', sans-serif;
	color: #252525;
}

#container {
	width: 1000px;
	margin: 0 auto;
}

div#header {
	background: goldenrod;
	height: 150px;
}

div#header h1 {
	padding: 30px;
}

div#profile {
	background: whitesmoke;
	height: 600px;
	border-radius: 10px;
	padding: 50px;
}

div#profile h2 {
	height: 50px;
}

div#profileMain {
	float: left;
}

div#profileMain div#pic {
	width: 250px;
	height: 350px;
}

div#profileMain div#pic span {
	display: block;
	width: 100px;
	height: 100px;
	float: left;
	text-align: center;
}

div#profileMain div#pic #proName {
	width: 100x;
	font-size: 25px;
	font-weight: bold;
	line-height: 100px;
}

div#profileMain div#pic #proDept {
	line-height: 110px;
	font-size: 20px;
	text-align: left;
}

div#profile div#pic img {
	width: 200px;
	height: 250px;
	display: block;
}

div#bonusTable {
	width: 600px;
	overflow: hidden;
	margin-left: 400px;
}

div#bonusTable table {
	width: 500px;
}

div#bonusTable table tr {
    width: 200px;
	height: 30px;
}

div#pbonusTable table th {
	text-align: left;
}

div#bonusTable table td {
	text-align: center;
}

div#bonusTable table td input {
	width: 250px;
	background: whitesmoke;
	border: none;
	padding: 10px;
	border-bottom: 1px solid gray;
}

table td#noline input[name='file'] {
	border: none;
}

div#bonusTable table td select {
	width: 250px;
	margin: 20px 0;
}

div#submit {
	text-align: center;
	height: 100px;
	line-height: 130px;
}

div#submit input {
	width: 100px;
	height: 40px;
	border: none;
	background: gray;
	margin-left: 20px;
	font-size: 15px;
	color: whitesmoke;
}
/* 에러 메세지 */
.errorMSG {
	color: tomato;
	display: none;
	font-size: 12px;
}


</style>

<script>
	$(function() {
		$("#empBonusList").show();
		 $("#empRealBonusList").show();
		//목록으로 버튼 누르면 
		$("#returnToList").click(function(){
			var whichBounus = "${bonus}";
			if(whichBounus =="bonus"){
			location.href="${pageContext.request.contextPath}/emp/empBonus.do";
			}else{
			location.href="${pageContext.request.contextPath}/emp/empRealBonus.do";
			}
		})
		
	
	
	})
</script>



<body>
	<div id="container">
		<div id="header">
			<h1>사원 실적 정보</h1>
		</div>
		

			<div id="profile">
				<h2>프로필</h2>
				<div id="profileMain">
					<div id="pic">
						<img alt="사원사진"
							src="${pageContext.request.contextPath}/empPic/${emp.pic==null?'no-img.jpg':emp.pic}" id="empPicture">
						<span id="proName">${emp.empName }</span> <span id="proDept">${emp.dept.deptName }팀</span>

					</div>
					<div id="nameInfo"></div>
				</div>
				
				
				<div id="bonusTable">
					<table>
					       <tr>
								<td>상품 코드</td>
								<td>상품 명</td>
								<td>고객 코드</td>
								<td>고객 명</td>
							</tr>
						<c:forEach var='performList' items="${list }">
							<tr>
								<td>${performList.plan.planCode }</td>
								<td>${performList.plan.planName }</td>
								<td>${performList.customer.custCode }</td>
								<td>${performList.customer.custName }</td>
							</tr>
				          
				</c:forEach>
					</table>
				</div>

				<div id="submit">
					<input type="button" value="목록으로" id="returnToList"> 
				</div>

			</div>

	</div>
</body>
</html>