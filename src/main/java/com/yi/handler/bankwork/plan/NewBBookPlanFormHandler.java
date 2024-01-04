package com.yi.handler.bankwork.plan;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Plan;
import com.yi.mvc.CommandHandler;
import com.yi.service.PlanService;

public class NewBBookPlanFormHandler implements CommandHandler {
	PlanService service = new PlanService();
	
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		if(req.getMethod().equalsIgnoreCase("get")) {
			String text = req.getParameter("text");
			
			//통장상품 전체 숫자
			int planAnum = service.showPlanA();
			String planA = "A"+String.format("%03d", planAnum+1);
			req.setAttribute("planA", planA);
			String planAwhat = "";   
			
			if(text.equals("예금 통장")) {    
				//예금 통장 숫자
				int planAAnum = service.showPlanAA();
				planAwhat = "AA"+String.format("%03d", planAAnum+1);
			
			}else if(text.equals("적금 통장")) {  
				//적금 통장 숫자
				int planABnum = service.showPlanAB();
				planAwhat = "AB"+String.format("%03d", planABnum+1);
			}else {   
				//마이너스 통장 숫자
				int planACnum = service.showPlanAC();
				planAwhat = "AC"+String.format("%03d", planACnum+1);
				req.setAttribute("planAwhat", planAwhat);   
			}
			req.setAttribute("planAwhat", planAwhat);
			return "/WEB-INF/view/bankwork/plan/newBBookPlanForm.jsp";
		}else {
			String planCode = req.getParameter("planCode");
			String planDetail = req.getParameter("planDetail");
			String planName = req.getParameter("planName");
			String planDesc = req.getParameter("planDesc");
			String planFor = req.getParameter("planFor");
			String planForDetail = req.getParameter("planForDetail");
			String planDiv1;
			String planDiv2;
			String planDiv;
			
			if(planFor.equals("기업 고객용")) {
				planDiv1 = "B";
			}else {
				planDiv1 = "C";
			}
			
			if(planForDetail.equals("VIP 등급용")) {
				planDiv2 = "V";
			}else {
				planDiv2 = "N";
			}
			
			planDiv = planDiv1 + planDiv2;
			
			Plan plan = new Plan(planCode, planDetail, planName, planDesc, planDiv);
			service.addPlan(plan);
			
			res.sendRedirect(req.getContextPath()+"/bankwork/plan/planSearch.do");
		}      
		
		return null;
		
	}
	
		

}
