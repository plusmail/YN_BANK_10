package com.yi.dao;

import java.sql.SQLException;

import com.yi.dto.Contribution;

public interface ContributionDao {
	public abstract Contribution bankTotalAmount() throws SQLException; 
}
