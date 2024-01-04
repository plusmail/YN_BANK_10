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
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.8.0/Chart.bundle.min.js"></script>
</head>
<style>
		* { font-family: 'Noto Sans KR', sans-serif; }
		div#empStatisticCenter{
		   width: 900px;
		   margin-left:350px;
		   margin-top:50px;
		
		}
		div.empChart{
		  width:800px;
		}
</style>
<script>
   $(function(){
	   
	   //선택한 메뉴 보이도록 설정 
	   $("#empStatistic").show();
	   $("#custStatistic").show();
	   $("#bankworkStatistic").show();
	  $("select").on("change",function(){
		  $("table").load(location.href+" table");
		  $("#searchForEmp").val("");
	  })

	   
	  
	  //각 줄을 클릭할 때마다 내가 부여한 data-empCode를 받아오기 
	  $(document).on("click",".oneEmp",function(){
		  var OneCode = $(this).attr("data-empCode");
		  //alert(OneCode);
		  location.href="${pageContext.request.contextPath}/emp/empDetail.do?empCode="+OneCode;
	  })
	  
              var HR = $("#numOfHR").val(); 
		      var CS = $("#numOfCS").val(); 
		      var total = HR + CS;
	  var ctx = document.getElementById('empChart1');
	  var myChart = new Chart(ctx, {
		      
		        type: 'pie', 
		        data: {
			            labels: ['인사팀', '고객팀'],
			            datasets: [{
			            data: [HR,CS],
			            backgroundColor: ['#1e88e5', '#ffd600'],
			            borderWidth: 0.5 ,
			            borderColor: '#ddd'
		        }]
		    },
		    options: {
		        title: {
		            display: true,
		            text: '부서별 사원 수',
		            position: 'top',
		            fontSize: 16,
		            fontColor: '#111',
		            padding: 20
		        },
		        legend: {
		            display: true,
		            position: 'bottom',
		            labels: {
		                boxWidth: 20,
		                fontColor: '#111',
		                padding: 15
		            }
		        },
		        tooltips: {
		            enabled: false
		        },
		        plugins: {
		            datalabels: {
		                color: '#111',
		                textAlign: 'center',
		                font: {
		                    lineHeight: 1.6
		                },
		                formatter: function(value, ctx) {
		                	 return ctx.chart.data.labels[ctx.dataIndex] + '\n' + value + '%';
		                }
		            }
		        }
		    }

		   
		});
	  
	   
   })


</script>
<body>


	<section>
		<input type="hidden" value="${HR }" id="numOfHR">
		<input type="hidden" value="${CS }" id="numOfCS">
		<div id="empStatisticCenter">
		      <h1>부서별 사원 수</h1>
		      <div class="empChart">
              <canvas id="empChart1" width="1000" height="400"></canvas>
              </div>
				<c:forEach var='empList' items="${list }">
				<%-- <tr class="oneEmp" data-empCode="${empList.empCode }" >
					<td>${empList.empCode }</td>
					<td>${empList.empName }</td>
					<td>${empList.empTitle }</td>
					<td>${empList.empAuth }</td>
					<td><fmt:formatNumber value='${empList.empSalary }' pattern='###,###,###'/></td>
					<td>${empList.empTel }</td>
					<td>${empList.empId }</td>
					<td>**********</td>
					<td>${empList.dept}</td>
					
				</tr> --%>
				</c:forEach>
				
				<h1>급여총액/ 1인당 평균 급여액 </h1>
				<h1>보너스 현황</h1>
	
		</div>
	</section>
</body>
</html>