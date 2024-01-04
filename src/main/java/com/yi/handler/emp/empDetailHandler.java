package com.yi.handler.emp;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.dto.Department;
import com.yi.dto.Employee;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empDetailHandler implements CommandHandler {
	
	private EmployeeUIService service = new EmployeeUIService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
		
		
		
		String empCode = req.getParameter("empCode");  //코드를 뽑아옴 

		System.out.println("emp Detail -> " + empCode);

		Employee emp = service.showPikedEmpByCode(empCode);
	//	System.out.println(emp);
		req.setAttribute("emp", emp);
		
		
		
		return "/WEB-INF/view/emp/empFormForUpdate.jsp";
		
		
	
	}

}
