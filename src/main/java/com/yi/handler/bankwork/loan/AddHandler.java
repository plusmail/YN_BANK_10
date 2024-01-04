package com.yi.handler.bankwork.loan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.dto.Employee;
import com.yi.dto.Loan;
import com.yi.dto.Plan;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;
import com.yi.service.LoanService;
import com.yi.service.LoginService;

public class AddHandler implements CommandHandler {
	private CustomerService custService = new CustomerService();
	private LoanService loanService = new LoanService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			List<Loan> list = loanService.showLoans();
			String div = req.getParameter("div");
			if(div.equals("0")) {
				List<Customer> custList = custService.showCustomerByNormal();
				List<Plan> planList = loanService.selectPlanByLoanCustomerVip();
				List<Plan> planListNormal = loanService.selectPlanByLoanCustomerNormal();
				req.setAttribute("custList", custList);
				req.setAttribute("planList", planList);
				req.setAttribute("planListNormal", planListNormal);
				req.setAttribute("number", list.size());
				req.setAttribute("custdiv", div);
				return "/WEB-INF/view/bankwork/loan/loanInsertForm.jsp";
			}
			else {
				List<Customer> custList = custService.showCustomerByBusiness();
				List<Plan> planList = loanService.selectPlanByLoanBusinessVip();
				List<Plan> planListNormal = loanService.selectPlanByLoanBusinessNormal();
				req.setAttribute("custList", custList);
				req.setAttribute("planList", planList);
				req.setAttribute("planListNormal", planListNormal);
				req.setAttribute("number", list.size());
				req.setAttribute("custdiv", div);
				return "/WEB-INF/view/bankwork/loan/loanInsertForm.jsp";
			}
			
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String div = req.getParameter("div");
			String loanAccountNum = req.getParameter("accountnum");
			String custName = req.getParameter("custname");
			Customer custCode = new Customer();
			custCode.setCustName(custName);
			String planName = req.getParameter("planname");
			Plan planCode = new Plan();
			planCode.setPlanName(planName);
			String dateStr = req.getParameter("loanStartDate");
			Date loanStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
			Calendar cal = GregorianCalendar.getInstance();
			cal.setTime(loanStartDate);
			int year = cal.get(cal.YEAR);
			int month = cal.get(cal.MONTH);
			int date = cal.get(cal.DATE);
			String loanDelayTerm = req.getParameter("loanDelayTerm")==null?"0":req.getParameter("loanDelayTerm").replaceAll("년", "");
			String loanExpireTerm = req.getParameter("loanExpireTerm").replaceAll("년", "");
			int loanDelayYear = year + Integer.parseInt(loanDelayTerm);
			cal.set(loanDelayYear,month,date);
			Date loanDelayDate = cal.getTime();
			int loanExpireYear = loanDelayYear + Integer.parseInt(loanExpireTerm);
			cal.set(loanExpireYear, month, date);
			Date loanExpireDate = cal.getTime();
			String loanMethod = req.getParameter("loanMethod").equals("만기일시상환")?"A":"B";
			String interestStr = req.getParameter("loanInterest");
			interestStr = interestStr.replaceAll("[\\%]", "");
			float loanInterest = Float.parseFloat(interestStr) / 100;
			String balanceStr = req.getParameter("loanBalance");
			balanceStr = balanceStr.replaceAll("[\\,]", "");
			long loanBalance = Long.parseLong(balanceStr);
			long contribution = Long.parseLong(req.getParameter("contribution"));
			if(contribution-loanBalance<0) {
				HttpSession session = req.getSession();
				session.setAttribute("Insufficient", "Insufficient");
				res.sendRedirect(req.getContextPath() + "/main/main.do");
			}
			else {
				String empName = req.getParameter("empname");
				Employee employee = new Employee();
				employee.setEmpName(empName);
				Loan loan = new Loan(loanAccountNum, custCode, planCode, loanStartDate, loanDelayDate, loanExpireDate, loanInterest, loanBalance, loanMethod, employee);
				Loan chkRedunduncy = loanService.checkRedunduncyLoanPlan(loan);
				if(chkRedunduncy==null) {
					loanService.insertLoan(loan);
					HttpSession session = req.getSession();
					session.setAttribute("successadd", "success");
					res.sendRedirect(req.getContextPath() + "/bankwork/loan/mgn.do?div="+div);
				}
				else {
					HttpSession session = req.getSession();
					session.setAttribute("duplicate", "error");
					res.sendRedirect(req.getContextPath() + "/bankwork/loan/add.do?div="+div);
				}
				
			}
		}
		return null;
	}

}
