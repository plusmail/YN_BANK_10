<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<link href="https://fonts.googleapis.com/css2?family=Kanit:wght@800&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
	$(function(){
		if(${Auth.empAuth=='AD'}) {
			$("#staff").css({"height":"10%","background":"goldenrod"});
			$("#staff ul li").css({"float":"left","list-style":"none"});
			$("#greeting").css({"width":"200px","margin":"15px 10px 10px 45px","text-align":"center"});
			$("#btns").css({"width":"150px","margin":"10px 5px 5px 70px","clear":"both"});
			$("#btns a").css({"font-size":"14px","color":"#000"});
			$("#btns a").eq(0).css({"padding-right":"5px","border-right":"1px solid grey"});
			$("#name").css("font-weight","bold");
			$("#staff ul li img").css({"width":"120px","height":"120px","margin":"10px","border-radius":"120px"});
		}
		else {
			$("#staff").css({"height":"20%","background":"goldenrod"});
			$("#staff ul li").css({"float":"left","list-style":"none"});
			$("#staff ul li").eq(0).css({"width":"120px","height":"120px","overflow":"hidden","padding":"10px"});
			$("#greeting").css({"width":"80px","margin":"50px 10px 10px 25px","text-align":"center"});
			$("#btns").css({"width":"150px","margin":"30px 5px 5px 5px"});
			$("#btns a").css({"font-size":"14px","color":"#000"});
			$("#btns a").eq(0).css({"padding-right":"5px","border-right":"1px solid grey"});
			$("#name").css("font-weight","bold");
			$("#staff ul li img").css({"width":"120px","height":"120px","margin":"10px","border-radius":"120px"});
		}
		$(".side1").click(function() {
			$(".side2").hide();
			$(this).find(".side2").toggle();
			
		})
		$(".side2").click(function() {
			$(".side2").hide();
			$(this).toggle();
			
		})
		$(".side2").hide();
	});
</script>
<style>
	* { margin: 0; padding: 0; font-family: 'Noto Sans KR', sans-serif;}
	body { min-width: 1900px;  }
	ul li { list-style: none; }
	a { text-decoration: none; color: #000; }
	nav { width: 300px; height: 100%;  
		  background: #292929;
		  position: absolute; top:0; left: 0;
		  }
	nav div#logo { background: #292929; }
	nav div#logo h1 { padding: 15px; font-size: 30px;}
	nav div#logo h1 a {font-family: 'Kanit', sans-serif;
				      color: whitesmoke; text-shadow: 2px 2px 2px gray;}

	/* 메뉴 */
	nav div#menu li { /* height: 40px; */
					  line-height: 40px; 
					  
					  text-indent: 20px; }
	nav div#menu li#menuTitle { height: 40px; 
								color: gainsboro;
								background: #000;   }
	
	nav div#menu li.side1 { text-indent: 40px; }
	nav div#menu li.side2 {  text-indent:60px;
							background: #464646;
							border-top: 1px solid gray;
							border-bottom: 1px solid gray;}
	nav div#menu li.side3 {  text-indent:80px;
							background: grey;
							border-top: 1px solid lightgray;
							border-bottom: 1px solid lightgray;}						
	
	nav div#menu a {  color: gainsboro;
				     display: block;  }
				     
	section div#dummy { height: 75px; background: #292929;}	
	
	
	.active{
      background: goldenrod;
     }
  
  					    
</style>
<body>
	<nav>
		<div id="logo">
			<h1><a href="${pageContext.request.contextPath}/main/main.do">YN BANK</a></h1>
		</div>
		<div id="staff">
			<ul>
				<c:if test="${Auth.empAuth!='AD'}">
				<li><img src="${pageContext.request.contextPath}/empPic/${Auth.pic == null?'no-img.jpg':Auth.pic}" id="staffImg"></li>
				</c:if>
				<li id="greeting"><span id="name">${Auth.empName} </span>님 환영합니다.</li>
				<li id="btns">
					<a href="${pageContext.request.contextPath}/emp/empProfile.do">내 프로필</a>
					<a href="${pageContext.request.contextPath}/main/logout.do">로그아웃</a>
				</li>
			</ul>
		</div>
		<div id="menu">
			<ul>
				<li id="menuTitle">Menu</li>
				<c:if test="${Auth.empAuth=='HR'}"> <!-- 인사팀일때 -->
				   <c:choose>
					<c:when test="${Auth.empTitle =='사원'}">
						    <li id="empMgn" class="side1"><a href="#">사원 관리</a>
							<ul>
								<li id="empAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empForm.do">신규 사원 추가</a></li>
								<li id="empList" class="side2"><a href="${pageContext.request.contextPath}/emp/empSearch.do">사원 목록</a></li>
							</ul>
						</li>
