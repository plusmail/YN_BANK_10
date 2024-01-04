<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
</head>
<style>
		* { font-family: 'Noto Sans KR', sans-serif; }
		div#search {
			width: 900px;
			margin: 50px auto;
			text-align: center;
		}
		
		div#search select {
			width: 200px; height : 30px;
			border: none;
			font-size: 16px;
			border: 1px solid goldenrod;;
		}
		
		fieldset { position: relative;  
				   top:10px; 
		 		   display: inline-block;  
		 		   padding: 0 0 0 40px;  
		 		   background: #fff;  
		 		   border: none;  
		 		   border-radius: 5px; } 
		 		   
		input, button { position: relative;  
						width: 200px;  height: 35px;  
						padding: 0;  
						display: inline-block;  
						float: left; }
		input {  color: #666;  
		 		 z-index: 2; 
		 		 border:none;  
		 		 border-bottom: 1px solid goldenrod; }
		input:focus {  outline: 0 none; } 

	
		button { z-index: 1;  
				 width: 40px;  
				 border: 0 none;  
				 background: goldenrod;  
				 cursor: pointer;  
				 border-radius: 0 5px 5px 0;
				 background-image: url("images/search.png");
				 background-size: 25px; 
				 background-repeat: no-repeat; 
				 background-position: center;}
		.fa-search { font-size: 1.4rem;  
					 color: #29abe2;  
					 z-index: 3;  
					 top: 25%;  }
		
		
		div#table {
			width: 900px;
			margin: 100px auto;
		}
		
		div#table table {
			border-collapse: collapse; 
		}
		
		div#table th, td {
			width: 200px; 
			height: 30px;
			text-align: center;
			font-size: 15px;
		}
		
		div#table tr:nth-child(odd) {
			width: 200px; 
			height: 30px;
			text-align: center;
			background: gainsboro;
			font-size: 15px;
		}
		
		div#table tr:hover td { background: goldenrod;}
		
</style>
<body>
	<jsp:include page="/WEB-INF/view/include/menu.jsp"/>
	<section>
		<div id="search">
				<select id="searchMenu">
					<option>검색구분</option>
					<option>계좌번호</option>
					<option>고객이름</option>
					<option>상품명</option>
					<option>통장상품</option>
				</select>
					<fieldset><input type="search" />
						<button type="submit">
							<i class="fa fa-search"></i>
						</button>	
					</fieldset>
				
		</div>
		<div id="table">
			<table>
				<tr>
					<th>계좌번호</th>
					<th>고객이름</th>
					<th>상품명</th>
					<th>통장구분</th>
					<th>계좌개설일</th>
					<th>이자율</th>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				<tr>
					<td>293133-11-000001</td>
					<td>김서형</td>
					<td>슈퍼정기예금</td>
					<td>예금</td>
					<td>2020-03-27 10:10:16</td>
					<td>15%</td>
				</tr>
				
		</table>
		
		</div>
		</section>
</body>
</html>