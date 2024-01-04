<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap" rel="stylesheet">
<style>
	* { margin:0; padding: 0; font-family: 'Noto Sans KR', sans-serif;}
	div#noticeWrap { width: 900px; margin: 0 auto;}
	div#noticeWrap h1 { background: goldenrod; height: 100px; padding: 30px; }
	div#noticeInner {  height: 700px; background: whitesmoke; }
	div#noticeInner p { height:10px; padding: 30px;  }
	div#noticeInner p#noticeContent { height: 50px;}
	div#noticeInner div#contents { height: 300px; text-align: center;
								  }
	div#noticeInner .th { width:150px; font-weight: bold;}
	div#noticeInner p span { display: block; width: 200px;  float: left; }
	div#noticeInner input { width: 300px;height: 40px;
							border: none; 
					        background: whitesmoke;
					        border-bottom: 2px solid goldenrod;}
	div#noticeInner div#noticeBtns { text-align: center;}
	div#noticeInner div#noticeBtns button { background: gray;
											border: none;
									        width: 100px;
									        height: 40px;
									        margin: 50px 20px 20px 20px;
									        font-size: 15px;
									        color: whitesmoke; }
</style>
<body>
	<div id="noticeWrap">
		<h1>공지사항 추가</h1>
		<div id="noticeInner">
			<p>
				<span class="th">제목</span>
				<input type="text" name="title">
			</p>
			<p>
				<span class="th">작성자</span>
				<input type="text" name="writer">
			</p>
			<p id="noticeContent">
				<span class="th">내용</span>
			</p>
			<div id="contents">
				<textarea rows="15" cols="100"></textarea>
			</div>
			
			<div id="noticeBtns">
				<button>수정</button>
				<button>취소</button>
			</div>
		</div>
	</div>
</body>
</html>