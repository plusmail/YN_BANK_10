package com.yi.dto;

import java.util.Date;

public class Notice {
	private int no;
	private String subject;
	private String writer;
	private Date writeDate;
	private String content;
	public Notice() {
		
	}
	
	public Notice(int no) {
		this.no = no;
	}
			
	public Notice(String content) {
		this.content = content;
	}

	public Notice(int no, String subject, String writer) {
		this.no = no;
		this.subject = subject;
		this.writer = writer;
	}
	
	
	public Notice(int no, String subject, String writer, String content) {
		this.no = no;
		this.subject = subject;
		this.writer = writer;
		this.content = content;
	}

	public Notice(String subject, String writer, Date writeDate, String content) {
		this.subject = subject;
		this.writer = writer;
		this.writeDate = writeDate;
		this.content = content;
	}

	public Notice(int no, String subject, String writer, Date writeDate, String content) {
		this.no = no;
		this.subject = subject;
		this.writer = writer;
		this.writeDate = writeDate;
		this.content = content;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}
	
	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public Date getWriteDate() {
		return writeDate;
	}
	public void setWriteDate(Date writeDate) {
		this.writeDate = writeDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	@Override
	public String toString() {
		return String.format("Notice [no=%s, title=%s, name=%s, writeDate=%s, content=%s]", no, subject, writer, writeDate,
				content);
	}
	
}
