package com.yi.jdbc;

import java.sql.DriverManager;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


@SuppressWarnings("serial")
public class DBCPInit extends HttpServlet {
	@Override
	public void init() throws ServletException {
		loadJDBCDriver();
		initConnectionPool();
	}
	private void loadJDBCDriver() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e) {
			throw new RuntimeException("fail to load JDBC Driver",e);
		}
	}
	private void initConnectionPool() {
		try {
			String url = "jdbc:mysql://localhost:3306/bank?useUnicode=true&characterEncoding=utf8";
			String user = "root";
			String password = "edurootroot";
			ConnectionFactory conFactory = new DriverManagerConnectionFactory(url, user,password);
			PoolableConnectionFactory poolableConFactory = new PoolableConnectionFactory(conFactory, null);
			poolableConFactory.setValidationQuery("select 1");
			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L*60L*5L);
			poolConfig.setTestWhileIdle(true);
			poolConfig.setMinIdle(10);
			poolConfig.setMaxTotal(100);
			GenericObjectPool<PoolableConnection> connectionPool = new GenericObjectPool<>(poolableConFactory, poolConfig);
			poolableConFactory.setPool(connectionPool);
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = (PoolingDriver)DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			driver.registerPool("bank", connectionPool);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
}
