package com.indutech.gnd.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_MAPPING_T")
public class ProductMapping {
	
	@Id
	@Column(name="id", nullable=false, unique=true)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="BANK_ID")
	private Long bankId;
	
	@Column(name="OLD_PRODUCT")
	private String oldProduct;
	
	@Column(name="OLD_BIN")
	private String oldBin;
	
	@Column(name="NEW_PRODUCT")
	private String newProduct;
	
	@Column(name="NEW_BIN")
	private String newBin;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getBankId() {
		return bankId;
	}
	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}
	public String getOldProduct() {
		return oldProduct;
	}
	public void setOldProduct(String oldProduct) {
		this.oldProduct = oldProduct;
	}
	public String getOldBin() {
		return oldBin;
	}
	public void setOldBin(String oldBin) {
		this.oldBin = oldBin;
	}
	public String getNewProduct() {
		return newProduct;
	}
	public void setNewProduct(String newProduct) {
		this.newProduct = newProduct;
	}
	public String getNewBin() {
		return newBin;
	}
	public void setNewBin(String newBin) {
		this.newBin = newBin;
	}
	
	

}
