package com.yi.dto;

import java.util.Date;

public class Repayment {
	private String loanAccountNum;
	private Customer cust;
	private Plan plan;
	private Date loanStartDate;
	private Date loanDelayDate;
	private Date loanExpireDate;
	private String loanMethod;
	private int loanRound;
	private float loanInterest;
	private long loanBalance;
	private int loanRepayment;
	public Repayment() {
		
	}
	public Repayment(String loanAccountNum, Customer cust, Plan plan, Date loanStartDate, Date loanDelayDate,
			Date loanExpireDate, String loanMethod, int loanRound, float loanInterest, long loanBalance,
			int loanRepayment) {
		this.loanAccountNum = loanAccountNum;
		this.cust = cust;
		this.plan = plan;
		this.loanStartDate = loanStartDate;
		this.loanDelayDate = loanDelayDate;
		this.loanExpireDate = loanExpireDate;
		this.loanMethod = loanMethod;
		this.loanRound = loanRound;
		this.loanInterest = loanInterest;
		this.loanBalance = loanBalance;
		this.loanRepayment = loanRepayment;
	}
	public String getLoanAccountNum() {
		return loanAccountNum;
	}
	public void setLoanAccountNum(String loanAccountNum) {
		this.loanAccountNum = loanAccountNum;
	}
	public Customer getCust() {
		return cust;
	}
	public void setCust(Customer cust) {
		this.cust = cust;
	}
	public Plan getPlan() {
		return plan;
	}
	public void setPlan(Plan plan) {
		this.plan = plan;
	}
	public Date getLoanStartDate() {
		return loanStartDate;
	}
	public void setLoanStartDate(Date loanStartDate) {
		this.loanStartDate = loanStartDate;
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
	public int getLoanRound() {
		return loanRound;
	}
	public void setLoanRound(int loanRound) {
		this.loanRound = loanRound;
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
	public int getLoanRepayment() {
		return loanRepayment;
	}
	public void setLoanRepayment(int loanRepayment) {
		this.loanRepayment = loanRepayment;
	}
	
}
