package com.yi.handler.emp;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Employee;
import com.yi.dto.Plan;
import com.yi.handler.paging.PageMaker;
import com.yi.handler.paging.SearchCriteria;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;
import com.yi.service.PlanService;

public class empRealBonusHandler implements CommandHandler {
	private EmployeeUIService service = new EmployeeUIService();
	private PlanService planService = new PlanService();

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
			List<Employee> list = service.showEmployeeByPerformLimit(cri);
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			pageMaker.setTotalCount(service.getTotalSearchCount(cri));

			req.setAttribute("list", list);
			req.setAttribute("cri", cri);
	
			
			//상품목록
			List<Plan> planList = planService.showPlans();
			req.setAttribute("planList", planList);
			req.setAttribute("pageMaker",pageMaker);	
				
				
				return "/WEB-INF/view/emp/empRealBonusList.jsp";
			}


		return null;
	}

}
