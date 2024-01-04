package com.yi.handler.bankwork.card;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.dto.Employee;
import com.yi.dto.Plan;
import com.yi.mvc.CommandHandler;
import com.yi.service.CardService;
import com.yi.service.CustomerService;

public class AddHandler implements CommandHandler {
	private CustomerService custService = new CustomerService();
	private CardService cardService = new CardService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			List<Card> list = cardService.showCards();
			String custname = req.getParameter("custname");
			if(custname!=null) {
				String cardnum = req.getParameter("cardnum");
				String planname = req.getParameter("planname");
				String cardSecuCode = req.getParameter("cardSecuCode");
				String dateStr = req.getParameter("cardIssueDate");
				String empname = req.getParameter("empname");
				String accountnum = req.getParameter("accountnum");
				Customer custCode = new Customer();
				custCode.setCustName(custname);
				Plan planCode = new Plan();
				planCode.setPlanName(planname);
				Date cardIssueDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
				Employee employee = new Employee();
				employee.setEmpName(empname);
				BankBook bankbook = new BankBook();
				bankbook.setAccountNum(accountnum);
				String div = req.getParameter("custdiv");
				Card card = new Card(cardnum, custCode, planCode, cardSecuCode, cardIssueDate, 0, 0, employee, bankbook);
				cardService.insertCheckCardProcedure(card);
				HttpSession session = req.getSession();
				session.setAttribute("successadd", "success");
				res.sendRedirect(req.getContextPath()+"/bankwork/card/mgn.do?div="+ div);
				return null;
			}
			String div = req.getParameter("div");
			if(div.equals("0")) {
				List<Customer> custList = custService.showCustomerByNormal();
				List<Plan> planList = cardService.selectPlanByCardCustomerVip();
				List<Plan> planListNormal = cardService.selectPlanByCardCustomerNormal();
				req.setAttribute("custList", custList);
				req.setAttribute("planList", planList);
				req.setAttribute("planListNormal", planListNormal);
				req.setAttribute("number", list.size());
				req.setAttribute("custdiv", div);
				return "/WEB-INF/view/bankwork/card/cardInsertForm.jsp";
			}
			else {
				List<Customer> custList = custService.showCustomerByBusiness();
				List<Plan> planList = cardService.selectPlanByCardBusinessVip();
				List<Plan> planListNormal = cardService.selectPlanByCardBusinessNormal();
				req.setAttribute("custList", custList);
				req.setAttribute("planList", planList);
				req.setAttribute("planListNormal", planListNormal);
				req.setAttribute("number", list.size());
				req.setAttribute("custdiv", div);
				return "/WEB-INF/view/bankwork/card/cardInsertForm.jsp";
			}
			
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String cardNum = req.getParameter("cardnum");
			Customer custCode = new Customer();
			String custName = req.getParameter("custname");
			custCode.setCustName(custName);
			String planName = req.getParameter("planname");
			Plan planCode = new Plan();
			planCode.setPlanName(planName);
			String cardSecuCode = req.getParameter("cardSecuCode");
			String dateStr = req.getParameter("cardIssueDate");
			Date cardIssueDate = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse(dateStr);
			int cardLimit = Integer.parseInt(req.getParameter("cardLimit")==""?"0":req.getParameter("cardLimit"));
			long cardBalance = 0;
			String empName = req.getParameter("empname");
			Employee employee = new Employee();
			employee.setEmpName(empName);
			Card card = new Card(cardNum, custCode, planCode, cardSecuCode, cardIssueDate, cardLimit, cardBalance, employee, null);
			Card chkRedunduncy = cardService.checkRedunduncyCardPlan(card);
			if(chkRedunduncy==null) {
				cardService.insertCardCredit(card);
				HttpSession session = req.getSession();
				session.setAttribute("successadd", "success");
				String div = req.getParameter("custdiv");
				res.sendRedirect(req.getContextPath()+"/bankwork/card/mgn.do?div="+ div);
			}
			else {
				HttpSession session = req.getSession();
				session.setAttribute("duplicate", "error");
				String div = req.getParameter("custdiv");
				res.sendRedirect(req.getContextPath()+"/bankwork/card/add.do?div="+ div);
			}
			
		}
		return null;
	}

}
