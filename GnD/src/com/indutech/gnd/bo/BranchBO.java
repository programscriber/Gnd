package com.indutech.gnd.bo;

public class BranchBO {
	
	private Long branchId;
	private String shortCode;
	private String branchName;
	private String liveStatus;
	private String ifscCode;
	private String micr;
	private String phoneNumber;
	private String emailAddress;
	private String tan;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String branchType;
	private Long  districtCode;
	private Long stateCode;
	private Long bankId;
	private Long status;
	private String circleCode;
	private String circle;
	private Long network;
	private Long modcode;
	private String module;
	private Long region;
	private Long popCode;
	private String popGroup;
	private String bpr;
	private String gcc;
	private String stringStatus;
	
	private String lcpcName;
	private String lcpcBranch;
	private String pinCode;
	private String stateString;
	private String districtString;
	private String bankNameString;
	private String branchStatusString;
	private Integer isNonCardIssueBranch;

	 
	 public Integer getIsNonCardIssueBranch() {
	  return isNonCardIssueBranch;
	 }
	 public void setIsNonCardIssueBranch(Integer isNonCardIssueBranch) {
	  this.isNonCardIssueBranch = isNonCardIssueBranch;
	 }

	public String getBranchStatusString() {
		return branchStatusString;
	}
	public void setBranchStatusString(String branchStatusString) {
		this.branchStatusString = branchStatusString;
	}
	public String getBankNameString() {
		return bankNameString;
	}
	public void setBankNameString(String bankNameString) {
		this.bankNameString = bankNameString;
	}
	public String getStateString() {
		return stateString;
	}
	public void setStateString(String stateString) {
		this.stateString = stateString;
	}
	public String getDistrictString() {
		return districtString;
	}
	public void setDistrictString(String districtString) {
		this.districtString = districtString;
	}
	public String getLcpcName() {
		return lcpcName;
	}
	public void setLcpcName(String lcpcName) {
		this.lcpcName = lcpcName;
	}
	public String getLcpcBranch() {
		return lcpcBranch;
	}
	public void setLcpcBranch(String lcpcBranch) {
		this.lcpcBranch = lcpcBranch;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getCircleCode() {
		return circleCode;
	}
	public void setCircleCode(String circleCode) {
		this.circleCode = circleCode;
	}
	public String getStringStatus() {
		return stringStatus;
	}
	public void setStringStatus(String stringStatus) {
		this.stringStatus = stringStatus;
	}

	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public Long getNetwork() {
		return network;
	}
	public void setNetwork(Long network) {
		this.network = network;
	}
	public Long getModcode() {
		return modcode;
	}
	public void setModcode(Long modcode) {
		this.modcode = modcode;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public Long getRegion() {
		return region;
	}
	public void setRegion(Long region) {
		this.region = region;
	}
	public Long getPopCode() {
		return popCode;
	}
	public void setPopCode(Long popCode) {
		this.popCode = popCode;
	}
	public String getPopGroup() {
		return popGroup;
	}
	public void setPopGroup(String popGroup) {
		this.popGroup = popGroup;
	}
	public String getBpr() {
		return bpr;
	}
	public void setBpr(String bpr) {
		this.bpr = bpr;
	}
	public String getGcc() {
		return gcc;
	}
	public void setGcc(String gcc) {
		this.gcc = gcc;
	}
	public final Long getStatus() {
		return status;
	}
	public final void setStatus(Long status) {
		this.status = status;
	}
	public final Long getBranchId() {
		return branchId;
	}
	public final void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public final String getShortCode() {
		return shortCode;
	}
	public final void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}
	public final String getBranchName() {
		return branchName;
	}
	public final void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public final String getLiveStatus() {
		return liveStatus;
	}
	public final void setLiveStatus(String liveStatus) {
		this.liveStatus = liveStatus;
	}
	public final String getIfscCode() {
		return ifscCode;
	}
	public final void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}
	public final String getMicr() {
		return micr;
	}
	public final void setMicr(String micr) {
		this.micr = micr;
	}
	public final String getPhoneNumber() {
		return phoneNumber;
	}
	public final void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public final String getEmailAddress() {
		return emailAddress;
	}
	public final void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public final String getTan() {
		return tan;
	}
	public final void setTan(String tan) {
		this.tan = tan;
	}
	public final String getAddress1() {
		return address1;
	}
	public final void setAddress1(String address1) {
		this.address1 = address1;
	}
	public final String getAddress2() {
		return address2;
	}
	public final void setAddress2(String address2) {
		this.address2 = address2;
	}
	public final String getAddress3() {
		return address3;
	}
	public final void setAddress3(String address3) {
		this.address3 = address3;
	}
	public final String getAddress4() {
		return address4;
	}
	public final void setAddress4(String address4) {
		this.address4 = address4;
	}
	public final String getBranchType() {
		return branchType;
	}
	public final void setBranchType(String branchType) {
		this.branchType = branchType;
	}
	public final Long getDistrictCode() {
		return districtCode;
	}
	public final void setDistrictCode(Long districtCode) {
		this.districtCode = districtCode;
	}
	public final Long getStateCode() {
		return stateCode;
	}
	public final void setStateCode(Long stateCode) {
		this.stateCode = stateCode;
	}
	public final Long getBankId() {
		return bankId;
	}
	public final void setBankId(Long bankId) {
		this.bankId = bankId;
	}	

}
