package com.yi.handler.emp;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Employee;
import com.yi.handler.paging.PageMaker;
import com.yi.handler.paging.SearchCriteria;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empBonusHandler implements CommandHandler {
    private EmployeeUIService service = new EmployeeUIService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			int page = req.getParameter("page")==null?1:Integer.parseInt(req.getParameter("page"));
			SearchCriteria cri = new SearchCriteria();
			String searchType = req.getParameter("searchType");
			String keyword = req.getParameter("keyword");
			cri.setPage(page);
			cri.setSearchType(searchType);
			cri.setKeyword(keyword);
			List<Employee> list = service.showEmployeeByPerformLimit(cri);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.getTotalSearchCount(cri));

			req.setAttribute("list", list);
			req.setAttribute("cri", cri);
			
			
//			if(list.size() == service.showExistEmpList().size()) {
//			req.setAttribute("mem1", list.get(0).getEmpCode());
//			req.setAttribute("mem2", list.get(1).getEmpCode());
//			req.setAttribute("mem3", list.get(2).getEmpCode());
//			}
			req.setAttribute("pageMaker",pageMaker);
			
		  
			return "/WEB-INF/view/emp/empBonusList.jsp";
	
			}

		  
		return null;
	}
  
}             
