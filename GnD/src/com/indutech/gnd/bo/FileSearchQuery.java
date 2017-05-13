package com.indutech.gnd.bo;

import javax.xml.bind.annotation.XmlElement;

public class FileSearchQuery {

	@XmlElement( name="ReceivedDate")  
	 private ProcessingDate receivedDate;
	@XmlElement( name="FileName")
	private String fileName;
	@XmlElement( name="bank")
	private String Bank;
	@XmlElement( name="branch")
	private String Branch;
	@XmlElement( name="status")
	private String Status;
	public ProcessingDate getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(ProcessingDate receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getBank() {
		return Bank;
	}
	public void setBank(String bank) {
		Bank = bank;
	}
	public String getBranch() {
		return Branch;
	}
	public void setBranch(String branch) {
		Branch = branch;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
}
