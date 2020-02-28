package org.apache.guacamole.jdbc.shared.user;

import java.sql.*;

public class UserDatabase {	
	//database credentials
	private static String username;
	private static String password;
	
	//constructors
	public UserDatabase() {
		username = "guacadmin";
		password = "guacadmin";
	}
	
	public UserDatabase(String user, String pass) {
		this.username = user;
		this.password = pass;
	}
	
	//user defined functions
	public Connection connect(String url) {
		try {
			return DriverManager.getConnection(url, username, password);
		}catch(SQLException s) {
			s.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		};
		
		return null;
	}
	
	//user defined functions
	public Connection connect(String url, String username, String password) {
		try {
			return DriverManager.getConnection(url, username, password);
		}catch(SQLException s) {
			s.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		};
		
		return null;
	}
	
	public boolean executeQuery(Connection conn, String sqlStmt) {
		try {
			Statement stmt = conn.createStatement();
			stmt.executeUpdate(sqlStmt);
			return true;
		}catch(SQLException s) {
			s.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean closeConnection(Connection conn) {
		try {
			if(conn != null)
				conn.close();
		}catch(SQLException s) {
			s.printStackTrace();
		}
		
		return false;
	}
}
