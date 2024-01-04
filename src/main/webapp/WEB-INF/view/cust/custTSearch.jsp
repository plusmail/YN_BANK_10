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

<script>
   //전역변수
   var div;
   var search;

   function getAjaxData(){
	  div = $("#searchMenu option:selected").val();
	  search = $("input[name='search']").val();
		 
	   $.ajax({
			url: "${pageContext.request.contextPath}/cust/custTransfer.do?div="+div+"&search="+search,
			url: "${pageContext.request.contextPath}/cust/custTransfer.do",
		    data: {search:search,div:div},  
		    type: "POST", 
		    dataType: "json", 
		    success : function(res) {
		    		console.log(res);
		    		if(res.error == "notExist") {
		    			alert("조건에 맞는 고객이 없습니다.");
		    		}
		    	else {
		    		$(".tableList").remove();
		    		
		    		var $table = $("<table>").addClass("tableList");
		    		var $tr1 = $("<tr>");
		    		
		    		var $th1 = $("<th>").html("고객 코드");
		    		var $th2 = $("<th>").html("고객명");
		    		var $th3 = $("<th>").html("고객 신용등급");
		    		var $th4 = $("<th>").html("계좌번호");
		    		var $th5 = $("<th>").html("잔액");
		    		var $th6 = $("<th>").html("분류");
		    		
		    		$tr1.append($th1);    
		    		$tr1.append($th2);
		    		$tr1.append($th3);
		    		$tr1.append($th4);
		    		$tr1.append($th5);
		    		$tr1.append($th6);
		    		$table.append($tr1);
		    		
		    		$(res).each(function(i, obj) {
		    			var $tr2 = $("<tr>");
		    			
		    			var $a1 = $("<a>").html(obj.custCode).attr("href", "${pageContext.request.contextPath}/cust/custTSelect.do?custCode="+obj.custCode+"&accountNum="+obj.bankbook.accountNum);
			    		var $a2 = $("<a>").html(obj.custName).attr("href", "${pageContext.request.contextPath}/cust/custTSelect.do?custCode="+obj.custCode+"&accountNum="+obj.bankbook.accountNum);
			    		var $a3 = $("<a>").html(obj.custCredit).attr("href", "${pageContext.request.contextPath}/cust/custTSelect.do?custCode="+obj.custCode+"&accountNum="+obj.bankbook.accountNum);
			    		var $a4 = $("<a>").html(obj.bankbook.accountNum).attr("href", "${pageContext.request.contextPath}/cust/custTSelect.do?custCode="+obj.custCode+"&accountNum="+obj.bankbook.accountNum);
			    		var $a5 = $("<a>").html(obj.bankbook.accountBalance+"원").attr("href", "${pageContext.request.contextPath}/cust/custTSelect.do?custCode="+obj.custCode+"&accountNum="+obj.bankbook.accountNum);
			    		var $a6;
			    		if(obj.custDiv==true){    
			    			$a6 = $("<a>").html("기업").attr("href", "#");
			    		}else{
			    			$a6 = $("<a>").html("일반").attr("href", "#");
			    		}
		    			   
			    		var $td1 = $("<td>");
			    		var $td2 = $("<td>");
			    		var $td3 = $("<td>");
			    		var $td4 = $("<td>"); 
			    		var $td5 = $("<td class='alright'>");
			    		var $td6 = $("<td>");
			    		
			    		$td1.append($a1);
			    		$td2.append($a2);
			    		$td3.append($a3);
			    		$td4.append($a4);
			    		$td5.append($a5);
			    		$td6.append($a6);
			    		
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
		    	 }    
		    }
		})   
   }
   
	$(function(){
		//선택한 메뉴만 보이게 하기
		$(".side2").hide();
		$("#tMgn").find(".side2").toggle();
		
		//검색 구분 조건 변경하면 테이블 reload
		$("select").on("change", function(){
			
				$(".tableList").load(location.href+" .tableList tr");
				$("input[name='search']").val("");   
		})
		
		//버튼 클릭 시 
		$("button").click(function() {  
			
		 div = $("#searchMenu option:selected").val();
		 search = $("input[name='search']").val();
	
			switch($("#searchMenu option:selected").val()) {
			case "검색 구분":
				alert("검색 조건을 선택해주세요.");
				$("input[name='search']").val("");
				
				break;
				
			case "고객 코드":
				getAjaxData();
				
				break;
			case "고객명":
				getAjaxData();

				break;
			case "연락처":
				getAjaxData();
				
				break;
				
			}
	}) //버튼 클릭이벤트 끝
	
		 //페이지 각 번호 클릭 시  
		$(document).on("click", ".page",function() {
			var page = $(this).html();
	        location.href = "${pageContext.request.contextPath}/emp/empBonus.do?page="+page;
		})   
		
		//prev 클릭시 이전 번호로 돌아감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".prev" , function(){
			var page = ${paging.pageNo}-1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){
				return false;       
			}
			location.href = "${pageContext.request.contextPath}/emp/empBonus.do?page="+page;
		})  
		//next 클릭시  다음 번호로 넘어감 (paging.pageNo = 현재 페이지 넘버)    
		$(document).on("click", ".next" , function(){
			var page = ${paging.pageNo}+1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){         
				return false;   
			}       
			location.href = "${pageContext.request.contextPath}/emp/empBonus.do?page="+page;
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
		<%@include file="../include/sectionBar.jsp"%>
		<h2 id="menuLocation">송금 (계좌 이체)</h2>
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
		<span><i class="fas fa-exclamation-circle"></i></span><span id="guide">계좌이체를 수행하려면  고객을 클릭하세요.</span>
			<table class="tableList">
				<tr>
					<th>고객 코드</th>
					<th>고객명</th>
					<th>고객 신용등급</th>
					<th>계좌번호</th>
					<th>잔액</th>
					<th>분류</th>

				</tr>  
				<c:forEach var='listForBal' items="${listForBal }">
					
					<tr>
							<td><a href="${pageContext.request.contextPath}/cust/custTSelect.do?custCode=${listForBal.custCode}&dw=dw&accountNum=${listForBal.bankbook.accountNum}">${listForBal.custCode }</a></td>
							<td><a href="${pageContext.request.contextPath}/cust/custTSelect.do?custCode=${listForBal.custCode}&dw=dw&accountNum=${listForBal.bankbook.accountNum}">${listForBal.custName }</a></td>
							<td><a href="${pageContext.request.contextPath}/cust/custTSelect.do?custCode=${listForBal.custCode}&dw=dw&accountNum=${listForBal.bankbook.accountNum}">${listForBal.custCredit }</a></td>
							<td><a href="${pageContext.request.contextPath}/cust/custTSelect.do?custCode=${listForBal.custCode}&dw=dw&accountNum=${listForBal.bankbook.accountNum}">${listForBal.bankbook.accountNum }</a></td>
							<td class='alright'><a href="${pageContext.request.contextPath}/cust/custTSelect.do?custCode=${listForBal.custCode}&dw=dw&accountNum=${listForBal.bankbook.accountNum}"><fmt:formatNumber value="${listForBal.bankbook.accountBalance}" groupingUsed="true"/> 원</a></td>
							
			
							<c:if test="${listForBal.custDiv==true}">
								<td><a href="#">기업</a></td>
							</c:if>     
							<c:if test="${listForBal.custDiv==false}">
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
		<c:if test="${successedTransfer!=null}">
	    	<script>
	    		alert("${targetCust} 님께 ${transferredAmount} 원 송금 되었습니다");
	    		<%
	    			session.removeAttribute("successedTransfer");
	    		    session.removeAttribute("targetCust");
	    		    session.removeAttribute("transferredAmount");
	    		%>
	    	</script>
        </c:if>
		</section>
</body>
</html>