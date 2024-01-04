package com.yi.handler.bankwork.plan;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import org.codehaus.jackson.map.ObjectMapper;

import com.yi.dto.Plan;
import com.yi.handler.paging.Paging;
import com.yi.mvc.CommandHandler;
import com.yi.service.PlanService;

public class PlanSearchHandler implements CommandHandler {
    private PlanService service = new PlanService();

    @Override
    public String process(HttpServletRequest req, HttpServletResponse res) throws Exception {
        if (req.getMethod().equalsIgnoreCase("get")) {

            int totalCount = service.showPlans().size();//전체 게시글 수
            int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

            Paging paging = new Paging();
            paging.makePaging();
            paging.setPageNo(page); //get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
            paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
            paging.setTotalCount(totalCount);

            //첫번째 row 계산
            int startRow;
            if (paging.getPageNo() == 1) {
                //현재 페이지가 1이면 0부터 10개 리스트 불러옴
                startRow = 0;
            } else {
                //현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
                startRow = (paging.getPageNo() - 1) * 10;
            }

            List<Plan> list = service.showPlansLimit(startRow, paging.getPageSize());

            req.setAttribute("list", list);
            req.setAttribute("paging", paging);


            return "/WEB-INF/view/bankwork/plan/planSearch.jsp";
        } else if (req.getMethod().equalsIgnoreCase("post")) {
            String search = req.getParameter("search"); //검색어
            String div = req.getParameter("div"); //검색 조건

            switch (div) {
                case "상품 코드(A)":
                    List<Plan> listAll = service.showPlansByCode(search);

                    if (listAll == null) {
                        HashMap<String, String> map = new HashMap<>();
                        map.put("error", "notExist");
                        Jsonb om = JsonbBuilder.create();
                        String json = om.toJson(map);

//					ObjectMapper om = new ObjectMapper();
//					String json = om.writeValueAsString(map);
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = res.getWriter();
                        out.write(json);
                        out.flush();
                        break;
                    }
                    int totalCount = listAll.size();//전체 게시글 수 = ('A')로 찾은 모든 상품 리스트의 길이
                    int page = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

                    Paging paging = new Paging();
                    paging.makePaging();
                    paging.setPageNo(page); //get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
                    paging.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
                    paging.setTotalCount(totalCount);

                    //첫번째 row 계산
                    int startRow;
                    if (paging.getPageNo() == 1) {
                        //현재 페이지가 1이면 0부터 10개 리스트 불러옴
                        startRow = 0;
                    } else {
                        //현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
                        startRow = (paging.getPageNo() - 1) * 10;
                    }
                    List<Plan> list = service.showPlansLimitByCode(search, startRow, paging.getPageSize());//('A')로 찾은 상품 리스트에서 limit가 있는 리스트

                    //String json = om.writeValueAsString(list);
                    //리스트, 페이징을 같이 가져가기
                    HashMap<String, Object> listPageMap = new HashMap<>();
                    listPageMap.put("paging", paging);
                    listPageMap.put("list", list);

                    Jsonb om = JsonbBuilder.create();
                    String json = om.toJson(listPageMap);

//				ObjectMapper om = new ObjectMapper();
//				String json = om.writeValueAsString(listPageMap);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out = res.getWriter();
                    out.write(json);
                    out.flush();

                    break;
                case "상품 세부코드(AB)":
                    List<Plan> listAll2 = service.showPlansByDetail(search);
                    if (listAll2 == null) {
                        HashMap<String, String> map2 = new HashMap<>();
                        map2.put("error", "notExist");
                        Jsonb om2 = JsonbBuilder.create();
                        String json2 = om2.toJson(map2);
//					ObjectMapper om2 = new ObjectMapper();
//					String json2 = om2.writeValueAsString(map2);
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out2 = res.getWriter();
                        out2.write(json2);
                        out2.flush();
                        break;
                    }

                    int totalCount2 = listAll2.size();//전체 게시글 수 = ('AA')로 찾은 모든 상품 리스트의 길이
                    int page2 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));

                    Paging paging2 = new Paging();
                    paging2.makePaging();   //기본 세팅
                    paging2.setPageNo(page2); //get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
                    paging2.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
                    paging2.setTotalCount(totalCount2);

                    //첫번째 row 계산
                    int startRow2;
                    if (paging2.getPageNo() == 1) {
                        //현재 페이지가 1이면 0부터 10개 리스트 불러옴
                        startRow2 = 0;
                    } else {
                        //현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
                        startRow2 = (paging2.getPageNo() - 1) * 10;
                    }
                    List<Plan> list2 = service.showPlansLimitByDetail(search, startRow2, paging2.getPageSize());//('AA')로 찾은 상품 리스트에서 limit가 있는 리스트

                    //String json = om.writeValueAsString(list);
                    //리스트, 페이징을 같이 가져가기
                    HashMap<String, Object> listPageMap2 = new HashMap<>();
                    listPageMap2.put("paging2", paging2);
                    listPageMap2.put("list2", list2);
                    Jsonb om2 = JsonbBuilder.create();
                    String json2 = om2.toJson(listPageMap2);
//                    ObjectMapper om2 = new ObjectMapper();
//                    String json2 = om2.writeValueAsString(listPageMap2);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out2 = res.getWriter();
                    out2.write(json2);
                    out2.flush();

                    break;
                case "상품 명":
                    List<Plan> listAll3 = service.showPlansByName(search);
                    if (listAll3 == null) {
                        HashMap<String, String> map3 = new HashMap<>();
                        map3.put("error", "notExist");

                        Jsonb om3 = JsonbBuilder.create();
                        String json3 = om3.toJson(map3);

//                        ObjectMapper om3 = new ObjectMapper();
//                        String json3 = om3.writeValueAsString(map3);
                        res.setContentType("application/json;charset=UTF-8");
                        PrintWriter out3 = res.getWriter();
                        out3.write(json3);
                        out3.flush();
                        break;
                    }
                    int totalCount3 = listAll3.size();//전체 게시글 수 = 상품 이름으로 찾은 모든 상품 리스트의 길이
                    int page3 = req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page"));
                    Paging paging3 = new Paging();
                    paging3.makePaging();   //기본 세팅
                    paging3.setPageNo(page3); //get방식의 parameter값으로 반은 page변수, 현재 페이지 번호
                    paging3.setPageSize(10); // 한페이지에 불러낼 게시물의 개수 지정
                    paging3.setTotalCount(totalCount3);

                    //첫번째 row 계산
                    int startRow3;
                    if (paging3.getPageNo() == 1) {
                        //현재 페이지가 1이면 0부터 10개 리스트 불러옴
                        startRow3 = 0;
                    } else {
                        //현재 페이지가 1이 아니면 첫 페이지를 계산해서 불러옴 (10, 20, 30...) 부터 10개 리스트 불러옴
                        startRow3 = (paging3.getPageNo() - 1) * 10;
                    }
                    List<Plan> list3 = service.showPlansLimitByName(search, startRow3, paging3.getPageSize());//('AA')로 찾은 상품 리스트에서 limit가 있는 리스트

                    //String json = om.writeValueAsString(list);
                    //리스트, 페이징을 같이 가져가기
                    HashMap<String, Object> listPageMap3 = new HashMap<>();
                    listPageMap3.put("paging3", paging3);
                    listPageMap3.put("list3", list3);
                    Jsonb om3 = JsonbBuilder.create();
                    String json3 = om3.toJson(listPageMap3);
//                    ObjectMapper om3 = new ObjectMapper();
//                    String json3 = om3.writeValueAsString(listPageMap3);
                    res.setContentType("application/json;charset=UTF-8");
                    PrintWriter out3 = res.getWriter();
                    out3.write(json3);
                    out3.flush();

                    break;
            }
        }
        return null;
    }

}
