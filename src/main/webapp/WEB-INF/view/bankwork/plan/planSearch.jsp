<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../include/menu.jsp"%>

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
	
		h2#menuLocation { width: 200px; height: 60px;  
						  line-height: 60px; 
						  margin: 0 500px;      
						  border-bottom: 2px solid #e9ebec;}       

		div#search {   
			width: 900px;
			margin: 20px auto;     
			text-align: center;
		}
		
		div#dummy { height: 75px; background: #292929;}	
		
			
		/* 테이블 영역  */
		div#table {
			width: 1000px;
			margin: 50px auto 50px auto;
			padding-left:100px;
		}  
				
		div#table table a { display: block;  }
		
		div#table th, td {
			width: 250px; 
			height: 30px;
			text-align: center;
			font-size: 15px;
		}
		
		div#table td.long a {
			width: 220px;  text-align: center;
		}
			
</style>  
<script>
	$(function(){
		
		$(".side2").hide();
		$("#planMgn").find(".side2").toggle();
		
		//검색 구분 조건 바뀔 때 테이블 reload
		$("select").on("change", function(){
			
				$(".tableList").load(location.href+" .tableList tr");
				$("input[name='search']").val("");   
				$(".pagination").load(location.href+" .pagination li");
		})    
		
		$("button").click(function() {         
			switch($("#searchMenu option:selected").val()) {    
			case "검색 구분":
				alert("검색 조건을 선택해주세요.");   
				$("input[name='search']").val("");
				break;
			case "상품 코드(A)":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/bankwork/plan/planSearch.do",
				    data: {search:search,div:div},  
				    type: "POST", 
				    dataType: "json", 
				    success : function(res) {
				    		//console.log(res.paging);
				    		if(res.error=="notExist") {
				    			alert("존재하지 않는 상품 입니다.");
				    		}
				    	else {
				    		$(".tableList").remove();
				    		
				    		var $table = $("<table>").addClass("tableList");
				    		var $tr1 = $("<tr>");
				    		
				    		var $th1 = $("<th>").html("상품 코드");
				    		var $th2 = $("<th>").html("상품 세부코드");
				    		var $th3 = $("<th>").html("상품명");
				    		var $th4 = $("<th>").html("상품 구분");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th3);   
				    		$tr1.append($th4);
				    		$table.append($tr1);   
				    		
				    		$(res.list).each(function(i, obj) {  
				    			var $tr2 = $("<tr>");
				    			var $a1 = $("<a>").html(obj.planCode).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a2 = $("<a>").html(obj.planDetail).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a3 = $("<a>").html(obj.planName).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a4;
					    		if(obj.planDiv=="CV"){  
					    			$a4 = $("<a>").html("일반고객(VIP 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else if(obj.planDiv=="CN"){
					    			$a4 = $("<a>").html("일반고객(일반 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else if(obj.planDiv=="BV"){
					    			$a4 = $("<a>").html("기업고객(VIP 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else {
					    			$a4 = $("<a>").html("기업고객(일반 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}
					    		
					    		var $td1 = $("<td>");
					    		var $td2 = $("<td>");
					    		var $td3 = $("<td>");   
					    		var $td4 = $("<td>");
					    		
					    		$td1.append($a1);
					    		$td2.append($a2);
					    		$td3.append($a3);
					    		$td4.append($a4);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td3);
					    		$tr2.append($td4);
					    		
					    		
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
				    		
				    		$("#table").append($divSorter);
				    	 }    
				    }
				})
				
				
				break;
			case "상품 세부코드(AB)":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/bankwork/plan/planSearch.do",
				    data: {search:search,div:div},  
				    type: "POST", 
				    dataType: "json", 
				    success : function(res) {
				    		if(res.error=="notExist") {
				    			alert("존재하지 않는 상품 입니다.");
				    		}
				    	else {
							$(".tableList").remove();
				    		
				    		var $table = $("<table>").addClass("tableList");
				    		var $tr1 = $("<tr>");
				    		
				    		var $th1 = $("<th>").html("상품 코드");
				    		var $th2 = $("<th>").html("상품 세부코드");
				    		var $th3 = $("<th>").html("상품명");
				    		var $th4 = $("<th>").html("상품 구분");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th3);   
				    		$tr1.append($th4);
				    		$table.append($tr1);   
				    		
				    		$(res.list2).each(function(i, obj) {  
				    			var $tr2 = $("<tr>");
				    			var $a1 = $("<a>").html(obj.planCode).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a2 = $("<a>").html(obj.planDetail).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a3 = $("<a>").html(obj.planName).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a4;
					    		if(obj.planDiv=="CV"){  
					    			$a4 = $("<a>").html("일반고객(VIP 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else if(obj.planDiv=="CN"){
					    			$a4 = $("<a>").html("일반고객(일반 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else if(obj.planDiv=="BV"){
					    			$a4 = $("<a>").html("기업고객(VIP 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else {
					    			$a4 = $("<a>").html("기업고객(일반 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}
					    		
					    		var $td1 = $("<td>");
					    		var $td2 = $("<td>");
					    		var $td3 = $("<td>");   
					    		var $td4 = $("<td>");
					    		
					    		$td1.append($a1);
					    		$td2.append($a2);
					    		$td3.append($a3);
					    		$td4.append($a4);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td3);
					    		$tr2.append($td4);
					    		
					    		
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
				    		
				    		
				    		for(var i=res.paging2.startPageNo; i<=res.paging2.endPageNo; i++){
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
				    		
				    		$("#table").append($divSorter);
				    	 }    
				    }
				})
				break;
			case "상품 명":
				var div = $("#searchMenu option:selected").val();
				var search = $("input[name='search']").val();
				$.ajax({
					url: "${pageContext.request.contextPath}/bankwork/plan/planSearch.do",
				    data: {search:search,div:div},  
				    type: "POST", 
				    dataType: "json", 
				    success : function(res) {
				    		//console.log(res);
				    		if(res.error=="notExist") {
				    			alert("존재하지 않는 상품 입니다.");
				    		}
				    	else {
							$(".tableList").remove();
				    		
				    		var $table = $("<table>").addClass("tableList");
				    		var $tr1 = $("<tr>");
				    		
				    		var $th1 = $("<th>").html("상품 코드");
				    		var $th2 = $("<th>").html("상품 세부코드");
				    		var $th3 = $("<th>").html("상품명");
				    		var $th4 = $("<th>").html("상품 구분");
				    		
				    		$tr1.append($th1);
				    		$tr1.append($th2);
				    		$tr1.append($th3);   
				    		$tr1.append($th4);
				    		$table.append($tr1);   
				    		
				    		$(res.list3).each(function(i, obj) {  
				    			var $tr2 = $("<tr>");
				    			var $a1 = $("<a>").html(obj.planCode).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a2 = $("<a>").html(obj.planDetail).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a3 = $("<a>").html(obj.planName).attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		var $a4;
					    		if(obj.planDiv=="CV"){  
					    			$a4 = $("<a>").html("일반고객(VIP 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else if(obj.planDiv=="CN"){
					    			$a4 = $("<a>").html("일반고객(일반 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else if(obj.planDiv=="BV"){
					    			$a4 = $("<a>").html("기업고객(VIP 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}else {
					    			$a4 = $("<a>").html("기업고객(일반 등급)").attr("href", "${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode="+obj.planCode);
					    		}
					    		
					    		var $td1 = $("<td>");
					    		var $td2 = $("<td>");
					    		var $td3 = $("<td>");   
					    		var $td4 = $("<td>");
					    		
					    		$td1.append($a1);
					    		$td2.append($a2);
					    		$td3.append($a3);
					    		$td4.append($a4);
					    		
					    		$tr2.append($td1);
					    		$tr2.append($td2);
					    		$tr2.append($td3);
					    		$tr2.append($td4);
					    		
					    		
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
				    		
				    		
				    		for(var i=res.paging3.startPageNo; i<=res.paging3.endPageNo; i++){
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
				    		
				    		$("#table").append($divSorter);
				    	 }    
				    }
				})
				break;
				
			}
		})
		$("button").eq(1).click(function() {
			$("input[name='search']").val("");
		})
		//페이지 각 번호 클릭 시  
		$(document).on("click", ".page",function() {
			var page = $(this).html();
	        location.href = "${pageContext.request.contextPath}/bankwork/plan/planSearch.do?page="+page;
		})   
		
		//prev 클릭시 이전 번호로 돌아감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".prev" , function(){
			var page = ${paging.pageNo}-1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){
				return false;       
			}
			location.href = "${pageContext.request.contextPath}/bankwork/plan/planSearch.do?page="+page;
		})  
		//next 클릭시  다음 번호로 넘어감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".next" , function(){
			var page = ${paging.pageNo}+1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){         
				return false;   
			}       
			location.href = "${pageContext.request.contextPath}/bankwork/plan/planSearch.do?page="+page;
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
<body>
	<section>
	<%@include file="../../include/sectionBar.jsp"%>
		<h2 id="menuLocation">상품 조회</h2>
	
		<div id="search">
				<select id="searchMenu">   
					<option>검색 구분</option>
					<option>상품 코드(A)</option>
					<option>상품 세부코드(AB)</option>
					<option>상품 명</option>   
					
				</select>
			
					<fieldset><input type="search" name="search" placeholder="검색어를 입력하세요."/>
						<button type="submit" id="searchBtn">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
				
		</div>
		<div id="table">
		<span><i class="fas fa-exclamation-circle"></i></span><span id="guide">상품 세부정보를 보려면 상품을 클릭하세요.</span>
			<table class="tableList">
				<tr>
					<th>상품 코드</th>
					<th>상품 세부코드</th>
					<th>상품명</th>    
					<th>상품 구분</th>

				</tr>
				<c:forEach var='planList' items="${list }">
					
					<tr>
							<td><a href="${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode=${planList.planCode}">${planList.planCode }</a></td>
							<td><a href="${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode=${planList.planCode}">${planList.planDetail }</a></td>
							<td><a href="${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode=${planList.planCode}">${planList.planName }</a></td>
							
							<c:if test="${planList.planDiv=='CV' }">
								<td class="long"><a href="${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode=${planList.planCode}">일반고객(VIP 등급)</a></td>
							</c:if>
							<c:if test="${planList.planDiv=='CN' }">
								<td class="long"><a href="${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode=${planList.planCode}">일반고객(일반 등급)</a></td>
							</c:if>
							<c:if test="${planList.planDiv=='BV' }">
								<td class="long"><a href="${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode=${planList.planCode}">기업고객(VIP 등급)</a></td>
							</c:if>
							<c:if test="${planList.planDiv=='BN' }">
								<td class="long"><a href="${pageContext.request.contextPath}/bankwork/plan/planDetail.do?planCode=${planList.planCode}">기업고객(일반 등급)</a></td>
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
</body>
</html>