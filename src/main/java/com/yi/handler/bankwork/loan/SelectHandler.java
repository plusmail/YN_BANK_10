package com.yi.handler.bankwork.loan;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.mvc.CommandHandler;

public class SelectHandler implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String div = req.getParameter("div");
		if(div.equals("add")) {
			return "/WEB-INF/view/bankwork/loan/loanAddCustSelect.jsp";
		}
		else {
			return "/WEB-INF/view/bankwork/loan/loanListCustSelect.jsp";
		}
	}

}
