package com.yi.service;

import java.sql.SQLException;

import com.yi.dao.ContributionDao;
import com.yi.dao.EmployeeDao;
import com.yi.dao.impl.ContributionDaoImpl;
import com.yi.dao.impl.EmployeeDaoImpl;
import com.yi.dto.Contribution;
import com.yi.dto.Employee;

public class LoginService {
	private EmployeeDao empDao;
	private ContributionDao contributionDao;
	
	public LoginService() {
		empDao = EmployeeDaoImpl.getInstance();
		contributionDao = ContributionDaoImpl.getInstance();
	}

	public Employee GetLoginInfo(Employee emp) throws SQLException {
		return empDao.getEmpIdPass(emp);
	}
	public Employee GetEmpAuth(Employee emp) throws SQLException {
		return empDao.getEmpAuth(emp);
	}
	public Contribution bankTotalAmount() throws SQLException {
		return contributionDao.bankTotalAmount();
	}
}
