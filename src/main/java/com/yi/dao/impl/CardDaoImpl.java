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

import com.yi.dao.CardDao;
import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.dto.Employee;
import com.yi.dto.Plan;

public class CardDaoImpl implements CardDao {
	String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
	private static final CardDaoImpl instance = new CardDaoImpl();
	private CardDaoImpl() {};
	public static CardDaoImpl getInstance() {
		return instance;
	}
	
	@Override
	public List<Card> showCards() throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getCardInfo(rs));
			}
		}
		return list;
	}
	

	@Override
	public List<Card> showCardsByNormal() throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where cs.custDiv = 0";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getCardInfo(rs));
			}
		}
		return list;
	}

	private Card getCardInfo(ResultSet rs) throws SQLException {
		String cardNum = rs.getString("c.cardnum");
		Customer custCode = new Customer(rs.getString("cs.custcode"));
		custCode.setCustName(rs.getString("cs.custname"));
		Plan planCode = new Plan(rs.getString("p.plancode"));
		planCode.setPlanName(rs.getString("p.planname"));
		String cardSecuCode = rs.getString("c.cardsecucode");
		Date cardIssueDate = rs.getTimestamp("c.cardissuedate");
		Card card = new Card(cardNum, custCode, planCode, cardSecuCode, cardIssueDate);
		if(cardNum.substring(6,7).equals("1")) {
			card.setCardBalance(rs.getLong("c.cardbalance"));
		}
		else {
			card.setCardLimit(rs.getInt("c.cardlimit"));
		}
		return card;
	}
	private Card getCardInfoCustDiv(ResultSet rs) throws SQLException {
		String cardNum = rs.getString("c.cardnum");
		Customer custCode = new Customer(rs.getString("cs.custcode"));
		custCode.setCustName(rs.getString("cs.custname"));
		custCode.setCustDiv(rs.getBoolean("cs.custDiv"));
		Plan planCode = new Plan(rs.getString("p.plancode"));
		planCode.setPlanName(rs.getString("p.planname"));
		String cardSecuCode = rs.getString("c.cardsecucode");
		Date cardIssueDate = rs.getTimestamp("c.cardissuedate");
		Card card = new Card(cardNum, custCode, planCode, cardSecuCode, cardIssueDate);
		if(cardNum.substring(6,7).equals("1")) {
			card.setCardBalance(rs.getLong("c.cardbalance"));
		}
		else {
			card.setCardLimit(rs.getInt("c.cardlimit"));
		}
		return card;
	}
	@Override
	public List<Card> showCardByCustName(Card card) throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where cs.custname like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);  
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%" + card.getCustCode().getCustName() + "%");
			pstmt.setBoolean(2, card.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getCardInfo(rs));
				}
			}
		}
		return list;
	}

	@Override
	public int insertCardCheck(Card card) throws SQLException {
		int res = -1;
		String sql = "insert into card values(?,(select custcode from customer where custname = ?),(select plancode from plan where planname = ?),?,?,?,?,(select empcode from employee where empname = ?),?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCardNum());
			pstmt.setString(2, card.getCustCode().getCustName());
			pstmt.setString(3, card.getPlanCode().getPlanName());
			pstmt.setString(4, card.getCardSecuCode());
			pstmt.setTimestamp(5, new Timestamp(card.getCardIssueDate().getTime()));
			pstmt.setInt(6, card.getCardLimit());
			pstmt.setLong(7, card.getCardBalance());
			pstmt.setString(8, card.getEmployee().getEmpName());
			pstmt.setString(9, card.getBankbook().getAccountNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}
	@Override
	public int insertCardCredit(Card card) throws SQLException {
		int res = -1;
		String sql = "insert into card values(?,(select custcode from customer where custname = ?),(select plancode from plan where planname = ?),?,?,?,?,(select empcode from employee where empname = ?),null)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCardNum());
			pstmt.setString(2, card.getCustCode().getCustName());
			pstmt.setString(3, card.getPlanCode().getPlanName());
			pstmt.setString(4, card.getCardSecuCode());
			pstmt.setTimestamp(5, new Timestamp(card.getCardIssueDate().getTime()));
			pstmt.setInt(6, card.getCardLimit());
			pstmt.setLong(7, card.getCardBalance());
			pstmt.setString(8, card.getEmployee().getEmpName());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int updateCard(Card card) throws SQLException {
		int res = -1;
		String sql = "update card set cardsecucode=?,cardissuedate=?,cardlimit=?,cardbalance=? where custcode = (select custcode from customer where custname = ?) and cardnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCardSecuCode());
			pstmt.setTimestamp(2, new Timestamp(card.getCardIssueDate().getTime()));
			pstmt.setInt(3, card.getCardLimit());
			pstmt.setLong(4, card.getCardBalance());
			pstmt.setString(5, card.getCustCode().getCustName());
			pstmt.setString(6, card.getCardNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}

	@Override
	public int deleteCard(Card card) throws SQLException {
		int res = 0;
		String sql = "delete from card where custcode = (select custcode from customer where custname = ?) and cardnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);  
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCustCode().getCustName());
			pstmt.setString(2, card.getCardNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}
	@Override
	public List<Card> showCardByPlanName(Card card) throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where p.planname like ? and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, "%" + card.getPlanCode().getPlanName() + "%");
			pstmt.setBoolean(2, card.getCustCode().getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getCardInfo(rs));
				}
			}
		}
		return list;
	}
	@Override
	public List<Card> showCardByCheckCard(Customer customer) throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where c.cardnum like '%331%' and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getCardInfo(rs));
				}
			}
			return list;
		}
				
	}
	@Override
	public List<Card> showCardByCreditCard(Customer customer) throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where c.cardnum like '%332%' and custdiv = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setBoolean(1, customer.getCustDiv());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					list.add(getCardInfo(rs));
				}
			}
			return list;
		}
	}
	@Override
	public int updateAccountBalance(Card card) throws SQLException {
		int res = 0;
		String sql = "call change_bankbalance(?,?,?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareCall(sql)) {
			pstmt.setString(1, card.getCustCode().getCustCode());
			pstmt.setLong(2, card.getCardBalance());
			pstmt.setString(3, card.getBankbook().getAccountNum());
			res = pstmt.executeUpdate();
		}
		return res;
	}
	@Override
	public Card showCardByAccountNum(Card card) throws SQLException {
		String sql = "select accountnum from card where custcode = (select custcode from customer where custname = ?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCustCode().getCustName());
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					return getCardbyAccountNum(rs);
				}
				
			}
			return null;
		}
	}
	private Card getCardbyAccountNum(ResultSet rs) throws SQLException {
		Card card = new Card();
		BankBook bankbook = new BankBook();
		bankbook.setAccountNum(rs.getString("accountnum"));
		card.setBankbook(bankbook);
		return card;
	}
	@Override
	public Card showCardByCheckAccountNum(Card card) throws SQLException {
		String sql = "select * from card where custcode = (select custcode from customer where custname = ?) and cardnum = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCustCode().getCustName());
			pstmt.setString(2, card.getCardNum());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					return getCardByAccountNum(rs);
				}	
			}
			return null;
		}
	}
	private Card getCardByAccountNum(ResultSet rs) throws SQLException {
		String cardNum = rs.getString("cardnum");
		Customer custCode = new Customer(rs.getString("custcode"));
		Plan planCode = new Plan(rs.getString("plancode"));
		String cardSecuCode = rs.getString("cardsecucode");
		Date cardIssueDate = rs.getTimestamp("cardissuedate");
		int cardLimit = rs.getInt("cardlimit");
		long cardBalance = rs.getLong("cardbalance");
		Employee employee = new Employee(rs.getString("empcode"));
		BankBook bankbook = new BankBook();
		bankbook.setAccountNum(rs.getString("accountnum"));
		return new Card(cardNum, custCode, planCode, cardSecuCode, cardIssueDate, cardLimit, cardBalance, employee, bankbook);
	}
	@Override
	public Card showCardByCardNumAndCustName(Card card) throws SQLException {
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance,cs.custDiv from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where c.cardnum = ? and cs.custname = ?";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCardNum());
			pstmt.setString(2, card.getCustCode().getCustName());
			try(ResultSet rs = pstmt.executeQuery()) {
				while(rs.next()) {
					return getCardInfoCustDiv(rs);
				}
			}
		}
		return null;
	}
	@Override
	public List<Card> showCardsByBusiness() throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where cs.custDiv = 1";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				list.add(getCardInfo(rs));
			}
		}
		return list;
	}
	@Override
	public Card checkRedunduncyCardPlan(Card card) throws SQLException {
		String sql = "select plancode from performance where custcode = (select custcode from customer where custname = ?) and plancode = (select plancode from plan where planname = ?)";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql)) {
			pstmt.setString(1, card.getCustCode().getCustName());
			pstmt.setString(2, card.getPlanCode().getPlanName());
			try(ResultSet rs = pstmt.executeQuery()) {
				if(rs.next()) {
					card = new Card();
					Plan planCode = new Plan(rs.getString("plancode"));
					card.setPlanCode(planCode);
					return card;
				}
			}
		}
		return null;
	}
	public List<Card> showCardsByNormal(int startRow, int endRow) throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where cs.custDiv = 0 limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			ResultSet rs = pstmt.executeQuery(); {
				while(rs.next()) {
					list.add(getCardInfo(rs));
				}
			}
		}
			
		return list;
	}
	@Override
	public List<Card> showCardsByBusiness(int startRow, int endRow) throws SQLException {
		List<Card> list = new ArrayList<>();
		String sql = "select c.cardnum,cs.custcode,cs.custname,p.plancode,p.planname,c.cardsecucode,c.cardissuedate,c.cardlimit,c.cardbalance from card c left join customer cs on c.custcode = cs.custcode left join plan p on p.planCode = c.plancode where cs.custDiv = 1 limit ?,?";
		try(Connection con = DriverManager.getConnection(jdbcDriver); 
				PreparedStatement pstmt = con.prepareStatement(sql);){
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			ResultSet rs = pstmt.executeQuery(); {
			while(rs.next()) {
				list.add(getCardInfo(rs));
			}
		  }
		}
		return list;
	}

}
