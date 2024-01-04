package com.yi.dto;

import java.util.Date;

public class Customer {
	private String custCode;
	private String custName;
	private int custCredit;
	private String custAddr;
	private String custTel;
	private Date lastAccountDate;
	private String lastAccount;
	private BankBook bankbook;
	private Boolean custDiv;
	
	

	public Customer() {
		
	}





	public Customer(String custCode, String custName) {
		super();
		this.custCode = custCode;
		this.custName = custName;
	}
	
	
	


	public Customer(String custCode, String custName, int custCredit, String custAddr, String custTel, Boolean custDiv) {
		super();
		this.custCode = custCode;
		this.custName = custName;
		this.custCredit = custCredit;
		this.custAddr = custAddr;
		this.custTel = custTel;
		this.custDiv = custDiv;
	}



	public Customer(String custCode, String custName, int custCredit, String custAddr,
			String custTel) {
		this.custCode = custCode;
		this.custName = custName;
		this.custCredit = custCredit;
		this.custAddr = custAddr;
		this.custTel = custTel;
	}


	public Customer(String custCode) {
		this.custCode = custCode;
	}
	public String getCustCode() {
		return custCode;
	}
	public void setCustCode(String custCode) {
		this.custCode = custCode;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public int getCustCredit() {
		return custCredit;
	}
	public void setCustCredit(int custCredit) {
		this.custCredit = custCredit;
	}
	public String getCustAddr() {
		return custAddr;
	}
	public void setCustAddr(String custAddr) {
		this.custAddr = custAddr;
	}
	public String getCustTel() {
		return custTel;
	}
	public void setCustTel(String custTel) {
		this.custTel = custTel;
	}
	

	public Boolean getCustDiv() {
		return custDiv;
	}



	public void setCustDiv(Boolean custDiv) {
		this.custDiv = custDiv;
	}



	public BankBook getBankbook() {
		return bankbook;
	}
	
	


	public void setBankbook(BankBook bankbook) {
		this.bankbook = bankbook;
	}


	
	

	public Date getLastAccountDate() {
		return lastAccountDate;
	}



	public void setLastAccountDate(Date lastAccountDate) {
		this.lastAccountDate = lastAccountDate;
	}



	public String getLastAccount() {
		return lastAccount;
	}



	public void setLastAccount(String lastAccount) {
		this.lastAccount = lastAccount;
	}



	@Override
	public String toString() {
		return String.format("%s", custName);
	}
	
}
