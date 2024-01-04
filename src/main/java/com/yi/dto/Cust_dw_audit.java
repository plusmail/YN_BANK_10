package com.yi.dto;

import java.util.Date;

public class Cust_dw_audit {
	private String dw;
	private String custName;
	private String accountNum;
	private int amount;
	private int accountBalance;
	private Date accountTransDate;
	public Cust_dw_audit() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Cust_dw_audit(String dw, String custName, String accountNum, int amount, int accountBalance,
			Date accountTransDate) {
		super();
		this.dw = dw;
		this.custName = custName;
		this.accountNum = accountNum;
		this.amount = amount;
		this.accountBalance = accountBalance;
		this.accountTransDate = accountTransDate;
	}
	public String getDw() {
		return dw;
	}
	public void setDw(String dw) {
		this.dw = dw;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(int accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Date getAccountTransDate() {
		return accountTransDate;
	}
	public void setAccountTransDate(Date accountTransDate) {
		this.accountTransDate = accountTransDate;
	}
	@Override
	public String toString() {
		return "Cust_dw_audit [dw=" + dw + ", custName=" + custName + ", accountNum=" + accountNum + ", amount="
				+ amount + ", accountBalance=" + accountBalance + ", accountTransDate=" + accountTransDate + "]";
	}
	
	
	
	
}	
