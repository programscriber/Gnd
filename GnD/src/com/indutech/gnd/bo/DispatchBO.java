package com.indutech.gnd.bo;

import java.util.Date;



public class DispatchBO {
	
	private Long id;
	
	private String bankSuffix;
	
	private String homeBranchCode;
	
	private String product;
	
	private Long pinCount;
	
	private Long rsn;
	
	private String pinAWB;
	
	private String corFileName;
	
	private Date dispatchedDate;
	
	private String challanNumber;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBankSuffix() {
		return bankSuffix;
	}

	public void setBankSuffix(String bankSuffix) {
		this.bankSuffix = bankSuffix;
	}

	public String getHomeBranchCode() {
		return homeBranchCode;
	}

	public void setHomeBranchCode(String homeBranchCode) {
		this.homeBranchCode = homeBranchCode;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public Long getPinCount() {
		return pinCount;
	}

	public void setPinCount(Long pinCount) {
		this.pinCount = pinCount;
	}

	public Long getRsn() {
		return rsn;
	}

	public void setRsn(Long rsn) {
		this.rsn = rsn;
	}

	public String getPinAWB() {
		return pinAWB;
	}

	public void setPinAWB(String pinAWB) {
		this.pinAWB = pinAWB;
	}

	public String getCorFileName() {
		return corFileName;
	}

	public void setCorFileName(String corFileName) {
		this.corFileName = corFileName;
	}

	public Date getDispatchedDate() {
		return dispatchedDate;
	}

	public void setDispatchedDate(Date dispatchedDate) {
		this.dispatchedDate = dispatchedDate;
	}

	public String getChallanNumber() {
		return challanNumber;
	}

	public void setChallanNumber(String challanNumber) {
		this.challanNumber = challanNumber;
	}
	
	
	


}
