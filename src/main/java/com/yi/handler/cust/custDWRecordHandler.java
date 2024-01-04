package com.yi.handler.cust;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Cust_dw_audit;
import com.yi.handler.paging.Paging;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custDWRecordHandler implements CommandHandler {
    private CustomerService service = new CustomerService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			//List<Cust_dw_audit> list = service.showCust_dw_audit();
			//req.setAttribute("list", list);
			int size = service.showCust_dw_audit().size();
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
			    
			    List<Cust_dw_audit> list = service.showCust_dw_audit(startRow, paging.getPageSize()); 
			    req.setAttribute("list", list);
			    req.setAttribute("paging", paging);    
			return "/WEB-INF/view/cust/custDWRecordSearch.jsp";
			
			
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			String search = req.getParameter("search"); //검색어
			String div = req.getParameter("div"); //검색 조건
			
			switch(div) {
			case "계좌 번호":
				List<Cust_dw_audit> list = service.showCust_dw_auditByAcc(search);
				if(list==null) {
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
				List<Cust_dw_audit> list2 = new ArrayList<>();
				list2 = service.showCust_dw_auditByName(search);
				 
				if(list2==null) {
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
			
			}
		}
		
		return null;
	}

}
