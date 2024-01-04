package com.yi.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yi.dao.BankBookDao;
import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.dto.Plan;

public class BankBookDaoImpl implements BankBookDao {
	String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
	private static final BankBookDaoImpl instance = new BankBookDaoImpl();
	
	private BankBookDaoImpl() {};
	
	public static BankBookDaoImpl getInstance() {
		return instance;
	}
	@Override
	public List<BankBook> showBankBooks() throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getBankBook(rs));
			}
		}
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByNormal() throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountDormant = 0 and accountTermination = 0 and c.custDiv = 0";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getBankBook(rs));
			}
		}
		return list;
	}

	private BankBook getBankBook(ResultSet rs) throws SQLException {
		String accountNum = rs.getString("accountnum");
		Customer custCode = new Customer(rs.getString("custcode"));
		custCode.setCustName(rs.getString("custname"));
		Plan accountPlanCode = new Plan(rs.getString("plancode"));
		accountPlanCode.setPlanName(rs.getString("planname"));
		Date accountOpenDate = rs.getTimestamp("accountOpenDate");
		float accountInterest = rs.getFloat("accountInterest");
		return new BankBook(accountNum, custCode, accountPlanCode, accountOpenDate, accountInterest);
	}
	
	private BankBook getBankBookCustDiv(ResultSet rs) throws SQLException {
		String accountNum = rs.getString("accountnum");
		Customer custCode = new Customer(rs.getString("custcode"));
		custCode.setCustName(rs.getString("custname"));
		custCode.setCustDiv(rs.getBoolean("custDiv"));
		Plan accountPlanCode = new Plan(rs.getString("plancode"));
		accountPlanCode.setPlanName(rs.getString("planname"));
		Date accountOpenDate = rs.getTimestamp("accountOpenDate");
		float accountInterest = rs.getFloat("accountInterest");
		return new BankBook(accountNum, custCode, accountPlanCode, accountOpenDate, accountInterest);
	}

	@Override
	public List<BankBook> showBankBooksByCustName(BankBook bankbook) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest,c.custDiv from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where c.custname like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%" + bankbook.getCustCode().getCustName() + "%");
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
		return list;
	}

	@Override
	public int insertBankBook(BankBook bankbook) throws SQLException {
		int res = -1;
		String sql = "insert into BankBook values(?,(select custcode from customer where custname = ?),(select plancode from plan where planname = ?),?,?,?,?,?,(select empcode from employee where empname = ?),?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, bankbook.getAccountNum());
			pstmt.setString(2, bankbook.getCustCode().getCustName());
			pstmt.setString(3, bankbook.getAccountPlanCode().getPlanName());
			pstmt.setTimestamp(4, new Timestamp(bankbook.getAccountOpenDate().getTime()));
			pstmt.setFloat(5, bankbook.getAccountInterest());
			pstmt.setLong(6, bankbook.getAccountBalance()==0?0:bankbook.getAccountBalance());
			pstmt.setBoolean(7, bankbook.isAccountDormant());
			pstmt.setBoolean(8, bankbook.isAccountTermination());
			pstmt.setString(9, bankbook.getEmployee().getEmpName());
			pstmt.setBoolean(10, bankbook.isConnectChk());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateBankBook(BankBook bankbook) throws SQLException {
		int res = -1;
		String sql = "update bankbook set accountopendate=?,accountinterest=? where custcode = (select custcode from customer where custname = ?) and accountnum = ? and accountplancode = (select plancode from plan where planname = ?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setTimestamp(1, new Timestamp(bankbook.getAccountOpenDate().getTime()));
			pstmt.setFloat(2, bankbook.getAccountInterest());
			pstmt.setString(3,bankbook.getCustCode().getCustName());
			pstmt.setString(4, bankbook.getAccountNum());
			pstmt.setString(5, bankbook.getAccountPlanCode().getPlanName());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteBankBook(BankBook bankbook) throws SQLException {
		int res = -1;
		String sql = "delete from bankbook where custcode = (select custcode from customer where custname = ?) and accountnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, bankbook.getCustCode().getCustName());
			pstmt.setString(2, bankbook.getAccountNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateBankBalance(Customer customer) throws SQLException {
		String sql = "update BankBook set accountBalance = ? where custCode=(select custCode from customer where custName=?) and accountNum =?";
		int res = -1;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setLong(1,customer.getBankbook().getAccountBalance());
			pstmt.setString(2, customer.getCustName());
			pstmt.setString(3, customer.getBankbook().getAccountNum());
			
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public String showDPTotalAmount() throws SQLException {
			String sql = "select sum(accountBalance) from bankbook where SUBSTRING_INDEX(SUBSTRING_INDEX(accountNum, \'-\', 2), \'-\', -1) = \'11\'";
			String sumDpBalance = null;
			try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()){
				if(rs.next()) {
					sumDpBalance = rs.getString(1);
				}
			}
		return sumDpBalance;
	}

	@Override
	public String showSvTotalAmount() throws SQLException {
		String sql = "select sum(accountBalance) from bankbook where SUBSTRING_INDEX(SUBSTRING_INDEX(accountNum, \'-\', 2), \'-\', -1) = \'12\'";
		String sumDpBalance = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()){
			if(rs.next()) {
				sumDpBalance = rs.getString(1);
			}
		}
	return sumDpBalance;
	}

	@Override
	public String showLoTotalAmount() throws SQLException {
		String sql = "select sum(accountBalance) from bankbook where SUBSTRING_INDEX(SUBSTRING_INDEX(accountNum, \'-\', 2), \'-\', -1) = \'13\'";
		String sumDpBalance = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery()){
			if(rs.next()) {
				sumDpBalance = rs.getString(1);
			}
		}
	return sumDpBalance;
	}

	@Override
	public List<String> showOpenDPMonth() throws SQLException {
		String sql = "select SUBSTRING_INDEX(SUBSTRING_INDEX(accountOpenDate, '-', 2), '-', -1) from bankbook where SUBSTRING_INDEX(SUBSTRING_INDEX(accountNum, '-', 2), '-', -1) =\'11\'";
		List<String> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			ResultSet rs = pstmt.executeQuery();
			
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
	public List<String> showOpenSvMonth() throws SQLException {
		String sql = "select SUBSTRING_INDEX(SUBSTRING_INDEX(accountOpenDate, '-', 2), '-', -1) from bankbook where SUBSTRING_INDEX(SUBSTRING_INDEX(accountNum, '-', 2), '-', -1) =\'12\'";
		List<String> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			ResultSet rs = pstmt.executeQuery();
			
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
	public List<String> showOpenLoMonth() throws SQLException {
		String sql = "select SUBSTRING_INDEX(SUBSTRING_INDEX(accountOpenDate, '-', 2), '-', -1) from bankbook where SUBSTRING_INDEX(SUBSTRING_INDEX(accountNum, '-', 2), '-', -1) =\'13\'";
		List<String> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			ResultSet rs = pstmt.executeQuery();
			
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
	public List<String> showDepositMonth() throws SQLException {
		String sql = "select  SUBSTRING_INDEX(SUBSTRING_INDEX(accountTransDate, '-', 2), '-', -1) from cust_dw_audit where dw =?";
		List<String> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "입금");
			ResultSet rs = pstmt.executeQuery();
			
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
	public List<String> showWithDrawalMonth() throws SQLException {
		String sql = "select  SUBSTRING_INDEX(SUBSTRING_INDEX(accountTransDate, '-', 2), '-', -1) from cust_dw_audit where dw =?";
		List<String> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "출금");
			ResultSet rs = pstmt.executeQuery();
			
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
	public List<BankBook> showBankBooksByAccountNum(BankBook bankbook) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where b.accountnum like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%"+bankbook.getAccountNum()+"%");
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByPlanName(BankBook bankbook) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where p.planname like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%"+bankbook.getAccountPlanCode().getPlanName()+"%");
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByDeposit(Customer customer) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountnum like '%-11-%' and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
			return list;
		}
	}

	@Override
	public List<BankBook> showBankBooksBySaving(Customer customer) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountnum like '%-12-%' and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
			return list;
		}
	}

	@Override
	public List<BankBook> showBankBooksByMinus(Customer customer) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountnum like '%-13-%' and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
			return list;
		}
	}

	@Override
	public int updateCardBalance(Customer customer) throws SQLException {
		int res = -1;
		String sql = "update Card set CardBalance = ? where custCode=? and accountNum =?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareCall(sql)) {
			pstmt.setLong(1, customer.getBankbook().getAccountBalance());
			pstmt.setString(2, customer.getCustCode());
			pstmt.setString(3, customer.getBankbook().getAccountNum());
			System.out.println(pstmt + " pstmt");
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public List<BankBook> showBankBookByIsConnect(Card card) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = null;
		if(card.getCustCode().getCustCode()!=null) {
			sql = "select * from bankbook_deposit_connect_to_card_info where custcode = ?";
			try(Connection con = DriverManager.getConnection(jdbcDriver);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setString(1, card.getCustCode().getCustCode());
				try(ResultSet rs = pstmt.executeQuery()) {
					while(rs.next()) {
						list.add(getBankBookConnect(rs));
					}
				}
				
			}
			return list;
		}
		else {
			sql = "select * from bankbook_deposit_connect_to_card_info where custcode = (select custcode from customer where custname = ?)";
			try(Connection con = DriverManager.getConnection(jdbcDriver);
					PreparedStatement pstmt = con.prepareStatement(sql)) {
				pstmt.setString(1, card.getCustCode().getCustName());
				try(ResultSet rs = pstmt.executeQuery()) {
					while(rs.next()) {
						list.add(getBankBookConnect(rs));
					}
				}
				
			}
			return list;
		}		
	}

	private BankBook getBankBookConnect(ResultSet rs) throws SQLException {
		String accountNum = rs.getString("accountnum");
		Customer custCode = new Customer(rs.getString("custcode"));
		boolean connectChk = rs.getInt("connectChk")==0?false:true;
		return new BankBook(accountNum, custCode, connectChk);
	}
	@Override
	public int updateConnectChk(Card card) throws SQLException {
		int res = -1;
		String sql = "update bankbook set connectchk = ? where custcode = (select custcode from customer where custname = ?) and accountnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, card.getBankbook().isConnectChk());
			pstmt.setString(2, card.getCustCode().getCustName());
			pstmt.setString(3, card.getBankbook().getAccountNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateCardBalanceByAccountBalance(Card card) throws SQLException {
		int res = -1;
		String sql = "update card set cardbalance = (select accountbalance from bankbook where accountnum = ?) where cardnum = ? and custcode = (select custcode from customer where custname = ?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1,card.getBankbook().getAccountNum());
			pstmt.setString(2, card.getCardNum());
			pstmt.setString(3, card.getCustCode().getCustName());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public BankBook showBankBookByCustNameAndAccountNum(BankBook bankbook) throws SQLException {
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest,c.custDiv from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where b.accountnum = ? and c.custname = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, bankbook.getAccountNum());
			pstmt.setString(2, bankbook.getCustCode().getCustName());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					return getBankBookCustDiv(rs);
				}
			}
		}
		return null;
	}

	@Override
	public int changeBankBookDormant(BankBook bankbook) throws SQLException {
		int res = -1;
		String sql = "update bankbook set accountDormant = ? where custcode = (select custcode from customer where custname = ?) and accountplancode = (select plancode from plan where planname = ?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver)) {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setBoolean(1, bankbook.isAccountDormant());
			pstmt.setString(2, bankbook.getCustCode().getCustName());
			pstmt.setString(3, bankbook.getAccountPlanCode().getPlanName());
			res = pstmt.executeUpdate();
			sql = "update bankbook set accountnum = ? where custcode = (select custcode from customer where custname = ?) and accountplancode = (select plancode from plan where planname = ?)";
			pstmt = con.prepareStatement(sql);
			String accountNum = bankbook.getAccountNum();
			accountNum = accountNum.replace("-1", "-2");
			bankbook.setAccountNum(accountNum);
			pstmt.setString(1, bankbook.getAccountNum());
			pstmt.setString(2, bankbook.getCustCode().getCustName());
			pstmt.setString(3, bankbook.getAccountPlanCode().getPlanName());
			res += pstmt.executeUpdate();
			if(res==2) {
				con.commit();
			}
			else {
				con.rollback();
			}
		}
		return res;
	}

	@Override
	public int changeBankBookTermination(BankBook bankbook) throws SQLException {
		int res = -1;
		BankBook isConnect = new BankBook();
		String sql = "select connectchk from bankbook where accountnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver)) {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setString(1, bankbook.getAccountNum());
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					isConnect.setConnectChk(rs.getBoolean("connectchk"));
				}
			}
			if(isConnect.isConnectChk()) {
				sql = "delete from card where accountnum = ?";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, bankbook.getAccountNum());
				res = pstmt.executeUpdate();
				res += makeTermination(bankbook, con);
				if(res==3) {
					con.commit();
				}
				else {
					con.rollback();
				}
			}
			else {
				res = makeTermination(bankbook, con);
				if(res==2) {
					con.commit();
				}
				else {
					con.rollback();
				}
			}
		}
		return res;
	}

	private int makeTermination(BankBook bankbook, Connection con) throws SQLException {
		int res;
		String sql;
		PreparedStatement pstmt;
		sql = "update bankbook set accountTermination = ? where custcode = (select custcode from customer where custname = ?) and accountplancode = (select plancode from plan where planname = ?)";
		pstmt = con.prepareStatement(sql);
		pstmt.setBoolean(1, bankbook.isAccountTermination());
		pstmt.setString(2, bankbook.getCustCode().getCustName());
		pstmt.setString(3, bankbook.getAccountPlanCode().getPlanName());
		res = pstmt.executeUpdate();
		sql = "update bankbook set accountnum = ? where custcode = (select custcode from customer where custname = ?) and accountplancode = (select plancode from plan where planname = ?)";
		pstmt = con.prepareStatement(sql);
		String accountNum = bankbook.getAccountNum();
		accountNum = accountNum.replace("-1", "-3");
		bankbook.setAccountNum(accountNum);
		pstmt.setString(1, bankbook.getAccountNum());
		pstmt.setString(2, bankbook.getCustCode().getCustName());
		pstmt.setString(3, bankbook.getAccountPlanCode().getPlanName());
		res += pstmt.executeUpdate();
		return res;
	}

	@Override
	public List<BankBook> showBankBooksByBusiness() throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountDormant = 0 and accountTermination = 0 and c.custDiv = 1";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getBankBook(rs));
			}
		}
		return list;
	}

	@Override
	public void update_balance_locking(int amount, String accountNum, String text) throws SQLException {
		String sql = "call pro_update_balance_locking_commit(?, ?, ?);";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setInt(1, amount);
			pstmt.setString(2, accountNum);
			pstmt.setString(3, text);
			pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<BankBook> showBankBookByDormant(BankBook bankbook) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where b.accountDormant = ? and c.custDiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, bankbook.isAccountDormant());
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
				
			}
		}
		return list;
	}

	@Override
	public List<BankBook> showBankBookByTermination(BankBook bankbook) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where b.accountTermination = ? and c.custDiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, bankbook.isAccountTermination());
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
		return list;
	}

	@Override
	public long[] TotalPlanTransAmountYearly() throws SQLException {
		long[] TotalPlanAmount = null;
		String sql = "select\r\n" + 
				"(select ifnull(sum(accountbalance),0) from bankbook where accountnum like '%-11-%' and month(accountopendate) = 4) as 'deposit',\r\n" + 
				"(select ifnull(sum(accountbalance),0) from bankbook where accountnum like '%-12-%' and month(accountopendate) = 4) as 'saving', \r\n" + 
				"(select ifnull(sum(accountbalance),0) from bankbook where accountnum like '%-13-%' and month(accountopendate) = 4) as 'minus', \r\n" + 
				"(select ifnull(sum(cardBalance),0) from card where cardnum like '%331%' and month(cardissuedate) = 4) as 'checkcard', \r\n" + 
				"(select ifnull(sum(cardLimit),0) from card where cardnum like '%332%' and month(cardissuedate) = 4) as 'creditcard', \r\n" + 
				"(select ifnull(sum(loanBalance),0) from loan where loanAccountNum like '%-11-%' and month(loanstartdate) = 4) as 'normalloan',\r\n" + 
				"(select ifnull(sum(loanBalance),0) from loan where loanAccountNum like '%-12-%' and month(loanstartdate) = 4) as 'creditloan',\r\n" + 
				"(select ifnull(sum(loanBalance),0) from loan where loanAccountNum like '%-13-%' and month(loanstartdate) = 4) as 'cardloan'";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				TotalPlanAmount = getToalPlanTransAmount(rs);
			}
		}
		return TotalPlanAmount;
	}

	private long[] getToalPlanTransAmount(ResultSet rs) throws SQLException {
		long deposit = rs.getLong("deposit");
		long saving = rs.getLong("saving");
		long minus = rs.getLong("minus");
		long checkcard = rs.getLong("checkcard");
		long creditcard = rs.getLong("creditcard");
		long normalloan = rs.getLong("normalloan");
		long creditloan = rs.getLong("creditloan");
		long cardloan = rs.getLong("cardloan");
		return new long[]{deposit,saving,minus,checkcard,creditcard,normalloan,creditloan,cardloan};
	}
	@Override
	public Long showAccBalanceByCodeAccNum(Customer customer) throws SQLException {
		String sql = "select accountBalance from bankbook where custCode= ? and accountNum = ?";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);) {
			pstmt.setString(1, customer.getCustCode());
			pstmt.setString(2, customer.getBankbook().getAccountNum());
			rs = pstmt.executeQuery();
			if(rs.next()) {
				Long balance = rs.getLong(1);
				return balance;
			}
		return null;
		}
		
	}

	//송금을 위함 
	@Override
	public BankBook findBankBook(String accountNum) throws SQLException {
		String sql="select b.accountNum, b.accountBalance, c.custCode,c.custName,p.planCode,p.planName from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where b.accountNum =?";
		BankBook bankBook = new BankBook();
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, accountNum);
			
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					bankBook = getOneBankBook(rs);
				}
			}
			
		}
		return bankBook;
	}

	private BankBook getOneBankBook(ResultSet rs) throws SQLException {
		String accountNum = rs.getString("b.accountNum");
		Customer cust= new Customer(rs.getString("c.custCode"));
		cust.setCustName(rs.getString("c.custName"));
		Plan plan = new Plan(rs.getString("p.planCode"));
		plan.setPlanName(rs.getString("p.planName"));
		long accountBalance = rs.getLong("b.accountBalance");
		return new BankBook(accountNum, cust, plan, accountBalance);
	}

	//계좌에서 인출한 뒤 입금시키기  //앞이 출금계좌 뒤가 입금계좌
	@Override
	public int changeBankBookBalance(BankBook bankBook, BankBook bankBook2, int fromto) throws SQLException {
		int res = -1;
		String sql ="update bankbook set accountBalance = accountBalance-? where accountNum =?";
			try(Connection con = DriverManager.getConnection(jdbcDriver)) {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fromto);
			pstmt.setString(2, bankBook.getAccountNum());
			res = pstmt.executeUpdate();
			sql = "update bankbook set accountBalance = accountBalance+? where accountNum =?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fromto);
			pstmt.setString(2, bankBook2.getAccountNum());
			
			res += pstmt.executeUpdate();
			if(res==2) {
				con.commit();
			}
			else {
				con.rollback();
			}
		}
		return res;
	}

	@Override
	public String selectCodeByAccNum(String accountNum) throws SQLException {
		String sql = "select custCode from bankbook where accountNum = ?";
		String custCode = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, accountNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				custCode = rs.getString("custCode");
				return custCode;
			}
			
		}
		return null;
	}

	@Override
	public int transferring(BankBook bankBook, BankBook bankBook2, int fromto) throws SQLException {
		int res = -1;
		String sql ="update bankbook set accountBalance = accountBalance-? where accountNum =?";
			try(Connection con = DriverManager.getConnection(jdbcDriver)) {
			con.setAutoCommit(false);
			PreparedStatement pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fromto);
			pstmt.setString(2, bankBook.getAccountNum());
			res = pstmt.executeUpdate();
			sql = "update transferringBankBook set balance = balance+? where accountnum =? and bankcode=? "; 
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, fromto);
			pstmt.setString(2, bankBook2.getAccountNum());
			pstmt.setString(3, bankBook2.getCustCode().getCustCode()); //고객은 아니지만 고객코드 에 뱅크코드 넣고 고객네임에 고객이름 넣을 예정  
			
			res += pstmt.executeUpdate();
			if(res==2) {
				con.commit();
			}
			else {
				con.rollback();
			}
		}
		return res;
	}

	
	//간단한 타행 이체 확인용 
	@Override
	public BankBook findTransferringBankBook(String accountNum, String bankCode) throws SQLException {
		String sql="select accountnum, bankcode, bankname, custname, balance from transferringbankbook where accountnum =? and bankcode=?";
		BankBook bankBook = new BankBook();
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, accountNum);
			pstmt.setString(2, bankCode);
			
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					bankBook = getOneTransferringBankBook(rs);
				}
			}
			
		}
		return bankBook;  
	}

	private BankBook getOneTransferringBankBook(ResultSet rs) throws SQLException {
		String accountNum = rs.getString("accountnum");
		Customer cust= new Customer(rs.getString("bankcode"));
		cust.setCustName(rs.getString("custname"));
		Plan plan = new Plan(rs.getString("bankname"));//플랜을 뱅크 네임으로..
		long accountBalance = rs.getLong("balance");
		return new BankBook(accountNum, cust, plan, accountBalance);
	}

	@Override
	public BankBook checkRedunduncyBankBookPlan(BankBook bankbook) throws SQLException {
		String sql = "select plancode from performance where custcode = (select custcode from customer where custname = ?) and plancode = (select plancode from plan where planname = ?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, bankbook.getCustCode().getCustName());
			pstmt.setString(2, bankbook.getAccountPlanCode().getPlanName());
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					bankbook = new BankBook();
					Plan planCode = new Plan(rs.getString("plancode"));
					bankbook.setAccountPlanCode(planCode);
					return bankbook;
				}
			}
		}
		return null;
	}
	public List<Integer> selectTerminationByCustCode(String custCode) throws SQLException {
		String sql = "select accountTermination from bankbook where custCode=?";
		List<Integer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
				pstmt.setString(1, custCode); 
				rs = pstmt.executeQuery();
				if(rs.next()) {   
					list = new ArrayList<>();
					do {   
						list.add(rs.getInt(1)); 
					}while(rs.next());
				}
				    
			}catch(SQLException e) {  
				e.printStackTrace();      
			}
			return list;          
	}

	@Override
	public List<Integer> selectLonaDoneCheckByCustCode(String custCode) throws SQLException {
		String sql = "select loanExpired from loan where custCode=?";
		List<Integer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
				pstmt.setString(1, custCode); 
				rs = pstmt.executeQuery();
				if(rs.next()) {
					list = new ArrayList<>();
					do {   
						list.add(rs.getInt(1)); 
					}while(rs.next());
				}
				    
			}catch(SQLException e) {  
				e.printStackTrace();      
			}
			return list;    
	}

	@Override
	public List<BankBook> showBankBooksByNormal(int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountDormant = 0 and accountTermination = 0 and c.custDiv = 0 limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			ResultSet rs = pstmt.executeQuery(); {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
				
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByBusiness(int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountDormant = 0 and accountTermination = 0 and c.custDiv = 1 limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			ResultSet rs = pstmt.executeQuery(); {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
				
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByAccountNum(BankBook bankbook, int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where b.accountnum like ? and custdiv = ? limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%"+bankbook.getAccountNum()+"%");
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByCustName(BankBook bankbook, int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest,c.custDiv from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where c.custname like ? and custdiv = ? limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%" + bankbook.getCustCode().getCustName() + "%");
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByPlanName(BankBook bankbook, int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where p.planname like ? and custdiv = ? limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%"+bankbook.getAccountPlanCode().getPlanName()+"%");
			pstmt.setBoolean(2, bankbook.getCustCode().getCustDiv());
			pstmt.setInt(3, startRow);
			pstmt.setInt(4, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
		}
		return list;
	}

	@Override
	public List<BankBook> showBankBooksByDeposit(Customer customer, int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountnum like '%-11-%' and custdiv = ? limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
			return list;
		}
	}

	@Override
	public List<BankBook> showBankBooksBySaving(Customer customer, int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountnum like '%-12-%' and custdiv = ? limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
			return list;
		}
	}

	@Override
	public List<BankBook> showBankBooksByMinus(Customer customer, int startRow, int endRow) throws SQLException {
		List<BankBook> list = new ArrayList<>();
		String sql = "select b.accountNum,c.custCode,c.custName,p.planCode,p.planName,b.accountOpenDate,b.accountInterest from bankbook b left join customer c on b.custCode = c.custCode left join plan p on b.accountPlanCode = p.planCode where accountnum like '%-13-%' and custdiv = ? limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getBankBook(rs));
				}
			}
			return list;
		}
	}
}
