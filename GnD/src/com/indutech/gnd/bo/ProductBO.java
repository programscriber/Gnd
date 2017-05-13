package com.indutech.gnd.bo;

import java.util.Date;



public class ProductBO {
	
	
	private Long productId;	
	private String productName;	
	private String shortCode;	
	private String bin;	
	private Long typeId;	
	private Long bankId;	
	private Long photoCard;	
	private Long dcmsId;	
	private Long networkId;
	private Date issueDate;
	private Long forthLineRequired;
	private Long status;
	private String statusString;
	private String fourthLineReqString;
	private String photoCardString;
	
	
	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public String getFourthLineReqString() {
		return fourthLineReqString;
	}

	public void setFourthLineReqString(String fourthLineReqString) {
		this.fourthLineReqString = fourthLineReqString;
	}

	public String getPhotoCardString() {
		return photoCardString;
	}

	public void setPhotoCardString(String photoCardString) {
		this.photoCardString = photoCardString;
	}

	public Date getIssueDate() {
		return issueDate;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate;
	}

	public Long getForthLineRequired() {
		return forthLineRequired;
	}

	public void setForthLineRequired(Long forthLineRequired) {
		this.forthLineRequired = forthLineRequired;
	}

	public final Long getStatus() {
		return status;
	}

	public final void setStatus(Long status) {
		this.status = status;
	}

	public final Long getProductId() {
		return productId;
	}

	public final void setProductId(Long productId) {
		this.productId = productId;
	}

	public final String getProductName() {
		return productName;
	}

	public final void setProductName(String productName) {
		this.productName = productName;
	}

	public final String getShortCode() {
		return shortCode;
	}

	public final void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public final String getBin() {
		return bin;
	}

	public final void setBin(String bin) {
		this.bin = bin;
	}

	public final Long getTypeId() {
		return typeId;
	}

	public final void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public final Long getBankId() {
		return bankId;
	}

	public final void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public final Long getPhotoCard() {
		return photoCard;
	}

	public final void setPhotoCard(Long photoCard) {
		this.photoCard = photoCard;
	}

	public final Long getDcmsId() {
		return dcmsId;
	}

	public final void setDcmsId(Long dcmsId) {
		this.dcmsId = dcmsId;
	}

	public final Long getNetworkId() {
		return networkId;
	}

	public final void setNetworkId(Long networkId) {
		this.networkId = networkId;
	}
	
	
}
