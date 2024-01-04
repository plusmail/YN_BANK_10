package com.yi.dto;

public class Department {
	private int deptNo;
	private String deptName;
	
	public Department() {
		
	}
	
	public Department(int deptNo, String deptName) {
		this.deptNo = deptNo;
		this.deptName = deptName;
	}
	

	public Department(int deptNo) {
		super();
		this.deptNo = deptNo;
	}
	
	

	public Department(String deptName) {
		super();
		this.deptName = deptName;
	}

	public int getDeptNo() {
		return deptNo;
	}
	public void setDeptNo(int deptNo) {
		this.deptNo = deptNo;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Override
	public String toString() {
		return String.format("%s(%d)", deptName,deptNo);
	}
	
	
	
}
