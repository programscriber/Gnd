package com.indutech.gnd.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="CARD_DISPATCH_T")
public class CardDispatch {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name="BANK_SUFFIX")
	private String bankSuffix;
	
	@Column(name="HOME_BRANCH_CODE")
	private String homeBranchCode;
	
	@Column(name="PRODUCT")
	private String product;
	
	@Column(name="PIN_COUNT")
	private Long pinCount;
	
	@Column(name="RSN")
	private Long rsn;
	
	@Column(name="CARD_AWB")
	private String cardAWB;
	
	@Column(name="CORE_FILE_NAME")
	private String corFileName;
	
	@Column(name="DISPATCHED_DATE")
	private Date dispatchedDate;
	
	@Column(name="CHALLAN_NUMBER")
	private String challanNumber;
	
	@Column(name="INFO")
	private String info;
	
	@Column(name="IS_INDIVIDUAL")
	private Long isIndividual;	

	public Long getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(Long isIndividual) {
		this.isIndividual = isIndividual;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

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

	public String getCardAWB() {
		return cardAWB;
	}

	public void setCardAWB(String cardAWB) {
		this.cardAWB = cardAWB;
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
