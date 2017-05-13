package com.indutech.gnd.dto;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "master_product_t")
public class Product {

	@Id
	@Column(name = "product_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long productId;

	@Column(name = "product_name", nullable = true, length = 50)
	private String productName;

	@Column(name = "short_code", nullable = true, length = 50)
	private String shortCode;

	@Column(name = "bin", nullable = true, length = 50)
	private String bin;
	
//	@ManyToOne(targetEntity=MasterType.class,cascade = CascadeType.ALL)
//	@JoinColumn(name = "type_id", referencedColumnName = "TYPE_ID")
	@Column(name = "type_id")
	private Long typeId;

//	@ManyToOne(targetEntity=MasterBank.class,cascade = CascadeType.ALL)
//	@JoinColumn(name = "bank_id", referencedColumnName = "id")
	@Column(name="bank_id")
	private Long bankId;

	@Column(name = "photo_card", nullable = true)
	private Long photoCard;

//	@ManyToOne(targetEntity=MasterDcms.class,cascade = CascadeType.ALL)
//	@JoinColumn(name = "dcms_id", referencedColumnName = "DCMS_ID")
	@Column(name="dcms_id")
	private Long dcmsId;

//	@ManyToOne(targetEntity=Network.class,cascade = CascadeType.ALL)
//	@JoinColumn(name = "network_id", referencedColumnName = "network_id")
	@Column(name="network_id")
	private Long networkId;

	@Column(name = "LAST_CARD_ISSUED_ON")
	private Date issueDate;

	@Column(name = "FORTH_LINE_REQUIRED")
	private Long forthLineRequired;

	@Column(name = "STATUS")
	private Long status;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getBin() {
		return bin;
	}

	public void setBin(String bin) {
		this.bin = bin;
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Long getPhotoCard() {
		return photoCard;
	}

	public void setPhotoCard(Long photoCard) {
		this.photoCard = photoCard;
	}

	public Long getDcmsId() {
		return dcmsId;
	}

	public void setDcmsId(Long dcmsId) {
		this.dcmsId = dcmsId;
	}

	public Long getNetworkId() {
		return networkId;
	}

	public void setNetworkId(Long networkId) {
		this.networkId = networkId;
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

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	
}
