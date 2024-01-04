<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/menu.jsp"%>
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
		//클릭한 메뉴만 보이게 하기
		$("#transfer").show();

		//즉시이체 클릭 시 
		$("#dep").click(function(){
			var num = ${num };
			//계좌번호 중간 번호 "13"-> 마이너스 통장 '입금' 제한 (마이너스 통장은 한도가 정해져 있고 출금만 가능)
			/* if(num=="13"){
				alert("해당 계좌는 입금이 제한되는 마이너스 통장입니다.");
				return false;
			} */
			var dw = $(this).html();
			$(location).attr('href','${pageContext.request.contextPath}/cust/custTtoSame.do?custCode=${custCode}&dw='+dw+"&accountNum=${accountNum}");
		})
		//타행송금 클릭 시 
		$("#wd").click(function(){
			var dw = $(this).html();
			$(location).attr('href','${pageContext.request.contextPath}/cust/custTtoDifferent.do?custCode=${custCode}&dw='+dw+"&accountNum=${accountNum}");
		})
		
		//이전으로 클릭 시 
		$("#back").click(function(){
			$(location).attr('href','${pageContext.request.contextPath}/cust/custTransfer.do');
		})
	})    
</script>
<body>
	<%@include file="../include/sectionBar.jsp"%>
	<div id="container">
		<div id="header">
			<h1>송금</h1>
		</div>
			<div id="profile">
			<h2></h2>	
				<h3>수행할 업무를 선택해주세요.</h3>
				<div id="submit">
					<button id="dep">즉시이체</button>
					<button id="wd">타행송금</button>
				</div>
				<div id="backTotheMain">
					<button id="back">이전으로</button>
				</div>
			</div>
	</div>
</body>
</html>