package com.yi.handler.emp;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Employee;
import com.yi.handler.paging.PageMaker;
import com.yi.handler.paging.SearchCriteria;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empAuthHandler implements CommandHandler {
	private EmployeeUIService service = new EmployeeUIService();

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			
			int page = req.getParameter("page")==null?1:Integer.parseInt(req.getParameter("page"));
			SearchCriteria cri = new SearchCriteria();
			String searchType = req.getParameter("searchType");
			String keyword = req.getParameter("keyword");
			cri.setPage(page);
			cri.setSearchType(searchType);
			cri.setKeyword(keyword);
			List<Employee> list = service.showExistEmployeeLimit(cri);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.getTotalSearchCount(cri));
		
			req.setAttribute("list", list);
			req.setAttribute("cri", cri);
			
			req.setAttribute("pageMaker",pageMaker);
			
			return "/WEB-INF/view/emp/empAuthList.jsp";
			
			/* String search = req.getParameter("search");
			String div = req.getParameter("div");
			if (div == null && search == null) {

				int totalCount = service.showExistEmpList().size();// 전체 게시글 수
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

				List<Employee> list = service.showExistEmployeeLimit(startRow, paging.getPageSize());

				req.setAttribute("list", list);
				req.setAttribute("paging", paging);
				req.setAttribute("pagingAjax", "false");

				return "/WEB-INF/view/emp/empAuthList.jsp";
			} else {

				switch (div) {

				case "사원번호":

					Employee emp = service.showPikedEmpByCode(search, 0);

					if (emp == null) {
						HashMap<String, String> map = new HashMap<>();
						map.put("error", "notExist");
						ObjectMapper om = new ObjectMapper();
						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
						break;
					}
					int totalCount = 1;// 전체 게시글 수 = ('A')로 찾은 모든 상품 리스트의 길이
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
					Employee employee = service.showExistEmployeeLimitByCode(search, startRow, paging.getPageSize());
					
				    List<Employee> list = new ArrayList<>();
                 	list.add(employee);
                	req.setAttribute("list", list);
					// String json = om.writeValueAsString(list);
					// 리스트, 페이징을 같이 가져가기
	
					req.setAttribute("paging", paging);
					//req.setAttribute("employee", employee);
		
					req.setAttribute("paging", paging);
					req.setAttribute("pagingAjax", "true");
					req.setAttribute("search", search);
					req.setAttribute("searchdiv", div);
					return "/WEB-INF/view/emp/empAuthList.jsp";

				case "사원이름":
					List<Employee> listAll = service.showPickedEmpList(search, 0);

					if (listAll.size()==0) {
						HashMap<String, String> map = new HashMap<>();
						map.put("error", "notExist");
						ObjectMapper om2 = new ObjectMapper();
						String json2 = om2.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out2 = res.getWriter();
						out2.write(json2);
						out2.flush();
						break;
					}
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

					List<Employee> list2 = service.showExistEmployeeLimitByName(search, startRow2,
							paging2.getPageSize());// (search)로 찾은 사원 리스트에서 limit가 있는 리스트
					// String json = om.writeValueAsString(list);
					if (list2 == null) {
						HashMap<String, String> map = new HashMap<>();
						map.put("error", "notExist");
						ObjectMapper om2 = new ObjectMapper();
						String json2 = om2.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out2 = res.getWriter();
						out2.write(json2);
						out2.flush();
						break;
					}
					// 리스트, 페이징을 같이 가져가기
					
					
					req.setAttribute("paging", paging2);
					req.setAttribute("list", list2);
				

					req.setAttribute("pagingAjax", "true");
					req.setAttribute("search", search);
					req.setAttribute("searchdiv", div);
					return "/WEB-INF/view/emp/empAuthList.jsp";

				case "부서(인사 or 고객)":
					List<Employee> listAll3 = service.showPickedEmpByDept(search, 0);

					if (listAll3.size()==0) {
						HashMap<String, String> map = new HashMap<>();
						map.put("error", "notExist");
						ObjectMapper om3 = new ObjectMapper();
						String json3 = om3.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out3 = res.getWriter();
						out3.write(json3);
						out3.flush();
						break;
					}
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

					List<Employee> list3 = service.showExistEmployeeLimitByDept(search, startRow3,
							paging3.getPageSize());// (search)로 찾은 사원 리스트에서 limit가 있는 리스트
					// 리스트, 페이징을 같이 가져가기
					req.setAttribute("paging", paging3);
					req.setAttribute("list", list3);
					req.setAttribute("pagingAjax", "true");
					req.setAttribute("search", search);
					req.setAttribute("searchdiv", div);
					return "/WEB-INF/view/emp/empAuthList.jsp";

				case "직급":
					List<Employee> listAll4 = service.showPickedEmpByTitle(search, 0);

					if (listAll4.size()==0) {
						HashMap<String, String> map = new HashMap<>();
						map.put("error", "notExist");
						ObjectMapper om4 = new ObjectMapper();
						String json4 = om4.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out4 = res.getWriter();
						out4.write(json4);
						out4.flush();
						break;
					}
					int totalCount4 = listAll4.size();// 전체 게시글 수 = (search)로 찾은 모든 사원 리스트의 길이
					int page4 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

					Paging paging4 = new Paging();
					paging4.makePaging();
					paging4.setPageNo(page4); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
					paging4.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
					paging4.setTotalCount(totalCount4);

					// 첫번째 row 계산
					int startRow4 = 0;
					if (paging4.getPageNo() == 1) {
						// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
						startRow4 = 0;
					} else {
						// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
						startRow4 = (paging4.getPageNo() - 1) * 10;
					}

					List<Employee> list4 = service.showExistEmployeeLimitByTitle(search, startRow4,
							paging4.getPageSize());// (search)로 찾은 사원 리스트에서 limit가 있는 리스트

					// 리스트, 페이징을 같이 가져가기
					
					req.setAttribute("paging", paging4);
					req.setAttribute("list", list4);

					req.setAttribute("pagingAjax", "true");
					req.setAttribute("search", search);
					req.setAttribute("searchdiv", div);
					return "/WEB-INF/view/emp/empAuthList.jsp";

				}
			} */
			// 포스트일때 -------------------------------------------------------------------------------------------------------------------------------------
		} 
		/* else if (req.getMethod().equalsIgnoreCase("post")) {
			String search = req.getParameter("search");
			String div = req.getParameter("div");

			switch (div) {

			case "사원번호":

				Employee emp = service.showPikedEmpByCode(search, 0);

				if (emp == null) {
					HashMap<String, String> map = new HashMap<>();
					map.put("error", "notExist");
					ObjectMapper om = new ObjectMapper();
					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
					break;
				}
				int totalCount = 1;// 전체 게시글 수 = ('A')로 찾은 모든 상품 리스트의 길이
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
				Employee employee = service.showExistEmployeeLimitByCode(search, startRow, paging.getPageSize());
				

				// String json = om.writeValueAsString(list);
				// 리스트, 페이징을 같이 가져가기
				HashMap<String, Object> listPageMap = new HashMap<>();
				listPageMap.put("paging", paging);
				listPageMap.put("employee", employee);
				ObjectMapper om = new ObjectMapper();
				String json = om.writeValueAsString(listPageMap);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out = res.getWriter();
				out.write(json);
				out.flush();

				break;

			case "사원이름":
				List<Employee> listAll = service.showPickedEmpList(search, 0);

				if (listAll.size()==0) {
					HashMap<String, String> map = new HashMap<>();
					map.put("error", "notExist");
					ObjectMapper om2 = new ObjectMapper();
					String json2 = om2.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out2 = res.getWriter();
					out2.write(json2);
					out2.flush();
					break;
				}
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

				List<Employee> list = service.showExistEmployeeLimitByName(search, startRow2, paging2.getPageSize());
				
				// String json = om.writeValueAsString(list);
				if (list == null) {
					HashMap<String, String> map = new HashMap<>();
					map.put("error", "notExist");
					ObjectMapper om2 = new ObjectMapper();
					String json2 = om2.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out2 = res.getWriter();
					out2.write(json2);
					out2.flush();
					break;
				}
				// 리스트, 페이징을 같이 가져가기
				HashMap<String, Object> listPageMap2 = new HashMap<>();
				listPageMap2.put("paging", paging2);
				listPageMap2.put("list", list);
				ObjectMapper om2 = new ObjectMapper();
				String json2 = om2.writeValueAsString(listPageMap2);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out2 = res.getWriter();
				out2.write(json2);
				out2.flush();

				break;

			case "부서(인사 or 고객)":
				List<Employee> listAll3 = service.showPickedEmpByDept(search, 0);

				if (listAll3.size()==0) {
					HashMap<String, String> map = new HashMap<>();
					map.put("error", "notExist");
					ObjectMapper om3 = new ObjectMapper();
					String json3 = om3.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out3 = res.getWriter();
					out3.write(json3);
					out3.flush();
					break;
				}
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

				List<Employee> list3 = service.showExistEmployeeLimitByDept(search, startRow3, paging3.getPageSize());

				// 리스트, 페이징을 같이 가져가기
				HashMap<String, Object> listPageMap3 = new HashMap<>();
				listPageMap3.put("paging", paging3);
				listPageMap3.put("list", list3);
				ObjectMapper om3 = new ObjectMapper();
				String json3 = om3.writeValueAsString(listPageMap3);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out3 = res.getWriter();
				out3.write(json3);
				out3.flush();

				break;

			case "직급":
				List<Employee> listAll4 = service.showPickedEmpByTitle(search, 0);

				if (listAll4.size()==0) {
					HashMap<String, String> map = new HashMap<>();
					map.put("error", "notExist");
					ObjectMapper om4 = new ObjectMapper();
					String json4 = om4.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out4 = res.getWriter();
					out4.write(json4);
					out4.flush();
					break;
				}
				int totalCount4 = listAll4.size();// 전체 게시글 수 = (search)로 찾은 모든 사원 리스트의 길이
				int page4 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

				Paging paging4 = new Paging();
				paging4.makePaging();
				paging4.setPageNo(page4); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
				paging4.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
				paging4.setTotalCount(totalCount4);

				// 첫번째 row 계산
				int startRow4 = 0;
				if (paging4.getPageNo() == 1) {
					// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
					startRow4 = 0;
				} else {
					// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
					startRow4 = (paging4.getPageNo() - 1) * 10;
				}

				List<Employee> list4 = service.showExistEmployeeLimitByTitle(search, startRow4, paging4.getPageSize());

				// 리스트, 페이징을 같이 가져가기
				HashMap<String, Object> listPageMap4 = new HashMap<>();
				listPageMap4.put("paging", paging4);
				listPageMap4.put("list", list4);
				ObjectMapper om4 = new ObjectMapper();
				String json4 = om4.writeValueAsString(listPageMap4);
				res.setContentType("application/json;charset=UTF-8");
				PrintWriter out4 = res.getWriter();
				out4.write(json4);
				out4.flush();

				break;

			}

		}  */

		return null;
	}

}
