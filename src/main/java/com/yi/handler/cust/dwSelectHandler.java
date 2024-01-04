package com.yi.handler.cust;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;
import com.yi.service.CustomerService;

public class dwSelectHandler implements CommandHandler {
	private CustomerService service = new CustomerService();
	private BankBookService bankbookService = new BankBookService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String custCode = req.getParameter("custCode");
		req.setAttribute("custCode", custCode);
		String accountNum = req.getParameter("accountNum");
		String num = accountNum.substring(7, 9);
		req.setAttribute("num", num);
		req.setAttribute("accountNum", accountNum);
		String dw = req.getParameter("dw");
		req.setAttribute("dw", dw);
		Customer customer = new Customer();
		customer.setCustCode(custCode);
		BankBook bankbook = new BankBook();
		bankbook.setAccountNum(accountNum);
		customer.setBankbook(bankbook);
		
		Long balance = bankbookService.showAccBalanceByCodeAccNum(customer);
		req.setAttribute("balance", balance);	
		
		return "/WEB-INF/view/cust/dwSelectForm.jsp";
	}

}
