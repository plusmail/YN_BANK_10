package com.yi.dto;

import java.util.Date;

public class Card {
	private String cardNum;
	private Customer custCode;
	private Plan planCode;
	private String cardSecuCode;
	private Date cardIssueDate;
	private int cardLimit;
	private long cardBalance;
	private Employee employee;
	private BankBook bankbook;
	public Card() {
		
	}
	public Card(Customer custCode) {
		this.custCode = custCode;
	}
	
	public Card(String cardNum, Customer custCode, Plan planCode, String cardSecuCode, Date cardIssueDate) {
		this.cardNum = cardNum;
		this.custCode = custCode;
		this.planCode = planCode;
		this.cardSecuCode = cardSecuCode;
		this.cardIssueDate = cardIssueDate;
	}
	
	public Card(String cardNum, Customer custCode, Plan planCode, String cardSecuCode, Date cardIssueDate,
			int cardLimit, long cardBalance, Employee employee, BankBook bankbook) {
		this.cardNum = cardNum;
		this.custCode = custCode;
		this.planCode = planCode;
		this.cardSecuCode = cardSecuCode;
		this.cardIssueDate = cardIssueDate;
		this.cardLimit = cardLimit;
		this.cardBalance = cardBalance;
		this.employee = employee;
		this.bankbook = bankbook;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
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
	public String getCardSecuCode() {
		return cardSecuCode;
	}
	public void setCardSecuCode(String cardSecuCode) {
		this.cardSecuCode = cardSecuCode;
	}
	public Date getCardIssueDate() {
		return cardIssueDate;
	}
	public void setCardIssueDate(Date cardIssueDate) {
		this.cardIssueDate = cardIssueDate;
	}
	public int getCardLimit() {
		return cardLimit;
	}
	public void setCardLimit(int cardLimit) {
		this.cardLimit = cardLimit;
	}
	public long getCardBalance() {
		return cardBalance;
	}
	public void setCardBalance(long cardBalance) {
		this.cardBalance = cardBalance;
	}
	
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	public BankBook getBankbook() {
		return bankbook;
	}
	public void setBankbook(BankBook bankbook) {
		this.bankbook = bankbook;
	}
	@Override
	public String toString() {
		return String.format(
				"Card [cardNum=%s, custCode=%s, planCode=%s, cardSecuCode=%s, cardIssueDate=%s, cardLimit=%s, cardBalance=%s, employee=%s, bankbook=%s]",
				cardNum, custCode, planCode, cardSecuCode, cardIssueDate, cardLimit, cardBalance, employee, bankbook);
	}
	
}
