package com.indutech.gnd.service.drool;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Pattern;

import org.apache.camel.Exchange;
import org.apache.camel.component.file.GenericFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Multimap;
import com.indutech.gnd.bo.ProductMappingBO;
import com.indutech.gnd.bo.RecordEventBO;
import com.indutech.gnd.bo.drool.DroolRecordBO;
import com.indutech.gnd.dao.BranchDAOImpl;
import com.indutech.gnd.dao.FindPathImpl;
import com.indutech.gnd.dao.GNDDAOImpl;
import com.indutech.gnd.dao.ProductDAOImpl;
import com.indutech.gnd.dto.Branch;
import com.indutech.gnd.dto.CreditCardDetails;
import com.indutech.gnd.dto.Product;
import com.indutech.gnd.dto.ProductMapping;
import com.indutech.gnd.enumTypes.Status;
import com.indutech.gnd.service.CardStateManagerService;
import com.indutech.gnd.service.FileStateManager;
import com.indutech.gnd.service.PropertiesLoader;
import com.indutech.gnd.util.DataInitialization;
import com.indutech.gnd.util.StringUtil;

@Component("droolService")
public class CreditCardService {
	
	Logger logger = Logger.getLogger(CreditCardService.class);
	
	common.Logger log = common.Logger.getLogger(CreditCardService.class);
	
	private Properties properties = PropertiesLoader.getInstance().loadProperties();
	
	List<ProductMappingBO> productMapList = null;
	private String fileName = null;
	
	Status stat = Status.valueOf("ACTIVE");
	String status = stat.getStatus();

	private static int currYear,fromYear=1990;
	
	
	
	@Autowired
	private ProductDAOImpl productDAO;
	
	@Autowired
	private GNDDAOImpl gndDAO;
	
	@Autowired
	private BranchDAOImpl branchDAO;
	
	@Autowired
	private FindPathImpl findPath;
	
	@Autowired
	private DroolRecordBO droolRecord;
	
	
	private String cardAWB = "";
	
	
	
	public String getCardAWB() {
		return cardAWB;
	}

	public void setCardAWB(String cardAWB) {
		this.cardAWB = cardAWB;
	}

	public DroolRecordBO getDroolRecord() {
		return droolRecord;
	}

	public void setDroolRecord(DroolRecordBO droolRecord) {
		this.droolRecord = droolRecord;
	}

	public GNDDAOImpl getGndDAO() {
		return gndDAO;
	}

	public void setGndDAO(GNDDAOImpl gndDAO) {
		this.gndDAO = gndDAO;
	}

		public FindPathImpl getFindPath() {
		return findPath;
	}

	public void setFindPath(FindPathImpl findPath) {
		this.findPath = findPath;
	}

		public ProductDAOImpl getProductDAO() {
		return productDAO;
	}


	public void setProductDAO(ProductDAOImpl productDAO) {
		this.productDAO = productDAO;
	}


	public BranchDAOImpl getBranchDAO() {
		return branchDAO;
	}

