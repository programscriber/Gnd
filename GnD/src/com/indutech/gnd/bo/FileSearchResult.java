package com.indutech.gnd.bo;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  
public class FileSearchResult {

	@XmlElement( name="ReceivedDate")  
	 private Date receivedDate;
	@XmlElement( name="FileName")
	private String fileName;
	@XmlElement( name="FileType")
	private String fileType;
	@XmlElement( name="NoOfRecods")
	private String noOfRecods;
	@XmlElement( name="bank")
	private String Bank;
	@XmlElement( name="branch")
	private String Branch;
	@XmlElement( name="status")
	private String Status;
	
	public Date getReceivedDate() {
		return receivedDate;
	}
	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getNoOfRecods() {
		return noOfRecods;
	}
	public void setNoOfRecods(String noOfRecods) {
		this.noOfRecods = noOfRecods;
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
