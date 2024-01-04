package com.yi.handler.emp;

import com.yi.dto.Employee;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Enumeration;

public class empProfileHandler implements CommandHandler {
    private EmployeeUIService service = new EmployeeUIService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {

        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            System.out.println("empProfile->" + paramName);
        }


        HttpSession session = req.getSession();
        Employee emp = (Employee) session.getAttribute("Auth");
        String empCode = emp.getEmpCode();
        Employee empFor = service.showPickedEmp2(empCode);

        req.setAttribute("emp", empFor);
        return "/WEB-INF/view/emp/empPersonalForm.jsp";
    }
}
