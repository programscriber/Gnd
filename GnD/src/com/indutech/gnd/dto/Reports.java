package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="Reports")
public class Reports {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="REPORT_NAME")
	private String reportName;
	
	@Column(name="SOURCE_PATH")
	private String sourcePath;
	
	@Column(name="DESTINATION_PATH")
	private String destinationPath;
	
	@Column(name="SEQUENCE")
	private Long sequence;
	
	@Column(name="allowed_roles")
	private String allowedroles;
	
//	@Column(name="FILE_TYPE")
//	private String fileType;
//	
//	
//
//	public String getFileType() {
//		return fileType;
//	}
//
//	public void setFileType(String fileType) {
//		this.fileType = fileType;
//	}

	
	public String getAllowedroles() {
		return allowedroles;
	}

	public void setAllowedroles(String allowedroles) {
		this.allowedroles = allowedroles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	

	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public String getDestinationPath() {
		return destinationPath;
	}

	public void setDestinationPath(String destinationPath) {
		this.destinationPath = destinationPath;
	}

	public Long getSequence() {
		return sequence;
	}

	public void setSequence(Long sequence) {
		this.sequence = sequence;
	}
	
	
}
