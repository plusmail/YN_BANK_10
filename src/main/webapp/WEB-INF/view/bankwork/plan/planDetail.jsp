<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    	//클릭한 메뉴만 보이게 하기
    	$(".side2").hide();
		$("#planMgn").find(".side2").toggle();
		
		//로딩될 때 확인 버튼 숨김 
    	$("input[value='수정 완료']").css("display", "none");
		
		//취소 버튼 클릭 시 
    	$("#cancel").click(function() {
    		location.href = "${pageContext.request.contextPath}/bankwork/plan/planSearch.do";
    	})
    	
    	//수정 버튼 클릭 시
    	$("input[value='수정하기']").click(function(){
    		$(this).css("display", "none");
    		$("input[value='수정 완료']").css("display", "inline");
    		$("input").removeAttr("readonly");  
    		$("textarea").removeAttr("readonly");  
    		$("input[name='planCode']").attr("readonly", "readonly"); 
    		$("input[name='planDetail']").attr("readonly", "readonly");
    		$("input[name='planDiv']").attr("readonly", "readonly");
    		$("input[name='planName']").focus();
    		$("input[value='삭제']").remove();
    		$("input[value='수정']").remove();
    		return false;
    	})
    	
    	
    	 //동적으로 생성된 input 태그에 이벤트
 		$("input[value='수정 완료']").on({
 			"click" : function(){
 				var edit = confirm("수정하시겠습니까?");
 				if(edit==false){
 					return false;
 				}
 			}
 		}) 
 		
 		$("input[value='삭제']").on({
 			"click" : function(){
 				var del = confirm("삭제하시겠습니까?");
 				if(del==false){
 					return false;  
 				}
 			}
 		})
 		     
 		      
 
    });
</script>       
</head>
<style>
	* { margin:0; padding:0; 
		font-family: 'Noto Sans KR', sans-serif;
		color: #252525; }
	div#dummy { height: 75px; background: #292929;}
	#container { width: 1000px; margin: 50px auto;}
	div#header { background: goldenrod;
			     height: 150px; }
	div#header h1 { padding: 30px;  }
	div#profile { background: whitesmoke;
				  height: 500px;
				  border-radius: 10px;
				  padding: 50px;}
	div#profile h2 { height: 50px; }				  
	div#profileMain { float: left; }
	
	div#profileEdit { width:600px; 
					  overflow: hidden;
					  margin-left: 200px;  }
	div#profileEdit table { width: 500px; }
	div#profileEdit table tr { height: 30px; }
	div#profileEdit table th { width: 150px; text-align: left; }
	div#profileEdit table td { width: 200px; text-align: center;}
	div#profileEdit table td input { width: 250px;
									 background: whitesmoke;
								     border: none; 
								     padding: 10px; 
								     border-bottom: 1px solid gray;}
	table td.long {height: 150px; 
				    }
	div#profileEdit table td select { width: 250px; margin: 20px 0;}	
				    
	/* 버튼 영역 */				    					     			
	div#submit { text-align: center; 
				 height: 100px; 
				 line-height: 100px; }
	div#submit input { width: 100px;  height: 40px; 
					   border: none;
					   background: gray; 
					   margin-left:20px; 
					   font-size: 15px;
					   color: whitesmoke;}						    
</style>
<body>
	<jsp:include page="/WEB-INF/view/include/menu.jsp"/>
	<%@include file="../../include/sectionBar.jsp"%>
	<div id="container">
		<div id="header">
			<h1>상품 세부 정보</h1>
		</div>
		<form action="planUpdate.do" method="post">
			<div id="profile">
				<h2>상품 정보</h2>
				<div id="profileEdit">
					<table>
						<tr>
							<th>상품 코드</th>
							<td>
								<input type="text" readonly="readonly" name="planCode" value="${plan.planCode }"> 
							</td>
						</tr>  
						<tr>
							<th>상품 세부코드</th>  
							<td>
								<input type="text" name="planDetail" readonly="readonly" value="${plan.planDetail }">
							</td>  
						</tr>
						<tr>
							<th>상품 명 </th>
							<td>
								<input type="text" name="planName" readonly="readonly" value="${plan.planName }">
							</td>
						</tr>
						<tr>
							<th>상품 설명</th>
							<td class="long">
								<textarea cols="40" rows="5" name="planDesc"  readonly="readonly">${plan.planDesc }</textarea>
							</td>
								
						</tr>
						
						<c:if test="${plan.planDiv =='CV' }">   
							<tr class="long">
							<th>상품 가입 대상</th>
								<td>
									<select name="planFor" id="div">
										<option>가입 대상 선택</option>
										<option>기업 고객용</option>
										<option selected="selected">일반 고객용</option>
									</select>
								</td>
							</tr>   
							
							<tr>    
								<th>상품 가입 대상 등급</th>
								<td>
									<select name="planForDetail" id="div">
										<option>세부 선택</option>  
										<option selected="selected">VIP 등급용</option>
										<option>일반 등급용</option>
									</select>
								</td>
							</tr>
						</c:if>
						
						
						<c:if test="${plan.planDiv =='CN' }">   
							<tr class="long">
							<th>상품 가입 대상</th>
								<td>
									<select name="planFor" id="div">
										<option>가입 대상 선택</option>
										<option>기업 고객용</option>
										<option selected="selected">일반 고객용</option>
									</select>
								</td>
							</tr>   
							
							<tr>    
								<th>상품 가입 대상 등급</th>
								<td>
									<select name="planForDetail" id="div">
										<option>세부 선택</option>  
										<option>VIP 등급용</option>
										<option selected="selected">일반 등급용</option>
									</select>
								</td>
							</tr>
						</c:if>
						
						
						<c:if test="${plan.planDiv =='BV' }">   
							<tr class="long">
							<th>상품 가입 대상</th>
								<td>
									<select name="planFor" id="div">
										<option>가입 대상 선택</option>
										<option selected="selected">기업 고객용</option>
										<option>일반 고객용</option>
									</select>
								</td>
							</tr>   
							
							<tr>    
								<th>상품 가입 대상 등급</th>
								<td>
									<select name="planForDetail" id="div">
										<option>세부 선택</option>  
										<option selected="selected">VIP 등급용</option>
										<option>일반 등급용</option>
									</select>
								</td>
							</tr>
						</c:if>
						
						<c:if test="${plan.planDiv =='BN' }">   
							<tr class="long">
							<th>상품 가입 대상</th>   
								<td>
									<select name="planFor" class="div">
										<option>가입 대상 선택</option>
										<option selected="selected">기업 고객용</option>
										<option>일반 고객용</option>
									</select>
								</td>   
							</tr>            
							  
							<tr>    
								<th>상품 가입 대상 등급</th>   
				   				<td>
									<select name="planForDetail" class="div">
										<option>세부 선택</option>  
										<option>VIP 등급용</option>
										<option selected="selected">일반 등급용</option>
									</select>
								</td>
							</tr>
						</c:if>
						
						
					</table>
				</div>
				
				<div id="submit">
					<input type="submit" value="수정하기">
					<input type="submit" value="수정 완료">
					<input type="submit" value="삭제" formaction="${pageContext.request.contextPath}/bankwork/plan/planDelete.do?planCode=${plan.planCode}">   
					<input type="reset" value="취소" id="cancel">
				</div>
				
			</div>
		</form>
	</div>
</body>
</html>