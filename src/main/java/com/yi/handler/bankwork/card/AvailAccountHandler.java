package com.yi.handler.bankwork.card;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.swing.plaf.synth.SynthSeparatorUI;

import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.CardService;

public class AvailAccountHandler implements CommandHandler {
	private CardService service = new CardService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String custCode = req.getParameter("custcode");
		Customer customer = new Customer(custCode);
		Card card = new Card(customer);
		List<BankBook> list = service.showBankBookIsConnect(card);
		req.setAttribute("list", list);
		return "/WEB-INF/view/bankwork/card/cardSelectAccountForm.jsp";	
	}

}
