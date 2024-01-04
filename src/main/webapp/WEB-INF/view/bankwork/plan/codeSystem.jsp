<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>YN BANK 코드 분류 체계</title>
</head>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<script src="https://kit.fontawesome.com/6f2f0f2d95.js"></script>
<style>
	* { font-family: 'Noto Sans KR', sans-serif;
		color: #252525;}
	div#container { width: 900px; 
					margin: 0 auto;}
	div#container h1 { text-indent: 5px;
					   width: 500px;
					   height: 60px;
					   border-bottom: 2px solid #aa998b; }
	div#tableWrap { width: 800px; padding: 30px 0;  }
	table { width: 800px; height: 400px; border-collapse: collapse; 
		   }
	table th { background: goldenrod;}
	table td { border: 1px solid #b5aea9;font-size: 15px;}
	table tr, td, th {  text-align: center; font-size: 18px;}
	table .div { background: whitesmoke; font-weight: bold;}
	p { font-weight: bold;}
</style>

<body>
	<div id="container">
		<h1><i class="fab fa-codepen"></i>YN BANK 코드 분류 체계</h1>
		
		<div id="tableWrap">
		<table>
			<tr>
				<th>구분</th>
				<th>상품 코드</th>
				<th>구분</th>
				<th>상품 세부 코드</th>
				<th>구분</th>
				<th>상품 구분 코드</th>
			</tr>
			<tr>
				<td rowspan="3" class="div">통장</td>
				<td rowspan="3">A</td>
				<td class="div">예금</td>
				<td>AA</td>
				<td rowspan="4" class="div">기업 상품</td>
				<td rowspan="2">BV (기업 고객 VIP 등급)</td>
			</tr>
			<tr>
				<td class="div">적금</td>
				<td>AB</td>
			</tr>
			<tr>
				<td class="div">마이너스</td>
				<td>AC</td>
				<td rowspan="2">BN (기업 고객 일반  등급)</td>
			</tr>
			
			
			<tr>
				<td rowspan="2" class="div">카드</td>
				<td rowspan="2">B</td>
				<td class="div">체크카드</td>
				<td>BA</td>
				
			</tr>
			<tr>
				<td class="div">신용카드</td>
				<td>BB</td>
				<td rowspan="4" class="div">일반 고객 상품</td>
				<td rowspan="2">CV (일반 고객 VIP 등급)</td>
			</tr>
			
			<tr>
				<td rowspan="3" class="div">대출</td>
				<td rowspan="3">C</td>
				<td class="div">일반 대출</td>
				<td>CA</td>
			</tr>
			<tr>
				<td class="div">신용 대출</td>
				<td>CB</td>
				<td rowspan="2">CN (일반 고객 일반 등급)</td>
			</tr>
			<tr>
				<td class="div">카드론</td>
				<td>CC</td>
			</tr>
			
			
		</table>
		
		<div id="detail">
			<h3><i class="fas fa-info-circle"></i>가이드</h3>
			<p>* 상품은 통장, 카드, 대출로 이루어져 있습니다.</p>
			<p>* 통장은 예금, 적금, 마이너스로 이루어져 있습니다.</p>
			<p>* 카드는 체크카드, 신용카드로 이루어져 있습니다.</p>
			<p>* 대출은 일반, 신용, 카드론으로 이루어져 있습니다.</p>
			<p>* 상품은 기업 고객용, 일반 고객용으로 나누어 집니다.</p>
			<p>* 기업 고객과 일반 고객은 또 다시 VIP 와 일반 등급으로 각각 나누어 집니다.</p>
		</div>
		</div>
	</div>
</body>
</html>