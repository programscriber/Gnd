package com.indutech.gnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="MASTER_CORE_FILES")
public class CoreFiles {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="FILENAME")
	private String filename;
	
	@Column(name="RECEIVED_DATE")
	private Date receivedDate;
	
	@Column(name="FILE_TYPE")
	private Long fileType;
	
	@Column(name="LINE_COUNT")
	private Long lineCount;
	
	@Column(name="STATUS")
	private Long status;
	
	@Column(name="Description", length=200)
	private String description;
	
	@Column(name="CORE_FILE_ID")
	private Long coreFileId;	
	
	@Column(name="AUF_FILE_ID")
	private Long aufFileId;
	
	@Column(name="PROCESSED_DATE")
	private Date processedDate;
	
	@Column(name="LCPC_GROUP")
	private String lcpcGroup;
		
	public Date getProcessedDate() {
		return processedDate;
	}
	public void setProcessedDate(Date processedDate) {
		this.processedDate = processedDate;
	}
	
	public String getLcpcGroup() {
		return lcpcGroup;
	}
	public void setLcpcGroup(String lcpcGroup) {
		this.lcpcGroup = lcpcGroup;
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
