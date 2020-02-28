package org.apache.guacamole.jdbc.shared.user;

import java.sql.*;

public class DatabaseSetup {
	//database driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";
	
	UserDatabase userdb = new UserDatabase();
	Connection conn = userdb.connect(DB_URL);
	
	public boolean validConnection(Connection conn) {
		return !(conn == null);
	}
	
	public boolean createTable() throws SQLException {
		try {
			
		}
		
		return false;
	}
	
}
