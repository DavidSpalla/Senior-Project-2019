package org.apace.guacamole.database;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.guacamole.net.auth.Connection;

public interface SharedDatabase {
	public Connection connect(String connectionUrl);
	public Connection connect(String connectionUrl, 
			ConcurrentHashMap<String, String> credentials);
	public boolean executeQuery(Connection connection, 
			String sqlStmt);
	public boolean closeConnection(Connection connection);
}
