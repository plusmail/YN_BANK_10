package com.yi.handler.emp;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Employee;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empBonusDetailHandler implements CommandHandler {
    private EmployeeUIService service = new EmployeeUIService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String empCode = req.getParameter("empCode");
		String bonus = req.getParameter("bonus");
		//System.out.println(bonus);
		
		try{Employee emp = service.showPickedEmp2(empCode);
		
		//System.out.println("보너스디테일"+emp);
		List<Employee> list = service.showDetailEmpPerformance(empCode);
		//System.out.println("리스트 사이즈"+list.size());
		//System.out.println("보너스디테일"+list);
				req.setAttribute("list", list);
				req.setAttribute("emp", emp);
				req.setAttribute("bonus", bonus);
		
		
	}catch (Exception e) {
		e.printStackTrace();
	}
  
		return "/WEB-INF/view/emp/empBonusDetail.jsp";
	}
}             
