package com.yi.handler.emp;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.yi.dto.Department;
import com.yi.dto.Employee;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empAuthUpdateHandler implements CommandHandler {
     private EmployeeUIService service = new EmployeeUIService();
	@Override
	public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

		if(req.getMethod().equalsIgnoreCase("get")) {
			return "/WEB-INF/view/emp/empFormForAuthUpdate.jsp";
			
		}else if(req.getMethod().equalsIgnoreCase("post")) {
			
		
			
          try { 
        	    String empCode = req.getParameter("empCode");   
		        String empName = req.getParameter("empName");
			    String empTitle = req.getParameter("empTitle");
			    String empAuth = req.getParameter("empAuth");
			    Department dept = new Department(Integer.parseInt(req.getParameter("deptNo")));
				dept.setDeptName(dept.getDeptNo()==1?"인사":"고객");
				
				
				//System.out.println("pic은"+pic);
				
				Employee dbEmp = service.showPickedEmp2(empCode);
				dbEmp.setEmpName(empName);
				dbEmp.setEmpAuth(empAuth);
//				System.out.println("db상의 데이터"+dbEmp);
//				String dbPic = dbEmp.getPic();
//				String empPwd = dbEmp.getEmpPwd();
//				System.out.println("dbPic은"+dbPic);

			
				service.modifyEmpAuth(dbEmp);
				HttpSession session = req.getSession();
				session.setAttribute("successed", "success");
				session.setAttribute("authEmpName",dbEmp.getEmpName());
				
	//			System.out.println("권한 수정되고 dbEmp"+dbEmp);
			}catch (Exception e) {
			   e.printStackTrace();
			}
			
			
			res.sendRedirect(req.getContextPath()+"/emp/empAuth.do");
			return null;
		}
		
		
		
		return null;
	}

}
