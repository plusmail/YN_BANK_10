package com.yi.dto;

import java.util.Date;

public class BankBook {
	private String accountNum;
	private Customer custCode;
	private Plan accountPlanCode;
	private Date accountOpenDate;
	private float accountInterest;
	private long accountBalance;
	private boolean accountDormant;
	private boolean accountTermination;
	private Employee employee;
	private boolean connectChk;
	public BankBook() {
		
	}
	public BankBook(Customer custCode) {
		this.custCode = custCode;
	}
	
    //송금위한 생성자 
	public BankBook(String accountNum, Customer custCode, Plan accountPlanCode, long accountBalance) {
		super();
		this.accountNum = accountNum;
		this.custCode = custCode;
		this.accountPlanCode = accountPlanCode;
		this.accountBalance = accountBalance;
	}
	
	
	public BankBook(String accountNum) {
		super();
		this.accountNum = accountNum;
	}
	public BankBook(String accountNum, Customer custCode, boolean connectChk) {
		this.accountNum = accountNum;
		this.custCode = custCode;
		this.connectChk = connectChk;
	}
	public BankBook(String accountNum, Customer custCode, Plan accountPlanCode, Date accountOpenDate,
			float accountInterest) {
		super();
		this.accountNum = accountNum;
		this.custCode = custCode;
		this.accountPlanCode = accountPlanCode;
		this.accountOpenDate = accountOpenDate;
		this.accountInterest = accountInterest;
	}
	public BankBook(String accountNum, Customer custCode, Plan accountPlanCode, Date accountOpenDate,
			float accountInterest, long accountBalance) {
		this.accountNum = accountNum;
		this.custCode = custCode;
		this.accountPlanCode = accountPlanCode;
		this.accountOpenDate = accountOpenDate;
		this.accountInterest = accountInterest;
		this.accountBalance = accountBalance;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public Customer getCustCode() {
		return custCode;
	}
	public void setCustCode(Customer custCode) {
		this.custCode = custCode;
	}
	public Plan getAccountPlanCode() {
		return accountPlanCode;
	}
	public void setAccountPlanCode(Plan accountPlanCode) {
		this.accountPlanCode = accountPlanCode;
	}
	public Date getAccountOpenDate() {
		return accountOpenDate;
	}
	public void setAccountOpenDate(Date accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}
	public float getAccountInterest() {
		return accountInterest;
	}
	public void setAccountInterest(float accountInterest) {
		this.accountInterest = accountInterest;
	}
	public long getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(long accountBalance) {
		this.accountBalance = accountBalance;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public boolean isConnectChk() {
		return connectChk;
	}
	public void setConnectChk(boolean connectChk) {
		this.connectChk = connectChk;
	}
	
	public boolean isAccountDormant() {
		return accountDormant;
	}
	public void setAccountDormant(boolean accountDormant) {
		this.accountDormant = accountDormant;
	}
	public boolean isAccountTermination() {
		return accountTermination;
	}
	public void setAccountTermination(boolean accountTermination) {
		this.accountTermination = accountTermination;
	}
	@Override
	public String toString() {
		return String.format(
				"BankBook [accountNum=%s, custCode=%s, accountPlanCode=%s, accountOpenDate=%s, accountInterest=%s, accountBalance=%s, employee=%s, connectChk=%s]",
				accountNum, custCode, accountPlanCode, accountOpenDate, accountInterest, accountBalance, employee,
				connectChk);
	}
	
}
