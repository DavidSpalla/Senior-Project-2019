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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.auth.ConnectionRecord;
import org.apache.guacamole.net.auth.Permissions;
import org.apache.guacamole.net.auth.RelatedObjectSet;
import org.apache.guacamole.net.auth.permission.ObjectPermissionSet;
import org.apache.guacamole.net.auth.permission.SystemPermissionSet;
import org.apache.guacamole.protocol.GuacamoleConfiguration;

/**
 * Base implementation of DatabaseUser which provides default implementations of
 * most functions.
 */
public class AbstractDatabaseUser implements DatabaseUser {
	/**
	 * User's name
	 * */
	private String name;
	
	/**
	 * User's unique identifier
	 **/
	private String identifier;
	
	/**
	 * 
	 **/
	private Map<String, String> userCredentials;
	
	/**
	 * Attributes associated with user
	 * */
	private Map<String, String> attributes;
	
	/**
	 * Date which the user was last active
	 * */
	private Date lastActive = new Date();
	
	/**
	 * List of connections
	 * */	
	List<ConnectionRecord> recordList;
	
	/**
	 * Set of user groups associated with the user
	 * */
	RelatedObjectSet userGroups;
	
	/**
	 * GuacamoleConfiguration settings for this user
	 * */
	GuacamoleConfiguration config = new GuacamoleConfiguration();
	
	/**
	 * 
	 * */
	private Set<String> sharingProfileIdentifiers = new HashSet<String>();
	
	/**
	 * Number of active connections for user
	 * */
	private int activeConnections;
	
	/**
	 * Unique identifier of the user's parent
	 * */
	String parentIdentifier;
	
	/**
	 * Permissions of the user
	 **/
	ObjectPermissionSet userPermissions;
	
	/**
	 * Effective permissions
	 * */
	Permissions effectivePermissions;
	
	/**
	 * Permissions of the user's active connection
	 * */
	ObjectPermissionSet activeConnectionPermissions;
	
	/**
	 * Permissions of the user's connection groups
	 * */
	ObjectPermissionSet connectionGroupPermissions;
	
	/**
	 * Permissions of the user's connection
	 * */
	ObjectPermissionSet connectionPermissions;
	
	/**
	 * Permissions of the user's shared profile
	 * */
	ObjectPermissionSet sharingProfilePermissions;
	
	/**
	 * Permissions of the user's userGroups
	 * */
	ObjectPermissionSet userGroupPermissions;
	
	/**
	 * User's system permissions
	 * */
	SystemPermissionSet systemPermissions;
	
	public AbstractDatabaseUser(){
		userCredentials.put(CredentialKeys.USERNAME.toString(), "guacadmin");
		userCredentials.put(CredentialKeys.PASSWORD.toString(), "guacadmin");
	}
	
	public AbstractDatabaseUser(String username, String password){
		userCredentials.put(CredentialKeys.USERNAME.toString(), username);
		userCredentials.put(CredentialKeys.PASSWORD.toString(), password);
	}
	
	
	public String getPassword() {
		return userCredentials.get(CredentialKeys.PASSWORD.toString());
	}
	
	public void setPassword(String password) {
		userCredentials.replace(CredentialKeys.PASSWORD.toString(), 
				getPassword(), password);
	}

	public Date getLastActive() {
		return this.lastActive;
	}
	
	public List<ConnectionRecord> getHistory() throws GuacamoleException {
		return this.recordList;
	}

	public RelatedObjectSet getUserGroups() throws GuacamoleException {
		return this.userGroups;
	}

	public Permissions getEffectivePermissions() throws GuacamoleException {
		return this.effectivePermissions;
	}

	public String getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Map<String, String> getAttributes() {
		return this.attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}

	public ObjectPermissionSet getActiveConnectionPermissions() throws GuacamoleException {
		return this.activeConnectionPermissions;
	}

	public ObjectPermissionSet getConnectionGroupPermissions() throws GuacamoleException {
		return this.connectionGroupPermissions;
	}

	public ObjectPermissionSet getConnectionPermissions() throws GuacamoleException {
		return this.connectionPermissions;
	}

	public ObjectPermissionSet getSharingProfilePermissions() throws GuacamoleException {
		return this.sharingProfilePermissions;
	}

	public SystemPermissionSet getSystemPermissions() throws GuacamoleException {
		return this.systemPermissions;
	}

	public ObjectPermissionSet getUserPermissions() throws GuacamoleException {
		return this.userPermissions;
	}

	public ObjectPermissionSet getUserGroupPermissions() throws GuacamoleException {
		return this.userGroupPermissions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentIdentifier() {
		return this.parentIdentifier;
	}

	public void setParentIdentifier(String parentIdentifier) {
		this.parentIdentifier = parentIdentifier;
	}

	public GuacamoleConfiguration getConfiguration() {
		return this.config;
	}

	public void setConfiguration(GuacamoleConfiguration config) {
		this.config = config;
	}

	public Set<String> getSharingProfileIdentifiers() throws GuacamoleException {
		return this.sharingProfileIdentifiers;
	}

	public int getActiveConnections() {
		return this.activeConnections;
	}

}
