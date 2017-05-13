package com.indutech.gnd.bo;

import java.util.Date;

public class FileEventBO {	
	
	private Long id;	
	private Long coreId;	
	private Long eventId;	
	private Date eventDate;
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
