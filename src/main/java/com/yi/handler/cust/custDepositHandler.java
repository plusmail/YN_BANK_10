package com.yi.handler.cust;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custDepositHandler implements CommandHandler {
	private CustomerService service = new CustomerService();
	
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String custCode = req.getParameter("custCode");
			String dw = req.getParameter("dw");
			String accountNum = req.getParameter("accountNum");
			List<Customer> listBal = service.showCustomerBankInfoByAcc(accountNum);
			Customer custBal = listBal.get(0);
			//System.out.println(custBal.getBankbook().getAccountBalance());
			req.setAttribute("accountNum", accountNum);
			req.setAttribute("custBal", custBal);
			req.setAttribute("dw", dw);
			
			return "/WEB-INF/view/cust/custDepositForm.jsp";  
		}         
		return null;     
	}        
  
}
