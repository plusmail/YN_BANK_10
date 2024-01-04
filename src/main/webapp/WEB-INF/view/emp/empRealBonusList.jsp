<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/menu.jsp"%>
<%@include file="../include/sectionBar.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script src="https://kit.fontawesome.com/6f2f0f2d95.js"></script>
<link href="${pageContext.request.contextPath}/css/listCSS.css" rel="stylesheet" />
</head>
<script>
var searchType;
var keyword;
   $(function(){
	   //메뉴보이기
	   $("#empBonusList").show();
	   $("#empRealBonusList").show();

	  //랭크 탑3의 코드를 불러옴  
	//  var rankMemCode = ["${mem1}","${mem2}","${mem3}"];
	  //alert(rankMemCode[0]);
	  //alert($(".tdForRank").children().eq(1).html());
	  
	   $("select").on("change",function(){
		  $(".tableList").load(location.href+" .tableList tr");
		  $("#searchForEmp").val("");
		  $(".pagination").load(location.href+" .pagination li");
	  })
	  
/* 	 $(document).on("mouseover",".tdForRank",function(){
		 var page = ${paging.pageNo};
         if(page == 1){
		 if($(this).children().eq(1).html() == rankMemCode[0]){
			 $(this).children().eq(0).html("<img src='YN_bank../../../images/ranking1.png' class='rankingImg'>");
		 }else if($(this).children().eq(1).html() == rankMemCode[1]){
			 $(this).children().eq(0).html("<img src='YN_bank../../../images/ranking2.png' class='rankingImg'>");
		 }else if($(this).children().eq(1).html() == rankMemCode[2]){
			 $(this).children().eq(0).html("<img src='YN_bank../../../images/ranking3.png' class='rankingImg'>");
		 }
        }
	 }) */
	 

	  
	  $("button").eq(0).click(function(){
		 searchType = $("#searchMenu option:selected").val();
		 keyword = $("input[name='search']").val();

         if(searchType =="상품조회"){
        	 alert("상품을 선택하세요");
        	 return false;
         }
         location.href = "empRealBonus.do?searchType="+searchType+"&keyword="+keyword;

	  })//버튼클릭끝나는거
	   
	    //각 줄을 클릭할 때마다 내가 부여한 data-empCode를 받아오기 
	    
	    
	    $(document).on("click",".oneEmp",function(){
          var OneCode = $(this).attr("data-empCode");
		  var perf = $(this).attr("data-perf");
		  if(perf == 0){
			  alert("실적이 없는 사원입니다.");
			  return false
		  }
		  //alert(OneCode);
		  location.href="${pageContext.request.contextPath}/emp/empBonusDetail.do?empCode="+OneCode+"&bonus=realBonus";
	  })
	  
	  $(".tdForRank").mouseover();  //마우스오버 이벤트 강제로 한번 일으킴 
	 
	//각 줄을 클릭할 때마다 내가 부여한 data-empCode를 받아오기 
	  $(document).on("click",".oneEmp",function(){
		  var OneCode = $(this).attr("data-empCode");
		  //alert(OneCode);
		  location.href="${pageContext.request.contextPath}/emp/empBonusDetail.do?empCode="+OneCode;
	  })

	  
	   $("select").on("change", function() {
		   var pCode = $("select#searchMenu").val();

		   $("#searchForEmp").val(pCode);
		  	var href = location.href;
		  	href = href.substring(0, href.indexOf("?"));
		  	if(href==null) {
		  		$("table").load(location.href + " table");
				//$("input[name='search']").val("");
		  	}
		  	else {
		  		$("table").load(href + " table");
				//$("input[name='search']").val("");
		  	}
	  })
	
   })


</script>
<body>
	<section>
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
				$("#searchForEmp").val(search);
			})
		</script>
	</c:if>
	<h2 id="menuLocation">인센티브 조회</h2>
		<div id="search">
				<select id="searchMenu">
				    <option>상품조회</option>
					<c:forEach var='planList' items="${planList }">
					<option value='${planList.planCode }'>${planList.planName }</option>
					
					</c:forEach>
				</select>
			
					<fieldset><input type="search" name="search" id="searchForEmp" placeholder="인센티브 상품코드를 선택하세요."/>
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
				
		</div>  
		<div id="table">
		<span><i class="fas fa-exclamation-circle"></i></span><span id="guide">검색 후 세부 내역을 조회하려면 사원을 클릭하세요.</span>
			<table class="tableList">
				<tr>
					<th>사원코드</th>
					<th>사원이름</th>
					<th>직책</th>
					<th>전체 실적</th>
					<!-- <th>인센티브</th> -->
<!-- 					<th>상품 종류</th>
					<th class="thPlanName">상품 이름</th> -->
				

				</tr>
				<span id="renew">
				<c:forEach var='empList' items="${list }">
				<tr class="oneEmp" data-empCode="${empList.empCode }" data-perf="${empList.perf }">

					<td class="tdForRank"><span class="imgSpan"></span><span class="codeSpan">${empList.empCode }</span></td>

					<td>${empList.empName }</td>
					<td>${empList.empTitle }</td>
					<td>${empList.perf}</td>
					<%-- <td><fmt:formatNumber value='${empList.bonus }' pattern='###,###,###'/></td> --%>
	 				<%-- <td>${empList.pCode}</td>
					<td>${empList.pName }</td>	  --%>
				</tr>
	          
				</c:forEach> 
				</span>
		</table>
		<div class="sorter">   
		       <ul class="pagination">
				<c:if test="${pageMaker.prev == true}">
					<li><a href="empRealBonus.do?page=${pageMaker.startPage-1}&searchType=${cri.searchType}&keyword=${cri.keyword}">&laquo;</a></li>
				</c:if>
				<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
					<li class="${cri.page==idx?'active':''}"><a href="empRealBonus.do?page=${idx}&searchType=${cri.searchType}&keyword=${cri.keyword}">${idx}</a></li>
				</c:forEach>
				<c:if test="${pageMaker.next == true}">
					<li><a href="empRealBonus.do?page=${pageMaker.endPage+1}&searchType=${cri.searchType}&keyword=${cri.keyword}">&raquo;</a></li>
				</c:if>
			</ul>
		    </div> 
		</div>   
		</section>
</body>
</html>