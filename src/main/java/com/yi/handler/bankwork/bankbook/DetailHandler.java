package com.yi.handler.bankwork.bankbook;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.dto.Plan;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;

public class DetailHandler implements CommandHandler {
	private BankBookService service = new BankBookService();
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
			return "/WEB-INF/view/bankwork/bankbook/bankbookDetail.jsp";
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String cmd = req.getParameter("cmd");
			String div = req.getParameter("div");
			switch(cmd) {
			case "mod":
				String accountNum = req.getParameter("accountnum");
				String custName = req.getParameter("custname");
				Customer custCode = new Customer();
				custCode.setCustName(custName);
				String planName = req.getParameter("planname");
				Plan accountPlanCode = new Plan();
				accountPlanCode.setPlanName(planName);
				String dateArr = req.getParameter("accountOpenDate");
				Date accountOpenDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateArr);
				String interestStr = req.getParameter("accountInterest");
				interestStr = interestStr.replaceAll("[\\%]", "");
				float accountInterest = (Float.parseFloat(interestStr)/100);
				BankBook bankbook = new BankBook(accountNum, custCode, accountPlanCode, accountOpenDate, accountInterest);
				service.updateBankBook(bankbook);
				HttpSession session = req.getSession();
				session.setAttribute("successmod", "success");
				res.sendRedirect(req.getContextPath() + "/bankwork/bankbook/mgn.do?div="+div);
				break;
			case "del":
				accountNum = req.getParameter("accountnum");
				custName = req.getParameter("custname");
				custCode = new Customer();
				custCode.setCustName(custName);
				planName = req.getParameter("planname");
				accountPlanCode = new Plan();
				accountPlanCode.setPlanName(planName);
				dateArr = req.getParameter("accountOpenDate");
				accountOpenDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateArr);
				interestStr = req.getParameter("accountInterest");
				interestStr = interestStr.replaceAll("[\\%]", "");
				accountInterest = (Float.parseFloat(interestStr)/100);
				bankbook = new BankBook(accountNum, custCode, accountPlanCode, accountOpenDate, accountInterest);
				bankbook.setAccountTermination(true);
				service.changeBankBookTermination(bankbook);
				session = req.getSession();
				session.setAttribute("successdel", "success");
				res.sendRedirect(req.getContextPath() + "/bankwork/bankbook/mgn.do?div="+div);
				break;
			case "change":
				accountNum = req.getParameter("accountnum");
				System.out.println(accountNum);
				custName = req.getParameter("custname");
				custCode = new Customer();
				custCode.setCustName(custName);
				planName = req.getParameter("planname");
				accountPlanCode = new Plan();
				accountPlanCode.setPlanName(planName);
				dateArr = req.getParameter("accountOpenDate");
				accountOpenDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateArr);
				interestStr = req.getParameter("accountInterest");
				interestStr = interestStr.replaceAll("[\\%]", "");
				accountInterest = (Float.parseFloat(interestStr)/100);
				bankbook = new BankBook(accountNum, custCode, accountPlanCode, accountOpenDate, accountInterest);
				bankbook.setAccountDormant(true);
				service.changeBankBookDormant(bankbook);
				session = req.getSession();
				session.setAttribute("successchange", "success");
				res.sendRedirect(req.getContextPath() + "/bankwork/bankbook/mgn.do?div="+div);
				break;
			}
		}
		return null;
	}

}
