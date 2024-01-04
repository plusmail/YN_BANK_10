package com.yi.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.yi.dao.CustomerDao;
import com.yi.dto.BankBook;
import com.yi.dto.Cust_dw_audit;
import com.yi.dto.Customer;

public class CustomerDaoImpl implements CustomerDao {
	private static final CustomerDaoImpl instance = new CustomerDaoImpl();
	String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
	public static CustomerDaoImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<Customer> selectCustomerAll() throws SQLException {
		List<Customer> list = null;
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Customer getCustomer(ResultSet rs) throws SQLException{
		String custCode = rs.getString("custCode");
		String custName = rs.getString("custName");
		int custCredit = rs.getInt("custCredit");
		String custAddr = rs.getString("custAddr");
		String custTel = rs.getString("custTel");
		int custDiv = rs.getInt("custDiv");
		Boolean custDivTF;
		if(custDiv==0) {
			custDivTF=false;
		}else{
			custDivTF=true;
		}
		
		return new Customer(custCode, custName, custCredit, custAddr, custTel, custDivTF);
	}

	  
	@Override
	public List<Customer> selectCustomerByName(String custName) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custName like ?";
		List<Customer> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, "%"+custName+"%");
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					list = new ArrayList<>();
					do {
						list.add(getCustomer(rs));
					}while(rs.next());
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	@Override
	public Customer selectCustomerByNameNoLike(String custName) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custName = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, custName);
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					Customer customer = getCustomer(rs);
					return customer;
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public void insertCustomer(Customer customer) throws SQLException {
		String sql = "insert into customer values(?,?,?,?,?,?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, customer.getCustCode());
			pstmt.setString(2, customer.getCustName());
			pstmt.setInt(3, customer.getCustCredit());
			pstmt.setString(4, customer.getCustAddr());
			pstmt.setString(5, customer.getCustTel());
			pstmt.setBoolean(6, customer.getCustDiv());
			
			pstmt.executeUpdate();
		}
		
	}

	@Override
	public int updateCustomer(Customer customer) throws SQLException {
		String sql = "update customer set custName =?, custCredit=?, custAddr=?, custTel=?, custDiv=? where custCode=? ";
		int res = -1;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, customer.getCustName());
			pstmt.setInt(2, customer.getCustCredit());
			pstmt.setString(3, customer.getCustAddr());
			pstmt.setString(4, customer.getCustTel());
			pstmt.setBoolean(5, customer.getCustDiv());
			pstmt.setString(6, customer.getCustCode());
			
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteCustomer(Customer customer) throws SQLException {
		String sql = "delete from customer where custCode =?";
		int res =  -1;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, customer.getCustCode());
			res=pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public List<Customer> selectCustomerBalance() throws SQLException {
		String sql = "select c.custCode, c.custName, b.accountNum, b.accountBalance from customer c "
				+ "join bankbook b on c.custcode = b.custcode";
		List<Customer> list = new ArrayList<>();
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				do {
					list.add(getCustomerForBalance(rs));
				}while(rs.next());
			}
		}
		
		return list;
	}

	private Customer getCustomerForBalance(ResultSet rs) throws SQLException {
		String custCode = rs.getString("c.custCode");
		String custName  = rs.getString("c.custName");
		String custAccnt = rs.getString("b.accountNum");
		String custBalance = rs.getString("b.accountBalance");
		if(custBalance==null) {
			custBalance="0";
		}
		Long balance = Long.parseLong(custBalance);
		
		Customer customer = new Customer(custCode, custName);
		BankBook bankbook = new BankBook(customer);
		bankbook.setAccountNum(custAccnt);
		bankbook.setAccountBalance(balance);
		
		customer.setBankbook(bankbook);
		/*
		 * customer.getBankbook().setCustCode(customer.getCustCode());
		 * customer.getBankbook().setAccountNum(custAccnt);
		 * customer.getBankbook().setAccountBalance(balance);
		 */
		
		return customer;
	}

	@Override
	public List<Customer> selectCustomerBankInfoByName(String custName) throws SQLException {
		String sql = "select custName, accountNum, accountBalance from customer c join bankbook b on c.custcode = b.custCode where custName like ?";
		List<Customer> list= null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, "%"+custName+"%");
			ResultSet rs = pstmt.executeQuery();
				
				if(rs.next()) {
					list = new ArrayList<>();
					do {
						list.add(getCustBankInfo(rs));
					}while(rs.next());
				}
				return list;
		}
	}

	private Customer getCustBankInfo(ResultSet rs) throws SQLException {
		Customer customer= new Customer();
		BankBook bankbook= new BankBook();
		customer.setCustName(rs.getString(1));
		bankbook.setAccountNum(rs.getString(2));
		bankbook.setAccountBalance(Long.parseLong(rs.getString(3)));
		customer.setBankbook(bankbook);
		
		return customer;
	}

	@Override
	public int selectNormalCustNum() throws SQLException {
		String sql ="select (count(*) - (select count(*) from customer where custdiv = 1)) from customer";
		int result;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
				return result;
			}
		}
		return -1;
	}

	@Override
	public int selectVIPCustNum() throws SQLException {
		String sql ="select count(*) from customer where custCredit = 1";
		int result;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
				return result;
			}
		}
		return -1;
	}

	@Override
	public int select5CreditCustNum() throws SQLException {
		String sql ="select count(*) from customer where custCredit = 5";
		int result;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
				return result;
			}
		}
		return -1;
	}

	@Override
	public int select4CreditCustNum() throws SQLException {
		String sql ="select count(*) from customer where custCredit = 4";
		int result;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
				return result;
			}
		}
		return -1;
	}

	@Override
	public int select3CreditCustNum() throws SQLException {
		String sql ="select count(*) from customer where custCredit = 3";
		int result;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
				return result;
			}
		}
		return -1;
	}

	@Override
	public int select2CreditCustNum() throws SQLException {
		String sql ="select count(*) from customer where custCredit = 2";
		int result;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				result = rs.getInt(1);
				return result;
			}
		}
		return -1;
	}

	@Override
	public List<String> selectCustExistChk() throws SQLException {
		List<String> list = null;
		String sql = "select custName from customer";
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
	public List<Customer> selectCustomerByCode(String custCode) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custCode like ?";
		List<Customer> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, custCode+"%");
			try(ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					list = new ArrayList<>();
					do {
						Customer customer = getCustomer(rs);
						list.add(customer);
					}while(rs.next());
				}
			}
			
		}catch(SQLException e) {
			e.printStackTrace();  
		}
		
		return list;
	}  
     
	@Override
	public List<Customer> selectCustomerByTel(String custTel) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custTel like ?";
		ResultSet rs = null;
		List<Customer> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, "%"+custTel+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
				
			}
			  
		}catch(SQLException e) {
			e.printStackTrace();  
		}
		
		return list;
	}

	@Override
	public List<Customer> selectCustomerBankInfoByAcc(String accountNum) throws SQLException {
		String sql = "select * from customer c join bankbook b on c.custCode = b.custCode where accountNum = ?";
		List<Customer> list = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, accountNum);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					Customer customer = getCustomerByAcc(rs);
					list.add(customer);
				}while(rs.next());
			}
		}
		return list;
	}
  
	private Customer getCustomerByAcc(ResultSet rs) throws SQLException {
		Customer customer = new Customer();
		BankBook bankbook = new BankBook();
		customer.setCustCode(rs.getString("custCode"));
		customer.setCustName(rs.getString("custName"));
		bankbook.setAccountNum(rs.getString("accountNum"));
		bankbook.setAccountBalance(Long.parseLong(rs.getString("accountBalance")));
		customer.setBankbook(bankbook);
		return customer;
	}   

	@Override
	public List<Customer> selectBusinessCust() throws SQLException {
		List<Customer> list = null;
		String sql = "select * from customer where custCode like ?";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "B%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerByNormal() throws SQLException {
		List<Customer> list = null;
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custdiv = 0";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();){
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerByBusiness() throws SQLException {
		List<Customer> list = null;
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custdiv = 1";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery();){
				if(rs.next()) {
					list = new ArrayList<>();
					do {
						list.add(getCustomer(rs));
					}while(rs.next());
				}
			}catch(SQLException e) {
				e.printStackTrace();
			}
			return list;
	}

	public List<Customer> selectNormalCust() throws SQLException {
		List<Customer> list = null;
		String sql = "select * from customer where custCode like ?";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "C%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWhoHasAcc() throws SQLException {
		List<Customer> list = null;
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where substr(b.accountNum, 8,2) = \"11\" or \"12\" or \"13\"";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//예금통장만
	@Override
	public List<Customer> selectCustomerWhoHas11Acc() throws SQLException {
		List<Customer> list = null;
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where substr(b.accountNum, 8,2) = \"11\"";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	private Customer getCustomerForAccBalance(ResultSet rs) throws SQLException {
		String custCode = rs.getString("c.custCode");
		String custName  = rs.getString("c.custName");
		int custCredit = rs.getInt("c.custCredit");
		String custAccnt = rs.getString("accountNum");
		String custBalance = rs.getString("accountBalance");
		int div = rs.getInt("c.custDiv");
		Boolean custDiv;
		if(custBalance==null) {
			custBalance="0";
		}
		if(div==0) {
			custDiv = false;
		}else {
			custDiv = true;
		}
		
		Long balance = Long.parseLong(custBalance);
		
		Customer customer = new Customer();
		customer.setCustCode(custCode);
		customer.setCustName(custName);
		customer.setCustCredit(custCredit);
		customer.setCustDiv(custDiv);
		
		BankBook bankbook = new BankBook();
		bankbook.setAccountNum(custAccnt);
		bankbook.setAccountBalance(balance);
		
		customer.setBankbook(bankbook);
	
		
		return customer;
	}

	@Override
	public List<Customer> selectCustomerWHasAccByCode(String custCode) throws SQLException {
		String sql ="select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custCode like ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){   
			pstmt.setString(1, "%"+custCode+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {    
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}  
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWHasAccByName(String custName) throws SQLException {
		String sql ="select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custName like ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){   
			pstmt.setString(1, "%"+custName+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {    
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWHasAccByTel(String custTel) throws SQLException {
		String sql ="select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custTel like ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){   
			pstmt.setString(1, "%"+custTel+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {    
				list = new ArrayList<>();   
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Cust_dw_audit> selectCust_dw_audit() throws SQLException {
		String sql = "select dw, custname, accountnum, amount, accountbalance, accountTransDate from cust_dw_audit order by accountTransDate desc";
		List<Cust_dw_audit> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCust_dw_audit(rs));
				}while(rs.next());
			}
			
		}
				  
		return list;
	}

	private Cust_dw_audit getCust_dw_audit(ResultSet rs) throws SQLException {
		Cust_dw_audit audit = new Cust_dw_audit();
		audit.setDw(rs.getString(1));
		audit.setCustName(rs.getString(2));
		audit.setAccountNum(rs.getString(3));
		audit.setAmount(rs.getInt(4));  
		audit.setAccountBalance(rs.getInt(5));
		audit.setAccountTransDate(rs.getTimestamp(6));
		return audit;
	}

	@Override
	public List<Cust_dw_audit> selectCust_dw_auditByAcc(String accountNum) throws SQLException {
		String sql = "select dw, custname, accountnum, amount, accountbalance, accountTransDate from cust_dw_audit where accountnum=? ";
		List<Cust_dw_audit> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, accountNum);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCust_dw_audit(rs));
				}while(rs.next());
			}
			
		}
				  
		return list;
	}

	@Override
	public List<Cust_dw_audit> selectCust_dw_auditByName(String custName) throws SQLException {
		String sql = "select dw, custname, accountnum, amount, accountbalance, accountTransDate from cust_dw_audit where custName like ? ";
		List<Cust_dw_audit> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+custName+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCust_dw_audit(rs));
				}while(rs.next());
			}
			
		}    
				    
		return list;
	}

	@Override
	public List<Cust_dw_audit> selectCust_dw_auditByDate(String date) throws SQLException {
		String sql = "select * from cust_dw_audit where substr(accountTransDate, 1, 10) = ?";
		List<Cust_dw_audit> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql)){
			pstmt.setString(1, date);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCust_dw_audit(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	
	//예금계좌만
	@Override
	public List<Customer> selectCustomerWHas11AccByCode(String custCode) throws SQLException {
		String sql ="select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custCode like ? and substr(accountNum, 8,2) = \"11\"";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){   
			pstmt.setString(1, "%"+custCode+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {    
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}  
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWHas11AccByName(String custName) throws SQLException {
		String sql ="select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custName like ? and substr(accountNum, 8,2) = \"11\"";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){   
			pstmt.setString(1, "%"+custName+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {    
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWHas11AccByTel(String custTel) throws SQLException {
		String sql ="select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custTel like ? and substr(accountNum, 8,2) = \"11\"";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){   
			pstmt.setString(1, "%"+custTel+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {    
				list = new ArrayList<>();   
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	
	//페이징 처리
	@Override
	public List<Cust_dw_audit> selectCust_dw_audit(int startRow, int endRow) throws SQLException {
		String sql = "select dw, custname, accountnum, amount, accountbalance, accountTransDate from cust_dw_audit order by accountTransDate desc limit ?,?";
		List<Cust_dw_audit> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCust_dw_audit(rs));
				}while(rs.next());
			}
			
		}
				  
		return list;
	}

	@Override
	public List<Customer> selectCustomerWhoHasAcc(int startRow, int endRow) throws SQLException {
		List<Customer> list = null;
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where substr(b.accountNum, 8,2) = \"11\" or \"12\" or \"13\" limit ?,?";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWhoHas11Acc(int startRow, int endRow) throws SQLException {
		List<Customer> list = null;
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where substr(b.accountNum, 8,2) = \"11\" limit ?,?";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerAll(int startRow, int endRow) throws SQLException {
		List<Customer> list = null;
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public void setForeignKeyCheckFalse() throws SQLException {
		String sql = "set FOREIGN_KEY_CHECKS=0";
		try(Connection con =  DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void setForeignKeyCheckTrue() throws SQLException {
		String sql = "set FOREIGN_KEY_CHECKS=1";
		try(Connection con =  DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.executeQuery();
		}catch(SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Customer> selectCustomersLimit(int startRow, int endRow) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer limit ?,?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomersLimitByCode(String search, int startRow, int endRow) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custCode like ? limit ?, ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomersLimitByName(String search, int startRow, int endRow) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custName like ? limit ?, ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomersLimitByTel(String search, int startRow, int endRow) throws SQLException {
		String sql = "select custCode, custName, custCredit, custAddr, custTel, custDiv from customer where custTel like ? limit ?, ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomer(rs));
				}while(rs.next());
			}
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWhoHasAccLimit(int startRow, int endRow) throws SQLException {
		List<Customer> list = null;
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where substr(b.accountNum, 8,2) = \"11\" or \"12\" or \"13\" limit ?,?";
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWhoHasAccLimitByCode(String search, int startRow, int endRow) throws SQLException {
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custCode like ? and (substr(b.accountNum, 8,2) = \"11\" or \"12\" or \"13\") limit ?, ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWhoHasAccLimitByName(String search, int startRow, int endRow)
			throws SQLException {
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custName like ? and (substr(b.accountNum, 8,2) = \"11\" or \"12\" or \"13\") limit ?, ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public List<Customer> selectCustomerWhoHasAccLimitByTel(String search, int startRow, int endRow)
			throws SQLException {
		String sql = "select c.custCode, c.custName, c.custCredit, accountNum, accountBalance, c.custDiv from customer c join bankbook b on c.custCode = b.custCode where c.custTel like ? and (substr(b.accountNum, 8,2) = \"11\" or \"12\" or \"13\") limit ?, ?";
		List<Customer> list = null;
		ResultSet rs = null;
		try(Connection con = DriverManager.getConnection(jdbcDriver);
			PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setString(1, "%"+search+"%");
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				list = new ArrayList<>();
				do {
					list.add(getCustomerForAccBalance(rs));
				}while(rs.next());
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	

}
