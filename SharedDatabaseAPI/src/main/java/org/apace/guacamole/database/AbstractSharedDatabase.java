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
	
	private String idColumnName;
	
	public AbstractSharedDatabase(String idColumnName) {
		this.idColumnName = idColumnName;
	}
	
	/** 
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
	 * 
	 * Returns a string that can be used to execute an sql INSERT command
	 * into a given table.
	 * 
	 * @param table
	 * 
	 * @param columnNames
	 * 
	 * @param columnValues
	 * 
	 * @return
	 * 		A string that can be used to execute an sql INSERT command
	 * 
	 * @throws GuacamoleSharedDatabaseException 
	 * */
	public String getInsertStatement(String table, String columnNames[], String columnValues[]) 
			throws GuacamoleSharedDatabaseException {
		String colNumErr = "Number of values being added does not match number of columns in table.";
		String invalidTable = "Table name is null";
		String invalidColumnValues = "Column values are null";
		String insertStatement = null;
		String argument;
		
		if(table == null)
			throw new GuacamoleSharedDatabaseException(invalidTable);
		
		if(columnValues == null)
			throw new GuacamoleSharedDatabaseException(invalidColumnValues);
		
		if(columnNames.length != columnNames.length)
			throw new GuacamoleSharedDatabaseException(colNumErr);

		insertStatement = "INSERT INTO " + table + " (";
		for(int i = 0; i < columnNames.length; i++) {
			if(i != columnNames.length - 1)
				argument = columnNames[i] + ") ";
			else
				argument = columnNames[i] + ", ";
			
			insertStatement += argument;
		}
		
		insertStatement += "VALUES (";
		for(int i = 0; i < columnValues.length; i++) {
			if(i == columnValues.length - 1)
				argument = "'" + columnValues[i] + "');";
			else
				argument = "'" + columnValues[i] + "', ";
			
			insertStatement += argument;
		}
		
		return insertStatement;
	}
	
	/**
	 * 
	 * Returns a string that can be user to execute an sql DELETE 
	 * command on a particular row in a table where it contains the 
	 * the unique identifier
	 * 
	 * @param table
	 * 
	 * @param identifier
	 * 
	 * @return
	 * 		A string that can be used to execute an sql DELETE command
	 * */
	public String getDeleteStatement(String table, String identifier) 
			throws GuacamoleSharedDatabaseException{
		String deleteStatement = null;
		
		String invalidTable = "Table name is null";
		String invalidIdentifier = "Column names are null";
		
		if(table == null)
			throw new GuacamoleSharedDatabaseException(invalidTable);
		
		if(identifier == null)
			throw new GuacamoleSharedDatabaseException(invalidIdentifier);
		
		deleteStatement = 
				"DELETE FROM " +
				table + 
				" WHERE " + 
				getRowIdentifier() + 
				" = '" +
				identifier +
				"';";
		
		return deleteStatement;
	}
	
	/**
	 * 
	 * Returns a string that can be used to execute an sql UPDATE command
	 * based on a user with a unique identifier the table this user is in, 
	 * column names or the table, and the new values you want associated with 
	 * the given user.
	 * 
	 * @param table
	 * 
	 * @param columnNames
	 * 
	 * @param columnValues
	 * 
	 * @param identifier
	 * 
	 * @return
	 * 		Returns a string that can be used to execute an sql UPDATE command
	 * 
	 * @throws GuacamoleSharedDatabaseException
	 * */
	public String getUpdateStatement(String table, String columnNames[], 
			String columnValues[], String identifier) throws GuacamoleSharedDatabaseException {
		
		String invalidIdentifier = "Identifier is null";
		String invalidTableName = "Table is null";
		String lengthError = "Number of columns does not match the number of values";
		
		String setArgument;
		
		String updateStatement = null;
		
		if(table == null)
			throw new GuacamoleSharedDatabaseException(invalidTableName);
		
		if(identifier == null)
			throw new GuacamoleSharedDatabaseException(invalidIdentifier);
		
		if(columnValues.length != columnNames.length)
			throw new GuacamoleSharedDatabaseException(lengthError);
		
		updateStatement = 
				"UPDATE " +
				table +
				" SET (";
				
		for(int i = 0; i < columnNames.length; i++) {
			if(i != columnNames.length - 1)
				setArgument = columnNames[i] + " = '" + columnValues[i] + "', ";
			else
				setArgument = columnNames[i] + " = '" + columnValues[i] + "') ";
			
			updateStatement += setArgument;
		}
		
		updateStatement += "WHERE " + getRowIdentifier() + " = '" + identifier + "';";
		
		return updateStatement;
	}
	
	/**
	 * Returns a string that can be used to execute an sql SELECT command to
	 * get the highest id in the table that can then be incremented and used to 
	 * store a new entry with a new unique integer id.
	 * 
	 * @param table
	 * 
	 * @param idColumnName
	 * 
	 * @return
	 * 		Returns a string that can be used to execute a SELECT command that can then
	 * 		in turn be used to retrieve the highest integer id in the table
	 * */
	public String getNextId(String table, String idColumnName) {
		String nextId;
		
		nextId = "SELECT MAX(" + idColumnName + ") FROM " + table +";";
		
		return nextId;
	}
	
	/**
	 * Sets the unique identifier column name that each entry in the table 
	 * will have.
	 * 
	 * @param idColumnName
	 * 
	 * */
	public void setRowIdentifier(String idColumnName) {
		this.idColumnName = idColumnName;
	}
	
	/**
	 * Returns the columnName that contains an entry's unique identifier.
	 * 
	 * @return
	 * 		A string that will be the column name containing an entry's unique
	 * 		identifier.
	 * 
	 * */
	public String getRowIdentifier() {
		return this.idColumnName;
	}
}
