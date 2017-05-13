package com.indutech.gnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TXN_CORE_FILE_EVENT_LOG")
public class FileEvent {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="CORE_ID")
	private Long coreId;
	
	@Column(name="EVENT_ID")
	private Long eventId;
	
	@Column(name="EVENT_DATE")
	private Date eventDate;

	@Column(name="info")
	private String info;

	public final Long getId() {
		return id;
	}

	public final void setId(Long id) {
		this.id = id;
	}

	public final Long getCoreId() {
		return coreId;
	}

	public final void setCoreId(Long coreId) {
		this.coreId = coreId;
	}

	public final Long getEventId() {
		return eventId;
	}

	public final void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public final Date getEventDate() {
		return eventDate;
	}

	public final void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public final String getInfo() {
		return info;
	}

	public final void setInfo(String info) {
		this.info = info;
	}
	
	
}
