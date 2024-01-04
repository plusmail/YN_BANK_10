<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@include file="../include/menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link rel="stylesheet" href="http://code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" type="text/css" />  
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>  
<script src="http://code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://kit.fontawesome.com/6f2f0f2d95.js"></script>
<link href="${pageContext.request.contextPath}/css/listCSS.css" rel="stylesheet" />
</head>
<style>
		
		/* 테이블 부분 */
		div#table {
			width: 1000px;
			margin: 20px auto;  
		}
		
		/* 테이블 위에 표시되는 정보 */
		
		button#seeAll { width: 100px; 
						border-radius: 10px;   
						background-image: none;
						font-weight: bold; }  
				       
		div#table p { font-weight: bold;      
					  width: 500px;    
					  height: 30px;       
					  padding: 10px;  }   
		div#table p#datePick span { display: block; 
								    float: left;
								    line-height: 30px; }	  
		div#table span#picker { width: 200px; 
							    text-align: right;  text-indent: 3px;
							    margin: 0 5px; }
		div#table span#pickSearch { width: 50px; }	  
		div#table span#pickSearch a { display: block; width: 50px; }		    					    
		
		div#table table.tableList {    
			clear: both;  
			border-collapse: collapse;  
			margin-top: 10px;  
		}        
		
		div#table th, div#table td {
			width: 180px;    
			height: 30px;
			text-align: center;   
			font-size: 15px;
		}
		
	
		div#table td.long {
			width: 200px; 
			text-align: right;
		}
=

