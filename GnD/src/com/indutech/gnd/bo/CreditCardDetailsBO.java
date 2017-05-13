package com.indutech.gnd.bo;

import java.util.Date;

import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator="^")
public class CreditCardDetailsBO {

	
	@DataField(pos = 1)
    private String serialNo;
 
    @DataField(pos = 2)
    private String institutionId;
 
    @DataField(pos = 3)
    private String customerId;
    
    @DataField(pos = 4)
    private String homeBranchCode;
    
    @DataField(pos = 5)
    private String issueBranchCode;
    
    @DataField(pos = 6)
    private String primaryAcctNo;
    
    @DataField(pos = 7)
    private String primaryAcctType;
    
    @DataField(pos = 8)
    private String primaryAcctSurviour;
    
    @DataField(pos = 9)
    private String secondaryAcct1;
    
    @DataField(pos = 10)
    private String secondaryAcct1Type;
    
    @DataField(pos = 11)
    private String secondaryAcctSurviour;
    
    @DataField(pos = 12)
    private String secondaryAcct2;
    
    @DataField(pos = 13)
    private String secondaryAcct2Type;
    
    @DataField(pos = 14)
    private String secondarySurviour;
    
    @DataField(pos = 15)
    private String embossName;
    
    @DataField(pos = 16)
    private String customerFirstName;
    
    @DataField(pos = 17)
    private String customerMiddleName; 
    
    @DataField(pos = 18)
    private String customerSurName;
    
    @DataField(pos = 19)
    private String addr1;
    
    @DataField(pos = 20)
    private String addr2;
    
    @DataField(pos = 21)
    private String addr3;
    
    @DataField(pos = 22)
    private String addr4;
    
    @DataField(pos = 23)
    private String city;
    
    @DataField(pos = 24)
    private String pin;
    
    @DataField(pos = 25)
    private String officePhone;
    
    @DataField(pos = 26)
    private String homePhone;
    
    @DataField(pos = 27)
    private String product;
    
    @DataField(pos = 28)
    private String cardStatus; 
    
    @DataField(pos = 29)
    private String registrationDate;
    
    @DataField(pos = 30)
    private String fax;
    
    @DataField(pos = 31)
    private String email;
    
    @DataField(pos = 32)
    private String fathersFirstName;
    
    @DataField(pos = 33)
    private String motherMaidenName;
    
    @DataField(pos = 34)
    private String dateOfBirth;
    
    @DataField(pos = 35)
    private String yearOfPassingSSC;
    
    @DataField(pos = 36)
    private String yearOfMarriage;
    
    @DataField(pos = 37)
    private String fourthLinePrintingData;
    
    @DataField(pos = 38)
    private String isdCode;
    
    @DataField(pos = 39)
    private String mobileNo;
    
    @DataField(pos = 40)
    private String primaryAccountProductCode;
    
    @DataField(pos = 41)
    private String secondaryAccount1ProductCode;
    
    @DataField(pos = 42)
    private String secondaryAccount2ProductCode;
    
    @DataField(pos = 43)
    private String homeBranchCircleCode; 
    
    @DataField(pos = 44)
    private String issueBranchCircleCode;
    
    
    private String cardAWB;

    private String pinAWB;
    
    private Long status;
    
    private Long rsn;
    
    private Long creditCardDetailsId;
    
    private String recordStatus;
    
    private Long fildId;
    
    private Long pinstatus;
    
    private Date creationDate;    
    
    private String pinStatusString;
   
    private String currentStatusString;
    
    private String awbName;
    
    private String bankCode;
    
    private String filename;
    
    private String processedBranchCode;
    
    private String lcpcBranch;
    //cr no 40
    private int isNonCardIssueBranch;
    

	public int getIsNonCardIssueBranch() {
		return isNonCardIssueBranch;
	}

	public void setIsNonCardIssueBranch(int isNonCardIssueBranch) {
		this.isNonCardIssueBranch = isNonCardIssueBranch;
	}

