package com.yi.handler.main;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.Notice;
import com.yi.mvc.CommandHandler;
import com.yi.service.NoticeService;

public class NoticeDelHandler implements CommandHandler {
	private NoticeService service = new NoticeService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int no = Integer.parseInt(req.getParameter("no"));
		service.removeNotice(new Notice(no));
		HttpSession session = req.getSession();
		session.setAttribute("successdel", "success");
		res.sendRedirect(req.getContextPath()+"/main/main.do");
		return null;
	}
	
}
