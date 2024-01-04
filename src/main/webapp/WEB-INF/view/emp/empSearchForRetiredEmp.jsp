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
<link href="${pageContext.request.contextPath}/css/listCSS.css" rel="stylesheet" />
</head>

<script>

function getAjaxData(errMessage){
	var div = $("#searchMenu option:selected").val();
    var search = $("input[name='search']").val();

    var $table = $("<table>").addClass("tableList");

    var $menutr = $("<tr>");
    var $menutd1 = $("<td>").html("사원코드");
    var $menutd2 = $("<td>").html("사원이름");
    var $menutd3 = $("<td>").html("직책");
    var $menutd4 = $("<td>").html("권한");
    var $menutd5 = $("<td>").html("월급");
    var $menutd6 = $("<td>").html("연락처");
    var $menutd7 = $("<td>").html("아이디");
    var $menutd8 = $("<td>").html("부서");
    $menutr.append($menutd1);
    $menutr.append($menutd2);
    $menutr.append($menutd3);
    $menutr.append($menutd4);
    $menutr.append($menutd5);
    $menutr.append($menutd6);
    $menutr.append($menutd7);
    $menutr.append($menutd8);


	  $.ajax({
	    url: "${pageContext.request.contextPath}/emp/empSearchRetired.do",
	    type: "post", 
	    data: {"search":search,"div":div},
	    dataType: "json",
	    success : function(res){
	    	console.log(res);
	    	if(res.error == "notExist"){
	    		alert(errMessage);
	    	}else{
	    		
	    		$(".tableList").remove();

	    		$(res).each(function(i,obj){
	    			var $tr = $("<tr class='oneEmp'>").attr("data-empCode",obj.empCode);
	    			var $td1 = $("<td>").html(obj.empCode);
	    			var $td2 = $("<td>").html(obj.empName);
	    			var $td3 = $("<td>").html(obj.empTitle);
	    			var $td4 = $("<td>").html(obj.empAuth);
	    			var $td5 = $("<td>").html(obj.empSalary);
	    			var $td6 = $("<td>").html(obj.empTel);
	    			var $td7 = $("<td>").html(obj.empId);
	    			var $td8 = $("<td>").html(obj.dept.deptName);
               
	    			$tr.append($td1);
	    			$tr.append($td2);
	    			$tr.append($td3);
	    			$tr.append($td4);
	    			$tr.append($td5);
	    			$tr.append($td6);
	    			$tr.append($td7);
	    			$tr.append($td8);

	    			
	    			$table.append($menutr);
	    			$table.append($tr);
	    		})
	    		//테이블 div
	    		$("#table").append($table);
	    	}
	    }
	  
  })
}
   
   $(function(){
	 //선택한 메뉴 보이도록 
		$("#empAdd").show();
		$("#empList").show();
	  $("select").on("change",function(){
		  $("table").load(location.href+" table");
		  $("#searchForEmp").val("");
	  })
	  $("button").eq(0).click(function(){
		var div = $("#searchMenu option:selected").val();
	    var search = $("input[name='search']").val();
   
        var $table = $("<table>").addClass("tableList");

        var $menutr = $("<tr>");
        var $menutd1 = $("<td>").html("사원코드");
        var $menutd2 = $("<td>").html("사원이름");
        var $menutd3 = $("<td>").html("직책");
        var $menutd4 = $("<td>").html("권한");
        var $menutd5 = $("<td>").html("월급");
        var $menutd6 = $("<td>").html("연락처");
        var $menutd7 = $("<td>").html("아이디");
        var $menutd8 = $("<td>").html("부서");
        $menutr.append($menutd1);
        $menutr.append($menutd2);
        $menutr.append($menutd3);
        $menutr.append($menutd4);
        $menutr.append($menutd5);
        $menutr.append($menutd6);
        $menutr.append($menutd7);
        $menutr.append($menutd8);
        
		  switch(div) {
			case "검색구분":
				alert("검색 조건을 선택해주세요.");
				  break;
			 
			case "사원번호":
				
				getAjaxData("해당 사원번호를 가진 퇴사 사원이 존재하지 않습니다.");
				
			  break; 
			case "사원이름":
				
				getAjaxData("해당 조건을 만족하는 사원이 없습니다.");
				
			  break;  
			case "부서(인사 or 고객)":
				
				getAjaxData("해당 부서가 존재하지 않거나 조건을 만족하는 데이터가 없습니다.");
			
			  break;  
			
			case "직급":
				
				getAjaxData("해당 직급이 존재하지 않거나 조건을 만족하는 데이터가 없습니다.");
				
			  break;
		  }
		  
	  }) //버튼 이벤트 끝나는 것 
	   
	  
	  //각 줄을 클릭할 때마다 내가 부여한 data-empCode를 받아오기 
	  $(document).on("click",".oneEmp",function(){
		  var OneCode = $(this).attr("data-empCode");
		  //alert(OneCode);
		  location.href="${pageContext.request.contextPath}/emp/empDetailForRetired.do?empCode="+OneCode;
	  })
	  
   })

</script>
<body>
	<section>
	<h2 id="menuLocation">퇴사사원 목록</h2>
		<div id="search">
				<select id="searchMenu">
				    <option>검색구분</option>
					<option>사원번호</option>
					<option>사원이름</option>
					<option>부서(인사 or 고객)</option>
					<option>직급</option>
					
				</select>
			
					<fieldset><input type="search" name="search" id="searchForEmp" placeholder="검색어를 입력하세요.">
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
				
		</div>
		<a href="empSearch.do"><button id="btnNone">일반사원 리스트</button></a><br><br>
		<div id="coutOfEmp">인사팀  : ${HR }명 ,  고객팀 : ${CS }명</div>
		<div id="table">
			<table class="tableList">
				<tr>
					<th>사원코드</th>
					<th>사원이름</th>
					<th>직책</th>
					<th>권한</th>
					<th>월급</th>
					<th>연락처</th>
					<th>아이디</th>
					<th>부서</th>

				</tr>
				<span id="renew">
				<c:forEach var='empList' items="${list }">
				<tr class="oneEmp" data-empCode="${empList.empCode }">
					<td>${empList.empCode }</td>
					<td>${empList.empName }</td>
					<td>${empList.empTitle }</td>
					<td>${empList.empAuth }</td>
					<td><fmt:formatNumber value='${empList.empSalary }' pattern='###,###,###'/></td>
					<td>${empList.empTel }</td>
					<td>${empList.empId }</td>
					<td>${empList.dept}</td>
					
				</tr>
	          
				</c:forEach>
				</span>
		</table>
		
		</div>
		</section>
</body>
</html>