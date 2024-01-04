<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../include/menu.jsp"%>
<%@include file="../include/sectionBar.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
   $(function(){
	   var searchType;
	   var keyword;
	   //선택한 메뉴 보이도록 설정 
	   $("#empAdd").show();
	   $("#empList").show();
	  
	  $("button").eq(0).click(function(){ 
		    searchType = $("#serachType option:selected").val();
			keyword = $("input[name='keyword']").val();
			if(searchType=="검색구분") {
				alert("검색 구분을 선택해주세요"); 
				return false;
			}
			 if(searchType=="부서") {
			    	var deptString = '고객부서';
					if(deptString.indexOf(keyword) == -1 ){
						
						alert("부서 혹은 고객으로만 검색 가능합니다.")
						return false;
					}

				}
			location.href = "empSearch.do?searchType="+searchType+"&keyword="+keyword;
	  })
	  
	  //각 줄을 클릭할 때마다 내가 부여한 data-empCode를 받아오기 
	  $(document).on("click",".oneEmp",function(){
		  var OneCode = $(this).attr("data-empCode");
		  //alert(OneCode);
		  location.href="${pageContext.request.contextPath}/emp/empDetail.do?empCode="+OneCode;
	  })
   });
</script>   
<body>   
	<section>
	<h2 id="menuLocation">사원 목록</h2>
		<div id="search">
				<select id="serachType">
				    <option>검색구분</option>
					<option ${cri.searchType=='사원번호'?'selected':''}>사원번호</option>
					<option ${cri.searchType=='사원이름'?'selected':''}>사원이름</option>
					<option ${cri.searchType=='부서'?'selected':''}>부서</option>    
					<option ${cri.searchType=='직급'?'selected':''}>직급</option>
				</select>
					<fieldset><input type="search" name="keyword" id="searchForEmp" placeholder="검색어를 입력하세요." value="${cri.keyword}"/>
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
		</div>
		<a href="empSearchRetired.do"><button id="btnNone">퇴사사원 조회</button></a><br><br>
		<span id="coutOfEmp">인사팀  : ${HR }명 ,  고객팀 : ${CS }명</span>
		<span id="avgSalary"> 평균급여 : <fmt:formatNumber value="${avgSal}" pattern="###,###,###"/> 원</span>
		<div id="table">
			<span><i class="fas fa-exclamation-circle"></i></span><span id="guide">사원 세부 정보를 보려면 사원을 클릭하세요.</span>
			<table class="tableList">
				<tr>
					<th>사원코드</th>
					<th>사원이름</th>
					<th>직책</th>
					<th>권한</th>
					<th>월급</th>
					<th class="thTel">연락처</th>
					<th>아이디</th>
					<th>비밀번호</th>
					<th>부서</th>

				</tr>
				<span id="renew"></span>
				<c:forEach var='empList' items="${list}">
				<tr class="oneEmp" data-empCode="${empList.empCode }" >
					<td>${empList.empCode }</td>
					<td>${empList.empName }</td>
					<td>${empList.empTitle }</td>
					<td>${empList.empAuth }</td>
					<td class='alright'><fmt:formatNumber value='${empList.empSalary }' pattern='###,###,###'/></td>
					<td>${empList.empTel }</td>
					<td>${empList.empId }</td>
					<td>**********</td>
					<td>${empList.dept}</td>		
				</tr>
				</c:forEach>
				
		</table>
		<div class="sorter">
			<ul class="pagination">
				<c:if test="${pageMaker.prev == true}">
					<li><a href="empSearch.do?page=${pageMaker.startPage-1}&searchType=${cri.searchType}&keyword=${cri.keyword}">&laquo;</a></li>
				</c:if>
				<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
					<li class="${cri.page==idx?'active':''}"><a href="empSearch.do?page=${idx}&searchType=${cri.searchType}&keyword=${cri.keyword}">${idx}</a></li>
				</c:forEach>
				<c:if test="${pageMaker.next == true}">
					<li><a href="empSearch.do?page=${pageMaker.endPage+1}&searchType=${cri.searchType}&keyword=${cri.keyword}">&raquo;</a></li>
				</c:if>
			</ul>
		</div>
		</div>
		<c:if test="${successed !=null}">
	    	<script>
	    		alert("${deletedEmp}님 퇴사처리 되었습니다.");
	    		<%
	    			session.removeAttribute("successed");
	    		    session.removeAttribute("deletedEmp");
	    		%>
	    	</script>
        </c:if>
        <c:if test="${successedForAdd !=null}">
	    	<script>
	    		alert("${addedEmp}님의 정보가 등록되었습니다.");
	    		<%
	    			session.removeAttribute("successedForAdd");
	    		    session.removeAttribute("addedEmp");
	    		%>
	    	</script>
        </c:if>
        <c:if test="${fn:length(list)==0}">
        	<script>
        		alert("검색 결과가 존재하지 않습니다");
        		location.href="empSearch.do";
        	</script>
        </c:if>
		</section>
</body>
</html>