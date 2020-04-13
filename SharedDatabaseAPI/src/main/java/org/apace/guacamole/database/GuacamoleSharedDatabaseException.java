package org.apace.guacamole.database;

import org.apache.guacamole.GuacamoleException;

public class GuacamoleSharedDatabaseException extends GuacamoleException{

	public GuacamoleSharedDatabaseException(String message) {
		super(message);
	}
	
	public GuacamoleSharedDatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
