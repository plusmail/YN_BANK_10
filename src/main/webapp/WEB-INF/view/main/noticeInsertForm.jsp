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
	div#noticeWrap { width: 900px; margin: 50px auto;}
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
	<jsp:include page="../include/menu.jsp"/>
	<script>
		$("form").submit(function() {
			if($("input[name='subject']").val()==""||$("input[name='writer']").val()==""||$("textarea[name='content']".val()=="")) {
				alert("입력 폼을 입력해주세요");
				return false;
			}
		})
		$(function(){
			$("button").eq(1).click(function() {
				location.href = "${pageContext.request.contextPath}/main/main.do";
			})
		})
	</script>
	<div id="noticeWrap">
		<h1>공지사항 추가</h1>
		<div id="noticeInner">
			<form action="${pageContext.request.contextPath}/main/noticeAdd.do" method="post">
			<p>
				<span class="th">제목</span>
				<input type="text" name="subject">
			</p>
			<p>
				<span class="th">작성자</span>
				<input type="text" name="writer">
			</p>
			<p id="noticeContent">
				<span class="th">내용</span>
			</p>
			<div id="contents">
				<textarea rows="15" cols="100" name="content"></textarea>
			</div>
			<div id="noticeBtns">
				<button type="submit">추가</button>
				<button type="reset">취소</button>
			</div>
			</form>
		</div>
	</div>
</body>
</html>