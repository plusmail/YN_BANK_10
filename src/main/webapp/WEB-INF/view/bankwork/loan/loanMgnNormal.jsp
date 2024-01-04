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
<script src="https://kit.fontawesome.com/6f2f0f2d95.js"></script>
<link href="${pageContext.request.contextPath}/css/listCSS.css" rel="stylesheet" />
<style>
		
		h2#menuLocation { width : 250px;
						  height: 60px;  
						  line-height: 60px; 
						  margin: 0 500px;      
						  border-bottom: 2px solid #e9ebec;} 
		div#search {
			width: 900px;
			margin: 50px 0;
			margin-left : 600px;
			text-align: center;
		}
		
		
		div#table {
			width : 1300px;
			margin: 100px 0;
			margin-left : 400px;
		}
			
		div#table th, td {
			width: 200px; 
			height: 30px;
			text-align: center;
			font-size: 13px;
		}
		
		div#dummy { height: 75px; background: #292929;}	
		
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
		var insertTotalCount = function() {
			$(".pickedOne").each(function(i, obj) {
				var str1 = $(obj).find("td").eq(4).html();
				var str2 = $(obj).find("td").eq(5).html();
				var str3 = $(obj).find("td").eq(6).html();
				var loanStartDate = new Date(str1);
				var loanDelayDate = new Date(str2);
				var loanExpireDate = new Date(str3);
				var yearDiff = loanExpireDate.getFullYear() - loanStartDate.getFullYear();
				var calCount = yearDiff * 12;
				$(".count").eq(i).html(calCount + "회차");
			})
		};
		insertTotalCount();
		$("#loanAdd").show();
		$("#loanList").show();
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
					url: "${pageContext.request.contextPath}/bankwork/loan/mgn.do?custdiv=${custdiv}",
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
				    		var th4 = $("<th>").html("대출구분");
				    		var th5 = $("<th>").html("대출시작일");
				    		var th6 = $("<th>").html("거치일");
				    		var th7 = $("<th>").html("대출만기일");
				    		var th8 = $("<th>").html("대출방식");
				    		var th9 = $("<th>").html("대출총납입회차");
				    		var th10 = $("<th>").html("대출이자");
				    		var th11 = $("<th>").html("대출금액");
				    		var th12 = $("<th>").html("대출연장여부");
				    		tr.append(th1).append(th2).append(th3).append(th4).append(th5).append(th6).append(th7).append(th8).append(th9).append(th10).append(th11).append(th12);
				    		table.append(tr);
							$(res).each(function(i, obj) {
								var tr = $("<tr>").attr("data-accountNum",obj.loanAccountNum).attr("data-custName",obj.custCode.custName).addClass("pickedOne");
								var a = []
								a[0] = $("<a>").html(obj.loanAccountNum).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[1] = $("<a>").html(obj.custCode.custName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[2] = $("<a>").html(obj.planCode.planName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var str = obj.loanAccountNum;
								var div = str.substring(8, 9)=='1'?"일반대출": str.substring(8, 9)=='2'?"신용대출":"카드론";
								a[3] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var date = new Date(obj.loanStartDate);
								var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2);
								a[4] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var delayDate = new Date(obj.loanDelayDate);
								dateFormat = delayDate.getFullYear() + '-' +('0' + (delayDate.getMonth()+1)).slice(-2)+ '-' +  ('0' + delayDate.getDate()).slice(-2);
								a[5] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var expireDate = new Date(obj.loanExpireDate);
								dateFormat = expireDate.getFullYear() + '-' +('0' + (expireDate.getMonth()+1)).slice(-2)+ '-' +  ('0' + expireDate.getDate()).slice(-2);
								a[6] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								div = obj.loanMethod=='A'?"만기일시상환":"원금균등상환";
								a[7] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var totalCount = (expireDate.getFullYear() - date.getFullYear()) * 12;
								a[8] = $("<a>").html(totalCount + "회차").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var interestToPercent = Math.floor(obj.loanInterest * 100);
								a[9] = $("<a>").html(interestToPercent + "%").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[10] = $("<a>").html(obj.loanBalance.toLocaleString()).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[11] = $("<a>").html(obj.loanExtended=="0"?"연장가능":"연장불가").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
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
					url: "${pageContext.request.contextPath}/bankwork/loan/mgn.do?custdiv=${custdiv}",
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
				    		var th4 = $("<th>").html("대출구분");
				    		var th5 = $("<th>").html("대출시작일");
				    		var th6 = $("<th>").html("거치일");
				    		var th7 = $("<th>").html("대출만기일");
				    		var th8 = $("<th>").html("대출방식");
				    		var th9 = $("<th>").html("대출총납입회차");
				    		var th10 = $("<th>").html("대출이자");
				    		var th11 = $("<th>").html("대출금액");
				    		var th12 = $("<th>").html("대출연장여부");
				    		tr.append(th1).append(th2).append(th3).append(th4).append(th5).append(th6).append(th7).append(th8).append(th9).append(th10).append(th11).append(th12);
				    		table.append(tr);
							$(res).each(function(i, obj) {
								var tr = $("<tr>").attr("data-accountNum",obj.loanAccountNum).attr("data-custName",obj.custCode.custName).addClass("pickedOne");
								var a = []
								a[0] = $("<a>").html(obj.loanAccountNum).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[1] = $("<a>").html(obj.custCode.custName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[2] = $("<a>").html(obj.planCode.planName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var str = obj.loanAccountNum;
								var div = str.substring(8, 9)=='1'?"일반대출": str.substring(8, 9)=='2'?"신용대출":"카드론";
								a[3] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var date = new Date(obj.loanStartDate);
								var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2);
								a[4] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var delayDate = new Date(obj.loanDelayDate);
								dateFormat = delayDate.getFullYear() + '-' +('0' + (delayDate.getMonth()+1)).slice(-2)+ '-' +  ('0' + delayDate.getDate()).slice(-2);
								a[5] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var expireDate = new Date(obj.loanExpireDate);
								dateFormat = expireDate.getFullYear() + '-' +('0' + (expireDate.getMonth()+1)).slice(-2)+ '-' +  ('0' + expireDate.getDate()).slice(-2);
								a[6] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								div = obj.loanMethod=='A'?"만기일시상환":"원금균등상환";
								a[7] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var totalCount = (expireDate.getFullYear() - date.getFullYear()) * 12;
								a[8] = $("<a>").html(totalCount + "회차").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var interestToPercent = Math.floor(obj.loanInterest * 100);
								a[9] = $("<a>").html(interestToPercent + "%").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[10] = $("<a>").html(obj.loanBalance.toLocaleString()).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[11] = $("<a>").html(obj.loanExtended=="0"?"연장가능":"연장불가").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
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
					url: "${pageContext.request.contextPath}/bankwork/loan/mgn.do?custdiv=${custdiv}",
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
				    		var th4 = $("<th>").html("대출구분");
				    		var th5 = $("<th>").html("대출시작일");
				    		var th6 = $("<th>").html("거치일");
				    		var th7 = $("<th>").html("대출만기일");
				    		var th8 = $("<th>").html("대출방식");
				    		var th9 = $("<th>").html("대출총납입회차");
				    		var th10 = $("<th>").html("대출이자");
				    		var th11 = $("<th>").html("대출금액");
				    		var th12 = $("<th>").html("대출연장여부");
				    		tr.append(th1).append(th2).append(th3).append(th4).append(th5).append(th6).append(th7).append(th8).append(th9).append(th10).append(th11).append(th12);
				    		table.append(tr);
							$(res).each(function(i, obj) {
								var tr = $("<tr>").attr("data-accountNum",obj.loanAccountNum).attr("data-custName",obj.custCode.custName).addClass("pickedOne");
								var a = []
								a[0] = $("<a>").html(obj.loanAccountNum).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[1] = $("<a>").html(obj.custCode.custName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[2] = $("<a>").html(obj.planCode.planName).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var str = obj.loanAccountNum;
								var div = str.substring(8, 9)=='1'?"일반대출": str.substring(8, 9)=='2'?"신용대출":"카드론";
								a[3] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var date = new Date(obj.loanStartDate);
								var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2);
								a[4] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var delayDate = new Date(obj.loanDelayDate);
								dateFormat = delayDate.getFullYear() + '-' +('0' + (delayDate.getMonth()+1)).slice(-2)+ '-' +  ('0' + delayDate.getDate()).slice(-2);
								a[5] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var expireDate = new Date(obj.loanExpireDate);
								dateFormat = expireDate.getFullYear() + '-' +('0' + (expireDate.getMonth()+1)).slice(-2)+ '-' +  ('0' + expireDate.getDate()).slice(-2);
								a[6] = $("<a>").html(dateFormat).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								div = obj.loanMethod=='A'?"만기일시상환":"원금균등상환";
								a[7] = $("<a>").html(div).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var totalCount = (expireDate.getFullYear() - date.getFullYear()) * 12;
								a[8] = $("<a>").html(totalCount + "회차").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								var interestToPercent = Math.floor(obj.loanInterest * 100);
								a[9] = $("<a>").html(interestToPercent + "%").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[10] = $("<a>").html(obj.loanBalance.toLocaleString()).attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
								a[11] = $("<a>").html(obj.loanExtended=="0"?"연장가능":"연장불가").attr("href","${pageContext.request.contextPath}/bankwork/bankbook/detail.do?accountnum="+obj.loanAccountNum+"&custname="+obj.custCode.custName);
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
			$(document).on("load","table",function(){
				$("table").load(location.href + " table");
				insertTotalCount();
			})
			$("input[name='search']").val("");
		})
		
		$(".pickedOne").click(function(){
		  var accountNumForPick = $(this).attr("data-accountNum");
		  var custNameForPick = $(this).attr("data-custName");
		  
		  location.href="${pageContext.request.contextPath}/bankwork/loan/detail.do?loanaccountnum="+accountNumForPick+"&custname="+custNameForPick;
	 	})
	 	$(document).on("click",'.pickedOne',function() {
	  		var accountNumForPick = $(this).attr("data-accountNum");
			var custNameForPick = $(this).attr("data-custName");
			  
			location.href="${pageContext.request.contextPath}/bankwork/loan/detail.do?loanaccountnum="+accountNumForPick+"&custname="+custNameForPick;
	  	});
		 //페이지 각 번호 클릭 시  
		$(document).on("click", ".page",function() {
			var page = $(this).html();
	        location.href = "${pageContext.request.contextPath}/bankwork/loan/mgn.do?div=0&page="+page;
		})   
		
		//prev 클릭시 이전 번호로 돌아감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".prev" , function(){
			var page = ${paging.pageNo}-1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){
				return false;       
			}
			location.href = "${pageContext.request.contextPath}/bankwork/loan/mgn.do?div=0&page="+page;
		})  
		//next 클릭시  다음 번호로 넘어감 (paging.pageNo = 현재 페이지 넘버)    
		$(document).on("click", ".next" , function(){
			var page = ${paging.pageNo}+1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){         
				return false;   
			}       
			location.href = "${pageContext.request.contextPath}/bankwork/loan/mgn.do?div=0&page="+page;
		})
		   
		$(document).on("mouseover", ".page", function(){
			$(this).css("background", "goldenrod");
		})
		$(document).on("mouseout", ".page", function(){  
			$(this).css("background", "#fff");
		})
		
		$(document).on("mouseover", ".prev", function(){
			$(this).css("background", "goldenrod");
		})
		
		$(document).on("mouseover", ".next", function(){
			$(this).css("background", "goldenrod");
		})
		
		$(document).on("mouseout", ".prev", function(){  
			$(this).css("background", "#fff");
		})
		
		$(document).on("mouseout", ".next", function(){  
			$(this).css("background", "#fff");        
		})
	})
