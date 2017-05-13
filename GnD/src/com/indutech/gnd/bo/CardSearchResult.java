package com.indutech.gnd.bo;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement  
public class CardSearchResult {

	private String accountNo;
	private String cardHolderName;
	private String  mobileNo;
	private Date processingDate;
	private String applicationNo;

	private String  bankName;
	
	private String branch;
	
	private String  recordStatus;
	
	private String   dispatchStatus;
	
	private String   approvedStatus;
	
	@XmlElement( name="Account")  
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	@XmlElement( name="CardHolderName")  
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	@XmlElement( name="Mobile")  
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	@XmlElement( name="ProcessingDate")  
	public Date getProcessingDate() {
		return processingDate;
	}
	public void setProcessingDate(Date processingDate) {
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
	@XmlElement( name="DispatchStatus")  
	public String getDispatchStatus() {
		return dispatchStatus;
	}
	public void setDispatchStatus(String dispatchStatus) {
		this.dispatchStatus = dispatchStatus;
	}
	@XmlElement( name="ApprovedStatus")  
	public String getApprovedStatus() {
		return approvedStatus;
	}
	public void setApprovedStatus(String approvedStatus) {
		this.approvedStatus = approvedStatus;
	}
}
