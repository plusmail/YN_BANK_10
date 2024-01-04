package com.yi.dao;

import java.sql.SQLException;
import java.util.List;

import com.yi.dto.Notice;

public interface NoticeDao {
	public List<Notice> selectNoticeByAll() throws SQLException;
	public Notice selectNoticeByNo(Notice notice) throws SQLException;
	public int insertNotice(Notice notice) throws SQLException;
	public int updateNotice(Notice notice) throws SQLException;
	public int deleteNotice(Notice notice) throws SQLException;
}
