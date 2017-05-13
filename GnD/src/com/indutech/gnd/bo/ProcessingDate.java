package com.indutech.gnd.bo;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name="ProcessingDate")
public class ProcessingDate {

	private Date processingDateFrom;
	private Date processingDateTo;
	@XmlElement( name="ProcessingDateFrom")  
	public Date getProcessingDateFrom() {
		return processingDateFrom;
	}
	public void setProcessingDateFrom(Date processingDateFrom) {
		this.processingDateFrom = processingDateFrom;
	}
	@XmlElement( name="ProcessingDateTo")  
	public Date getProcessingDateTo() {
		return processingDateTo;
	}
	public void setProcessingDateTo(Date processingDateTo) {
		this.processingDateTo = processingDateTo;
	}
	
	
}
