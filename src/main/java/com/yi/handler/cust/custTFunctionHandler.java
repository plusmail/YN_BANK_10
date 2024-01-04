package com.yi.handler.cust;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;

public class custTFunctionHandler implements CommandHandler {
	BankBookService service = new BankBookService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String accountNum = req.getParameter("accountNum");
		String amount = req.getParameter("amount");
		String code= req.getParameter("code");
		
		//송금
		String toAccountNum = req.getParameter("findAccNum");
	    String transferAmount =req.getParameter("transferAmount");
		try {
			BankBook bankBook = service.showOneBankBook(accountNum);
			BankBook bankBook2 = service.showOneBankBook(toAccountNum);
			
			service.changeBankBookBalance(bankBook, bankBook2, Integer.parseInt(transferAmount));
			
			//받는 사람   
			Customer toCust = new Customer();
			BankBook toBankBook = new BankBook();
			toBankBook.setAccountNum(toAccountNum);  
			toCust.setBankbook(toBankBook);
			String toCustCode = service.showCodeByAccNum(toAccountNum);
			toCust.setCustCode(toCustCode);
			
			HttpSession session = req.getSession();
			session.setAttribute("successedTransfer", "success");
			session.setAttribute("targetCust", bankBook2.getCustCode().getCustName());
			session.setAttribute("transferredAmount", transferAmount);
			
			//보내는 사람
			Customer fromCust = new Customer();
			BankBook fromBankBook = new BankBook();
			fromBankBook.setAccountNum(accountNum);
			fromCust.setBankbook(fromBankBook);
			fromCust.setCustCode(code);
			
			
			//받는 사람 잔액
			Long toBalance = service.showAccBalanceByCodeAccNum(toCust);
			toCust.getBankbook().setAccountBalance(toBalance);
			
			
			//보내는 사람 잔액
			Long fromBalance = service.showAccBalanceByCodeAccNum(fromCust);
			fromCust.getBankbook().setAccountBalance(fromBalance);
			
			
			//받는 사람 카드 잔액 세팅
			service.updateCardBalance(toCust);
			
			
			//보내는 사람 카드 잔액 세팅
			service.updateCardBalance(fromCust);
		
		}catch(RuntimeException e) {          
			e.printStackTrace();
		}
		
		res.sendRedirect(req.getContextPath()+"/cust/custTransfer.do");
		return null;
	}  
         
}    
                 