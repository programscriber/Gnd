package com.indutech.gnd.bo;

import java.util.Date;

import javax.persistence.Column;

public class CoreFilesBO {
	
	private Long id;	
	private String filename;	
	private Date receivedDate;
	private Long fileType;
	private Long lineCount;
	private Long status;
	private String statusData;
	private String description;
	private String fileTypeData;
	private Long coreFileId;	
	private Long aufFileId;
	private Date processedDate;
	
	
	
	
		
	public Date getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	public Long getCoreFileId() {
		return coreFileId;
	}
	public void setCoreFileId(Long coreFileId) {
		this.coreFileId = coreFileId;
	}
	public Long getAufFileId() {
		return aufFileId;
	}
	public void setAufFileId(Long aufFileId) {
		this.aufFileId = aufFileId;
	}
	
	public String getFileTypeData() {
		return fileTypeData;
	}
	public void setFileTypeData(String fileTypeData) {
		this.fileTypeData = fileTypeData;
	}
	
	public String getStatusData() {
		return statusData;
	}
	public void setStatusData(String statusData) {
		this.statusData = statusData;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getLineCount() {
		return lineCount;
	}
	public void setLineCount(Long lineCount) {
		this.lineCount = lineCount;
	}
	public Long getFileType() {
		return fileType;
	}
	public void setFileType(Long fileType) {
		this.fileType = fileType;
	}
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final String getFilename() {
		return filename;
	}
	public final void setFilename(String filename) {
		this.filename = filename;
	}
	public final Date getReceivedDate() {
		return receivedDate;
	}
	public final void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	
}
