package com.yi.handler.emp;

import java.io.File;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import jakarta.servlet.annotation.MultipartConfig;
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
import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@MultipartConfig(location = "empPic", maxFileSize = 1024*1024*10, fileSizeThreshold = 1024*1024, maxRequestSize = 1024*1024*10)
public class empUpdateHandler implements CommandHandler {
    private EmployeeUIService service = new EmployeeUIService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println("empUpdate->" + paramName);
        }

        if (req.getMethod().equalsIgnoreCase("get")) {
            return "/WEB-INF/view/emp/empFormForUpdate.jsp";

        } else if (req.getMethod().equalsIgnoreCase("post")) {

            String uploadPath = req.getServletContext().getRealPath("empPic");

            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdir();
            }

//            for (Part part : req.getParts()) {
//                String fileName = extractFileName(part);
//                if (fileName != null && !fileName.isEmpty()) {
//                    part.write(uploadPath + File.separator + fileName);
//                }
//            }

            res.getWriter().println("File(s) uploaded successfully!");

            String empCode11 = req.getParameter("empCode");
            String empName11 = req.getParameter("empName");
            //System.out.println("먼저"+empCode11);
            try {
                String empCode = req.getParameter("empCode");
                String empName = req.getParameter("empName");
                String empTitle = req.getParameter("empTitle");
                String empAuth = req.getParameter("empAuth");
                int empSalary = Integer.parseInt(req.getParameter("empSalary"));
                String empTel = req.getParameter("empTel");
                String empId = req.getParameter("empId");
                String empPwd = req.getParameter("empPwd");
                Department dept = new Department(Integer.parseInt(req.getParameter("deptNo")));
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            HttpSession session = req.getSession();
            Employee emp11 = (Employee) session.getAttribute("Auth");
            //	System.out.println(emp11.getEmpCode());
            if (emp11.getEmpCode().equals(empCode11)) {
                emp11.setEmpName(empName11);
                //	System.out.println("일로들어오니??");
                session.setAttribute("empPerson", "empPerson");
                return "/WEB-INF/view/main/mainSection.jsp";
            }

            res.sendRedirect(req.getContextPath() + "/emp/empSearch.do");
            return null;
        }

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
