package com.yi.handler.bankwork.card;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.dto.Plan;
import com.yi.handler.paging.Paging;
import com.yi.mvc.CommandHandler;
import com.yi.service.CardService;

public class MgnHandler implements CommandHandler {
	private CardService service = new CardService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String div = req.getParameter("div");
			if(div.equals("0")) {
				//List<Card> list = service.showCardsByNormal();
				int size = service.showCardsByNormal().size();
				req.setAttribute("size", size);
				
				int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
				 Paging paging = new Paging();   
				    paging.makePaging();      
				    paging.setPageNo(page); //get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
				    paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
				    paging.setTotalCount(size);
				
				  //첫번째 row 계산  
				    int startRow = 0;
				    if(paging.getPageNo()==1) {   
				    	//현재 페이지가 1이면 0부터 10개 리스트 불러옴
				    	startRow = 0;
				    }else {   
				    	//현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
				    	startRow = (paging.getPageNo()-1)*10;
				    }    
			    List<Card> list = service.showCardsByNormal(startRow, paging.getPageSize());  
				if(list.size()==0) {
					HttpSession session = req.getSession();
					session.setAttribute("errornonnormal", "error");
					return "/WEB-INF/view/bankwork/card/cardListCustSelect.jsp";
				}
				req.setAttribute("custdiv", div);
				req.setAttribute("list", list);
				req.setAttribute("paging", paging); 
		
				
				return "/WEB-INF/view/bankwork/card/cardMgnNormal.jsp";
			}
			else {
				//List<Card> list = service.showCardsByBusiness();
				int size = service.showCardsByBusiness().size();
				req.setAttribute("size", size);
				
				int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
				 Paging paging = new Paging();   
				    paging.makePaging();      
				    paging.setPageNo(page); //get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
				    paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
				    paging.setTotalCount(size);
				
				  //첫번째 row 계산  
				    int startRow = 0;
				    if(paging.getPageNo()==1) {   
				    	//현재 페이지가 1이면 0부터 10개 리스트 불러옴
				    	startRow = 0;
				    }else {   
				    	//현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
				    	startRow = (paging.getPageNo()-1)*10;
				    } 
				 List<Card> list = service.showCardsByBusiness(startRow, paging.getPageSize());    
				    
				if(list.size()==0) {
					HttpSession session = req.getSession();
					session.setAttribute("errornonbusiness", "error");
					return "/WEB-INF/view/bankwork/card/cardListCustSelect.jsp";
				}
				req.setAttribute("custdiv", div);
				req.setAttribute("list", list);
				req.setAttribute("paging", paging); 
				
				
				return "/WEB-INF/view/bankwork/card/cardMgnBusiness.jsp";
			}
		}
		else if(req.getMethod().equalsIgnoreCase("post")) {
			String search = req.getParameter("search");
			String custdiv = req.getParameter("custdiv");
			String div = req.getParameter("div");
			switch(div) {
			case "고객이름":
				Customer customer = new Customer();
				customer.setCustName(search);
				customer.setCustDiv(custdiv.equals("0")?false:true);
				Card card = new Card();
				card.setCustCode(customer);
				List<Card> list = service.showCardByCustName(card);
				if(list.size()==0) {
					HashMap<String,String> map = new HashMap<>();
					map.put("errorCustName", "error");

					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}
				else {
					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(list);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(list);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}
				break;
			case "상품명":
				Plan plan = new Plan();
				plan.setPlanName(search);
				card = new Card();
				card.setPlanCode(plan);
				customer = new Customer();
				customer.setCustDiv(custdiv.equals("0")?false:true);
				card.setCustCode(customer);
				list = service.showCardByPlanName(card);
				if(list.size()==0) {
					HashMap<String,String> map = new HashMap<>();
					map.put("errorPlanName", "error");
					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}
				else {
					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(list);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(list);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}
				break;
			case "카드구분":
				switch(search) {
				case "체크카드":
					customer = new Customer();
					customer.setCustDiv(custdiv.equals("0")?false:true);
					list = service.showCardByCheckCard(customer);
					if(list.size()==0) {
						HashMap<String,String> map = new HashMap<>();
						map.put("errorNoDiv", "error");

						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);

//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					}
					else {
						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(list);
//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(list);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					}
					break;
				case "신용카드":
					customer = new Customer();
					customer.setCustDiv(custdiv.equals("0")?false:true);
					list = service.showCardByCreditCard(customer);
					if(list.size()==0) {
						HashMap<String,String> map = new HashMap<>();
						map.put("errorNoDiv", "error");

						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);

//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					}
					else {
						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(list);
//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(list);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					}
					break;
				default :
					HashMap<String,String> map = new HashMap<>();
					map.put("errorCardDiv", "error");

					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
					break;
				}
				break;
			}
		}
		return null;
	}

}
