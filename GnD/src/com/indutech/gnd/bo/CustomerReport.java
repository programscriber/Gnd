package com.indutech.gnd.bo;

import java.util.Date;

public class CustomerReport {

	private String serialNo;
	private String institutionId;
	private String primaryAcctNo;
	private String product;
	private String ruleStatus;
	private Long bankId;
	private String coreFileName;
	private String homeBranchCode;
	private Date qcDate;
	private String email;
	
	public String getCoreFileName() {
		return coreFileName;
	}
	public void setCoreFileName(String coreFileName) {
		this.coreFileName = coreFileName;
	}
	public String getHomeBranchCode() {
		return homeBranchCode;
	}
	public void setHomeBranchCode(String homeBranchCode) {
		this.homeBranchCode = homeBranchCode;
	}
	public Date getQcDate() {
		return qcDate;
	}
	public void setQcDate(Date qcDate) {
		this.qcDate = qcDate;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSerialNo() {
		return serialNo;
	}
	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}
	public String getInstitutionId() {
		return institutionId;
	}
	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}
	public String getPrimaryAcctNo() {
		return primaryAcctNo;
	}
	public void setPrimaryAcctNo(String primaryAcctNo) {
		this.primaryAcctNo = primaryAcctNo;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getRuleStatus() {
		return ruleStatus;
	}
	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
		
	
}