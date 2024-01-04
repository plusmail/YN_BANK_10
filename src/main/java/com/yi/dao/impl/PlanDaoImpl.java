package com.yi.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yi.dao.PlanDao;
import com.yi.dto.Plan;

public class PlanDaoImpl implements PlanDao {
	private static final PlanDaoImpl instance = new PlanDaoImpl();
	String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
	public static PlanDaoImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<Plan> selectPlanAll() throws SQLException {
		List<Plan> list = null;
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	private Plan getPlan(ResultSet rs) throws SQLException {
		String planCode = rs.getString("planCode");
		String planDetail = rs.getString("planDetail");
		String planName = rs.getString("planName");
		String planDesc = rs.getString("planDesc");
		String planDiv = rs.getString("planDiv");
		return new Plan(planCode, planDetail, planName, planDesc, planDiv);
	}
	
	
	@Override
	public List<Plan> selectPlanByName(String planName) throws SQLException {
		List<Plan> list = null;
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan where planName like ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, '%'+planName+'%');
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public int insertPlan(Plan plan) throws SQLException {
		String sql = "insert into plan values(?,?,?,?,?);";
		int res = -1;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, plan.getPlanCode());
			pstmt.setString(2, plan.getPlanDetail());
			pstmt.setString(3, plan.getPlanName());
			pstmt.setString(4, plan.getPlanDesc());
			pstmt.setString(5, plan.getPlanDiv());
			
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updatePlan(Plan plan) throws SQLException {
		String sql = "update plan set planDetail=?, planName=?, planDesc=?, planDiv=? where planCode=?";
		int res = -1;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
		//	pstmt.setString(1, plan.getPlanCode());
			pstmt.setString(1, plan.getPlanDetail());
			pstmt.setString(2, plan.getPlanName());
			pstmt.setString(3, plan.getPlanDesc());
			pstmt.setString(4, plan.getPlanDiv());
			pstmt.setString(5, plan.getPlanCode());
			
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deletePlan(Plan plan) throws SQLException {
		String sql = "delete from plan where planCode=?";
		int res = -1;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, plan.getPlanCode());
			
			res = pstmt.executeUpdate();
		}
		
		return res;
	}

	@Override
	public List<String> planExistChk() throws SQLException {
		List<String> list = null;
		String sql = "select planName from plan";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(rs.getString(1));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public int selectPlanA() throws SQLException {
		String sql = "select count(*) from plan where planCode like 'A%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanB() throws SQLException {
		String sql = "select count(*) from plan where planCode like 'B%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanC() throws SQLException {
		String sql = "select count(*) from plan where planCode like 'C%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanAA() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'AA%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanAB() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'AB%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanAC() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'AC%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanBA() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'BA%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanBB() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'BB%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanCA() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'CA%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanCB() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'CB%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public int selectPlanCC() throws SQLException {
		String sql = "select count(*) from plan where planDetail like 'CC%'";
		int result = 0;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
			}
		}
		return result;
	}

	@Override
	public List<Plan> selectPlanByCode(String planCode) throws SQLException {
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan where planCode like ?";
		List<Plan> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, planCode+'%');
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Plan> selectPlanByDetail(String planDetail) throws SQLException {
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan where planDetail like ?";
		List<Plan> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+planDetail+"%");
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Plan> selectPlanByBankBookCustomerNormal() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%A%' and plandiv = 'CN'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByBankBookCustomerVip() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%A%' and plandiv like 'C%'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByBankBookBusinessNormal() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%A%' and plandiv = 'BN'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByBankBookBusinessVip() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%A%' and plandiv like 'B%'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByCardCustomerNormal() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%B%' and plandiv = 'CN'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByCardCustomerVip() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%B%' and plandiv like 'C%'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByCardBusinessNormal() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%B%' and plandiv = 'BN'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByCardBusinessVip() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%B%' and plandiv like 'B%'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByLoanCustomerNormal() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%C%' and plandiv like 'CN'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByLoanCustomerVip() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%C%' and plandiv like 'C%'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByLoanBusinessNormal() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%C%' and plandiv like 'BN'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlanByLoanBusinessVip() throws SQLException {
		List<Plan> list = new ArrayList<>();
		String sql = "select * from plan where plancode like '%C%' and plandiv like 'B%'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);								
				ResultSet rs = pstmt.executeQuery()){
				while(rs.next()) {
					list.add(getPlan(rs));
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
		return list;
	}

	@Override
	public List<Plan> selectPlansLimit(int startRow, int endRow) throws SQLException {
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan limit ?, ?";
		List<Plan> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public List<Plan> selectPlansLimitByCode(String planCode, int startRow, int endRow) throws SQLException {
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan where planCode like ? limit ?, ?";
		List<Plan> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, "%"+planCode+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public List<Plan> selectPlansLimitByDetail(String planDetail, int startRow, int endRow) throws SQLException {
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan where planDetail like ? limit ?, ?";
		List<Plan> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, "%"+planDetail+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public List<Plan> selectPlansLimitByName(String planName, int startRow, int endRow) throws SQLException {
		String sql = "select planCode, planDetail, planName, planDesc, planDiv from plan where planName like ? limit ?, ?";
		List<Plan> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, "%"+planName+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getPlan(rs));
				}while(rs.next());
			}
		}
		return list;
	}

}