</style>
<script>
	$(function(){
		//클릭한 메뉴만 표시되기
		$(".side2").hide();
		$("#dwMgn").find(".side2").toggle();
		
		//검색 구분의 검색 조건 변경할 때마다 테이블 다시 reload
		$("select").on("change", function(){
				$("table").load(location.href+" table");
				$("input[name='search']").val("");   
				//$(".pagination").load(location.href+" .pagination li");
		})
		
		//전체 보기 버튼
		$("#seeAll").on("click", function(){
				$(".tableList").load(window.location.href+" .tableList tr"); 
		})
		  
		//검색어 입력 후 검색 버튼 클릭 시
		$("#searchBtn").click(function() {  
			switch($("#searchMenu option:selected").val()) {
			
			case "검색 구분":
				alert("검색 조건을 선택해주세요.");
				$("input[name='search']").val("");     
				break;
			case "계좌 번호":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/cust/custDWRecord.do",
				    data: {search:search,div:div},  
				    type: "POST", 
				    dataType: "json", 
				    success : function(res) {
				    		//console.log(res);
				    		if(res.error=="notExist") {
				    			alert("존재하지 않는 고객 입니다.");
				    		}
				    	else {
				    		$(".tableList").remove();
				    		
				    		var $table = $("<table>").addClass("tableList");
				    		var $tr1 = $("<tr>");
				    		
				    		var $th1 = $("<th>").html("분류");
				    		var $th2 = $("<th>").html("고객명");
				    		var $th3 = $("<th>").html("계좌번호");
				    		var $th4 = $("<th>").html("금액");
				    		var $th5 = $("<th>").html("잔액");
				    		var $th6 = $("<th>").html("처리 일시");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th3);
				    		$tr1.append($th4);
				    		$tr1.append($th5);
				    		$tr1.append($th6);
				    		$table.append($tr1);
				    		
				    		$(res).each(function(i, obj) {
								var $tr2 = $("<tr>");
				    			
					    		var date = new Date(obj.accountTransDate);
					    		var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2) + ' '+('0' + (date.getHours())).slice(-2)+ ':'+('0' + (date.getMinutes())).slice(-2)+ ':'+('0' + (date.getSeconds())).slice(-2);
					    		
					    		
					    		var $td1 = $("<td>").html(obj.dw);
					    		var $td2 = $("<td>").html(obj.custName);
					    		var $td3 = $("<td>").html(obj.accountNum);
					    		var $td4 = $("<td class='alright'>").html(obj.amount+"원");
					    		var $td5 = $("<td class='alright'>").html(obj.accountBalance+"원"); 
					    		var $td6 = $("<td>").html(dateFormat);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td3);
					    		$tr2.append($td4);
					    		$tr2.append($td5);
					    		$tr2.append($td6);
					    		
					    		$table.append($tr2);
					    		
				    		})      
				    		$("#table").append($table);
				    		$(".sorter").remove();
				    		
				    		/* $(".sorter").remove();
				    		$divSorter = $("<div>").addClass("sorter");
				    		$ulPaging = $("<ul>").addClass("pagination");
				    		$liPaging1 = $("<li>");
				    		$aPaging1 = $("<a>").attr("href", "#").addClass("prev").html("Prev");
				    		
				    		$liPaging1.append($aPaging1);
				    		$ulPaging.append($liPaging1);
				    		
				    		
				    		for(var i=res.paging.startPageNo; i<=res.paging.endPageNo; i++){
				    			$liPagingRepeat1 = $("<li>").addClass("active");
					    		$aPagingRepeat1 = $("<a>").attr("href", "#").addClass("page").html(i);
					    		$liPagingRepeat2 = $("<li>");
					    		$aPagingRepeat2 = $("<a>").attr("href", "#").addClass("page").html(i);
					    		
					    		$liPagingRepeat1.append($aPagingRepeat1);
					    		
					    		$ulPaging.append($liPagingRepeat1);
				    		}
				    		
				    		$liPaging2 = $("<li>");
				    		$aPaging2 = $("<a>").attr("href", "#").addClass("next").html("Next");
				    		
				    		$liPaging2.append($aPaging2);
				    		$ulPaging.append($liPaging2);
				    		
				    		$divSorter.append($ulPaging);
				    		
				    		$("#table").append($divSorter); */
				    	 }    
				    }
				})   
				break;
			case "고객명":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/cust/custDWRecord.do",
				    data: {search:search,div:div},  
				    type: "POST", 
				    dataType: "json", 
				    success : function(res) {
				    		//console.log(res);
				    		if(res.error=="notExist") {
				    			alert("존재하지 않는 고객 입니다.");
				    		}
				    	else {
				    		$(".tableList").remove();
				    		
				    		var $table = $("<table>").addClass("tableList");
				    		var $tr1 = $("<tr>");
				    		
				    		var $th1 = $("<th>").html("분류");
				    		var $th2 = $("<th>").html("고객명");
				    		var $th3 = $("<th>").html("계좌번호");
				    		var $th4 = $("<th>").html("금액");
				    		var $th5 = $("<th>").html("잔액");
				    		var $th6 = $("<th>").html("처리 일시");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th3);
				    		$tr1.append($th4);
				    		$tr1.append($th5);
				    		$tr1.append($th6);
				    		$table.append($tr1);
				    		
				    		$(res).each(function(i, obj) {
								var $tr2 = $("<tr>");
				    			
					    		var date = new Date(obj.accountTransDate);
					    		var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2) + ' '+('0' + (date.getHours())).slice(-2)+ ':'+('0' + (date.getMinutes())).slice(-2)+ ':'+('0' + (date.getSeconds())).slice(-2);
					    		
					    		
					    		var $td1 = $("<td>").html(obj.dw);
					    		var $td2 = $("<td>").html(obj.custName);
					    		var $td3 = $("<td>").html(obj.accountNum);
					    		var $td4 = $("<td class='alright'>").html(obj.amount+"원");
					    		var $td5 = $("<td class='alright'>").html(obj.accountBalance+"원"); 
					    		var $td6 = $("<td>").html(dateFormat);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td3);
					    		$tr2.append($td4);
					    		$tr2.append($td5);
					    		$tr2.append($td6);
					    		
					    		$table.append($tr2);
					    		
				    		})
				    		$("#table").append($table);
				    		$(".sorter").remove();
				    		/* $(".sorter").remove();
				    		$divSorter = $("<div>").addClass("sorter");
				    		$ulPaging = $("<ul>").addClass("pagination");
				    		$liPaging1 = $("<li>");
				    		$aPaging1 = $("<a>").attr("href", "#").addClass("prev").html("Prev");
				    		
				    		$liPaging1.append($aPaging1);
				    		$ulPaging.append($liPaging1);
				    		
				    		
				    		for(var i=res.paging.startPageNo; i<=res.paging.endPageNo; i++){
				    			$liPagingRepeat1 = $("<li>").addClass("active");
					    		$aPagingRepeat1 = $("<a>").attr("href", "#").addClass("page").html(i);
					    		$liPagingRepeat2 = $("<li>");
					    		$aPagingRepeat2 = $("<a>").attr("href", "#").addClass("page").html(i);
					    		
					    		$liPagingRepeat1.append($aPagingRepeat1);
					    		
					    		$ulPaging.append($liPagingRepeat1);
				    		}
				    		
				    		$liPaging2 = $("<li>");
				    		$aPaging2 = $("<a>").attr("href", "#").addClass("next").html("Next");
				    		
				    		$liPaging2.append($aPaging2);
				    		$ulPaging.append($liPaging2);
				    		
				    		$divSorter.append($ulPaging);
				    		
				    		$("#table").append($divSorter); */
				    	 }    
				    }
				})
				break;
			
				
			}
		})
		$("#pickSearch a").click(function(){
			var date = $("#date").val();
			if(date==""){
				alert("날짜를 선택해주세요.");
				return false;
			}
			
			$.ajax({
				url: "${pageContext.request.contextPath}/cust/custDWRecordDate.do",
			    data: {date:date},  
			    type: "post", 
			    dataType: "json", 
			    success : function(res) {
			    	console.log(res);
			    	if(res.nodata=="nodata") {
		    			alert("해당 날짜에 데이터가 존재하지 않습니다.");           
		    		}else{
		    			$(".tableList").remove();
			    		
			    		var $table = $("<table>").addClass("tableList");
			    		var $tr1 = $("<tr>");
			    		
			    		var $th1 = $("<th>").html("분류");
			    		var $th2 = $("<th>").html("고객명");
			    		var $th3 = $("<th>").html("계좌번호");
			    		var $th4 = $("<th>").html("금액");
			    		var $th5 = $("<th>").html("잔액");
			    		var $th6 = $("<th>").html("처리 일시");
			    		
			    		$tr1.append($th1);
			    		$tr1.append($th2);
			    		$tr1.append($th3);
			    		$tr1.append($th4);
			    		$tr1.append($th5);
			    		$tr1.append($th6);
			    		$table.append($tr1);
			    		
			    		$(res).each(function(i, obj) {
							var $tr2 = $("<tr>");
			    			
				    		var date = new Date(obj.accountTransDate);
				    		var dateFormat = date.getFullYear() + '-' +('0' + (date.getMonth()+1)).slice(-2)+ '-' +  ('0' + date.getDate()).slice(-2) + ' '+('0' + (date.getHours())).slice(-2)+ ':'+('0' + (date.getMinutes())).slice(-2)+ ':'+('0' + (date.getSeconds())).slice(-2);
				    		
				    		
				    		var $td1 = $("<td>").html(obj.dw);
				    		var $td2 = $("<td>").html(obj.custName);  
				    		var $td3 = $("<td>").html(obj.accountNum);
				    		var $td4 = $("<td class='alright'>").html(obj.amount.toLocaleString()+"원");
				    		var $td5 = $("<td class='alright'>").html(obj.accountBalance.toLocaleString()+"원"); 
				    		var $td6 = $("<td>").html(dateFormat);
				    		
				    		$tr2.append($td1);
				    		$tr2.append($td2);
				    		$tr2.append($td3);
				    		$tr2.append($td4);
				    		$tr2.append($td5);
				    		$tr2.append($td6);
				    		
				    		$table.append($tr2);
				    		
			    		})      
			    		$("#table").append($table);
			    		$(".sorter").remove();
			    		/* $(".sorter").remove();
			    		$divSorter = $("<div>").addClass("sorter");
			    		$ulPaging = $("<ul>").addClass("pagination");
			    		$liPaging1 = $("<li>");
			    		$aPaging1 = $("<a>").attr("href", "#").addClass("prev").html("Prev");
			    		
			    		$liPaging1.append($aPaging1);
			    		$ulPaging.append($liPaging1);
			    		
			    		
			    		for(var i=res.paging.startPageNo; i<=res.paging.endPageNo; i++){
			    			$liPagingRepeat1 = $("<li>").addClass("active");
				    		$aPagingRepeat1 = $("<a>").attr("href", "#").addClass("page").html(i);
				    		$liPagingRepeat2 = $("<li>");
				    		$aPagingRepeat2 = $("<a>").attr("href", "#").addClass("page").html(i);
				    		
				    		$liPagingRepeat1.append($aPagingRepeat1);
				    		
				    		$ulPaging.append($liPagingRepeat1);
			    		}
			    		
			    		$liPaging2 = $("<li>");
			    		$aPaging2 = $("<a>").attr("href", "#").addClass("next").html("Next");
			    		
			    		$liPaging2.append($aPaging2);
			    		$ulPaging.append($liPaging2);
			    		
			    		$divSorter.append($ulPaging);
			    		
			    		$("#table").append($divSorter); */
		    		}
			    }
		})  
		
	});
		
		$("button").eq(1).click(function() {   
			$("input[name='search']").val("");
		})
		
		$("#date").datepicker({
            dateFormat: 'yy-mm-dd',
            onSelect: function(datetext) {
                var d = new Date(); // for now
                datetext = datetext;
                $('#date').val(datetext);
            }
        });
		
		 //페이지 각 번호 클릭 시  
		$(document).on("click", ".page",function() {
			var page = $(this).html();
	        location.href = "${pageContext.request.contextPath}/cust/custDWRecord.do?page="+page;
		})   
		
		//prev 클릭시 이전 번호로 돌아감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".prev" , function(){
			var page = ${paging.pageNo}-1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){
				return false;       
			}
			location.href = "${pageContext.request.contextPath}/cust/custDWRecord.do?page="+page;
		})  
		//next 클릭시  다음 번호로 넘어감 (paging.pageNo = 현재 페이지 넘버)    
		$(document).on("click", ".next" , function(){
			var page = ${paging.pageNo}+1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){         
				return false;   
			}       
			location.href = "${pageContext.request.contextPath}/cust/custDWRecord.do?page="+page;
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
	});
