package com.yi.handler.bankwork.bankbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dao.BankBookDao;
import com.yi.dao.CustomerDao;
import com.yi.dao.PlanDao;
import com.yi.dao.impl.BankBookDaoImpl;
import com.yi.dao.impl.CustomerDaoImpl;
import com.yi.dao.impl.PlanDaoImpl;
import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.dto.Employee;
import com.yi.dto.Plan;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;
import com.yi.service.CustomerService;
import com.yi.service.PlanService;

public class AddHandler implements CommandHandler {
	private CustomerService custService = new CustomerService();
	private BankBookService bankbookService = new BankBookService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String div = req.getParameter("div");
			List<BankBook> list = bankbookService.showBankBooks();
			int number = list.size() + 1;
			if(div.equals("0")) {
				List<Customer> custList = custService.showCustomerByNormal();
				List<Plan> planList = bankbookService.selectPlanByBankBookCustomerVip();
				List<Plan> planListNormal = bankbookService.selectPlanByBankBookCustomerNormal();
				req.setAttribute("custList", custList);
				req.setAttribute("planList", planList);
				req.setAttribute("planListNormal", planListNormal);
				req.setAttribute("number", number);
				req.setAttribute("custdiv", div);
				return "/WEB-INF/view/bankwork/bankbook/bankbookInsertForm.jsp";
			}
			else {
				List<Customer> custList = custService.showCustomerByBusiness();
				List<Plan> planList = bankbookService.selectPlanByBankBookBusinessVip();
				List<Plan> planListNormal = bankbookService.selectPlanByBankBookBusinessNormal();
				req.setAttribute("custList", custList);
				req.setAttribute("planList", planList);
				req.setAttribute("planListNormal", planListNormal);
				req.setAttribute("number", number);
				req.setAttribute("custdiv", div);
				return "/WEB-INF/view/bankwork/bankbook/bankbookInsertForm.jsp";
			}
			
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String custdiv = req.getParameter("custdiv");
			String accountNum = req.getParameter("accountnum");
			String custName = req.getParameter("custname");
			Customer custCode = new Customer();
			custCode.setCustName(custName);
			String planName = req.getParameter("planname");
			Plan accountPlanCode = new Plan();
			accountPlanCode.setPlanName(planName);
			String dateStr = req.getParameter("accountOpenDate");
			Date accountOpenDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			String interestStr = req.getParameter("accountInterest");
			interestStr = interestStr.replaceAll("[\\%]", "");
			float accountInterest = (Float.parseFloat(interestStr) / 100);
			BankBook bankbook = new BankBook(accountNum, custCode, accountPlanCode, accountOpenDate, accountInterest);
			long accountBalance = Long.parseLong(req.getParameter("accountBalance")==""?"0":req.getParameter("accountBalance"));
			long contribution = Long.parseLong(req.getParameter("contribution"));
			if(contribution - accountBalance < 0) {
				HttpSession session = req.getSession();
				session.setAttribute("Insufficient", "Insufficient");
				res.sendRedirect(req.getContextPath() + "/bankwork/bankbook/add.do?div="+custdiv);
			}
			else {
				String empName = req.getParameter("empname");
				Employee emp = new Employee(empName);
				bankbook.setEmployee(emp);
				bankbook.setAccountBalance(accountBalance);
				BankBook chkRedendency = bankbookService.checkRedunduncyBankBookPlan(bankbook);
				if(chkRedendency==null) {
					bankbookService.insertBankBook(bankbook);
					HttpSession session = req.getSession();
					session.setAttribute("successadd", "success");
					res.sendRedirect(req.getContextPath()+"/bankwork/bankbook/mgn.do?div="+custdiv);
				}
				else {
					HttpSession session = req.getSession();
					session.setAttribute("duplicate", "error");
					res.sendRedirect(req.getContextPath() + "/bankwork/bankbook/add.do?div="+custdiv);
				}
				
			}
			
		}
		return null;
	}

}
