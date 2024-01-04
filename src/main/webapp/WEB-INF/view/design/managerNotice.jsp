<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Kanit:wght@800&display=swap" rel="stylesheet">
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://kit.fontawesome.com/6f2f0f2d95.js">
</script>
</head>
<style>
	* { margin: 0; padding: 0; /* font-family: 'Noto Serif KR', serif; */font-family: 'Noto Sans KR', sans-serif;}
	ul li { list-style: none; }
	a { text-decoration: none; color: #000; }
	/* 섹션 */
	section { height: 100%; } 
	
	section div#dummy { height: 75px; background: #292929;}		  
	
	/* 환율 */
	section div#currency { width: 700px; height: 300px; margin-top: 50px; margin-left: 300px; float: left; }
	section div#currency h2 {  text-align: center;}
	/* 차트 */
	section div#mainchart { width: 800px;  overflow: hidden; }
	
	/* 공지사항  */
	/* -------------여기부터 ----------------  */
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
	section div#notice span#no { width: 20px;  text-align: center; }
	section div#notice span#content { width: 340px;}
	section div#notice span#content a { display: block;}
	section div#notice span#writer { width: 100px; text-align: right;}
	section div#notice span#date { width: 100px; text-align: right;}
	
	/* -------------여기까지 ----------------  */
	
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
	          ['Month', '예금',      '적금',       '대출',         'Average' ],
	          ['2004/05',  165,      938,         522,             998,   ],
	          ['2005/06',  135,      1120,        599,             1268,  ],
	          ['2006/07',  157,      1167,        587,             807,   ],
	          ['2007/08',  139,      1110,        615,             968,   ],
	          ['2008/09',  136,      691,         629,             1026,  ]
	        ]);
	
	        var options = {
	          title : '월별 예/적금/대출 금액',
	          vAxis: {title: 'Amount'},
	          hAxis: {title: 'Month'},
	          seriesType: 'bars',
	          series: {5: {type: 'line'}}        };
	
	        var chart = new google.visualization.ComboChart(document.getElementById('chart_div'));
	        chart.draw(data, options);
	      }
	    </script>
	 
	    <div id="chart_div" style="width: 800px; height: 400px;"></div>
    </div>
    
	<div id="notice">
    	<h2>
    		<!--  ----------여기부터-------------    -->
    		<span id="noticeTitle">공지사항</span>
    		<div id="noticeMng">
	    		<a href="#"><i class="fas fa-plus" id="fa_plus"></i></a>
	    		<a href="#"><i class="fas fa-minus" id="fa_minus"></i></a>
	    		<a href="#"><i class="fas fa-edit" id="fa_edit"></i></a>
    		</div>
    		
    		<!--  -----------여기까지------------    -->
    		
    	</h2>
    	
    	<ul>
    		<li>
    			<span id="no">1</span>
    			<span id="content"><a href="#">코로나19로 인한 휴점</a></span>
    			<span id="writer">관리자</span>
    			<span id="date">2020.03.31</span>
    		</li>
    		<li>
    			<span id="no">2</span>
    			<span id="content"><a href="#">코로나19로 인한 휴점</a></span>
    			<span id="writer">관리자</span>
    			<span id="date">2020.03.31</span>
    		</li>
    		<li>
    			<span id="no">3</span>
    			<span id="content"><a href="#">코로나19로 인한 휴점</a></span>
    			<span id="writer">관리자</span>
    			<span id="date">2020.03.31</span>
    		</li>
    		<li>
    			<span id="no">4</span>
    			<span id="content"><a href="#">코로나19로 인한 휴점</a></span>
    			<span id="writer">관리자</span>
    			<span id="date">2020.03.31</span>
    		</li>
    		<li>
    			<span id="no">5</span>
    			<span id="content"><a href="#">코로나19로 인한 휴점</a></span>
    			<span id="writer">관리자</span>
    			<span id="date">2020.03.31</span>
    		</li>
    	</ul>
    </div> 
    
    
    <div id="bestStaff">
    	<h2><span id="bestTitle">이 달의 우수사원</span></h2>
    	<ul>
    		<li>
    			<div id="bestImg">
    				<img alt="우수사원" src="images/staff2.jpg">
    			</div>
    			<span id="bestTeam">YN 인사팀</span>
    			<span id="bestWho">이지은 대리</span>
    		</li>
    	</ul>
    </div> 
	</section> 
</body>
</html>