package com.indutech.gnd.bo;

import java.util.Date;

public class NotificationsBO {

	private Long id;

	private Long notification_type;

	private Long enable;

	private String email;

	private Long threshold;

	private Date date_update;

	public Date getDate_update() {
		return date_update;
	}

	public void setDate_update(Date date_update) {
		this.date_update = date_update;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNotification_type() {
		return notification_type;
	}

	public void setNotification_type(Long notification_type) {
		this.notification_type = notification_type;
	}

	public Long getEnable() {
		return enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getThreshold() {
		return threshold;
	}

	public void setThreshold(Long threshold) {
		this.threshold = threshold;
	}

}
