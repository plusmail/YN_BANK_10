<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../include/menu.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://kit.fontawesome.com/6f2f0f2d95.js"></script>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<style>
	* { margin:0; padding:0; 
		font-family: 'Noto Sans KR', sans-serif;
		color: #252525; }
	div#dummy { height: 75px; background: #292929;}
	#container { width: 1000px; margin: 30px auto;}
	div#header { background: goldenrod;
			     height: 150px; }
	div#header h1 { padding: 30px;  }
	
	/* 상품 영역 */
	
	div#profile { background: whitesmoke;
				  height: 500px;
				  border-radius: 10px;
				  padding: 50px;}
	div#profile h2 { height: 50px; }				  
	div#profileMain { float: left; }
	
	
	div#profileEdit { width:600px; 
					  overflow: hidden;
					  margin-left: 200px; }
	div#profileEdit table { width: 600px; }
	div#profileEdit table tr { height: 50px; }
	div#profileEdit table th { width: 100px; text-align: left; }
	div#profileEdit table tr td { width: 250px; text-align: center; }
	div#profileEdit table tr.long { height: 70px; }							     
	div#profileEdit table td input { width: 250px;
									 background: whitesmoke;   
								     border: none; 
								     padding: 10px; 
								     border-bottom: 1px solid gray;}						     
	div#profileEdit table td select { width: 250px; margin: 20px 0;}		  
	
	/* 버튼 영역 */
	div#submit { text-align: center; 
				 height: 100px;           
				 line-height: 160px; }
	div#submit input { width: 100px;  height: 40px; 
					   border: none;
					   background: gray; 
					   margin-left:20px;    
					   font-size: 15px;
					   color: whitesmoke;}						         
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<script>
	$(function(){
			
			$(".side2").hide();
			$("#planMgn").find(".side2").toggle();
			
			//코드 분류 체계 팝업창
			$("a#code").click(function(){
				function winopen(){
					window.open("${pageContext.request.contextPath}/bankwork/plan/codeSystem.do", "codeSystem","width=1000,height=900");				
				}
				winopen();
			})
			   
		   
		   $("form").submit(function(){
			   var planName = $("input[name='planName']").val();
			   var planDesc = $("textarea[name='planDesc']").val();
			   var planDiv = $("select[name='planDiv']").val();
			   
			   if(planName=="" || planDesc ==""){
				   alert("모든 항목을 입력 해주세요.");
				   return false;
			   }
			   if(planDiv=="구분 코드 선택"){
				   alert("구분 코드를 선택 해주세요.");
				   return false;
			   }
				 var add = confirm("상품을 추가하시겠습니까?");
				if(add==false){   
					return false;
				} 
			
			
		})
		   
		
		$("input[type='reset']").click(function(){
			var choose = confirm("신규 상품 추가를 취소하시겠습니까? 메인으로 돌아갑니다.");
			if(choose){
				$(location).attr('href','${pageContext.request.contextPath}/main/main.do');
			}else {
				return false;
			}
			
		})
	})
</script>
<body>
	<%@include file="../../include/sectionBar.jsp"%>
	<div id="container">
		<div id="header">
			<h1>통장 상품 추가</h1>
		</div>
		<form action="addPlan.do" method="post">
			
			<div id="profile">
				<h2>통장 상품</h2>
				<div id="profileEdit">
					<a href="#" id="code"><i class="far fa-question-circle" id="q1"></i> 코드 분류 체계</a>
					<table>
						<tr>
							<th>상품 코드</th>
							<td>
								<input type="text" name="planCode" value="${planA }" readonly="readonly">
							</td>
						</tr>
						<tr>
							<th>상품 세부코드</th>
							<td><input type="text" name="planDetail" value="${planAwhat }" readonly="readonly"></td>
						</tr>
						<tr>
							<th>상품 명</th>     
							<td>
								<input type="text" name="planName"  placeholder="상품 명을 입력하세요.">
							</td>  
								
						</tr>
						<tr>
							<th>상품 세부 설명</th>
							<td><textarea cols="40" rows="3" name="planDesc" placeholder="세부 설명을 입력하세요."></textarea></td>
						</tr>  
						<tr class="long">
							<th>상품 가입 대상</th>
							<td><select name="planFor" id="div">
									<option>가입 대상 선택</option>
									<option>기업 고객용</option>
									<option>일반 고객용</option>
								</select></td>
						</tr>
						<tr>    
							<th>상품 가입 대상 등급</th>
							<td><select name="planForDetail" id="div">
									<option>등급 선택</option>  
									<option>VIP 등급용</option>
									<option>일반 등급용</option>
								</select></td>
						</tr>
						
					</table>
				</div> 
				
				<div id="submit">
					<input type="submit" value="추가">
					<input type="reset" value="취소">
				</div>
				
			</div>
		</form>
	</div>
</body>
</html>