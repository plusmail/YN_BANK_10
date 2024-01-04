<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.Connection"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		Connection con = null;
		try {
			String jdbcDriver = "jdbc:apache:commons:dbcp:bank";
			con = DriverManager.getConnection(jdbcDriver);
			if(con != null) {
				out.println("커넥션 풀에 연결되었습니다");
			}
			else {
				out.println("커넥션 풀 연결에 실패하였습니다");
			}
		}
		finally {
			if(con != null) {
				con.close();
			}
		}
		
	%>
</body>
</html>