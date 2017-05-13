package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_branch_t")
public class Branch {
	
	@Id
	@Column(name = "id", unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long branchId;
	@Column(name="short_code", unique=true)
	private String shortCode;
	@Column(name="branch_name")
	private String branchName;
	@Column(name="live")
	private String liveStatus;
	@Column(name="IFSC")
	private String ifscCode;
	@Column(name="MICR")
	private String micr;
	@Column(name="phone")
	private String phoneNumber;
	@Column(name="email")
	private String emailAddress;
	@Column(name="TAN")
	private String tan;
	@Column(name="Addr1")
	private String address1;
	@Column(name="Addr2")
	private String address2;
	@Column(name="Addr3")
	private String address3;
	@Column(name="Addr4")
	private String address4;
	@Column(name="branch_type")
	private String branchType;
	@Column(name="District_Code")
	private Long  districtCode;
	@Column(name="State_Code")
	private Long stateCode;
	@Column(name="bank_id")
	private Long bankId;
	@Column(name="status")
	private Long status;
	
	@Column(name="CIRCLE_CODE")
	private String circleCode;
	@Column(name="CIRCLE")
	private String circle;
	@Column(name="NETWORK")
	private Long network;
	@Column(name="MOD_CODE")
	private Long modcode;
	@Column(name="MODULE")
	private String module;
	@Column(name="REGION")
	private Long region;
	@Column(name="POP_CODE")
	private Long popCode;
	@Column(name="POP_GROUP")
	private String popGroup;
	@Column(name="BPR")
	private String bpr;
	@Column(name="GCC")
	private String gcc;
	
	@Column(name="LCPC_NAME")
	private String lcpcName;
	@Column(name="LCPC_BRANCH")
	private String lcpcBranch;
	
	@Column(name="PIN_CODE")
	private String pinCode;
	
	@Column(name="IS_NON_CARD_ISSUE_BRANCH")
	 private Integer isNonCardIssueBranch;
	
	
	@Column(name="PIN_COVERING_LETTER_COUNT")
	private Long pinCoveringLetterCount;
	
	
	 
	 public Long getPinCoveringLetterCount() {
		return pinCoveringLetterCount;
	}
	public void setPinCoveringLetterCount(Long pinCoveringLetterCount) {
		this.pinCoveringLetterCount = pinCoveringLetterCount;
	}
	public Integer getIsNonCardIssueBranch() {
	  return isNonCardIssueBranch;
	 }
	 public void setIsNonCardIssueBranch(Integer isNonCardIssueBranch) {
	  this.isNonCardIssueBranch = isNonCardIssueBranch;
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
