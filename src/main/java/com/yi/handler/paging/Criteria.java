package com.yi.handler.paging;

public class Criteria {
	// 시작번호
	private int page; // 현재 페이지 번호
	private int perPageNum; // 한 페이지 display될 게시글의 개수

	public Criteria() {
		this.page = 1;
		this.perPageNum = 10; // 디폴트 값 설정
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPerPageNum() {
		return perPageNum;
	}

	public void setPerPageNum(int perPageNum) {
		this.perPageNum = perPageNum;
	}

	@Override
	public String toString() {
		return "Criteria [page=" + page + ", perPageNum=" + perPageNum + "]";
	}

	// 디비 인덱스 번호 구하는 함수
	public int getPageStart() {
		return (this.page - 1) * this.perPageNum;
	}

}
