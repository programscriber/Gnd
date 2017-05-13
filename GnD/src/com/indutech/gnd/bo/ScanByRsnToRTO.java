package com.indutech.gnd.bo;

public class ScanByRsnToRTO {
	private String rsn;
	private String cardAWB;
	private String shortCode;
	private String fileName;
	private String information;
	private Integer counter;
	private String result;

	private boolean boolRes;
	
	public boolean isBoolRes() {
		return boolRes;
	}

	public void setBoolRes(boolean boolRes) {
		this.boolRes = boolRes;
	}


	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Integer getCounter() {
		return counter;
	}

	public void setCounter(Integer counter) {
		this.counter = counter;
	}

	public String getRsn() {
		return rsn;
	}

	public void setRsn(String rsn) {
		this.rsn = rsn;
	}

	public String getCardAWB() {
		return cardAWB;
	}

	public void setCardAWB(String cardAWB) {
		this.cardAWB = cardAWB;
	}

	public String getShortCode() {
		return shortCode;
	}

	public void setShortCode(String shortCode) {
		this.shortCode = shortCode;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

}
