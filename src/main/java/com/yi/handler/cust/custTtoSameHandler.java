package com.yi.handler.cust;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.BankBook;
import com.yi.dto.Customer;
import com.yi.mvc.CommandHandler;
import com.yi.service.BankBookService;
import com.yi.service.CustomerService;

public class custTtoSameHandler implements CommandHandler {
    private CustomerService service = new CustomerService();
    private BankBookService bankService = new BankBookService();


    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (req.getMethod().equalsIgnoreCase("get")) {
            String custCode = req.getParameter("custCode");
            String dw = req.getParameter("dw");
            String accountNum = req.getParameter("accountNum");
            List<Customer> listBal = service.showCustomerBankInfoByAcc(accountNum);
            Customer custBal = listBal.get(0);
            req.setAttribute("accountNum", accountNum);
            req.setAttribute("custBal", custBal);
            req.setAttribute("dw", dw);

            return "/WEB-INF/view/cust/custTransferFormToSame.jsp";
        } else if (req.getMethod().equalsIgnoreCase("post")) {
            String findAccNum = req.getParameter("targetAccNum");


            try {
                BankBook bankBook = bankService.showOneBankBook(findAccNum);
                //System.out.println("통장정보임마"+bankBook);
                if (bankBook != null) {
                    String custName = bankBook.getCustCode().getCustName();
                    HashMap<String, String> map = new HashMap<>();
                    map.put("successs", "existAccount");
                    map.put("targetCustName", custName);

                    Jsonb om = JsonbBuilder.create();
                    String json = om.toJson(map);
//                    ObjectMapper om = new ObjectMapper();
//                    String json = om.writeValueAsString(map);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.write(json);
                    out.flush();
                } else {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("error", "notExist");
                    Jsonb om = JsonbBuilder.create();
                    String json = om.toJson(map);
//                    ObjectMapper om = new ObjectMapper();
//                    String json = om.writeValueAsString(map);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.write(json);
                    out.flush();
                }

            } catch (Exception e) {
                //e.printStackTrace();
                HashMap<String, String> map = new HashMap<>();
                map.put("error", "notExist");
                Jsonb om = JsonbBuilder.create();
                String json = om.toJson(map);
//                ObjectMapper om = new ObjectMapper();
//                String json = om.writeValueAsString(map);
                res.setContentType("application/json;charset=UTF-8");
                PrintWriter out = res.getWriter();
                out.write(json);
                out.flush();
            }


        }
        return null;
    }

}
