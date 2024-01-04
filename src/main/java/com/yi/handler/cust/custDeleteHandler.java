package com.yi.handler.cust;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;
import com.yi.service.CustomerService;

public class custDeleteHandler implements CommandHandler {
	private CustomerService service = new CustomerService();
	private BankBookService bankBookService = new BankBookService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
			try {
				String custCode = req.getParameter("custCode");
				Customer customer = new Customer();
				customer.setCustCode(custCode);
				List<Integer> accCheckList = bankBookService.showTerminationByCustCode(custCode);
				List<Integer> loanCheckList = bankBookService.showLoanDoneCheckByCustCode(custCode);
				
				//모든 대출을 상환 했는지 체크
				if(loanCheckList!=null) {
					for(int i=0; i<loanCheckList.size(); i++) {
						if(loanCheckList.get(i)==0) {    
							List<Customer> list = service.showCustomerByCode(custCode);
							customer = list.get(0);
							req.setAttribute("customer", customer);
							res.setContentType("text/html; charset=UTF-8") ;  					
							HttpSession session = req.getSession();
							session.setAttribute("loanExists", "error");
							return "/WEB-INF/view/cust/custDetail.jsp"; 
						}
					}   	
				}
				//해지 계좌 리스트에 추가된 계좌가 있는지 확인
				if(accCheckList!=null) {
					for(int j=0; j<accCheckList.size(); j++) {
						if(accCheckList.get(j)==1) {    
							List<Customer> list = service.showCustomerByCode(custCode);
							customer = list.get(0);
							req.setAttribute("customer", customer);
							res.setContentType("text/html; charset=UTF-8") ;  					
							HttpSession session = req.getSession();     
							session.setAttribute("delExists", "error");          
							return "/WEB-INF/view/cust/custDetail.jsp"; 
						}else {
							List<Customer> list = service.showCustomerByCode(custCode);
							customer = list.get(0);
							req.setAttribute("customer", customer);
							res.setContentType("text/html; charset=UTF-8") ;  					
							HttpSession session = req.getSession();     
							session.setAttribute("accExists", "error");          
							return "/WEB-INF/view/cust/custDetail.jsp";
						}
					} 	
				}
				service.setForeignKeyCheckFalse();
				service.removeCustomer(customer); 
				service.setForeignKeyCheckTrue();
				res.setContentType("text/html; charset=UTF-8") ;  					
				HttpSession session = req.getSession();     
				session.setAttribute("delSuccess", "success"); 
				res.sendRedirect(req.getContextPath()+"/cust/custSearch.do");   
			}catch(Exception e) {  
				e.printStackTrace();       
			}   
		     
		return null;     
	}          

}
   