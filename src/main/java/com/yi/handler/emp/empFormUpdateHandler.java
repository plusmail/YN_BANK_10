package com.yi.handler.emp;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Employee;
import com.yi.mvc.CommandHandler;
import com.yi.service.EmployeeUIService;

public class empFormUpdateHandler implements CommandHandler {
    private EmployeeUIService service = new EmployeeUIService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (req.getMethod().equalsIgnoreCase("get")) {
            //인사팀 리스트
            List<Employee> listForHR = new ArrayList<>();
            listForHR = service.showPickedEmpByDeptNo(1);
            //고객팀 리스트
            List<Employee> listForCS = new ArrayList<>();
            listForCS = service.showPickedEmpByDeptNo(2);

            //System.out.println("A"+String.format("%03d", listForHR.size()+1));
            req.setAttribute("numHR", "A" + String.format("%03d", listForHR.size() + 1));
            req.setAttribute("numCS", "B" + String.format("%03d", listForCS.size() + 1));
        } else if (req.getMethod().equalsIgnoreCase("post")) {

            try {

                Employee dbEmp = service.forCheckId(req.getParameter("checkEmpId"));
                // System.out.println(dbEmp);
                if (dbEmp != null) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("error", "existId");
                    Jsonb om = JsonbBuilder.create();
                    String json = om.toJson(map);
//				ObjectMapper om = new ObjectMapper();
//				String json = om.writeValueAsString(map);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.write(json);
                    out.flush();
                }

                return null;
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
        return "/WEB-INF/view/emp/empForm.jsp";
    }

}
