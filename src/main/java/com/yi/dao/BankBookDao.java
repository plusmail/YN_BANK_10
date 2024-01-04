package com.yi.dao;

import java.sql.SQLException;
import java.util.List;

import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Customer;

public interface BankBookDao {
	public abstract List<BankBook> showBankBooks() throws SQLException;
	public abstract List<BankBook> showBankBooksByNormal() throws SQLException;
	public abstract List<BankBook> showBankBooksByBusiness() throws SQLException;
	public abstract BankBook showBankBookByCustNameAndAccountNum(BankBook bankbook) throws SQLException;
	public abstract List<BankBook> showBankBooksByAccountNum(BankBook bankbook) throws SQLException;
	public abstract List<BankBook> showBankBooksByCustName(BankBook bankbook) throws SQLException;
	public abstract List<BankBook> showBankBooksByPlanName(BankBook bankbook) throws SQLException;
	public abstract List<BankBook> showBankBooksByDeposit(Customer customer) throws SQLException;
	public abstract List<BankBook> showBankBooksBySaving(Customer customer) throws SQLException;
	public abstract List<BankBook> showBankBooksByMinus(Customer customer) throws SQLException;
	public abstract String showDPTotalAmount() throws SQLException;//예금 총금액
	public abstract String showSvTotalAmount() throws SQLException;//적금 총금액
	public abstract String showLoTotalAmount() throws SQLException;//대출 총금액
	public abstract List<String> showOpenDPMonth() throws SQLException; //월별 예금 건수
	public abstract List<String> showOpenSvMonth() throws SQLException; //월별 적금 건수
	public abstract List<String> showOpenLoMonth() throws SQLException; //월별 대출 건수
	public abstract List<String> showDepositMonth() throws SQLException; //월별 입금 건수
	public abstract List<String> showWithDrawalMonth() throws SQLException; //월별 출금 건수
	public abstract void update_balance_locking(int amount, String accountNum, String text) throws SQLException; //입출금 처리 - 해당 계좌번호 row에만 locking
	public abstract Long showAccBalanceByCodeAccNum(Customer customer) throws SQLException; // 고객 코드, 계좌번호로 잔액 검색
	public abstract String selectCodeByAccNum(String accountNum) throws SQLException; //계좌번호로 고객 코드 검색 (송금 시 카드 잔액 자동변경 시 사용)
	public abstract List<Integer> selectTerminationByCustCode(String custCode) throws SQLException; //고객 코드로 해지 계좌인지 체크하기
	public abstract List<Integer> selectLonaDoneCheckByCustCode(String custCode) throws SQLException; //고객 코드로 모든 대출이 상환되었는지 체크하기
	public abstract int insertBankBook(BankBook bankbook) throws SQLException;
	public abstract int updateBankBook(BankBook bankbook) throws SQLException;
	public abstract int deleteBankBook(BankBook bankbook) throws SQLException;
	public abstract int updateBankBalance(Customer customer) throws SQLException;
	public abstract List<BankBook> showBankBookByDormant(BankBook bankbook) throws SQLException;
	public abstract List<BankBook> showBankBookByTermination(BankBook bankbook) throws SQLException;
	public abstract int updateCardBalance(Customer customer) throws SQLException;
	public abstract List<BankBook> showBankBookByIsConnect(Card card) throws SQLException;
	public abstract int updateConnectChk(Card card) throws SQLException;
	public abstract int updateCardBalanceByAccountBalance(Card card) throws SQLException;
	public abstract int changeBankBookDormant(BankBook bankbook) throws SQLException;
	public abstract int changeBankBookTermination(BankBook bankbook) throws SQLException;
	public abstract long[] TotalPlanTransAmountYearly() throws SQLException;
	
	public BankBook findBankBook(String accountNum) throws SQLException;
	public int changeBankBookBalance(BankBook bankBook, BankBook bankBook2, int fromto) throws SQLException;
	public int transferring(BankBook bankBook, BankBook bankBook2, int fromto) throws SQLException;
	public BankBook findTransferringBankBook(String accountNum, String bankCode) throws SQLException;
	public BankBook checkRedunduncyBankBookPlan(BankBook bankbook) throws SQLException;
	
	//페이징
	public abstract List<BankBook> showBankBooksByNormal(int startRow, int endRow) throws SQLException;
	public abstract List<BankBook> showBankBooksByBusiness(int startRow, int endRow) throws SQLException;
	public abstract List<BankBook> showBankBooksByAccountNum(BankBook bankbook, int startRow, int endRow) throws SQLException;
	public abstract List<BankBook> showBankBooksByCustName(BankBook bankbook, int startRow, int endRow) throws SQLException;
	public abstract List<BankBook> showBankBooksByPlanName(BankBook bankbook, int startRow, int endRow) throws SQLException;
	public abstract List<BankBook> showBankBooksByDeposit(Customer customer, int startRow, int endRow) throws SQLException;
	public abstract List<BankBook> showBankBooksBySaving(Customer customer, int startRow, int endRow) throws SQLException;
	public abstract List<BankBook> showBankBooksByMinus(Customer customer, int startRow, int endRow) throws SQLException;
}
