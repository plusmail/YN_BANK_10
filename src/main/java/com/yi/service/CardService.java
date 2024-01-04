package com.yi.service;

import java.sql.SQLException;
import java.util.List;

//import jakarta.swing.JOptionPane;

import com.yi.dao.BankBookDao;
import com.yi.dao.CardDao;
import com.yi.dao.CustomerDao;
import com.yi.dao.PlanDao;
import com.yi.dao.impl.BankBookDaoImpl;
import com.yi.dao.impl.CardDaoImpl;
import com.yi.dao.impl.CustomerDaoImpl;
import com.yi.dao.impl.PlanDaoImpl;
import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;
import com.yi.dto.Plan;

public class CardService {
	private CardDao cardDao;
	private CustomerDao custDao;
	private PlanDao planDao;
	private BankBookDao bankbookDao;

	public CardService() {
		cardDao = CardDaoImpl.getInstance();
		custDao = CustomerDaoImpl.getInstance();
		planDao = PlanDaoImpl.getInstance();
		bankbookDao = BankBookDaoImpl.getInstance();
	}
	public List<Card> showCards() throws SQLException {
		return cardDao.showCards();
	}
	public List<Card> showCardsByNormal() throws SQLException {
		return cardDao.showCardsByNormal();
	}
	public List<Card> showCardsByBusiness() throws SQLException {
		return cardDao.showCardsByBusiness();
	}
	public List<Card> showCardByCustName(Card card) throws SQLException {
		return cardDao.showCardByCustName(card);
	}
	public List<Card> showCardByPlanName(Card card) throws SQLException {
		return cardDao.showCardByPlanName(card);
	}
	public List<Card> showCardByCheckCard(Customer customer) throws SQLException {
		return cardDao.showCardByCheckCard(customer);
	}
	public List<Card> showCardByCreditCard(Customer customer) throws SQLException {
		return cardDao.showCardByCreditCard(customer);
	}
	public int insertCardCredit(Card card) throws SQLException {
		return cardDao.insertCardCredit(card);
	}
	public int updateCard(Card card) throws SQLException {
		return cardDao.updateCard(card);
	}
	public int deleteCard(Card card) throws SQLException {
		return cardDao.deleteCard(card);
	}
	public List<Customer> showCusts() throws SQLException {
		return custDao.selectCustomerAll();
	}
	public int updateAccountBalance(Card card) throws SQLException {
		return cardDao.updateAccountBalance(card);
	}
	public List<BankBook> showBankBookIsConnect(Card card) throws SQLException {
		return bankbookDao.showBankBookByIsConnect(card);
	}
	public int insertCheckCardProcedure(Card card) throws SQLException {
		try {
			int res = 0;
			res += cardDao.insertCardCheck(card);
			card.getBankbook().setConnectChk(true);
			res += bankbookDao.updateConnectChk(card);
			res += bankbookDao.updateCardBalanceByAccountBalance(card);
			if(res==3) {
				return res;
			}
			else {
				new RuntimeException();
			}
		}
		catch(RuntimeException e) {
			e.printStackTrace();
		}
		return -1;
	}
	public int deleteCheckCardProcedure(Card card) throws SQLException {
		try {
			int res = 0;
			Card account = cardDao.showCardByAccountNum(card);
			card.setBankbook(account.getBankbook());
			card.getBankbook().setConnectChk(false);
			res += bankbookDao.updateConnectChk(card);
			res += cardDao.deleteCard(card);
			if(res==2) {
				return res;
			}
			else {
				new RuntimeException();
			}
		}
		catch(RuntimeException e) {
			System.out.println(e);
//			JOptionPane.showMessageDialog(null, "실패하였습니다");
		}
		return -1;
	}
	public Card showCardByAccountnum(Card card) throws SQLException {
		return cardDao.showCardByAccountNum(card);
	}
	public Card showCardByCheckAccountNum(Card card) throws SQLException {
		return cardDao.showCardByCheckAccountNum(card);
	}
	public Card showCardByCardNumAndCustName(Card card) throws SQLException {
		return cardDao.showCardByCardNumAndCustName(card);
	}
	public List<Plan> selectPlanByCardCustomerNormal() throws SQLException {
		return planDao.selectPlanByCardCustomerNormal();
	}
	public List<Plan> selectPlanByCardCustomerVip() throws SQLException {
		return planDao.selectPlanByCardCustomerVip();
	}
	public List<Plan> selectPlanByCardBusinessNormal() throws SQLException {
		return planDao.selectPlanByCardBusinessNormal();
	}
	public List<Plan> selectPlanByCardBusinessVip() throws SQLException {
		return planDao.selectPlanByCardBusinessVip();
	}
	public Card checkRedunduncyCardPlan(Card card) throws SQLException {
		return cardDao.checkRedunduncyCardPlan(card);
	}
	//페이징 처리 
	public List<Card> showCardsByNormal(int startRow, int endRow) throws SQLException {
		return cardDao.showCardsByNormal(startRow, endRow);
	}
	public List<Card> showCardsByBusiness(int startRow, int endRow) throws SQLException {
		return cardDao.showCardsByBusiness(startRow, endRow);
	}
}

