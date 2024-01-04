package com.yi.handler.bankwork.card;

import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.dto.Employee;
import com.yi.dto.Plan;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;
import com.yi.service.CardService;

public class DetailHandler implements CommandHandler {
	private CardService cardService = new CardService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String cardNum = req.getParameter("cardnum");
			String custName = req.getParameter("custname");
			Card card = new Card();
			card.setCardNum(cardNum);
			Customer customer = new Customer();
			customer.setCustName(custName);
			card.setCustCode(customer);
			card = cardService.showCardByCardNumAndCustName(card);
			String div;
			if(card.getCardNum().substring(6, 7).equals("1")) {
				div = "체크카드";
			}
			else {
				div = "신용카드";
			}
			String custDiv = card.getCustCode().getCustDiv()?"1":"0";
			req.setAttribute("custDiv", custDiv);
			req.setAttribute("carddiv", div);
			req.setAttribute("card", card);
			return "/WEB-INF/view/bankwork/card/cardDetail.jsp";
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String cmd = req.getParameter("cmd");
			String div = req.getParameter("custDiv");
			if(cmd.equals("mod")) {
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
				String limitStr = req.getParameter("cardLimit")==null?"0":req.getParameter("cardLimit").replaceAll("[\\,]", "");
				String balanceStr = req.getParameter("cardBalance")==null?"0":req.getParameter("cardBalance").replaceAll("[\\,]", "");
				int cardLimit = Integer.parseInt(limitStr);
				long cardBalance = Long.parseLong(balanceStr);
				Card card = new Card(cardNum, custCode, planCode, cardSecuCode, cardIssueDate);
				card.setCardLimit(cardLimit);
				card.setCardBalance(cardBalance);
				if(card.getCardNum().substring(6, 7).equals("1")) {
					cardService.updateCard(card);
					card = cardService.showCardByCheckAccountNum(card);
					cardService.updateAccountBalance(card);
				}
				else {
					cardService.updateCard(card);
				}
				HttpSession session = req.getSession();
				session.setAttribute("successmod", "success");
				res.sendRedirect(req.getContextPath() + "/bankwork/card/mgn.do?div="+div);
			}
			else {
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
				int cardLimit = Integer.parseInt(req.getParameter("cardLimit")==null?"0":req.getParameter("cardLimit").replaceAll("[\\,]", ""));
				long cardBalance = Long.parseLong(req.getParameter("cardBalance")==null?"0":req.getParameter("cardBalance").replaceAll("[\\,]", ""));
				String empName = req.getParameter("empname");
				Employee employee = new Employee();
				employee.setEmpName(empName);
				Card card = new Card(cardNum, custCode, planCode, cardSecuCode, cardIssueDate, cardLimit, cardBalance, employee, null);
				if(card.getCardNum().substring(6, 7).equals("1")) {
					cardService.deleteCheckCardProcedure(card);
				}
				else {
					cardService.deleteCard(card);
				}
				
				HttpSession session = req.getSession();
				session.setAttribute("successdel", "success");
				res.sendRedirect(req.getContextPath() + "/bankwork/card/mgn.do?div="+div);
			}
		}
		return null;
	}

}
