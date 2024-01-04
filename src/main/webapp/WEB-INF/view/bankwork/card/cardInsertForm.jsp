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
    	};
    	$("#cardAdd").show();
		$("#cardList").show();
    	var open;
    	setInterval(function() {
    		if(open!=null) {
    			if(open.closed) {
        			var custname = $("#cust option:selected").val();
        			var cardnum = $("input[name='cardnum']").val();
        			var planname = $("#plan option:selected").val();
        			var cardSecuCode = $("input[name='cardSecuCode']").val();
        			var cardIssueDate = $("input[name='cardIssueDate']").val();
        			var empname = $("input[name='empname']").val();
        			var accountnum = $("#accountnum").val();
        			location.href = "${pageContext.request.contextPath}/bankwork/card/add.do?"+"custname="+custname+"&"+"cardnum="+cardnum+"&"+"planname="+planname+"&"+"cardSecuCode="+cardSecuCode+"&"+"cardIssueDate="+cardIssueDate+"&"+"empname="+empname+"&"+"accountnum="+accountnum+"&"+"custdiv=${custdiv}";
        		}
    		}
    	}, 100);
    	$(".normal").hide();
    	$("#cardLimit").hide();
    	$("#cardBalance").hide();
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
    	});
    	$("input[type='submit']").click(function() {
    		var custname = $("#cust option:selected").val();
    		var str = $("#plan option:selected").attr("data-planDetail");
    		var planDetail = str.substring(1,2);
    		if(planDetail=='A') {
    			$.ajax({
    				url : "${pageContext.request.contextPath}/bankwork/card/availCheck.do",
    				method : "get",
    				data : {custname : custname},
    				dataType : "json",
    				success : function(res) {
    					if(res==null) {
    						alert("연결된 예금통장이 존재하지 않습니다. 예금통장을 먼저 계설해주세요");
    					}
    					else {
    						var custCode;
    						$(res).each(function(i, obj) {
    							custCode = obj.custCode.custCode;
    						})
    						open = window.open("${pageContext.request.contextPath}/bankwork/card/availAccount.do?custcode="+custCode,"_blank","toolbar=yes,scrollbars=yes,resizable=yes,top=500,left=200,width=800,height=600");
    					}
    				}
    			})
    		}
    	});
    	$("#plan").change(function() {
		    var str = $("#plan option:selected").attr("data-planDetail");
		    var plandiv = str.substring(1,2);
		    if(plandiv=='B') {
				$("#cardLimit").show();
			}
			else {
				$("#cardLimit").hide();
			}
		    switch(plandiv) {
		    case 'A':
		    	$("input[name='cardnum']").val('29313310'+pad((${number}+1),7) + "0");
		    	break;
		    case 'B':
		    	$("input[name='cardnum']").val('29313320'+pad((${number}+1),7) + "0");
		    	break;
		    }
		});
    	$("form").submit(function() {
    		if($("#cust option:selected").val()==""||$("input[name='cardnum']").val()==""||$("#plan option:selected").val()==""||
    		$("input[name='cardSecuCode']").val()==""||$("input[name='cardIssueDate']").val()=="") {
    			alert("입력란을 모두 다 입력해주세요");
    			return false;
    		}
    		var cardnum = $("input[name='cardnum']").val();
    		var cardnumReg = /^(29313310|29313320)[0-9]{7}(0)$/;
    		if(!cardnumReg.test(cardnum)) {
    			alert("카드 형식에 맞게 입력해주세요");
    			return false;
    		}
    		var cardSecuCode = $("input[name='cardSecuCode']").val();
    		var secuCodeReg = /^[0-9]{3}$/;
    		if(!secuCodeReg.test(cardSecuCode)) {
    			alert("카드 보안 코드 형식에 맞게 입력해주세요(0~9,3자리)");
    			return false;
    		}
    		var str = $("#plan option:selected").attr("data-planDetail");
    		var planDetail = str.substring(1,2);
    		if(planDetail=='A') {
    			return false;	
    		}
    		else {
    			if($("input[name='cardLimit']").val()=="") {
    				alert("카드 한도를 입력해주세요");
    				return false;
    			}
    			var cardLimit = $("input[name='cardLimit']").val();
    			var cardLimitReg = /^[0-9]*$/;
    			if(!cardLimitReg.test(cardLimit)) {
    				alert("숫자만 입력하세요");
    				return false;
    			}
    		}
    	});
    	$("input[type='reset']").click(function() {
    		location.href = "${pageContext.request.contextPath}/main/main.do";
    	});
    	$("input[name='cardnum']").val('29313310'+pad((${number}+1),7) + "0");
    });
	</script>
	<div id="container">
		<div id="header">
			<h1>카드 추가</h1>
		</div>
		<form action="${pageContext.request.contextPath}/bankwork/card/add.do?custdiv=${custdiv}" method="post">
			<input type="hidden" name="accoutnum" id="accountnum">
			<input type="hidden" value=${Auth.empName} name="empname">
			<div id="profile">
				<h2>카드 정보</h2>
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
							<th>카드번호</th>
							<td><input type="text" name="cardnum" readonly="readonly"></td>
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
							<th>카드보안코드</th>
							<td><input type="text" name="cardSecuCode"></td>
						</tr>
						<tr>
							<th>카드발급일</th>
							<td><input type="text" id="date" name="cardIssueDate"></td>
						</tr>
						<tr id="cardLimit">
							<th>카드한도</th>
							<td><input type="text" name="cardLimit"></td>
						</tr>
						<tr id="cardBalance">
							<th>카드잔액</th>
							<td><input type="text" name="cardBalance"></td>
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
    		alert("상품이 중복되었습니다. 다시 확인하고 추가하세요");
    		<%
    			session.removeAttribute("duplicate");
    		%>
    	</script>
    </c:if>
</body>
</html>