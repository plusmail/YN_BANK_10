package com.yi.handler.bankwork.bankbook;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.dto.Plan;
import com.yi.handler.paging.Paging;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;

public class MgnHandler implements CommandHandler {
	private BankBookService service = new BankBookService();
	private int startRow;
	private Paging paging;
	private int page;
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if (req.getMethod().equalsIgnoreCase("get")) {
			String search = req.getParameter("search");
			String searchdiv = req.getParameter("searchdiv");
			String div = req.getParameter("div");
			HttpSession session = req.getSession();
			if (div.equals("0")) {
				if(search==null&searchdiv==null) {
					int size = service.showBankBooksByNormal().size();
					req.setAttribute("size", size);
					pagingAction(req, size);

					List<BankBook> list = service.showBankBooksByNormal(startRow, paging.getPageSize());
					if (list.size() == 0) {
						session.setAttribute("errornonnormal", "error");
						return "/WEB-INF/view/bankwork/bankbook/bankbookListCustSelect.jsp";
					}
					setAttrNormal(req, div, list);
					return "/WEB-INF/view/bankwork/bankbook/bankbookMgnNormal.jsp";
				}
				else {
					switch(searchdiv) {
					case "계좌번호":
						BankBook bankbook = new BankBook();
						bankbook.setAccountNum(search);
						Customer customer = new Customer();
						customer.setCustDiv(false);
						bankbook.setCustCode(customer);
						List<BankBook> list = service.showBankBookByAccoutNum(bankbook);
						int size = list.size();
						pagingAction(req,size);
						list = service.showBankBookByAccoutNum(bankbook, startRow, paging.getPageSize());
						setAttrAjax(req, search, searchdiv, div, list);
						return "/WEB-INF/view/bankwork/bankbook/bankbookMgnNormal.jsp";
					case "고객이름":
						customer = new Customer();
						customer.setCustName(search);
						customer.setCustDiv(false);
						bankbook = new BankBook();
						bankbook.setCustCode(customer);
						list = service.showBankBookByCustName(bankbook);
						size = list.size();		
						pagingAction(req,size);
						list = service.showBankBookByCustName(bankbook, startRow, paging.getPageSize());
						setAttrAjax(req, search, searchdiv, div, list);
						return "/WEB-INF/view/bankwork/bankbook/bankbookMgnNormal.jsp";
					case "상품명":
						Plan plan = new Plan();
						plan.setPlanName(search);
						customer = new Customer();
						customer.setCustDiv(false);
						bankbook = new BankBook();
						bankbook.setAccountPlanCode(plan);
						bankbook.setCustCode(customer);
						list = service.showBankBookByPlanName(bankbook);
						size = list.size();
						pagingAction(req,size);
						list = service.showBankBookByPlanName(bankbook, startRow, paging.getPageSize());
						setAttrAjax(req, search, searchdiv, div, list);
						return "/WEB-INF/view/bankwork/bankbook/bankbookMgnNormal.jsp";
					case "통장상품":
						switch (search) {
						case "예금":
							customer = new Customer();
							customer.setCustDiv(false);
							list = service.showBankBookByDeposit(customer);
							size = list.size();
							pagingAction(req,size);
							list = service.showBankBookByDeposit(customer, startRow, paging.getPageSize());
							setAttrAjax(req, search, searchdiv, div, list);
							return "/WEB-INF/view/bankwork/bankbook/bankbookMgnNormal.jsp";
						case "적금":
							customer = new Customer();
							customer.setCustDiv(false);
							list = service.showBankBookBySaving(customer);
							size = list.size();
							pagingAction(req,size);
							list = service.showBankBookBySaving(customer, startRow, paging.getPageSize());
							setAttrAjax(req, search, searchdiv, div, list);
							return "/WEB-INF/view/bankwork/bankbook/bankbookMgnNormal.jsp";
						case "마이너스":
							customer = new Customer();
							customer.setCustDiv(false);
							list = service.showBankBookByMinus(customer);
							size = list.size();
							pagingAction(req,size);
							list = service.showBankBookByMinus(customer, startRow, paging.getPageSize());
							setAttrAjax(req, search, searchdiv, div, list);
							return "/WEB-INF/view/bankwork/bankbook/bankbookMgnNormal.jsp";
						}
						break;
					}
				}
				
			} else {
				if(search==null&searchdiv==null) {
					int size = service.showBankBooksByBusiness().size();
					req.setAttribute("size", size);
					pagingAction(req, size);
					List<BankBook> list = service.showBankBooksByBusiness(startRow, paging.getPageSize());
					if (list.size() == 0) {
						session.setAttribute("errornonbusiness", "error");
						return "/WEB-INF/view/bankwork/bankbook/bankbookListCustSelect.jsp";
					}
					setAttrNormal(req, div, list);
					return "/WEB-INF/view/bankwork/bankbook/bankbookMgnBusiness.jsp";
				}
				else {
					switch(searchdiv) {
					case "계좌번호":
						BankBook bankbook = new BankBook();
						bankbook.setAccountNum(search);
						Customer customer = new Customer();
						customer.setCustDiv(false);
						bankbook.setCustCode(customer);
						List<BankBook> list = service.showBankBookByAccoutNum(bankbook);
						int size = list.size();
						pagingAction(req,size);
						list = service.showBankBookByAccoutNum(bankbook, startRow, paging.getPageSize());
						setAttrAjax(req, search, searchdiv, div, list);
						return "/WEB-INF/view/bankwork/bankbook/bankbookMgnBusiness.jsp";
					case "고객이름":
						customer = new Customer();
						customer.setCustName(search);
						customer.setCustDiv(false);
						bankbook = new BankBook();
						bankbook.setCustCode(customer);
						list = service.showBankBookByCustName(bankbook);
						size = list.size();		
						pagingAction(req,size);
						list = service.showBankBookByCustName(bankbook, startRow, paging.getPageSize());
						setAttrAjax(req, search, searchdiv, div, list);
						return "/WEB-INF/view/bankwork/bankbook/bankbookMgnBusiness.jsp";
					case "상품명":
						Plan plan = new Plan();
						plan.setPlanName(search);
						customer = new Customer();
						customer.setCustDiv(false);
						bankbook = new BankBook();
						bankbook.setAccountPlanCode(plan);
						bankbook.setCustCode(customer);
						list = service.showBankBookByPlanName(bankbook);
						size = list.size();
						pagingAction(req,size);
						list = service.showBankBookByPlanName(bankbook, startRow, paging.getPageSize());
						setAttrAjax(req, search, searchdiv, div, list);
						return "/WEB-INF/view/bankwork/bankbook/bankbookMgnBusiness.jsp";
					case "통장상품":
						switch (search) {
						case "예금":
							customer = new Customer();
							customer.setCustDiv(false);
							list = service.showBankBookByDeposit(customer);
							size = list.size();
							pagingAction(req,size);
							list = service.showBankBookByDeposit(customer, startRow, paging.getPageSize());
							setAttrAjax(req, search, searchdiv, div, list);
							return "/WEB-INF/view/bankwork/bankbook/bankbookMgnBusiness.jsp";
						case "적금":
							customer = new Customer();
							customer.setCustDiv(false);
							list = service.showBankBookBySaving(customer);
							size = list.size();
							pagingAction(req,size);
							list = service.showBankBookBySaving(customer, startRow, paging.getPageSize());
							setAttrAjax(req, search, searchdiv, div, list);
							return "/WEB-INF/view/bankwork/bankbook/bankbookMgnBusiness.jsp";
						case "마이너스":
							customer = new Customer();
							customer.setCustDiv(false);
							list = service.showBankBookByMinus(customer);
							size = list.size();
							pagingAction(req,size);
							list = service.showBankBookByMinus(customer, startRow, paging.getPageSize());
							setAttrAjax(req, search, searchdiv, div, list);
							return "/WEB-INF/view/bankwork/bankbook/bankbookMgnBusiness.jsp";
						}
						break;
					}
				}
			}
		} else if (req.getMethod().equalsIgnoreCase("post")) {
			String search = req.getParameter("search");
			String div = req.getParameter("searchdiv");
			String custdiv = req.getParameter("custdiv");
			switch (div) {
			case "계좌번호":
				BankBook bankbook = new BankBook();
				bankbook.setAccountNum(search);
				Customer customer = new Customer();
				customer.setCustDiv(custdiv.equals("0") ? false : true);
				bankbook.setCustCode(customer);
				List<BankBook> list = service.showBankBookByAccoutNum(bankbook);
				int size = list.size();
				if (list.size() == 0) {
					HashMap<String, String> map = new HashMap<>();
					map.put("errorAccountNum", "error");
					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);
//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				} else {
					pagingAction(req,size);
					list = service.showBankBookByAccoutNum(bankbook, startRow, paging.getPageSize());
					req.setAttribute("list", list);
					Map<String,Object> map = new HashMap<>();
					map.put("list", list);
					map.put("paging", this.paging);

					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}
				break;
			case "고객이름":
				customer = new Customer();
				customer.setCustName(search);
				customer.setCustDiv(custdiv.equals("0") ? false : true);
				bankbook = new BankBook();
				bankbook.setCustCode(customer);
				list = service.showBankBookByCustName(bankbook);
				size = list.size();
				if (list.size() == 0) {
					HashMap<String, String> map = new HashMap<>();
					map.put("errorCustName", "error");
					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				} else {
					pagingAction(req,size);
					list = service.showBankBookByCustName(bankbook, startRow, paging.getPageSize());
					req.setAttribute("list", list);
					Map<String,Object> map = new HashMap<>();
					map.put("list", list);
					map.put("paging", this.paging);

					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}
				break;
			case "상품명":
				Plan plan = new Plan();
				plan.setPlanName(search);
				customer = new Customer();
				customer.setCustDiv(custdiv.equals("0") ? false : true);
				bankbook = new BankBook();
				bankbook.setAccountPlanCode(plan);
				bankbook.setCustCode(customer);
				list = service.showBankBookByPlanName(bankbook);
				size = list.size();
				if (list.size() == 0) {
					HashMap<String, String> map = new HashMap<>();
					map.put("errorPlanName", "error");
					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);
//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				} else {
					pagingAction(req,size);
					list = service.showBankBookByPlanName(bankbook, startRow, paging.getPageSize());
					req.setAttribute("list", list);
					Map<String,Object> map = new HashMap<>();
					map.put("list", list);
					map.put("paging", this.paging);

					Jsonb om = JsonbBuilder.create();
					String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
					res.setContentType("application/json;charset=UTF-8");
					PrintWriter out = res.getWriter();
					out.write(json);
					out.flush();
				}
				break;
			case "통장상품":
				switch (search) {
				case "예금":
					customer = new Customer();
					customer.setCustDiv(custdiv.equals("0") ? false : true);
					list = service.showBankBookByDeposit(customer);
					size = list.size();
					if (list.size() == 0) {
						HashMap<String, String> map = new HashMap<>();
						map.put("errorNoDiv", "error");

						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);

//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					} else {
						pagingAction(req,size);
						list = service.showBankBookByDeposit(customer, startRow, paging.getPageSize());
						req.setAttribute("list", list);
						Map<String,Object> map = new HashMap<>();
						map.put("list", list);
						map.put("paging", this.paging);

						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);

//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					}
					break;
				case "적금":
					customer = new Customer();
					customer.setCustDiv(custdiv.equals("0") ? false : true);
					list = service.showBankBookBySaving(customer);
					size = list.size();
					if (list.size() == 0) {
						HashMap<String, String> map = new HashMap<>();
						map.put("errorNoDiv", "error");

						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);

//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					} else {
						pagingAction(req,size);
						list = service.showBankBookBySaving(customer, startRow, paging.getPageSize());
						req.setAttribute("list", list);
						Map<String,Object> map = new HashMap<>();
						map.put("list", list);
						map.put("paging", this.paging);

						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);
