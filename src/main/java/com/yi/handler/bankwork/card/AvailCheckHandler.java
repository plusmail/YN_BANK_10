package com.yi.handler.bankwork.card;

import java.io.PrintWriter;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.CardService;

public class AvailCheckHandler implements CommandHandler {
	private CardService service = new CardService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			Customer custCode = new Customer();
			String custName = req.getParameter("custname");
			custCode.setCustName(custName);
			Card card = new Card();
			card.setCustCode(custCode);
			List<BankBook> list = service.showBankBookIsConnect(card);
			if(list.size()==0) {
				return null;
			}
			else {
				Jsonb om = JsonbBuilder.create();
				String json = om.toJson(list);
//				ObjectMapper om = new ObjectMapper();
//				String json = om.writeValueAsString(list);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(json);
				out.flush();
			}
			
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			return null;
		}
		return null;
	}

}
