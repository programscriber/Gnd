package com.indutech.gnd.dto;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_RECORDS_T")
public class CreditCardDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CREDIT_CARD_DETAILS_ID", unique = true, nullable = false, length = 20)
	//@GeneratedValue(generator = "assigned")
	private Long creditCardDetailsId;

	@Column(name = "SERIAL_NO", nullable =true, length = 50)
    private String serialNo;
 
    @Column(name = "INSTUTION_ID", nullable =true, length = 50)
    private String institutionId;
 
    @Column(name = "CUSTOMER_ID", nullable =true, length = 50)
    private String customerId;
    
    @Column(name = "HOME_BRANCH_CODE", nullable =true, length = 50)
    private String homeBranchCode;
    
    @Column(name = "ISSUE_BRANCH_CODE", nullable =true, length = 50)
    private String issueBranchCode;
    
    @Column(name = "PRIMARY_ACCT_NO", nullable =true, length = 50)
    private String primaryAcctNo;
    
    @Column(name = "PRIMARY_ACCT_TYPE", nullable =true, length = 50)
    private String primaryAcctType;
    
    @Column(name = "PRIMARY_ACCT_SURVIOUR", nullable =true, length = 50)
    private String primaryAcctSurviour;
    
    @Column(name = "SECONDARY_ACCT1", nullable =true, length = 50)
    private String secondaryAcct1;
    
    @Column(name = "SECONDARY_ACCT1_TYPE", nullable =true, length = 50)
    private String secondaryAcct1Type;
    
    @Column(name = "SECONDARY_ACCT1_SURVIOUR", nullable =true, length = 50)
    private String secondaryAcctSurviour;
    
    @Column(name = "SECONDARY_ACCT2", nullable =true, length = 50)
    private String secondaryAcct2;
    
    @Column(name = "SECONDARY_ACCT2_TYPE", nullable =true, length = 50)
    private String secondaryAcct2Type;
    
    @Column(name = "SECONDARY_SURVIOUR", nullable =true, length = 50)
    private String secondarySurviour;
    
    @Column(name = "EMBOSS_NAME", nullable =true, length = 50)
    private String embossName;
    
    @Column(name = "CUSTOMER_FIRST_NAME", nullable =true, length = 50)
    private String customerFirstName;
    
    @Column(name = "CUSTOMER_MIDDLE_NAME", nullable =true, length = 50)
    private String customerMiddleName; 
    
    @Column(name = "CUSTOMER_SUR_NAME", nullable =true, length = 50)
    private String customerSurName;
    
    @Column(name = "ADDR1", nullable =true, length = 50)
    private String addr1;
    
    @Column(name = "ADDR2", nullable =true, length = 50)
    private String addr2;
    
    @Column(name = "ADDR3", nullable =true, length = 50)
    private String addr3;
    
    @Column(name = "ADDR4", nullable =true, length = 50)
    private String addr4;
    
    @Column(name = "CITY", nullable =true, length = 50)
    private String city;
    
    @Column(name = "PIN", nullable =true, length = 50)
    private String pin;
    
    @Column(name = "OFFICE_PHONE", nullable =true, length = 50)
    private String officePhone;
    
    @Column(name = "HOME_PHONE", nullable =true, length = 50)
    private String homePhone;
    
    @Column(name = "PRODUCT", nullable =true, length = 50)
    private String product;
    
    @Column(name = "CARD_STATUS", nullable =true, length = 50)
    private String cardStatus; 
    
    @Column(name = "REGISTRATION_DATE", nullable =true, length = 50)
    private Date registrationDate;
    
    @Column(name = "FAX", nullable =true, length = 50)
    private String fax;
    
    @Column(name = "EMAIL", nullable =true, length = 50)
    private String email;
    
    @Column(name = "FATHERS_FIRST_NAME", nullable =true, length = 50)
    private String fathersFirstName;
    
    @Column(name = "MOTHER_MAIDEN_NAME", nullable =true, length = 50)
    private String motherMaidenName;
    
    @Column(name = "DATE_OF_BIRTH", nullable =true, length = 50)
    private Date dateOfBirth;
    
    @Column(name = "YEAR_OF_PASSING_SSC", nullable =true, length = 50)
    private String yearOfPassingSSC;
    
    @Column(name = "YEAR_OF_MARRIAGE", nullable =true, length = 50)
    private String yearOfMarriage;
    
    @Column(name = "FOURTH_LINE_PRINTING_DATA", nullable =true, length = 50)
    private String fourthLinePrintingData;
    
    @Column(name = "ISD_CODE", nullable =true, length = 50)
    private String isdCode;
    
    @Column(name = "MOBILE_NO", nullable =true, length = 50)
    private String mobileNo;
    
    @Column(name = "PRIMARY_ACCOUNT_PRODUCT_CODE", nullable =true, length = 50)
    private String primaryAccountProductCode;
    
    @Column(name = "SECONDARY_ACCOUNT1_P_C", nullable =true, length = 50)
    private String secondaryAccount1ProductCode;
    
    @Column(name = "SECONDARY_ACCOUNT2_P_C", nullable =true, length = 50)
    private String secondaryAccount2ProductCode;
    
    @Column(name = "HOME_BRANCH_CIRCLE_CODE", nullable =true, length = 50)
    private String homeBranchCircleCode; 
    
    @Column(name = "ISSUE_BRANCH_CIRCLE_CODE", nullable =true, length = 50)
    private String issueBranchCircleCode;
    
    @Column(name = "RSN", nullable =true)
    private Long rsn;
    
    @Column(name="STATUS")
    private Long status;
    
    @Column(name="PIN_STATUS", length=50)
    private Long pinstatus;
     
    @Column(name="RULE_STATUS", length=50)
    private String ruleStatus;
    
    
    @Column(name="FLOW_STATUS", length=50)
    private String flowStatus;
    
