<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/menu.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://kit.fontawesome.com/6f2f0f2d95.js"></script>
<link href="${pageContext.request.contextPath}/css/listCSS.css" rel="stylesheet" />
</head>
<style>  		
		
		/* 테이블 영역 */
		div#table {
			width: 1000px;
			margin: 50px auto;
		}
		div#table p { font-family: 'Noto Sans KR', sans-serif; 
					  font-weight: bold;
					  height: 30px;}
						
		
		div#table table {
			border-collapse: collapse; 
		}
		
		div#table table a { display: block; width: 150px; }
		
		div#table th, td {
			width: 150px; 
			height: 30px;
			text-align: center;
			font-size: 15px;
		}
		
		div#table td.long a {
			width: 220px; 
		}

</style>
<script>
	$(function(){
		//전역변수
		 var ajax;
		 var div;
		 var search;
		//클릭한 메뉴만 보이게 하기
		$("#custAdd").show();
		$("#custList").show();      
		
		/* 검색 구분 조건 변경할 때 테이블 reload */
		$("select").on("change", function(){
				$(".tableList").load(location.href+" .tableList tr");
				$("input[name='search']").val("");
				$(".pagination").load(location.href+" .pagination li");  
		})       
		
		/* 검색 버튼 클릭 시 */         
		$("button").click(function() {  
			switch($("#searchMenu option:selected").val()) {
			case "검색 구분":   
				alert("검색 조건을 선택해주세요.");     
			//	$("input[name='search']").val("");
				break;
			case "고객 코드":
				div = $("#searchMenu option:selected").val();
				search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/cust/custSearch.do",
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
				    		
				    		var $th1 = $("<th>").html("고객 코드");
				    		var $th2 = $("<th>").html("고객명");
				    		var $th4 = $("<th>").html("고객 신용등급");
				    		var $th5 = $("<th>").html("고객 주소");
				    		var $th6 = $("<th>").html("연락처");
				    		var $th7 = $("<th>").html("분류");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th4);
				    		$tr1.append($th5);
				    		$tr1.append($th6);
				    		$tr1.append($th7);  
				    		$table.append($tr1);
				    		
				    		$(res.list).each(function(i, obj) {
				    			var $tr2 = $("<tr>");
				    			
				    			var $a1 = $("<a>").html(obj.custCode).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a2 = $("<a>").html(obj.custName).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a4 = $("<a>").html(obj.custCredit).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a5 = $("<a>").html(obj.custAddr).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a6 = $("<a>").html(obj.custTel).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a7;
					    		if(obj.custDiv==true){
					    			$a7 = $("<a>").html("기업").attr("href", "#");
					    		}else{
					    			$a7 = $("<a>").html("일반").attr("href", "#");
					    		}
				    			   
					    		
					    		var $td1 = $("<td>");
					    		var $td2 = $("<td>");
					    		var $td4 = $("<td>");
					    		var $td5 = $("<td>"); 
					    		var $td6 = $("<td>");
					    		var $td7 = $("<td>");
					    		
					    		$td1.append($a1);
					    		$td2.append($a2);
					    		$td4.append($a4);
					    		$td5.append($a5);
					    		$td6.append($a6);
					    		$td7.append($a7);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td4);
					    		$tr2.append($td5);
					    		$tr2.append($td6);
					    		$tr2.append($td7);
					    		
					    		
					    		$table.append($tr2);
					    		
				    		})      
				    		$("#table").append($table);
				    		$(".sorter").remove();
				    		$divSorter = $("<div>").addClass("sorter");
				    		$ulPaging = $("<ul>").addClass("pagination");
				    		$liPaging1 = $("<li>");
				    		$aPaging1 = $("<a>").attr("href", "#").addClass("prev").html("Prev");
				    		
				    		$liPaging1.append($aPaging1);
				    		$ulPaging.append($liPaging1);
				    		
				    		
				    		for(var i=res.paging.startPageNo; i<=res.paging.endPageNo; i++){
				    			$liPagingRepeat1 = $("<li>").addClass("active");
					    		$aPagingRepeat1 = $("<a>").attr("href", "#").addClass("page").html(i);
					    		
					    		$liPagingRepeat1.append($aPagingRepeat1);
					    		
					    		$ulPaging.append($liPagingRepeat1);
				    		}
				    		
				    		$liPaging2 = $("<li>");
				    		$aPaging2 = $("<a>").attr("href", "#").addClass("next").html("Next");
				    		
				    		$liPaging2.append($aPaging2);
				    		$ulPaging.append($liPaging2);
				    		
				    		$divSorter.append($ulPaging);
				    		
				    		$("#table").append($divSorter);
				    		ajax = true;
				    	 }    
				    }
				})   
				break;
			case "고객명":
				div = $("#searchMenu option:selected").val();
				search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/cust/custSearch.do",
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
				    		
				    		var $th1 = $("<th>").html("고객 코드");
				    		var $th2 = $("<th>").html("고객명");
				    		var $th4 = $("<th>").html("고객 신용등급");
				    		var $th5 = $("<th>").html("고객 주소");
				    		var $th6 = $("<th>").html("연락처");
				    		var $th7 = $("<th>").html("분류");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th4);
				    		$tr1.append($th5);
				    		$tr1.append($th6);
				    		$tr1.append($th7);
				    		$table.append($tr1);
				    		
				    		$(res.list).each(function(i, obj) {
								var $tr2 = $("<tr>");
				    			
								var $a1 = $("<a>").html(obj.custCode).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a2 = $("<a>").html(obj.custName).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a4 = $("<a>").html(obj.custCredit).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a5 = $("<a>").html(obj.custAddr).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a6 = $("<a>").html(obj.custTel).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a7;
					    		if(obj.custDiv==true){
					    			$a7 = $("<a>").html("기업").attr("href", "#");
					    		}else{
					    			$a7 = $("<a>").html("일반").attr("href", "#");
					    		}
					    		
					    		var $td1 = $("<td>");
					    		var $td2 = $("<td>");
					    		var $td4 = $("<td>");
					    		var $td5 = $("<td>"); 
					    		var $td6 = $("<td>");
					    		var $td7 = $("<td>");
					    		
					    		$td1.append($a1);
					    		$td2.append($a2);
					    		$td4.append($a4);
					    		$td5.append($a5);
					    		$td6.append($a6);
					    		$td7.append($a7);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td4);
					    		$tr2.append($td5);
					    		$tr2.append($td6);
					    		$tr2.append($td7);
					    		
					    		
					    		$table.append($tr2);
					    		
				    		})
				    		$("#table").append($table);
				    		$(".sorter").remove();
				    		$divSorter = $("<div>").addClass("sorter");
				    		$ulPaging = $("<ul>").addClass("pagination");
				    		$liPaging1 = $("<li>");
				    		$aPaging1 = $("<a>").attr("href", "#").addClass("prev").html("Prev");
				    		
				    		$liPaging1.append($aPaging1);
				    		$ulPaging.append($liPaging1);
				    		
				    		
				    		for(var i=res.paging.startPageNo; i<=res.paging.endPageNo; i++){
				    			$liPagingRepeat1 = $("<li>").addClass("active");
					    		$aPagingRepeat1 = $("<a>").attr("href", "#").addClass("page").html(i);
					    		
					    		$liPagingRepeat1.append($aPagingRepeat1);
					    		
					    		$ulPaging.append($liPagingRepeat1);
				    		}
				    		
				    		$liPaging2 = $("<li>");
				    		$aPaging2 = $("<a>").attr("href", "#").addClass("next").html("Next");
				    		
				    		$liPaging2.append($aPaging2);
				    		$ulPaging.append($liPaging2);
				    		
				    		$divSorter.append($ulPaging);
				    		
				    		$("#table").append($divSorter);
				    		ajax = true;
				    	 }    
				    }
				})
				break;
			case "연락처":
				div = $("#searchMenu option:selected").val();
				search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/cust/custSearch.do",
				    data: {search:search,div:div},  
				    type: "POST", 
				    dataType: "json", 
				    success : function(res) {
				    		console.log(res);
				    		if(res.error=="notExist") {
				    			alert("존재하지 않는 고객 입니다.");
				    		}
				    	else {
							$(".tableList").remove();
				    		
				    		var $table = $("<table>").addClass("tableList");
				    		var $tr1 = $("<tr>");
				    		
				    		var $th1 = $("<th>").html("고객 코드");
				    		var $th2 = $("<th>").html("고객명");
				    		var $th4 = $("<th>").html("고객 신용등급");
				    		var $th5 = $("<th>").html("고객 주소");
				    		var $th6 = $("<th>").html("연락처");
				    		var $th7 = $("<th>").html("분류");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th4);
				    		$tr1.append($th5);
				    		$tr1.append($th6);
				    		$tr1.append($th7);
				    		$table.append($tr1);
				    		
				    		$(res.list).each(function(i, obj) {
								var $tr2 = $("<tr>");
				    			
								var $a1 = $("<a>").html(obj.custCode).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a2 = $("<a>").html(obj.custName).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a4 = $("<a>").html(obj.custCredit).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a5 = $("<a>").html(obj.custAddr).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a6 = $("<a>").html(obj.custTel).attr("href", "${pageContext.request.contextPath}/cust/custDetail.do?custName="+obj.custName);
					    		var $a7;
					    		if(obj.custDiv==true){
					    			$a7 = $("<a>").html("기업").attr("href", "#");
					    		}else{
					    			$a7 = $("<a>").html("일반").attr("href", "#");
					    		}
					    		
					    		var $td1 = $("<td>");
					    		var $td2 = $("<td>");
					    		var $td4 = $("<td>");
					    		var $td5 = $("<td>"); 
					    		var $td6 = $("<td>");
					    		var $td7 = $("<td>");
					    		
					    		$td1.append($a1);
					    		$td2.append($a2);
					    		$td4.append($a4);
					    		$td5.append($a5);
					    		$td6.append($a6);
					    		$td7.append($a7);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td4);
					    		$tr2.append($td5);
					    		$tr2.append($td6);
					    		$tr2.append($td7);
					    		
					    		
					    		$table.append($tr2);
					    		
				    		})
				    		$("#table").append($table);
				    		$(".sorter").remove();
				    		$divSorter = $("<div>").addClass("sorter");
				    		$ulPaging = $("<ul>").addClass("pagination");
				    		$liPaging1 = $("<li>");
				    		$aPaging1 = $("<a>").attr("href", "#").addClass("prev").html("Prev");
				    		
				    		$liPaging1.append($aPaging1);
				    		$ulPaging.append($liPaging1);
				    		
				    		
				    		for(var i=res.paging.startPageNo; i<=res.paging.endPageNo; i++){
				    			$liPagingRepeat1 = $("<li>").addClass("active");
					    		$aPagingRepeat1 = $("<a>").attr("href", "#").addClass("page").html(i);
					    		
					    		$liPagingRepeat1.append($aPagingRepeat1);
					    		
					    		$ulPaging.append($liPagingRepeat1);
				    		}
				    		
				    		$liPaging2 = $("<li>");
				    		$aPaging2 = $("<a>").attr("href", "#").addClass("next").html("Next");
				    		
				    		$liPaging2.append($aPaging2);
				    		$ulPaging.append($liPaging2);
				    		
				    		$divSorter.append($ulPaging);
				    		
				    		$("#table").append($divSorter);
				    		ajax = true;
				    	 }    
				    }
				})   
				break;
				
			}
		})
		$("button").eq(1).click(function() {    
		//	$("input[name='search']").val("");   
		})
		
		//페이징 시작
		//페이지 각 번호 클릭 시  
		
		$(document).on("click", ".page",function() {
			if(ajax) {
				var page = $(this).html();
				ajax = false;
		        location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page+"&search="+search+"&div="+div;
			}
			else {
				if(isPagingAjax) {
					var page = $(this).html();
					div = $("#searchMenu option:selected").val();
					search = $("input[name='search']").val();
					location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page+"&search="+search+"&div="+div;
				}
				else {
					var page = $(this).html();
			        location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page;
				}
			}
			
		}) 
		
		
		/* 	$(document).on("click", ".page",function() {
			var page = $(this).html();
	        location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page;
		})    */
		
		//prev 클릭시 이전 번호로 돌아감 (paging.pageNo = 현재 페이지 넘버)
		
		
		$(document).on("click", ".prev" , function(){
			if(ajax) {
				var page = ${paging.pageNo}-1;
				//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
				if($(".page").size()==1){
					return false;       
				}
				ajax = false;
				location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page+"&search="+search+"&div="+div;
			}
			else {
				if(isPagingAjax) {
					var page = ${paging.pageNo}-1;
					if($(".page").size()==1){
						return false;       
					}
					div = $("#searchMenu option:selected").val();
					search = $("input[name='search']").val();
					//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
					location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page+"&search="+search+"&div="+div;
				}
				else {
					var page = ${paging.pageNo}-1;
					//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
					if($(".page").size()==1){
						return false;       
					}
					location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page;
				}
			}
			
		})
		
		
		
		/* $(document).on("click", ".prev" , function(){
			var page = ${paging.pageNo}-1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){
				return false;       
			}
			location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page;
		})  */
		
		
		
		
		
		
		//next 클릭시  다음 번호로 넘어감 (paging.pageNo = 현재 페이지 넘버)    
		
		   
		$(document).on("click", ".next" , function(){
			if(ajax) {
				var page = ${paging.pageNo}+1;
				if($(".page").size()==1){         
					return false;   
				} 
				ajax = false;
				location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page+"&search="+search+"&div="+div;
			}
			else {
				if(isPagingAjax) {
					var page = ${paging.pageNo}+1;
					if($(".page").size()==1){         
						return false;   
					} 
					div = $("#searchMenu option:selected").val();
					search = $("input[name='search']").val();
					if($(".page").size()==1){         
						return false;   
					}
					location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page+"&search="+search+"&div="+div;
				}
				else {
					var page = ${paging.pageNo}+1;
					//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
					if($(".page").size()==1){         
						return false;   
					}       
					location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page;
				}
			}
			
		})
		
		
		
		/* $(document).on("click", ".next" , function(){
			var page = ${paging.pageNo}+1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){         
				return false;   
			}       
			location.href = "${pageContext.request.contextPath}/cust/custSearch.do?page="+page;
		}) */
		   
		//페이징 끝
		
		
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
		
		var isPagingAjax = "${pagingAjax}"=="true"?true:false;
	})
