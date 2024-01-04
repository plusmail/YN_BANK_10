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
import jakarta.servlet.http.Part;

public class empAddHandler implements CommandHandler {
    private EmployeeUIService service;

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

        if (req.getMethod().equalsIgnoreCase("get")) {


            return "/WEB-INF/view/emp/empForm.jsp";

        } else if (req.getMethod().equalsIgnoreCase("post")) {

            String uploadPath = req.getServletContext().getRealPath("empPic");
            File dir = new File(uploadPath);
            if (!dir.exists()) {
                dir.mkdir();
            }

            for (Part part : req.getParts()) {
                String fileName = extractFileName(part);
                if (fileName != null && !fileName.isEmpty()) {
                    part.write(uploadPath + File.separator + fileName);
                }
            }

            res.getWriter().println("File(s) uploaded successfully!");


            try {

                Employee emp = new Employee(
                        req.getParameter("empCode"),
                        req.getParameter("empName"),
                        req.getParameter("empTitle"),
                        req.getParameter(null),
                        Integer.parseInt(req.getParameter("empSalary")),
                        req.getParameter("empTel"),
                        req.getParameter("empId"),
                        req.getParameter("empPwd"),
                        new Department(Integer.parseInt(req.getParameter("deptNo"))),
                        req.getPart("pic").getSubmittedFileName());

                //
                //System.out.println(emp);
                service = new EmployeeUIService();
                service.addEmp(emp);
                HttpSession session = req.getSession();
                session.setAttribute("successedForAdd", "success");
                session.setAttribute("addedEmp", emp.getEmpName());


            } catch (Exception e) {
                e.printStackTrace();
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
