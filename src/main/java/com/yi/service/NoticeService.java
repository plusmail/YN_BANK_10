package com.yi.service;

import java.sql.SQLException;
import java.util.List;

import com.yi.dao.NoticeDao;
import com.yi.dao.impl.NoticeDaoImpl;
import com.yi.dto.Notice;

public class NoticeService {
	private NoticeDao dao;

	public NoticeService() {
		dao = NoticeDaoImpl.getInstance();
	}
	public List<Notice> showNoticeByAll() throws SQLException {
		return dao.selectNoticeByAll();
	}
	public Notice showNoticeByNo(Notice notice) throws SQLException {
		return dao.selectNoticeByNo(notice);
	}
	public int addNotice(Notice notice) throws SQLException {
		return dao.insertNotice(notice);
	}
	public int modifyNotice(Notice notice) throws SQLException {
		return dao.updateNotice(notice);
	}
	public int removeNotice(Notice notice) throws SQLException {
		return dao.deleteNotice(notice);
	}
}
