package com.yi.dto;

import java.util.Date;

public class Loan {
	private String loanAccountNum;
	private Customer custCode;
	private Plan planCode;
	private Date loanStartDate;
	private Date loanDelayDate;
	private Date loanExpireDate;
	private float loanInterest;
	private long loanBalance;
	private String loanMethod;
	private boolean loanExtended;
	private Employee employee;
	public Loan() {
		
	}
	public Loan(Customer custCode) {
		this.custCode = custCode;
	}
	
	
	public Loan(String loanAccountNum, Customer custCode, Plan planCode, Date loanStartDate, Date loanDelayDate,
			Date loanExpireDate, float loanInterest, long loanBalance, String loanMethod ) {
		this.loanAccountNum = loanAccountNum;
		this.custCode = custCode;
		this.planCode = planCode;
		this.loanStartDate = loanStartDate;
		this.loanDelayDate = loanDelayDate;
		this.loanExpireDate = loanExpireDate;
		this.loanInterest = loanInterest;
		this.loanBalance = loanBalance;
		this.loanMethod = loanMethod;
	}
	
	public Loan(String loanAccountNum, Customer custCode, Plan planCode, Date loanStartDate, Date loanDelayDate,
			Date loanExpireDate, float loanInterest, long loanBalance, String loanMethod, boolean loanExtended) {
		this.loanAccountNum = loanAccountNum;
		this.custCode = custCode;
		this.planCode = planCode;
		this.loanStartDate = loanStartDate;
		this.loanDelayDate = loanDelayDate;
		this.loanExpireDate = loanExpireDate;
		this.loanInterest = loanInterest;
		this.loanBalance = loanBalance;
		this.loanMethod = loanMethod;
		this.loanExtended = loanExtended;
	}
	public Loan(String loanAccountNum, Customer custCode, Plan planCode, Date loanStartDate, Date loanDelayDate,
			Date loanExpireDate, float loanInterest, long loanBalance, String loanMethod, Employee employee) {
		this.loanAccountNum = loanAccountNum;
		this.custCode = custCode;
		this.planCode = planCode;
		this.loanStartDate = loanStartDate;
		this.loanDelayDate = loanDelayDate;
		this.loanExpireDate = loanExpireDate;
		this.loanInterest = loanInterest;
		this.loanBalance = loanBalance;
		this.loanMethod = loanMethod;
		this.employee = employee;
	}
	public String getLoanAccountNum() {
		return loanAccountNum;
	}
	public void setLoanAccountNum(String loanAccountNum) {
		this.loanAccountNum = loanAccountNum;
	}
	public Customer getCustCode() {
		return custCode;
	}
	public void setCustCode(Customer custCode) {
		this.custCode = custCode;
	}
	public Plan getPlanCode() {
		return planCode;
	}
	public void setPlanCode(Plan planCode) {
		this.planCode = planCode;
	}
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
	}
	public float getLoanInterest() {
		return loanInterest;
	}
	public void setLoanInterest(float loanInterest) {
		this.loanInterest = loanInterest;
	}
	public long getLoanBalance() {
		return loanBalance;
	}
	public void setLoanBalance(long loanBalance) {
		this.loanBalance = loanBalance;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public Date getLoanDelayDate() {
		return loanDelayDate;
	}
	public void setLoanDelayDate(Date loanDelayDate) {
		this.loanDelayDate = loanDelayDate;
	}
	public Date getLoanExpireDate() {
		return loanExpireDate;
	}
	public void setLoanExpireDate(Date loanExpireDate) {
		this.loanExpireDate = loanExpireDate;
	}
	public String getLoanMethod() {
		return loanMethod;
	}
	public void setLoanMethod(String loanMethod) {
		this.loanMethod = loanMethod;
	}
	public boolean isLoanExtended() {
		return loanExtended;
	}
	public void setLoanExtended(boolean loanExtended) {
		this.loanExtended = loanExtended;
	}
}
