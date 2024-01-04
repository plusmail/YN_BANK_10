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
<style>
  
</style>
</head>
<script>

/* var ajax;
var div;
var search;

function getAjaxData(ajaxPaging,ajaxKey){
	div = $("#searchMenu option:selected").val();
    search = $("input[name='search']").val();

    var $table = $("<table>").addClass("tableList");

    var $menutr = $("<tr>");
    var $menutd1 = $("<td>").html("사원코드");
    var $menutd2 = $("<td>").html("사원이름");
    var $menutd3 = $("<td>").html("직책");
    var $menutd4 = $("<td>").html("부서");
    var $menutd5 = $("<td>").html("권한");
   
    $menutr.append($menutd1);
    $menutr.append($menutd2);
    $menutr.append($menutd3);
    $menutr.append($menutd4);
    $menutr.append($menutd5);
    
	  $.ajax({
	    url: "${pageContext.request.contextPath}/emp/empAuth.do",
	    type: "post", 
	    data: {"search":search,"div":div},
	    dataType: "json",
	    success : function(res){
	    	console.log(res);
	    	if(res.error == "notExist"){
	    		alert("존재하지 않는 사원입니다. 사원번호를 확인해주세요");
	    	}else{
	    		
	    		$(".tableList").remove();   
	    		$table.append($menutr);
	    		$(ajaxKey).each(function(i,obj){
	    			var $tr = $("<tr class='oneEmp'>").attr("data-empCode",obj.empCode);
	    			var $td1 = $("<td>").html(obj.empCode);
	    			var $td2 = $("<td>").html(obj.empName);
	    			var $td3 = $("<td>").html(obj.empTitle);
	    			var $td4 = $("<td>").html(obj.dept.deptName);
	    			var $td5 = $("<td>").html(obj.empAuth);
               
	    			$tr.append($td1);
	    			$tr.append($td2);
	    			$tr.append($td3);
	    			$tr.append($td4);
	    			$tr.append($td5);
	    			
	    		
	    			$table.append($tr);
	    		})
	    		//테이블 div
	    		$("#table").append($table);
	    		      
	    		$(".sorter").remove();
	    		$divSorter = $("<div>").addClass("sorter");
	    		$ulPaging = $("<ul>").addClass("pagination");
	    		$liPaging1 = $("<li>");
	    		$aPaging1 = $("<a>").attr("href", "#").addClass("prev").html("Prev");
	    		
	    		$liPaging1.append($aPaging1);
	    		$ulPaging.append($liPaging1);
	    		
	    		
	    		for(var i=ajaxPaging.startPageNo; i<=ajaxPaging.endPageNo; i++){
	    			$liPagingRepeat1 = $("<li>").addClass("active");
		    		$aPagingRepeat1 = $("<a>").attr("href", "#").addClass("page").html(i);
		    		$liPagingRepeat2 = $("<li>");
		    		$aPagingRepeat2 = $("<a>").attr("href", "#").addClass("page").html(i);
		    		
		    		$liPagingRepeat1.append($aPagingRepeat1);
		    		
		    		$ulPaging.append($liPagingRepeat1);
	    		}
	    		
	    		$liPaging2 = $("<li>");
	    		$aPaging2 = $("<a>").attr("href", "#").addClass("next").html("Next");
	    		
	    		$liPaging2.append($aPaging2);
	    		$ulPaging.append($liPaging2);
	    		
	    		$divSorter.append($ulPaging);
	    		
	    		$("#table").append($divSorter);
	    		ajax = true;
	    	}
	    }
	  
  })
	
}
 */
   $(function(){
	   
	   var searchType;
	   var keyword;
	   //목록보이기
	  $("#empAuthAdd").show();
	  
	  $("select").on("change",function(){
		  $(".tableList").load(location.href+" .tableList");
		  $("#searchForEmp").val("");
	      $(".pagination").load(location.href+" .pagination li");   
	  })
	  $("button").eq(0).click(function(){
		searchType = $("#searchMenu option:selected").val();
		keyword = $("input[name='search']").val();
	    
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
		location.href = "empAuth.do?searchType="+searchType+"&keyword="+keyword;
 
        /* var $table = $("<table>").addClass("tableList");

        var $menutr = $("<tr>");
        var $menutd1 = $("<td>").html("사원코드");
        var $menutd2 = $("<td>").html("사원이름");
        var $menutd3 = $("<td>").html("직책");
        var $menutd4 = $("<td>").html("부서");
        var $menutd5 = $("<td>").html("권한");
       
        $menutr.append($menutd1);
        $menutr.append($menutd2);
        $menutr.append($menutd3);
        $menutr.append($menutd4);
        $menutr.append($menutd5);
  
		  switch(div) {
			case "검색구분":
				alert("검색 조건을 선택해주세요.");
				  break;
			 
			case "사원번호":
					$.ajax({
					    url: "${pageContext.request.contextPath}/emp/empAuth.do",
					    type: "post", 
					    data: {"search":search,"div":div},
					    dataType: "json",
					    success : function(res){
					    	//console.log(res);
					    	if(res.error == "notExist"){   
					    		alert("검색한 사원번호와 일치하는 사원이 존재하지 않습니다.");
					    	}else{
					    		//console.log(res.employee);
					    	
				        getAjaxData(res.paging,res.employee);
			    }} });
				
			  break; 
			case "사원이름":
					$.ajax({
					    url: "${pageContext.request.contextPath}/emp/empAuth.do",
					    type: "post", 
					    data: {"search":search,"div":div},
					    dataType: "json",
					    success : function(res){
					    	//console.log(res);
					    	if(res.error == "notExist"){   
					    		alert("조건을 만족하는 사원이 없습니다.");
					    	}else{
					    		//console.log(res.employee);
					    	
				        getAjaxData(res.paging,res.list);
			    }} });
				
			  break;  
			case "부서(인사 or 고객)":
					$.ajax({
					    url: "${pageContext.request.contextPath}/emp/empSearch.do",
					    type: "post", 
					    data: {"search":search,"div":div},
					    dataType: "json",
					    success : function(res){
					    	//console.log(res);
					    	if(res.error == "notExist"){   
					    		console.log(res.error);
					    		alert("존재하지 않는 부서이거나 데이터가 존재하지 않습니다");
					    	}else{
					    		//console.log(res.employee);
					    	
				        getAjaxData(res.paging,res.list);
			    }} });
				  break;  	
			
			case "직급":
				$.ajax({
				    url: "${pageContext.request.contextPath}/emp/empSearch.do",
				    type: "post", 
				    data: {"search":search,"div":div},
				    dataType: "json",
				    success : function(res){
				    	//console.log(res);
				    	if(res.error == "notExist"){   
				    		alert("존재하지 않는 직급이거나 해당 직급을 가진 사원이 없습니다.");
				    	}else{
				    		//console.log(res.employee);
				    	
			        getAjaxData(res.paging,res.list);
		      }} });
				
			  break;
		  } */
		  
	  }) //버튼 끝남 
	  
	  //각 줄을 클릭할 때마다 내가 부여한 data-empCode를 받아오기 
	  //동적인거라 document
	  $(document).on("click",".oneEmp",function(){

		  var OneCode = $(this).attr("data-empCode");
		  //alert(OneCode);
		  location.href="${pageContext.request.contextPath}/emp/empAuthDetail.do?empCode="+OneCode;
	  })
	  
	  
	   $("select").on("change", function() {
		  	var href = location.href;
		  	href = href.substring(0, href.indexOf("?"));
		  	if(href==null) {
		  		$("table").load(location.href + " table");
				$("input[name='search']").val("");
		  	}
		  	else {
		  		$("table").load(href + " table");
				$("input[name='search']").val("");
		  	}
	  })
	  
/* 	   //페이지 각 번호 클릭 시  
		$(document).on("click", ".page",function() {
			if(ajax) {
				var page = $(this).html();
				ajax = false;
		        location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page+"&search="+search+"&div="+div;
			}
			else {
				if(isPagingAjax) {
					var page = $(this).html();
					div = $("#searchMenu option:selected").val();
					search = $("input[name='search']").val();
					location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page+"&search="+search+"&div="+div;
				}
				else {
					var page = $(this).html();
			        location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page;
				}
			}
		})   
		
		//prev 클릭시 이전 번호로 돌아감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".prev" , function(){
			if(ajax) {
				var page = ${paging.pageNo}-1;
				//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
				if($(".page").size()==1){
					return false;       
				}
				ajax = false;
				location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page+"&search="+search+"&div="+div;
			}
			else {
				if(isPagingAjax) {
					var page = ${paging.pageNo}-1;
					if($(".page").size()==1){
						return false;       
					}
					div = $("#searchMenu option:selected").val();
					search = $("input[name='search']").val();
					//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
					location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page+"&search="+search+"&div="+div;
				}
				else {
					var page = ${paging.pageNo}-1;
					//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
					if($(".page").size()==1){
						return false;       
					}
					location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page;
				}
			}
		})  
		//next 클릭시  다음 번호로 넘어감 (paging.pageNo = 현재 페이지 넘버)
		$(document).on("click", ".next" , function(){
			if(ajax) {
				var page = ${paging.pageNo}+1;
				if($(".page").size()==1){         
					return false;   
				} 
				div = $("#searchMenu option:selected").val();
				search = $("input[name='search']").val();
				ajax = false;
				    location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page+"&search="+search+"&div="+div;
			}
			else {
				if(isPagingAjax) {
					var page = ${paging.pageNo}+1;
					if($(".page").size()==1){         
						return false;   
					} 
					div = $("#searchMenu option:selected").val();
					search = $("input[name='search']").val();
					if($(".page").size()==1){         
						return false;   
					}
					location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page+"&search="+search+"&div="+div;
				}
				else {
					var page = ${paging.pageNo}+1;
					//.page 태그(페이징의 번호)가 1개 밖에 없을 경우(1페이지 밖에 없을 경우) prev, next 버튼으로 이동 제한
					if($(".page").size()==1){         
						return false;   
					}       
					location.href = "${pageContext.request.contextPath}/emp/empAuth.do?page="+page;
				}
			}
		}) */
		
	/* 	$(document).on("mouseover", ".page", function(){
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
		var isPagingAjax = "${pagingAjax}"=="true"?true:false; */
	
	
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
	<h2 id="menuLocation">사원 권한 수정</h2>
		<div id="search">
				<select id="searchMenu">
				    <option>검색구분</option>
					<option>사원번호</option>
					<option>사원이름</option>
					<option>부서</option>
					<option>직급</option>
					
				</select>    
			
					<fieldset><input type="search" name="search" id="searchForEmp" placeholder="검색어를 입력하세요."/>
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
				
		</div>
		<div id="table">
		<span><i class="fas fa-exclamation-circle"></i></span><span id="guide">권한을 수정하려면 사원을 클릭하세요.</span>
			<table class="tableList">
				<tr>
					<th>사원코드</th>
					<th>사원이름</th>
					<th>직책</th>
					<th>부서</th>
					<th>권한</th>
				

				</tr>
				<span id="renew">
				<c:forEach var='empList' items="${list }">
				<tr class="oneEmp" data-empCode="${empList.empCode }">
					<td>${empList.empCode }</td>
					<td>${empList.empName }</td>
					<td>${empList.empTitle }</td>
					<td>${empList.dept}</td>
					<td>${empList.empAuth }</td>	
				</tr>
	            
				</c:forEach>
				</span>
		</table>
		<div class="sorter">   
		      <ul class="pagination">
				<c:if test="${pageMaker.prev == true}">
					<li><a href="empAuth.do?page=${pageMaker.startPage-1}&searchType=${cri.searchType}&keyword=${cri.keyword}">&laquo;</a></li>
				</c:if>
				<c:forEach begin="${pageMaker.startPage}" end="${pageMaker.endPage}" var="idx">
					<li class="${cri.page==idx?'active':''}"><a href="empAuth.do?page=${idx}&searchType=${cri.searchType}&keyword=${cri.keyword}">${idx}</a></li>
				</c:forEach>
				<c:if test="${pageMaker.next == true}">
					<li><a href="empAuth.do?page=${pageMaker.endPage+1}&searchType=${cri.searchType}&keyword=${cri.keyword}">&raquo;</a></li>
				</c:if>
			</ul>
		    </div>  
		</div>
		
		<c:if test="${successed !=null }">
		   <script>
		       alert("${authEmpName}님의 권한이 수정되었습니다.");
		          <%
		             session.removeAttribute("successed");
		             session.removeAttribute("authEmpName");
		          %>
		   </script>
		</c:if>
		
		<c:if test="${fn:length(list)==0}">
        	<script>
        		alert("검색 결과가 존재하지 않습니다");
        		location.href="empAuth.do";
        	</script>
        </c:if>
		</section>
</body>
</html>