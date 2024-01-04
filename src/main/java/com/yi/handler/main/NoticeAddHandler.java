package com.yi.handler.main;

import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.Notice;
import com.yi.mvc.CommandHandler;
import com.yi.service.NoticeService;

public class NoticeAddHandler implements CommandHandler {
	private NoticeService service = new NoticeService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			return "/WEB-INF/view/main/noticeInsertForm.jsp";
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String subject = req.getParameter("subject");
			String writer = req.getParameter("writer");
			String content = req.getParameter("content");
			Date writeDate = new Date();
			service.addNotice(new Notice(subject, writer, writeDate, content));
			HttpSession session = req.getSession();
			session.setAttribute("successadd", "success");
			res.sendRedirect(req.getContextPath()+"/main/main.do");
		}
		return null;
	}

}
