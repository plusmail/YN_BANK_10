package com.yi.handler.cust;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custTSelectHandler implements CommandHandler {
	private CustomerService service = new CustomerService();
	
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
		
			
		return "/WEB-INF/view/cust/custTSelectForm.jsp";
	}

}
