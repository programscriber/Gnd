package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="master_bank_t")
public class Bank {
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="bank_id", unique=true)
	private Long bankId;
	@Column(name="bank_name", nullable=true)
	private String bankName;
	@Column(name="short_code")
	private String shortCode;
	@Column(name="status")
	private Long status;
	@Column(name="BANK_CODE")
	private String bankCode;
	@Column(name="prefix")
	private String prefix;
	@Column(name="LP_AWB_Branch_Group")
	private Long LPAWBBranchGroup;
	@Column(name="AUF_FORMAT")
	 private Integer aufFormat;
	@Column(name="RTO_Branch_Group")
	private Integer RTOBranchGroup;
	@Column(name="CENTRALIZE_BRANCH")
	private String centralizedBranch;
	
	public String getCentralizedBranch() {
		return centralizedBranch;
	}
	public void setCentralizedBranch(String centralizedBranch) {
		this.centralizedBranch = centralizedBranch;
	}
	public Integer getRTOBranchGroup() {
		return RTOBranchGroup;
	}
	public void setRTOBranchGroup(Integer rTOBranchGroup) {
		RTOBranchGroup = rTOBranchGroup;
	}
	public Integer getAufFormat() {
	  return aufFormat;
	 }
	 public void setAufFormat(Integer aufFormat) {
	  this.aufFormat = aufFormat;
	 }

	public Long getLPAWBBranchGroup() {
		return LPAWBBranchGroup;
	}
	public void setLPAWBBranchGroup(Long lPAWBBranchGroup) {
		LPAWBBranchGroup = lPAWBBranchGroup;
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
