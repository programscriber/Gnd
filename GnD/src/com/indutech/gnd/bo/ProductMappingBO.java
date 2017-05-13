package com.indutech.gnd.bo;

public class ProductMappingBO {
	private Long id;
	private Long bankId;
	private String oldProduct;
	private String oldBin;
	private String newProduct;
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
