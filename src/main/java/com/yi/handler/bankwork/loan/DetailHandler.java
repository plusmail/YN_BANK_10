package com.yi.handler.bankwork.loan;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Contribution;
import com.yi.dto.Customer;
import com.yi.dto.Loan;
import com.yi.dto.Plan;
import com.yi.dto.Repayment;
import com.yi.mvc.CommandHandler;
import com.yi.service.LoanService;
import com.yi.service.LoginService;

public class DetailHandler implements CommandHandler {
	private LoanService loanService = new LoanService();
	private LoginService loginService = new LoginService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String loanAccountNum = req.getParameter("loanaccountnum");
			String custName = req.getParameter("custname");
			Loan loan = new Loan();
			loan.setLoanAccountNum(loanAccountNum);
			Customer customer = new Customer();
			customer.setCustName(custName);
			loan.setCustCode(customer);
			loan = loanService.showLoanByLoanAccountNumAndCustName(loan);
			List<Repayment> list = loanService.searchRepaymentsByAccountNum(loan.getLoanAccountNum());
			Calendar calStart = GregorianCalendar.getInstance();
			Calendar calDelay = GregorianCalendar.getInstance();
			Calendar calExpire = GregorianCalendar.getInstance();
			calDelay.setTime(loan.getLoanDelayDate());
			calStart.setTime(loan.getLoanStartDate());
			calExpire.setTime(loan.getLoanExpireDate());
			int expireYear = calExpire.get(Calendar.YEAR);
			int delayYear = calDelay.get(Calendar.YEAR);
			int nowYear = calStart.get(Calendar.YEAR);
			int delayCount = (delayYear - nowYear) * 12;
			int totalCount = (expireYear - nowYear) * 12;
			req.setAttribute("count", list.size());
			if(loan.getLoanMethod().equals("A")) {
				if(totalCount-1 == list.size()) {
					loan.setLoanBalance((long)(loan.getLoanBalance() + (loan.getLoanBalance() * loan.getLoanInterest() / 12)));
				}
				else {
					loan.setLoanBalance((long)(loan.getLoanBalance() * loan.getLoanInterest() / 12));
				}
			}
			else {
				if(delayCount > list.size()) {
					loan.setLoanBalance((long)((loan.getLoanBalance() * loan.getLoanInterest() / 12)));
				}
				else if(list.size() < totalCount - 1) {
					long principal  = list.get(0).getLoanBalance()/(totalCount-delayCount);
					long interest = (long)((loan.getLoanBalance() * loan.getLoanInterest()) / 12);
					loan.setLoanBalance(principal + interest);
				}
				else {
					loan.setLoanBalance(loan.getLoanBalance());
				}
			}
			req.setAttribute("loan", loan);
			if(loan.getCustCode().getCustDiv()) {
				req.setAttribute("custdiv", 1);
			}
			else {
				req.setAttribute("custdiv", 0);
			}
			return "/WEB-INF/view/bankwork/loan/loanDetail.jsp";
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String custdiv = req.getParameter("custdiv");
			String cmd = req.getParameter("cmd");
			switch(cmd) {
			case "extend":
				Customer custCode = new Customer();
				String custName = req.getParameter("custname");
				custCode.setCustName(custName);
				String loanAccountNum =req.getParameter("loanaccountnum");
				String dateStr = req.getParameter("expireDate");
				Date expireDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
				Calendar cal = GregorianCalendar.getInstance();
				cal.setTime(expireDate);
				cal.set(Calendar.YEAR, cal.get(Calendar.YEAR)+1);
				Loan loan = new Loan();
				loan.setLoanExpireDate(cal.getTime());
				loan.setCustCode(custCode);
				loan.setLoanAccountNum(loanAccountNum);
				loanService.updateLoanExpireDate(loan);
				Map<String,String> map = new HashMap<>();
				map.put("expireDate", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime()));

