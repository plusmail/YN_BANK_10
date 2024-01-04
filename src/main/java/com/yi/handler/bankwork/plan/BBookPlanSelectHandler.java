package com.yi.handler.bankwork.plan;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.mvc.CommandHandler;

public class BBookPlanSelectHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		return "/WEB-INF/view/bankwork/plan/bBookplanSelectForm.jsp";
	}

}
   