	//cr no 40
    
    
	public String getLcpcBranch() {
		return lcpcBranch;
	}

	public void setLcpcBranch(String lcpcBranch) {
		this.lcpcBranch = lcpcBranch;
	}

	public String getProcessedBranchCode() {
		return processedBranchCode;
	}

	public void setProcessedBranchCode(String processedBranchCode) {
		this.processedBranchCode = processedBranchCode;
	}
    
    
    
	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getAwbName() {
		return awbName;
	}

	public void setAwbName(String awbName) {
		this.awbName = awbName;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getCurrentStatusString() {
		return currentStatusString;
	}

	public void setCurrentStatusString(String currentStatusString) {
		this.currentStatusString = currentStatusString;
	}

	public String getPinStatusString() {
		return pinStatusString;
	}

	public void setPinStatusString(String pinStatusString) {
		this.pinStatusString = pinStatusString;
	}

	public Long getPinstatus() {
		return pinstatus;
	}

	public void setPinstatus(Long pinstatus) {
		this.pinstatus = pinstatus;
	}

	public Long getFildId() {
		return fildId;
	}

	public void setFildId(Long fildId) {
		this.fildId = fildId;
	}

	public String getRecordStatus() {
		return recordStatus;
	}

	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}

	public Long getCreditCardDetailsId() {
		return creditCardDetailsId;
	}

	public void setCreditCardDetailsId(Long creditCardDetailsId) {
		this.creditCardDetailsId = creditCardDetailsId;
	}

	public Long getRsn() {
		return rsn;
	}