				Jsonb om = JsonbBuilder.create();
				String json = om.toJson(map);

//				ObjectMapper om = new ObjectMapper();
//				String json = om.writeValueAsString(map);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(json);
				out.flush();
				break;
			case "repayment":
				custName = req.getParameter("custname");
				loanAccountNum = req.getParameter("loanaccountnum");
				String planName = req.getParameter("planname");
				dateStr = req.getParameter("loanStartDate");
				Date loanStartDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
				dateStr = req.getParameter("loanDelayDate");
				Date loanDelayDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
				dateStr = req.getParameter("loanExpireDate");
				Date loanExpireDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateStr);
				int loanRound = Integer.parseInt(req.getParameter("loanCount").replaceAll("[회차]", ""));
				String loanMethod = req.getParameter("loanMethod").equals("만기일시상환")?"A":"B";
				float loanInterest = Float.parseFloat(req.getParameter("loaninterest").replaceAll("[\\%]", "")) / 100;
				int loanRepayment = Integer.parseInt(req.getParameter("loanbalance").replaceAll("[\\,]", ""));
				Customer cust = new Customer();
				cust.setCustName(custName);
				Plan plan = new Plan();
				plan.setPlanName(planName);
				loan = new Loan();
				loan.setCustCode(cust);
				loan.setLoanAccountNum(loanAccountNum);
				loan = loanService.showLoanByLoanAccountNumAndCustName(loan);
				List<Repayment> list = loanService.searchRepaymentsByAccountNum(loan.getLoanAccountNum());
				Calendar calStart = GregorianCalendar.getInstance();
				Calendar calDelay = GregorianCalendar.getInstance();
				Calendar calExpire = GregorianCalendar.getInstance();
				calStart.setTime(loan.getLoanStartDate());
				calDelay.setTime(loan.getLoanDelayDate());
				calExpire.setTime(loan.getLoanExpireDate());
				int delayYear = calDelay.get(Calendar.YEAR);
				int expireYear = calExpire.get(Calendar.YEAR);
				int nowYear = calStart.get(Calendar.YEAR);
				int delayCount = (delayYear - nowYear) * 12;
				int totalCount = (expireYear - nowYear) * 12;
				if(loan.getLoanMethod().equals("A")) {
					if(totalCount-1 == list.size()) {
						Repayment repayment = new Repayment(loanAccountNum, cust, plan, loanStartDate, loanDelayDate, loanExpireDate, loanMethod, loanRound, loanInterest, loan.getLoanBalance(), loanRepayment);
						loanService.insertAndDeleteProcedure(repayment);
						Contribution contribution = loginService.bankTotalAmount();
						HttpSession session = req.getSession();
						session.removeAttribute("contribution");
						session.setAttribute("contribution", contribution);
						session.setAttribute("finishrepayment", "success");
						res.sendRedirect(req.getContextPath() + "/bankwork/loan/mgn.do?div="+custdiv);
						
					}
					else {
						Repayment repayment = new Repayment(loanAccountNum, cust, plan, loanStartDate, loanDelayDate, loanExpireDate, loanMethod, loanRound, loanInterest, loan.getLoanBalance(), loanRepayment);
						loanService.insertRepayment(repayment);
						Contribution contribution = loginService.bankTotalAmount();
						HttpSession session = req.getSession();
						session.removeAttribute("contribution");
						session.setAttribute("contribution", contribution);
						session.setAttribute("successrepayment", "success");
						res.sendRedirect(req.getContextPath() + "/bankwork/loan/mgn.do?div="+custdiv);
					}		
				}
				else {
					if(delayCount > list.size()) {
						Repayment repayment = new Repayment(loanAccountNum, cust, plan, loanStartDate, loanDelayDate, loanExpireDate, loanMethod, loanRound, loanInterest, loan.getLoanBalance(), loanRepayment);
						loanService.insertRepayment(repayment);
						Contribution contribution = loginService.bankTotalAmount();
						HttpSession session = req.getSession();
						session.removeAttribute("contribution");
						session.setAttribute("contribution", contribution);
						session.setAttribute("successrepayment", "success");
						res.sendRedirect(req.getContextPath() + "/bankwork/loan/mgn.do?div="+custdiv);
					}
					else {
						if(totalCount-1 == list.size()) {
							Repayment repayment = new Repayment(loanAccountNum, cust, plan, loanStartDate, loanDelayDate, loanExpireDate, loanMethod, loanRound, loanInterest, loan.getLoanBalance(), loanRepayment);
							loanService.insertAndDeleteProcedure(repayment);
							Contribution contribution = loginService.bankTotalAmount();
							HttpSession session = req.getSession();
							session.removeAttribute("contribution");
							session.setAttribute("contribution", contribution);
							session.setAttribute("finishrepayment", "success");
							session.removeAttribute("loan");
							res.sendRedirect(req.getContextPath() + "/bankwork/loan/mgn.do?div="+custdiv);
						}
						else {
							Repayment repayment = new Repayment(loanAccountNum, cust, plan, loanStartDate, loanDelayDate, loanExpireDate, loanMethod, loanRound, loanInterest, loan.getLoanBalance(), loanRepayment);
							loanService.insertRepaymentByEquityPaymentProcedure(repayment);
							Contribution contribution = loginService.bankTotalAmount();
							HttpSession session = req.getSession();
							session.removeAttribute("contribution");
							session.setAttribute("contribution", contribution);
							session.setAttribute("successrepayment", "success");
							res.sendRedirect(req.getContextPath() + "/bankwork/loan/mgn.do?div="+custdiv);
						}
					}
					
				}
			}
			return null;
		}
		return null;
	}

}
