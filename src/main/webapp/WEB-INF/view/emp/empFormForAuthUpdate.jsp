<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
</head>
<%@include file="../include/menu.jsp"%>
<%@include file="../include/sectionBar.jsp"%>
<link href="${pageContext.request.contextPath}/css/empFormCSS.css" rel="stylesheet" />


<script>
	$(function() {
		 //목록보이기
		  $("#empAuthAdd").show();

		//아이디와 비밀번호 정규표현식으로 걸러내기 
		$("form").submit(
				function() {
					$(".errorMSG").css("display", "none");
					var empCode = $("input[name='empCode']").val();
					var empName = $("input[name='empName']").val();
					var empTitle = $("input[name='empTitle']").val();
					var empAuth = $("input[name='empAuth']").val();
					

					//빈 값이 있을땐 못넘어 가도록 처리 /정규표현식 이용 

					if (empCode == "") {
						$("input[name='empCode']").next().next().css("display","inline");
						return false;

						// 이름체크
					} 
					
					
					if (empName == "") { //이름값을 입력하지 않은경우
						$("input[name='empName']").next().next().css("display","inline");
						return false;
					} else { //이름은 입력했으나 규칙에 맞지 않는경우 
						var nameReg = /^[가-힣]{2,5}$/; //네임은 2-5 한글
						if (nameReg.test(empName) == false) {
							$("input[name='empName']").next().next().next().css("display", "inline");
							return false;
						}
					} 
					
					
					if (empTitle == "") { //직책
						$("input[name='empTitle']").next().next().css("display", "inline");
						return false;
					} else { //직책
						if ((empTitle == "사원" || empTitle == "대리"
								|| empTitle == "과장" || empTitle == "차장"
								|| empTitle == "부장" || empTitle == "부지점장"
								|| empTitle == "지점장") == false ) {
							$("input[name='empTitle']").next().next().next().css("display", "inline");
							return false;
						}
					} 


				}) //서밋 끝나는거 
				
				
				
			
			$("#empName").change(function(){
				var empNameForProfile = $("#empName").val(); 
				//alert(empNameForProfile);
				$("#proName").html(empNameForProfile);
			})
			var pickedDeptNo = ${emp.dept.deptNo };
			//alert(pickedDeptNo);
			if(pickedDeptNo == 1){
				$("#proDept").html("인사팀");
			}else{
				$("#proDept").html("고객팀");
			}
			
			//부서 바꾸기
			$("#selectForDept").change(function(){
				var empDeptNo = $("#selectForDept").val(); 
				
				if(empDeptNo == 1){
					$("#proDept").html("인사팀");
				}else{
					$("#proDept").html("고객팀");
				}

			})
			
			//취소 버튼 누르면
			$("#returnToList").click(function(){
				location.href="${pageContext.request.contextPath}/emp/empAuth.do";
			})
			
			
	})
</script>



<body>
	<div id="container">
		<div id="header">
			<h1>사원 정보</h1>
		</div>
		<form action="empAuthUpdate.do" method="post" >
            <input type="hidden" name="pic" value="${emp.pic }">
			<div id="profile">
				<h2>프로필</h2>
				<div id="profileMain">
					<div id="pic">
						<img alt="사원사진"
							src="${pageContext.request.contextPath}/empPic/${emp.pic ==null?'no-img.jpg':emp.pic}" id="empPicture">
						<span id="proName">${emp.empName }</span> <span id="proDept"></span>

					</div>
					<div id="nameInfo"></div>
				</div>
				<div id="profileEdit">
					<table>
					    
						<tr>
							<th>사원 코드</th>
							<td><input type="text" name="empCode" readonly="readonly" value="${emp.empCode}" ><br> <span
								class="errorMSG">사원코드를 입력해주세요. 부서 선택시 자동으로 출력됩니다.</span></td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input type="text" name="empName" id="empName" value="${emp.empName }" readonly="readonly"><br> <span
								class="errorMSG">사원이름을 입력해주세요.</span> <span class="errorMSG">사원이름은
									2-5자리 한글로 입력해주세요.</span> <span class="errorMSG">사원이름 중복입니다. 구분이
									필요합니다.</span></td>
							</td>
						</tr>
						<tr>
							<th>직책</th>
							<td><input type="text" name="empTitle" placeholder="사원,대리,과장,차장,부장,부지점장,지점장" value="${emp.empTitle }" readonly="readonly"><br> 
							<span class="errorMSG">직책을 입력해주세요.</span> 
							<span class="errorMSG">직책을 확인해주세요.</span></td>
						</tr>
						<tr>
							<th>권한</th>
							<td><select name="empAuth" id="selectForAuth" >
							        <option value="" ${emp.empAuth == '' ? 'selected': '' }>권한없음</option>
									<option value="HR" ${emp.empAuth == 'HR' ? 'selected': '' }>HR</option>
									<option value="CS" ${emp.empAuth == 'CS' ? 'selected': '' }>CS</option>
									<option value="AD" ${emp.empAuth == 'AD' ? 'selected': '' }>AD</option>
							</select></td>
						</tr>
						
						<tr>
							<th>부서</th> <!-- 바로 부서별로 선택되도록 하기  -->
							<td><input type="hidden" name="deptNo" id="selectForDept" value="${emp.dept.deptNo}">
								<input type="text" id="deptName" readonly="readonly" value="${emp.dept.deptName }">	
							</td>
						</tr>
					

					</table>
				</div>

				<div id="submit">
					<input type="submit" value="수정"> 
					<input type="reset" value="취소" id="returnToList">
				    
				</div>

			</div>
		</form>
	</div>
</body>
</html>