</script>
<body>
	<section>
	<%@include file="../include/sectionBar.jsp"%>
	<!-- paging c:if -->
	<c:if test="${pagingAjax=='true'}">
		<script>
			$(function(){
				var search = "${search}";
				var div = "${searchdiv}";
				$("#searchMenu option").each(function(i, obj) {
					if($(obj).val()==div) {
						$(obj).prop("selected",true);
					}
				})
				$("input[name='search']").val(search);
			})
		</script>
	</c:if>
		<h2 id="menuLocation">고객 조회</h2>   
		<div id="search">   

				<select id="searchMenu">
					<option>검색 구분</option>
					<option>고객 코드</option>
					<option>고객명</option>
					<option>연락처</option>   
					
				</select>
			
					<fieldset><input type="search" name="search" placeholder="검색어를 입력하세요."/>
						<button type="submit" id="searchBtn">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
				
		</div>  
		<div id="table">
			<p><i class="fas fa-user"></i> VIP 고객 : ${vipNum } 명</p>
			<p><i class="far fa-user"></i> 일반 고객 : ${norNum } 명</p>
			<span><i class="fas fa-exclamation-circle"></i></span><span id="guide">고객 상세정보를 보려면 고객을 클릭하세요.</span>
			<table class="tableList">
				<tr>
					<th>고객 코드</th>
					<th>고객명</th>
					<th>고객 신용등급</th>
					<th>고객 주소</th>
					<th>연락처</th>
					<th>분류</th>

				</tr>
				<c:forEach var='custList' items="${list }">
					
					<tr>
							<td><a href="${pageContext.request.contextPath}/cust/custDetail.do?custName=${custList.custName}">${custList.custCode }</a></td>
							<td><a href="${pageContext.request.contextPath}/cust/custDetail.do?custName=${custList.custName}">${custList.custName }</a></td>
							<td><a href="${pageContext.request.contextPath}/cust/custDetail.do?custName=${custList.custName}">${custList.custCredit }</a></td>
							<td class="long"><a href="${pageContext.request.contextPath}/cust/custDetail.do?custName=${custList.custName}">${custList.custAddr }</a></td>
							<td><a href="${pageContext.request.contextPath}/cust/custDetail.do?custName=${custList.custName}">${custList.custTel }</a></td>
							<c:if test="${custList.custDiv==true}">
								<td><a href="#">기업</a></td>
							</c:if>
							<c:if test="${custList.custDiv==false}">
								<td><a href="#">일반</a></td>
							</c:if>
							  
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
		<c:if test="${delSuccess!=null}">
			<script>
				alert("삭제되었습니다.");
			</script>
			<%
				session.removeAttribute("delSuccess");
			%>
		</c:if>
</body>
</html>