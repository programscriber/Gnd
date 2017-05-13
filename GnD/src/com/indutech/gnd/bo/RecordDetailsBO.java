package com.indutech.gnd.bo;

import java.util.Date;

public class RecordDetailsBO {
	
	private Long creditCardDetailsId;
	private String embossName;
	private String product;
	private String recordStatus; 
	private long  eventStatus;
	private String cardNumber;
	private String bank;
	private String address;
	private String address2;
	private String address3;
	private String address4;
	private String city;
	private String customerId;
	private String cardDispatchAWB;
	private String pinDispatchAWB;
	private String branch;
	private String issueBranch;
	private String processedBranch;
	private String pincode;
	private String description;
	private String mobileNo;
	private String bankName;
	
	
	
	
	
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public String getIssueBranch() {
		return issueBranch;
	}
	public void setIssueBranch(String issueBranch) {
		this.issueBranch = issueBranch;
	}
	public String getProcessedBranch() {
		return processedBranch;
	}
	public void setProcessedBranch(String processedBranch) {
		this.processedBranch = processedBranch;
	}
	private Date eventDate;
	private String cardAWB;
	private String pinAWB;
	private Long rsn;
	
	

	public Long getRsn() {
		return rsn;
	}
	public void setRsn(Long rsn) {
		this.rsn = rsn;
	}
	public String getCardAWB() {
		return cardAWB;
	}
	public void setCardAWB(String cardAWB) {
		this.cardAWB = cardAWB;
	}
	public String getPinAWB() {
		return pinAWB;
	}
	public void setPinAWB(String pinAWB) {
		this.pinAWB = pinAWB;
	}
	public Long getCreditCardDetailsId() {
		return creditCardDetailsId;
	}
	public void setCreditCardDetailsId(Long creditCardDetailsId) {
		this.creditCardDetailsId = creditCardDetailsId;
	}
	public final long getEventStatus() {
		return eventStatus;
	}
	public final void setEventStatus(long eventStatus) {
		this.eventStatus = eventStatus;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getEmbossName() {
		return embossName;
	}
	public void setEmbossName(String embossName) {
		this.embossName = embossName;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getBank() {
		return bank;
	}
	public void setBank(String bank) {
		this.bank = bank;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCardDispatchAWB() {
		return cardDispatchAWB;
	}
	public void setCardDispatchAWB(String cardDispatchAWB) {
		this.cardDispatchAWB = cardDispatchAWB;
	}
	public String getPinDispatchAWB() {
		return pinDispatchAWB;
	}
	public void setPinDispatchAWB(String pinDispatchAWB) {
		this.pinDispatchAWB = pinDispatchAWB;
	}
	public String getBranch() {
		return branch;
	}
	public void setBranch(String branch) {
		this.branch = branch;
	}
	public String getPincode() {
		return pincode;
	}
	public void setPincode(String pincode) {
		this.pincode = pincode;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
}
