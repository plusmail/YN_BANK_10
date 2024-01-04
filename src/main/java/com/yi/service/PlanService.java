package com.yi.service;

import java.sql.SQLException;
import java.util.List;

import com.yi.dao.PlanDao;
import com.yi.dao.impl.PlanDaoImpl;
import com.yi.dto.Plan;
import com.yi.handler.paging.Paging;

public class PlanService {
	private PlanDao dao;
	
	public PlanService() {
		dao = PlanDaoImpl.getInstance();
	}
	
	public List<Plan> showPlans() throws SQLException{
		return dao.selectPlanAll();
	}   
	
	//상품 리스트 페이징 limit SQL문
	public List<Plan> showPlansLimit(int startRow, int endRow) throws SQLException{
		return dao.selectPlansLimit(startRow, endRow);
	}
	//상품 리스트 페이징 limit SQL문 (코드로 검색)
	public List<Plan> showPlansLimitByCode(String planCode, int startRow, int endRow) throws SQLException{
		return dao.selectPlansLimitByCode(planCode, startRow, endRow);
	}
	//상품 리스트 페이징 limit SQL문 (세부 코드로 검색)
	public List<Plan> showPlansLimitByDetail(String planDetail, int startRow, int endRow) throws SQLException{
		return dao.selectPlansLimitByDetail(planDetail, startRow, endRow);
	}
	
	//상품 리스트 페이징 limit SQL문 (세부 코드로 검색)
		public List<Plan> showPlansLimitByName(String planName, int startRow, int endRow) throws SQLException{
			return dao.selectPlansLimitByName(planName, startRow, endRow);
		}
	
	public List<Plan> showPlansByName(String planName) throws SQLException{
		return dao.selectPlanByName(planName);   
	}
	
	public List<Plan> showPlansByCode(String planCode) throws SQLException{
		return dao.selectPlanByCode(planCode);
	}
	
	public List<Plan> showPlansByDetail(String planDetail) throws SQLException{
		return dao.selectPlanByDetail(planDetail);
	}
	
	public List<String> planExistChk() throws SQLException{
		return dao.planExistChk();
	}

	
	public int addPlan(Plan plan) throws SQLException{
		return dao.insertPlan(plan);
	}
	
	public int editPlan(Plan plan) throws SQLException{
		return dao.updatePlan(plan);
	}
	
	public int removePlan(Plan plan) throws SQLException{
		return dao.deletePlan(plan);
	}
	
	public int showPlanA() throws SQLException{
		return dao.selectPlanA();
	}
	
	public int showPlanB() throws SQLException{
		return dao.selectPlanB();
	}
	
	public int showPlanC() throws SQLException{
		return dao.selectPlanC();
	}
	
	public int showPlanAA() throws SQLException{
		return dao.selectPlanAA();
	}
	public int showPlanAB() throws SQLException{
		return dao.selectPlanAB();
	}
	public int showPlanAC() throws SQLException{
		return dao.selectPlanAC();
	}
	public int showPlanBA() throws SQLException{
		return dao.selectPlanBA();
	}
	public int showPlanBB() throws SQLException{
		return dao.selectPlanBB();
	}
	public int showPlanCA() throws SQLException{
		return dao.selectPlanCA();
	}
	public int showPlanCB() throws SQLException{
		return dao.selectPlanCB();
	}
	public int showPlanCC() throws SQLException{
		return dao.selectPlanCC();
	}
	
}
