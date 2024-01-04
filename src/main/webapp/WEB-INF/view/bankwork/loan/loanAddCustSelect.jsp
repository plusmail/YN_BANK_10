<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../include/sectionBar.jsp"%>
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
		$("#loanAdd").show();
		$("#loanList").show();
		$("#bus").click(function(){
			$(location).attr('href','${pageContext.request.contextPath}/bankwork/loan/add.do?div=1');
		})
		$("#nor").click(function(){
			$(location).attr('href','${pageContext.request.contextPath}/bankwork/loan/add.do?div=0');
		})
	})    
</script>
<body>
	<jsp:include page="../../include/menu.jsp"/>
	<div id="container">
		<div id="header">
			<h1>대출 추가</h1>
		</div>
			<div id="profile">
				<h2>프로필</h2>
				
				<h3>고객 분류를 선택해주세요.</h3>
				<div id="submit">
					<button id="bus">기업</button>
					<button id="nor">일반</button>
				</div>
			</div>
	</div>
</body>
</html>