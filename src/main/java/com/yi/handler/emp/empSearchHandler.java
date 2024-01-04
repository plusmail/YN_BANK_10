package com.yi.handler.emp;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Employee;
import com.yi.handler.paging.PageMaker;
import com.yi.handler.paging.SearchCriteria;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empSearchHandler implements CommandHandler {
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
			int HR = service.countMemberByDepartment(1);
			int CS = service.countMemberByDepartment(2);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.getTotalSearchCount(cri));
			int avgSalary = service.avgOfSalary();
			req.setAttribute("list", list);
			req.setAttribute("cri", cri);
			req.setAttribute("avgSal", avgSalary);
			req.setAttribute("HR", HR);
			req.setAttribute("CS", CS);
			req.setAttribute("pageMaker",pageMaker);
			return "/WEB-INF/view/emp/empSearch.jsp";
		}
		return null;
	}

}
