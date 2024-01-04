<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../../include/sectionBar.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link rel="stylesheet" href="//code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" />
<style>
	* { margin:0; padding:0; 
		font-family: 'Noto Sans KR', sans-serif;
		color: #252525; }
	#container { width: 1000px; margin: 50px auto;}
	div#header { background: goldenrod;
			     height: 150px; }
	div#header h1 { padding: 30px;  }
	div#profile { background: whitesmoke;
				  height: 500px;
				  border-radius: 10px;
				  padding: 50px;}
	div#profile h2 { height: 50px; }				  
	div#profileMain { float: left; }
	div#profileMain div#pic { width: 250px; height: 350px;}
	div#profileMain div#pic span { display: block;
							       width: 100px; height: 100px;
							       float: left; 
							       text-align: center;}
	div#profileMain div#pic #proName { width: 100x; font-size: 30px; 
									   font-weight: bold;
							    	   line-height: 100px;  }
	div#profileMain div#pic #proDept { line-height: 110px;
									   font-size: 20px;
									   text-align: left; }
	div#profile div#pic img { width: 200px; height: 250px; display: block;}
	
	div#profileEdit { width:600px; 
					  overflow: hidden;
					  margin-left: 200px;  }
	div#profileEdit table { width: 500px; }
	div#profileEdit table tr { height: 30px; }
	div#profileEdit table th { width: 100px; text-align: left; }
	div#profileEdit table td { width: 200px; text-align: center;}
	div#profileEdit table td input { width: 250px;
									 background: whitesmoke;
								     border: none; 
								     padding: 10px; 
								     border-bottom: 1px solid gray;}
	table td#noline input[name='file'] { border: none;}									     
	div#profileEdit table td select { width: 250px; margin: 20px 0;}		
	div#submit { text-align: center; 
				 height: 100px; 
				 line-height: 250px; }
	div#submit input { width: 100px;  height: 40px; 
					   border: none;
					   background: gray; 
					   margin-left:20px; 
					   font-size: 15px;
					   color: whitesmoke;}
	div#dummy { height: 75px; background: #292929;}							    
