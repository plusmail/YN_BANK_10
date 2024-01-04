package com.yi.dao;

import java.sql.SQLException;
import java.util.List;

import com.yi.dto.Cust_dw_audit;
import com.yi.dto.Customer;

public interface CustomerDao {
	
	abstract List<Customer> selectCustomerAll() throws SQLException;
	abstract List<Customer> selectCustomerByNormal() throws SQLException;
	abstract List<Customer> selectCustomerByBusiness() throws SQLException;
	abstract List<Customer> selectCustomerBalance() throws SQLException;
	abstract List<Customer> selectCustomerByName(String custName) throws SQLException;
	abstract Customer selectCustomerByNameNoLike(String custName) throws SQLException;
	abstract List<Customer> selectBusinessCust() throws SQLException;
	abstract List<Customer> selectNormalCust() throws SQLException;
	abstract List<Customer>  selectCustomerByCode(String custCode) throws SQLException;
	abstract List<Customer> selectCustomerWhoHasAcc() throws SQLException;
	//예금통장만
	abstract List<Customer> selectCustomerWhoHas11Acc() throws SQLException;
	//예금계좌만
	abstract List<Customer> selectCustomerWHas11AccByCode(String custCode) throws SQLException;
	abstract List<Customer> selectCustomerWHas11AccByName(String custName) throws SQLException;
	abstract List<Customer> selectCustomerWHas11AccByTel(String custTel) throws SQLException;
	abstract List<Customer> selectCustomerWHasAccByCode(String custCode) throws SQLException;
	abstract List<Customer> selectCustomerWHasAccByName(String custName) throws SQLException;
	abstract List<Customer> selectCustomerWHasAccByTel(String custTel) throws SQLException;
	abstract List<Customer> selectCustomerByTel(String custTel) throws SQLException;
	abstract List<Customer> selectCustomerBankInfoByName (String custName) throws SQLException;
	abstract List<Customer> selectCustomerBankInfoByAcc (String accountNum) throws SQLException;
	abstract List<Cust_dw_audit> selectCust_dw_audit() throws SQLException;
	abstract List<Cust_dw_audit> selectCust_dw_auditByAcc(String accountNum) throws SQLException;
	abstract List<Cust_dw_audit> selectCust_dw_auditByName(String custName) throws SQLException;
	abstract List<Cust_dw_audit> selectCust_dw_auditByDate(String date) throws SQLException;
	abstract int selectNormalCustNum() throws SQLException; 
	abstract int selectVIPCustNum() throws SQLException;
	abstract int select5CreditCustNum() throws SQLException;
	abstract int select4CreditCustNum() throws SQLException;
	abstract int select3CreditCustNum() throws SQLException;
	abstract int select2CreditCustNum() throws SQLException;
	abstract List<String> selectCustExistChk() throws SQLException;
	abstract void insertCustomer(Customer customer) throws SQLException;
	abstract int updateCustomer (Customer customer) throws SQLException;
	abstract int deleteCustomer (Customer customer) throws SQLException;
	
	//페이징
	abstract List<Cust_dw_audit> selectCust_dw_audit(int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomerWhoHasAcc(int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomerWhoHas11Acc(int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomerAll(int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomersLimit(int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomersLimitByCode(String search, int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomersLimitByName(String search, int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomersLimitByTel(String search, int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomerWhoHasAccLimit(int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomerWhoHasAccLimitByCode(String search, int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomerWhoHasAccLimitByName(String search, int startRow, int endRow) throws SQLException;
	abstract List<Customer> selectCustomerWhoHasAccLimitByTel(String search, int startRow, int endRow) throws SQLException;
	
	abstract void setForeignKeyCheckFalse() throws SQLException;
	abstract void setForeignKeyCheckTrue() throws SQLException;
	
}
