package com.indutech.gnd.bo;

public class BankBO {
	
	private Long id;
	private Long bankId;
	private String bankName;
	private String shortCode;
	private Long status;
	private String bankCode;
	private String prefix;

	private String lPAWBBranchGroup;
	private Long LPBranchGroup;
	private String statusString;
	private String aufFormatString;
	private Integer RTOBranchGroup;
	private String centralizedBranch;
	
	public String getCentralizedBranch() {
		return centralizedBranch;
	}
	public void setCentralizedBranch(String centralizedBranch) {
		this.centralizedBranch = centralizedBranch;
	}

	
	public Long getLPBranchGroup() {
		return LPBranchGroup;
	}
	public void setLPBranchGroup(Long lPBranchGroup) {
		LPBranchGroup = lPBranchGroup;
	}
	public Integer getRTOBranchGroup() {
		return RTOBranchGroup;
	}
	public void setRTOBranchGroup(Integer rTOBranchGroup) {
		RTOBranchGroup = rTOBranchGroup;
	}
	public String getlPAWBBranchGroup() {
		return lPAWBBranchGroup;
	}
	public void setlPAWBBranchGroup(String lPAWBBranchGroup) {
		this.lPAWBBranchGroup = lPAWBBranchGroup;
	}
	public String getAufFormatString() {
		return aufFormatString;
	}
	public void setAufFormatString(String aufFormatString) {
		this.aufFormatString = aufFormatString;
	}
	private Integer aufFormat;
	
	public Integer getAufFormatInt() {
		return aufFormat;
	}
	public void setAufFormat(Integer aufFormat) {
		this.aufFormat = aufFormat;
	}

	
	 public String getAufFormat() {
		  return aufFormatString;
		 }
	public void setAufFormat(String aufFormat) {
		  this.aufFormatString = aufFormat;
		 }
	public String getStatusString() {
		return statusString;
	}
	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}
	public String getLPAWBBranchGroup() {
		return lPAWBBranchGroup;
	}
	public void setLPAWBBranchGroup(String lPAWBBranchGroup) {
		this.lPAWBBranchGroup = lPAWBBranchGroup;
	}
	public String getPrefix() {
		return prefix;
	}
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
	
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public final Long getId() {
		return id;
	}
	public final void setId(Long id) {
		this.id = id;
	}
	public final Long getBankId() {
		return bankId;
	}
	public final void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public final String getBankName() {
		return bankName;
	}
	public final void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public final String getShortCode() {
		return shortCode;
	}
	public final void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public final Long getStatus() {
		return status;
	}
	public final void setStatus(Long status) {
		this.status = status;
	}	

}
