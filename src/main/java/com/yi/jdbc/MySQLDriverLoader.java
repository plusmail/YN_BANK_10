package com.yi.jdbc;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;

@SuppressWarnings("serial")
public class MySQLDriverLoader extends HttpServlet {
	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(Exception e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}
