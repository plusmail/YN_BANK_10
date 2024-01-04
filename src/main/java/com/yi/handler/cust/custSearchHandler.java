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

import com.yi.dto.Customer;
import com.yi.handler.paging.Paging;
import com.yi.mvc.CommandHandler;
import com.yi.service.CustomerService;

public class custSearchHandler implements CommandHandler {
    private CustomerService service = new CustomerService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			int vipNum = service.showVIPCustNum();
			int norNum = service.showNormalCustNum();
			req.setAttribute("vipNum", vipNum);
			req.setAttribute("norNum", norNum);
			int size = service.showCustomers().size();
			req.setAttribute("size", size);
			
			String search = req.getParameter("search");
			String div = req.getParameter("div");            
			if(div==null && search==null) {   
				int totalCount = service.showCustomers().size();// 전체 게시글 수
				int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

				Paging paging = new Paging();
				paging.makePaging();
				paging.setPageNo(page); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
				paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
				paging.setTotalCount(totalCount);

				// 첫번째 row 계산
				int startRow = 0;
				if (paging.getPageNo() == 1) {
					// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
					startRow = 0;
				} else {
					// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
					startRow = (paging.getPageNo() - 1) * 10;
				}

				List<Customer> list = service.showCustomersLimit(startRow, paging.getPageSize());

				req.setAttribute("list", list);
				req.setAttribute("paging", paging);
				
				req.setAttribute("pagingAjax", "false");
				return "/WEB-INF/view/cust/custSearch.jsp";
			}else {
				switch (div) {
				case "고객 코드":
					List<Customer> listAll2 = service.showCustomers();
					int totalCount = listAll2.size();// 전체 게시글 수 = ('A')로 찾은 모든 상품 리스트의 길이
					int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

					Paging paging = new Paging();
					paging.makePaging();
					paging.setPageNo(page); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
					paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
					paging.setTotalCount(totalCount);

					// 첫번째 row 계산   
					int startRow;
					if (paging.getPageNo() == 1) {
						// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
						startRow = 0;
					} else {
						// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
						startRow = (paging.getPageNo() - 1) * 10;
					}
					List<Customer> list = service.showCustomersLimitByCode(search, startRow, paging.getPageSize());
					
					req.setAttribute("list", list);
					req.setAttribute("paging", paging);
					  
					req.setAttribute("pagingAjax", "true");
					req.setAttribute("search", search);
					req.setAttribute("searchdiv", div);
					return "/WEB-INF/view/cust/custSearch.jsp";      
				case "고객명":
					List<Customer> listAll = service.showCustomers();
					int totalCount2 = listAll.size();// 전체 게시글 수 = (search)로 찾은 모든 사원 리스트의 길이
					int page2 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

					Paging paging2 = new Paging();
					paging2.makePaging();
					paging2.setPageNo(page2); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
					paging2.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
					paging2.setTotalCount(totalCount2);

					// 첫번째 row 계산
					int startRow2 = 0;
					if (paging2.getPageNo() == 1) {
						// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
						startRow2 = 0;
					} else {
						// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
						startRow2 = (paging2.getPageNo() - 1) * 10;
					}

					List<Customer> list2 = service.showCustomersLimitByName(search, startRow2, paging2.getPageSize());
					req.setAttribute("list", list2);
					req.setAttribute("paging", paging2);
					
					req.setAttribute("pagingAjax", "true");
					req.setAttribute("search", search);
					req.setAttribute("searchdiv", div);
					return "/WEB-INF/view/cust/custSearch.jsp";
				case "연락처":
					List<Customer> listAll3 = service.showCustomers();
					int totalCount3 = listAll3.size();// 전체 게시글 수 = (search)로 찾은 모든 사원 리스트의 길이
					int page3 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

					Paging paging3 = new Paging();
					paging3.makePaging();
					paging3.setPageNo(page3); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
					paging3.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
					paging3.setTotalCount(totalCount3);

					// 첫번째 row 계산
					int startRow3 = 0;
					if (paging3.getPageNo() == 1) {
						// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
						startRow3 = 0;
					} else {
						// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
						startRow3 = (paging3.getPageNo() - 1) * 10;
					}

					List<Customer> list3 = service.showCustomersLimitByTel(search, startRow3, paging3.getPageSize());
					req.setAttribute("list", list3);
					req.setAttribute("paging", paging3);
					
					req.setAttribute("pagingAjax", "true");
					req.setAttribute("search", search);
					req.setAttribute("searchdiv", div);
					return "/WEB-INF/view/cust/custSearch.jsp";
				
				}
			}
		
			
			
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			String search = req.getParameter("search"); //검색어
			String div = req.getParameter("div"); //검색 조건
			
