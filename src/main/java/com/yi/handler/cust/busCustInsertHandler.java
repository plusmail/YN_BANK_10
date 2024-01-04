package com.yi.handler.cust;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class busCustInsertHandler implements CommandHandler {
	private CustomerService service = new CustomerService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			List<Customer> list = service.showBusinessCustomer();
			String nextCustNum = "B"+String.format("%03d", list.size()+1);
			req.setAttribute("nextCustNum", nextCustNum);
			
			return "/WEB-INF/view/cust/newBusCustForm.jsp";
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			List<Customer> list = service.showBusinessCustomer();
			String nextCustNum = "B"+String.format("%03d", list.size()+1);
			String code = nextCustNum;
			String name = req.getParameter("name");
			String credit = req.getParameter("credit");
			String addr = req.getParameter("addr");
			String contact1 = req.getParameter("contact1");
			String contact2= req.getParameter("contact2");
			String contact3 = req.getParameter("contact3");
			String contact = contact1+"-"+contact2+"-"+contact3;
			Boolean custDiv = true;
			
			try {	
				Customer customer = new Customer(code, name, Integer.parseInt(credit), addr, contact, custDiv);
				service.AddCustomer(customer);
				res.sendRedirect(req.getContextPath()+"/cust/custSearch.do");
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		
		return null;
	}

}
