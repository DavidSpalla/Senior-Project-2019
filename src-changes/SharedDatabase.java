package org.apache.guacamole.jdbc.shared.user;

import java.sql.Connection;
import java.util.concurrent.ConcurrentHashMap;

public interface SharedDatabase {
	public Connection connect(String connectionUrl);
	public Connection connect(String connectionUrl, 
			ConcurrentHashMap<String, String> credentials);
	public boolean executeQuery(Connection connection, 
			String sqlStmt);
	public boolean closeConnection(Connection connection);
}