//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					}
					break;
				case "마이너스":
					customer = new Customer();
					customer.setCustDiv(custdiv.equals("0") ? false : true);
					list = service.showBankBookByMinus(customer);
					size = list.size();
					if (list.size() == 0) {
						HashMap<String, String> map = new HashMap<>();
						map.put("errorNoDiv", "error");
						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);

//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					} else {
						pagingAction(req,size);
						list = service.showBankBookByMinus(customer, startRow, paging.getPageSize());
						req.setAttribute("list", list);
						Map<String,Object> map = new HashMap<>();
						map.put("list", list);
						map.put("paging", this.paging);

						Jsonb om = JsonbBuilder.create();
						String json = om.toJson(map);
//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);
						res.setContentType("application/json;charset=UTF-8");
						PrintWriter out = res.getWriter();
						out.write(json);
						out.flush();
					}
					break;
				default:
					HashMap<String, String> map = new HashMap<>();
					map.put("errorBankBookName", "error");

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
	private void setAttrAjax(HttpServletRequest req, String search, String searchdiv, String div, List<BankBook> list) {
		req.setAttribute("list", list);
		req.setAttribute("paging", paging);
		req.setAttribute("custdiv", div);
		req.setAttribute("pagingAjax", "true");
		req.setAttribute("search", search);
		req.setAttribute("searchdiv", searchdiv);
	}
	private void setAttrNormal(HttpServletRequest req, String div, List<BankBook> list) {
		req.setAttribute("list", list);
		req.setAttribute("custdiv", div);
		req.setAttribute("paging", paging);
		req.setAttribute("pagingAjax", "false");
	}
	private void pagingAction(HttpServletRequest req, int size) {
		page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
		startRow = 0;
		paging = new Paging();
		paging.makePaging();
		paging.setPageNo(page); // get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
		paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
		paging.setTotalCount(size);

		// 첫번째 row 계산
		if (paging.getPageNo() == 1) {
			// 현재 페이지가 1이면 0부터 10개 리스트 불러옴
			startRow = 0;
		} else {
			// 현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
			startRow = (paging.getPageNo() - 1) * 10;
		}
	}

}
