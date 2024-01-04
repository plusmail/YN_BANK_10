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
	* { margin:0; padding:0; 
		font-family: 'Noto Sans KR', sans-serif;
		color: #252525; }
	#container { width: 1000px; margin: 0 auto;}
	div#header { background: goldenrod;
			     height: 150px; }
	div#header h1 { padding: 30px;  }
	div#profile { background: whitesmoke;
				  height: 600px;
				  border-radius: 10px;
				  padding: 50px;}
	div#profile h2 { height: 50px; }				  
	div#profileMain { float: left; }
	div#profileMain div#pic { width: 250px; height: 350px;}
	div#profileMain div#pic span { display: block;
							       width: 100px; height: 100px;
							       float: left; 
							       text-align: center;}
	div#profileMain div#pic #proName { width: 100x; font-size: 30px; 
									   font-weight: bold;
							    	   line-height: 100px;  }
	div#profileMain div#pic #proDept { line-height: 110px;
									   font-size: 20px;
									   text-align: left; }
	div#profile div#pic img { width: 200px; height: 250px; display: block;}
	
	div#profileEdit { width:600px; 
					  overflow: hidden;
					  margin-left: 400px;  }
	div#profileEdit table { width: 500px; }
	div#profileEdit table tr { height: 30px; }
	div#profileEdit table th { width: 100px; text-align: left; }
	div#profileEdit table td { width: 200px; text-align: center;}
	div#profileEdit table td input { width: 250px;
									 background: whitesmoke;
								     border: none; 
								     padding: 10px; 
								     border-bottom: 1px solid gray;}
	table td#noline input[name='file'] { border: none;}									     
	div#profileEdit table td select { width: 250px; margin: 20px 0;}		
	div#submit { text-align: center; 
				 height: 100px; 
				 line-height: 130px; }
	div#submit input { width: 100px;  height: 40px; 
					   border: none;
					   background: gray; 
					   margin-left:20px; 
					   font-size: 15px;
					   color: whitesmoke;}						    
</style>
<body>
	<div id="container">
		<div id="header">
			<h1>사용자 프로필</h1>
		</div>
		<form>
			
			<div id="profile">
				<h2>프로필</h2>
				<div id="profileMain">
					<div id="pic">
						<img alt="" src="images/staff1.jpg">
						<span id="proName">이주빈</span>
						<span id="proDept">인사팀</span>
						
					</div>
					<div id="nameInfo">
						
					</div>
				</div>
				<div id="profileEdit">
					<table>
						<tr>
							<th>사원 코드</th>
							<td><input type="text" name="code"></td>
						</tr>
						<tr>
							<th>이름</th>
							<td><input type="text" name="name"></td>
						</tr>
						<tr>
							<th>직책</th>
							<td><input type="text" name="name"></td>
						</tr>
						<tr>
							<th>권한</th>
							<td><input type="text" name="auth"></td>
						</tr>
						<tr>
							<th>월급</th>
							<td><input type="text" name="money"></td>
						</tr>
						<tr>
							<th>연락처</th>
							<td><input type="text" name="contact"></td>
						</tr>
						<tr>
							<th>아이디</th>
							<td><input type="text" name="id"></td>
						</tr>
						<tr>
							<th>비밀번호</th>
							<td><input type="text" name="pw"></td>
						</tr>
						<tr>
							<th>부서</th>
							<td>
								<select name="dept">
									<option>인사(1)</option>
									<option>고객(2)</option>
								</select>
							</td>
						</tr>
						<tr>
							<th>이미지 선택</th>
							<td id="noline"><input type="file" name="file"></td>
						</tr>
						
					</table>
				</div>
				
				<div id="submit">
					<input type="submit" value="등록">
					<input type="reset" value="취소">
				</div>
				
			</div>
		</form>
	</div>
</body>
</html>