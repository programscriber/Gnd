package com.indutech.gnd.bo;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.deser.DateDeserializer;

public class RecordEventBO {
	
	public static final long EVENT_QC_RULES      = 1;
	public static final long EVENT_QC_HOLD      = 2;
	public static final long EVENT_AWB_ASSIGNED  = 4;
	public static final long EVENT_AUF_GENERATED = 5;
	
	public static final long EVENT_PRODUCT_CHANGE = 101;
	public static final long NCF_EVENT_PRODUCT_CHANGE = 102;
	
	public static final String EVENT_PRODUCT_CHANGE_STRING = "product and bin updated from : ";
	public static final String NCF_EVENT_PRODUCT_CHANGE_STRING = "product and bin updated from : ";
	public static final String PRODUCT_CHANGE_SEPERATOR = " to : ";
	
	private Long id;
	private Long recordId;
	private Long eventId;
	@JsonDeserialize(using = DateDeserializer.class)
	private Date eventDate;
	private String description;
	private String additionalInfo;

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	private String eventDateStr;
	
	public String getEventDateStr() {
		return eventDateStr;
	}
	public void setEventDateStr(String eventDateStr) {
		this.eventDateStr = eventDateStr;

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
	
	public static String getStatusInString(long eventid) {
		String result = "";
		switch ((int)eventid) {
			case (int) EVENT_QC_RULES     :
				result = "EVENT_QC_RULES";
				break;
			
			case (int) EVENT_AWB_ASSIGNED :
				result = "_AWB_ASSIGNED";
				break;
				
			case (int) EVENT_AUF_GENERATED:
				result = "EVENT_AUF_GENERATED";
				break;
		}
		return result;
	}
	
}
