package com.yi.handler.bankwork.bankbook;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;
import com.yi.service.CustomerService;

public class TerminatedDetailHandler implements CommandHandler {
	private BankBookService service = new BankBookService();
	private CustomerService custService = new CustomerService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String accountNum = req.getParameter("accountnum");
			String custName = req.getParameter("custname");
			BankBook bankbook = new BankBook();
			bankbook.setAccountNum(accountNum);
			Customer customer = new Customer();
			customer.setCustName(custName);
			bankbook.setCustCode(customer);   
			bankbook = service.showBankBookByCustNameAndAccountNum(bankbook);
			String div = bankbook.getCustCode().getCustDiv()?"1":"0";
			req.setAttribute("custdiv", div);
			req.setAttribute("bankbook", bankbook);      
			return "/WEB-INF/view/bankwork/bankbook/bankbookTerminatedDetail.jsp";
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String cmd = req.getParameter("cmd");
			switch(cmd) {
			case "del":
				String accountNum = req.getParameter("accountnum");
				String custName = req.getParameter("custName");
				BankBook bankbook = new BankBook();
				bankbook.setAccountNum(accountNum);
				Customer customer = new Customer();
				customer.setCustName(custName);
				bankbook.setCustCode(customer);
				         
				service.deleteBankBook(bankbook);
				res.setContentType("text/html; charset=UTF-8") ;  					   
				HttpSession session = req.getSession();     
				session.setAttribute("bankbookDelSuccess", "success"); 
			     
				Customer cust = custService.showCustomerByNameNoLike(custName);     
				Boolean div = cust.getCustDiv();   
				int custDiv = 1;  
				if(div==false) {  
					custDiv = 0;        
				}else {   
					custDiv = 1;      
				}
				req.setAttribute("custdiv", custDiv);       
				res.sendRedirect(req.getContextPath()+"/bankbook/bankbook/terminationList.do?custdiv="+custDiv);  
				break;     
			
			}
		}    
		return null;
	}

}
