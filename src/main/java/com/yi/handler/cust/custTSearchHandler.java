package com.yi.handler.cust;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
//import jakarta.swing.plaf.synth.SynthSeparatorUI;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Customer;
import com.yi.handler.paging.Paging;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custTSearchHandler implements CommandHandler {
    private CustomerService service = new CustomerService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String dw = req.getParameter("dw");
			req.setAttribute("dw", dw);
			int size = service.showCustomerWhoHas11Acc().size();
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
		
			List<Customer> listForBal = service.showCustomerWhoHas11Acc(startRow, paging.getPageSize());
			
			
			req.setAttribute("list", listForBal);
			req.setAttribute("listForBal", listForBal);
			req.setAttribute("paging", paging);   
			

			return "/WEB-INF/view/cust/custTSearch.jsp";
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			
			
			String search = req.getParameter("search"); //검색어
			String div = req.getParameter("div"); //검색 조건
			
			
			switch(div) {
			case "고객 코드":   
				List<Customer> list = service.showCustomerWHas11AccByCode(search);
				if(list == null ) {  
					HashMap<String,String> map = new HashMap<>();
					map.put("error", "notExist");
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
				Jsonb om = JsonbBuilder.create();
				String json = om.toJson(list);

//				ObjectMapper om = new ObjectMapper();
//				String json = om.writeValueAsString(list);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out = res.getWriter();     
				out.write(json);
				out.flush();  
				
				break;
			case "고객명":
				List<Customer> list2 = new ArrayList<>();
				list2 = service.showCustomerWHas11AccByName(search);
				 
				if(list2 == null) {
					HashMap<String,String> map = new HashMap<>();
					map.put("error", "notExist");

					Jsonb om2 = JsonbBuilder.create();
					String json2 = om2.toJson(map);

//					ObjectMapper om2 = new ObjectMapper();
//					String json2 = om2.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out2 = res.getWriter();
					out2.write(json2);
					out2.flush();
					break;
				}
				Jsonb om2 = JsonbBuilder.create();
				String json2 = om2.toJson(list2);

//				ObjectMapper om2 = new ObjectMapper();
//				String json2 = om2.writeValueAsString(list2);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out2 = res.getWriter();
				out2.write(json2);
				out2.flush(); 
				break;
			case "연락처":
				List<Customer> list3 = new ArrayList<>();
				list3 = service.showCustomerWHas11AccByTel(search);
				if(list3 == null) {
					HashMap<String,String> map = new HashMap<>();
					map.put("error", "notExist");

					Jsonb om3 = JsonbBuilder.create();
					String json3 = om3.toJson(map);

//					ObjectMapper om3 = new ObjectMapper();
//					String json3 = om3.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out3 = res.getWriter();
					out3.write(json3);
					out3.flush();
					break;
				}

				Jsonb om3 = JsonbBuilder.create();
				String json3 = om3.toJson(list3);

//				ObjectMapper om3 = new ObjectMapper();
//				String json3 = om3.writeValueAsString(list3);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out3 = res.getWriter();
				out3.write(json3);
				out3.flush(); 
				break;
			}
		}
		
		return null;
	}

}
