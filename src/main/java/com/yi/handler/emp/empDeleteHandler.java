package com.yi.handler.emp;

import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import org.codehaus.jackson.map.ObjectMapper;

//import com.oreilly.servlet.MultipartRequest;
//import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;
import com.yi.dto.Department;
import com.yi.dto.Employee;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empDeleteHandler implements CommandHandler {
     private EmployeeUIService service = new EmployeeUIService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

//		if(req.getMethod().equalsIgnoreCase("get")) {
//			return "/WEB-INF/view/emp/empFormForAuthUpdate.jsp";
//			
//		}else if(req.getMethod().equalsIgnoreCase("post")) {
			
		
			
          try { 
        	    String empCode = req.getParameter("no");   
		        Employee emp = service.showPickedEmp2(empCode);
		        
			
				service.changeStatus(emp);
				HttpSession session = req.getSession();
				session.setAttribute("successed", "success");
				session.setAttribute("deletedEmp", emp.getEmpName());
	
			}catch (Exception e) {
			   e.printStackTrace();
			}
			
			
/*			res.sendRedirect(req.getContextPath()+"/emp/empAuth.do");
			return null;*/
//		}
		
		res.sendRedirect("empSearch.do");
		return null;
	}

}