<%-- 						<li id="empAuth" class="side1"><a href="#">사원 권한 관리</a>
						    <ul><li id="empAuthAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empAuth.do">사원 권한 수정</a></li></ul>
						</li> --%>
						<li id="empBonus" class="side1"><a href="#">사원 실적</a>
						    <ul><li id="empBonusList" class="side2"><a href="${pageContext.request.contextPath}/emp/empBonus.do">전체 실적 조회</a></li></ul>
						    <ul><li id="empRealBonusList" class="side2"><a href="${pageContext.request.contextPath}/emp/empRealBonus.do">인센티브 조회</a></li></ul>
						</li>
						<li id="statistic" class="side1"><a href="${pageContext.request.contextPath}/bankwork/bankbook/statistic.do">통계</a></li>
					</c:when>
					<c:when test="${Auth.empTitle =='대리'}">
						    <li id="empMgn" class="side1"><a href="#">사원 관리</a>
							<ul>
								<li id="empAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empForm.do">신규 사원 추가</a></li>
								<li id="empList" class="side2"><a href="${pageContext.request.contextPath}/emp/empSearch.do">사원 목록</a></li>
							</ul>
						</li>
<%-- 						<li id="empAuth" class="side1"><a href="#">사원 권한 관리</a>
						    <ul><li id="empAuthAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empAuth.do">사원 권한 수정</a></li></ul>
						</li> --%>
						<li id="empBonus" class="side1"><a href="#">인센티브</a>
						    <ul><li id="empBonusList" class="side2"><a href="${pageContext.request.contextPath}/emp/empBonus.do">인센티브 조회</a></li></ul>
						</li>
						<li id="statistic" class="side1"><a href="${pageContext.request.contextPath}/bankwork/bankbook/statistic.do">통계</a></li>
					</c:when>
					<c:otherwise>
					    <li id="empMgn" class="side1"><a href="#">사원 관리</a>
							<ul>
								<li id="empAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empForm.do">신규 사원 추가</a></li>
								<li id="empList" class="side2"><a href="${pageContext.request.contextPath}/emp/empSearch.do">사원 목록</a></li>
							</ul>
						</li>
						<li id="empAuth" class="side1"><a href="#">사원 권한 관리</a>
						    <ul><li id="empAuthAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empAuth.do">사원 권한 수정</a></li></ul>
						</li>
						<li id="empBonus" class="side1"><a href="#">인센티브</a>
						    <ul><li id="empBonusList" class="side2"><a href="${pageContext.request.contextPath}/emp/empBonus.do">인센티브 조회</a></li></ul>
						</li>
						<li id="statistic" class="side1"><a href="${pageContext.request.contextPath}/bankwork/bankbook/statistic.do">통계</a></li>
					</c:otherwise>
				  </c:choose>
				</c:if>
				
				
				
				<c:if test="${Auth.empAuth=='CS'}"> <!-- 고객팀일때 -->
					<li id="custMgn" class="side1"><a href="#">고객 관리</a>
					    <ul>
						    <li id="custAdd" class="side2"><a href="${pageContext.request.contextPath}/cust/selectCust.do">신규 고객 추가</a></li>
						    <li id="custList" class="side2"><a href="${pageContext.request.contextPath}/cust/custSearch.do">고객 조회</a></li>
					    </ul>
					</li>
					<li id="dwMgn" class="side1"><a href="#">입/출금 관리</a>
						<ul>
							<li id="deposit" class="side2"><a href="${pageContext.request.contextPath}/cust/custDWSearch.do">입/출금</a></li>
							<li id="dwList" class="side2"><a href="${pageContext.request.contextPath}/cust/custDWRecord.do">입/출금 조회</a></li>
						</ul>
					</li>
					<li id="tMgn" class="side1"><a href="#">송금 업무</a>
						<ul>
							<li id="transfer" class="side2"><a href="${pageContext.request.contextPath}/cust/custTransfer.do">계좌이체</a></li>
						</ul>
					</li>
					<li id="bankbookMgn" class="side1"><a href="#">통장 관리</a>
						<ul>
							<li id="bankbookAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/bankbook/select.do?div=add">통장 추가</a></li>
							<li id="bankbookList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/bankbook/select.do?div=mgn">통장 조회</a></li>
						</ul>
					</li>
					<li id="cardMgn" class="side1"><a href="#">카드 관리</a>
						<ul>
							<li id="cardAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/card/select.do?div=add">카드 추가</a></li>
							<li id="cardList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/card/select.do?div=mgn">카드 조회</a></li>
						</ul>
					</li>
					<li id="loanMgn" class="side1"><a href="#">대출 관리</a>
						<ul>
							<li id="loanAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/loan/select.do?div=add">대출 추가</a></li>
							<li id="loanList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/loan/select.do?div=mgn">대출 상환</a></li>
						</ul>
					</li>
					<li id="planMgn" class="side1"><a href="#">상품 관리</a>
						<ul>
							<li id="planAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/selectBBookPlan.do">통장 상품 추가</a></li>
							<li id="planAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/selectCardPlan.do">카드 상품 추가</a></li>
							<li id="planAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/selectLoanPlan.do">대출 상품 추가</a></li>
							<li id="planList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/planSearch.do">상품 조회</a></li>  
						</ul>
					</li>
					<li id="statistic" class="side1"><a href="${pageContext.request.contextPath}/bankwork/bankbook/statistic.do">통계</a></li>
				</c:if>
				<c:if test="${Auth.empAuth=='AD'}"> <!-- 관리자일때 -->
					<li id="empMgn" class="side1"><a href="#">사원 관리</a>
						<ul>
							<li id="empAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empForm.do">신규 사원 추가</a></li>
							<li id="empList" class="side2"><a href="${pageContext.request.contextPath}/emp/empSearch.do">사원 목록</a></li>
						</ul>
					</li>
					<li id="empAuth" class="side1"><a href="#">사원 권한 관리</a>
					    <ul><li id="empAuthAdd" class="side2"><a href="${pageContext.request.contextPath}/emp/empAuth.do">사원 권한 수정</a></li></ul>
					</li>
					<li id="empBonus" class="side1"><a href="#">사원 실적</a>
						    <ul><li id="empBonusList" class="side2"><a href="${pageContext.request.contextPath}/emp/empBonus.do">전체 실적 조회</a></li></ul>
						    <ul><li id="empRealBonusList" class="side2"><a href="${pageContext.request.contextPath}/emp/empRealBonus.do">인센티브 조회</a></li></ul>
					</li>
					<li id="custMgn" class="side1"><a href="#">고객 관리</a>
					    <ul>
						    <li id="custAdd" class="side2"><a href="${pageContext.request.contextPath}/cust/selectCust.do">신규 고객 추가</a></li>
						    <li id="custList" class="side2"><a href="${pageContext.request.contextPath}/cust/custSearch.do">고객 조회</a></li>
					    </ul>
					</li>
					<li id="dwMgn" class="side1"><a href="#">입/출금 관리</a>
						<ul>
							<li id="deposit" class="side2"><a href="${pageContext.request.contextPath}/cust/custDWSearch.do">입/출금</a></li>
							<li id="dwList" class="side2"><a href="${pageContext.request.contextPath}/cust/custDWRecord.do">입/출금 조회</a></li>
						</ul>
					</li>
					<li id="tMgn" class="side1"><a href="#">송금 업무</a>
						<ul>
							<li id="transfer" class="side2"><a href="${pageContext.request.contextPath}/cust/custTransfer.do">계좌이체</a></li>
						</ul>
					</li>
					<li id="bankbookMgn" class="side1"><a href="#">통장 관리</a>
						<ul>
							<li id="bankbookAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/bankbook/select.do?div=add">통장 추가</a></li>
							<li id="bankbookList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/bankbook/select.do?div=mgn">통장 조회</a></li>
						</ul>
					</li>
					<li id="cardMgn" class="side1"><a href="#">카드 관리</a>
						<ul>
							<li id="cardAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/card/select.do?div=add">카드 추가</a></li>
							<li id="cardList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/card/select.do?div=mgn">카드 조회</a></li>
						</ul>
					</li>
					<li id="loanMgn" class="side1"><a href="#">대출 관리</a>
						<ul>
							<li id="loanAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/loan/select.do?div=add">대출 추가</a></li>
							<li id="loanList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/loan/select.do?div=mgn">대출 상환</a></li>
						</ul>
					</li>
					<li id="planMgn" class="side1"><a href="#">상품 관리</a>
						<ul>
							<li id="planAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/selectBBookPlan.do">통장 상품 추가</a></li>
							<li id="planAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/selectCardPlan.do">카드 상품 추가</a></li>
							<li id="planAdd" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/selectLoanPlan.do">대출 상품 추가</a></li>
							<li id="planList" class="side2"><a href="${pageContext.request.contextPath}/bankwork/plan/planSearch.do">상품 조회</a></li>   
						</ul>
					</li>
					<li id="statistic" class="side1"><a href="${pageContext.request.contextPath}/bankwork/bankbook/statistic.do">통계</a></li>

				</c:if>
			</ul>	
		</div>
	</nav>
</body>