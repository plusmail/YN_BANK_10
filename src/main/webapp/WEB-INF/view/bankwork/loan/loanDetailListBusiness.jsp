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
<style>
		* { font-family: 'Noto Sans KR', sans-serif; }
		h2#menuLocation { width: 250px; height: 60px;  
						  line-height: 60px; 
						  margin: 30px 400px;      
						  border-bottom: 2px solid #e9ebec;} 
		
		fieldset { position: relative;  
				   top:10px; 
		 		   display: inline-block;  
		 		   padding: 0 0 0 40px;  
		 		   background: #fff;  
		 		   border: none;  
		 		   border-radius: 5px; } 
		 		   
		input, button { position: relative;  
						width: 200px;  height: 35px;  
						padding: 0;  
						display: inline-block;  
						float: left; }
		input {  color: #666;  
		 		 z-index: 2; 
		 		 border:none;  
		 		 border-bottom: 1px solid goldenrod; }
		input:focus {  outline: 0 none; } 

	
		button { z-index: 1;  
				 width: 40px;  
				 border: 0 none;  
				 background: goldenrod;  
				 cursor: pointer;  
				 border-radius: 0 5px 5px 0;
				 background-image: url("${pageContext.request.contextPath}/images/search.png");
				 background-size: 25px; 
				 background-repeat: no-repeat; 
				 background-position: center;}
		.fa-search { font-size: 1.4rem;  
					 color: #29abe2;  
					 z-index: 3;  
					 top: 25%;  }
		

		span#guide { font-weight: bold; 
					 font-size: 15px;    
					  }
		
		div#table {
			width: 1300px;
			margin: 100px 0;
			margin-left : 400px;
		}
		
		div#table table {
			border-collapse: collapse; 
		}
		
		div#table th, td {
			width: 200px; 
			height: 30px;
			text-align: center;
			font-size: 13px; 
		}
		
		div#table tr:nth-child(odd) {
			width: 200px; 
			height: 30px;
			text-align: center;
			background: gainsboro;
			font-size: 15px;
		}
		 /* 페이징 중앙 위치 처리 */	
		div.sorter { height: 50px; margin-top: 20px;}
		div.sorter ul.pagination {           
			float:right; position:relative; left:-45%;     
		}                
		div.sorter ul.pagination li {     
		float:left; position:relative; margin-right:20px; left:40%;        
		}         
		div.sorter ul.pagination li a {         
			display: block;
			width: 30px; 
			height: 30px;              
			border-radius: 10px;        
			line-height: 30px;  
			text-align: center;     
			font-weight: bold;
		}
		
		div#table tr:hover td { background: goldenrod;}
		div#dummy { height: 75px; background: #292929;}
		
		#btnMenu1 {
		   margin-left: 20px;
		   width:150px;
		   border:2px solid goldenrod;
		   border-radius: 10px;
		   background: none;
		   margin-left : 400px;
		}
		#btnMenu1:hover {
		   background: goldenrod;
		   font-weight: bold;
		}
		
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
		$("#loanAdd").show();
		$("#loanList").show();
		$("#btnMenu1").click(function() {
			var accountnum = $("#accountnum").attr("data-accountnum");
			var custname = $("#custname").attr("data-custname");
			location.href="${pageContext.request.contextPath}/bankwork/loan/detail.do?loanaccountnum="+accountnum+"&custname="+custname;
		})
		//페이지 각 번호 클릭 시  
		$(document).on("click", ".page",function() {
			var accountnum = $("#accountnum").attr("data-accountnum");
			var custname = $("#custname").attr("data-custname");
			var page = $(this).html();
	        location.href = "${pageContext.request.contextPath}/bankwork/loan/detailList.do?custdiv=1&page="+page+"&"+"loanaccountnum="+accountnum+"&custname="+custname;
		})   
		
		//prev 클릭시 이전 번호로 돌아감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".prev" , function(){
			var accountnum = $("#accountnum").attr("data-accountnum");
			var custname = $("#custname").attr("data-custname");
			var page = ${paging.pageNo}-1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){
				return false;       
			}
			location.href = "${pageContext.request.contextPath}/bankwork/loan/detailList.do?custdiv=1&page="+page+"&"+"loanaccountnum="+accountnum+"&custname="+custname;
		})  
		//next 클릭시  다음 번호로 넘어감 (paging.pageNo = 현재 페이지 넘버)    
		$(document).on("click", ".next" , function(){
			var accountnum = $("#accountnum").attr("data-accountnum");
			var custname = $("#custname").attr("data-custname");
			var page = ${paging.pageNo}+1;
			//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
			if($(".page").size()==1){         
				return false;   
			}       
			location.href = "${pageContext.request.contextPath}/bankwork/loan/detailList.do?custdiv=1&page="+page+"&"+"loanaccountnum="+accountnum+"&custname="+custname;
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
		<h2 id="menuLocation">대출 상환 세부 내역</h2>
		<button id="btnMenu1">돌아가기</button>
		<div id="table">
			<table>
				<tr>
					<th>계좌번호</th>
					<th>고객이름</th>
					<th>상품명</th>
					<th>대출구분</th>
					<th>대출시작일</th>
					<th>거치일</th>
					<th>대출만기일</th>
					<th>대출납입회차</th>
					<th>대출방식</th>
					<th>대출이자</th>
					<th>상환액</th>
					<th>잔여대출금액</th>
				</tr>
				<c:forEach var="loan" items="${list}">
				<tr>
					<td data-accountnum="${loan.loanAccountNum}" id="accountnum">${loan.loanAccountNum}</td>
					<td data-custname="${loan.cust.custName}" id="custname">${loan.cust.custName}</td>
					<td>${loan.plan.planName}</td>
					<td>${fn:substring(loan.loanAccountNum,8,9) eq '1'?'일반대출':fn:substring(loan.loanAccountNum,8,9) eq '2'?'신용대출':'카드론'}</td>
					<td><fmt:formatDate value="${loan.loanStartDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${loan.loanDelayDate}" pattern="yyyy-MM-dd"/></td>
					<td><fmt:formatDate value="${loan.loanExpireDate}" pattern="yyyy-MM-dd"/></td>
					<td>${loan.loanRound}회차</td>
					<td>${loan.loanMethod eq 'A'?'만기일시상환':'원금균등상환'}</td>
					<td><fmt:formatNumber value="${loan.loanInterest}" type="percent"/></td>
					<td><fmt:formatNumber value="${loan.loanRepayment}" type="number" maxFractionDigits="3"/></td>
					<td><fmt:formatNumber value="${loan.loanBalance}" type="number" maxFractionDigits="3"/></td>
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