	public void setBranchDAO(BranchDAOImpl branchDAO) {
		this.branchDAO = branchDAO;
	}

	
		@SuppressWarnings("rawtypes")
		public DroolRecordBO getDroolRecordBO(Map creditCardDetailsMap) {
			setCardAWB("");
			DroolRecordBO droolRecord = new DroolRecordBO();
			try {				
				if(creditCardDetailsMap.size() != 44) {
					droolRecord.setFlowStatus(DroolRecordBO.FLOW_SYNTAX_ERR);					
					return droolRecord;
				}
				SimpleDateFormat smdformatter = new SimpleDateFormat("dd/MM/yyyy");
				droolRecord.setSerialNo((String) creditCardDetailsMap.get("SERIAL NO"));
//				logger.info("Serial no is : "+droolRecord.getSerialNo());
				droolRecord.setAddr1((String)creditCardDetailsMap.get("ADDR1"));
				droolRecord.setAddr2((String)creditCardDetailsMap.get("ADDR2"));
				droolRecord.setAddr3((String)creditCardDetailsMap.get("ADDR3"));
				droolRecord.setAddr4((String)creditCardDetailsMap.get("ADDR4"));
				droolRecord.setCardStatus((String)creditCardDetailsMap.get("CARD STATUS"));
				droolRecord.setCity((String)creditCardDetailsMap.get("CITY"));
				droolRecord.setCustomerFirstName((String)creditCardDetailsMap.get("CUSTOMER FIRST NAME"));
				droolRecord.setCustomerId((String)creditCardDetailsMap.get("CUSTOMER ID"));
				droolRecord.setCustomerMiddleName((String)creditCardDetailsMap.get("CUSTOMER MIDDLE NAME"));
				droolRecord.setCustomerSurName((String)creditCardDetailsMap.get("CUSTOMER SUR NAME"));
				String dateOfbirth =(String)creditCardDetailsMap.get("DATE OF BIRTH");
				String registrationDate =(String)creditCardDetailsMap.get("REGISTRATION DATE");
				try {
					if(!StringUtil.isEMptyOrNull(dateOfbirth.trim()) && Pattern.compile("\\d+").matcher(dateOfbirth.trim()).matches()) {					
						
						if(dateOfbirth.contains("/")) {
							droolRecord.setDateOfBirth(smdformatter.parse(dateOfbirth));
						}
						else {
							SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
							Date doBConvDate = sdf.parse(dateOfbirth);
							String dobString = sdf.format(doBConvDate);
							if(!dobString.equals(dateOfbirth.trim())) {
								droolRecord.setDateOfBirth(smdformatter.parse("27/02/1880"));
//								logger.info("now the date is : "+droolRecord.getDateOfBirth());
							}
							else {
								droolRecord.setDateOfBirth(doBConvDate);
							}
						}
					}
				}
				catch(ParseException pe) {
					droolRecord.setDateOfBirth(null);
					logger.error(pe.getMessage());
					pe.printStackTrace();
				}
				try {
					if(!StringUtil.isEMptyOrNull(registrationDate.trim()) && registrationDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
						
						Date regdConvDate = smdformatter.parse(registrationDate);
						String regdString = smdformatter.format(regdConvDate);
						if(!regdString.equals(registrationDate.trim())) {
							droolRecord.setRegistrationDate(smdformatter.parse("27/02/1880"));
						}
						else {
							droolRecord.setRegistrationDate(regdConvDate);
						}
					}
				} catch (ParseException e) {
					droolRecord.setRegistrationDate(null);
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				droolRecord.setEmail((String)creditCardDetailsMap.get("EMAIL"));
				droolRecord.setEmbossName((String)creditCardDetailsMap.get("EMBOSS NAME"));
				droolRecord.setFathersFirstName((String)creditCardDetailsMap.get("FATHERS FIRST NAME"));
				droolRecord.setFax((String)creditCardDetailsMap.get("FAX"));
				droolRecord.setFourthLinePrintingData((String)creditCardDetailsMap.get("FOURTH LINE PRINTING DATA"));
				droolRecord.setHomeBranchCircleCode((String)creditCardDetailsMap.get("HOME BRANCH CIRCLE CODE"));
				droolRecord.setHomeBranchCode((String)creditCardDetailsMap.get("HOME BRANCH CODE"));
				droolRecord.setHomePhone((String)creditCardDetailsMap.get("HOME PHONE"));
				droolRecord.setInstitutionId((String)creditCardDetailsMap.get("INSTUTION ID"));
				droolRecord.setIsdCode((String)creditCardDetailsMap.get("ISD CODE"));
				droolRecord.setIssueBranchCircleCode((String)creditCardDetailsMap.get("ISSUE BRANCH CIRCLE CODE"));
				droolRecord.setIssueBranchCode((String)creditCardDetailsMap.get("ISSUE BRANCH CODE"));
				droolRecord.setMobileNo((String)creditCardDetailsMap.get("MOBILE NO"));
				droolRecord.setMotherMaidenName((String)creditCardDetailsMap.get("MOTHER MAIDEN NAME"));
				droolRecord.setOfficePhone((String)creditCardDetailsMap.get("OFFICE PHONE"));
				droolRecord.setPin((String)creditCardDetailsMap.get("PIN"));
				droolRecord.setPrimaryAccountProductCode((String)creditCardDetailsMap.get("PRIMARY ACCOUNT PRODUCT CODE"));
				droolRecord.setPrimaryAcctNo((String)creditCardDetailsMap.get("PRIMARY ACCT NO"));
				droolRecord.setPrimaryAcctSurviour((String)creditCardDetailsMap.get("PRIMARY ACCT SURVIOUR"));
				droolRecord.setPrimaryAcctType((String)creditCardDetailsMap.get("PRIMARY ACCT TYPE"));
				droolRecord.setProduct((String)creditCardDetailsMap.get("PRODUCT"));
				droolRecord.setSecondaryAccount1ProductCode((String)creditCardDetailsMap.get("SECONDARY ACCOUNT1 PRODUCT CODE"));
				droolRecord.setSecondaryAccount2ProductCode((String)creditCardDetailsMap.get("SECONDARY ACCOUNT2 PRODUCT CODE"));
				droolRecord.setSecondaryAcct1((String)creditCardDetailsMap.get("SECONDARY ACCT1"));
				droolRecord.setSecondaryAcct2((String)creditCardDetailsMap.get("SECONDARY ACCT2"));
				droolRecord.setSecondaryAcct2Type((String)creditCardDetailsMap.get("SECONDARY ACCT2 TYPE"));
				droolRecord.setSecondaryAcctSurviour((String)creditCardDetailsMap.get("SECONDARY ACCT1 SURVIOUR"));
				droolRecord.setSecondarySurviour((String)creditCardDetailsMap.get("SECONDARY_SURVIOUR"));
				
				droolRecord.setBankId(getDroolRecord().getBankId());
				
				droolRecord.setYearOfMarriage((String)creditCardDetailsMap.get("YEAR OF MARRIAGE"));
				droolRecord.setYearOfPassingSSC((String)creditCardDetailsMap.get("YEAR OF PASSING SSC"));
				droolRecord.setSecondaryAcct1Type((String)creditCardDetailsMap.get("SECONDARY ACCT1 TYPE"));

				droolRecord.setInviduality(getDroolRecord().getInviduality());
				droolRecord.setFileName(getDroolRecord().getFileName());
				droolRecord.setRegistrationDateValid(isYrRegistrationValid((String)creditCardDetailsMap.get("REGISTRATION DATE")));
//				boolean composit = isValidCompositeKey(droolRecord.getCustomerId(),droolRecord.getPrimaryAcctNo(),droolRecord.getProduct());
//				droolRecord.setCompositeValid(composit);
//				if(composit == false) {
//					logger.info("Card already exists");
//					droolRecord.setCardAWB(getCardAWB());
//					this.setCardAWB("");
//				}
				droolRecord.setDobValid(isDOBValidRange_rule_5y1((String)creditCardDetailsMap.get("DATE OF BIRTH")));
			} catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return droolRecord;
		}
		
		public DroolRecordBO getDBDroolRecordBO(DroolRecordBO droolRecord) {
			try {
				droolRecord.setHomeBranchValid(isValidBranchCode(droolRecord.getHomeBranchCode().trim(), droolRecord.getBankId()));
				droolRecord.setIssueBranchValid(isValidBranchCode(droolRecord.getIssueBranchCode().trim(), droolRecord.getBankId()));
				droolRecord.setLcpcBranchValid(isValidLCPCBranchCode(droolRecord));
				droolRecord.setProductCodeValid(isValidProductCode(droolRecord.getProduct().trim(),droolRecord.getInstitutionId().trim(), droolRecord.getBankId()));
				boolean photoCheck = searchPhoto(droolRecord.getPrimaryAcctNo().trim(),droolRecord.getProduct().trim(), droolRecord.getInstitutionId().trim(), droolRecord.getBankId());
				droolRecord.setPhotoStatusValid(photoCheck);
				droolRecord.setFourthLineRequiredValid(forthLineValidity(droolRecord.getFourthLinePrintingData(), droolRecord.getProduct().trim(), droolRecord.getInstitutionId().trim(), droolRecord.getBankId()));
			} catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return droolRecord;
			
		}
		
		
		public boolean isValidCompositeKey(String customerId, String primaryAcNO, String productId) {
			try {
				List<CreditCardDetails> list = getGndDAO().checkRecordValidity(customerId, primaryAcNO, productId);
				if(list.size() == 0) {
					return true;
				}
				else {
					Date date = DateUtils.addDays(new Date(), -15);
					CreditCardDetails details = (CreditCardDetails) list.get(0);
					Date recordCreatedDate = details.getCreatedDate();
					if(recordCreatedDate.before(date)) {
						return true;
					}
					setCardAWB(details.getCardAWB() != null ?details.getCardAWB():""); 
				}
			}catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			
			return false;
		}

		public boolean isValidBranchCode(String branchCode, Long bankId) {
			
			boolean result = false; 
			try {
				Map<Long, Map<String, Branch>> bankList = DataInitialization.getInstance().getBranchInfo();
				if(bankList != null && bankList.size() > 0 ) {
					Map<String, Branch> branchList = bankList.get(bankId);
					if(branchList != null && branchList.size() > 0) {
						Branch branch = (Branch) branchList.get(branchCode);
						if(branch != null) {
							if(branch.getBankId() == bankId && branch.getShortCode().equals(branchCode) && branch.getStatus() == Long.parseLong(status)) {
								result = true;
							}
						}
					}
				} 
			}catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return result;
			
		}
		
		public boolean isValidLCPCBranchCode(DroolRecordBO droolRecord) {
			
			boolean result = false; 
			try {
				Map<Long, Map<String, Branch>> bankList = DataInitialization.getInstance().getBranchInfo();
				if(bankList != null && bankList.size() > 0 ) {
					Map<String, Branch> branchList = bankList.get(droolRecord.getBankId());
					if(branchList != null && branchList.size() > 0) {
						Branch branch = (Branch) branchList.get(droolRecord.getIssueBranchCode());
						if(branch != null) {
							if(branch.getBankId() == droolRecord.getBankId() && branch.getShortCode().equals(droolRecord.getIssueBranchCode()) && branch.getStatus() == Long.parseLong(status)) {
								if(branch.getLcpcName() != null && !branch.getLcpcName().trim().isEmpty()) {
									droolRecord.setLcpcBranch(branch.getLcpcName());
									result = true;
								}
							}
						}
					}
				}
			}catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return result;
			
		}
		
		public boolean isValidProductCode(String productCode, String institutionId, Long bankId){
			boolean result = false; 
			try {
				Multimap<Long, Product> map = DataInitialization.getInstance().getProductInfo();
				List<Product> productList = (List<Product>) map.get(bankId);
				if(productList != null && productList.size() > 0) {
					for(Product product : productList) {
						if(product.getBankId() == bankId && product.getShortCode().equals(productCode) && product.getBin().equals(institutionId) && product.getStatus() == Long.parseLong(status)) {
							result = true;
							break;
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
			return result;
		}
		
		public boolean forthLineValidity(String forthLineData, String productCode, String institutionId, Long bankId) {
			boolean result = false;			
			try {				
				Product productInfo = null; 
//				List<Product> productList = getProductDAO().getProduct(productCode, institutionId, bankId);
				Multimap<Long, Product> map = DataInitialization.getInstance().getProductInfo();
				List<Product> productList = (List<Product>) map.get(bankId);
				if(productList.size() > 0) {	
					for(Product product : productList) {
						if(product.getShortCode().equals(productCode) && product.getBin().equals(institutionId) && product.getBankId() == bankId && product.getStatus() == Long.parseLong(status)) {
							productInfo = product;
							break;
						}
					}
				}
//				if(productList.size() > 0) {
//					Product product = (Product) productList.get(0);
				if(productInfo != null) {
					if(productInfo.getForthLineRequired() == 1) {						
						if(forthLineData.trim().isEmpty() == false) {
							result = true;
						}
					}
					else {
						result = true;
					}
				}
				else {
					result = true;
				}
			} catch(Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
			return result;
			
		}
		
		public boolean searchPhoto(String primaryAcNo, String productCode, String institutionId, Long bankId) {
			boolean result = false;
			Product productInfo = null;
			try {	
//				List<Product> productList = getProductDAO().getProduct(productCode, institutionId, bankId);
				Multimap<Long, Product> map = DataInitialization.getInstance().getProductInfo();
				List<Product> productList = (List<Product>) map.get(bankId);
				if(productList.size() > 0) {	
					for(Product product : productList) {
						if(product.getShortCode().equals(productCode) && product.getBin().equals(institutionId) && product.getBankId() == bankId && product.getStatus() == Long.parseLong(status)) {
							productInfo = product;
							break;
						}
					}
				}
				if(productInfo != null) {
					if(primaryAcNo.trim() != null) {
						if(productInfo.getPhotoCard() == 1) {					
							String imageDir = properties.getProperty("imagesInput");			
							File fileDir = new File(imageDir);
							if(fileDir.exists() && fileDir.isDirectory()) {
							File[] fileList=fileDir.listFiles();
							for (File file : fileList) {				
							    if (file.isFile()) {
							    	String fname = file.getName();
							    	String fnameonly[] = fname.split("\\.");
							    	String str1 = fnameonly[0];
							    	String str2 = fnameonly[1];
							    	if(str2.equalsIgnoreCase("JPG") || str2.equalsIgnoreCase("JPEG")) {	
											if (str1.equals(primaryAcNo.trim())) {
												FileUtils.moveFile(new File(imageDir+"/"+fname), new File(properties.getProperty("imagesApproved")+"/"+fname));
												result = true;
												break;
											}
							    		}
							    	}
								}
							}
							
						} else {
							result = true;
						}
					}
				}
			} catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return result;
		}
		
		
		
		
		
		
		// CNF object building
		
		@SuppressWarnings("rawtypes")
		@Transactional
		public DroolRecordBO getCNFDroolRecordBO(Map creditCardDetailsMap) {
			DroolRecordBO droolRecord = new DroolRecordBO();
			try { 
				if(creditCardDetailsMap.size() != 7 ) {
					droolRecord.setFlowStatus(DroolRecordBO.FLOW_SYNTAX_ERR);
					return droolRecord;
				}
				droolRecord.setHomeBranchCode((String)creditCardDetailsMap.get("BRANCH CODE"));
				droolRecord.setPrimaryAcctNo((String)creditCardDetailsMap.get("PRIMARY ACCOUNT"));
				droolRecord.setPrimaryAcctType((String)creditCardDetailsMap.get("PRIMARY ACCOUNT TYPE"));
				droolRecord.setProduct((String)creditCardDetailsMap.get("PRODUCT"));
				droolRecord.setPrimaryAccountProductCode((String)creditCardDetailsMap.get("PRIMARY PRODUCT CODE"));
				droolRecord.setHomeBranchCircleCode((String)creditCardDetailsMap.get("HOME BRANCH CIRCLE CODE"));
				droolRecord.setIssueBranchCircleCode((String)creditCardDetailsMap.get("ISSUE BRANCH CIRCLE CODE"));
				droolRecord.setIssueBranchCode(droolRecord.getHomeBranchCode());
				droolRecord.setBankId(getDroolRecord().getBankId());
				
				if(droolRecord.getHomeBranchCode().trim().isEmpty() == false) {

//					Branch  branch = getBranchDetails(droolRecord.getHomeBranchCode().trim(), droolRecord.getBankId());
					Map<Long, Map<String, Branch>> bankList = DataInitialization.getInstance().getBranchInfo();
					if(bankList != null && bankList.size() > 0 ) {
						Map<String, Branch> branchList = bankList.get(droolRecord.getBankId());
						if(branchList != null && branchList.size() > 0) {
							Branch branch = (Branch) branchList.get(droolRecord.getHomeBranchCode());
							if(branch != null) {
								droolRecord.setAddr1(branch.getAddress1()!= null && !branch.getAddress1().isEmpty()? branch.getAddress1().length()>40?branch.getAddress1().substring(0, 40):branch.getAddress1() : " ");
								droolRecord.setAddr2(branch.getAddress2() != null && !branch.getAddress2().isEmpty()?branch.getAddress2().length()>40?branch.getAddress2().substring(0, 40):branch.getAddress2() : " ");
								droolRecord.setAddr3(branch.getAddress3() != null && !branch.getAddress3().isEmpty()?branch.getAddress3().length()>40?branch.getAddress3().substring(0, 40):branch.getAddress3() : " ");
								droolRecord.setAddr4(branch.getAddress4() != null && !branch.getAddress4().isEmpty()?branch.getAddress4().length()>40?branch.getAddress4().substring(0, 40):branch.getAddress4() : " ");
								droolRecord.setCity ("          ");
								droolRecord.setPin  (branch.getPinCode());
							}
						}
					}
				}
				droolRecord.setInstitutionId("");
				if(droolRecord.getProduct().trim().isEmpty() == false ) {
					Multimap<Long, Product> map = DataInitialization.getInstance().getProductInfo();
					List<Product> productList = (List<Product>) map.get(droolRecord.getBankId());
					if(productList != null && productList.size() > 0) {
						for(Product product : productList) {
							if(product.getShortCode().equals(droolRecord.getProduct())) {
								droolRecord.setInstitutionId(product.getBin());
								break;
							}
						} 
					}
				}	
				else {
					droolRecord.setAddr1("     ");
					droolRecord.setAddr2("     ");
					droolRecord.setAddr3("     ");
					droolRecord.setAddr4("     ");
					droolRecord.setCity ("     ");
					droolRecord.setPin  ("     ");
				}
				droolRecord.setCustomerId            ("                 ");
				droolRecord.setCustomerFirstName     ("          ");
				droolRecord.setCustomerMiddleName    ("          ");
				droolRecord.setCustomerSurName       ("          ");
//				droolRecord.setDateOfBirth           ("        ");
				droolRecord.setOfficePhone           ("          ");
				droolRecord.setFax                   ("          ");
				droolRecord.setHomePhone             ("          ");
				droolRecord.setMobileNo              ("          ");
				droolRecord.setEmail                 ("          ");
				droolRecord.setPrimaryAcctSurviour   (" ");
				droolRecord.setSecondaryAcct1        ("          ");
				droolRecord.setSecondaryAcct2Type    ("  ");
				droolRecord.setSecondarySurviour     (" ");
				droolRecord.setSecondaryAcct2        ("          ");
				droolRecord.setSecondaryAcct1Type    ("  ");
				droolRecord.setSecondaryAcctSurviour (" ");
				droolRecord.setFourthLinePrintingData("          ");
				droolRecord.setYearOfMarriage        ("    ");
				droolRecord.setYearOfPassingSSC      ("    ");
				droolRecord.setMotherMaidenName      ("          ");
				droolRecord.setFathersFirstName      ("          ");
				droolRecord.setSerialNo("          ");
				
				droolRecord.setEmbossName("STATE BANK CUSTOMER");
				droolRecord.setRegistrationDate(new Date());
				droolRecord.setCardStatus("NEW");	
				droolRecord.setBankId(getDroolRecord().getBankId());
				droolRecord.setInviduality(FileStateManager.CORE_NCF_INDIVIDUALITY);
//				boolean composit = isVaildCompositKey(droolRecord.getPrimaryAcctNo(),droolRecord.getProduct());
//				droolRecord.setCompositeValid(composit);
//				if(composit == false) {
//					logger.info("Card already exists");
//					
//					droolRecord.setCardAWB(getCardAWB());
//					setCardAWB("");
//				}
				
			} catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return droolRecord;
		}
		
		public boolean isVaildCompositKey(String primaryAcNo, String productId) {
			try {
				List<CreditCardDetails> list = getGndDAO().checkRecordValidity(primaryAcNo, productId);
				if(list.size() == 0) {
					return true;
				}
				else {
					Date date = DateUtils.addDays(new Date(), -15);
					CreditCardDetails details = (CreditCardDetails) list.get(0);
					Date recordCreatedDate = details.getCreatedDate();
					if(recordCreatedDate.before(date)) {
						return true;
					}
					setCardAWB(details.getCardAWB() != null ? details.getCardAWB() : "");  
				}
			
				
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		return false;
			
		}
		
		
		
		public Branch getBranchDetails(String homeBranchCode, Long bankId) {

			Branch branch = null;
			try {
			List<Branch> branchList = getBranchDAO().getBranch(homeBranchCode, bankId);
			if(branchList.size() > 0) {
				branch = (Branch) branchList.get(0);						
			}
			} catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
			return branch;
		}
		
		
		@SuppressWarnings("rawtypes")
		@Transactional
		public synchronized Map pinMISImport(Exchange exchange) {
			Map map = null;
			try {
				map = (Map) exchange.getIn().getBody();
				String rsn = (String) map.get("RSN");
				if(rsn != null && rsn.trim().isEmpty() == false && Pattern.compile("\\d+").matcher(rsn.trim()).matches()) {
					getGndDAO().changePinStatus(Long.parseLong(rsn));
					
				}
			} catch(Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}			
			return map;
		}
		
		
		public boolean isYrRegistrationValid(String regdDate)	{
			
//			logger.info("regd  is : "+regdDate);
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			if(regdDate == null || regdDate.trim().isEmpty()) {
				return false;
			}
			if(regdDate.length() != 10 || !regdDate.matches("\\d{2}/\\d{2}/\\d{4}")) {
				return false;
			}
			Date date = null;
			try {
				 date = sdf.parse(regdDate.trim());
				 if(date != null) {
					 Date fromDate = sdf.parse("01/01/1990");
//					 logger.info("from date is : "+fromDate);
					if(date.after(new Date()) || date.before(fromDate)) {
						return false;
					}
				}
			} catch (ParseException e) {
//				logger.info("invalid Invalid Registration Date");
				return false;
			}
			
			
			 return true;	
		}
		
		public boolean isDOBValidRange_rule_5y1(String dob)	{
//			logger.info("dob is : "+dob);
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy");
			if(dob == null || dob.trim().isEmpty())	{
				return false;
			}
			if(dob.length() != 8 || Pattern.compile("\\d+").matcher(dob.trim()).matches() == false) {
				return false;
			}
			try {
				 Date date = sdf.parse(dob.trim());
//				 logger.info("dob after parshing is : "+date);
				 if(date != null) {
					if(date.after(new Date()) || date.before(sdf.parse("01011900"))) {
						return false;
					}
				 }
			} catch (ParseException e) {
//				logger.info("in catch block");
				e.printStackTrace();
//				logger.info("invalid date of birth");
				return false;
			}
			return true;
		}
		
		public boolean validateYear(int year) {				
			currYear= Calendar.getInstance().get(Calendar.YEAR);		
			if((year >fromYear) && (year <= currYear)) {
				return true;
			}	
			return false;
		}
		
		@SuppressWarnings("rawtypes")
		@Transactional
		public GenericFile productChangeUpload(GenericFile gfile) {
			try {
				if(properties.getProperty("isProductChange").equals("1")) {
					fileName = gfile.getFileNameOnly();
					List<ProductMapping> list = getGndDAO().getProductMappingList(fileName.substring(0,1));
					if(list != null && list.size() > 0) {
						productMapList = new ArrayList<ProductMappingBO>();
//						Iterator<ProductMapping> itr = list.iterator();
//						while(itr.hasNext()) {
						for(ProductMapping productMap : list) {	
//							ProductMapping productMap = (ProductMapping) itr.next();
							productMapList.add(buildProductMapBO(productMap));
						}
					}
				}
			}
			catch(Exception e) {
				logger.error(e);
				e.printStackTrace();
			}
			return gfile;
		}

		private ProductMappingBO buildProductMapBO(ProductMapping productMap) {
			ProductMappingBO productMapBO = new ProductMappingBO();
			productMapBO.setId(productMap.getId());
			productMapBO.setBankId(productMap.getBankId());
			productMapBO.setOldProduct(productMap.getOldProduct());
			productMapBO.setOldBin(productMap.getOldBin());
			productMapBO.setNewProduct(productMap.getNewProduct());
			productMapBO.setNewBin(productMap.getNewBin());
			return productMapBO;
		}

		public DroolRecordBO productCheck(DroolRecordBO droolRecord) {
			try {
				if(properties.getProperty("isProductChange").equals("1")) {
					if(productMapList != null && productMapList.size()>0) {
						if(!fileName.contains(".ncf")) {
//							Iterator<ProductMappingBO> itr = productMapList.iterator();
//							while (itr.hasNext()) {
							for(ProductMappingBO productMapBO : productMapList) {
//								ProductMappingBO productMapBO = (ProductMappingBO) itr.next();
								Long bankId = droolRecord.getBankId();
								if(bankId == productMapBO.getBankId()) {
									if(droolRecord.getProduct().equals(productMapBO.getOldProduct()) && droolRecord.getInstitutionId().equals(productMapBO.getOldBin())) {
										getDroolRecord().setProductMappingFlag(1);
										getDroolRecord().setProductMappingInfo(RecordEventBO.EVENT_PRODUCT_CHANGE_STRING+droolRecord.getProduct()+" "+droolRecord.getInstitutionId()+RecordEventBO.PRODUCT_CHANGE_SEPERATOR+productMapBO.getNewProduct()+" "+productMapBO.getNewBin());
										droolRecord.setProduct(productMapBO.getNewProduct());
										droolRecord.setInstitutionId(productMapBO.getNewBin());
										break;
									}
								}
							}
						} else if(fileName.contains(".ncf")){
//							Iterator<ProductMappingBO> itr = productMapList.iterator();
//							while (itr.hasNext()) {
							for(ProductMappingBO productMapBO : productMapList) {
//								ProductMappingBO productMapBO = (ProductMappingBO) itr.next();
								Long bankId = droolRecord.getBankId();
								if(bankId == productMapBO.getBankId()) {
									if(droolRecord.getProduct().equals(productMapBO.getOldProduct())) {
										getDroolRecord().setProductMappingFlag(1);
										getDroolRecord().setProductMappingInfo(RecordEventBO.NCF_EVENT_PRODUCT_CHANGE_STRING+droolRecord.getProduct()+RecordEventBO.PRODUCT_CHANGE_SEPERATOR+productMapBO.getNewProduct());
										droolRecord.setProduct(productMapBO.getNewProduct());
										droolRecord.setInstitutionId(productMapBO.getNewBin());
										break;
									}
								}
							}
							
						}
					} 
						
					}
				} catch(Exception e) {
					logger.error(e);
					e.printStackTrace();
				} 
			return droolRecord;
		}
		
//		@Transactional
		public DroolRecordBO productIgnore(DroolRecordBO droolRecord) {
			try {
				Multimap<Long, Product> map = DataInitialization.getInstance().getProductInfo();
				if(map != null && map.size()  > 0 ) {
					List<Product> productList = (List<Product>) map.get(droolRecord.getBankId());
					if(productList == null || productList.size() ==  0) {
						droolRecord.setFlowStatus(DroolRecordBO.FLOW_IGNORE_ERR);
						droolRecord.setRuleStatus(DroolRecordBO.RULE_IGNORE_ERR);
						droolRecord.setStatus((long)CardStateManagerService.CARD_STATUS_PENDING);
					} else {
						boolean productFound = false;
						for(Product product : productList) {
							if(product.getShortCode().equals(droolRecord.getProduct())) {
								productFound = true;
								break;
							}
						}
						if(productFound == false) {
							droolRecord.setFlowStatus(DroolRecordBO.FLOW_IGNORE_ERR);
							droolRecord.setRuleStatus(DroolRecordBO.RULE_IGNORE_ERR);
							droolRecord.setStatus((long)CardStateManagerService.CARD_STATUS_PENDING);
						}
					}
				}
			} catch(Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
			
			return droolRecord;
		}

			
	}