//    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="CREATED_DATE")
    private Date createdDate;
    
    
    @Column(name="FILE_ID", length=15)
    private Long fileId; 
    
    @Column(name="AUF_ID")
    private Long aufId;
    
    @Column(name="EMBOSS_ID", length=15)
    private Long embossaId;
    
    
    @Column(name="CARD_AWB", length=50)
    private String cardAWB;
    
    @Column(name="PIN_AWB", length=50)
    private String pinAWB;
    
    @Column(name="CARD_SERVICE_PROVIDER")
    private String cardServiceProvider;
    
    @Column(name="PIN_SERVICE_PROVIDER")
    private String pinServiceProvider;
    
    @Column(name="IS_INDIVIDUAL")
    private Integer isIndividual;
    
    @Column(name="PAN_MASKED")
    private String panMasked;
    
    @Column(name="IS_VIP")
    private Integer isVIP;    
    
    @Column(name="BANK_ID")
    private Long bankId;
    
    @Column(name="PROCESSED_BRANCH_CODE")
    private String processedBranchCode;
    
    @Column(name="LCPC_BRANCH")
    private String lcpcBranch;
    
    @Column(name="RTO_BRANCH")
    private String rtoBranch;
    
    @Column(name="EMBO_BATCH_NAME")
    private String emboBatchName;
    
    
    
	public String getEmboBatchName() {
		return emboBatchName;
	}

	public void setEmboBatchName(String emboBatchName) {
		this.emboBatchName = emboBatchName;
	}

	public String getRtoBranch() {
		return rtoBranch;
	}

	public void setRtoBranch(String rtoBranch) {
		this.rtoBranch = rtoBranch;
	}

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

	public Long getAufId() {
		return aufId;
	}

	public void setAufId(Long aufId) {
		this.aufId = aufId;
	}

	public Long getBankId() {
		return bankId;
	}

	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}

	public Integer getIsVIP() {
		return isVIP;
	}

	public void setIsVIP(Integer isVIP) {
		this.isVIP = isVIP;
	}

	public String getCardServiceProvider() {
		return cardServiceProvider;
	}

	public void setCardServiceProvider(String cardServiceProvider) {
		this.cardServiceProvider = cardServiceProvider;
	}

	public String getPinServiceProvider() {
		return pinServiceProvider;
	}

	public void setPinServiceProvider(String pinServiceProvider) {
		this.pinServiceProvider = pinServiceProvider;
	}

	public String getCardAWB() {
		return cardAWB;
	}

	public void setCardAWB(String cardAWB) {
		this.cardAWB = cardAWB;
	} 

	public String getPanMasked() {
		return panMasked;
	}

	public void setPanMasked(String panMasked) {
		this.panMasked = panMasked;
	}

	public String getPinAWB() {
		return pinAWB;
	}

	public void setPinAWB(String pinAWB) {
		this.pinAWB = pinAWB;
	}

	public Long getEmbossaId() {
		return embossaId;
	}

	public void setEmbossaId(Long embossaId) {
		this.embossaId = embossaId;
	}

	public Integer getIsIndividual() {
		return isIndividual;
	}

	public void setIsIndividual(Integer isIndividual) {
		this.isIndividual = isIndividual;
	}

	
	public Long getPinstatus() {
		return pinstatus;
	}

	public void setPinstatus(Long pinstatus) {
		this.pinstatus = pinstatus;
	}


	

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public Long getRsn() {
		return rsn;
	}

	public void setRsn(Long rsn) {
		this.rsn = rsn;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	public final Long getStatus() {
		return status;
	}

	public final void setStatus(Long status) {
		this.status = status;
	}

	public Long getCreditCardDetailsId() {
		return creditCardDetailsId;
	}

	public void setCreditCardDetailsId(Long creditCardDetailsId) {
		this.creditCardDetailsId = creditCardDetailsId;
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
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

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
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

	public void setSecondaryAccount1ProductCode(
			String secondaryAccount1ProductCode) {
		this.secondaryAccount1ProductCode = secondaryAccount1ProductCode;
	}

	public String getSecondaryAccount2ProductCode() {
		return secondaryAccount2ProductCode;
	}

	public void setSecondaryAccount2ProductCode(
			String secondaryAccount2ProductCode) {
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

}
