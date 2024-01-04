<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../include/menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link href="${pageContext.request.contextPath}/css/selectCSS.css" rel="stylesheet" />
</head>

	<script>
	$(function(){
		$(".side2").hide();
		$("#planMgn").find(".side2").toggle();
		
		$(".card").click(function(){
			var text = $(this).html();
			$(location).attr('href','${pageContext.request.contextPath}/bankwork/plan/addCardPlan.do?text='+text);
		})
		$("#back").click(function(){
			$(location).attr('href','${pageContext.request.contextPath}/main/main.do');
		})
	})    
</script>
<body>
	<%@include file="../../include/sectionBar.jsp"%>
	<div id="container">
		<div id="header">
			<h1>카드 상품 추가</h1>
		</div>
			<div id="profile">
				<h2></h2>
				
				<h3>상품 분류를 선택해주세요.</h3>
				<div id="submit">
					<button class="card">체크 카드</button>
					<button class="card">신용 카드</button>
				</div>
				<div id="backTotheMain">
					<button id="back">이전으로</button>
				</div>
			</div>
	</div>
</body>
</html>