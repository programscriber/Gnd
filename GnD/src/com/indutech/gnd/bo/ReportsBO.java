package com.indutech.gnd.bo;

public class ReportsBO {	

	private Long id;
	
	private String reportName;
	
	private String sourcePath;
	
	private String destinationPath;
	
	private Long sequence;
	
	private String allowedroles;
	
//	private String fileType;	
//
//	public String getFileType() {
//		return fileType;
//	}
//
//	public void setFileType(String fileType) {
//		this.fileType = fileType;
//	}

	public String getAllowed_roles() {
		return allowedroles;
	}

	public void setAllowed_roles(String allowedroles) {
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
