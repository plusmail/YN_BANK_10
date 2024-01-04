package com.yi.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yi.dao.ContributionDao;
import com.yi.dto.Contribution;

public class ContributionDaoImpl implements ContributionDao {
	private static final ContributionDaoImpl instance = new ContributionDaoImpl();
	private ContributionDaoImpl() {};
	String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
	public static ContributionDaoImpl getInstance() {
		return instance;
	}

	@Override
	public Contribution bankTotalAmount() throws SQLException {
		String sql = "select * from bank_totalbalance";
		try(Connection con = DriverManager.getConnection(jdbcDriver);
				PreparedStatement pstmt = con.prepareStatement(sql);
				ResultSet rs = pstmt.executeQuery()) {
			while(rs.next()) {
				return getContribution(rs);
			}
		}
		return null;
	}

	private Contribution getContribution(ResultSet rs) throws SQLException {
		long totalDepositWithdrawAmount = rs.getLong("totalBankBookAmount");
		long totalLoanAmount = rs.getLong("totalLoanAmount");
		long totalContribution = rs.getLong("totalBankAmount");
		return new Contribution(totalDepositWithdrawAmount, totalLoanAmount, totalContribution);
	}

}
