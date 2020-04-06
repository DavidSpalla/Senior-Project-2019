package org.apace.guacamole.database;

import java.util.Date;

import org.apache.guacamole.net.auth.ActivityRecord;

public class AbstractActivityRecord implements ActivityRecord {
	Date startDate = new Date();
	Date endDate = new Date();
	
	String remoteHost;
	String username;
	boolean active;
	
	public AbstractActivityRecord(String username, Date startDate,
			Date endDate, String remoteHost, boolean active) {
		this.username = username;
		this.startDate = startDate;
		this.endDate = endDate;
		this.remoteHost = remoteHost;
		this.active = active;
	}
	
	public Date getStartDate() {
		return this.startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public String getRemoteHost() {
		return this.remoteHost;
	}

	public String getUsername() {
		return this.username;
	}

	public boolean isActive() {
		return this.active;
	}

}
