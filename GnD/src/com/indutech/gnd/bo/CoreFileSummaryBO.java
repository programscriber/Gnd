package com.indutech.gnd.bo;

public class CoreFileSummaryBO {
	private String core_file_name;
	private String QC_Output_Date;
	private String LinkIndicator;
	private String File_name;
	private String File_count;
	private String Record_Counts;
	public String getCorefileName() {
		return core_file_name;
	}
	public void setCorefileName(String core_file_name) {
		this.core_file_name = core_file_name;
	}
	public String getDate() {
		return QC_Output_Date;
	}
	public void setDate(String QC_Output_Date) {
		this.QC_Output_Date = QC_Output_Date;
	}
	public String getLinkIndicator() {
		return LinkIndicator;
	}
	public void setLinkIndicator(String LinkIndicator) {
		this.LinkIndicator = LinkIndicator;
	}
	public String getFileName() {
		return File_name;
	}
	public void setFileName(String File_name) {
		this.File_name = File_name;
	}
	public String getFileCount() {
		return File_count;
	}
	public void setFileCount(String File_count) {
		this.File_count = File_count;
	}
	public String getRecordCount() {
		return Record_Counts;
	}
	public void setRecordCount(String Record_Counts) {
		this.Record_Counts = Record_Counts;
	}

}
