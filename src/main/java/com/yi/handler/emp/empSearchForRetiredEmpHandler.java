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

public class empSearchForRetiredEmpHandler implements CommandHandler {
    private EmployeeUIService service = new EmployeeUIService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (req.getMethod().equalsIgnoreCase("get")) {
            List<Employee> list = service.showRetiredEmpList();
            //		System.out.println(list);

            req.setAttribute("list", list);

            //각 부서별 사람 수 구하기
            //인사팀
            List<Employee> listForExistHR = service.showPickedEmpByDept("인사", 1);
            List<Employee> listForExistCS = service.showPickedEmpByDept("고객", 1);

            req.setAttribute("HR", listForExistHR.size());
            req.setAttribute("CS", listForExistCS.size());
            return "/WEB-INF/view/emp/empSearchForRetiredEmp.jsp";
        } else if (req.getMethod().equalsIgnoreCase("post")) {
            String search = req.getParameter("search");
            String div = req.getParameter("div");


            switch (div) {

                case "사원번호":
                    Employee emp = service.showPikedEmpByCode(search, 1);
                    if (emp == null) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("error", "notExist");
                        Jsonb om = JsonbBuilder.create();
                        String json = om.toJson(map);
//						ObjectMapper om = new ObjectMapper();
//						String json = om.writeValueAsString(map);

                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = res.getWriter();
                        out.write(json);
                        out.flush();
                        break;
                    }

                    List<Employee> list = new ArrayList<Employee>();
                    list.add(emp);
                    Jsonb om = JsonbBuilder.create();
                    String json = om.toJson(list);
//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(list);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.write(json);
                    out.flush();

                    break;

                case "사원이름":
                    List<Employee> list2 = new ArrayList<Employee>();
                    list2 = service.showPickedEmpList(search, 1);
                    if (list2.size() == 0) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("error", "notExist");
                        Jsonb om2 = JsonbBuilder.create();
                        String json2 = om2.toJson(map);

//						ObjectMapper om2 = new ObjectMapper();
//						String json2 = om2.writeValueAsString(map);
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out2 = res.getWriter();
                        out2.write(json2);
                        out2.flush();
                        break;
                    }
                    Jsonb om2 = JsonbBuilder.create();
                    String json2 = om2.toJson(list2);
//					ObjectMapper om2 = new ObjectMapper();
//					String json2 = om2.writeValueAsString(list2);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out2 = res.getWriter();
                    out2.write(json2);
                    out2.flush();

                    break;

                case "부서(인사 or 고객)":
                    List<Employee> list3 = new ArrayList<Employee>();
                    list3 = service.showPickedEmpByDept(search, 1);
                    if (list3.size() == 0) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("error", "notExist");
                        Jsonb om3 = JsonbBuilder.create();
                        String json3 = om3.toJson(map);
//
//						ObjectMapper om3 = new ObjectMapper();
//						String json3 = om3.writeValueAsString(map);
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out3 = res.getWriter();
                        out3.write(json3);
                        out3.flush();
                        break;
                    }
                    Jsonb om3 = JsonbBuilder.create();
                    String json3 = om3.toJson(list3);
//					ObjectMapper om3 = new ObjectMapper();
//					String json3 = om3.writeValueAsString(list3);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out3 = res.getWriter();
                    out3.write(json3);
                    out3.flush();
                    break;

                case "직급":
                    List<Employee> list4 = new ArrayList<Employee>();
                    list4 = service.showPickedEmpByTitle(search, 1);
                    if (list4.size() == 0) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("error", "notExist");

                        Jsonb om4 = JsonbBuilder.create();
                        String json4 = om4.toJson(map);

//						ObjectMapper om4 = new ObjectMapper();
//						String json4 = om4.writeValueAsString(map);
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out4 = res.getWriter();
                        out4.write(json4);
                        out4.flush();
                        break;
                    }

                    Jsonb om4 = JsonbBuilder.create();
                    String json4 = om4.toJson(list4);
//					ObjectMapper om4 = new ObjectMapper();
//					String json4 = om4.writeValueAsString(list4);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out4 = res.getWriter();
                    out4.write(json4);
                    out4.flush();
                    break;

            }

        }


        return null;
    }

}             
