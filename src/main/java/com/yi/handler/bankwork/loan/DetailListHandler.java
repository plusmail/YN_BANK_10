package com.yi.handler.bankwork.loan;

import java.net.URLEncoder;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.Customer;
import com.yi.dto.Repayment;
import com.yi.handler.paging.Paging;
import com.yi.mvc.CommandHandler;
import com.yi.service.LoanService;

public class DetailListHandler implements CommandHandler {
	private LoanService service = new LoanService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
			String loanAccountNum = req.getParameter("loanaccountnum");
			String custName = req.getParameter("custname");
			String custDiv = req.getParameter("custdiv");
			Customer cust = new Customer();
			cust.setCustName(custName);
			cust.setCustDiv(custDiv.equals("0")?false:true);
			Repayment repayment = new Repayment();
			repayment.setLoanAccountNum(loanAccountNum);
			repayment.setCust(cust);
			List<Repayment> list = service.searchRepaymentsByAccountNumAndCustDiv(repayment);
			if(list.size()==0) {
				HttpSession session = req.getSession();
				session.setAttribute("nonlist", "error");
				custName = URLEncoder.encode(custName, "UTF-8");
				res.sendRedirect(req.getContextPath() + "/bankwork/loan/detail.do?loanaccountnum="+loanAccountNum + "&custname="+custName);
			}
			else {
				if(custDiv.equals("0")) {
					int size = list.size();
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
					list = service.searchRepaymentsByAccountNumAndCustDiv(repayment, startRow, paging.getPageSize());
					req.setAttribute("list", list);
					req.setAttribute("paging", paging); 
					return "/WEB-INF/view/bankwork/loan/loanDetailListNormal.jsp";
				}
				else {
					int size = list.size();
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
					list = service.searchRepaymentsByAccountNumAndCustDiv(repayment, startRow, paging.getPageSize());
					req.setAttribute("list", list);
					req.setAttribute("paging", paging); 
					return "/WEB-INF/view/bankwork/loan/loanDetailListBusiness.jsp";
				}
				
			}
			return null;
	}
}
