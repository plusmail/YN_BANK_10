<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@include file="../include/menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>    
<link href="${pageContext.request.contextPath}/css/custTransferCSS.css" rel="stylesheet" />  
</head>

<script>
	$(function(){
		//클릭한 메뉴만 보이게 하기
		$("#deposit").show();
		$("#dwList").show();
		
		//취소 클릭 시
		$("#cancel").click(function() {
			var choose = confirm("취소하시겠습니까? 리스트로 돌아갑니다.");
			if(choose){
				location.href = "${pageContext.request.contextPath}/cust/custDWSearch.do";	
			}
    	})
    	
    	//입금 클릭 시
    	$("input[value='입금']").click(function(){
    		
    		var deposit = confirm("입금하시겠습니까?");
    		if(deposit){

        		var accountNum = $("input[name='accNum']").val();
        		var amount=$("input[name='amount']").val();
        		var code = $("input[name='code']").val();
        		
        		location.href= "${pageContext.request.contextPath}/cust/custDWFunction.do?accountNum="+accountNum+"&amount="+amount+"&text=입금&code="+code;
    		}
    		
    	})  
    	
    	//출금 클릭 시
    	$("input[value='출금']").click(function(){
    		
    		var deposit = confirm("출금하시겠습니까?");
    		if(deposit){

        		var accountNum = $("input[name='accNum']").val();
        		var amount=$("input[name='amount']").val();
        		var code = $("input[name='code']").val();
        		  
        		location.href= "${pageContext.request.contextPath}/cust/custDWFunction.do?accountNum="+accountNum+"&amount="+amount+"&text=출금&code="+code;
    		}
    		
    	})
    	
	})
</script>
<body>	
	<%@include file="../include/sectionBar.jsp"%>
	<div id="container">
		<div id="header">
			<c:if test="${dw=='출금' }">
				<h1>출금</h1>
			</c:if>
			<c:if test="${dw=='입금' }">   
				<h1>입금</h1>
			</c:if>
		</div>	
			<div id="profile">
				<h2>고객 정보</h2>
						
						<div id="profileEdit">
							<table>
								
						<tr>
							<th>고객 코드</th>
							<td>
								<input type="text" name="code" value="${custBal.custCode }" readonly="readonly">
							</td>
						</tr>
						<tr>
							<th>고객명</th>
							<td><input type="text" name="name" value="${custBal.custName }" readonly="readonly"></td>
						</tr>
						<tr>
							<th>계좌번호</th>
							<td>
								<input type="text" name="accNum" value="${accountNum }" readonly="readonly">
							</td>
						</tr>   
						<tr>           
							<th>잔액</th>
							<td>
								<input type="text" name="accBal" value="${custBal.bankbook.accountBalance }" readonly="readonly">
							</td>   
						</tr>
						<tr>
							<c:if test="${dw=='입금' }">
							<th>입금 금액</th>
							<td><input type="text" name="amount"></td>
							</c:if>
							<c:if test="${dw=='출금' }">
							<th>출금 금액</th>
							<td><input type="text" name="amount"></td>
							</c:if>    
						</tr>
						
					</table>
				</div>
				
				<div id="submit">
					<c:if test="${dw=='입금' }">
						<input type="submit" value="입금">
					</c:if>
					<c:if test="${dw=='출금' }">
						<input type="submit" value="출금">
					</c:if>
					<input type="reset" value="취소" id="cancel">
				</div>
				
			</div>
	</div>
</body>
</html>