<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
	div#noticeInner div#contents { height: 300px; 
								   padding: 0 60px; 
								   overflow: scroll;
								  }
	div#noticeInner .th { font-weight: bold;}
	div#noticeInner p span { display: block; width: 200px;  float: left; }
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
    	$(function(){
    		if(${Auth.empAuth!='AD'}) {
    			$("button").click(function() {
    				location.href = "${pageContext.request.contextPath}/main/main.do";
    			})
    		}
    		else {
    			$("button").eq(0).click(function() {
    				location.href = "${pageContext.request.contextPath}/main/noticeMod.do?no=${notice.no}";
    			})
    			$("button").eq(1).click(function() {
    				var res = confirm("삭제하시겠습니까?");
    				if(res) {
    					location.href = "${pageContext.request.contextPath}/main/noticeDel.do?no=${notice.no}";
    				}
    				else {
    					alert("삭제를 취소하였습니다");
    				}
    			})
    			$("button").eq(2).click(function() {
    				location.href = "${pageContext.request.contextPath}/main/main.do";
    			})
    		}
    	})
    </script>
	<div id="noticeWrap">
		<h1>공지사항</h1>
		<div id="noticeInner">
			<p>
				<span class="th">제목</span>
				<span>${notice.subject}</span>
			</p>
			<p>
				<span class="th">작성자</span>
				<span>${notice.writer}</span>
			</p>
			<p id="noticeContent">
				<span class="th">내용</span>
			</p>
				<div id="contents">
				<span id="contentText">${notice.content}</span>
			</div>
			
			<div id="noticeBtns">
				<c:if test="${Auth.empAuth=='AD'}">
					<button>수정</button>
					<button>삭제</button>
					<button>취소</button>
				</c:if>
				<c:if test="${Auth.empAuth!='AD'}">
					<button>돌아가기</button>
				</c:if>
			</div>
		</div>
	</div>
</body>
</html>