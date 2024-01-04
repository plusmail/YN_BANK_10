<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions"%>
<%@include file="../../include/sectionBar.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/listCSS.css" rel="stylesheet" />
</head>
<style>
				
		div#table {
			width: 900px;
			margin: 100px auto;
		}
			
		div#dummy { height: 75px; background: #292929;}
		#btnMenu1 {
		   margin-left:510px;
		   width:150px;
		   border:2px solid goldenrod;
		   border-radius: 10px;
		   background: none;
		}
		#btnMenu1:hover {
		   background: goldenrod;
		   font-weight: bold;
		}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
		$("#bankbookAdd").show();
		$("#bankbookList").show();
		$("button").eq(0).click(function() {
			switch($("#searchMenu option:selected").val()) {
			case "검색구분":
				alert("검색 구분을 선택해주세요");
				$("input[name='search']").val("");
				break;
			case "계좌번호":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				if(search=="") {
					alert("계좌번호를 입력하세요");
					return;
				}
				$.ajax({
					url: "${pageContext.request.contextPath}/bankwork/bankbook/mgn.do?custdiv=${custdiv}",
				    data: {search:search,div:div},
				    type: "POST", 
				    dataType: "json",
				    success : function(res) {
				    	if(res.errorAccountNum!=null) {
				    		alert("그런 계좌번호는 없습니다. 다시 입력하세요");
				    		$("input[name='search']").val("");
				    	}
				    	else {
				    		console.log(res);
				    		$("#table table").remove();
				    		var table = $("<table>");
				    		var tr = $("<tr>");
				    		var th1 = $("<th>").html("계좌번호");
				    		var th2 = $("<th>").html("고객이름");
				    		var th3 = $("<th>").html("상품명");
				    		var th4 = $("<th>").html("통장구분");
				    		var th5 = $("<th>").html("계좌개설일");
				    		var th6 = $("<th>").html("이자율");
				    		tr.append(th1).append(th2).append(th3).append(th4).append(th5).append(th6);
				    		table.append(tr);
							$(res).each(function(i, obj) {
								var tr = $("<tr>").attr("data-accountNum",obj.accountNum).attr("data-custName",obj.custCode.custName).addClass("pickedOne");
								var a = []
								a[0] = $("<a>").html(obj.accountNum).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[1] = $("<a>").html(obj.custCode.custName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[2] = $("<a>").html(obj.accountPlanCode.planName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var str = obj.accountNum;
								var div = str.substring(8, 9)=='1'?"예금": str.substring(8, 9)=='2'?"적금":"마이너스";
								a[3] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var date = new Date(obj.accountOpenDate);
								var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2) + ' '+('0' + (date.getHours())).slice(-2)+ ':'+('0' + (date.getMinutes())).slice(-2)+ ':'+('0' + (date.getSeconds())).slice(-2);
								a[4] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var interestToPercent = obj.accountInterest * 100;
								a[5] = $("<a>").html(interestToPercent + "%").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								$(a).each(function(i, obj) {
									var td = $("<td>").append(a[i]);
									tr.append(td);
								})
								table.append(tr);
							})
							$("#table").append(table);
				    	}
				    }
				})
				break;
			case "고객이름":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				if(search=="") {
					alert("고객 이름을 입력하세요");
					return;
				}
				$.ajax({
					url: "${pageContext.request.contextPath}/bankwork/bankbook/mgn.do?custdiv=${custdiv}",
				    data: {search:search,div:div},
				    type: "POST", 
				    dataType: "json",
				    success : function(res) {
				    	if(res.errorCustName!=null) {
				    		alert("그런 고객은 없습니다. 다시 입력하세요");
				    		$("input[name='search']").val("");
				    	}
				    	else {
				    		console.log(res);
				    		$("#table table").remove();
				    		var table = $("<table>");
				    		var tr = $("<tr>");
				    		var th1 = $("<th>").html("계좌번호");
				    		var th2 = $("<th>").html("고객이름");
				    		var th3 = $("<th>").html("상품명");
				    		var th4 = $("<th>").html("통장구분");
				    		var th5 = $("<th>").html("계좌개설일");
				    		var th6 = $("<th>").html("이자율");
				    		tr.append(th1).append(th2).append(th3).append(th4).append(th5).append(th6);
				    		table.append(tr);
				    		$(res).each(function(i, obj) {
								var tr = $("<tr>").attr("data-accountNum",obj.accountNum).attr("data-custName",obj.custCode.custName).addClass("pickedOne");
								var a = []
								a[0] = $("<a>").html(obj.accountNum).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[1] = $("<a>").html(obj.custCode.custName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[2] = $("<a>").html(obj.accountPlanCode.planName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var str = obj.accountNum;
								var div = str.substring(8, 9)=='1'?"예금": str.substring(8, 9)=='2'?"적금":"마이너스";
								a[3] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var date = new Date(obj.accountOpenDate);
								var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2) + ' '+('0' + (date.getHours())).slice(-2)+ ':'+('0' + (date.getMinutes())).slice(-2)+ ':'+('0' + (date.getSeconds())).slice(-2);
								a[4] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var interestToPercent = obj.accountInterest * 100;
								a[5] = $("<a>").html(interestToPercent + "%").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								$(a).each(function(i, obj) {
									var td = $("<td>").append(a[i]);
									tr.append(td);
								})
								table.append(tr);
							})
							$("#table").append(table);
				    	}
				    }
				})
				break;
			case "상품명":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				if(search=="") {
					alert("상품명을 입력하세요");
					return;
				}
				$.ajax({
					url: "${pageContext.request.contextPath}/bankwork/bankbook/mgn.do?custdiv=${custdiv}",
				    data: {search:search,div:div},
				    type: "POST", 
				    dataType: "json",
				    success : function(res) {
				    	if(res.errorPlanName!=null) {
				    		alert("그런 상품은 없습니다. 다시 입력하세요");
				    		$("input[name='search']").val("");
				    	}
				    	else {
				    		console.log(res);
				    		$("#table table").remove();
				    		var table = $("<table>");
				    		var tr = $("<tr>");
				    		var th1 = $("<th>").html("계좌번호");
				    		var th2 = $("<th>").html("고객이름");
				    		var th3 = $("<th>").html("상품명");
				    		var th4 = $("<th>").html("통장구분");
				    		var th5 = $("<th>").html("계좌개설일");
				    		var th6 = $("<th>").html("이자율");
				    		tr.append(th1).append(th2).append(th3).append(th4).append(th5).append(th6);
				    		table.append(tr);
				    		$(res).each(function(i, obj) {
								var tr = $("<tr>").attr("data-accountNum",obj.accountNum).attr("data-custName",obj.custCode.custName).addClass("pickedOne");
								var a = []
								a[0] = $("<a>").html(obj.accountNum).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[1] = $("<a>").html(obj.custCode.custName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[2] = $("<a>").html(obj.accountPlanCode.planName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var str = obj.accountNum;
								var div = str.substring(8, 9)=='1'?"예금": str.substring(8, 9)=='2'?"적금":"마이너스";
								a[3] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var date = new Date(obj.accountOpenDate);
								var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2) + ' '+('0' + (date.getHours())).slice(-2)+ ':'+('0' + (date.getMinutes())).slice(-2)+ ':'+('0' + (date.getSeconds())).slice(-2);
								a[4] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var interestToPercent = obj.accountInterest * 100;
								a[5] = $("<a>").html(interestToPercent + "%").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								$(a).each(function(i, obj) {
									var td = $("<td>").append(a[i]);
									tr.append(td);
								})
								table.append(tr);
							})
							$("#table").append(table);
				    	}
				    }
				})
				break;
			case "통장상품":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				if(search=="") {
					alert("통장상품(예금,적금,마이너스 중 하나)을 입력하세요");
					return;
				}
				$.ajax({
					url: "${pageContext.request.contextPath}/bankwork/bankbook/mgn.do?custdiv=${custdiv}",
				    data: {search:search,div:div},
				    type: "POST", 
				    dataType: "json",
				    success : function(res) {
				    	if(res.errorNoDiv!=null) {
				    		alert("예금,적금,마이너스 통장 중 있는 상품만 입력하세요");
				    		$("input[name='search']").val("");
				    		return;
				    	}
				    	if(res.errorBankBookName!=null) {
				    		alert("예금,적금,마이너스만 입력하세요");
				    		$("input[name='search']").val("");
				    	}
				    	else {
				    		console.log(res);
				    		$("#table table").remove();
				    		var table = $("<table>");
				    		var tr = $("<tr>");
				    		var th1 = $("<th>").html("계좌번호");
				    		var th2 = $("<th>").html("고객이름");
				    		var th3 = $("<th>").html("상품명");
				    		var th4 = $("<th>").html("통장구분");
				    		var th5 = $("<th>").html("계좌개설일");
				    		var th6 = $("<th>").html("이자율");
				    		tr.append(th1).append(th2).append(th3).append(th4).append(th5).append(th6);
				    		table.append(tr);
				    		$(res).each(function(i, obj) {
								var tr = $("<tr>").attr("data-accountNum",obj.accountNum).attr("data-custName",obj.custCode.custName).addClass("pickedOne");
								var a = []
								a[0] = $("<a>").html(obj.accountNum).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[1] = $("<a>").html(obj.custCode.custName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								a[2] = $("<a>").html(obj.accountPlanCode.planName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var str = obj.accountNum;
								var div = str.substring(8, 9)=='1'?"예금": str.substring(8, 9)=='2'?"적금":"마이너스";
								a[3] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var date = new Date(obj.accountOpenDate);
								var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2) + ' '+('0' + (date.getHours())).slice(-2)+ ':'+('0' + (date.getMinutes())).slice(-2)+ ':'+('0' + (date.getSeconds())).slice(-2);
								a[4] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								var interestToPercent = obj.accountInterest * 100;
								a[5] = $("<a>").html(interestToPercent + "%").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.accountNum+"&custname="+obj.custCode.custName);
								$(a).each(function(i, obj) {
									var td = $("<td>").append(a[i]);
									tr.append(td);
								})
								table.append(tr);
							})
							$("#table").append(table);
				    	}
				    }
				})
				break;
			}
		})
		$("select").on("change", function() {
			$("table").load(location.href + " table");
			$("input[name='search']").val("");
		})
		$("#btnMenu1").click(function() {
			location.href = "${pageContext.request.contextPath}/bankwork/bankbook/mgn.do?div=${custdiv}";
		})
	})
</script>
</head>   
<body>
	<section>
	<jsp:include page="../../include/menu.jsp"/>
		<div id="search">
				<select id="searchMenu">
					<option>검색구분</option>
					<option>계좌번호</option>
					<option>고객이름</option>
					<option>상품명</option>
					<option>통장상품</option>
				</select>
					<fieldset><input type="search" name="search"/>
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
				
		</div>
		<button id="btnMenu1">통장 조회</button>
		<div id="table">
			<table>
				<tr>
					<th>계좌번호</th>
					<th>고객이름</th>
					<th>상품명</th>
					<th>통장구분</th>
					<th>계좌개설일</th>
					<th>이자율</th>
				</tr>
				<c:forEach var="bankbook" items="${list}">
				<tr>
					<td>${bankbook.accountNum}</td>
					<td>${bankbook.custCode.custName}</td>
					<td>${bankbook.accountPlanCode.planName}</td>
					<td>${fn:substring(bankbook.accountNum,8,9) eq '1'?'예금':fn:substring(bankbook.accountNum,8,9) eq '2'?'적금':'마이너스'}</td>
					<td><fmt:formatDate value="${bankbook.accountOpenDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
					<td><fmt:formatNumber value="${bankbook.accountInterest}" type="percent"/></td>
				</tr>
				</c:forEach>
			</table>
		</div>
	</section>
</body>
</html>