package com.yi.handler.cust;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;
import com.yi.service.CustomerService;

public class custDetailHandler implements CommandHandler {
	private CustomerService custService = new CustomerService();
		
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String custName = req.getParameter("custName");
		Customer customer = custService.showCustomerByNameNoLike(custName);
	  
		req.setAttribute("customer", customer);  
		
		return "/WEB-INF/view/cust/custDetail.jsp";
	}

}
