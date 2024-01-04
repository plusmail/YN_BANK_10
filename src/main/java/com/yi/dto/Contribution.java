package com.yi.dto;

public class Contribution {
	private long totalDepositWithdrawAmount;
	private long totalLoanAmount;
	private long totalContribution;
	public Contribution() {
		
	}
	public Contribution(long totalDepositWithdrawAmount, long totalLoanAmount, long totalContribution) {
		this.totalDepositWithdrawAmount = totalDepositWithdrawAmount;
		this.totalLoanAmount = totalLoanAmount;
		this.totalContribution = totalContribution;
	}
	public long getTotalDepositWithdrawAmount() {
		return totalDepositWithdrawAmount;
	}
	public void setTotalDepositWithdrawAmount(long totalDepositWithdrawAmount) {
		this.totalDepositWithdrawAmount = totalDepositWithdrawAmount;
	}
	public long getTotalLoanAmount() {
		return totalLoanAmount;
	}
	public void setTotalLoanAmount(long totalLoanAmount) {
		this.totalLoanAmount = totalLoanAmount;
	}
	public long getTotalContribution() {
		return totalContribution;
	}
	public void setTotalContribution(long totalContribution) {
		this.totalContribution = totalContribution;
	}
	@Override
	public String toString() {
		return String.format("Contribution [totalDWAmount=%s, totalLoanAmount=%s, totalContribution=%s]", totalDepositWithdrawAmount,
				totalLoanAmount, totalContribution);
	}
	
}
