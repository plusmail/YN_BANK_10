package com.yi.handler.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Notice;
import com.yi.mvc.CommandHandler;
import com.yi.service.NoticeService;

public class NoticeDetailHandler implements CommandHandler {
	private NoticeService service = new NoticeService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int no = Integer.parseInt(req.getParameter("no"));
		Notice notice = service.showNoticeByNo(new Notice(no));
		req.setAttribute("notice", notice);
		return "/WEB-INF/view/main/noticeDetail.jsp";
	}

}
