package com.yi.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.yi.dao.LoanDao;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.dto.Loan;
import com.yi.dto.Plan;
import com.yi.dto.Repayment;
import com.yi.service.LoanService;

public class LoanDaoImpl implements LoanDao {
	private static final LoanDaoImpl instance = new LoanDaoImpl();
	private LoanDaoImpl() {};
	String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
	public static LoanDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<Loan> showLoans() throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getLoan(rs));
			}
		}
		return list;
	}

	private Loan getLoan(ResultSet rs) throws SQLException {
		String loanAccountNum = rs.getString("l.loanaccountnum");
		Customer custCode = new Customer();
		custCode.setCustName(rs.getString("c.custname"));
		Plan planCode = new Plan();
		planCode.setPlanName(rs.getString("p.planname"));
		Date loanStartDate = rs.getTimestamp("l.loanStartDate");
		Date loanDelayDate = rs.getTimestamp("l.loanDelayDate");
		Date loanExpireDate = rs.getTimestamp("l.loanExpireDate");
		float loanInterest = rs.getFloat("l.loaninterest");
		Long loanBalance = rs.getLong("l.loanbalance");
		String loanMethod = rs.getString("l.loanMethod");
		boolean loanExtended = rs.getBoolean("l.loanExtended");
		return new Loan(loanAccountNum, custCode, planCode, loanStartDate, loanDelayDate, loanExpireDate, loanInterest, loanBalance, loanMethod, loanExtended);
	}
	private Loan getLoanCustDivAndCredit(ResultSet rs) throws SQLException {
		String loanAccountNum = rs.getString("l.loanaccountnum");
		Customer custCode = new Customer();
		custCode.setCustName(rs.getString("c.custname"));
		custCode.setCustDiv(rs.getBoolean("c.custDiv"));
		custCode.setCustCredit(rs.getInt("c.custCredit"));
		Plan planCode = new Plan();
		planCode.setPlanName(rs.getString("p.planname"));
		Date loanStartDate = rs.getTimestamp("l.loanStartDate");
		Date loanDelayDate = rs.getTimestamp("l.loanDelayDate");
		Date loanExpireDate = rs.getTimestamp("l.loanExpireDate");
		float loanInterest = rs.getFloat("l.loaninterest");
		Long loanBalance = rs.getLong("l.loanbalance");
		String loanMethod = rs.getString("l.loanMethod");
		boolean loanExtended = rs.getBoolean("l.loanExtended");
		return new Loan(loanAccountNum, custCode, planCode, loanStartDate, loanDelayDate, loanExpireDate, loanInterest, loanBalance, loanMethod,loanExtended);
	}

	@Override
	public List<Loan> showLoanByCustName(Loan loan) throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where c.custname = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setString(1, loan.getCustCode().getCustName());
				try(ResultSet rs = pstmt.executeQuery()) {
					while(rs.next()) {
						list.add(getLoan(rs));
					}
				}	
		}
		return list;
	}

	@Override
	public int insertLoan(Loan loan) throws SQLException {
		int res = -1;
		String sql = "insert into loan values(?,(select custcode from customer where custname = ?),(select plancode from plan where planname = ?),?,?,?,?,?,?,?,0,(select empcode from employee where empname = ?))";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, loan.getLoanAccountNum());
			pstmt.setString(2, loan.getCustCode().getCustName());
			pstmt.setString(3, loan.getPlanCode().getPlanName());
			pstmt.setTimestamp(4, new Timestamp(loan.getLoanStartDate().getTime()));
			pstmt.setTimestamp(5, new Timestamp(loan.getLoanDelayDate().getTime()));
			pstmt.setTimestamp(6, new Timestamp(loan.getLoanExpireDate().getTime()));
			pstmt.setString(7, loan.getLoanMethod());
			pstmt.setFloat(8, loan.getLoanInterest());
			pstmt.setLong(9, loan.getLoanBalance());
			pstmt.setBoolean(10, loan.isLoanExtended());
			pstmt.setString(11, loan.getEmployee().getEmpName());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateLoanExpireDate(Loan loan) throws SQLException {
		int res = -1;
		String sql = "update loan set loanexpiredate = ?, loanExtended = ? where custcode = (select custcode from customer where custname = ?) and loanaccountnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setTimestamp(1, new Timestamp(loan.getLoanExpireDate().getTime()));
			pstmt.setBoolean(2, true);
			pstmt.setString(3, loan.getCustCode().getCustName());
			pstmt.setString(4, loan.getLoanAccountNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteLoan(Loan loan) throws SQLException {
		int res = -1;
		String sql = "delete from loan where custcode = (select custcode from customer where custname = ?) and loanaccountnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, loan.getCustCode().getCustName());
			pstmt.setString(2, loan.getLoanAccountNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public List<Loan> searchLoanAccountNums(Loan loan) throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where l.loanaccountnum like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%" + loan.getLoanAccountNum() + "%");
			pstmt.setBoolean(2, loan.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getLoan(rs));
				}
			}
		}
		return list;
	}

	@Override
	public List<Loan> searchLoanCustNames(Loan loan) throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where c.custname like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%" + loan.getCustCode().getCustName() + "%");
			pstmt.setBoolean(2, loan.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getLoan(rs));
				}
			}
		}
		return list;
	}

	@Override
	public List<Loan> searchLoanPlanNames(Loan loan) throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where p.planname like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%" + loan.getPlanCode().getPlanName() + "%");
			pstmt.setBoolean(2, loan.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getLoan(rs));
				}
			}
		}
		return list;
	}

	@Override
	public Loan showLoanByLoanAccountNumAndCustName(Loan loan) throws SQLException {
		String sql = "select l.loanAccountNum,c.custName,c.custCredit,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended,c.custDiv from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where l.loanaccountnum = ? and c.custname = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, loan.getLoanAccountNum());
			pstmt.setString(2, loan.getCustCode().getCustName());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					return getLoanCustDivAndCredit(rs);
				}
			}
		}
		return null;
	}

	@Override
	public List<Loan> showLoansNormal() throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where c.custdiv = 0 and l.loanExpired = 0";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getLoan(rs));
			}
		}
		return list;
	}

	@Override
	public List<Loan> showLoansBuisness() throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where custdiv = 1 and l.loanExpired = 0";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getLoan(rs));
			}
		}
		return list;
	}

	@Override
	public List<Repayment> searchRepaymentsByAccountNum(String accountnum) throws SQLException {
		List<Repayment> list = new ArrayList<>();
		String sql = "select loanaccountnum,custname,planname,loanstartdate,loandelaydate,loanexpiredate,loanmethod,loanround,loaninterest,loanbalance,loanrepayment from repayment r join customer c on r.custcode = c.custcode join plan p on r.loanplancode = p.plancode where loanaccountnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, accountnum);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getRepayment(rs));
				}	
			}
		}
		return list;
	}

	private Repayment getRepayment(ResultSet rs) throws SQLException {
		String loanAccountNum = rs.getString("loanaccountnum");
		Customer cust = new Customer();
		cust.setCustName(rs.getString("custname"));
		Plan plan = new Plan();
		plan.setPlanName(rs.getString("planname"));
		Date loanStartDate = rs.getTimestamp("loanstartdate");
		Date loanDelayDate = rs.getTimestamp("loandelaydate");
		Date loanExpireDate = rs.getTimestamp("loanexpiredate");
		String loanMethod = rs.getString("loanmethod");
		int loanRound = rs.getInt("loanround");
		float loanInterest = rs.getFloat("loaninterest");
		long loanBalance = rs.getLong("loanbalance");
		int loanRepayment = rs.getInt("loanrepayment");
		return new Repayment(loanAccountNum, cust, plan, loanStartDate, loanDelayDate, loanExpireDate, loanMethod, loanRound, loanInterest, loanBalance, loanRepayment);
	}

	@Override
	public List<Repayment> searchRepaymentsByAccountNumAndCustDiv(Repayment repayment) throws SQLException {
		List<Repayment> list = new ArrayList<>();
		String sql = "select loanaccountnum,custname,planname,loanstartdate,loandelaydate,loanexpiredate,loanmethod,loanround,loaninterest,loanbalance,loanrepayment from repayment r join customer c on r.custcode = c.custcode join plan p on r.loanplancode = p.plancode where loanaccountnum = ? and c.custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, repayment.getLoanAccountNum());
			pstmt.setBoolean(2, repayment.getCust().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getRepayment(rs));
				}	
			}
		}
		return list;
	}

	@Override
	public int insertRepayment(Repayment repayment) throws SQLException {
		int res = -1;
		String sql = "insert into repayment values(?,(select custcode from customer where custname = ?),(select plancode from plan where planname = ?),?,?,?,?,?,?,?,?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1,repayment.getLoanAccountNum());
			pstmt.setString(2, repayment.getCust().getCustName());
			pstmt.setString(3, repayment.getPlan().getPlanName());
			pstmt.setTimestamp(4, new Timestamp(repayment.getLoanStartDate().getTime()));
			pstmt.setTimestamp(5, new Timestamp(repayment.getLoanDelayDate().getTime()));
			pstmt.setTimestamp(6, new Timestamp(repayment.getLoanExpireDate().getTime()));
			pstmt.setString(7, repayment.getLoanMethod());
			pstmt.setInt(8, repayment.getLoanRound());
			pstmt.setFloat(9, repayment.getLoanInterest());
			pstmt.setLong(10, repayment.getLoanBalance());
			pstmt.setInt(11, repayment.getLoanRepayment());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int insertAndDeleteProcedure(Repayment repayment) throws SQLException {
		int res = -1;
		String sql = "insert into repayment values(?,(select custcode from customer where custname = ?),(select plancode from plan where planname = ?),?,?,?,?,?,?,?,?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver)) {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,repayment.getLoanAccountNum());
			pstmt.setString(2, repayment.getCust().getCustName());
			pstmt.setString(3, repayment.getPlan().getPlanName());
			pstmt.setTimestamp(4, new Timestamp(repayment.getLoanStartDate().getTime()));
			pstmt.setTimestamp(5, new Timestamp(repayment.getLoanDelayDate().getTime()));
			pstmt.setTimestamp(6, new Timestamp(repayment.getLoanExpireDate().getTime()));
			pstmt.setString(7, repayment.getLoanMethod());
			pstmt.setInt(8, repayment.getLoanRound());
			pstmt.setFloat(9, repayment.getLoanInterest());
			pstmt.setLong(10, repayment.getLoanBalance());
			pstmt.setInt(11, repayment.getLoanRepayment());
			res = pstmt.executeUpdate();
			sql = "update loan set loanBalance = ?, loanExpired = ? where custcode = (select custcode from customer where custname = ?) and loanaccountnum = ?";
			pstmt = con.prepareStatement(sql);
			pstmt.setLong(1, 0);
			pstmt.setBoolean(2, true);
			pstmt.setString(3, repayment.getCust().getCustName());
			pstmt.setString(4, repayment.getLoanAccountNum());
			res += pstmt.executeUpdate();
			if(res==2) {
				con.commit();
				return res;
			}
			else {
				con.rollback();
				return res;
			}
		}
	}

	@Override
	public int insertRepaymentByEquityPaymentProcedure(Repayment repayment) throws SQLException {
		int res = -1;
		String sql = "insert into repayment values(?,(select custcode from customer where custname = ?),(select plancode from plan where planname = ?),?,?,?,?,?,?,?,?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver)) {
			con.setAutoCommit(false);
			Calendar calDelay = GregorianCalendar.getInstance();
			Calendar calExpire = GregorianCalendar.getInstance();
			calDelay.setTime(repayment.getLoanDelayDate());
			calExpire.setTime(repayment.getLoanExpireDate());
			LoanService service = new LoanService();
			List<Repayment> list = service.searchRepaymentsByAccountNum(repayment.getLoanAccountNum());
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1,repayment.getLoanAccountNum());
			pstmt.setString(2, repayment.getCust().getCustName());
			pstmt.setString(3, repayment.getPlan().getPlanName());
			pstmt.setTimestamp(4, new Timestamp(repayment.getLoanStartDate().getTime()));
			pstmt.setTimestamp(5, new Timestamp(repayment.getLoanDelayDate().getTime()));
			pstmt.setTimestamp(6, new Timestamp(repayment.getLoanExpireDate().getTime()));
			pstmt.setString(7, repayment.getLoanMethod());
			pstmt.setInt(8, repayment.getLoanRound());
			pstmt.setFloat(9, repayment.getLoanInterest());
			pstmt.setLong(10, repayment.getLoanBalance());
			pstmt.setInt(11, repayment.getLoanRepayment());
			res = pstmt.executeUpdate();
			int monthGap = (calExpire.get(Calendar.YEAR) - calDelay.get(Calendar.YEAR)) * 12;
			sql = "update loan set loanBalance = ? where custcode = (select custcode from customer where custname = ?) and loanaccountnum = ?";
			pstmt = con.prepareStatement(sql);
			long principlePayment = repayment.getLoanBalance() - (list.get(0).getLoanBalance() / monthGap);
			pstmt.setLong(1, principlePayment);
			pstmt.setString(2, repayment.getCust().getCustName());
			pstmt.setString(3, repayment.getLoanAccountNum());
			res += pstmt.executeUpdate();
			if(res==2) {
				con.commit();
				return res;
			}
			else {
				con.rollback();
				return res;
			}
		}
	}

	@Override
	public Loan checkRedunduncyLoanPlan(Loan loan) throws SQLException {
		String sql = "select plancode from performance where custcode = (select custcode from customer where custname = ?) and plancode = (select plancode from plan where planname = ?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, loan.getCustCode().getCustName());
			pstmt.setString(2, loan.getPlanCode().getPlanName());
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					loan = new Loan();
					Plan planCode = new Plan(rs.getString("plancode"));
					loan.setPlanCode(planCode);
					return loan;
				}
			}
		}
		return null;
	}

	@Override
	public List<Loan> showLoansByNormal(int startRow, int endRow) throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where c.custdiv = 0 and l.loanExpired = 0 limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getLoan(rs));
				}
			}
			return list;
		}		
	}

	@Override
	public List<Loan> showLoansByBusiness(int startRow, int endRow) throws SQLException {
		List<Loan> list = new ArrayList<>();
		String sql = "select l.loanAccountNum,c.custName,p.planName,l.loanStartDate,l.loanDelayDate,l.loanExpireDate,l.loanInterest,l.loanBalance,l.loanMethod,l.loanExtended from loan l left join customer c on l.custCode = c.custCode left join plan p on l.loanPlanCode = p.planCode where c.custdiv = 1 and l.loanExpired = 0 limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getLoan(rs));
				}
			}
			return list;
		}
	}

	@Override
	public List<Repayment> searchRepaymentsByAccountNumAndCustDiv(Repayment repayment, int startRow, int endRow) throws SQLException {
		List<Repayment> list = new ArrayList<>();
		String sql = "select loanaccountnum,custname,planname,loanstartdate,loandelaydate,loanexpiredate,loanmethod,loanround,loaninterest,loanbalance,loanrepayment from repayment r join customer c on r.custcode = c.custcode join plan p on r.loanplancode = p.plancode where loanaccountnum = ? and c.custdiv = ? limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, repayment.getLoanAccountNum());
			pstmt.setBoolean(2, repayment.getCust().getCustDiv());
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getRepayment(rs));
				}	
			}
		}
		return list;
	}
}
