package com.yi.dto;

public class Plan {
	private String planCode;
	private String planDetail;
	private String planName;
	private String planDesc;
	private String planDiv;
	public Plan() {
		
	}
	public Plan(String planCode) {
		this.planCode = planCode;
	}
	public Plan(String planCode, String planDetail, String planName, String planDesc, String planDiv) {
		this.planCode = planCode;
		this.planDetail = planDetail;
		this.planName = planName;
		this.planDesc = planDesc;
		this.planDiv = planDiv;
	}
	public String getPlanCode() {
		return planCode;
	}
	public void setPlanCode(String planCode) {
		this.planCode = planCode;
	}
	public String getPlanDetail() {
		return planDetail;
	}
	public void setPlanDetail(String planDetail) {
		this.planDetail = planDetail;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public String getPlanDesc() {
		return planDesc;
	}
	public void setPlanDesc(String planDesc) {
		this.planDesc = planDesc;
	}
	public String getPlanDiv() {
		return planDiv;
	}
	public void setPlanDiv(String planDiv) {
		this.planDiv = planDiv;
	}
	@Override
	public String toString() {
		return String.format("%s", planName);
	}
	
}
