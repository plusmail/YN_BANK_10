package com.yi.handler.cust;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custSelectHandler implements CommandHandler {
	private CustomerService service = new CustomerService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			return "/WEB-INF/view/cust/custSelectForm.jsp";
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			
		}
		
		return null;
	}

}
