<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../../include/menu.jsp"%>
<%@include file="../../include/sectionBar.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
</head>
<style>
	* { font-family: 'Noto Sans KR', sans-serif; }
	div#container {
		width: 1200px;
		height : 80%;
		margin: 50px auto;
	}
	div#year {
		width : 100%;
		padding : 20px;
	}
	label {
		float : left;
		width : 50px;
		font-weight: bold;
	}
	select {
		border : 1px solid goldenrod;
		background : goldenrod;
		color : white;
		font-weight: bold;
		padding : 2px;
	}
	div#dummy { height: 75px; background: #292929;}
	input[type='radio'] {
		margin-left : 10px;
	}
	p {
		margin-bottom : 20px;
		margin-left : 135px; 
	}
	span {
		font-weight: bold;
	}
}
</style>
<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
<script>
   $(function(){
	   var drawchart = function(div,year) {
		   var jenuary;
		   var february;
		   var march;
		   var april;
		   var may;
		   var june;
		   var july;
		   var august;
		   var september;
		   var october;
		   var november;
		   var december;
		   if(year==2020) {
			   switch(div) {
			   case "예금":
				    april = ${totalAmount[0]};
			 		break;
			 	case "적금":
			 		 april = ${totalAmount[1]};
			 		break;
			 	case "마이너스":
			 		 april = ${totalAmount[2]};
			 		break;
			 	case "체크카드":
			 		 april = ${totalAmount[3]};
			 		break;
			 	case "신용카드":
			 		april = ${totalAmount[4]};
			 		break;
			 	case "일반대출":
			 		april = ${totalAmount[5]};
			 		break;
			 	case "신용대출":
			 		april = ${totalAmount[6]};
			 		break;
			 	case "카드론":
			 		april = ${totalAmount[7]};
			 		break;
			 	} 
		   }
		   else {
				april = Math.floor((Math.random()*100000000)+1000000);  	
		   }
		   jenuary = Math.floor((Math.random()*100000000)+1000000);
		   february = Math.floor((Math.random()*100000000)+1000000);
		   march = Math.floor((Math.random()*100000000)+1000000);
		   may = Math.floor((Math.random()*100000000)+1000000);
		   june = Math.floor((Math.random()*100000000)+1000000);
		   july = Math.floor((Math.random()*100000000)+1000000);
		   august = Math.floor((Math.random()*100000000)+1000000);
		   september = Math.floor((Math.random()*100000000)+1000000);
		   october = Math.floor((Math.random()*100000000)+1000000);
		   november = Math.floor((Math.random()*100000000)+1000000);
	       december = Math.floor((Math.random()*100000000)+1000000);
		   google.charts.load("current", {packages:['corechart']});
	 	    google.charts.setOnLoadCallback(drawChart);
	 	    function drawChart() {
	 	      var data = google.visualization.arrayToDataTable([
	 	        ["월", "총액", { role: "style" } ],
	 	        ["1월", jenuary, "red"],
	 	        ["2월", february, "orange"],
	 	        ["3월", march, "yellow"],
	 	        ["4월", april, "lightgreen"],
	 	        ["5월", may, "green"],
	 	        ["6월", june, "darkgreen"],
	 	        ["7월", july, "lightblue"],
	 	        ["8월", august, "blue"],
	 	        ["9월", september, "deepblue"],
	 	        ["10월", october, "navy"],
	 	        ["11월", november, "grey"],
	 	        ["12월", december, "darkgrey"]
	 	      ]);

	 	      var view = new google.visualization.DataView(data);
	 	      view.setColumns([0, 1,
	 	                       { calc: "stringify",
	 	                         sourceColumn: 1,
	 	                         type: "string",
	 	                         role: "annotation" },
	 	                       2]);

	 	      var options = {
	 	        title: year + "년도 "+ div + " 통계",
	 	        width: 1400,
	 	        height: 600,
	 	        bar: {groupWidth: "95%"},
	 	        legend: { position: "none" },
	 	      };
	 	      var chart = new google.visualization.ColumnChart(document.getElementById("chart"));
	 	      chart.draw(view, options);
	 	  	}
	   }
	   
	   for (i = new Date().getFullYear(); i > 2009; i--)
	   {
	       $('#yearpicker').append($('<option />').val(i).html(i));
	   }
	   $(".chk").change(function() {
		    var year = $("select option:selected").val();
		    var div = $(this).val();
		 	switch($(this).val()) {
		 	case "예금":
		 		drawchart(div,year);
		 		break;
		 	case "적금":
		 		drawchart(div,year);
		 		break;
		 	case "마이너스":
		 		drawchart(div,year);
		 		break;
		 	case "체크카드":
		 		drawchart(div,year);
		 		break;
		 	case "신용카드":
		 		drawchart(div,year);
		 		break;
		 	case "일반대출":
		 		drawchart(div,year);
		 		break;
		 	case "신용대출":
		 		drawchart(div,year);
		 		break;
		 	case "카드론":
		 		drawchart(div,year);
		 		break;
		 	}
		})
		$("#yearpicker").change(function() {
			 var year = $("select option:selected").val();
			 var div = $("input[type='radio']:checked").val();
			 drawchart(div,year);
		})
		$(".chk").eq(0).prop("checked",true);
	    var year = $("select option:selected").val();
	    var div = $("input[type='radio']:checked").val();
	    drawchart(div,year);
   })
</script>
<body>
	<section>
		<div id="container">
			<div id="year">
				<p>
					<label>연도</label>
					<select name="yearpicker" id="yearpicker"></select>
				</p>
				<p>
					<label>구분</label>
					<input type="radio" name="div" value="예금" class="chk"><span>예금</span>
					<input type="radio" name="div" value="적금" class="chk"><span>적금</span>
					<input type="radio" name="div" value="마이너스" class="chk"><span>마이너스</span>
					<input type="radio" name="div" value="체크카드" class="chk"><span>체크카드</span>
					<input type="radio" name="div" value="신용카드" class="chk"><span>신용카드</span>
					<input type="radio" name="div" value="일반대출" class="chk"><span>일반대출</span>
					<input type="radio" name="div" value="신용대출" class="chk"><span>신용대출</span>
					<input type="radio" name="div" value="카드론" class="chk"><span>카드론</span> 
				</p>
			</div>
			<div id="chart">
			</div>
		</div>
	</section>
</body>
</html>