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
	 * Sql database table name
	 * */
	private String tableName;
	
	/**
	 * Comma seperated string for database column names
	 * */
	private String tableColumns;
	
	/**
	 * Comma seperated string for database column values
	 * */
	private String tableColumnValues;
	
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
	 * Sets table column names
	 * 
	 * @param columnNames
	 * */
	public void setTableColumns(String columnNames[]) {
		String entry;
		
		for(int i = 0; i < columnNames.length; i++) {
			if(i == columnNames.length - 1)
				entry = columnNames[i];
			else
				entry = columnNames[i] + ", ";
			
			this.tableColumns += entry;
		}		
	}
	
	/**
	 * Sets table column names
	 * 
	 * @param columnNames
	 * */
	public void setTableColumns(String columnNames) {
		this.tableColumns = columnNames;
	}
	
	/**
	 * Sets table column values
	 * 
	 * @param values
	 * */
	public void setTableColumnValues(String values[]) {
		String entry;
		
		for(int i = 0; i < values.length; i++) {
			if(i == values.length - 1)
				entry = values[i];
			else
				entry = values[i] + ", ";
			
			this.tableColumnValues += entry;
		}		
	}
	
	/**
	 * Sets table column values
	 * 
	 * @param values
	 * */
	public void setTableColumnValues(String values) {
		this.tableColumnValues = values;
	}
	
	/**
	 * Returns a comma seperated String of the 
	 * different column names in the Sql SharedDatabase
	 * 
	 * @return
	 * 		Comma seperated String of column names
	 * */
	public String getTableColumns() {
		return this.tableColumns;
	}
	
	/**
	 * Sets current table name in the SharedDatabase
	 * */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * Returns the current table name
	 * 
	 * @return
	 * 		SQL SharedDatabase table name
	 * */
	public String getTableName() {
		return this.tableName;
	}
	
	/**
	 * Returns an sql insert statement made up of the 
	 * current set for the table name, column names, and 
	 * values of the columns
	 * 
	 * @return
	 * 		A fully built sql insert statement
	 * */
	private String getInsertStatement() {
		if(this.tableName == null || 
				this.tableColumns == null || 
				this.tableColumnValues == null) {
			return null;
		}

		String insertStatement = 
				"INSERT INTO " +
				this.tableName +
				" (" +
				this.tableColumns +
				") VALUES (" +
				this.tableColumnValues +
				");";
		return insertStatement;
	}
}