</style>
<body>
	<jsp:include page="/WEB-INF/view/include/menu.jsp"/>
	<script>
    $(function() {
    	function pad(n, width) {
    		  n = n + '';
    		  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
    	}
    	$(".normal").hide();
    	$("#bankbookAdd").show();
		$("#bankbookList").show();
    	$('#date').datepicker({
            dateFormat: 'yy-mm-dd',
            onSelect: function(datetext) {
                var d = new Date(); // for now

                var h = d.getHours();
                h = (h < 10) ? ("0" + h) : h ;

                var m = d.getMinutes();
                m = (m < 10) ? ("0" + m) : m ;

                var s = d.getSeconds();
                s = (s < 10) ? ("0" + s) : s ;

                datetext = datetext + " " + h + ":" + m + ":" + s;
                $('#date').val(datetext);
            }
        });
    	$("#cust").change(function() {
    		if($("#cust option:selected").attr("data-rank")!='1') {
    			$(".vip").hide();
    			$(".normal").show();
    			$(".normal").eq(0).prop("selected", true)
    		}
    		else {
    			$(".normal").hide();
    			$(".vip").show();
    			$(".vip").eq(0).prop("selected", true)
    		}
    	})
    	$("form").submit(function() {
    		if($("input[name='accountnum']").val()==""||$("input[name='accountOpenDate']").val()==""||$("input[name='accountInterest']").val()=="") {
    			alert("입력란을 모두 입력해주세요");
    			return false;
    		}
    		var accountnum = $("input[name='accountnum']").val();
    		var accountnumReg = /^(293133)[-](11|12|13)[-][0-9]{6}$/;
    		if(!accountnumReg.test(accountnum)) {
    			alert("계좌번호 형식에 맞지 않습니다. 다시 입력해주세요");
    			return false;
    		}
    		var accountInterest = $("input[name='accountInterest']").val();
    		var accountInterestReg = /^(100|[0-9]{1,2})[%]$/;
    		if(!accountInterestReg.test(accountInterest)) {
    			alert("이자율 형식에 맞지 않습니다. 다시 입력해주세요(0~100%)");
    			return false;
    		}
    		var accountBalance = $("input[name='accountBalance']").val();
    		var str = $("#plan option:selected").attr("data-planDetail");
    		var plandiv = str.substring(1,2);
 		    if(plandiv == 'C') {
 		    	if($("input[name='accountInterest']").val()=="") {
 		    		alert("입력란을 모두 입력하세요");
 		    		return false;
 		    	}
 		    	var accountBalanceReg = /^[0-9]*$/;
 				if(!accountBalanceReg.test(accountBalance)) {
 					alert("숫자만 입력하세요");
 					return false;
 				}
 		    }
    	})
    	$("input[type='reset']").click(function() {
    		location.href = "${pageContext.request.contextPath}/main/main.do";
    	})
    	$("#plan").change(function() {
		    var str = $("#plan option:selected").attr("data-planDetail");
		    var plandiv = str.substring(1,2);
		    if(plandiv == 'C') {
		    	$("#balance").show();
		    }
		    else {
		    	$("#balance").hide();
		    }
		    switch(plandiv) {
		    case 'A':
		    	$("input[name='accountnum']").val('293133-11-'+pad(${number},6));
		    	break;
		    case 'B':
		    	$("input[name='accountnum']").val('293133-12-'+pad(${number},6));
		    	break;
		    case 'C':
		    	$("input[name='accountnum']").val('293133-13-'+pad(${number},6));
		    	break;
		    }
		})
		$("#balance").hide();
    	$("input[name='accountnum']").val('293133-11-'+pad(${number},6));
    });
	</script>
	<div id="container">
		<div id="header">
			<h1>통장 추가</h1>
		</div>
		<form action="${pageContext.request.contextPath}/bankwork/bankbook/add.do?custdiv=${custdiv}" method="post">
			<input type="hidden" value=${Auth.empName} name="empname">
			<input type="hidden" value=${contribution.totalContribution} name="contribution">
			<div id="profile">
				<h2>통장 정보</h2>
				<div id="profileEdit">
					<table>
						<tr>
							<th>고객명</th>
							<td>
								<select name="custname" id="cust">
									<c:forEach var="cust" items="${custList}">
										<option data-rank="${cust.custCredit}">${cust}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<th>계좌번호</th>
							<td><input type="text" name="accountnum" readonly="readonly"></td>
						</tr>
						<tr>
							<th>상품명</th>
							<td>
								<select name="planname" id="plan">
									<c:forEach var="plan" items="${planList}">
										<option class="vip" data-planDetail="${plan.planDetail}">${plan}</option>
									</c:forEach>
									<c:forEach var="planNormal" items="${planListNormal}">
										<option class='normal' data-planDetail="${planNormal.planDetail}">${planNormal}</option>
									</c:forEach>
									
								</select>
							</td>
								
						</tr>
						<tr>
							<th>계좌개설일</th>
							<td><input type="text" id="date" name="accountOpenDate"></td>
						</tr>
						<tr>
							<th>이자율</th>
							<td><input type="text" name="accountInterest"></td>
						</tr>
						<tr id="balance">
							<th>대출금액</th>
							<td><input type="text" name="accountBalance"></td>
						</tr>
					</table>
				</div>
				<div id="submit">
					<input type="submit" value="등록">
					<input type="reset" value="취소">
				</div>
			</div>
		</form>
	</div>
	<c:if test="${duplicate!=null}">
    	<script>
    		alert("중복된 상품입니다. 다시 확인하고 추가하세요");
    		<%
    			session.removeAttribute("duplicate");
    		%>
    	</script>
    </c:if>
	<c:if test="${Insufficient!=null}">
    	<script>
    		alert("통장 출자금보다 마이너스 통장 대출 금액이 더 많습니다. 다시 확인하고 대출을 진행해주세요");
    		<%
    			session.removeAttribute("Insufficient");
    		%>
    	</script>
    </c:if>
</body>
</html>