package com.indutech.gnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name="txn_record_event_log")
public class RecordEvent {

	@Id
	@Column(name="id", unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="record_id")
	private Long recordId;
	
	@Column(name="event_id")
	private Long eventId;
	
//	@Temporal(TemporalType.TIMESTAMP)
//	@CreationTimestamp
	@Column(name="event_date", nullable=false)
	private Date eventDate;
	
	@Column(name="info")
	private String description;
	
	@Column(name="additional_info")
	private String additionalInfo;
	
	

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRecordId() {
		return recordId;
	}

	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Date getEventDate() {
		return eventDate;
	}

	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
	
}
