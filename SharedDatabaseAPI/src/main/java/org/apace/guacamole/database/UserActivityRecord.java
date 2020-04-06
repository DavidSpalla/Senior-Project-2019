package org.apace.guacamole.database;

import java.util.Date;

public class UserActivityRecord extends AbstractActivityRecord{
	public UserActivityRecord(String username, Date startDate,
			Date endDate, String remoteHost, boolean active) {
		super(username, startDate, endDate, remoteHost, active);
	}
}