	public void setRsn(Long rsn) {
		this.rsn = rsn;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getInstitutionId() {
		return institutionId;
	}

	public void setInstitutionId(String institutionId) {
		this.institutionId = institutionId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getHomeBranchCode() {
		return homeBranchCode;
	}

	public void setHomeBranchCode(String homeBranchCode) {
		this.homeBranchCode = homeBranchCode;
	}

	public String getIssueBranchCode() {
		return issueBranchCode;
	}

	public void setIssueBranchCode(String issueBranchCode) {
		this.issueBranchCode = issueBranchCode;
	}

	public String getPrimaryAcctNo() {
		return primaryAcctNo;
	}

	public void setPrimaryAcctNo(String primaryAcctNo) {
		this.primaryAcctNo = primaryAcctNo;
	}

	public String getPrimaryAcctType() {
		return primaryAcctType;
	}

	public void setPrimaryAcctType(String primaryAcctType) {
		this.primaryAcctType = primaryAcctType;
	}

	public String getPrimaryAcctSurviour() {
		return primaryAcctSurviour;
	}

	public void setPrimaryAcctSurviour(String primaryAcctSurviour) {
		this.primaryAcctSurviour = primaryAcctSurviour;
	}

	public String getSecondaryAcct1() {
		return secondaryAcct1;
	}

	public void setSecondaryAcct1(String secondaryAcct1) {
		this.secondaryAcct1 = secondaryAcct1;
	}

	public String getSecondaryAcct1Type() {
		return secondaryAcct1Type;
	}

	public void setSecondaryAcct1Type(String secondaryAcct1Type) {
		this.secondaryAcct1Type = secondaryAcct1Type;
	}

	public String getSecondaryAcctSurviour() {
		return secondaryAcctSurviour;
	}

	public void setSecondaryAcctSurviour(String secondaryAcctSurviour) {
		this.secondaryAcctSurviour = secondaryAcctSurviour;
	}

	public String getSecondaryAcct2() {
		return secondaryAcct2;
	}

	public void setSecondaryAcct2(String secondaryAcct2) {
		this.secondaryAcct2 = secondaryAcct2;
	}

	public String getSecondaryAcct2Type() {
		return secondaryAcct2Type;
	}

	public void setSecondaryAcct2Type(String secondaryAcct2Type) {
		this.secondaryAcct2Type = secondaryAcct2Type;
	}

	public String getSecondarySurviour() {
		return secondarySurviour;
	}

	public void setSecondarySurviour(String secondarySurviour) {
		this.secondarySurviour = secondarySurviour;
	}

	public String getEmbossName() {
		return embossName;
	}

	public void setEmbossName(String embossName) {
		this.embossName = embossName;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerMiddleName() {
		return customerMiddleName;
	}

	public void setCustomerMiddleName(String customerMiddleName) {
		this.customerMiddleName = customerMiddleName;
	}

	public String getCustomerSurName() {
		return customerSurName;
	}

	public void setCustomerSurName(String customerSurName) {
		this.customerSurName = customerSurName;
	}

	public String getAddr1() {
		return addr1;
	}

	public void setAddr1(String addr1) {
		this.addr1 = addr1;
	}

	public String getAddr2() {
		return addr2;
	}

	public void setAddr2(String addr2) {
		this.addr2 = addr2;
	}

	public String getAddr3() {
		return addr3;
	}

	public void setAddr3(String addr3) {
		this.addr3 = addr3;
	}

	public String getAddr4() {
		return addr4;
	}

	public void setAddr4(String addr4) {
		this.addr4 = addr4;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getOfficePhone() {
		return officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getHomePhone() {
		return homePhone;
	}

	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFathersFirstName() {
		return fathersFirstName;
	}

	public void setFathersFirstName(String fathersFirstName) {
		this.fathersFirstName = fathersFirstName;
	}

	public String getMotherMaidenName() {
		return motherMaidenName;
	}

	public void setMotherMaidenName(String motherMaidenName) {
		this.motherMaidenName = motherMaidenName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getYearOfPassingSSC() {
		return yearOfPassingSSC;
	}

	public void setYearOfPassingSSC(String yearOfPassingSSC) {
		this.yearOfPassingSSC = yearOfPassingSSC;
	}

	public String getYearOfMarriage() {
		return yearOfMarriage;
	}

	public void setYearOfMarriage(String yearOfMarriage) {
		this.yearOfMarriage = yearOfMarriage;
	}

	public String getFourthLinePrintingData() {
		return fourthLinePrintingData;
	}

	public void setFourthLinePrintingData(String fourthLinePrintingData) {
		this.fourthLinePrintingData = fourthLinePrintingData;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getPrimaryAccountProductCode() {
		return primaryAccountProductCode;
	}

	public void setPrimaryAccountProductCode(String primaryAccountProductCode) {
		this.primaryAccountProductCode = primaryAccountProductCode;
	}

	public String getSecondaryAccount1ProductCode() {
		return secondaryAccount1ProductCode;
	}

	public void setSecondaryAccount1ProductCode(String secondaryAccount1ProductCode) {
		this.secondaryAccount1ProductCode = secondaryAccount1ProductCode;
	}

	public String getSecondaryAccount2ProductCode() {
		return secondaryAccount2ProductCode;
	}

	public void setSecondaryAccount2ProductCode(String secondaryAccount2ProductCode) {
		this.secondaryAccount2ProductCode = secondaryAccount2ProductCode;
	}

	public String getHomeBranchCircleCode() {
		return homeBranchCircleCode;
	}

	public void setHomeBranchCircleCode(String homeBranchCircleCode) {
		this.homeBranchCircleCode = homeBranchCircleCode;
	}

	public String getIssueBranchCircleCode() {
		return issueBranchCircleCode;
	}

	public void setIssueBranchCircleCode(String issueBranchCircleCode) {
		this.issueBranchCircleCode = issueBranchCircleCode;
	}

	public String getCardAWB() {
		return cardAWB;
	}

	public void setCardAWB(String cardAWB) {
		this.cardAWB = cardAWB;
	}

	public String getPinAWB() {
		return pinAWB;
	}

	public void setPinAWB(String pinAWB) {
		this.pinAWB = pinAWB;
	}

}
