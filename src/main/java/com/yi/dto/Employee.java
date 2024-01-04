package com.yi.dto;

import java.util.Arrays;

public class Employee {
	private String empCode;
	private String empName;
	private String empTitle;
	private String empAuth;
	private int empSalary;
	private String empTel;
	private String empId;
	private String empPwd;
	private Department dept;
	private String pic;
	private boolean empRetire;
	
	private int perf;
	private int bonus;
	private String vip;
	private String pCode;
	private String pName;
	
	private Customer customer;
	private Plan plan;
	
	
	
	public Employee() {
		
	}
	
	

    

	public Employee(String empCode, String empName, Customer customer, Plan plan) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.customer = customer;
		this.plan = plan;
	}





	public Employee(String empCode, String empName, String empTitle, String empAuth, int empSalary, String empTel,
			String empId, String empPwd, Department dept, String pic) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.empAuth = empAuth;
		this.empSalary = empSalary;
		this.empTel = empTel;
		this.empId = empId;
		this.empPwd = empPwd;
		this.dept = dept;
		this.pic = pic;
	}

    public Employee(String empCode, String empName, String empTitle, String empAuth, int empSalary, String empTel,
		String empId, String empPwd, Department dept, String pic, boolean empRetire) {
	super();
	this.empCode = empCode;
	this.empName = empName;
	this.empTitle = empTitle;
	this.empAuth = empAuth;
	this.empSalary = empSalary;
	this.empTel = empTel;
	this.empId = empId;
	this.empPwd = empPwd;
	this.dept = dept;
	this.pic = pic;
	this.empRetire = empRetire;
}







	public Employee(String empCode, String empName, String empTitle, String pic, int perf, int bonus, String vip) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.pic = pic;
		this.perf = perf;
		this.bonus = bonus;
		this.vip = vip;
	
	}



	//실적테이블을 위한 생성자
	public Employee(String empCode, String empName, String empTitle, int perf, int bonus, String pCode, String pName) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.perf = perf;
		this.bonus = bonus;
	
		this.pCode = pCode;
		this.pName = pName;
	}

    

    public Employee(String empCode, String empName, String empTitle, String pic, int perf, int bonus) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.pic = pic;
		this.perf = perf;
		this.bonus = bonus;
	}



	// 직급별 테스트용 
	public Employee(String empCode, String empName, String empTitle, String empAuth, int empSalary, String empTel,
			String empId) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.empAuth = empAuth;
		this.empSalary = empSalary;
		this.empTel = empTel;
		this.empId = empId;
	}

    

	public Customer getCustomer() {
		return customer;
	}





	public void setCustomer(Customer customer) {
		this.customer = customer;
	}





	public Plan getPlan() {
		return plan;
	}





	public void setPlan(Plan plan) {
		this.plan = plan;
	}





	public String getpCode() {
		return pCode;
	}



	public void setpCode(String pCode) {
		this.pCode = pCode;
	}



	public String getpName() {
		return pName;
	}



	public void setpName(String pName) {
		this.pName = pName;
	}



	public int getPerf() {
		return perf;
	}



	public void setPerf(int perf) {
		this.perf = perf;
	}



	public int getBonus() {
		return bonus;
	}



	public void setBonus(int bonus) {
		this.bonus = bonus;
	}



	public String getVip() {
		return vip;
	}



	public void setVip(String vip) {
		this.vip = vip;
	}
	



	public boolean isEmpRetire() {
		return empRetire;
	}



	public void setEmpRetire(boolean empRetire) {
		this.empRetire = empRetire;
	}



	public Employee(String empCode, String empName, String empTitle, String empAuth, int empSalary, String empTel,
			String empId, String empPwd, Department dept) {
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.empAuth = empAuth;
		this.empSalary = empSalary;
		this.empTel = empTel;
		this.empId = empId;
		this.empPwd = empPwd;
		this.dept = dept;
	}

	//네개만 있는 emp
	public Employee(String empCode, String empName, String empTitle, String empAuth) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.empAuth = empAuth;
	}
	
	

	public Employee(String empCode, String empName, String empTitle, String empAuth, Department dept) {
		super();
		this.empCode = empCode;
		this.empName = empName;
		this.empTitle = empTitle;
		this.empAuth = empAuth;
		this.dept = dept;
	}



	public Employee(String empId, String empPwd) {
		this.empId = empId;
		this.empPwd = empPwd;
	}

	
	
	public Employee(String empName) {
		super();
		this.empName = empName;
	}

	public String getEmpCode() {
		return empCode;
	}
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getEmpTitle() {
		return empTitle;
	}
	public void setEmpTitle(String empTitle) {
		this.empTitle = empTitle;
	}
	public String getEmpAuth() {
		return empAuth;
	}
	public void setEmpAuth(String empAuth) {
		this.empAuth = empAuth;
	}
	public int getEmpSalary() {
		return empSalary;
	}
	public void setEmpSalary(int empSalary) {
		this.empSalary = empSalary;
	}
	public String getEmpTel() {
		return empTel;
	}
	public void setEmpTel(String empTel) {
		this.empTel = empTel;
	}
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getEmpPwd() {
		return empPwd;
	}
	public void setEmpPwd(String empPwd) {
		this.empPwd = empPwd;
	}
	public Department getDept() {
		return dept;
	}
	public void setDept(Department dept) {
		this.dept = dept;
	}



	public String getPic() {
		return pic;
	}



	public void setPic(String pic) {
		this.pic = pic;
	}



	@Override
	public String toString() {
		return "Employee [empCode=" + empCode + ", empName=" + empName + ", empTitle=" + empTitle + ", empAuth="
				+ empAuth + ", empSalary=" + empSalary + ", empTel=" + empTel + ", empId=" + empId + ", empPwd="
				+ empPwd + ", dept=" + dept + ", pic=" + pic + ", perf=" + perf + ", bonus=" + bonus
				+ ", vip=" + vip + "]";
	}
	
	
	
}
