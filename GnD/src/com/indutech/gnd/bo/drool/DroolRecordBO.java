package com.indutech.gnd.bo.drool;

import java.util.Date;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.indutech.gnd.bo.BankBO;
import com.indutech.gnd.bo.BranchBO;
import com.indutech.gnd.bo.ProductBO;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dto.CoreFiles;
import com.indutech.gnd.util.DataInitialization;
import com.indutech.gnd.service.FileStateManager;


public class DroolRecordBO {
	
	Logger logger = Logger.getLogger(DroolRecordBO.class);
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}



	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

	public static final String FLOW_PASS                   = "RECORD_VALID";
	public static final String FLOW_ERROR                  = "RECORD_ERROR";
	public static final String FLOW_HOLD                   = "RECORD_HOLD";
	public static final String FLOW_SYNTAX_ERR             = "RECORD_SYNTAX_ERROR";
	public static final String FLOW_IGNORE_ERR             = "RECORD_IGNORE_ERROR";
	public static final String RULE_IGNORE_ERR			   = "Product Not Available";
	public static final String FLOW_NPC_ERR                = "RECORD_CNF_ERROR";
	public static final String FLOW_MANUAL_HOLD            = "RECORD_MANUAL_HOLD";
	public static final String FLOW_MANUAL_REJECT          = "RECORD_MANUAL_REJECT";
	public static final String RULE_APPROVED_STATUS        =  "Valid Record";
	public static final String FLOW_AWB_ASSIGNED           =  "AWB_ASSIGNED";
	
	public static final String SERIAL_LENGTH_ERR           = "Serial No Field Length Error";
	public static final String SERIAL_CONTENT_ERR          = "Serial No is invalid";
	public static final String INSTITUTIONID_LENGTH_ERR    = "Institution ID Field Length Error";
	public static final String INSTITUTIONID_CONTENT_ERR   = "Instituion ID invalid";
	public static final String CUSTID_LENGTH_ERR           = "Cust ID Field Length Error";
	public static final String CUSTID_CONTENT_ERR          = "Cust ID invalid";
	public static final String BOTH_HOME_AND_ISSUE_CONTENT_ERR   = "Both Home Branch and Issue branch not in master";
	public static final String HOMEBRANCHCODE_LENGTH_ERR   = "Home Branch Field Length Error";
	public static final String HOMEBRANCHCODE_CONTENT_ERR  = "Home Branch invalid";
	public static final String HOMEBRANCH_AND_ISSUEBRANCH_ISNONCARD="Both Issue Branch code and Home Branch code inactive";
	public static final String HOMEBRANCHCODE_INVALID_ERR  = "Home Branch not in master";
	public static final String HOMEBRANCHCODEISNONCARDISSUE= "Home Branch Code inactive";
	public static final String ISSUEBRANCHCODE_LENGTH_ERR  = "Issue Branch Field Length Error";
	public static final String ISSUEBRANCHCODE_CONTENT_ERR = "Issue Branch invalid";
	public static final String ISSUEBRANCHCODE_INVALID_ERR = "Issue Branch not in master";
	public static final String PAN_LENGTH_ERR              = "Primary Account Field Length Error";
	public static final String PAN_CONTENT_ERR             = "Primary Account invalid";
	public static final String PANTYPE_LENGTH_ERR          = "Primary Account Type Field Length Error";
	public static final String PANTYPE_CONTENT_ERR         = "Primary Account Type invalid";
	public static final String PANSURVIOUR_LENGTH_ERR      = "Primary Account Surviour Field Length Error";
	public static final String SAN_LENGTH_ERR              = "Secondary Account Field Length Error";
	public static final String SAN_CONTENT_ERR             = "Secondary Account Field invalid";
	public static final String SANTYPE_LENGTH_ERR          = "Secondary Account Type Field Length Error";
	public static final String SANTYPE_CONTENT_ERR         = "Secondary Account Type invalid";
	public static final String SANSURVIOUR_LENGTH_ERR      = "Secondary Account Surviour Field Length Error";
	public static final String SAN2_LENGTH_ERR             = "Secondary Account2 Field Length Error";
	public static final String SAN2_CONTENT_ERR            = "Secondary Account2 Field invalid";
	public static final String SAT2_LENGTH_ERR             = "Secondary Account2 Type Field Length Error";
	public static final String SAN2TYPE_CONTENT_ERR         = "Secondary Account2 Type invalid";
	public static final String SAN2SURVIOUR_LENGTH_ERR     = "Secondary Account2 Surviour Field Length Error";
	public static final String EMBOSS_LENGTH_ERR           = "Emboss Field Length Error";
	public static final String EMBOSS_CONTENT_ERR          = "Emboss invalid";
	public static final String FIRSTNAME_LENGTH_ERR        = "First Name Field Length Error";
	public static final String FIRSTNAME_MISSING_ERR       = "First Name Missing";
	public static final String MIDDLENAME_LENGTH_ERR       = "Middle Name Field Length Error";
	public static final String MIDDLENAME_MISSING_ERR      = "Middle Name Missing";
	public static final String SURNAME_LENGTH_ERR          = "Sur Name Field Length Error";
	public static final String SURNAME_MISSING_ERR         = "Sur Name Missing";
	public static final String ADDR1_LENGTH_ERR            = "Invalid Address Field1 Length";
	public static final String ADDR1_CONTENT_ERR           = "Invalid Address";
	public static final String ADDR2_LENGTH_ERR            = "Invalid Address Field2 Length";
	public static final String ADDR2_CONTENT_ERR           = "Invalid Address";
	public static final String ADDR3_LENGTH_ERR            = "Invalid Address Field3 Length";
	public static final String ADDR3_CONTENT_ERR           = "Invalid Address";
	public static final String ADDR4_LENGTH_ERR            = "Invalid Address Field4 Length";
	public static final String ADDR4_CONTENT_ERR           = "Invalid Address";
	public static final String ADDR_EMPTY_ERR              = "Adddress field empty";
	public static final String PIN_LENGTH_ERR              = "Invalid PIN Length";
	public static final String PIN_CONTENT_ERR             = "Invalid PIN Code";
	public static final String OFFICEPHONE_LENGTH_ERR      = "Office Phone Field Length Error";
	public static final String OFFICEPHONE_CONTENT_ERR     = "Office Phone invalid";
	public static final String HOMEPHONE_LENGTH_ERR        = "Home Phone Field Length Error";
	public static final String HOMEPHONE_CONTENT_ERR       = "Home Phone invlaid";
	public static final String PRODUCT_LENGTH_ERR          = "Product Field Length Error";
	public static final String PRODUCT_MISSING_ERR         = "Product Missing";
	public static final String PRODUCT_INVALID_ERR         = "Product and Bin Not in Master";
	public static final String PRODUCT_STATUS_INVALID_ERR  = "Product Status Invalid";
	public static final String CARDSTATUS_LENGTH_ERR       = "Card Status Field Length Error";
	public static final String CARDSTATUS_CONTENT_ERR      = "Card Status invalid";
	public static final String REGDATE_LENGTH_ERR          = "Reg Date Field Length Error";
	public static final String REGDATE_CONTENT_ERR         = "Reg Date Field invalid";
	public static final String FAX_LENGTH_ERR              = "FAX Field Length Error";
	public static final String FAX_CONTENT_ERR             = "FAX invalid";
	public static final String EMAIL_LENGTH_ERR            = "Email Field Length Error";
	public static final String FATHERNAME_LENGTH_ERR       = "Father Name Field Length Error";
	public static final String MOTHERNAME_LENGTH_ERR       = "Mother Name Field Length Error";
	public static final String BIRTHDATE_LENGTH_ERR        = "Birth Date Field Length Error";
	public static final String BIRTHDATE_CONTENT_ERR        = "Birth Date Field invalid";
	public static final String SSCYEAR_LENGTH_ERR          = "SSC Year Field Length Error";
	public static final String MARRIAGEYEAR_LENGTH_ERR     = "Marriage Year Field Length Error";
	public static final String FOURTHLINE_LENGTH_ERR       = "Fourth Line Field Length Error";
	public static final String FOURTHLINE_MISSING_ERR       = "Fourth Line Field is empty";
	public static final String ISD_LENGTH_ERR              = "ISD Field Length Error";
	public static final String MOBILE_LENGTH_ERR           = "MObile Field Length Error";
	public static final String PANPRODUCT_LENGTH_ERR       = "PAN Product Field Length Error";
	public static final String SANPRODUCT_LENGTH_ERR       = "SAN Product Field Length Error";
	public static final String SAN2PRODUCT_LENGTH_ERR      = "SAN2 Product Field Length Error";
	public static final String HOMECIRCLE_LENGTH_ERR       = "Home Circle Field Length Error";
	public static final String ISSUECIRCLE_LENGTH_ERR      = "Issuance Circle Field Length Error";
	public static final String COMPOSITE_KEY_ERROR         = "Record already exist 15 days limit";
	public static final String CITY_LENGTH_ERR             = "City Length Error";
	public static final String CITY_CONTENT_ERR			   = "CIty is Invalid";
	public static final String SSCYEAR_CONTENT_ERR          = "SSC Year Field Type invalid";
	public static final String MARRIAGEYEAR_CONTENT_ERR     = "Marriage Year Field Type Error";
	public static final String ISD_CONTENT_ERR             = "ISD Field Type Error";
	public static final String MOBILE_CONTENT_ERR          = "MObile Field Type Error";
	public static final String HOMECIRCLE_CONTENT_ERR      = "Home Circle Field Type Error";
	public static final String ISSUECIRCLE_CONTENT_ERR     = "Issuance Circle Field Type Error";
	public static final String NCF_FOUND_ERR               = "Correspoding NCF Record not found";
	public static final String NCF_COMPOSITE_KEY_ERROR     = "Record already exist 15 days limit";
	public static final String LCPC_BRANCH_INVALID_ERROR  = "LCPC Branch not present";

	
	public static final String RULE_MANUAL_HOLD            = "Record Hold by Manually";
	public static final String RULE_MANUAL_REJECT            = "Record Rejected by Manually";
	//database errors
	

	public static final String PHOTO_CARD_ERROR= "Photo card not present in specified location";
	public static final int AUFGENERATED=5;
	
	public static final String RULE_CARD_AWB_ASSIGNED_ERROR  = "AWB Keys for card is not present in Master AWB";
	public static final String RULE_PIN_AWB_ASSIGNED_ERROR  = "AWB Keys for pin is not present in Master AWB";
	
	
	public static final String RULE_BRANCH_GROUP_5000_EXCEED = "Same Branch having more than 5000 records";
	
	
	private Long fileId;
	
	private ProductBO productBO;
	
	private BranchBO branchBO;
	
	private Long creditCardDetailsId;
	
	private String fileName;
	
    private String serialNo;
    
    private String flowStatus = FLOW_PASS;
 
    private String institutionId;
 
    private String customerId;
    
    private String homeBranchCode;
    
    private String issueBranchCode;
    
    private String primaryAcctNo;
    
    private String primaryAcctType;
    
    private String primaryAcctSurviour;
    
    private String secondaryAcct1;
    
    private String secondaryAcct1Type;
    
    private String secondaryAcctSurviour;
    
    private String secondaryAcct2;
    
    private String secondaryAcct2Type;
    
    private String secondarySurviour;
    
    private String embossName;    
    
    private String customerFirstName;
    
    private String customerMiddleName; 
    
    private String customerSurName;
    
    
    private String addr1;
    
    
    private String addr2;
    
    
    private String addr3;
    
   
    private String addr4;
    
    
    private String city;
    
    
    private String pin;
    
   
    private String officePhone;
    
   
    private String homePhone;
    
   
    private String product;
    
   
    private String cardStatus; 
    
   
    private Date registrationDate;
    
   
    private String fax;
    
   
    private String email;
    
   
    private String fathersFirstName;
    
   
    private String motherMaidenName;
    
   
    private Date dateOfBirth;
    
   
    private String yearOfPassingSSC;
    
   
    private String yearOfMarriage;
    
  
    private String fourthLinePrintingData;
    
   
    private String isdCode;
    
  
    private String mobileNo;
    
   
    private String primaryAccountProductCode;
    
  
    private String secondaryAccount1ProductCode;
    
  
    private String secondaryAccount2ProductCode;
    
   
    private String homeBranchCircleCode; 
    
  
    private String issueBranchCircleCode;  
    
    private String lcpcBranch;
    
    private String awbName;
    
    private String ruleStatus;
    private Long embossaId;  
    private Long aluFileId;
    private Long bankId;
    private Long vipFileId;
    private Long pinStatus;
    
    private Integer inviduality;
    private Integer isVIP;
    
    private boolean homeBranchValid;
    private boolean lcpcBranchValid;
    private boolean issueBranchValid;
    private boolean productCodeValid;
    private boolean photoStatusValid;
    private boolean compositeValid;    
    private boolean productStatusValid;
    private boolean homeBranchStatusValid;
    private boolean issueBranchStatusValid;
    private boolean fourthLineRequiredValid;
    
    private boolean registrationDateValid;
    
    private boolean dobValid;
    
    private boolean sscPassValid;
    private boolean marriageValid;
    
    private String cardAWB;
    
    private int productMappingFlag = 0 ;
    private String productMappingInfo;
    
    
    
    
    
	public String getLcpcBranch() {
		return lcpcBranch;
	}



	public void setLcpcBranch(String lcpcBranch) {
		this.lcpcBranch = lcpcBranch;
	}



	public boolean isLcpcBranchValid() {
		return lcpcBranchValid;
	}



	public void setLcpcBranchValid(boolean lcpcBranchValid) {
		this.lcpcBranchValid = lcpcBranchValid;
	}



	public int getProductMappingFlag() {
		return productMappingFlag;
	}



	public void setProductMappingFlag(int productMappingFlag) {
		this.productMappingFlag = productMappingFlag;
	}



	public String getProductMappingInfo() {
		return productMappingInfo;
	}



	public void setProductMappingInfo(String productMappingInfo) {
		this.productMappingInfo = productMappingInfo;
	}



	public String getCardAWB() {
		return cardAWB;
	}



	public void setCardAWB(String cardAWB) {
		this.cardAWB = cardAWB;
	}



	public Integer getIsVIP() {
		return isVIP;
	}



	public void setIsVIP(Integer isVIP) {
		this.isVIP = isVIP;
	}



	public Long getPinStatus() {
		return pinStatus;
	}



	public void setPinStatus(Long pinStatus) {
		this.pinStatus = pinStatus;
	}



	public Long getVipFileId() {
		return vipFileId;
	}



	public void setVipFileId(Long vipFileId) {
		this.vipFileId = vipFileId;
	}



	public Long getBankId() {
		return bankId;
	}



	public void setBankId(Long bankId) {
		this.bankId = bankId;
	}



	public boolean isDobValid() {
		return dobValid;
	}
	
	

	public boolean getFourthLineRequiredValid() {
		return fourthLineRequiredValid;
	}



	public void setFourthLineRequiredValid(boolean fourthLineRequiredValid) {
		this.fourthLineRequiredValid = fourthLineRequiredValid;
	}



	public void setDobValid(boolean dobValid) {
		this.dobValid = dobValid;
	}

	public boolean isSscPassValid() {
		return sscPassValid;
	}

	public void setSscPassValid(boolean sscPassValid) {
		this.sscPassValid = sscPassValid;
	}

	public boolean isMarriageValid() {
		return marriageValid;
	}

	public void setMarriageValid(boolean marriageValid) {
		this.marriageValid = marriageValid;
	}

	public boolean getRegistrationDateValid() {
		return registrationDateValid;
	}

	public void setRegistrationDateValid(boolean registrationDateValid) {
		this.registrationDateValid = registrationDateValid;
	}

	public Integer getInviduality() {
		return inviduality;
	}

	public void setInviduality(Integer inviduality) {
		this.inviduality = inviduality;
	}

	public boolean isProductStatusValid() {
		return productStatusValid;
	}

	public void setProductStatusValid(boolean productStatusValid) {
		this.productStatusValid = productStatusValid;
	}

	public boolean isHomeBranchStatusValid() {
		return homeBranchStatusValid;
	}

	public void setHomeBranchStatusValid(boolean homeBranchStatusValid) {
		this.homeBranchStatusValid = homeBranchStatusValid;
	}

	public boolean isIssueBranchStatusValid() {
		return issueBranchStatusValid;
	}

	public void setIssueBranchStatusValid(boolean issueBranchStatusValid) {
		this.issueBranchStatusValid = issueBranchStatusValid;
	}

	public Long getAluFileId() {
		return aluFileId;
	}

	public void setAluFileId(Long aluFileId) {
		this.aluFileId = aluFileId;
	}

	
	public Long getEmbossaId() {
		return embossaId;
	}

	public void setEmbossaId(Long embossaId) {
		this.embossaId = embossaId;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public boolean isCompositeValid() {
		return compositeValid;
	}

	public void setCompositeValid(boolean compositeValid) {
		this.compositeValid = compositeValid;
	}

	public boolean isIssueBranchValid() {
		return issueBranchValid;
	}

	public void setIssueBranchValid(boolean issueBranchValid) {
		this.issueBranchValid = issueBranchValid;
	}

	

	public boolean getProductCodeValid() {
		return productCodeValid;
	}

	public void setProductCodeValid(boolean productCodeValid) {
		this.productCodeValid = productCodeValid;
	}

	public boolean isPhotoStatusValid() {
		return photoStatusValid;
	}

	public void setPhotoStatusValid(boolean photoStatusValid) {
		this.photoStatusValid = photoStatusValid;
	}

	public boolean isHomeBranchValid() {
		return homeBranchValid;
	}

	public void setHomeBranchValid(boolean homeBranchValid) {
		this.homeBranchValid = homeBranchValid;
	}

	
	public ProductBO getProductBO() {
		return productBO;
	}

	public void setProductBO(ProductBO productBO) {
		this.productBO = productBO;
	}

	public BranchBO getBranchBO() {
		return branchBO;
	}

	public void setBranchBO(BranchBO branchBO) {
		this.branchBO = branchBO;
	}

	public String getRuleStatus() {
		return ruleStatus;
	}

	public void setRuleStatus(String ruleStatus) {
		this.ruleStatus = ruleStatus;
	}

	private Long status;
    
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

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date date) {
		this.registrationDate = date;
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

	public void setDateOfBirth(Date date) {
		this.dateOfBirth = date;
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

	public String getAwbName() {
		return awbName;
	}

	public void setAwbName(String awbName) {
		this.awbName = awbName;
	}

	public String getFlowStatus() {
		return flowStatus;
	}

	public void setFlowStatus(String flowStatus) {
		this.flowStatus = flowStatus;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	} 
	
	
	
	
	public Long getCreditCardDetailsId() {
		return creditCardDetailsId;
	}



	public void setCreditCardDetailsId(Long creditCardDetailsId) {
		this.creditCardDetailsId = creditCardDetailsId;
	}



	@SuppressWarnings("rawtypes")
//	//@Transactoinal
	public boolean setFile(Exchange exchange) {
		GenericFile file = null;
		boolean result = true;
		try {
			file = (GenericFile) exchange.getIn().getBody();
			String fileName = file.getFileNameOnly();
			setFileName(fileName);
			List<CoreFiles> coreFileList =  getGndDAO().getFileList(getFileName());
			if(coreFileList != null && coreFileList.size() > 0) {
				CoreFiles corefile = coreFileList.get(0);
				setFileId(corefile.getId());
				logger.info("record validation started, please wait.........");
			}
			else {
				logger.info("Seems file is placed directly in approved folder, Please check once");
				result = false;
				return result;
			}
			List<BankBO> bankList = DataInitialization.getInstance().getBankInfo();//getGndDAO().getBankCodeByPrefix(fileName.substring(0,1));
			if(bankList != null) {
				for(BankBO bank : bankList) {
					if(bank.getPrefix().equalsIgnoreCase(fileName.substring(0,1))) {
						setBankId(bank.getBankId());
						if(bank.getLPBranchGroup() == 0) {
							setInviduality(FileStateManager.CORE_NORMAL_INDIVIDUALITY);
						} else {
							setInviduality(FileStateManager.CORE_NCF_INDIVIDUALITY);
						}
						break;
					}
					
				}
//				setBankId(bank.getBankId());
//				if(bank.getLPAWBBranchGroup() == 0) {
//					setInviduality(FileStateManager.CORE_NORMAL_INDIVIDUALITY);
//				} else {
//					setInviduality(FileStateManager.CORE_NCF_INDIVIDUALITY);
//				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	

}

