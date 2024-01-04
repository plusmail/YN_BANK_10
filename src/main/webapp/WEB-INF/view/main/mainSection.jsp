<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<link href="https://fonts.googleapis.com/css2?family=Kanit:wght@800&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://kit.fontawesome.com/6f2f0f2d95.js">
</script>
<style>
	* { margin: 0; padding: 0; /* font-family: 'Noto Serif KR', serif; */font-family: 'Noto Sans KR', sans-serif;}
	ul li { list-style: none; }
	a { text-decoration: none; color: #000; }
	/* 섹션 */
	section { height: 100%; } 
	
			  
	
	/* 환율 */
	section div#currency { width: 700px; height: 300px; margin-top: 50px; margin-left: 300px; float: left; }
	section div#currency h2 {  text-align: center;}
	/* 차트 */
	section div#mainchart { width: 800px;  overflow: hidden; }
	
	/* 공지사항 */
	section div#notice { width: 600px; height: 300px;  margin-left: 350px; margin-right: 150px; margin-top: 100px;  float: left; }
	section div#notice h2 { width: 100%;  height: 50px;  }
	section div#notice h2 span#noticeTitle { width: 200px; height: 50px; line-height: 50px;
						    text-indent: 10px; border-bottom: 3px solid goldenrod; }
	section div#notice div#noticeMng { width: 350px;
									   margin-left: 200px; 
									   text-align: right;}	
	section div#notice div#noticeMng #fa_minus { margin: 0 20px;}									   					    						   					    
	section div#notice ul { margin-top: 10px; }
	section div#notice ul li { height: 30px; }
	section div#notice span { display: block; float: left;}
	section div#notice span#no { width: 30px;  text-align: center; }
	section div#notice span#subject { width: 340px;}
	section div#notice span#subject a { display: block;}
	section div#notice span#writer { width: 100px; text-align: center;}
	section div#notice span#date { width: 100px; text-align: right;}
	
	/* 우수사원 */  
	section div#bestStaff { width: 600px; height: 300px; overflow:hidden;  margin-top: 100px; }
	section div#bestStaff h2 {width: 200px; height: 50px; line-height: 50px;
						    text-indent: 10px;
						    border-bottom: 3px solid goldenrod; }
	section div#bestStaff div#bestImg { width:200px; height: 200px; 
									    margin-top: 10px; 
										border-radius: 100px; overflow: hidden;
										float: left; }
	section div#bestStaff div#bestImg img { width: 200px; height: 200px; }
	section div#bestStaff span#bestTeam, span#bestWho { display: block; 
												        width: 300px;
												        height: 100px;  
												        line-height: 100px; 
													    overflow: hidden;
													    text-align: center;}
	span#bestTeam { font-size: 35px; font-weight: bold;}
	span#bestWho { font-size: 25px; }
	
	
	.myCalendar.nao-month td {
	  padding: 15px;
	}
	
	.myCalendar .month-head>div,
	.myCalendar .month-head>button {
	  padding: 15px;
	}
</style>

<body>
	<jsp:include page="../include/menu.jsp"/>
	<section>
	 <div id="dummy"></div>
	 	<div id="currency">
	 		<h2>환율</h2>
			<!--Currency Converter widget by FreeCurrencyRates.com -->
			
			<div id='gcw_mainFxfANK58B' class='gcw_mainFxfANK58B'></div>
			<a id='gcw_siteFxfANK58B' href='https://freecurrencyrates.com/en/'>FreeCurrencyRates.com</a>
			<script>
				function reloadFxfANK58B() {
					var sc = document.getElementById('scFxfANK58B');
					if (sc)
						sc.parentNode.removeChild(sc);
					sc = document.createElement('script');
					sc.type = 'text/javascript';
					sc.charset = 'UTF-8';
					sc.async = true;
					sc.id = 'scFxfANK58B';
					sc.src = 'https://freecurrencyrates.com/en/widget-table?iso=USD-EUR-GBP-RUB&df=2&p=FxfANK58B&v=fi&source=fcr&width=600&width_title=0&firstrowvalue=1&thm=A6C9E2,FCFDFD,4297D7,5C9CCC,FFFFFF,C5DBEC,FCFDFD,2E6E9E,000000&title=Currency%20Converter&tzo=-540';
					var div = document.getElementById('gcw_mainFxfANK58B');
					div.parentNode.insertBefore(sc, div);
				}
				reloadFxfANK58B();
			</script>
			</div>
			

 	<div id="mainchart">
	    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	    <script type="text/javascript">
	      google.charts.load('current', {'packages':['corechart']});
	      google.charts.setOnLoadCallback(drawVisualization);
	
	      function drawVisualization() {
	        // Some raw data (not necessarily accurate)
	        var data = google.visualization.arrayToDataTable([
	          [' ','예적금 및 이자 총액','대출 총금액','현재 은행 출자금'],
	          [' ',${contribution.totalDepositWithdrawAmount},${contribution.totalLoanAmount},${contribution.totalContribution}]
	        ]);
	
	        var options = {
	          title : 'YN-BANK 출자금',
	          vAxis: {title: 'Amount'},
	          hAxis: {title: ''},
	          seriesType: 'bars',
	          series: {5: {type: 'line'}}};
	
	        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
	        chart.draw(data, options);
	      }
	    </script>
	 
	    <div id="chart_div" style="width: 800px; height: 400px;"></div>
    </div>
    
	<div id="notice">
    	<h2>
    		<span id="noticeTitle">공지사항</span>
    		<c:if test="${Auth.empAuth=='AD'}">
    			<div id="noticeMng">
		    		<a href="${pageContext.request.contextPath}/main/noticeAdd.do"><i class="fas fa-plus" id="fa_plus"></i></a>
    			</div>
    		</c:if>
    		
    	</h2>
    	
    	<ul>
    		<c:forEach var="notice" items="${list}">
    		<li>
    			<span id="no">${notice.no}</span>
    			<span id="subject"><a href="${pageContext.request.contextPath}/main/noticeDetail.do?no=${notice.no}">${notice.subject}</a>
    			</span>
    			<span id="writer">${notice.writer}</span>
    			<span id="date"><fmt:formatDate value="${notice.writeDate}" pattern="yyyy-MM-dd"/></span>
    		</li>
    		</c:forEach>
    	</ul>
    </div> 
    
    
    <div id="bestStaff">
    	<h2><span id="bestTitle">이 달의 우수사원</span></h2>
    	<ul>
    		<li>
    			<div id="bestImg">
    				<img alt="우수사원" src="${pageContext.request.contextPath}/empPic/${empNo1.pic}">
    			</div>
    			<span id="bestTeam">
    			
    			YN Bank</span>
    			<span id="bestWho">${empNo1.empName } ${empNo1.empTitle }</span>
    		</li>
    	</ul>
    </div>
    <c:if test="${successadd!=null}">
    	<script>
    		alert("추가되었습니다");
    		<%
    			session.removeAttribute("successadd");
    		%>
    	</script>
    </c:if>
    <c:if test="${successmod!=null}">
    	<script>
    		alert("수정되었습니다");
    		<%
    			session.removeAttribute("successmod");
    		%>
    	</script>
    </c:if>
    <c:if test="${successdel!=null}">
    	<script>
    		alert("삭제되었습니다");
    		<%
    			session.removeAttribute("successdel");
    		%>
    	</script>
    </c:if> 
	</section> 
</body>