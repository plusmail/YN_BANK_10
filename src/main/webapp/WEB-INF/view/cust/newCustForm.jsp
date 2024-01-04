<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/custFormCSS.css" rel="stylesheet" /> 
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
		//클릭한 메뉴만 보이게 하기
		$("#custAdd").show();
		$("#custList").show();
		
		//등록 버튼 클릭 시
		$("form").submit(function(){

			var name = $("input[name='name']").val();
			var addr = $("input[name='addr']").val();
			var contact1 = $("input[name='contact1']").val();
			var contact2 = $("input[name='contact2']").val();
			var contact3 = $("input[name='contact3']").val();
			var credit = $("select[name='credit']").val();
			
			if(name=="" || addr=="" || contact1=="" || contact2=="" || contact3==""  || credit=="신용등급 선택"){
				alert("모든 항목을 선택해주세요.");
				return false;
			}else{
				//정규표현식
				//연락처 - 숫자 이외 문자 x
				var contactReg = /[0-9]/;             
				if(contactReg.test(contact1)==false || contactReg.test(contact2) == false || contactReg.test(contact3) == false){
					$("td#regExp").css("display", "block");
					return false;         
				}
					
			}
			var add = confirm("신규 고객을 등록하시겠습니까?");
			if(add==false){
				return false;
			}
			
			
		})
		  
		//취소 버튼 클릭 시
		$("input[type='reset']").click(function(){
			var choose = confirm("신규 고객 추가를 취소하시겠습니까? 목록으로 돌아갑니다.");
			if(choose){
				$(location).attr('href','${pageContext.request.contextPath}/cust/custSearch.do');
			}else {
				return false;
			}
			
		})
	})
</script>
<body>
	<%@include file="../include/sectionBar.jsp"%>
	<div id="container">
		<div id="header">
			<h1>고객 추가</h1>
		</div>
		<form action="addCust.do" method="post">
			
			<div id="profile">
				<h2>일반 고객</h2>
				<div id="profileEdit">
					<table>
						<tr>
							<th>고객 코드</th>
							<td><input type="text" name="code" value="${nextCustNum }" disabled></td>
						</tr>
						<tr>
							<th>고객명</th>
							<td><input type="text" name="name"></td>
						</tr>

						<tr>
							<th>신용등급</th>
							<td><select name="credit">
									<option>신용등급 선택</option>
									<option>1</option>
									<option>2</option>
									<option>3</option>
									<option>4</option>
									<option>5</option>
								</select></td>
						</tr>
						<tr>
							<th>주소</th>
							<td><input type="text" name="addr"></td>
						</tr>
						<tr>
							<th>연락처</th>
							<td>
								<input type="text" class="contact" name="contact1">-
								<input type="text" class="contact"  name="contact2">-
								<input type="text" class="contact"  name="contact3">
							</td>
						</tr>
						<tr>
							<td></td>
							<td id="regExp">
								연락처는 숫자만 입력해주세요.
							</td>
						</tr>
						
						
					</table>
				</div> 
				
				<div id="submit">
					<input type="submit" value="등록">
					<input type="reset" value="취소">
				</div>
				
			</div>
		</form>
	</div>
</body>
</html>