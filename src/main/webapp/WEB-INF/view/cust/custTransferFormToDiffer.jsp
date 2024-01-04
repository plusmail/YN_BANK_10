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
<style>

  div#profileEdit table td select {
	width: 250px;
	height:30px;
	padding-left: 10px;
	margin-left:50px;
}
					    
</style>
<script>
	$(function(){
		//클릭한 메뉴만 보이게 하기
		$("#transfer").show();
		

		//있는 계좌번호인지 판단 
		$("#findAccNum").on("change",function(){
			$(".errorMSG").css("display", "none");
			$(".errorCust").html("");
			var targetAccNum = $("#findAccNum").val();
			var bankCode = $("#selectBank").val();
			//alert(targetAccNum);
			
			$.ajax({
				url: "${pageContext.request.contextPath}/cust/custTtoDifferent.do",
			    type: "post", 
			    data: {"targetAccNum":targetAccNum,"bankCode":bankCode},
			    dataType: "json",
			    success : function(res){
			         console.log(res)
			    	if(res.successs =="existAccount"){
			    		
			    		$("input[name='findAccNum']").next().next().html("  "+res.targetCustName);
			    		$("input[name='findAccNum']").next().next().next().css("display", "inline");
			    		
			    	}if(res.error =="notExist"){
			    		$("input[name='findAccNum']").next().css("display", "inline");
			    	}
			    	
			    }
			})
			
		})
		
		$("#selectBank").change(function(){
			$(".errorMSG").css("display", "none");
			$(".errorCust").html("");
			$("#findAccNum").val("");
		})
		
		
		
		
		//취소 클릭 시
		$("#cancel").click(function() {
			var choose = confirm("취소하시겠습니까? 리스트로 돌아갑니다.");
			if(choose){
				location.href = "${pageContext.request.contextPath}/cust/custTransfer.do";	
			}
    	})
    	
    	//이체 클릭 시
    	$("input[value='이체']").click(function(){
    		var transferAmount = $("input[name='transferAmount']").val();
    		var amountReg=/[0-9]/;
    		if(transferAmount == "" || amountReg.test(transferAmount) == false){
    			$("input[name='transferAmount']").next().css("display","inline");
    			return false;
    		}
    		var deposit = confirm("이체하시겠습니까?");
    		if(deposit){

        		var accountNum = $("input[name='accNum']").val();
        		//var amount=$("input[name='amount']").val();
        		var code = $("input[name='code']").val();
        		var transferAmount = $("input[name='transferAmount']").val();
        		var findAccNum = $("input[name='findAccNum']").val();
        		var selectBank = $("#selectBank").val();
        		
        		location.href= "${pageContext.request.contextPath}/cust/custTFunctionToOther.do?accountNum="+accountNum+"&text=송금&code="+code+"&transferAmount="+transferAmount+"&findAccNum="+findAccNum+"&selectBank="+selectBank;
        	}
    		
    		
    		
    		
    	})  
    	
/*     	//출금 클릭 시
    	$("input[value='출금']").click(function(){
    		
    		var deposit = confirm("출금하시겠습니까?");
    		if(deposit){

        		var accountNum = $("input[name='accNum']").val();
        		var amount=$("input[name='amount']").val();
        		var code = $("input[name='code']").val();
        		  
        		location.href= "${pageContext.request.contextPath}/cust/custDWFunction.do?accountNum="+accountNum+"&amount="+amount+"&text=출금&code="+code;
    		}
    		
    	}) */
    	
	})
</script>
<body>	
	<%@include file="../include/sectionBar.jsp"%>
	<div id="container">
		<div id="header">
			<c:if test="${dw=='즉시이체' }">
				<h1>즉시이체</h1>
			</c:if>
	 		<c:if test="${dw=='타행송금' }">   
				<h1>타행송금</h1>
			</c:if> 
		</div>	
			<div id="profile">
				<h2>송금 정보</h2>
						
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
							<th>고객 계좌번호</th>
							<td>
								<input type="text" name="accNum" value="${accountNum }" readonly="readonly">
							</td>
						</tr>   
						<tr>           
							<th>고객 잔액</th>
							<td>
								<input type="text" name="accBal" value="${custBal.bankbook.accountBalance }" readonly="readonly">
							</td>   
						</tr>
						<tr>
						    <th id="rel">송금할 은행</th>
						    <td>
						    <select id="selectBank">
						        <option value="011">농협(011)</option>
						        <option value="004">국민은행(004)</option>
						        <option value="031">대구은행(031)</option>
						        <option value="020">우리은행(020)</option>
						        <option value="071">우체국(071)</option>
						        <option value="090">카카오뱅크(090)</option>
						    </select>
						    </td>
						</tr>
					
						<tr>
							<th>송금 계좌번호</th>
							<td>
								<input type="input" name="findAccNum" id="findAccNum">
								<span class="errorMSG">없는 계좌번호 입니다. 확인해주세요(6자리입력 필요)</span>
								<span class="errorCust"></span><span class="errorMSG"> 님의 계좌번호가 맞다면 진행하세요</span> 
							</td>
						</tr>
						<tr>           
							<th>이체 금액</th>
							<td>
								<input type="text" name="transferAmount">
								<span class="errorMSG">숫자를 입력해주세요</span>
								
							</td>   
						</tr>
						
					</table>
				</div>
				
				<div id="submit">
					<c:if test="${dw=='즉시이체' }">
						<input type="submit" value="이체">
					</c:if>
					<c:if test="${dw=='타행송금' }">
						<input type="submit" value="이체">
					</c:if>
					<input type="reset" value="취소" id="cancel">
				</div>
				
			</div>
	</div>
</body>
</html>