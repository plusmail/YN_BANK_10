package com.yi.handler.bankwork.bankbook;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;

public class StatisticHandler implements CommandHandler {
	private BankBookService service = new BankBookService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		long[] totalAmount = service.TotalPlanTransAmountYearly(); //2020년 4월까지만 데이터가 들어가 있기 때문에 4월달만 계산함
		req.setAttribute("totalAmount", totalAmount);
		return "/WEB-INF/view/bankwork/bankbook/bankbookStatistic.jsp";
	}

}
