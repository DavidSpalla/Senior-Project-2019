package org.apache.guacamole.jdbc.shared.user;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserDatabase {
	//database credentials
	private Map<String, String> databaseCredentials = new 
			ConcurrentHashMap<String, String>();
	
	//constructors
	
	/**
	 * creates a new UserDatabase instance with default credentials
	 * */
	public UserDatabase() {
		databaseCredentials.put("username", "guacadmin");
		databaseCredentials.put("password", "guacadmin");
	}
	
	/**
	 * Returns an SQL connection provided by the given url
	 * 
	 * @param username
	 * 		Sql database username 
	 * 
	 * @param password
	 * 		Sql database password
	 * */
	public UserDatabase(String username, String password) {
		databaseCredentials.put("username", username);
		databaseCredentials.put("password", password);
	}
	
	//user defined functions
	
	/**
	 * Returns an SQL connection provided by the given connection 
	 * url
	 * 
	 * @param connectionUrl
	 * 		The url that the database is located at
	 * 
	 * @param credentials
	 * 		ConcurrentHashMap containing sql database credentials 
	 * 		(i.e username and password)
	 * */
	public Connection connect(String connectionUrl) {
		try {
			return DriverManager.getConnection(
					connectionUrl, 
					databaseCredentials.get("username"), 
					databaseCredentials.get("password"));
		}catch(SQLException s) {
			s.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		};
		
		return null;
	}
	
	/**
	 * Returns an SQL connection provided by the given connection 
	 * url
	 * 
	 * @param connectionUrl
	 * 		The url that the database is located at
	 * 
	 * @param credentials
	 * 		ConcurrentHashMap containing sql database credentials 
	 * 		(i.e username and password)
	 * */
	public Connection connect(String connectionUrl, ConcurrentHashMap<String, String> credentials) {
		try {
			return DriverManager.getConnection(
					connectionUrl, 
					credentials.get("username"), 
					credentials.get("password"));
		}catch(SQLException s) {
			s.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		};
		
		return null;
	}
	
	/**
	 * Executes an sql statement on a given connection
	 * 
	 * @param connection
	 * 		Sql connection specified
	 * 
	 * @param sqlStmt
	 * 		Sql executable command
	 * */
	public boolean executeQuery(Connection connection, String sqlStmt) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sqlStmt);
			return true;
		}catch(SQLException s) {
			s.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	/**
	 * Closes a given connection
	 * 
	 * @param connection
	 * 		Sql connection being closed
	 * */
	public boolean closeConnection(Connection connection) {
		try {
			if(!connection.isClosed()) {
				connection.close();
				return true;
			}
		}catch(SQLException s) {
			s.printStackTrace();
		}
		
		return false;
	}
	/**
	 * Returns username credential for sql database
	 * */
	public String getUsername() {
		return databaseCredentials.get("username");
	}
	
	/**
	 * Sets username credential for sql database
	 * 
	 * @param newUsername
	 * 		Set username credential for sql database
	 * */
	public void setUsername(String newUsername) {
		databaseCredentials.replace("username", newUsername);
	}
	
	/**
	 * Returns password credential for sql database
	 * */
	public String getPassword() {
		return databaseCredentials.get("password");
	}
	
	/**
	 * Sets password credential for sql database
	 * 
	 * @param newUsername
	 * 		Set password credential for sql database
	 * */
	public void setPassword(String newPassword) {
		databaseCredentials.replace("password", newPassword);
	}
}
