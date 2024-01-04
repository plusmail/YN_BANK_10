package com.yi.handler.emp;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;

import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

//import org.codehaus.jackson.map.ObjectMapper;

//import com.oreilly.servlet.MultipartRequest;
import com.yi.dto.Department;
import com.yi.dto.Employee;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;
import jakarta.servlet.http.Part;

//@MultipartConfig(location = "empPic", maxFileSize = 1024*1024*10, fileSizeThreshold = 1024*1024, maxRequestSize = 1024*1024*10)
public class empProfileUpdateHandler implements CommandHandler {
    private EmployeeUIService service = new EmployeeUIService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        System.out.println("empProfileUpdate->");
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println("empProfileUpdate->" + paramName);
        }

        //		String uploadPath = req.getServletContext().getRealPath("empPic");
//		File dir = new File(uploadPath);
//		if (!dir.exists()) {
//			dir.mkdir();
//		}
//
//		for (Part part : req.getParts()) {
//			String fileName = extractFileName(part);
//			if (fileName != null && !fileName.isEmpty()) {
//				part.write(uploadPath + File.separator + fileName);
//			}
//		}
//
//		res.getWriter().println("File(s) uploaded successfully!");


        try {
            int deptNo = 0;
            String empCode = req.getParameter("empCode");
            String empName = req.getParameter("empName");
            String empTitle = req.getParameter("empTitle");
            String empAuth = req.getParameter("empAuth");
            int empSalary = Integer.parseInt(req.getParameter("empSalary"));
            String empTel = req.getParameter("empTel");
            String empId = req.getParameter("empId");
            String empPwd = req.getParameter("empPwd");

            if (req.getParameter("deptNo").contentEquals("인사")) {
                deptNo = 1;
            } else {
                deptNo = 2;
            }
            Department dept = new Department(deptNo);
            dept.setDeptName(dept.getDeptNo() == 1 ? "인사" : "고객");
			String pic = req.getPart("pic").getSubmittedFileName();

            //System.out.println("pic은"+pic);

            Employee dbEmp = service.showPickedEmp2(empCode);
            //System.out.println("db상의 데이터"+dbEmp);
            String dbPic = dbEmp.getPic();
            String dbEmpPwd = dbEmp.getEmpPwd();
            //System.out.println("dbPic은"+dbPic);


            //비번을 수정했는지 여부
            if (empPwd.contentEquals("**********")) {
                //System.out.println("별인가요"+ empPwd);
                Employee emp = new Employee(empCode, empName, empTitle, empAuth, empSalary, empTel, empId, dbEmpPwd, dept, pic == null ? dbPic : pic);
                service.modifyEmpExceptForPwd(emp);
                //System.out.println("비번뺀 emp"+emp);
            } else {
                //System.out.println("아닌경우"+empPwd);
                Employee emp = new Employee(empCode, empName, empTitle, empAuth, empSalary, empTel, empId, empPwd, dept, pic == null ? dbPic : pic);
                service.modifyEmp(emp);
                //System.out.println("비번도 바꾼 emp"+emp);
            }

            HttpSession session = req.getSession();
            Employee emp = (Employee) session.getAttribute("Auth");
            emp.setPic(pic);


        } catch (Exception e) {
            e.printStackTrace();
        }
        res.sendRedirect(req.getContextPath() + "/main/main.do");
        return null;
    }
	private String extractFileName(Part part) {
		String contentDisposition = part.getHeader("content-disposition");
		String[] tokens = contentDisposition.split(";");
		for (String token : tokens) {
			if (token.trim().startsWith("filename")) {
				return token.substring(token.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
}
