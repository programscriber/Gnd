package com.indutech.gnd.bo;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopFloorDetails {
	private Long rsn;
	private String cardAWB;
	private String pinAWB;
	private String shortCode;
	private String fileName;
	private String information;
	private Integer counter;
	private String result;
	private boolean redispatachAgain;
	private boolean boolRes;
	private Long status;
	private File file;
	private String primaryAccNumber;
	private String eventdate;


	private List<ShopFloorDetails>  shopFloorDetailsreturn=new ArrayList<ShopFloorDetails>();
	private List<ShopFloorDetails> shopFloorDetailsOtherStatus=new ArrayList<ShopFloorDetails>();
	private List<ShopFloorDetails> shopFloorDetailsreturnResult=new ArrayList<ShopFloorDetails>();
	private List<ShopFloorDetails> shopFloorDetailsRedispatchAgain=new ArrayList<ShopFloorDetails>();
	
	private List<ShopFloorDetails> shopFloorDetailsDelivery=new ArrayList<ShopFloorDetails>();
	private List<ShopFloorDetails> shopFloorDetailsDeliveryResult=new ArrayList<ShopFloorDetails>();
	
	public String getEventdate() {
		return eventdate;
	}

	public void setEventdate(String eventdate) {
		this.eventdate = eventdate;
	}
	
	
	
	public String getPinAWB() {
		return pinAWB;
	}

	public void setPinAWB(String pinAWB) {
		this.pinAWB = pinAWB;
	}
	
	
	public List<ShopFloorDetails> getShopFloorDetailsDeliveryResult() {
		return shopFloorDetailsDeliveryResult;
	}

	public void setShopFloorDetailsDeliveryResult(
			List<ShopFloorDetails> shopFloorDetailsDeliveryResult) {
		this.shopFloorDetailsDeliveryResult = shopFloorDetailsDeliveryResult;
	}

	public List<ShopFloorDetails> getShopFloorDetailsDelivery() {
		return shopFloorDetailsDelivery;
	}

	public void setShopFloorDetailsDelivery(
			List<ShopFloorDetails> shopFloorDetailsDelivery) {
		this.shopFloorDetailsDelivery = shopFloorDetailsDelivery;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	public List<ShopFloorDetails> getShopFloorDetailsRedispatchAgain() {
		return shopFloorDetailsRedispatchAgain;
	}

	public void setShopFloorDetailsRedispatchAgain(
			List<ShopFloorDetails> shopFloorDetailsRedispatchAgain) {
		this.shopFloorDetailsRedispatchAgain = shopFloorDetailsRedispatchAgain;
	}

	public List<ShopFloorDetails> getShopFloorDetailsreturnResult() {
		return shopFloorDetailsreturnResult;
	}

	public void setShopFloorDetailsreturnResult(
			List<ShopFloorDetails> shopFloorDetailsreturnResult) {
		this.shopFloorDetailsreturnResult = shopFloorDetailsreturnResult;
	}

	public List<ShopFloorDetails> getShopFloorDetailsreturn() {
		return shopFloorDetailsreturn;
	}

	public void setShopFloorDetailsreturn(
			List<ShopFloorDetails> shopFloorDetailsreturn) {
		this.shopFloorDetailsreturn = shopFloorDetailsreturn;
	}

	public List<ShopFloorDetails> getShopFloorDetailsOtherStatus() {
		return shopFloorDetailsOtherStatus;
	}

	public void setShopFloorDetailsOtherStatus(
			List<ShopFloorDetails> shopFloorDetailsOtherStatus) {
		this.shopFloorDetailsOtherStatus = shopFloorDetailsOtherStatus;
	}

	public String getPrimaryAccNumber() {
		return primaryAccNumber;
	}

	public void setPrimaryAccNumber(String primaryAccNumber) {
		this.primaryAccNumber = primaryAccNumber;
	}

	

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public boolean isBoolRes() {
		return boolRes;
	}

	public void setBoolRes(boolean boolRes) {
		this.boolRes = boolRes;
	}
	public boolean isRedispatachAgain() {
		return redispatachAgain;
	}

	public void setRedispatachAgain(boolean redispatachAgain) {
		this.redispatachAgain = redispatachAgain;
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