</script>
<body>
	<section>
	<%@include file="../include/sectionBar.jsp"%>
		<h2 id="menuLocation">입/출금 내역 조회</h2>
		<div id="search">
				<select id="searchMenu">
					<option>검색 구분</option>
					<option>계좌 번호</option>
					<option>고객명</option>	
				</select>
				
					<fieldset><input type="search" name="search" placeholder="검색어를 입력하세요." autocomplete="off"/>
						<button type="submit" id="searchBtn">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
		</div>      
		<div id="table">  
			<p><button id="seeAll">전체보기</button></p>
			<p id="datePick">
				<span><i class="far fa-calendar-alt"></i> 날짜별 검색</span> 
				<span id="picker"><input type="text" id="date" name="date" placeholder="날짜를 선택하려면 클릭하세요."></span>
				<span id="pickSearch"><a href="#"><i class="fas fa-search"></i></a></span>
			</p>
			<table class="tableList">
				<tr>
					<th>분류</th>
					<th>고객명</th>
					<th>계좌번호</th>    
					<th>금액</th>
					<th>잔액</th>
					<th>처리 일시</th>
				</tr>
				<c:forEach var='audit' items="${list }">
					<tr>
							<td>${audit.dw }</td>
							<td>${audit.custName }</td>
							<td>${audit.accountNum }</td>
							<td class='alright'><fmt:formatNumber value="${audit.amount}" groupingUsed="true"/>원</td>
							<td class="long"><fmt:formatNumber value="${audit.accountBalance}" groupingUsed="true"/>원</td>
							<td class="long"><fmt:formatDate value="${audit.accountTransDate }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
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
		</section>
</body>
</html>