</script>
</head>
<body>
	<section>
	<jsp:include page="../../include/menu.jsp"/>
	<h2 id="menuLocation">일반 고객 대출 정보 조회</h2>
		<div id="search">
				<select id="searchMenu">
					<option>검색구분</option>
					<option>계좌번호</option>
					<option>고객이름</option>
					<option>상품명</option>
				</select>
				<fieldset>
					<input type="search" name="search" placeholder="검색어를 입력하세요."/>
					<button type="submit">
						<i class="fa fa-search"></i>
					</button>	
				</fieldset>
		</div>
		<div id="table">
		<span><i class="fas fa-exclamation-circle"></i></span><span id="guide">대출 세부 정보를 보려면 대출을 클릭하세요.</span>
			<table>
				<tr>
					<th>계좌번호</th>
					<th>고객이름</th>
					<th>상품명</th>
					<th>대출구분</th>
					<th>대출시작일</th>
					<th>거치일</th>
					<th>대출만기일</th>
					<th>대출방식</th>
					<th>대출총납입회차</th>
					<th>대출이자</th>
					<th>대출금액</th>
					<th>대출연장여부</th>
				</tr>
				<c:forEach var="loan" items="${list}">
				<tr class="pickedOne" data-accountNum="${loan.loanAccountNum}" data-custName="${loan.custCode.custName}">
					<td>${loan.loanAccountNum}</td>
					<td>${loan.custCode.custName}</td>
					<td>${loan.planCode.planName}</td>
					<td>${fn:substring(loan.loanAccountNum,8,9) eq '1'?'일반대출':fn:substring(loan.loanAccountNum,8,9) eq '2'?'신용대출':'카드론'}</td>
					<td><fmt:formatDate value="${loan.loanStartDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${loan.loanDelayDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${loan.loanExpireDate}" pattern="yyyy-MM-dd"/></td>
					<td>${loan.loanMethod eq 'A'?'만기일시상환':'원금균등상환'}</td>
					<td class="count"></td>
					<td><fmt:formatNumber value="${loan.loanInterest}" type="percent"/></td>
					<td><fmt:formatNumber value="${loan.loanBalance}" type="number" maxFractionDigits="3"/></td>
					<td>${loan.loanExtended eq true?'연장불가':'연장가능'}</td>
				</tr>
				</c:forEach>
			</table>
			<div class="sorter">   
		      <ul class="pagination">
		        <li><a href="#" class="prev">Prev</a></li>
		              <c:forEach var="i" begin="${paging.startPageNo}" end="${paging.endPageNo}" step="1">
		                  <c:choose>
		                      <c:when test="${i eq paging.pageNo}">
		                <li class="active"><a href="#" class="page">${i}</a></li>
		                      </c:when>
		                      <c:otherwise>
		                        <li><a href="#" class="page">${i}</a></li>
		                      </c:otherwise>
		                  </c:choose>
		              </c:forEach>
		        <li><a href="#" class="next">Next</a></li>
		      </ul>
		    </div> 
		</div>
		<c:if test="${successadd!=null}">
    	<script>
    		alert("추가되었습니다");
    		<%
    			session.removeAttribute("successadd");
    		%>
    	</script>
    	</c:if>
		<c:if test="${finishrepayment!=null}">
		<script>
			alert("모든 상환이 완료되었습니다");
			<%
				session.removeAttribute("finishrepayment");
			%>
		</script>
		</c:if>
		<c:if test="${successrepayment!=null}">
			<script>
				alert("상환되었습니다");
			</script>
			<%
				session.removeAttribute("successrepayment");
			%>
		</c:if>
	</section>
</body>
</html>