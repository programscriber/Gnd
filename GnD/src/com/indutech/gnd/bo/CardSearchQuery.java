package com.indutech.gnd.bo;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CardSearchQuery {

	private AccountRange accountRange;
	private String  awb;
	private String rsn;
	
	private String  mobileNo;
	
	private ProcessingDate processingDate;
	
	private String applicationNo;
	 
	private String  bankName;
	
	private String branch;
	 
	private String  recordStatus;
	 
	private String productCode;
	
	@XmlElement( name="AccountRange")
	public AccountRange getAccountRange() {
		return accountRange;
	}
	public void setAccountRange(AccountRange accountRange) {
		this.accountRange = accountRange;
	}
	@XmlElement( name="AWB")  
	public String getAwb() {
		return awb;
	}
	public void setAwb(String awb) {
		this.awb = awb;
	}
	@XmlElement( name="RSN")  
	public String getRsn() {
		return rsn;
	}
	public void setRsn(String rsn) {
		this.rsn = rsn;
	}
	@XmlElement( name="Mobile")  
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	@XmlElement( name="ProcessingDate")  
	public ProcessingDate getProcessingDate() {
		return processingDate;
	}
	public void setProcessingDate(ProcessingDate processingDate) {
		this.processingDate = processingDate;
	}
	@XmlElement( name="ApplicationNo")  
	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	@XmlElement( name="BankName") 
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	@XmlElement( name="Branch")  
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	@XmlElement( name="RecordStatus") 
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	@XmlElement( name="ProductCode") 
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

}
