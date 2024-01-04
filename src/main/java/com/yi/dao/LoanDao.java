package com.yi.dao;

import java.sql.SQLException;
import java.util.List;

import com.yi.dto.BankBook;
import com.yi.dto.Card;
import com.yi.dto.Loan;
import com.yi.dto.Repayment;

public interface LoanDao {
	public List<Loan> showLoans() throws SQLException;
	public List<Loan> showLoansNormal() throws SQLException;
	public List<Loan> showLoansBuisness() throws SQLException;
	public Loan showLoanByLoanAccountNumAndCustName(Loan loan) throws SQLException;
	public List<Loan> showLoanByCustName(Loan loan) throws SQLException;
	public int insertLoan(Loan loan) throws SQLException;
	public int updateLoanExpireDate(Loan loan) throws SQLException;
	public int deleteLoan(Loan loan) throws SQLException;
	public List<Loan> searchLoanAccountNums(Loan loan) throws SQLException;
	public List<Loan> searchLoanCustNames(Loan loan) throws SQLException;
	public List<Loan> searchLoanPlanNames(Loan loan) throws SQLException;
	public List<Repayment> searchRepaymentsByAccountNum(String accountnum) throws SQLException;
	public List<Repayment> searchRepaymentsByAccountNumAndCustDiv(Repayment repayment) throws SQLException;
	public int insertRepayment(Repayment repayment) throws SQLException;
	public int insertRepaymentByEquityPaymentProcedure(Repayment repayment) throws SQLException;
	public int insertAndDeleteProcedure(Repayment repayment) throws SQLException;
	public Loan checkRedunduncyLoanPlan(Loan loan) throws SQLException;
	public abstract List<Loan> showLoansByNormal(int startRow, int endRow) throws SQLException;
	public abstract List<Loan> showLoansByBusiness(int startRow, int endRow) throws SQLException;
	public List<Repayment> searchRepaymentsByAccountNumAndCustDiv(Repayment repayment, int startRow, int endRow) throws SQLException;
}
