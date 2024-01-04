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
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custDWRecordDateHandler implements CommandHandler {
	CustomerService service = new CustomerService();
	   
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("post")) {
			String date = req.getParameter("date"); //검색 날짜
				List<Cust_dw_audit> list = service.showCust_dw_auditByDate(date);
				if(list==null || list.size()==0) {
					HashMap<String,String> map = new HashMap<>();
					map.put("nodata", "nodata");

					Jsonb jsonb = JsonbBuilder.create();
					String json = jsonb.toJson(map);
//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}else {
					Jsonb jsonb = JsonbBuilder.create();
					String json = jsonb.toJson(list);
//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(list);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();     
					out.write(json);
					out.flush();
				}
				  
				
			
		}
		return null;
	}

}
