package org.apace.guacamole.database;

import java.util.concurrent.ConcurrentHashMap;

import org.apache.guacamole.net.auth.Connection;

public class AbstractSharedDatabase implements SharedDatabase {

	public Connection connect(String connectionUrl) {
		// TODO implement connect()
		return null;
	}

	public Connection connect(String connectionUrl, ConcurrentHashMap<String, String> credentials) {
		// TODO implement connect()
		return null;
	}

	public boolean executeQuery(Connection connection, String sqlStmt) {
		// TODO implement executeQuery()
		return false;
	}

	public boolean closeConnection(Connection connection) {
		// TODO implement closeConnection()
		return false;
	}

}
