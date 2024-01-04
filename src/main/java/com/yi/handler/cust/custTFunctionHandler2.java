package com.yi.handler.cust;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.BankBook;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;

public class custTFunctionHandler2 implements CommandHandler {
	BankBookService service = new BankBookService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String accountNum = req.getParameter("accountNum");
		String amount = req.getParameter("amount");
		String code= req.getParameter("code");
		
		//송금
		String toAccountNum = req.getParameter("findAccNum");
	    String transferAmount =req.getParameter("transferAmount");
	    String selectBank = req.getParameter("selectBank"); 
	    
		try {
			BankBook bankBook = service.showOneBankBook(accountNum);
			BankBook bankBook2 = service.showOneTransferringBankBook(toAccountNum, selectBank);
			
			service.transferring(bankBook, bankBook2, Integer.parseInt(transferAmount));
			String tCustomer = bankBook2.getCustCode().getCustName();
			req.setAttribute("otherBanks", tCustomer);
			//System.out.println(tCustomer);
			//System.out.println("완료됐지롱 ");
			HttpSession session = req.getSession();
			session.setAttribute("successedTransfer", "success");
			session.setAttribute("targetCust", bankBook2.getCustCode().getCustName());
			session.setAttribute("transferredAmount", transferAmount);
	
		
		}catch(RuntimeException e) {          
			e.printStackTrace();
		}
		
		res.sendRedirect(req.getContextPath()+"/cust/custTransfer.do");
		
		return null;
	}  
         
}    
                 