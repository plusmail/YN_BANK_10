package com.yi.handler.cust;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custUpdateHandler implements CommandHandler {
	private CustomerService service = new CustomerService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		//고객 = 0 , false
		if(req.getMethod().equalsIgnoreCase("post")) {
			String custCode = req.getParameter("custCode");
			
			String custName = req.getParameter("custName");
			int custCredit = Integer.parseInt(req.getParameter("custCredit"));
			String addr = req.getParameter("addr");
			String custTel = req.getParameter("custTel");
			String div = req.getParameter("custDiv");
 			Boolean custDiv = true;
 			if(div.equals("일반")) {
 				custDiv = false;
 			}
 			
			try {
				Customer customer = new Customer(custCode, custName, custCredit, addr, custTel, custDiv);
				service.editCustomer(customer);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
			res.sendRedirect(req.getContextPath()+"/cust/custSearch.do");
			
		}
		return null;
		
	}

}
