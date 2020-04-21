/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apace.guacamole.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Base implementation of SharedDatabase which provides default implementations of
 * most functions.
 */
public class AbstractSharedDatabase implements SharedDatabase {	
	/**
	 * @author raymond
	 * 
	 * Connects to a shared database
	 * 
	 * @param connectionUrl
	 * 
	 * @return
	 * 		Returns a connection to the specified database (or a null
	 * 		connection if database is inaccessible or doesn't exist)
	 * 
	 * */
	public Connection connect(String connectionUrl) throws SQLException {
		Connection conn = null;
		
		if(connectionUrl != null)
			conn = DriverManager.getConnection(connectionUrl);
		
		return conn;
	}
	
	/**
	 * @author raymond
	 * 
	 * Connects to a secured shared database
	 * 
	 * @param connectionUrl
	 * 
	 * @param credentials
	 * 
	 * @return
	 * 		Returns a connection to the specified database (or a null
	 * 		connection if database is inaccessible or doesn't exist)
	 * 
	 * */
	public Connection connect(String connectionUrl, ConcurrentHashMap<String, 
			String> credentials) throws SQLException {
		
		Connection conn = null;
		if(connectionUrl != null)
			conn = DriverManager.getConnection(connectionUrl, 
				credentials.get(CredentialKeys.USERNAME.toString()),
				credentials.get(CredentialKeys.PASSWORD.toString()));

		return conn;
	}
	
	/**
	 * @author raymond
	 * 
	 * Executes an INSERT, UPDATE, or DELETE command
	 * 
	 * @param connection
	 * 
	 * @param sqlCmd
	 * 
	 * @return
	 * 		Boolean indicating if the command was executed successfully
	 * 		
	 * */
	public boolean executeQuery(Connection connection, String sqlCmd) {
		try {
			if(connection.isClosed() || sqlCmd == null)
				return false;
			
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(sqlCmd);
			
			return true;
		}
		catch(SQLException e) {
			return false;
		}
	}
	
	/**
	 * @author raymond
	 * 
	 * Closes a connection
	 * 
	 * @param connection
	 * 
	 * @return
	 * 		Boolean indicating if the connection is closed
	 * */
	public boolean closeConnection(Connection connection) {
		try {
			if(!connection.isClosed()) {
				connection.close();
				return true;
			}
			
			return false;
		}
		catch(SQLException e) {
			return false;
		}
	}
	
	/**
	 * @author raymond
	 * 
	 * Returns a given array as a comma seperated string
	 * 
	 * @param values
	 * 
	 * @return
	 * 		Comma seperated list of values
	 * */
	public String convertToString(String values[]) {
		String valueString = null;
		String entry;
		
		for(int i = 0; i < values.length; i++) {
			if(i == values.length - 1)
				entry = values[i];
			else
				entry = values[i] + ", ";
			
			valueString += entry;
		}
		
		return valueString;
	}
	
	/**
	 * @author raymond
	 * 
	 * Returns an sql insert statement made up of the 
	 * given the table name, column names, and 
	 * values of the columns
	 * 
	 * @param table
	 * 
	 * @param columnValues
	 * 
	 * @param values
	 * 
	 * @return
	 * 		A fully built sql insert statement
	 * 
	 * @throws GuacamoleSharedDatabaseException 
	 * */
	public String getInsertStatement(String table, String columnNames[], String columnValues[], 
			String values[]) throws GuacamoleSharedDatabaseException {
		String colNumErr = "Number of values being added does not match number of columns in table.";
		String insertStatement = null;
		
		if(table == null || 
		   columnValues == null) {
			return insertStatement;
		}
		
		if(values.length != columnNames.length)
			throw new GuacamoleSharedDatabaseException(colNumErr);

		insertStatement = 
				"INSERT INTO " +
				table +
				" (" +
				convertToString(columnValues) +
				") VALUES (" +
				convertToString(values) +
				");";
		
		return insertStatement;
	}
	
	/**
	 * @author raymond
	 * 
	 * Returns an sql delete statement that will delete an entry
	 * with the given username from the given table
	 * 
	 * @param table
	 * 
	 * @param username
	 * 
	 * @return
	 * 		Fully valid sql delete statement
	 * */
	public String getDeleteStatement(String table, String columnNames[], String username) {
		String deleteStatement = null;
		
		if(table == null || 
				columnNames == null) {
			return deleteStatement;
		}
		
		deleteStatement = 
				"DELETE FROM " +
				table + 
				" WHERE NAME = '" +
				username +
				"';";
		
		return deleteStatement;
	}
}
