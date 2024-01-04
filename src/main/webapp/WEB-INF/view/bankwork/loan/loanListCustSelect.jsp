<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			$(location).attr('href','${pageContext.request.contextPath}/bankwork/loan/mgn.do?div=1');
		})
		$("#nor").click(function(){
			$(location).attr('href','${pageContext.request.contextPath}/bankwork/loan/mgn.do?div=0');
		})
	})    
</script>
<body>
	<jsp:include page="../../include/menu.jsp"/>
	<div id="container">
		<div id="header">
			<h1>대출 상환</h1>
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
	<c:if test="${finishrepayment!=null}">
		<script>
			alert("모든 상환이 완료되었습니다");
			<%
				session.removeAttribute("finishrepayment");
			%>
		</script>
		</c:if>
	<c:if test="${errornonbusiness!=null}">
		<script>
			alert("기업 고객의 대출 정보가 존재하지 않습니다. 추가부터 해주세요");
			<%
				session.removeAttribute("errornonbusiness");
			%>
		</script>
	</c:if>
	<c:if test="${errornonnormal!=null}">
		<script>
			alert("일반 고객의 대출 정보가 존재하지 않습니다. 추가부터 해주세요");
			<%
				session.removeAttribute("errornonnormal");
			%>
		</script>
	</c:if>
</body>
</html>