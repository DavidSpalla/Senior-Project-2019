package org.apache.guacamole.jdbc.shared.user;

import java.sql.*;

public class UserDatabase {
	
	//database driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	
	//database credentials
	private static String username = "guacadmin";
	private static String password = "guacadmin";
	
	//constructors
	public UserDatabase() {
		
	}
	
	public UserDatabase(String user, String pass) {
		this.username = user;
		this.password = pass;
	}
	
	//user defined functions
	public Connection connect() {
		try {
			return DriverManager.getConnection(DB_URL, username, password);
		}catch(SQLException s) {
			s.printStackTrace();
			return null;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
