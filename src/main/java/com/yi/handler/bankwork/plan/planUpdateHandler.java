package com.yi.handler.bankwork.plan;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Plan;
import com.yi.mvc.CommandHandler;
import com.yi.service.PlanService;

public class planUpdateHandler implements CommandHandler {
	PlanService service = new PlanService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String planCode = req.getParameter("planCode");
		String planDetail = req.getParameter("planDetail");
		String planName = req.getParameter("planName");
		String planDesc = req.getParameter("planDesc");
		String planFor = req.getParameter("planFor");
		String planForDetail = req.getParameter("planForDetail");
		String planDiv = "";
		String planDiv1 ="";
		String planDiv2 = "";
		   
		if(planFor.equals("기업 고객용")) {
			planDiv1 = "B";
		}else if(planFor.equals("일반 고객용")) {
			planDiv1 = "C";
		}
		if(planForDetail.equals("VIP 등급용")) {
			planDiv2 = "V";
		}else if(planForDetail.equals("일반 등급용")) {
			planDiv2 = "N";
		}
		
		planDiv = planDiv1 + planDiv2;
		
		Plan plan = new Plan(planCode, planDetail, planName, planDesc, planDiv);
		service.editPlan(plan);  
		
		res.sendRedirect(req.getContextPath()+"/bankwork/plan/planSearch.do");
		return null;
	}

}