			switch(div) {
			case "고객 코드":
				List<Customer> list = service.showCustomerByCode(search);
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
				int totalCount = list.size();// 전체 게시글 수 = ('A')로 찾은 모든 상품 리스트의 길이
				int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
					
				Paging paging = new Paging();
				paging.makePaging();
				paging.setPageNo(page); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
				paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
				paging.setTotalCount(totalCount);

				// 첫번째 row 계산
				int startRow;
				if (paging.getPageNo() == 1) {
					// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
					startRow = 0;
				} else {
					// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
					startRow = (paging.getPageNo() - 1) * 10;
				}
				List<Customer> list2 = service.showCustomersLimitByCode(search, startRow, paging.getPageSize());// 'A001'
																													// 로
																													// 찾은
																													// 직원
																													// 리스트에서
																													// limit가
																													// 있는
																													// 리스트
				
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
				// String json = om.writeValueAsString(list);
				// 리스트, 페이징을 같이 가져가기
				HashMap<String, Object> listPageMap = new HashMap<>();
				listPageMap.put("paging", paging);
				listPageMap.put("list", list2);
				Jsonb om = JsonbBuilder.create();
				String json = om.toJson(listPageMap);

//				ObjectMapper om = new ObjectMapper();
//				String json = om.writeValueAsString(listPageMap);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(json);
				out.flush();
				break;
			case "고객명":
				List<Customer> list3 = new ArrayList<>();
				list3 = service.showCustomerByName(search);  
				 
				if(list3==null) {
					HashMap<String,String> map = new HashMap<>();
					map.put("error", "notExist");

					Jsonb om2 = JsonbBuilder.create();
					String json2= om2.toJson(map);

//					ObjectMapper om2 = new ObjectMapper();
//					String json2 = om2.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out2 = res.getWriter();
					out2.write(json2);
					out2.flush();
					break;
				}
				int totalCount3 = list3.size();// 전체 게시글 수 = ('A')로 찾은 모든 상품 리스트의 길이
				int page3 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

				Paging paging3 = new Paging();
				paging3.makePaging();
				paging3.setPageNo(page3); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
				paging3.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
				paging3.setTotalCount(totalCount3);

				// 첫번째 row 계산
				int startRow3;
				if (paging3.getPageNo() == 1) {
					// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
					startRow3 = 0;
				} else {
					// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
					startRow3 = (paging3.getPageNo() - 1) * 10;
				}
				List<Customer> list4= service.showCustomersLimitByName(search, startRow3, paging3.getPageSize());// 'A001'
																													// 로
																													// 찾은
																													// 직원
														   															// 리스트에서
																													// limit가
																													// 있는
																													// 리스트
				if(list4==null) {
					HashMap<String,String> map = new HashMap<>();
					map.put("error", "notExist");

					Jsonb om2 = JsonbBuilder.create();
					String json2 = om2.toJson(map);
//
//					ObjectMapper om2 = new ObjectMapper();
//					String json2 = om2.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out2 = res.getWriter();
					out2.write(json2);
					out2.flush();
					break;  
				}
				// String json = om.writeValueAsString(list);
				// 리스트, 페이징을 같이 가져가기
				HashMap<String, Object> listPageMap3 = new HashMap<>();
				listPageMap3.put("paging", paging3);
				listPageMap3.put("list", list4);

				Jsonb om3 = JsonbBuilder.create();
				String json3 = om3.toJson(listPageMap3);

//				ObjectMapper om3 = new ObjectMapper();
//				String json3 = om3.writeValueAsString(listPageMap3);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out3 = res.getWriter();
				out3.write(json3);
				out3.flush();
				break;
			case "연락처":
				List<Customer> list6 = service.showCustomerByTel(search);
				
				if(list6 == null) {
					HashMap<String,String> map = new HashMap<>();
					map.put("error", "notExist");

					Jsonb om4 = JsonbBuilder.create();
					String json4 = om4.toJson(map);
//					ObjectMapper om4 = new ObjectMapper();
//					String json4 = om4.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out4 = res.getWriter();
					out4.write(json4);
					out4.flush();
					break;
				}
				int totalCount4 = list6.size();// 전체 게시글 수 = ('A')로 찾은 모든 상품 리스트의 길이
				int page4 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

				Paging paging4 = new Paging();
				paging4.makePaging();
				paging4.setPageNo(page4); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
				paging4.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
				paging4.setTotalCount(totalCount4);

				// 첫번째 row 계산
				int startRow4;
				if (paging4.getPageNo() == 1) {
					// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
					startRow4 = 0;
				} else {
					// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
					startRow4 = (paging4.getPageNo() - 1) * 10;
				}
				List<Customer> list5 = service.showCustomersLimitByTel(search, startRow4, paging4.getPageSize());// 'A001'
																													// 로
																													// 찾은
																													// 직원
																													// 리스트에서
																													// limit가
																													// 있는
																													// 리스트
				if(list5==null) {
					HashMap<String,String> map = new HashMap<>();
					map.put("error", "notExist");

					Jsonb om2 = JsonbBuilder.create();
					String json2 = om2.toJson(map);
//
//					ObjectMapper om2 = new ObjectMapper();
//					String json2 = om2.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out2 = res.getWriter();
					out2.write(json2);
					out2.flush();
					break;
				}
				// String json = om.writeValueAsString(list);
				// 리스트, 페이징을 같이 가져가기
				HashMap<String, Object> listPageMap5 = new HashMap<>();
				listPageMap5.put("paging", paging4);
				listPageMap5.put("list", list5);
				Jsonb om5 = JsonbBuilder.create();
				String json5 = om5.toJson(listPageMap5);

//				ObjectMapper om5 = new ObjectMapper();
//				String json5 = om5.writeValueAsString(listPageMap5);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out5 = res.getWriter();
				out5.write(json5);
				out5.flush();
				break;
			}
		}
		
		return null;
	}

}
