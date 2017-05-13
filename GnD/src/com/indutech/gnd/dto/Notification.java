package com.indutech.gnd.dto;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NOTIFICATION")
public class Notification {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Basic(optional = false)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name = "NOTIFICATION_TYPE")
	private Long notification_type;

	@Column(name = "ENABLE")
	private Long enable;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "THRESHOLD")
	private Long threshold;

	@Column(name = "DATE_UPDATED")
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
