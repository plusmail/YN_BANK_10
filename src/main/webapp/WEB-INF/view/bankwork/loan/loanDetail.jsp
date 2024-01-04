<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../../include/sectionBar.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link rel="stylesheet" href="//code.jquery.com/ui/1.8.18/themes/base/jquery-ui.css" />
<script src="//ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script src="//code.jquery.com/ui/1.8.18/jquery-ui.min.js"></script>
<script>
    $(function() {
    	$("#loanAdd").show();
		$("#loanList").show();
		$("#repayment").click(function() {
			if(!confirm("상환하시겠습니까?")) {
				alert("상환이 취소되었습니다");
				return false;
			}
		});
		$("#extend").click(function() {
			if(!confirm("연장하시겠습니까?")) {
				alert("연장이 취소되었습니다");
				return false;
			}
			else {
				if(${loan.custCode.custCredit>=2}) {
					var expireDate = $("#expireDate").val();
					if(${loan.loanExtended =='0'}) {
						$.ajax({
							url : "${pageContext.request.contextPath}/bankwork/loan/detail.do",
							type : "post",
							data : {cmd:"extend",expireDate:expireDate, custname : "${loan.custCode.custName}", loanaccountnum : "${loan.loanAccountNum}"},
							dataType : "json",
							success : function(res) {
								if(res!=null) {
									$("#expireDate").val(res.expireDate);
									alert("만기일이 연장되었습니다.");
									expire = true;
								}
							}
						})
					}
					else {
						alert("이미 한번 연장되었습니다");
						return false;
					}
				}
				else {
					alert("연장을 할 수 없습니다.(신용등급 2등급 이상만 가능)");
					return false;
				}
			}
		});
		$("#cancel").click(function() {
			location.href = "${pageContext.request.contextPath}/bankwork/loan/mgn.do?div=${custdiv}";
		})
		$("#list").click(function() {
			location.href= "${pageContext.request.contextPath}/bankwork/loan/detailList.do?custdiv=${custdiv}&loanaccountnum=${loan.loanAccountNum}&custname=${loan.custCode.custName}";
		})
    });
</script>
</head>
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
	
	div#profileEdit { width:700px; 
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
	div#profileEdit table td select { width: 250px; margin: 20px 0;}	
	div#submit { text-align: center; 
				 height: 100px; 
				 line-height: 100px; margin-top : 0px;}
	#extend {width: 100px;  height: 40px; 
					   border: none;
					   background: gray; 
					   margin-left:20px; 
					   font-size: 15px;
					   color: whitesmoke;
					   position: absolute; left : 1150px; top : 585px;}
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
	<div id="container">
		<div id="header">
			<h1>대출 상환 세부 정보</h1>
		</div>
		<form>
			<div id="profile">
				<h2>${loan.custCode.custName}님의 ${loan.planCode.planName} 정보</h2>
				<div id="profileEdit">
					<table>
						<tr>
							<th>고객명</th>
							<td>
								<input type="text" name="custname" readonly="readonly" value='${loan.custCode.custName}'>
							</td>
						</tr>
						<tr>
							<th>대출 계좌 번호</th>
							<td><input type="text" name="loanaccountnum" readonly="readonly" value='${loan.loanAccountNum}'></td>
						</tr>
						<tr>
							<th>상품명</th>
							<td>
								<input type="text" name="planname" readonly="readonly" value='${loan.planCode.planName}'>
							</td>	
						</tr>
						<tr>
							<th>대출시작일</th>
							<td><input type="text" name="loanStartDate"  value="<fmt:formatDate value="${loan.loanStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"></td>
						</tr>
						<c:if test="${loan.loanDelayDate eq loan.loanStartDate}">
						<tr>
							<th>거치일</th>
							<td><input type="text" name="loanDelayDateNone"  value="없음" readonly="readonly"><input type="hidden" value="<fmt:formatDate value="${loan.loanDelayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" name="loanDelayDate"></td>
						</tr>
						</c:if>
						<c:if test="${loan.loanDelayDate != loan.loanStartDate}">
						<tr>
							<th>거치일</th>
							<td><input type="text" name="loanDelayDate"  value="<fmt:formatDate value="${loan.loanDelayDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly"></td>
						</tr>
						</c:if>
						<tr>
							<th>대출만기일</th>
							<td>
								<input type="text" name="loanExpireDate"  value="<fmt:formatDate value="${loan.loanExpireDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" readonly="readonly" id="expireDate">
							</td>
						</tr>
						<tr>
							<th>대출납입회차</th>
							<td>
								<input type="text" name="loanCount" readonly="readonly" value="${count+1}회차">
							</td>
						</tr>
						<tr>
							<th>대출방식</th>
							<td><input type="text" name="loanMethod" readonly="readonly" value="${loan.loanMethod eq 'A'?'만기일시상환':'원금균등상환'}"></td>
						</tr>
						<tr>
							<th>대출이자</th>
							<td><input type="text" name="loaninterest" readonly="readonly" value=<fmt:formatNumber value="${loan.loanInterest}" type="percent"/>></td>
						</tr>
						<tr>
							<th>상환금액</th>
							<td><input type="text" name="loanbalance" readonly="readonly" value=<fmt:formatNumber value="${loan.loanBalance}" type="number" maxFractionDigits="3"/>></td>
						</tr>
					</table>
				</div>
				<div id="submit">
					<input type="submit" value="상환" formaction="${pageContext.request.contextPath}/bankwork/loan/detail.do?cmd=repayment&custdiv=${custdiv}" formmethod="post" id="repayment">
					<input type="button" value="내역보기" id="list">
					<input type="reset" value="취소" id="cancel">
				</div>
				<input type="button" value="만기일 연장" id="extend">
			</div>
			<c:if test="${nonlist!=null}">
				<script>
					alert("상환 내역을 찾을 수 없습니다.");
				</script>
				<% 
					session.removeAttribute("nonlist");
				%>
			</c:if>
		</form>
	</div>
</body>
</html>