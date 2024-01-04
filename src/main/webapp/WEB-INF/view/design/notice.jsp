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
	<div id="noticeWrap">
		<h1>공지사항</h1>
		<div id="noticeInner">
			<p>
				<span class="th">제목</span>
				<span>코로나 19 로 인한 휴점</span>
			</p>
			<p>
				<span class="th">작성자</span>
				<span>관리자</span>
			</p>
			<p id="noticeContent">
				<span class="th">내용</span>
			</p>
				<div id="contents">
				<span id="contentText">신종 코로나바이러스 감염증 확산 방지를 위해 여러분의 협조부탁드립니다. <br><br>
					○ 코로나바이러스 감염증 확산으로 다중이용시설 출입이 우려되시거나 증상(기침, 발열, 호흡곤란, 구토
					등)이 있으신 분은 출입을 자제하여 주시기 바랍니다.<br><br> ○ 또한, 출입시 반드시 마스크를 착용하여 주시고 출입구와 현관에 비치된
					손소독제로 소독 하시기 바랍니다.<br> <br>○  상황 변경시 문자 등으로 개별 연락드릴 예정이며, 향후
					국가 감염병 위기경보가 현단계 '경계'에서 '심각'으로 격상될 경우 휴점 예정이오니 양지하여 주시기 바랍니다.<br><br>
					 ○  상황 변경시 문자 등으로 개별 연락드릴 예정이며, 향후
					국가 감염병 위기경보가 현단계 '경계'에서 '심각'으로 격상될 경우 휴점 예정이오니 양지하여 주시기 바랍니다.<br><br>
					 ○  상황 변경시 문자 등으로 개별 연락드릴 예정이며, 향후
					국가 감염병 위기경보가 현단계 '경계'에서 '심각'으로 격상될 경우 휴점 예정이오니 양지하여 주시기 바랍니다.<br><br>
					 ○  상황 변경시 문자 등으로 개별 연락드릴 예정이며, 향후
					국가 감염병 위기경보가 현단계 '경계'에서 '심각'으로 격상될 경우 휴점 예정이오니 양지하여 주시기 바랍니다.
					 </span>
			</div>
			
			<div id="noticeBtns">
				<button>수정</button>
				<button>취소</button>
			</div>
		</div>
	</div>
</body>
</html>