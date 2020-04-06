package org.apace.guacamole.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.guacamole.GuacamoleException;
import org.apache.guacamole.net.auth.ActivityRecord;
import org.apache.guacamole.net.auth.Permissions;
import org.apache.guacamole.net.auth.RelatedObjectSet;
import org.apache.guacamole.net.auth.permission.ObjectPermissionSet;
import org.apache.guacamole.net.auth.permission.Permission;
import org.apache.guacamole.net.auth.permission.SystemPermissionSet;
import org.apache.guacamole.protocol.GuacamoleConfiguration;

public class AbstractDatabaseUser implements DatabaseUser {
	/**
	 * User's name
	 * */
	private String name;
	
	/**
	 * 
	 **/
	private String identifier;
	
	/**
	 * 
	 * */
	private Map<String, String> userCredentials;
	
	/**
	 * 
	 * */
	private Map<String, String> attributes;
	
	/**
	 * 
	 * */
	private Date lastActive = new Date();
	
	/**
	 * 
	 * */
	List<ActivityRecord> activityRecordList = 
			new ArrayList<ActivityRecord>();
	
	/**
	 * 
	 * */
	RelatedObjectSet userGroups;
	
	/**
	 * 
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
	 * 
	 * */
	String parentIdentifier;
	
	ObjectPermissionSet userPermissions;
	
	/**
	 * Effective permissions
	 * */
	Permissions effectivePermissions;
	
	/**
	 * 
	 * */
	ObjectPermissionSet activeConnectionPermissions;
	
	/**
	 * 
	 * */
	ObjectPermissionSet connectionGroupPermissions;
	
	/**
	 * 
	 * */
	ObjectPermissionSet connectionPermissions;
	
	/**
	 * 
	 * */
	ObjectPermissionSet sharingProfilePermissions;
	
	/**
	 * 
	 * */
	ObjectPermissionSet userGroupPermissions;
	
	/**
	 * 
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

	public List<? extends ActivityRecord> getHistory() throws GuacamoleException {
		//TODO implement getHistory()
		return this.activityRecordList;
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
