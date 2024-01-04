package com.yi.handler.emp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.yi.mvc.CommandHandler;

public class test implements CommandHandler {

	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
//	    String empName = req.getParameter("empName");
//	    String empTitle = req.getParameter("empTitle");
//	    String empAuth = req.getParameter("empAuth");
//	    int empSalary = Integer.parseInt(req.getParameter("empSalary"));
//	    String empTel = req.getParameter("empTel");
//	    String empId = req.getParameter("empId");
//	    String empPwd = req.getParameter("empPwd");
//		int deptNo = Integer.parseInt(req.getParameter("deptNo"));
//		byte[] pic = req.getParameter("pic").getBytes();
//		
//		Employee emp = new Employee();
//				emp.setEmpCode(empCode);
//				emp.setEmpName(empName);
//				emp.setEmpTitle(empTitle);
//				emp.setEmpAuth(empAuth);
//				emp.setEmpSalary(empSalary);
//				emp.setEmpTel(empTel);
//				emp.setEmpId(empId);
//				emp.setEmpPwd(empPwd);
//				new Department(deptNo);
//				emp.setPic(pic);
		
		
		
		
		
		
		
		/*  $.ajax({
		 	url: "${pageContext.request.contextPath}/emp/empAdd.do",
		    type: "post", 
		    dataType: "json",
		    success : function(res){
		    	if(res.error == "notExist"){
		    		$("input[name='empName']").next().next().next().next().css("display","inline");
		 	    	return false;	
		    	}
		    }

		 }) */
		
		
		return null;
	}

}
