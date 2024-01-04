<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
	#container { width: 1100px; margin: 50px auto;}
	div#header { background: goldenrod;
			     height: 150px; }
	div#header h1 { padding: 30px;  }
	div#profile { background: whitesmoke;
				  height: 500px;
				  border-radius: 10px;
				  padding: 50px;}
	div#profile h2 { height: 50px; }
	
	div#table1 { width:1000px;}
	div#table1 table { width: 500px; float : left;}
	div#table1 table tr { height: 30px; }
	div#table1 table th { width: 100px; text-align: left; }
	div#table1 table td { width: 200px; text-align: center;}
	div#table1 table td input { width: 250px;
									 background: whitesmoke;
								     border: none; 
								     padding: 10px; 
								     border-bottom: 1px solid gray;}					     
	div#table1 table td select { width: 250px; margin: 20px 0;}
	div#table1 table:last-child {
		margin-top : 10px;
	}
	div#submit { text-align: center; 
				 height: 100px; 
				 line-height: 250px; clear : both;}
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
    	$(".vip").hide();
    	$(".normal").hide();
    	function pad(n, width) {
  		  n = n + '';
  		  return n.length >= width ? n : new Array(width - n.length + 1).join('0') + n;
  		}
    	if($("#cust option:selected").attr("data-rank")==1) {
    		$(".vip").show();
    	}
    	else {
    		$(".normal").show();	
    	}
    	$("#loanAdd").show();
		$("#loanList").show();
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
    		if($("#cust option:selected").attr("data-rank")!=1) {
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
    		if($("input[name='loanStartDate']")==""||$("input[name='loanInterest']").val()==""||$("input[name='loanBalance']").val()=="") {
    			alert("입력란을 모두 입력해주세요");
    			return false;
    		}
    		var accountnum = $("input[name='accountnum']").val();
    		var accountnumReg = /^(293133)[-](11|12|13)[-][0-9]{6}$/;
    		if(!accountnumReg.test(accountnum)) {
    			alert("계좌번호 형식에 맞지 않습니다. 다시 입력해주세요");
    			return false;
    		}
    		var loanInterest = $("input[name='loanInterest']").val();
    		var loanInterestReg = /^(100|[0-9]{1,2})[%]$/;
    		if(!loanInterestReg.test(loanInterest)) {
    			alert("이자율 형식에 맞지 않습니다. 다시 입력해주세요(0~100%)");
    			return false;
    		}
    		var loanBalance = $("input[name='loanBalance']").val();
			var loanBalanceReg = /^[0-9]*$/;
			if(!loanBalanceReg.test(loanBalance)) {
				alert("숫자만 입력하세요");
				return false;
			}
    	})
    	$("#plan").change(function() {
		    var str = $("#plan option:selected").attr("data-planDetail");
		    var plandiv = str.substring(1,2);
		    switch(plandiv) {
		    case 'A':
		    	$("input[name='accountnum']").val('293133-11-'+pad((${number}+1),6));
		    	break;
		    case 'B':
		    	$("input[name='accountnum']").val('293133-12-'+pad((${number}+1),6));
		    	break;
		    case 'C':
		    	$("input[name='accountnum']").val('293133-13-'+pad((${number}+1),6));
		    	break;
		    }
		});
    	$("input[type='reset']").click(function() {
    		location.href = "${pageContext.request.contextPath}/main/main.do";
    	});
    	$("input[name='accountnum']").val('293133-11-'+pad((${number}+1),6)); 
    	$("select[name='loanDelayTerm']").change(function() {
    		var limitYear = 10;
    		var str = $("select[name='loanDelayTerm']").val();
    		var delayYear = Number(str.substring(0, str.indexOf("년")));
    		var expireYear = limitYear - delayYear;
    		$("select[name='loanExpireTerm']").find("option").remove();
    		for(var i=1;i<expireYear+1;i++) {
    			$("select[name='loanExpireTerm']").append("<option>" + i + "년" + "</option>");
    		}
    	});
    	for(var i=1;i<10;i++) {
    		$("select[name='loanExpireTerm']").append("<option>" + i + "년" + "</option>");
    	}
    	$("select[name='loanDelayTerm']").parent().parent().hide();
    	$("select[name='loanMethod']").change(function() {
    		if($("select[name='loanMethod'] option:selected").val()!="만기일시상환") {
    			$("select[name='loanDelayTerm']").parent().parent().show();
    			$("select[name='loanDelayTerm']").find("option").remove();
    			$("select[name='loanExpireTerm']").find("option").remove();
    			for(var i=1;i<6;i++) {
            		$("select[name='loanDelayTerm']").append("<option>" + i + "년" + "</option>");
            	}
        		for(var i=1;i<10;i++) {
            		$("select[name='loanExpireTerm']").append("<option>" + i + "년" + "</option>");
            	}
    		}
    		else {
    			$("select[name='loanDelayTerm']").parent().parent().hide();
    			for(var i=1;i<10;i++) {
            		$("select[name='loanExpireTerm']").append("<option>" + i + "년" + "</option>");
            	}
    		}
    	}); 	
    	$("select[name='loanExpireTerm']").change(function() {
    		if($("select[name='loanMethod'] option:selected").val()!="만기일시상환") {
    			if($("select[name='loanDelayTerm'] option").prop("selected")) {
    				alert("거치 기간을 먼저 클릭해주세요");
        			$("select[name='loanExpireTerm'] option").eq(0).prop("selected",true);
    			}
    		}
    	});
    })
	</script>
	<div id="container">
		<div id="header">
			<h1>대출 추가</h1>
		</div>
		<form action="${pageContext.request.contextPath}/bankwork/loan/add.do?div=${custdiv}" method="post">
			<input type="hidden" value=${Auth.empName} name="empname">
			<input type="hidden" value=${contribution.totalContribution} name="contribution">
			<div id="profile">
				<h2>대출 정보</h2>
				<div id="table1">
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
							<th>대출시작일자</th>
							<td><input type="text" id="date" name="loanStartDate"></td>
						</tr>
						<tr>
							<th>이자율</th>
							<td><input type="text" name="loanInterest"></td>
						</tr>
					</table>
					<table>
						<tr>
							<th>대출금액</th>
							<td><input type="text" name="loanBalance"></td>
						</tr>
						<tr>
							<th>거치기간</th>
							<td>
								<select name="loanDelayTerm"></select>
							</td>
						</tr>
						<tr>
							<th>대출기간</th>
							<td>
								<select name="loanExpireTerm"></select>
							</td>
						</tr>
						<tr>
							<th>대출방식</th>
							<td>
								<select name="loanMethod">
									<option>만기일시상환</option>
									<option>원금균등분할상환</option>
								</select>
							</td>
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
			alert("상품이 중복되었습니다. 확인하고 다시 추가하세요");
			<%
				session.removeAttribute("duplicate");
			%>
		</script>
	</c:if>
</body>
</html>